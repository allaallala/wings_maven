package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationCreateForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationEditForm;
import edu.msstate.nsparc.wings.integration.forms.relocation.RelocationSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10456")
public class TC_11_02_Relocation_Full_Workflow extends BaseWingsTest {

    public void main() {
        Relocation relocation = new Relocation();
        TradeEnrollmentSteps.createTradeEnrollment(relocation.getTradeEnrollment(), Roles.ADMIN);

        logStep("Log in to WINGS");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Navigate to Participants - Trade - Relocation - Create");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION);
        BaseWingsSteps.popClick(Popup.Create);

        logStep("Fill out the creation form");
        RelocationCreateForm creationForm = new RelocationCreateForm();
        creationForm.fillOutCreationForm(relocation);

        logStep("Press Create button");
        creationForm.clickButton(Buttons.Create);

        logStep("Make Sure the record was created");
        RelocationDetailsForm detailsForm = new RelocationDetailsForm();
        detailsForm.validateInformation(relocation);

        logStep("Navigate to Participants - Trade - Relocation - Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.P_TRADE_RELOCATION);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for Relocation and open it");
        RelocationSearchForm searchForm = new RelocationSearchForm();
        searchForm.performSearchAndOpen(relocation);

        logStep("Press edit button");
        detailsForm.editRelocation();

        logStep("Edit information");
        relocation.setApproved(Constants.TRUE);
        relocation.setStatusDeterminationDate(CommonFunctions.getCurrentDate());
        RelocationEditForm editForm = new RelocationEditForm();
        editForm.editDetails(relocation);

        logStep("Save Changes");
        editForm.clickButton(Buttons.Save);

        logStep("Validate that changes were saved");
        detailsForm.validateInformation(relocation);
    }
}

