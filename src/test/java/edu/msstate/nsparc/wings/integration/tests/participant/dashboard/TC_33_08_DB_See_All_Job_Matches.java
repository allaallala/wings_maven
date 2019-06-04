package edu.msstate.nsparc.wings.integration.tests.participant.dashboard;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.JobFindForm;
import edu.msstate.nsparc.wings.integration.forms.JobMatchingForm;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static framework.customassertions.CustomAssertion.softAssertContains;
import static framework.customassertions.CustomAssertion.softAssertEquals;

/**
 * Checking [See All # Matches] and the counter on this button
 * Created by a.korotkin on 11/15/2016.
 */

@TestCase(id = "WINGS-11200")
public class TC_33_08_DB_See_All_Job_Matches extends BaseWingsTest {

    public void main() {
        String osoc = "Management Analysts";
        String titleOfPage = "Job Matches";
        info("Create self participant to work with");
        BaseNavigationSteps.loginParticipant();
        Participant participant = new Participant();
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);

        logStep("Adding OSOC");
        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.clickWhyTheseJobs();
        JobMatchingForm jobMatchingForm = new JobMatchingForm(Constants.PARTICIPANT_SS);
        jobMatchingForm.typeAndSelectOSOCS_S(osoc);

        logStep("Checking that OSOC is added");
        jobMatchingForm.getOSOCS_S(2, osoc);

        logStep("Getting the number of Matches");
        BaseNavigationSteps.home();
        String numOfMatches = participantHomePage.getNumberOfMatches().replace(",","");

        logStep("Checking the counter of Matches");
        participantHomePage.clickSeeAllMatches();
        JobFindForm jobFindForm = new JobFindForm(Constants.PARTICIPANT_SS);
        softAssertContains(jobFindForm.getPageTitle(), titleOfPage, "Incorrect page is displayed");
        softAssertEquals(jobFindForm.getJobResultNumber(), numOfMatches, "Incorrect number of Jobs/Matches is displayed");
    }
}
