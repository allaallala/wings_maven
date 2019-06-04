package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10672")
public class TC_07_06_Youth_Assessment_Post_Test_Without_Service extends BaseWingsTest {

    String dateAdministred = CommonFunctions.getYesterdayDate();
    String type = "ABLE";
    String score = CommonFunctions.getRandomIntegerNumber(Constants.CODE_LENGTH);
    String functionalArea = "Reading/Reading for Information";
    String assessmentProgram = "WIA";

    private static final String ERROR_MESSAGE = "This post-test cannot be created because the participant has not received any youth services";

    public void main() {

        // Creating preconditions for Youth Assessments creation
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        ParticipantCreationSteps.createParticipantDriver(participant, Constants.TRUE, Constants.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);
        Assessment assessment = new Assessment(participant, assessmentProgram, dateAdministred, type, score, functionalArea);
        Assessment postTestAssessment = new Assessment(participant, assessmentProgram, dateAdministred, type, score, functionalArea);
        postTestAssessment.setPreTest(false);
        postTestAssessment.setDateAdministered(CommonFunctions.getYesterdayDate());
        postTestAssessment.setScore(CommonFunctions.getRandomIntegerNumber(Constants.CODE_LENGTH));
        postTestAssessment.setParticipantPrePopulated(true);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Create);

        logStep("Fill information for Pre-Test assessment");
        AssessmentCreationForm creationForm = new AssessmentCreationForm();
        creationForm.fillAssessmentInformation(new User(Roles.STAFF), assessment);

        logStep("Complete Assessment creation");
        creationForm.clickButton(Buttons.Create);

        logStep("Validate Youth Assessment information");
        AssessmentDetailsForm detailsForm = new AssessmentDetailsForm();
        detailsForm.validateAssessmentInformation(assessment);

        logStep("Start creating Post-Test Assessment");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS);
        BaseWingsSteps.popClick(Popup.Create);

        logStep("Fill information for Post-Test assessment");
        creationForm = new AssessmentCreationForm();
        creationForm.fillAssessmentInformationForPostTest(new User(Roles.STAFF), postTestAssessment);

        logStep("Try to complete Post-Test Assessment creation");
        creationForm.clickButton(Buttons.Create);

        logStep("Validate that error message is displayed");
        assertTrue("Error message wasn't displayed", BaseWingsForm.BaseOtherElement.ERROR_MESSAGE.getElement().getText().contains(ERROR_MESSAGE));
    }
}
