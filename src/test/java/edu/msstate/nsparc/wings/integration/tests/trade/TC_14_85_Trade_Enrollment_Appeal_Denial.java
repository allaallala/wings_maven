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

@TestCase(id = "WINGS-10846")
public class TC_14_85_Trade_Enrollment_Appeal_Denial extends BaseWingsTest {

    public void main() {
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        tradeEnrollment.setEligible(Constants.FALSE);
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);

        logStep("Log in as Admin and open Trade Enrollment participantSSDetails page");
        TradeEnrollmentSteps.openTradeEnrollmentDetailPage(tradeEnrollment, Roles.ADMIN);

        logStep("Expand the Denials section");
        TradeEnrollmentDetailsForm tradeEnrollmentDetailsForm = new TradeEnrollmentDetailsForm();
        tradeEnrollmentDetailsForm.expandDenials();

        logStep("Click the [Appeal] button next to any Denial and fill out all the required fields with valid data");
        tradeEnrollmentDetailsForm.inputAppealDate(CommonFunctions.getCurrentDate());

        logStep("Click the [Save Changes] button");
        tradeEnrollmentDetailsForm.clickButton(Buttons.Save);

        logStep("Validate that the Appeal changes are saved");
        tradeEnrollmentDetailsForm.expandDenials();
        assertTrue("Assert Appeal Date failed!",tradeEnrollmentDetailsForm.getAppealDateText().contains(CommonFunctions.getCurrentDate()));
    }
}
