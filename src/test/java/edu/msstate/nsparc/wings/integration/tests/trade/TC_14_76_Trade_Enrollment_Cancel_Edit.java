package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10841")
public class TC_14_76_Trade_Enrollment_Cancel_Edit extends BaseWingsTest {

    public void main() {


        TradeEnrollment tradeEnrollment = TradeEnrollmentObjects.getCreatedTradeEnrollment();

        logStep("Log in as Admin and open Trade Enrollment participantSSDetails page");
        TradeEnrollmentSteps.openTradeEnrollmentDetailPage(tradeEnrollment, Roles.ADMIN);

        logStep("Press edit button");
        TradeEnrollmentDetailsForm detailsForm = new TradeEnrollmentDetailsForm();
        detailsForm.clickButton(Buttons.Edit);

        logStep("Edit information");
        TradeEnrollmentEditForm tradeEnrollmentEditForm = new TradeEnrollmentEditForm();
        TradeEnrollment newTradeEnrollment = new TradeEnrollment();
        tradeEnrollmentEditForm.fillOutEditForm(newTradeEnrollment);

        logStep("Click the [Cancel] button");
        detailsForm.clickButton(Buttons.Cancel);
        detailsForm.areYouSure(Popup.Yes);

        logStep("Validate information from creation");
        //detailsForm.assertIsOpen();
        detailsForm.validateInformation(tradeEnrollment);
    }
}
