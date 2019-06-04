package edu.msstate.nsparc.wings.integration.tests.WIA;

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


@TestCase(id = "WINGS-10602")
public class TC_04_13_WIA_Training_Create_Training_Type extends TC_04_11_WIA_Training_Create_Adult {
    private String[] trainingTypes = {"Please Select",
                                      "Skill Upgrading & Retraining",
                                      "Other Occupational Skills Training"
    };


    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, false, true);

        logStep("Log in WINGS and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Popup.Create);

        logStep("Select Participant");
        WIATrainingCreateForm wiaTrainingCreateForm = new WIATrainingCreateForm();
        wiaTrainingCreateForm.clickParticipantLookup();
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndSelect(participant);

        logStep("Select WIA Enrollment");
        wiaTrainingCreateForm.selectFirstEnrollment();

        logStep("Enter valid Project code");
        wiaTrainingCreateForm.selectProjectCode("");

        logStep("Select different types of Training Type");
        for (String trainingType : trainingTypes) {
            wiaTrainingCreateForm.selectTrainingType(trainingType);
        }

        logStep("Fill in all required fields");
        wiaTrainingCreateForm.fillTrainingInformation(trainingType, dayTraining, dayCompletion);

        logStep("Create");
        wiaTrainingCreateForm.clickButton(Buttons.Create);
        wiaTrainingCreateForm.passParticipationRecalculationPage();
        wiaTrainingCreateForm.clickButton(Buttons.Done);

        openValidateWIATraining(participant);

        BaseNavigationSteps.logout();
    }
}
