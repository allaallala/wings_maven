package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingCreateForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10461")
public class TC_04_10_WIA_Training_Create extends BaseWingsTest {

    private String trainingType = "Other Occupational Skills Training";
    private String dayTraining = CommonFunctions.getCurrentDate();
    private String dayCompletion = CommonFunctions.getTomorrowDate();


    public void main() {
        TC_01_10_WIA_Enrollment_Create createWIAEnrollment = new TC_01_10_WIA_Enrollment_Create();
        Participant participant = createWIAEnrollment.createWIAE();

        logStep("Open 'Participant'->'WIA training'");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING);

        logStep("Choose 'Create' in the pop-up");
        BaseWingsSteps.popClick(Popup.Create);

        logStep("Select pc code");
        WIATrainingCreateForm wiaTrainingCreateForm = new WIATrainingCreateForm();
        wiaTrainingCreateForm.selectProjectCode("");

        logStep("Fill out WIA training creation form");
        wiaTrainingCreateForm.fillTrainingInformation(trainingType, dayTraining, dayCompletion);

        logStep("Click [Create] button");
        wiaTrainingCreateForm.clickButton(Buttons.Create);

        logStep("Confirm creation");
        wiaTrainingCreateForm.passParticipationRecalculationPage();

        logStep("Check WIA training participantSSDetails");
        WIATrainingDetailsForm wiaTrainingDetailsForm = new WIATrainingDetailsForm();
        wiaTrainingDetailsForm.validateWIATrainingDetails(participant, trainingType, dayTraining, dayCompletion);
    }
}
