package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10837")
public class TC_14_72_Trade_Enrollment_Clear_Search_Form extends BaseWingsTest {

    public void main() {

        TradeEnrollment tradeEnrollment = TradeEnrollmentObjects.getCreatedTradeEnrollment();

        logStep("Log in as Staff and open Trade Enrollment search page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS, Popup.Search);

        logStep("Fill out some search criteria fields with any data");
        TradeEnrollmentSearchForm tradeEnrollmentSearchForm = new TradeEnrollmentSearchForm();
        tradeEnrollmentSearchForm.fillSearchCriteria(tradeEnrollment);

        logStep("Click the Clear Form button");
        tradeEnrollmentSearchForm.clickButton(Buttons.Clear);

        logStep("Check all parameters are set to default");
        tradeEnrollmentSearchForm.validateEmptyLookup();
    }
}
