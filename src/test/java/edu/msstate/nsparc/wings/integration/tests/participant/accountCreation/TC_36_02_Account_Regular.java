package edu.msstate.nsparc.wings.integration.tests.participant.accountCreation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * There is a user with created MS Account. Login with this account and create a regular participant (Not a veteran, not a mississippi national guard)
 * Created by a.vnuchko on 16.01.2017.
 */

@TestCase(id = "WINGS-11230")
public class TC_36_02_Account_Regular extends TC_36_01_Account_General_Create {

    public void main() {
        fillParticipantInfoDontComplete(false, false);

        logStep("Login to the System with Access MS Username and Password");
        BaseNavigationSteps.loginParticipant();

        info("On the Classification page select 'No' from the 'Are you a veteran?' "
                + "radio-button and select 'No' from the Mississippi National Guard radio-button");
        logStep("Fill in all required fields and Create");
        Participant participant = new Participant();
        ParticipantCreationSteps.createCompleteSSParticipant(participant, Constants.FALSE, Constants.FALSE);

        logResult("New account is created, it is possible to login to the WINGS and work");
        BaseNavigationSteps.loginParticipant();
        new ParticipantHomePage(Constants.PARTICIPANT_SS);
    }
}
