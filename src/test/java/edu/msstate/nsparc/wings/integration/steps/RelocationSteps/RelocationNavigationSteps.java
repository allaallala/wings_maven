package edu.msstate.nsparc.wings.integration.steps.RelocationSteps;

import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import framework.Logger;

/**
 * Base steps for Relocation
 */
public class RelocationNavigationSteps extends BaseWingsSteps {
   
    /**
     * Perform common steps:
     *  1. log in to the system;
     *  2. navigate to the Relocation search page;
     *  3. fill out search criteria fields;
     *  4. perform search;
     *  5. open detail page of found Relocation.
     * @param relocation Relocation object to be found and opened
     * @param role User role
     */
    public static void openRelocationDetailPage(Relocation relocation, Roles role) {
        openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION, Popup.Search);

        Logger.getInstance().info("Search for 'Relocation' and open it");
        RelocationSearchForm relocationSearchForm = new RelocationSearchForm();
        relocationSearchForm.performSearchAndOpen(relocation);
    }
}
