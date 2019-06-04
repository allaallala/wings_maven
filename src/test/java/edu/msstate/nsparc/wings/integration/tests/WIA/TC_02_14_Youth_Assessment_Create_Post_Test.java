package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10549")
public class TC_02_14_Youth_Assessment_Create_Post_Test extends BaseWingsTest {

    private static final String SERVICE_NAME = CommonFunctions.getRandomLiteralCode(10);


    public void main() {

        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        ServiceSteps.createService(Roles.ADMIN, SERVICE_NAME, true, true);
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);
        Assessment preTestAssessment = new Assessment(participant, "WIA");
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, preTestAssessment);
        ServiceSteps.createParticipantServiceEnrollment(staff, participant, SERVICE_NAME, Constants.COMPLETED,  Constants.EMPTY);

        Assessment postTestAssessment = new Assessment(participant, "WIA");
        postTestAssessment.setPreTest(false);

        logStep("Log in WINGS and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_ASSESSMENTS, Popup.Create);

        logStep("Create youth assessment with post test");
        AssessmentCreationForm creationForm = new AssessmentCreationForm();
        creationForm.fillAssessmentInformation(new User(Roles.STAFF), postTestAssessment);
        creationForm.clickButton(Buttons.Create);

        logStep("Validate assessment information");
        AssessmentDetailsForm assessmentDetailsForm = new AssessmentDetailsForm();
        assessmentDetailsForm.validateAssessmentInformation(postTestAssessment);
    }
}
