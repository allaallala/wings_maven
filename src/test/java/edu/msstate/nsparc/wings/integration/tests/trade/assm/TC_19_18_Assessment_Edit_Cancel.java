package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentEditForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create an assessment, edit some information, but do not save it. Check, that changes are not applied.
 * Created by a.vnuchko on 20.06.2016.
 */

@TestCase(id = "WINGS-10963")
public class TC_19_18_Assessment_Edit_Cancel extends TC_19_01_Assessment_Create_Cancel {

    public void main() {
        info("Generate some test data");
        String faValue = "Writing";
        String tpValue = "SPL";

        info("Precondition: Create some Assessment");
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);
        Assessment assessment = new Assessment(tradeEnrollment.getParticipant(), Constants.TRADE);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, assessment);

        AssessmentEditForm editPage = AssessmentSteps.openEditAssessment(assessment, Constants.RANDOM_ONE);

        logStep("Edit any parameters");
        editPage.editAssessment(faValue,tpValue);

        logStep(" Click the [Cancel] button");
        editPage.clickButton(Buttons.Cancel);
        editPage.areYouSure(Popup.Yes);

        logResult("The Assessment Detail Screen is shown, the changes are not saved");
        AssessmentDetailsForm detailsPage = new AssessmentDetailsForm();
        detailsPage.validateAssessmentInformation(assessment);
    }
}
