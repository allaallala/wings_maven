package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import org.testng.Assert;


@TestCase(id = "WINGS-10412")
public class TC_04_01_Manage_Job_Openings_Create_And_Approve extends BaseWingsTest {
    private String status = "OPEN";

    public void main() {
        info("We need to create Employer first");
        Employer employer = EmployerSteps.createEmployerSelfRegistration();

        logStep("Login employer");
        LoginForm loginForm = new LoginForm();
        loginForm.loginEmployer();

        logStep("Navigate to Employer Details Page");
        EmployerHomePage homePage = new EmployerHomePage(employer.getCompanyName());
        JobOrderCreationForm jobOrderCreationForm = homePage.openJobOrderCreate();

        logStep("Create Job Order");
        JobOrder jobOrder = new JobOrder();
        jobOrder.setEmployer(employer);
        jobOrderCreationForm.createJobOrderSelfServices(jobOrder, false, false);

        logStep("Navigate to My Openings page");
        JobOrderSearchForm searchForm = homePage.openJobOrderSearch();

        logStep("Find created Job Opening");
        searchForm.findJobOrder(jobOrder.getJobTitle());
        searchForm.checkInternalError();

        logStep("Check found Job Opening information");
        CustomAssertion.softAssertContains(searchForm.getJobTitleText(), jobOrder.getJobTitle(), "Incorrect job title text on the page");
        CustomAssertion.softTrue("Incorrect creation date", searchForm.getCreationDate().contains(jobOrder.getCreationDate()));
        CustomAssertion.softTrue("Incorrect job order status", searchForm.getFirstStatusTable().contains(jobOrder.getStatus()));
        BaseNavigationSteps.logout();

        logStep("Login as Admin");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Job Orders -> Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_WP_JOB_ORDERS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Find created Job Order");
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();
        jobOrderSearchForm.performSearch(jobOrder);
        jobOrderSearchForm.clickJobTitle();

        logStep("Approve Job Order");
        JobOrderDetailsForm jobOrderDetailsForm = new JobOrderDetailsForm();
        jobOrderDetailsForm.editStaffInformation();
        jobOrderCreationForm.clickJobDevelopmentNo();
        jobOrderCreationForm.inputNonVeteranReleaseDate(CommonFunctions.getCurrentDate());
        jobOrderCreationForm.clickButton(Buttons.Save);
        jobOrderDetailsForm.clickButton(Buttons.Done); //TODO ISSUE https://jira.nsparc.msstate.edu/browse/WINGS-9329

        logStep("Validate Status is 'Open'");
        jobOrderSearchForm.clickButton(Buttons.Search);
        Assert.assertTrue(searchForm.getFirstStatusTable().contains(status));
    }
}
