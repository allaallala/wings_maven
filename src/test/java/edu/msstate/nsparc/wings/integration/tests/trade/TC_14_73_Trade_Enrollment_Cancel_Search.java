package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10838")
public class TC_14_73_Trade_Enrollment_Cancel_Search extends BaseWingsTest {

    public void main() {

        TradeEnrollment tradeEnrollment = TradeEnrollmentObjects.getCreatedTradeEnrollment();

        logStep("Log in as Staff and open Trade Enrollment search page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS, Popup.Search);

        logStep("Fill out some search criteria fields with any data");
        TradeEnrollmentSearchForm tradeEnrollmentSearchForm = new TradeEnrollmentSearchForm();
        tradeEnrollmentSearchForm.fillSearchCriteria(tradeEnrollment);

        logStep("Click Cancel on the search page");
        tradeEnrollmentSearchForm.clickAndWait(BaseWingsForm.BaseButton.CANCEL);

        logStep("Validate Staff Home Screen is open");
        new StaffHomeForm();
    }
}
