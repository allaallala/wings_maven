package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10847")
public class TC_14_86_Trade_Enrollment_Cancel_Appeal_Denial extends BaseWingsTest {

    public void main() {

        TradeEnrollment tradeEnrollment = TradeEnrollmentObjects.getCreatedDeniedTradeEnrollment();

        logStep("Log in as Admin and open Trade Enrollment participantSSDetails page");
        TradeEnrollmentSteps.openTradeEnrollmentDetailPage(tradeEnrollment, Roles.ADMIN);

        logStep("Expand the Denials section");
        TradeEnrollmentDetailsForm tradeEnrollmentDetailsForm = new TradeEnrollmentDetailsForm();
        tradeEnrollmentDetailsForm.expandDenials();

        logStep("Click the [Appeal] button next to any Denial and fill out some fields");
        tradeEnrollmentDetailsForm.inputAppealDate(CommonFunctions.getCurrentDate());

        logStep("Click the [Cancel] button");
        tradeEnrollmentDetailsForm.clickButton(Buttons.Cancel);
        tradeEnrollmentDetailsForm.areYouSure(Popup.Yes);

        logStep("Expand the Denials section");
        tradeEnrollmentDetailsForm.expandDenials();

        logStep("Validate that the chosen Denial isn't appealed");
        tradeEnrollmentDetailsForm.checkAppealDateNotPresent();
    }
}
