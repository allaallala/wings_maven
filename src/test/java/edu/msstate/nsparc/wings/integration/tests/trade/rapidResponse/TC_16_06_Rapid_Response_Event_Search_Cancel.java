package edu.msstate.nsparc.wings.integration.tests.trade.rapidResponse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import webdriver.Browser;
import framework.CommonFunctions;


/**
 * Created by a.vnuchko on 18.06.2015.
 * Check, that if you click the 'Cancel' button on the RRE Search Form,you'll be redirected to the Staff Home Form.
 */

@TestCase(id = "WINGS-10880")
public class TC_16_06_Rapid_Response_Event_Search_Cancel extends BaseWingsTest {

    public void main() {
        logStep("Log into the system as Staff or Executive or admin");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Employers -> Rapid  Response Events");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_RAPID_RESPONSE_EVENTS);

        logStep("Choose 'Search' on the pop up");
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Fill out some search criteria fields with any data");
        RapidResponseEventSearchForm eventSearchPage = new RapidResponseEventSearchForm();
        eventSearchPage.inputDateFromTo(CommonFunctions.getCurrentDate(), CommonFunctions.getCurrentDate());

        logStep("Click the [Cancel] button");
        eventSearchPage.clickButton(Buttons.Cancel);
        Browser.getInstance().waitForPageToLoad();

        logResult("The Staff Home Screen is shown");
        //new StaffHomeForm().assertIsOpen();
    }
}
