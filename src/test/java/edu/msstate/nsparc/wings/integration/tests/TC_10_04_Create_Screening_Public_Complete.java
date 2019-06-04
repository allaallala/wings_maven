package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderStaffCreation.BasicInformationStaffForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderStaffCreation.JobRequirementsStaffForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * While creating job order, is chosen, that this job is at public school. Check not editable questions, a job order is
 * successully created.
 * Created by a.vnuchko on 04.04.2017.
 */

@TestCase(id = "WINGS-10720")
public class TC_10_04_Create_Screening_Public_Complete extends BaseWingsTest {

    public void main() {

        info("It's necessary to create an employer first to use it on creating a job order");
        JobOrder jobOrder = new JobOrder(AccountUtils.getEmployerAccount());

        JobOrderCreationForm creationPage = repeatedSteps(jobOrder);

        logResult("Check, that not editable questions are displayed.");

        logStep("Create");
        creationPage.clickButton(Buttons.Continue);
        creationPage.clickAllowOnline(Constants.TRUE);
        creationPage.clickJobDevelopmentNo();
        creationPage.clickButton(Buttons.Create);
        BaseNavigationSteps.logout();

        logResult("Job Order with added questions should be successfully created");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Search);
        JobOrderSearchForm searchForm = new JobOrderSearchForm();
        searchForm.performSearch(jobOrder);
    }

    /**
     * This method represents repeated steps for other test
     *
     * @param jobOrder - job order
     * @return job order creation form.
     */
    protected JobOrderCreationForm repeatedSteps(JobOrder jobOrder) {
        String cooksOsoc = "Cooks, Restaurant";
        EmployerSteps.createEmployer(jobOrder.getEmployer(), Roles.STAFF);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS, Popup.Create);

        logStep("Select employer and job title and add occupation code");
        JobOrderCreationForm creationPage = new JobOrderCreationForm();
        creationPage.selectEmployer(jobOrder.getEmployer());
        creationPage.selectOsocCode(cooksOsoc);
        new BasicInformationStaffForm().fillTitleDateDescription(jobOrder.getJobTitle(), CommonFunctions.getCurrentDate(), CommonFunctions.getNextWeekDate());

        logStep("Select 'Yes' value in the 'Is this job at a Public School in MS?' radio button group");
        creationPage.clickJobPublicSchool(Constants.TRUE);

        logStep("Fill in all required fields->Go to the page with question");
        creationPage.clickButton(Buttons.Continue);
        new JobRequirementsStaffForm().passWorkingHoursFields();
        creationPage.clickButton(Buttons.Continue);
        return creationPage;
    }
}
