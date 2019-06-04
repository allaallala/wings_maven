package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderEditForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.JobOrderSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.AssertJUnit.assertTrue;


@TestCase(id = "WINGS-10624")
public class TC_05_09_Job_Order_Assign_Locked extends BaseWingsTest {


    //sub-task WINGS-2757
    public void main() {

        info("Creating Job Order");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);
        JobOrderSteps.createJobOrder(jobOrder);

        logStep("Log in WINGS as Admin");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Open Job Order search form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for created Job Order");
        JobOrderSearchForm searchForm = new JobOrderSearchForm();
        searchForm.performSearch(jobOrder);

        logStep("Open Job Order");
        searchForm.clickJobTitle();
        JobOrderDetailsForm detailsForm = new JobOrderDetailsForm();

        logStep("Click on 'Edit Staff Information' button");
        detailsForm.editStaffInformation();

        logStep("Choose Staff member for Job Order lock");
        JobOrderEditForm editForm = new JobOrderEditForm();
        editForm.selectStaff();

        logStep("Save Job Order");
        editForm.clickButton(Buttons.Save);

        logStep("Log out from WINGS");
        BaseNavigationSteps.logout();

        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Search);

        searchForm.performSearch(jobOrder);
        searchForm.clickJobTitle();

        logStep("Check that Admin is still able to edit Job Order");
        detailsForm = new JobOrderDetailsForm();
        assertTrue("Edit buttons are not displayed", detailsForm.checkStaffEditable());

        logEnd();
    }
}
