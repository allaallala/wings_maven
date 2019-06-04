package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10541")
public class TC_02_03_Dashboard_Help_Information extends BaseWingsTest {

    private static final String HELP_INFORMATION_STRING = "We have prepared a handy list of frequently asked questions that cover"
            + " everything from finding a job to getting unemployment insurance.";


    public void main() {

        info("We need to create Participant first");
        Participant participant = ParticipantCreationSteps.createParticipantSelfRegistration();
        ParticipantDetailSteps.addEmploymentParticipantSelfService(participant);
        ParticipantDetailSteps.addAcademicRecordSelfService(participant);

        logStep("Login to the System");
        BaseNavigationSteps.loginParticipant();

        logStep("Check information for assistance on home page");
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.clickJobTips();

        CustomAssertion.softTrue("Assert help information failed!", participantHomePage.getHelpInformation().contains(HELP_INFORMATION_STRING));
    }
}
