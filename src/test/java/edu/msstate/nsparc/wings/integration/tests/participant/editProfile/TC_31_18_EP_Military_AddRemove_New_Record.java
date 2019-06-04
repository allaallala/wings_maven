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
 * Open participant S-S profile, edit all information in the military section and save it. Check, that changes are saved.
 * Created by a.vnuchko on 09.11.2016.
 */
@TestCase(id = "WINGS-10423")
public class TC_31_18_EP_Military_AddRemove_New_Record extends TC_31_01_EP_General_Standard_View {

    public void main() {
        String militaryBranch = "Army";
        String dischargeType = "Honorable";
        String serviceDate = CommonFunctions.getCurrentDate();
        String nextDate = CommonFunctions.getDaysNextDate(CommonFunctions.getCurrentDate(),1);
        Participant participant = precondition();

        logStep("Go to the Military History section");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        info("If we have an existing military record, it should be deleted");
        detailsPage.removeMilitaryRecordPresent();

        logStep("Add new one");
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.ADD_MILITARY);
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.MILITARY);
        editPage.addMilitaryRecord(militaryBranch, dischargeType, serviceDate,
                nextDate);

        logStep("Save changes");
        editPage.clickButton(Buttons.Save);

        logResult("All changes are saved");
        ParticipantDetailSteps.validateMilitary(militaryBranch, dischargeType + " Discharge", serviceDate, nextDate);
    }
}
