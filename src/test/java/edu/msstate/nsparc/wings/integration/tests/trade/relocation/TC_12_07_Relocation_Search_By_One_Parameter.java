package edu.msstate.nsparc.wings.integration.tests.trade.relocation;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.RelocationSteps.RelocationCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10769")
public class TC_12_07_Relocation_Search_By_One_Parameter extends BaseWingsTest {

    public void main() {

        Relocation relocation = new Relocation();
        RelocationCreationSteps.createRelocation(relocation, Roles.ADMIN);

        logStep("Log in as Trade Director and open search Relocation page");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION, Popup.Search);

        logStep("Fill in one search criteria field");
        RelocationSearchForm relocationSearchForm = new RelocationSearchForm();
        relocationSearchForm.inputEmployerName(relocation.getEmployerName());

        logStep("Click Search button and open found Relocation");
        relocationSearchForm.clickButton(Buttons.Search);
        relocationSearchForm.openFirstSearchResult();

        logStep("Validate found Relocation");
        RelocationDetailsForm relocationDetailsForm = new RelocationDetailsForm();
        relocationDetailsForm.validateInformation(relocation);
    }
}
