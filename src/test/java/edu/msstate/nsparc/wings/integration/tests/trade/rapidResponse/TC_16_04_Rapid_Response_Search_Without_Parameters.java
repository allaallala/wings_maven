package edu.msstate.nsparc.wings.integration.tests.trade.rapidResponse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.EmployerSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.employer.RapidResponseEvent;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import org.testng.Assert;


@TestCase(id = "WINGS-10878")
public class TC_16_04_Rapid_Response_Search_Without_Parameters extends BaseWingsTest {

    public void main() {
        RapidResponseEvent event = new RapidResponseEvent();
        EmployerSteps.createRapidResponseEvent(event, Roles.RRADMIN);

        logStep("Log into the system as Staff or Executive or admin");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Employers -> Rapid Response Events");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_RAPID_RESPONSE_EVENTS);

        logStep("Choose 'Search' on the pop up");
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Click the [Search] button");
        RapidResponseEventSearchForm responseSearchPage = new RapidResponseEventSearchForm();
        responseSearchPage.clickButton(Buttons.Search);

        info("All the Rapid Response Events are shown in the Search Results form");
        int rapidCount = EmployerSqlFunctions.getRapidResponseEventCount();
        info("Rapid response event records in the DB = " + rapidCount);
        String recordsCount = responseSearchPage.getSearchedCountRegex().replace(",", "");
        info("Rapid response event records in the search page = " + recordsCount);
        Assert.assertEquals("Incorrect quantity of rapid response records on the search page", String.valueOf(rapidCount), recordsCount);
    }
}
