package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10848")
public class TC_14_87_Trade_Enrollment_Edit_Appeal_Denial extends BaseWingsTest {

    private static final String RANDOM_APPEAL_DOCKET_NUMBER = CommonFunctions.getRandomIntegerNumber(9);

    public void main() {

        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        tradeEnrollment.setEligible(Constants.FALSE);
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);
        TradeEnrollmentSteps.appealDenial(tradeEnrollment);

        logStep("Log in as Admin and open Trade Enrollment participantSSDetails page");
        TradeEnrollmentSteps.openTradeEnrollmentDetailPage(tradeEnrollment, Roles.ADMIN);

        logStep("Expand the Denials section");
        TradeEnrollmentDetailsForm tradeEnrollmentDetailsForm = new TradeEnrollmentDetailsForm();
        tradeEnrollmentDetailsForm.expandDenials();

        logStep("Click the [Edit] button next to any Denial Appeal and edit any parameter");
        tradeEnrollmentDetailsForm.inputAppealDocketNumber(RANDOM_APPEAL_DOCKET_NUMBER);

        logStep("Click the [Save Changes] button");
        tradeEnrollmentDetailsForm.clickButton(Buttons.Save);

        logStep("Validate that the changes are saved");
        tradeEnrollmentDetailsForm.expandDenials();
        assertTrue("Assert Appeal Docket Number failed!", tradeEnrollmentDetailsForm.getAppealDocketNumberText().contains(RANDOM_APPEAL_DOCKET_NUMBER));
    }
}
