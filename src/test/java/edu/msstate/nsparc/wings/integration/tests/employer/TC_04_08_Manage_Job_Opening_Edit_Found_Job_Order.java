package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderEditForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10414")
public class TC_04_08_Manage_Job_Opening_Edit_Found_Job_Order extends BaseWingsTest {


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

        logStep("Open found Job Opening");
        searchForm.clickJobTitle();

        logStep("Click Edit");
        JobOrderDetailsForm detailsForm = new JobOrderDetailsForm(jobOrder.getJobTitle());
        detailsForm.clickButton(Buttons.Edit);

        logStep("Click Full Edit");
        JobOrderEditForm editForm = new JobOrderEditForm(jobOrder.getJobTitle());
        editForm.clickButton(Buttons.Edit);

        logStep("Change Job Title");
        jobOrder.setJobTitle(CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH));
        editForm.inputJobTitle(jobOrder.getJobTitle());
        editForm.clickButton(Buttons.Continue);
        editForm.clickButton(Buttons.Save);

        logStep("Return to My Openings page");
        detailsForm.clickButton(Buttons.Done);

        logStep("Find edited Job Opening");
        searchForm.findJobOrder(jobOrder.getJobTitle());
        CustomAssertion.softAssertContains(searchForm.getJobTitleText(), jobOrder.getJobTitle(), "Incorrect job title text on the page");
    }
}
