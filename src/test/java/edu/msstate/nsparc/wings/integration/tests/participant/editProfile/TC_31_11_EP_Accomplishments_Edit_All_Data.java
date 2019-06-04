package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant S-S profile, edit all data (that can be edited) in the 'Accomplishments' section, save it. Check, that data has changed.
 * Created by a.vnuchko on 04.11.2016.
 */

@TestCase(id = "WINGS-10420")
public class TC_31_11_EP_Accomplishments_Edit_All_Data extends TC_31_01_EP_General_Standard_View {
    public void main() {
        Participant participant = precondition();

        logStep("Click [Edit] button in the Accomplishments");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_ACCOMPLISHMENTS);

        logStep("Edit all data");
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.ACCOMPLISHMENTS);
        String[] expectedParameters = editPage.editAccomplishments();

        logStep("Save changes");
        editPage.clickButton(Buttons.Save);

        logResult("All changes are saved");
        ParticipantDetailSteps.validateAccomplishmentsSection(expectedParameters[0], expectedParameters[1]);

    }

}
