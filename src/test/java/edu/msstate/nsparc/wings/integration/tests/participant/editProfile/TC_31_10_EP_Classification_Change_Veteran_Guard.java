package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant S-S profile, try to edit veteran status. Check, that correspondent message is displayed.
 * Created by a.vnuchko on 03.11.2016.
 */

@TestCase(id = "WINGS-11164")
public class TC_31_10_EP_Classification_Change_Veteran_Guard extends TC_31_01_EP_General_Standard_View {
    private String expectedVeteranText = "Reported during initial registration. To change your veteran status, please call or visit your local WIN Service Center.";
    private String expectedGuardText = "Reported during initial registration. To change your national guard information, please call or visit your local WIN Service Center.";

    public void main() {
        Participant part = precondition();

        logStep("Click [Edit] button in the Classification Information ");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(part);
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_CLASSIFICATION);

        logStep("Try to edit Veteran Status, Try to edit Mississippi National Guard Status");
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.CLASSIFICATION);

        logResult("The field is 'display-only'. Message asks that participant call local WIN Job Center to change self-reported status.");
        editPage.checkVeteranGuardMessageText(expectedVeteranText, expectedGuardText);
    }
}
