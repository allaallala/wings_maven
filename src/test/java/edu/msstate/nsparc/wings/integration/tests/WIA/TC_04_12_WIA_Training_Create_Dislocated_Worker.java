package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingCreateForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10601")
public class TC_04_12_WIA_Training_Create_Dislocated_Worker extends TC_04_11_WIA_Training_Create_Adult {

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, false, true);

        WIATrainingCreateForm wiaTrainingCreateForm = repeatedSteps(participant);

        logStep("Enter valid Project code");
        wiaTrainingCreateForm.selectProjectCode("");

        logStep("Fill in all required fields");
        wiaTrainingCreateForm.fillTrainingInformation(trainingType, dayTraining, dayCompletion);

        logStep("Create");
        wiaTrainingCreateForm.clickButton(Buttons.Create);
        wiaTrainingCreateForm.passParticipationRecalculationPage();
        wiaTrainingCreateForm.clickButton(Buttons.Done);

        openValidateWIATraining(participant);

        BaseNavigationSteps.logout();
    }

    /**
     * Open WIA training create form and select first WIA Enrollment
     */
    protected WIATrainingCreateForm repeatedSteps(Participant participant) {
        logStep("Log in WINGS and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Popup.Create);

        logStep("Select Dislocated worker Participant");
        WIATrainingCreateForm wiaTrainingCreateForm = new WIATrainingCreateForm();
        wiaTrainingCreateForm.clickParticipantLookup();
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndSelect(participant);

        logStep("Select WIA Enrollment");
        wiaTrainingCreateForm.selectFirstEnrollment();
        return wiaTrainingCreateForm;
    }
}
