package edu.msstate.nsparc.wings.integration.tests.participant.jobVites;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.employer.employerSS.JobCandidateDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.employer.employerSS.JobViteExtendedForm;
import edu.msstate.nsparc.wings.integration.forms.employer.employerSS.JobViteSendForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.JobViteSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.EmployerSqlFunctions;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.tests.participant.editProfile.TC_31_01_EP_General_Standard_View;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import webdriver.Browser;
import framework.CommonFunctions;


/**
 * Create job-vite for a participant and check, that all job-vites are displayed correctly for him
 * Created by a.vnuchko on 24.02.2017.
 * Updated by a.korotkin on 18.04.2017
 */
@TestCase(id = "WINGS-10428")
public class TC_36_10_Job_Vites_list_Opening extends TC_31_01_EP_General_Standard_View {
    Participant participant;
    Integer countJobVites = 2;
    String[] jobTitles = new String[countJobVites];

    public void main() {
        info("Precondition: make some job-vites for a participant");
        participant = precondition();

        info("Create an employer");
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSqlFunctions.createEmployerUsingSQL(employer);

        info("Create job orders from Staff side");
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

        info("Check that all jobs are displayed");
        JobViteSearchForm jobViteSearchForm = new JobViteSearchForm();
        jobViteSearchForm.checkAllJobVites(jobTitles);
    }

    /**
     * Create several job openings
     * @param employer - employer.
     */
    protected void makeSomeJobOpening(Employer employer) {
        info("Create Job Order");
        JobOrder jobOrder = new JobOrder();
        jobOrder.setEmployer(employer);
        jobOrder = EmployerSqlFunctions.createJobOrderUsingSQL(jobOrder);
        jobTitles[0] = jobOrder.getJobTitle();
        for (int i = 1; i < countJobVites; i++) {
            jobOrder.setJobTitle(CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH));
            jobOrder = EmployerSqlFunctions.createJobOrderUsingSQL(jobOrder);
            jobTitles[i] = jobOrder.getJobTitle();
        }
    }
}
