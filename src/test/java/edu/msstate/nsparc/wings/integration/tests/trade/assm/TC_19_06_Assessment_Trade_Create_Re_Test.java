package edu.msstate.nsparc.wings.integration.tests.trade.assm;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.assessment.AssessmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Assessment;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.AssessmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import org.testng.Assert;


/**
 * Check, that is possible to create Assesment with Re Test.
 * Created by a.korotkin
 * modified by a.vnuchko
 */

@TestCase(id = "WINGS-10953")
public class TC_19_06_Assessment_Trade_Create_Re_Test extends TC_19_01_Assessment_Create_Cancel {

    public void main() {

        info("Precondition: Create Assessments with Re Test");
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);
        Assessment preTestAssessment = new Assessment(tradeEnrollment.getParticipant(), Constants.TRADE);
        User staff = new User(Roles.STAFF);
        AssessmentSteps.createAssessment(staff, preTestAssessment);

        Assessment postTestAssessment = new Assessment(tradeEnrollment.getParticipant(), Constants.TRADE);
        postTestAssessment.setPreTest(Constants.FALSE);

        AssessmentSteps.openFillCreationForm(postTestAssessment);
        AssessmentCreationForm creationPage = new AssessmentCreationForm();

        logResult("The  Assessment Creation screen is shown.Pre or Post switch available for Participant with Pre Test information on step 5a. If Post Test is selected, "
                + "Pre Test information should be selected form drop-down list. ");
        Assert.assertTrue(creationPage.getComboboxFirstTest().contains(preTestAssessment.getFunctionalArea()));

        logStep("Click the [Create] button");
        creationPage.clickButton(Buttons.Create);

        logResult(" A new Assessment was created and contains the same data you have entered");
        AssessmentDetailsForm assessmentDetailsForm = new AssessmentDetailsForm();
        assessmentDetailsForm.validateAssessmentInformation(postTestAssessment);

    }
}
