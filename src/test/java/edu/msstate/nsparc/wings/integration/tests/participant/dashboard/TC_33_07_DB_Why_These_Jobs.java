package edu.msstate.nsparc.wings.integration.tests.participant.dashboard;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.JobMatchingForm;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static framework.customassertions.CustomAssertion.softAssertEquals;

/**
 * Checking [Why These Jobs?]
 * Created by a.korotkin on 11/15/2016.
 */

@TestCase(id = "WINGS-11199")
public class TC_33_07_DB_Why_These_Jobs extends BaseWingsTest {

    public void main() {
        String question = "Notify me about the matching jobs that are";
        info("Create self participant to work with");
        BaseNavigationSteps.loginParticipant();
        Participant participant = new Participant();
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);

        logStep("Searching for created Job Order");
        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.clickWhyTheseJobs();
        JobMatchingForm jobMatchingForm = new JobMatchingForm(Constants.PARTICIPANT_SS);
        softAssertEquals(jobMatchingForm.getFirstQuestionS_S(), question, "Incorrect page is opened");
    }
}
