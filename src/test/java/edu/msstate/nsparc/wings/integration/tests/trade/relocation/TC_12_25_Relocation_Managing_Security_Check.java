package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationCreateForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.models.trade.RelocationExpense;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.Logger;


@TestCase(id = "WINGS-10789")

public class TC_12_25_Relocation_Managing_Security_Check extends BaseWingsTest {

    public void main() {
        Relocation relocation = new Relocation(Constants.TRUE);
        RelocationExpense relocationExpense = new RelocationExpense(Constants.TRUE);
        RelocationCreationSteps.createRelocationWithExpense(relocation, relocationExpense);

        // Staff validation
        logStep("Log into the system as Staff");
        BaseWingsSteps.logInAs(Roles.STAFF);

        checkCreationIsAvailable();
        Logger.getInstance().info("Navigate to Participants - Trade - Relocation - Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION);
        BaseWingsSteps.popClick(Popup.Search);

        Logger.getInstance().info("Perform search and open relocation");
        RelocationSearchForm relocationSearchForm = new RelocationSearchForm();
        relocationSearchForm.performSearchAndOpen(relocation);
        checkEditRelocationNotAvailable();
        checkWorkWithExpenseNotAvailable();
        BaseNavigationSteps.logout();

        // Manager validation
        logStep("Log into the system as Manager");
        BaseWingsSteps.logInAs(Roles.MANAGER);

        checkCreationIsAvailable();
        Logger.getInstance().info("Navigate to Participants - Trade - Relocation - Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION);
        BaseWingsSteps.popClick(Popup.Search);

        Logger.getInstance().info("Perform search and open relocation");
        relocationSearchForm = new RelocationSearchForm();
        relocationSearchForm.performSearchAndOpen(relocation);
        checkEditRelocationNotAvailable();
        checkWorkWithExpenseNotAvailable();
        BaseNavigationSteps.logout();
    }

    /**
     * Check creation is available
     */
    private void checkCreationIsAvailable() {
        logStep("Navigate to Participants - Trade - Relocation - Create");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION);
        BaseWingsSteps.popClick(Popup.Create);

        logStep("Validate Create relocation form");
        new RelocationCreateForm();
    }

    /**
     * Check edit relocation not available
     */
    private void checkEditRelocationNotAvailable() {
        logStep("Validate editing Relocation is unavailable");
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.checkRelocationNotPresent();
    }

    /**
     * Check work with expense not available
     */
    private void checkWorkWithExpenseNotAvailable() {
        logStep("Validate adding, editing and removing expenses are unavailable");
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.expenceButtonsNotPresent();
    }
}
