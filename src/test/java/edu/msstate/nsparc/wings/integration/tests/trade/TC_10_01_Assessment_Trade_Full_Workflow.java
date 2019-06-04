package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentEditForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.tests.WIA.TC_02_21_Youth_Assessment_View;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10453")
public class TC_10_01_Assessment_Trade_Full_Workflow extends TC_02_21_Youth_Assessment_View {

    private String program = "Trade";

    public void main() {
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);
        Assessment assessment = new Assessment(tradeEnrollment.getParticipant(), program);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, assessment);

        AssessmentDetailsForm detailsForm = openAssessmentDetails(assessment);

        logStep("Open Edit form");
        detailsForm.clickButton(Buttons.Edit);

        logStep("Edit a few parameters");
        assessment.setScore(CommonFunctions.getRandomIntegerNumber(Constants.CODE_LENGTH));
        AssessmentEditForm assessmentEditForm = new AssessmentEditForm();
        assessmentEditForm.typeScore(assessment.getScore());

        logStep("Save Changes");
        assessmentEditForm.saveChanges();

        logStep("Validate that changes were saved");
        detailsForm.validateAssessmentInformation(assessment);
    }
}

