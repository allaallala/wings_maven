package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.tradeEnrollment.TradeEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10836")
public class TC_14_71_Trade_Enrollment_Search_With_No_Parameters extends BaseWingsTest {

    public void main() {

        logStep("Log in as Staff and open Trade Enrollment search page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRADE_ENROLLMENTS, Popup.Search);

        logStep("Perform search without filling any parameters");
        TradeEnrollmentSearchForm tradeEnrollmentSearchForm = new TradeEnrollmentSearchForm();
        tradeEnrollmentSearchForm.clickButton(Buttons.Search);

        logStep("Check that at least one record was found");
        tradeEnrollmentSearchForm.yesSearchResult();
    }
}
