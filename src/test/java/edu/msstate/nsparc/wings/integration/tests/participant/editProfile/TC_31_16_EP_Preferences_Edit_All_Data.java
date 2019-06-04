package edu.msstate.nsparc.wings.integration.tests.participant.editProfile;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.ParticipantSsEditForm;
import edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails.BaseParticipantSsDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantDetailSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Open participant S-S profile, edit all information in the employment preferences and save it. Check, that changes are saved.
 * Created by a.vnuchko on 09.11.2016.
 */

@TestCase(id = "WINGS-10422")
public class TC_31_16_EP_Preferences_Edit_All_Data extends TC_31_01_EP_General_Standard_View {
    private String hoursWeek = "50";
    private String desiredSalary = "$48.00 hourly ($100,000 annually) or more";
    private String distanceRelocate = "50";
    private String distanceCommute = "50";

    public void main() {
        Participant participant = precondition();

        logStep("Click [Edit] button in the Employment Preferences");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_PREFERENCES);

        logStep("Edit all data");
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.PREFERENCES);
        editPage.editEmploymentPreferences(hoursWeek, desiredSalary, distanceRelocate, distanceCommute);

        logStep("Save changes");
        editPage.clickButton(Buttons.Save);

        logResult("All changes are saved");
        ParticipantDetailSteps.validatePreferences(Constants.TRUE, hoursWeek, desiredSalary, distanceRelocate, distanceCommute);
    }
}
