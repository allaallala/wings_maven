package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.wiaTraining.WIATrainingSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10589")
public class TC_04_04_WIA_Training_Search_Course extends BaseWingsTest {


    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount());
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, false, false);
        TrainingProvider trainingProvider = new TrainingProvider();
        TrainingSteps.createTraining(trainingProvider, Roles.PCADMIN);
        String course = TrainingSteps.createWIATraining(participant, trainingProvider);

        logStep("Log in WINGS and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_WIA_TRAINING, Popup.Search);

        logStep("Select some Course->Search");
        WIATrainingSearchForm wiaTrainingSearchForm = new WIATrainingSearchForm();
        wiaTrainingSearchForm.selectCourse(course);
        wiaTrainingSearchForm.clickButton(Buttons.Search);

        logStep("Validate Course");
        assertTrue("Assert Training Course failed", wiaTrainingSearchForm.getTrainingCourseText().contains(course));

        BaseNavigationSteps.logout();
        logEnd();
    }
}
