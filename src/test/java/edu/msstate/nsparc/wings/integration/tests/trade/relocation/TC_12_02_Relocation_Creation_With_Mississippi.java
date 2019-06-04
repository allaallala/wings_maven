package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationCreateForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10764")
public class TC_12_02_Relocation_Creation_With_Mississippi extends BaseWingsTest {

    private static final String ALABAMA_LOCATION_STATE = "Alabama";
    private static final String MISSISSIPPI_LOCATION_STATE = "Mississippi";

    public void main() {

        logStep("Log in as Trade Director and open Relocation creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION, Popup.Create);

        logStep("Select any state except Mississippi: County drop-down shouldn't be displayed");
        RelocationCreateForm relocationCreateForm = new RelocationCreateForm();
        relocationCreateForm.selectLocationState(ALABAMA_LOCATION_STATE);
        relocationCreateForm.checkLocationCountyPresent(Constants.FALSE);

        logStep("Select a state = Mississippi: County drop-down should appear");
        relocationCreateForm.selectLocationState(MISSISSIPPI_LOCATION_STATE);
        relocationCreateForm.checkLocationCountyPresent(Constants.TRUE);
    }
}
