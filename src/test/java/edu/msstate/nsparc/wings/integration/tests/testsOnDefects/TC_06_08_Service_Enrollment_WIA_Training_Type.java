package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.participantEnrollment.ParticipantEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingEditForm;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10656")
public class TC_06_08_Service_Enrollment_WIA_Training_Type extends BaseWingsTest {

    String trainingType = "Other Occupational Skills Training";
    String dayTraining = CommonFunctions.getCurrentDate();
    String dayCompletion = CommonFunctions.getTomorrowDate();

    private static final String NEW_TRAINING_TYPE = "Skill Upgrading & Retraining";
    private static final String NEW_TRAINING_TYPE_AS_SERVICE_ENROLLMENT = "Skill Upgrading and Retraining";


    //Bug WINGS-3045 , sub-task WINGS-3046
    public void main() {

        info("Creating WIA Training");
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), false);
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, false, false);
        TrainingSteps.createWIATraining(participant, trainingType, dayTraining, dayCompletion);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT, Popup.Search);

        logStep("Perform search");
        ParticipantEnrollmentSearchForm enrollmentSearchForm = new ParticipantEnrollmentSearchForm();
        enrollmentSearchForm.performSearch(participant, trainingType);

        logStep("Check that Participant was found");
        assertTrue("Participant wasn't found", enrollmentSearchForm.checkParticipantName());

        logStep("Navigate to WIA Training search form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for created WIA Training");
        WIATrainingSearchForm wiaTrainingSearchForm = new WIATrainingSearchForm();
        wiaTrainingSearchForm.performSearch(participant);

        logStep("Open WIA Training");
        wiaTrainingSearchForm.clickFirstParticipant();
        WIATrainingDetailsForm detailsForm = new WIATrainingDetailsForm();

        logStep("Change WIA Training type");
        detailsForm.clickButton(Buttons.Edit);
        WIATrainingEditForm editForm = new WIATrainingEditForm();
        editForm.selectTrainingType(NEW_TRAINING_TYPE);

        logStep("Save Changes");
        editForm.clickButton(Buttons.Save);

        logStep("Go back to Participant Service Enrollment search form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_PARTICIPANT_SERVICE_ENROLLMENT);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Perform search with new Training Type");
        enrollmentSearchForm.performSearch(participant, NEW_TRAINING_TYPE_AS_SERVICE_ENROLLMENT);

        logStep("Check that Participant was found");
        assertTrue("Participant wasn't found", enrollmentSearchForm.checkParticipantName());

        logStep("Clear Search form");
        enrollmentSearchForm.clickButton(Buttons.Clear);

        logStep("Perform search with old Training Type");
        enrollmentSearchForm.performSearch(participant, trainingType);

        logStep("Check that Participant wasn't found");
        assertFalse("Participant was found", enrollmentSearchForm.checkParticipantName());
    }
}
