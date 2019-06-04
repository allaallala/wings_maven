package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant S-S profile, edit some data in the classification information section, but don't save it. Check, that data hasn't changed.
 * Created by a.vnuchko on 03.11.2016.
 */

@TestCase(id = "WINGS-11163")
public class TC_31_09_EP_Classification_Edit_Cancel extends TC_31_01_EP_General_Standard_View {
    private String defaultEmploymentStatus = "Not Employed";

    public void main() {
        Participant participant = precondition();

        logStep("Click [Edit] button in the Classification Information ");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_CLASSIFICATION);

        logStep("Change some parameters");
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.CLASSIFICATION);
        editPage.clickEmployment();
        editPage.clickCancelAndConfirm();

        logResult("On this screen click [Cancel] button");
        ParticipantDetailSteps.validateClassificationSection(Constants.NO_ANSWER, defaultEmploymentStatus, Constants.EMPTY);

    }

}
