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


/**
 * Created by a.vnuchko on 28.02.2017.
 */

@TestCase(id = "WINGS-10413")
public class TC_04_08_01_Manage_JO_Search_Status extends BaseWingsTest {
    private String status = "Pending";

    public void main() {
        info("Precondition: create employer and job order first");
        Employer employer = EmployerSteps.createEmployerSelfRegistration();
        LoginForm loginForm = new LoginForm();
        loginForm.loginEmployer();
        EmployerHomePage homePage = new EmployerHomePage(employer.getCompanyName());
        JobOrderCreationForm jobOrderCreationForm = homePage.openJobOrderCreate();
        JobOrder jobOrder = new JobOrder();
        jobOrder.setEmployer(employer);
        jobOrderCreationForm.createJobOrderSelfServices(jobOrder, false, false);

        logStep("Manage job openings and Search");
        homePage.openJobOrderSearch();

        logStep("Select some status you need->Search");
        logResult("All job orders matched the search criteria are shown");
        new JobOrderSearchForm(Constants.PARTICIPANT_SS).performStatusSearchValidate(status, jobOrder);
    }
}
