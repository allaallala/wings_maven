package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant S-S profile, edit some data in the eligibility information section but do not save it. Check, that data hasn't changed.
 * Created by a.vnuchko on 31.10.2016.
 */

@TestCase(id = "WINGS-11161")
public class TC_31_05_EP_Eligibility_Edit_Cancel extends TC_31_01_EP_General_Standard_View {
    public void main() {
        Participant participant = precondition();

        logStep("Click [Edit] button in the Eligibility Information");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_ELIGIBILITY);

        logStep("Change some parameters");
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.ELIGIBILITY);
        editPage.editDateBirth(Constants.EMPTY);

        logStep("On this screen click [Cancel] button");
        editPage.clickCancelAndConfirm();

        logResult("Changes on this page are not applied");
        ParticipantDetailSteps.validateEligibilitySection(participant);
    }
}
