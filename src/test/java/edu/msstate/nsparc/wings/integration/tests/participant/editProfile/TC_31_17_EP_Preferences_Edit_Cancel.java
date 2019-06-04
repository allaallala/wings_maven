package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant S-S profile, edit some information in the employment preferences, but do not save it. Check, that changes are not applied.
 * Created by a.vnuchko on 09.11.2016.
 */

@TestCase(id = "WINGS-11168")
public class TC_31_17_EP_Preferences_Edit_Cancel extends TC_31_01_EP_General_Standard_View {
    private String defaultSalary = "$7.25 hourly ($15,000 annually) or more";

    public void main() {
        Participant participant = precondition();

        logStep("Click [Edit] button in the Employment Preferences");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_PREFERENCES);

        logStep("Change some parameters");
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.PREFERENCES);
        editPage.editEmploymentPreferences(Constants.EMPTY, defaultSalary, Constants.EMPTY, Constants.EMPTY);

        logStep("On this screen click [Cancel] button");
        editPage.clickCancelAndConfirm();

        logResult("Changes on this page are not applied");
        ParticipantDetailSteps.validatePreferences(Constants.FALSE, Constants.EMPTY, defaultSalary, Constants.EMPTY, Constants.EMPTY);
        ParticipantDetailSteps.preferencesAreNotPresent();
    }
}
