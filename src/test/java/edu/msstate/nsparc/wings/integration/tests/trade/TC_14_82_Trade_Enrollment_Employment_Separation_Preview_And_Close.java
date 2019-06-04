package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.AssertJUnit.assertFalse;

@TestCase(id = "WINGS-10843")
public class TC_14_82_Trade_Enrollment_Employment_Separation_Preview_And_Close extends BaseWingsTest {

    public void main() {

        TradeEnrollment tradeEnrollment = TradeEnrollmentObjects.getCreatedTradeEnrollment();

        logStep("Log in as Admin and open Trade Enrollment participantSSDetails page");
        TradeEnrollmentSteps.openTradeEnrollmentDetailPage(tradeEnrollment, Roles.ADMIN);

        logStep("Expand the Employment at Separation section");
        TradeEnrollmentDetailsForm tradeEnrollmentDetailsForm = new TradeEnrollmentDetailsForm();
        tradeEnrollmentDetailsForm.expandEmploymentSeparation();

        logStep("Click the magnifying glass button to open Employment at Separation preview page");
        tradeEnrollmentDetailsForm.click(BaseWingsForm.BaseButton.OPEN_PREVIEW_PAGE);

        logStep("Verify that window with parameters of Employment at Separation is displayed");
        tradeEnrollmentDetailsForm.checkPreviewEmploymentSeparation();

        logStep("Close the Employment at Separation preview page");
        tradeEnrollmentDetailsForm.click(BaseWingsForm.BaseButton.CLOSE_PREVIEW_PAGE);

        logStep("Verify the Employment at Separation preview page is closed");
        assertFalse("Assert closing Separation preview failed!", tradeEnrollmentDetailsForm.checkPreviewEmploymentSeparation());
    }
}
