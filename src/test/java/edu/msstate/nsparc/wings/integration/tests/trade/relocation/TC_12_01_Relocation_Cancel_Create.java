package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationCreateForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10762")
public class TC_12_01_Relocation_Cancel_Create extends BaseWingsTest {

    public void main() {

        Relocation relocation = new Relocation();

        logStep("Log in as Trade Director and open Relocation creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION, Popup.Create);

        logStep("Fill out some fields on the creation form");
        RelocationCreateForm relocationCreateForm = new RelocationCreateForm();
        relocationCreateForm.fillOutCreationFormWithoutParticipant(relocation);

        logStep("Click the [Cancel] button");
        relocationCreateForm.clickButton(Buttons.Cancel);
        relocationCreateForm.areYouSure(Popup.Yes);

        logStep("Navigate to Participants - Trade - Relocation - Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for Relocation");
        RelocationSearchForm relocationSearchForm = new RelocationSearchForm();
        relocationSearchForm.performSearchByEmployerName(relocation.getEmployerName());

        logStep("Validate that Relocation wasn't created");
        info("No relocation should be found by Employer Name: " + relocation.getEmployerName());
        relocationSearchForm.noSearchResults();
    }
}
