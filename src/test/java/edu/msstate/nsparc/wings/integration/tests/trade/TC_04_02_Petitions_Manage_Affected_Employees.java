package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.home.ParticipantHomePage;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.petition.AffectedEmployeesForm;
import edu.msstate.nsparc.wings.integration.forms.petition.PetitionDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.petition.PetitionSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * To save some time we won't create new Petition record in this test, we will use an existing one instead
 */

@TestCase(id = "WINGS-10583")
public class TC_04_02_Petitions_Manage_Affected_Employees extends BaseWingsTest {


    public void main() {
        Participant employee = new Participant();
        ParticipantCreationSteps.createParticipantDriver(employee, Boolean.TRUE, Boolean.FALSE);

        logStep("Log in WINGS as Participant");
        BaseNavigationSteps.loginParticipant();

        logStep("Make sure that the Trade Benefits message is not displayed");
        ParticipantHomePage participantHomePage = new ParticipantHomePage(Constants.PARTICIPANT_SS);

        logStep("Logout");
        BaseNavigationSteps.logout();

        logStep("Log in WINGS as Admin");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Navigate to Employers - Trade - Petitions - Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_TRADE_PETITIONS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for any record and open it");
        PetitionSearchForm searchForm = new PetitionSearchForm();
        searchForm.clickButton(Buttons.Search);
        searchForm.openFirstSearchResult();

        logStep("Press Manage Affected Employees button");
        PetitionDetailsForm detailsForm = new PetitionDetailsForm();
        detailsForm.manageAffectedEmployees();

        logStep("Search for specified Employee");
        AffectedEmployeesForm affectedEmployeesForm = new AffectedEmployeesForm();
        affectedEmployeesForm.performSearch(employee);

        logStep("Employee should not be found");
        affectedEmployeesForm.noSearchResults();

        logStep("Add Employee to the list");
        affectedEmployeesForm.addEmployeeToList();

        logStep("Search for added Employee");
        affectedEmployeesForm.performSearch(employee);

        logStep("Employee should be displayed");
        affectedEmployeesForm.validateEmployeeInformation(employee);

        logStep("Log in WINGS as Participant");
        BaseNavigationSteps.logout();
        BaseNavigationSteps.loginParticipant();

        logStep("Make sure that the Trade Benefits message is displayed");
        participantHomePage.checkTradeBenefitsPresent(Constants.TRUE);
    }
}
