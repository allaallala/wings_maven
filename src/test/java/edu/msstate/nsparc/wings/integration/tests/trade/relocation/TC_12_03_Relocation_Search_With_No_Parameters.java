package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10765")
public class TC_12_03_Relocation_Search_With_No_Parameters extends BaseWingsTest {

    public void main() {


        logStep("Log in as Trade Director and open search Relocation page");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION, Popup.Search);

        logStep("Click Search button with no search criteria entered");
        RelocationSearchForm relocationSearchForm = new RelocationSearchForm();
        relocationSearchForm.clickButton(Buttons.Search);

        logStep("At least one Job Search was found");
        relocationSearchForm.yesSearchResult();
        info("First found item: " + new SearchResultsForm().getFirstSearchResultLinkText());
    }
}
