package edu.msstate.nsparc.wings.integration.tests.participant.dashboard;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.JobMatchingForm;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static framework.customassertions.CustomAssertion.softAssertContains;

/**
 * Adding OSOC and checking job matches
 * Created by a.korotkin on 11/15/2016.
 */

@TestCase(id = "WINGS-11198")
public class TC_33_06_DB_Add_OSOC_And_Check_Job_Matches extends BaseWingsTest {

    public void main() {
        String osoc = "Management Analysts";
        info("Create self participant to work with");
        BaseNavigationSteps.loginParticipant();
        Participant participant = new Participant();
        ParticipantCreationForm creationForm = new ParticipantCreationForm(Constants.PARTICIPANT_SS);
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);

        logStep("Adding OSOC");
        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.clickWhyTheseJobs();
        JobMatchingForm jobMatchingForm = new JobMatchingForm(Constants.PARTICIPANT_SS);
        jobMatchingForm.typeAndSelectOSOCS_S(osoc);

        logStep("Checking that OSOC is added");
        jobMatchingForm.getOSOCS_S(2, osoc);
        BaseNavigationSteps.home();

        logStep("Opening any job from the Job Matches section");
        String jobTitle = participantHomePage.getJobTitle();
        participantHomePage.clickFirstJob();

        logStep("Checking that correct job is opened");
        JobOrderDetailsForm jobOrderDetailsForm = new JobOrderDetailsForm();
        softAssertContains(jobOrderDetailsForm.getJobTitleS_S(), jobTitle, "Incorrect Job is opened");
    }
}
