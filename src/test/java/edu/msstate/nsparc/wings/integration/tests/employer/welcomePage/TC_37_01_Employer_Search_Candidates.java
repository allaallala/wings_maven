package edu.msstate.nsparc.wings.integration.tests.employer.welcomePage;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.employer.employerSS.JobCandidateDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.employer.employerSS.JobCandidateSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check, that  all links on the candidate search are working correctly (Employer SS)
 * Created by User on 26.02.2017.
 */

@TestCase(id = "WINGS-11239")
public class TC_37_01_Employer_Search_Candidates extends BaseWingsTest {

    public void main(){
        preconditions("We need to create Employer first");
        EmployerSteps.createEmployerSelfRegistration();

        logStep("Login employer");
        LoginForm loginForm = new LoginForm();
        loginForm.loginEmployer();

        logStep("Perform Job Candidate Search");
        EmployerHomePage employerHomePage = new EmployerHomePage();
        employerHomePage.clickSearch();

        JobCandidateSearchForm candiSearchPage = new JobCandidateSearchForm();
        logStep("Click on any Job Candidate from list");
        candiSearchPage.clickFirstCandidate();

        logStep("Check all links/buttons on Job Candidate Details page");
        logResult("Job Candidate Details page displayed correctly. All links/buttons works correctly");
        JobCandidateDetailsForm detailsPage = new JobCandidateDetailsForm();
        detailsPage.checkDefaultButtonsLinks();
    }
}
