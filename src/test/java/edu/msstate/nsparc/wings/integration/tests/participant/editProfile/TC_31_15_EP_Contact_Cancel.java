package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant S-S profile, edit some information in the contact information section but do not save it,
 * Created by a.vnuchko on 09.11.2016.
 */
@TestCase(id = "WINGS-11167")
public class TC_31_15_EP_Contact_Cancel extends TC_31_01_EP_General_Standard_View {

    public void main() {
        Participant participant = precondition();

        logStep("Click [Edit] button in the Contact Information");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_CONTACTS);

        logStep("Change some parameters");
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.CONTACT);
        editPage.editResidenceAddress(participant);

        logStep("On this screen click [Cancel] button");
        editPage.clickCancelAndConfirm();

        logResult("Changes on this page are not applied");
        ParticipantDetailSteps.validateContactInformationSection(participant, Constants.FALSE);

    }
}
