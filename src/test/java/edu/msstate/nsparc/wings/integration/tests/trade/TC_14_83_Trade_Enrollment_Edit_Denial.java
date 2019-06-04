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


@TestCase(id = "WINGS-10844")
public class TC_14_83_Trade_Enrollment_Edit_Denial extends BaseWingsTest {

    public void main() {
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        tradeEnrollment.setEligible(Constants.FALSE);
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);

        logStep("Log in as Admin and open Trade Enrollment participantSSDetails page");
        TradeEnrollmentSteps.openTradeEnrollmentDetailPage(tradeEnrollment, Roles.ADMIN);

        logStep("Expand the Denials section");
        TradeEnrollmentDetailsForm tradeEnrollmentDetailsForm = new TradeEnrollmentDetailsForm();
        tradeEnrollmentDetailsForm.expandDenials();

        logStep("Click the Edit Denial button and edit any parameter");
        tradeEnrollmentDetailsForm.editDenial(CommonFunctions.getCurrentDate());

        logStep("Click the [Save Changes] button");
        tradeEnrollmentDetailsForm.clickButton(Buttons.Save);

        logStep("Validate that the changes are saved");
        tradeEnrollmentDetailsForm.expandDenials();
        tradeEnrollmentDetailsForm.validateDenials(true);
    }
}
