package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation.EligibilitySSForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10503")
public class TC_01_04_Participant_Create_Cancel extends BaseWingsTest {


    public void main() {
        logStep("Login to the System with Unemployment Services System Username and Password");
        BaseNavigationSteps.loginParticipant();

        logStep("Fill in some fields");
        Participant participant = new Participant();
        ParticipantCreationForm creationForm = new ParticipantCreationForm(Constants.PARTICIPANT_SS);
        EligibilitySSForm eligibilitySSForm = new EligibilitySSForm();
        eligibilitySSForm.fillFirstPageSelfServices(participant.getSsn(), participant.getParticipantBioInfo().getDateOfBirth());

        logStep("Press Cancel Button");
        eligibilitySSForm.clickButton(Buttons.Cancel);
        eligibilitySSForm.areYouSure(Popup.Yes);

        logStep("Check, that account isn't created");
        BaseNavigationSteps.loginParticipant();
        creationForm.checkSsn();
    }
}
