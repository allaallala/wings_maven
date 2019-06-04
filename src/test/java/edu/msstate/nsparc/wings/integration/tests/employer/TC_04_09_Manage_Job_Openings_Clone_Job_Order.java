package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10415")
public class TC_04_09_Manage_Job_Openings_Clone_Job_Order extends BaseWingsTest {

    public void main() {
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
        jobOrderCreationForm.createJobOrderSelfServices(jobOrder, false, true);

        logStep("Navigate to My Openings page");
        JobOrderSearchForm searchForm = homePage.openJobOrderSearch();

        logStep("Find created and cloned Job Openings");
        searchForm.findJobOrder(jobOrder.getJobTitle());

        logStep("Check found Job Opening information");
        // Check created Job Order
        CustomAssertion.softAssertContains(searchForm.getJobTitleText(), jobOrder.getJobTitle(), "Incorrect job title");
        CustomAssertion.softTrue("Incorrect creation date", searchForm.getCreationDate().contains(jobOrder.getCreationDate()));
        CustomAssertion.softTrue("Incorrect first status", searchForm.getFirstStatusTable().contains(jobOrder.getStatus()));

        // Check cloned Job Order
        searchForm.checkSecondJobData(jobOrder);
    }
}
