package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerCreationForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffLocationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.petition.PetitionCreationForm;
import edu.msstate.nsparc.wings.integration.forms.petition.PetitionDetailsForm;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.EmployerSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.trade.Petition;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10586")
public class TC_04_03_Petitions_Out_Of_State extends BaseWingsTest {


    public void main() {
        Petition petition = new Petition(PetitionType.RTAA);
        petition.markAsOutOfState();
        EmployerSqlFunctions.insertPetitionInformationInReaui(petition);

        logStep("Log in to WINGS");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Navigate to Employers - Trade - Petitions - Create");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_TRADE_PETITIONS);
        BaseWingsSteps.popClick(Popup.Create);

        logStep("Start creating Out-of-State petition");
        PetitionCreationForm petitionCreationForm = new PetitionCreationForm();
        petitionCreationForm.startCreatingOutOfState(petition);

        logStep("Enter Employer information for Out-of-State petition");
        EmployerCreationForm employerCreationForm = new EmployerCreationForm();
        employerCreationForm.createEmployer(petition.getEmployer());

        logStep("Complete petition creation form filling");
        petitionCreationForm.fillOutCreationForm(petition);

        logStep("Press Create button");
        petitionCreationForm.clickButton(Buttons.Create);

        logStep("Make Sure the record was created");
        PetitionDetailsForm detailsForm = new PetitionDetailsForm();
        detailsForm.validateInformation(petition);
    }
}
