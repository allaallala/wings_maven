package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.petition.PetitionCreationForm;
import edu.msstate.nsparc.wings.integration.forms.petition.PetitionDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.petition.PetitionEditForm;
import edu.msstate.nsparc.wings.integration.forms.petition.PetitionSearchForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.EmployerSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.trade.Petition;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10446")
public class TC_04_01_Petitions_Full_Workflow extends BaseWingsTest {


    public void main () {

        Petition petition = new Petition(PetitionType.RTAA);
        EmployerSteps.createEmployer(petition.getEmployer(), Roles.STAFF);
        EmployerSqlFunctions.insertPetitionInformationInReaui(petition);

        logStep("Log in to the system and open required page.");
        BaseWingsSteps.openCreationSearchForm(Roles.TRADEDIRECTOR, WingsTopMenu.WingsStaffMenuItem.E_TRADE_PETITIONS, Popup.Create);

        logStep("Fill out the creation form");
        PetitionCreationForm creationForm = new PetitionCreationForm();
        creationForm.fillOutCreationForm(petition);

        logStep("Press Create button");
        creationForm.clickButton(Buttons.Create);

        logStep("Make Sure the record was created");
        PetitionDetailsForm detailsForm = new PetitionDetailsForm();
        detailsForm.validateInformation(petition);

        logStep("Navigate to Employers - Trade - Petitions - Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_TRADE_PETITIONS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for Petition and open it");
        PetitionSearchForm searchForm = new PetitionSearchForm();
        searchForm.performSearchAndOpen(petition);

        logStep("Press edit button");
        detailsForm.clickButton(Buttons.Edit);

        logStep("Edit information");
        petition.generateRandomData();
        PetitionEditForm editForm = new PetitionEditForm();
        editForm.fillOutEditForm(petition);

        logStep("Save Changes");
        editForm.clickButton(Buttons.Save);

        logStep("Validate that changes were saved");
        detailsForm.validateInformation(petition);
    }
}
