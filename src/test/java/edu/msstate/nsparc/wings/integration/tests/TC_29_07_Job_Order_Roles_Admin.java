package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderEditForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Check functionality of the job order for an administrator
 * Created by a.vnuchko on 23.02.2016.
 */

@TestCase(id = "WINGS-11139")
public class TC_29_07_Job_Order_Roles_Admin extends BaseWingsTest {
    private JobOrder jobOrder;
    private String newJobTitle = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
    private String newCloseDate = CommonFunctions.getDaysNextDate(CommonFunctions.getCurrentDate(), 5);

    public void main() {

        //Role - administrator
        User user = new User(Roles.ADMIN);
        commonStepsJobOrder(user);
    }

    /**
     * Put some steps in one method
     * @param user - current user
     *
     */
    protected void commonStepsJobOrder(User user) {

        //(!) Create job order, if possible
        if (user.getOrder().getCreate()) {
            logStep("Create new job order");
            AccountUtils.init();
            //jobOrder = new JobOrder(user.getRole());
            jobOrder = user.getOrder();
            EmployerSteps.createEmployer(jobOrder.getEmployer(), user.getRole());
            createJobOrder(jobOrder, user);
        }

        //(!) Verify / release
        logStep("Verify/Release");
        verifyRelease(user);

        logStep("Check other functionality");
        checkOtherFunctionality(user);
        BaseNavigationSteps.logout();
    }


    /**
     * Create new job order
     * @param jobOrder - title of the job
     * @param user - user
     */
    protected void createJobOrder(JobOrder jobOrder, User user) {
        logStep("Login as a " + user.getRole() + " and open creation form");
        BaseWingsSteps.openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Create);

        logStep("Fill out creation form");
        JobOrderCreationForm jobOrdedCreationPage = new JobOrderCreationForm();
        jobOrdedCreationPage.createJobOrderWithUser(user, jobOrder);

        BaseNavigationSteps.logout();

    }

    /**
     * Open job order participantSSDetails form.
     * @param user - user role
     */
    protected void checkOtherFunctionality(User user) {

        logStep("Check jobOrder view");
        BaseWingsSteps.logInAs(user.getRole());

        divideMessage("Navigate to " + WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS.name());
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS);

        //(!) If user can create job order, he has to confirm the pop-up (create/search)
        if (user.getOrder().getCreate()) {
            BaseWingsSteps.popClick(Popup.Search);
        }

        divideMessage("Search for job order and open participantSSDetails page");
        JobOrderSearchForm searchPage = new JobOrderSearchForm();
        searchPage.checkInternalError();
        searchPage.performSimpleSearch(jobOrder.getJobTitle());
        JobOrderDetailsForm detailsPage = new JobOrderDetailsForm();
        detailsPage.validateBasicInformation(jobOrder);

        //(!) Check required buttons
        logStep("Check for required buttons present or not on the page");
        detailsPage.checkJobOrderButtons(user);

        //(!) Clone job order
        if (user.getOrder().getClone()) {
            logStep("Check clone functionality");
            detailsPage.cloneOrder(jobOrder, user);
        }

        //(!) Edit job order
        if (user.getOrder().getEdit()) {
            logStep("Check edit functionality, if possible");
            jobOrder.setJobTitle(newJobTitle);
            jobOrder.setCloseDate(newCloseDate);
            detailsPage.edit(jobOrder, user.getRole());
            detailsPage.validateBasicInformation(jobOrder);
            if (user.getRole().equals(Roles.ADMIN)) {
                detailsPage.validateDisclaimer(jobOrder);
            }
        }

        //(!) Edit staff lookup
        if (user.getOrder().getEditStaffLookup()) {
            logStep("Edit staff lookup");
            detailsPage.editStaffInformation();
            JobOrderEditForm editPage = new JobOrderEditForm();
            editPage.editStaffLookup(jobOrder);
        }

    }

    /**
     * Verify / Release is not working now.
     */
    protected void verifyRelease(User user) {
        if (user.getOrder().getJoVerifyRelease()) {
            info("verifyRelease");
        } else {
            info("Release is not working now");
        }
    }
}
