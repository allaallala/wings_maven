package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant S-S profile, edit some data in the 'Accomplishments' section, but do not save it. Check, that data hasn't changed.
 * Created by a.vnuchko on 04.11.2016.
 */

@TestCase(id = "WINGS-11165")
public class TC_31_12_EP_Accomplishments_Edit_Cancel extends TC_31_01_EP_General_Standard_View {

    public void main() {
        String defaultGrade = "None Completed";
        String defaultLicense = "No";
        Participant participant = precondition();

        logStep("Click [Edit] button in the Accomplishments");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_ACCOMPLISHMENTS);

        logStep("Change some parameters");
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.ACCOMPLISHMENTS);
        editPage.editAccomplishments();

        logStep("On this screen click [Cancel] button");
        editPage.clickCancelAndConfirm();

        logResult("Changes on this page are not applied");
        ParticipantDetailSteps.validateAccomplishmentsSection(defaultGrade, defaultLicense);
    }
}
