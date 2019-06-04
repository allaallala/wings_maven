package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10845")
public class TC_14_84_Trade_Enrollment_Cancel_Edit_Denial extends BaseWingsTest {

    public void main() {

        TradeEnrollment tradeEnrollment = TradeEnrollmentObjects.getCreatedDeniedTradeEnrollment();

        logStep("Log in as Admin and open Trade Enrollment participantSSDetails page");
        TradeEnrollmentSteps.openTradeEnrollmentDetailPage(tradeEnrollment, Roles.ADMIN);

        logStep("Expand the Denials section");
        TradeEnrollmentDetailsForm tradeEnrollmentDetailsForm = new TradeEnrollmentDetailsForm();
        tradeEnrollmentDetailsForm.expandDenials();

        logStep("Click the Edit Denial button and edit any parameter");
        tradeEnrollmentDetailsForm.editDenial(CommonFunctions.getCurrentDate());

        logStep("Click the [Cancel] button");
        tradeEnrollmentDetailsForm.clickButton(Buttons.Cancel);
        tradeEnrollmentDetailsForm.areYouSure(Popup.Yes);

        logStep("Expand the Denials section");
        tradeEnrollmentDetailsForm.expandDenials();

        logStep("Validate that the changes are not saved");
        tradeEnrollmentDetailsForm.validateDenials(Constants.FALSE);
    }
}
