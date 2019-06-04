package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerEditForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.trade.Petition;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check user permissions for the Employer module. For those roles, who don't care if an employer tied or not to Trade or Petition.
 * (Administrator, Everify administrator, Project code administrator, DVOP veteran, Executive, LWDA Staff, WIOA Provider
 * Created by a.vnuchko on 12.07.2016.
 */

@TestCase(id = "WINGS-11096")
public class TC_26_06_Employer_Neutral_Roles extends BaseWingsTest {
    Petition petition;
    Boolean ifTide;
    Boolean editCompany, editContact, editEverify, neutral;

    public void main() {

        //Role - administrator
        User user = new User(Roles.ADMIN);
        commonEmployerSteps(user, false);

        //Role - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonEmployerSteps(user, false);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonEmployerSteps(user, false);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonEmployerSteps(user, false);

        //Role - DVOP
        user.setNewUser(Roles.DVOP);
        commonEmployerSteps(user, false);

        //Role - executive
        user.setNewUser(Roles.EXECUTIVE);
        commonEmployerSteps(user, false);

        //Role - LWDA Staff
        user.setNewUser(Roles.LWDASTAFF);
        commonEmployerSteps(user, false); //TODO ISSUE https://jira.nsparc.msstate.edu/browse/WINGS-9172

    }

    /**
     * Common steps for checking user permissions
     * @param user - current user.
     */
    protected void commonEmployerSteps(User user, Boolean tied){
        //(!) Check, if user can create new employer.
        if(user.getEmployer().getEmployerCreate()){
            logStep("Create new employer");
            AccountUtils.init();
            petition = new Petition(PetitionType.RTAA);
            //If user has to be tied to the petition, we should create petition.
            if(tied){
                TradeEnrollmentSteps.createPetition(petition, Roles.ADMIN);
            }else {
                EmployerSteps.createEmployer(petition.getEmployer(), user.getRole());
            }
        }

        checkOtherFunctionality(user, tied);
    }

    /**
     * Check other functionality (View, Edit)
     * @param user - current user
     */
    private void checkOtherFunctionality(User user, Boolean tideness){
        if(tideness){
            ifTide = user.getEmployer().getEmployerCheckTide(); //to make difference, if user tide to the petition or not.
        }else {
            ifTide = user.getEmployer().getEmployerCheckNotTide();
        }

        editCompany = user.getEmployer().getEmployerEditCompanyInformation();
        editContact = user.getEmployer().getEmployerEditContactInformation();
        editEverify = user.getEmployer().getEmployerEditEverifyInformation();
        neutral = user.getEmployer().getEmployerCheckNeutral();

        logStep("Check view functionality");
        BaseWingsSteps.logInAs(user.getRole());

        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS);

        //if user can create employers, he should confirm pop-up.
        if(user.getEmployer().getEmployerCreate()){
            BaseWingsSteps.popClick(Popup.Search);
        }

        EmployerSearchForm searchPage = new EmployerSearchForm();
        searchPage.performSearch(petition.getEmployer());
        searchPage.openFirstSearchResult();

        EmployerDetailsForm detailsPage = new EmployerDetailsForm();
        detailsPage.validateData(petition.getEmployer());

        //(!) Check [edit] buttons present on the page.
        logStep("Check buttons present on the page");
        detailsPage.checkEmployerButtons(user, ifTide, neutral);

        editVariations();

        //(!) Check, if user can reset username
        if(user.getEmployer().getEmployerEditRemoveUsername()){
            logStep("Remove username");
            detailsPage.removeUsername();

            //(!) Check, if user can create username account
            if(user.getEmployer().getEmployerEditCreateAccount()){
                logStep("Create username again");
                detailsPage.createUsername(petition.getEmployer());
                detailsPage.clickButton(Buttons.Save);
            }
        }

        detailsPage.validateData(petition.getEmployer());

        BaseNavigationSteps.logout();
    }

    /**
     * Edit employer depending on if employer is tied to the petition or not.
     */
    private void editVariations(){
        EmployerDetailsForm detailsPage = new EmployerDetailsForm();
        //(!) Check, if user can edit company information.
        if(editCompany&&(ifTide || neutral)){
            logStep("Edit company information");
            detailsPage.clickEditCompany();
            EmployerEditForm editPage = new EmployerEditForm();
            editPage.editCompanyFeinEan(petition.getEmployer());
            editPage.clickButton(Buttons.Save);
        }

        //(!) Check, if user can edit contact information.
        if(editContact&&(ifTide || neutral)){
            logStep("Edit contact information");
            detailsPage.clickEditContact();
            EmployerEditForm editPage = new EmployerEditForm();
            editPage.editCity(petition.getEmployer());
            editPage.clickButton(Buttons.Save);
        }

        //(!) Check, if user can edit everify information.
        if(editEverify&&(ifTide || neutral)){
            logStep("Edit everify information");
            detailsPage.clickEditEverify();
            EmployerEditForm editPage = new EmployerEditForm();
            editPage.editEverifyLineOne(petition.getEmployer());
            editPage.clickButton(Buttons.Save);
        }
    }
}
