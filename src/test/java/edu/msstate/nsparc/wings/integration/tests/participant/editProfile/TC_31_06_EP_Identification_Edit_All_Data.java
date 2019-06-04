package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Open participant S-S profile, edit all data in the identification information section, save it. Check, that data has changed.
 * Created by a.vnuchko on 31.10.2016.
 */

@TestCase(id = "WINGS-10418")
public class TC_31_06_EP_Identification_Edit_All_Data extends TC_31_01_EP_General_Standard_View {

    public void main() {
        String firstName = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
        String lastName = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);
        Participant participant = precondition();

        logStep("Click [Edit] button in the Identification Information");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_IDENTIFICATION);

        logStep("Edit all data");
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.IDENTIFICATION);
        editPage.editAllIdentification(participant, firstName, lastName);

        logStep("Save changes");
        editPage.clickButton(Buttons.Save);

        logResult("All changes are saved");
        ParticipantDetailSteps.validateIdentificationSection(participant, Constants.FALSE);
    }

}
