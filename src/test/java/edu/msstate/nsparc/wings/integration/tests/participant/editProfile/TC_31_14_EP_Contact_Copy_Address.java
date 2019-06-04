package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.Address;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant S-S profile, edit information about residence address in the contact section, copy and save it. Check, that information has successfully changed.
 * Created by a.vnuchko on 09.11.2016.
 */

@TestCase(id = "WINGS-11166")
public class TC_31_14_EP_Contact_Copy_Address extends TC_31_01_EP_General_Standard_View {

    public void main() {
        Participant participant = precondition();

        logStep("Click [Edit] button in the Contact Information ");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_CONTACTS);

        logStep("Edit data in the Recidence Address");
        participant.setNewAddress(new Address());
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.CONTACT);
        editPage.editResidenceAddress(participant);

        logStep("Click the Copy button");
        editPage.clickCopyButton();

        logStep("Save changes");
        editPage.clickButton(Buttons.Save);

        logResult("The values from the Recidence Address are copied to the Mailing Address. All changes are saved");
        ParticipantDetailSteps.validateResidenceMailingAddress(participant);
    }
}
