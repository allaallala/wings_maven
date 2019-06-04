package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant S-S profile, edit some data in the identification information section but do not save it. Check, that data hasn't changed.
 * Created by a.vnuchko on 31.10.2016.
 */

@TestCase(id = "WINGS-11162")
public class TC_31_07_EP_Identification_Edit_Cancel extends TC_31_01_EP_General_Standard_View {

    public void main() {
        Participant participant = precondition();

        logStep("Click [Edit] button in the Identification Information");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_IDENTIFICATION);

        logStep("Change some parameters");
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.IDENTIFICATION);
        editPage.inputFirstLastName(Constants.EMPTY, Constants.EMPTY);

        logStep("On this screen click [Cancel] button");
        editPage.clickCancelAndConfirm();

        logResult("Changes on this page are not applied");
        ParticipantDetailSteps.validateIdentificationSection(participant, Constants.TRUE);
    }
}
