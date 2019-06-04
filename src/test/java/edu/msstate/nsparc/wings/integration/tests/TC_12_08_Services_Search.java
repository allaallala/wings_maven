package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceSearchForm;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ServiceSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10771")
public class TC_12_08_Services_Search extends BaseWingsTest {

    private String titleService = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);

    public void main() {
        ServiceSteps.createService(Roles.ADMIN, titleService, Constants.FALSE, Constants.FALSE);

        performSearch(titleService);

        logStep("Validate search result");
        ServiceSearchForm serviceSearchForm = new ServiceSearchForm();
        serviceSearchForm.validateSearchResults(titleService);
        BaseNavigationSteps.logout();
    }

    /**
     * Search for services
     * @param title - titleService of the services
     */
    public void performSearch(String title) {
        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Advanced->Services->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_SERVICES);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Fill in filters that help you to find all services you need");
        ServiceSearchForm serviceSearchForm = new ServiceSearchForm();
        serviceSearchForm.inputServiceName(title);

        logStep("Search");
        serviceSearchForm.clickButton(Buttons.Search);
    }
}
