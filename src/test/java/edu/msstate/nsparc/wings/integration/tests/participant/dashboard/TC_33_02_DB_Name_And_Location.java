package edu.msstate.nsparc.wings.integration.tests.participant.dashboard;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static framework.customassertions.CustomAssertion.softAssertEquals;

/**
 * Checking that correct user name and location are displayed
 * Created by a.korotkin on 11.11.2016.
 */

@TestCase(id = "WINGS-11194")
public class TC_33_02_DB_Name_And_Location extends BaseWingsTest {

    public void main() {
        info("Create self participant to work with");
        BaseNavigationSteps.loginParticipant();
        Participant participant = new Participant();
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);

        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        softAssertEquals(participantHomePage.getParticipantName(),
                participant.getFullName(), "Incorrect participant Name");
        softAssertEquals(participantHomePage.getTextOfLocation(),
                participant.getLocationHome() + " Edit Location", "Location of the Participant is incorrect");
    }
}
