package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create trade enrollment with denials, search for it and open its participantSSDetails page. Click [Print] button and check, that printins starts.
 * Created by a.vnuchko on 18.11.2015.
 */

@TestCase(id = "WINGS-10514")
public class TC_01_103_Trade_Enrollment_Denials_Print extends BaseWingsTest {

    public void main() {

        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        tradeEnrollment.setEligible(Constants.FALSE);
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);

        TradeEnrollmentSteps.openTradeEnrollmentDetailPage(tradeEnrollment, Roles.STAFF);

        logStep("Expand denials section and check, that [Print] button is available");
        TradeEnrollmentDetailsForm detailsPage = new TradeEnrollmentDetailsForm();
        detailsPage.expandDenialsCheckPrintButton();

        //TODO check, that on clicking [Print] button printing starts.
        logStep("Click the [Print] button next to any Denial");
        logResult("The printing of the chosen Denial starts");
    }
}
