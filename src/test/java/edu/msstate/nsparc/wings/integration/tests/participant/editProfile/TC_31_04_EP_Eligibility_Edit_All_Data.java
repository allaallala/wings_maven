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
 * Open participant S-S profile, edit all data in the eligibility information section and check, that all data is saved.
 * Created by a.vnuchko on 31.10.2016.
 */

@TestCase(id = "WINGS-10417")
public class TC_31_04_EP_Eligibility_Edit_All_Data extends TC_31_01_EP_General_Standard_View {
    private String newSsn = CommonFunctions.getRandomSSN();
    private String newBirthDate = CommonFunctions.getDaysAndYearsAgoDate(0, 50);

    public void main() {
        Participant participant = precondition();

        logStep("Click [Edit] button in the Eligibility Information");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_ELIGIBILITY);

        logStep("Edit all data");
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.ELIGIBILITY);
        editPage.editEligibility(participant, newSsn, newBirthDate);

        logStep("Save changes");
        editPage.clickButton(Buttons.Save);

        logResult("All changes are saved");
        ParticipantDetailSteps.validateEligibilitySection(participant);
    }
}
