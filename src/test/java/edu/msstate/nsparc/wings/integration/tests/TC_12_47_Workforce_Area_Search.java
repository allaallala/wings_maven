package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.workforceArea.WorkforceAreasSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10793")
public class TC_12_47_Workforce_Area_Search extends BaseWingsTest {

    private String area = "area";
    public void main() {
        performSearch();
        logStep("Validate search result");
        WorkforceAreasSearchForm workforceAreasSearchForm = new WorkforceAreasSearchForm();
        workforceAreasSearchForm.validateSearchResults(area);

        BaseNavigationSteps.logout();
    }

    /**
     * Search for workforce area
     */
    public void performSearch() {
        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Advanced->Workforce Area->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_WORKFORCE_AREAS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Fill in filters that help you to find all Workforce Areas you need");
        WorkforceAreasSearchForm workforceAreasSearchForm = new WorkforceAreasSearchForm();
        workforceAreasSearchForm.inputWorkforceArea(area);

        logStep("Search");
        workforceAreasSearchForm.clickButton(Buttons.Search);
    }
}
