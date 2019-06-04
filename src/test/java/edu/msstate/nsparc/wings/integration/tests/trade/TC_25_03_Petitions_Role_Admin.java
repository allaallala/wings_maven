package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.petition.AffectedEmployeesForm;
import edu.msstate.nsparc.wings.integration.forms.petition.PetitionDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.petition.PetitionEditForm;
import edu.msstate.nsparc.wings.integration.forms.petition.PetitionSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.Petition;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.ParticipantSteps.ParticipantCreationSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import webdriver.Browser;
import framework.CommonFunctions;


/**
 * Check functionality of the petition module: the user role - 'Administrator'
 * Created by a.vnuchko on 10.03.2016.
 */

@TestCase(id = "WINGS-11080")
public class TC_25_03_Petitions_Role_Admin extends BaseWingsTest {
    private Petition petition;
    private Participant employee;

    public void main() {
        //Role - Administrator
        User user = new User(Roles.ADMIN);
        commonPetitionSteps(user);
    }

    /**
     * Common steps for checking user permissions. Petition module.
     *
     * @param user - current user
     */
    protected void commonPetitionSteps(User user) {
        //(!) Create new petition if possible
        if (user.getPetition().getPetitionCreate()) {
            AccountUtils.init();
            petition = new Petition(PetitionType.RTAA);
            TradeEnrollmentSteps.createPetition(petition, user.getRole());
            employee = new Participant();
            ParticipantCreationSteps.createParticipantDriver(employee, Boolean.TRUE, Boolean.FALSE);
        }

        checkOtherFunctionality(user);
    }

    /**
     * Check view, edit and manage affected employee functionality
     *
     * @param user - current user
     */
    private void checkOtherFunctionality(User user) {
        divideMessage("Check other functionality");
        logStep("Check View functionality");
        BaseWingsSteps.logInAs(user.getRole());
        info("Navigate to " + WingsTopMenu.WingsStaffMenuItem.E_TRADE_PETITIONS.name());
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_TRADE_PETITIONS);

        //(!) If user can create petition - > he should confirm pop-up window.
        if (user.getPetition().getPetitionCreate()) {
            BaseWingsSteps.popClick(Popup.Search);
        }

        PetitionSearchForm searchPage = new PetitionSearchForm();
        searchPage.performSearchAndOpen(petition);
        PetitionDetailsForm detailsPage = new PetitionDetailsForm();
        detailsPage.validateAllData(petition);

        //(!) Manage affected employee
        if (user.getPetition().getPetitionManageEmployee()) {
            logStep("Manage affected employee");
            manageAffectedEmployee(employee);
        }

        //(!) If user has permissions to edit petition.
        if (user.getPetition().getPetitionEdit()) {
            logStep("Edit petition");
            petition.setDepartment(CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH));
            petition.setNewTypeWork(CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH));
            editPetition(petition);
        }
        BaseNavigationSteps.logout();
    }

    /**
     * Manage affected employee (add it, search for it and check, that it's displayed)
     *
     * @param employee - participant
     */
    protected void manageAffectedEmployee(Participant employee) {
        PetitionDetailsForm detailsPage = new PetitionDetailsForm();
        detailsPage.manageAffectedEmployees();
        logStep("Add Employee to the list, search for it and check, that it is displayed");
        AffectedEmployeesForm affectedEmployeesForm = new AffectedEmployeesForm();
        affectedEmployeesForm.performSearch(employee);
        affectedEmployeesForm.addEmployeeToList();
        affectedEmployeesForm.validateEmployeeInformation(employee);
        affectedEmployeesForm.clickButton(Buttons.Cancel);
        Browser.getInstance().waitForPageToLoad();
    }

    /**
     * Edit petition
     *
     * @param petit - petition
     */
    protected void editPetition(Petition petit) {
        logStep("Edit petition");
        PetitionDetailsForm detailsPage = new PetitionDetailsForm();
        detailsPage.clickButton(Buttons.Edit);
        PetitionEditForm editPage = new PetitionEditForm();
        editPage.fillOutEditForm(petit);
        editPage.clickButton(Buttons.Save);
        detailsPage.validateInformation(petition);
    }
}
