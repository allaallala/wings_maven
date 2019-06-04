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
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import webdriver.Browser;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Create more than 10 job-vites and apply them for a chosen participant. Check, that pagination and sorting is correct.
 * Created by a.vnuchko on 25.02.2017.
 * Updated by a.korotkin on 25.04.2017
 */

@TestCase(id = "WINGS-11238")
public class TC_36_12_Job_Vites_Check_Pagination_Sorting extends TC_36_10_Job_Vites_list_Opening {
    private Integer iterations = 11;
    private String[] jobTitles = new String[iterations];
    private Employer[] employers = new Employer[iterations];

    public void main() {
        info("Precondition: make some job-vites for a participant");
        participant = precondition();
        String partID = ParticipantSqlFunctions.getParticipantIdFname(participant.getFirstName());
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        for (int i = 0; i < iterations; i++) {
            if (i != 0) {
                AccountUtils.initEmployer();
                employer = new Employer(AccountUtils.getEmployerAccount());
            }
            employers[i] = EmployerSqlFunctions.createEmployerUsingSQL(employer);
            info("Create job orders from Staff side");
            makeSomeJobOpening(employers[i], i);

            info("Open candidate participantSSDetails");
            BaseNavigationSteps.loginEmployer();
            EmployerHomePage employerHomePage = new EmployerHomePage(employers[i].getCompanyName());
            employerHomePage.searchCandidates();
            String newURL = Browser.getDriver().getCurrentUrl() + String.format("&_eventId=view&selectedId=%1$s&jobSource=SPUR", partID);
            Browser.getInstance().navigate(newURL);

            info("Send Job-Vites");
            JobCandidateDetailsForm jobCandidateDetailsForm = new JobCandidateDetailsForm();
            jobCandidateDetailsForm.clickInterested();
            JobViteSendForm jobViteSendForm = new JobViteSendForm();
            jobViteSendForm.sendJobVite();
            JobViteExtendedForm jobViteExtendedForm = new JobViteExtendedForm();
            jobViteExtendedForm.clickReturnToDetails();
            BaseNavigationSteps.logout();
        }
        BaseNavigationSteps.loginParticipant();

        info("View Job-Vites");
        ParticipantHomePage participantHomePage = new ParticipantHomePage(participant);
        participantHomePage.viewJobVites();
        ArrayList listOfTitles = new ArrayList();
        ArrayList listOfEmployers = new ArrayList();
        Collections.addAll(listOfTitles, jobTitles);
        Collections.sort(listOfTitles);

        info("Check pagination");
        JobViteSearchForm jobViteSearchForm = new JobViteSearchForm();
        jobViteSearchForm.checkPaginationExists();

        info("Check sorting");
        jobViteSearchForm.clickSortByTitle();
        jobViteSearchForm.checkTitleSorting(listOfTitles);
        jobViteSearchForm.clickSortByTitle();
        Collections.reverse(listOfTitles);
        jobViteSearchForm.checkTitleSorting(listOfTitles);
        for (Employer employerx: employers) {
            listOfEmployers.add(employerx.getCompanyName());
        }
        Collections.sort(listOfEmployers);
        jobViteSearchForm.clickSortByEmployer();
        jobViteSearchForm.checkEmployerSorting(listOfEmployers);
        Collections.reverse(listOfEmployers);
        jobViteSearchForm.clickSortByEmployer();
        jobViteSearchForm.checkEmployerSorting(listOfEmployers);
    }

    /**
     * Create several job openings
     * @param employer - employer.
     */
    protected void makeSomeJobOpening(Employer employer, Integer i) {
        info("Create Job Order");
        JobOrder jobOrder = new JobOrder();
        jobOrder.setEmployer(employer);
        jobOrder = EmployerSqlFunctions.createJobOrderUsingSQL(jobOrder);
        jobTitles[i] = jobOrder.getJobTitle();
    }
}
