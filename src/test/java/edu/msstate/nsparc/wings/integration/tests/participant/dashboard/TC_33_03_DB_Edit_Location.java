package edu.msstate.nsparc.wings.integration.tests.participant.dashboard;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantEditForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static framework.customassertions.CustomAssertion.softAssertEquals;

/**
 * Edit location of the user and check that all applied changes are reflected on the Dashboard
 * Created by a.korotkin on 11.11.2016.
 */

@TestCase(id = "WINGS-11195")
public class TC_33_03_DB_Edit_Location extends BaseWingsTest {

    public void main() {
        info("Create self participant to work with");
        BaseNavigationSteps.loginParticipant();
        Participant participant = new Participant();
        ParticipantCreationForm creationForm = new ParticipantCreationForm(Constants.PARTICIPANT_SS);
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.TRUE, Constants.FALSE);

        BaseNavigationSteps.loginParticipant();
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);
        participantHomePage.clickEditLocation();
        ParticipantEditForm participantEditForm = new ParticipantEditForm(Constants.CONTACT);
        participantEditForm.fillLocations(participant);
        softAssertEquals(participantHomePage.getTextOfLocation(),
                participant.getLocationHome() + " Edit Location", "Location of the Participant is incorrect");
    }
}
