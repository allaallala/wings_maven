package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10561")
public class TC_03_03_Contextual_Information_Pane_Trade extends BaseWingsTest {

    public void main () {


        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);

        logStep("Log in WINGS and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS, Popup.Search);

        logStep("Search for Trade Enrollment and open it");
        TradeEnrollmentSearchForm searchForm = new TradeEnrollmentSearchForm();
        searchForm.performSearchAndOpen(tradeEnrollment);

        logStep("Validate information in the Contextual Information Pane");
        TradeEnrollmentDetailsForm detailsForm = new TradeEnrollmentDetailsForm();
        detailsForm.validateTradeContextualInformationPane(tradeEnrollment);
    }
}
