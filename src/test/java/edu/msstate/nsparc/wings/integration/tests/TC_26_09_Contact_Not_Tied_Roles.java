package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactCreationForm;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactEditForm;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.trade.Petition;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Check functionality of the Contact module for different user roles. Check only employers that are not tied to the Trade or Petition
 * (Area Director, Manager, Staff, Intake Staff, Trade Administrator, Rapid Response Administrator, LVER) and also those who don't care if they tied or not
 * (Administrator, Everify administrator, project code administrator, DVOP, Executive, LWDA Staff, WIOA Provider)
 * Created by a.vnuchko on 13.07.2016.
 */

@TestCase(id = "WINGS-11099")
public class TC_26_09_Contact_Not_Tied_Roles extends BaseWingsTest {
    Petition petition;
    Boolean neutral, ifTied;
    String contactMethod;

    public void main(){

        //Role - administrator
        User user = new User(Roles.ADMIN);
        commonContactSteps(user, false);

        //Role - LWDA staff
        user.setNewUser(Roles.LWDASTAFF);
        commonContactSteps(user, false);

        //Role - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonContactSteps(user, false);

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonContactSteps(user, false);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        commonContactSteps(user, false);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonContactSteps(user, false);

        //Role - staff
        user.setNewUser(Roles.STAFF);
        commonContactSteps(user, false);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonContactSteps(user, false);


        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonContactSteps(user, false);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonContactSteps(user, false);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonContactSteps(user, false);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonContactSteps(user, false);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonContactSteps(user, false);

        //Role - DVOP
        user.setNewUser(Roles.DVOP);
        commonContactSteps(user, false);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonContactSteps(user, false);

        //Role - executive
        user.setNewUser(Roles.EXECUTIVE);
        commonContactSteps(user, false);

        //Role - LWDA staff
        user.setNewUser(Roles.LWDASTAFF);
        commonContactSteps(user, false);

        //Role - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonContactSteps(user, false);

    }

    /**
     * Describes common steps for checking contact entity
     * @param user - current user
     * @param tied - if employer is tied to Trade or petition
     */
    protected void commonContactSteps(User user, Boolean tied){
        if(tied){
            ifTied = user.getContact().getConCheckTied(); //to make difference, if user tide to the petition or not.
        }else {
            ifTied = user.getContact().getConCheckNotTied();
        }
        neutral = user.getContact().getConCheckNeutral();

        //(!) Check, if user can create new contact.
        if(user.getContact().getConCreate()){
            logStep("Create new contact");
            AccountUtils.init();
            contactMethod = "Phone";
            petition = new Petition(PetitionType.RTAA);
            //If user has to be tied to the petition, we should create petition.
            if(tied){
                TradeEnrollmentSteps.createPetition(petition, Roles.ADMIN);
            }else {
                EmployerSteps.createEmployer(petition.getEmployer(), user.getRole());
            }
            //(!) Check, if user has permissions to create contact depending on employer tide or not to the Trade or Petition.
            if(neutral || ifTied){
                EmployerSteps.createContact(petition.getEmployer(), user.getRole());
            }else{
                createContactImpossible(petition.getEmployer(), user.getRole());
            }
        }

        logStep("Check view functionality");
        BaseWingsSteps.logInAs(user.getRole());
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_WP_CONTACTS);
        //If user can create contact, he should confirm pop-up
        if(user.getContact().getConCreate()){
            BaseWingsSteps.popClick(Popup.Search);
        }
        ContactSearchForm searchPage = new ContactSearchForm();
        searchPage.searchJustCreatedContact(petition.getEmployer().getCompanyName(), CommonFunctions.getCurrentDate(), contactMethod);
        searchPage.openFirstSearchResult();

        ContactDetailsForm detailsPage = new ContactDetailsForm();
        detailsPage.validateBaseParameters(petition.getEmployer(), contactMethod, CommonFunctions.getCurrentDate());

        //(!) Check edit functionality
        if(user.getContact().getConEdit()&&(neutral || ifTied)){
            logStep("Check edit functionality");
            contactMethod = "Mail";
            detailsPage.clickButton(Buttons.Edit);
            ContactEditForm editPage = new ContactEditForm();
            editPage.selectContactMethod(contactMethod);
            editPage.clickButton(Buttons.Save);
        }else {
            new ContactEditForm().assertIsNotPresent(BaseWingsForm.BaseButton.EDIT);
        }
        detailsPage.validateBaseParameters(petition.getEmployer(), contactMethod, CommonFunctions.getCurrentDate());
        BaseNavigationSteps.logout();
    }

    /**
     * Try to create contact and check validation message
     * @param employer - employer
     * @param role - user role
     */
    private void createContactImpossible(Employer employer, Roles role){
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.E_WP_CONTACTS, Popup.Create);

        ContactCreationForm creationForm = new ContactCreationForm();
        creationForm.selectEmployer(employer);
        creationForm.inputJobContactDateMethod(true, CommonFunctions.getCurrentDate(), "Phone");
        creationForm.clickButton(Buttons.Create);

        creationForm.clickButton(Buttons.Done);

        BaseNavigationSteps.logout();
    }
}
