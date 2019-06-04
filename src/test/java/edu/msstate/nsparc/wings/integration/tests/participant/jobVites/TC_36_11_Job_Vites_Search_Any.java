package edu.msstate.nsparc.wings.integration.tests.participant.jobVites;

import edu.msstate.nsparc.wings.integration.forms.employer.employerSS.JobCandidateDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.employer.employerSS.JobViteExtendedForm;
import edu.msstate.nsparc.wings.integration.forms.employer.employerSS.JobViteSendForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.JobViteSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.EmployerSqlFunctions;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import webdriver.Browser;


/**
 * Create several job-vites and apply for chosen participant. Check, that all job-vites are displayed on the page,
 * using different search criteria. Also check, that each status is highlighted correctly.
 * Created by a.vnuchko on 24.02.2017.
 */

@TestCase(id = "WINGS-11237")
public class TC_36_11_Job_Vites_Search_Any extends TC_36_10_Job_Vites_list_Opening {

    public void main(){
        info("Precondition: make some job-vites for a participant");
        participant = precondition();

        //Employer employer = BaseEmployerFunctions.createEmployerSelfRegistration();
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSqlFunctions.createEmployerUsingSQL(employer);
        makeSomeJobOpening(employer);

        info("Open candidate participantSSDetails");
        BaseNavigationSteps.loginEmployer();
        EmployerHomePage employerHomePage = new EmployerHomePage(employer.getCompanyName());
        employerHomePage.searchCandidates();
        String partID = ParticipantSqlFunctions.getParticipantIdFname(participant.getFirstName());
        String newURL = Browser.getDriver().getCurrentUrl() + String.format("&_eventId=view&selectedId=%1$s&jobSource=SPUR", partID);
        Browser.getInstance().navigate(newURL);
        info("Send Job-Vites");
        for (int i = 0; i < countJobVites; i++) {
            JobCandidateDetailsForm jobCandidateDetailsForm = new JobCandidateDetailsForm();
            jobCandidateDetailsForm.clickInterested();
            JobViteSendForm jobViteSendForm = new JobViteSendForm();
            jobViteSendForm.sendJobVite();
            JobViteExtendedForm jobViteExtendedForm = new JobViteExtendedForm();
            jobViteExtendedForm.clickReturnToDetails();
        }
        BaseNavigationSteps.logout();
        BaseNavigationSteps.loginParticipant();

        info("View Job-Vites");
        ParticipantHomePage participantHomePage = new ParticipantHomePage(participant);
        participantHomePage.viewJobVites();

        info("Check searching of each job");
        JobViteSearchForm jobViteSearchForm = new JobViteSearchForm();
        jobViteSearchForm.searchEachJob(jobTitles);
    }
}
