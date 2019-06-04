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
 * Open participant S-S profile, add/delete/edit all information in the military section and save it. Check, that changes are saved.
 * Created by a.vnuchko on 09.11.2016.
 */
@TestCase(id = "WINGS-10424")
public class TC_31_19_EP_Military_Edit extends TC_31_01_EP_General_Standard_View {
    private String initialDischarge = "Dishonorable";
    private String editedDischarge = "Honorable";
    private String militaryType = "Army";
    private String serviceStart = CommonFunctions.getYesterdayDate();
    private String serviceEnd = CommonFunctions.getCurrentDate();
    private String serviceEndFuture = "Present";

    public void main() {
        Participant participant = precondition();

        logStep("Go to the Military History section");
        BaseParticipantSsDetailsForm detailsPage = openParticipantDetail(participant);
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_MILITARY);

        logStep("Add information about military records and save changes");
        ParticipantSsEditForm editPage = new ParticipantSsEditForm(Constants.MILITARY);
        editPage.clickMilitaryBranch(militaryType);
        editPage.selectDischargeType(initialDischarge);
        editPage.inputServiceEnd(serviceEnd);
        editPage.clickButton(Buttons.Save);

        logResult("New information is added/deleted, all changes are saved.");
        ParticipantDetailSteps.validateMilitary(militaryType, initialDischarge + " Discharge", serviceStart, serviceEnd);

        logStep("Edit information about military records and save changes");
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_MILITARY);
        editPage.selectDischargeType(editedDischarge);
        editPage.clickButton(Buttons.Save);

        logResult("Information is edited, all changes are saved.");
        ParticipantDetailSteps.validateMilitary(militaryType, editedDischarge + " Discharge", serviceStart, serviceEnd);

        logStep("Delete information about military records and save changes");
        detailsPage.clickButton(BaseParticipantSsDetailsForm.ParticipantDetailsButtons.EDIT_MILITARY);
        editPage.inputServiceEnd(Constants.EMPTY);
        editPage.clickButton(Buttons.Save);

        logResult("Information is deleted, all changes are saved.");
        ParticipantDetailSteps.validateMilitary(militaryType, editedDischarge + " Discharge", serviceStart, serviceEndFuture);
    }
}
