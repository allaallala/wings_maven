package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create First-test assessment (which has one or more Re-Tests/Post-tests based on it), open its detailed page and check fields, which can be edited.
 * Created by a.vnuchko on 21.09.2015.
 */

@TestCase(id = "WINGS-10965")
public class TC_19_20_Assessment_Edit_FT_OneMore_RePostTest extends TC_19_01_Assessment_Create_Cancel {

    public void main() {
        info("Precondition: Create First-Test assessment, which has one/more Re-Tests/Post-Tests based on it");
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);
        Assessment firstTestAssessment = new Assessment(tradeEnrollment.getParticipant(), Constants.TRADE);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, firstTestAssessment);
        Assessment reTestAssessment = new Assessment(tradeEnrollment.getParticipant(), Constants.TRADE);
        reTestAssessment.setPreTest(Constants.FALSE);
        AssessmentSteps.createAssessment(staff, reTestAssessment);

        AssessmentSteps.openEditAssessment(reTestAssessment, Constants.RANDOM_ONE);

        logResult("The  Assessment Edit  screen is shown. User is NOT able to edit 'Functional Area'; 'Type'; 'Category' of this First-Test");
        AssessmentDetailsForm detailsPage = new AssessmentDetailsForm();
        detailsPage.elementsNotPresent();
    }
}
