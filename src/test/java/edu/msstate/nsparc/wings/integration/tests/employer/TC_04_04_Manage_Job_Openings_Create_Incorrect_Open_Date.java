package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10588")
public class TC_04_04_Manage_Job_Openings_Create_Incorrect_Open_Date extends BaseWingsTest {

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
        jobOrderCreationForm.createJobOrderSelfServicesWithIncorrectOpenDate(jobOrder.getJobTitle(), Constants.FALSE);

        logStep("Navigate to My Openings page");
        JobOrderSearchForm searchForm = homePage.openJobOrderSearch();

        logStep("Find created Job Opening");
        searchForm.findJobOrder(jobOrder.getJobTitle());

        logStep("Check found Job Opening information");
        CustomAssertion.softAssertContains(searchForm.getJobTitleText(), jobOrder.getJobTitle(), "Incorrect job title text on the page");
        CustomAssertion.softAssertContains(searchForm.getCreationDate(), jobOrder.getCreationDate(), "Incorrect creation date");
        CustomAssertion.softAssertContains(searchForm.getFirstStatusTable(), jobOrder.getStatus(), "Incorrect first status");
    }

}
