package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10840")
public class TC_14_75_Trade_Enrollment_Save_Date_Signed extends BaseWingsTest {

    public void main() {
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);

        logStep("Log in as Admin and open Trade Enrollment participantSSDetails page");
        TradeEnrollmentSteps.openTradeEnrollmentDetailPage(tradeEnrollment, Roles.ADMIN);

        logStep("Expand Forms section");
        TradeEnrollmentDetailsForm detailsForm = new TradeEnrollmentDetailsForm();
        detailsForm.expandFormSection();

        logStep("Select any Date Signed and Save");
        detailsForm.inputDataSigned(tradeEnrollment.getApplicationDate());
        detailsForm.areYouSure(Popup.Yes);

        logStep("Validate that Date Signed was saved: expand the Forms - check the Date");
        detailsForm.expandFormSection();
        CustomAssertion.softAssertContains(detailsForm.getSignedSaveText(), tradeEnrollment.getApplicationDate(), "Assert Date Signed failed!");
    }
}
