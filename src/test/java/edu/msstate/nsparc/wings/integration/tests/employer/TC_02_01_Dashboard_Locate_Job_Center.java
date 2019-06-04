package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.JobCenterSearchForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10534")
public class TC_02_01_Dashboard_Locate_Job_Center extends BaseWingsTest {

    public void main() {
        logStep("Find a WIN Job Center");
        JobCenterSearchForm jobCenterSearchForm = loginJobCenterSearch();

        logStep("Contact information about Job center is shown");
        jobCenterSearchForm.checkJobCenterAddress();
    }

    /**
     * Login as an employer and open Job Center Search Form
     * @return - job center
     */
    protected JobCenterSearchForm loginJobCenterSearch(){
        info("We need to create Employer first");
        Employer employer = EmployerSteps.createEmployerSelfRegistration();

        logStep("Login to the System");
        LoginForm loginForm = new LoginForm();
        loginForm.loginEmployer();

        logStep("Home");
        EmployerHomePage employerHomePage = new EmployerHomePage(employer.getCompanyName());
        return employerHomePage.openJobCenterSearch();
    }
}
