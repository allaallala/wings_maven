package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;
import org.testng.Assert;


@TestCase(id = "WINGS-10674")
public class TC_07_08_Youth_Assessment_Check_Pre_Test_Dates extends BaseWingsTest {

    private String dateAdministred = CommonFunctions.getCurrentDate();
    private String type = "ABLE";
    private String score = CommonFunctions.getRandomIntegerNumber(Constants.CODE_LENGTH);
    private String functionalArea = "Reading/Reading for Information";
    private String program = "WIA";
    private String creationDate = "";
    private String enrollmentDate = "2011-01-01";

    private static final String SERVICE_NAME = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    private static final String ERROR_MESSAGE = "Pre-Test cannot be more than 60 days after the first WIA Youth Service";

    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        ServiceSteps.createService(Roles.ADMIN, SERVICE_NAME, true, true);
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);
        TrainingProvider trainingProvider = new TrainingProvider();
        TrainingSteps.createTraining(trainingProvider, Roles.PCADMIN);
        TrainingSteps.createWIATraining(participant, trainingProvider);
        User staff = new User(Roles.STAFF);
        ServiceSteps.createParticipantServiceEnrollment(staff, participant, SERVICE_NAME, Constants.COMPLETED, creationDate);
        AdvancedSqlFunctions.changeServiceEnrollmentDate(participant, enrollmentDate);

        Assessment assessment = new Assessment(participant, program, dateAdministred, type, score, functionalArea);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Create);

        logStep("Select a participant");
        AssessmentCreationForm creationForm = new AssessmentCreationForm();
        creationForm.fillAssessmentInformation(new User(Roles.STAFF), assessment);

        logStep("Click on Create button");
        creationForm.clickButton(Buttons.Create);

        logStep("Validate that error message is displayed");
        Assert.assertTrue(creationForm.getErrorDataText().contains(ERROR_MESSAGE));
    }
}
