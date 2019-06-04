package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**Create a job opening open, its participantSSDetails page and check, all information displayed correctly.
 * Created by User on 28.02.2017.
 */

@TestCase(id = "WINGS-10606")
public class TC_04_17_Manage_JO_View_Opening extends BaseWingsTest {
    Employer employer;
    JobOrder jobOrder;
    EmployerHomePage homePage;

    public void main(){
        repeatedSteps();

        logStep("Manage Job Openings and search for a job order");
        JobOrderSearchForm searchForm = homePage.openJobOrderSearch();
        searchForm.findJobOrder(jobOrder.getJobTitle());

        logStep("Open one of the job orders");
        searchForm.clickJobTitle();

        logResult("View Opening page is shown. All information are displayed correctly");
        new JobOrderDetailsForm(jobOrder.getJobTitle()).validateJobOrderSS(jobOrder);

    }

    /**
     * Steps that are similar to steps in other test
     * @return created job order.
     */
    protected JobOrder repeatedSteps(){
        info("We need to create Employer first");
        employer = EmployerSteps.createEmployerSelfRegistration();

        info("Login employer");
        LoginForm loginForm = new LoginForm();
        loginForm.loginEmployer();

        info("Navigate to Employer Details Page");
        homePage = new EmployerHomePage(employer.getCompanyName());
        JobOrderCreationForm jobOrderCreationForm = homePage.openJobOrderCreate();

        info("Create Job Order");
        jobOrder = new JobOrder();
        jobOrder.setEmployer(employer);
        jobOrderCreationForm.createJobOrderSelfServices(jobOrder, false, false);
        return jobOrder;
    }
}
