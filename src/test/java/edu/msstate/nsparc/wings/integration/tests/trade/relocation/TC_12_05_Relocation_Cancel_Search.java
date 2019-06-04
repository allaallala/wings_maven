package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10767")
public class TC_12_05_Relocation_Cancel_Search extends BaseWingsTest {

    public void main() {

        Relocation relocation = new Relocation();

        logStep("Log in as Trade Director and open search Relocation page");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION, Popup.Search);

        logStep("Fill in search some criteria fields");
        RelocationSearchForm relocationSearchForm = new RelocationSearchForm();
        relocationSearchForm.selectAnyAvailableParticipant();
        relocationSearchForm.inputEmployerName(relocation.getEmployerName());

        logStep("Click the [Cancel] button");
        relocationSearchForm.clickAndWait(BaseWingsForm.BaseButton.CANCEL);

        logStep("The Staff Home Screen is shown");
        new StaffHomeForm();
    }
}
