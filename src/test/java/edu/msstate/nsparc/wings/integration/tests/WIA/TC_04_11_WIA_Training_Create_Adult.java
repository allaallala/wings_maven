package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingCreateForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10599")
public class TC_04_11_WIA_Training_Create_Adult extends BaseWingsTest {

    protected String trainingType = "Other Occupational Skills Training";
    protected String dayTraining = CommonFunctions.getCurrentDate();
    protected String dayCompletion = CommonFunctions.getTomorrowDate();


    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, false, false);

        WIATrainingCreateForm wiaTrainingCreateForm = fillWiaTrainingCreateForm(participant);

        //sub-task WINGS-2736
        logStep("Checking validation for WINGS-2736");
        wiaTrainingCreateForm.checkDataValidation();
        //-----------------------------------------------------------

        logStep("Create");
        wiaTrainingCreateForm.clickButton(Buttons.Create);
        wiaTrainingCreateForm.passParticipationRecalculationPage();
        wiaTrainingCreateForm.clickButton(Buttons.Done);

        openValidateWIATraining(participant);

        BaseNavigationSteps.logout();
    }

    /**
     * Search for WIA training and validate training participantSSDetails
     * @param participant - participant
     */
    protected void openValidateWIATraining(Participant participant){
        logStep("Find new created WIA Training");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING);
        BaseWingsSteps.popClick(Popup.Search);
        WIATrainingSearchForm wiaTrainingSearchForm = new WIATrainingSearchForm();
        wiaTrainingSearchForm.performSearch(participant);
        wiaTrainingSearchForm.clickFirstParticipant();

        logStep("Validate WIA Training data");
        WIATrainingDetailsForm wiaTrainingDetailsForm = new WIATrainingDetailsForm();
        wiaTrainingDetailsForm.validateWIATrainingDetails(participant, trainingType, dayTraining, dayCompletion);
    }

    /**
     * Fill out WIA Training Creation Form
     * @param participant - participant
     * @return - WIA training creation form.
     */
    protected WIATrainingCreateForm fillWiaTrainingCreateForm(Participant participant){
        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Popup.Create);

        logStep("Select Adult Participant");
        WIATrainingCreateForm wiaTrainingCreateForm = new WIATrainingCreateForm();
        wiaTrainingCreateForm.selectParticipant(participant);

        logStep("Select WIA Enrollment");
        wiaTrainingCreateForm.selectFirstEnrollment();

        logStep("Enter valid Project code");
        wiaTrainingCreateForm.selectProjectCode("");

        logStep("Fill in all required fields");
        wiaTrainingCreateForm.fillTrainingInformation(trainingType, dayTraining, dayCompletion);
        return wiaTrainingCreateForm;
    }
}
