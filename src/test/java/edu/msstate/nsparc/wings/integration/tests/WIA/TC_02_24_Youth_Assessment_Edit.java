package edu.msstate.nsparc.wings.integration.tests.WIA;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentEditForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.WiaEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10553")
public class TC_02_24_Youth_Assessment_Edit extends TC_02_21_Youth_Assessment_View {


    public void main() {
        Participant participant = new Participant(AccountUtils.getParticipantAccount(), true);
        ParticipantCreationSteps.createParticipantDriver(participant, Boolean.TRUE, Boolean.FALSE);
        WiaEnrollmentSteps.createWIAEnrollment(new User(Roles.STAFF), participant, Boolean.TRUE, Boolean.FALSE);
        Assessment assessment = new Assessment(participant, "WIA");
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, assessment);

        AssessmentDetailsForm detailsForm = openAssessmentDetails(assessment);

        logStep("Open assessment edit");
        detailsForm.clickButton(Buttons.Edit);

        logStep("Change assessment info");
        // Changing assessment information
        assessment.setDateAdministered(CommonFunctions.getDaysAgoDate(Constants.SECOND_TEST_DATE));
        assessment.setScore(CommonFunctions.getRandomIntegerNumber(Constants.CODE_LENGTH));
        AssessmentEditForm youthAssessmentEditForm = new AssessmentEditForm();
        youthAssessmentEditForm.inputDateAdministered(assessment.getDateAdministered());
        youthAssessmentEditForm.typeScore(assessment.getScore());
        youthAssessmentEditForm.saveChanges();

        logStep("Validate assessment info");
        detailsForm.validateAssessmentInformation(assessment);
    }
}
