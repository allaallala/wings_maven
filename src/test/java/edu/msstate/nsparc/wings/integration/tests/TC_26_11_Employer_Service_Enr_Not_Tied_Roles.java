package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employerEnrollment.EmployerEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.employerEnrollment.EmployerEnrollmentDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.employerEnrollment.EmployerEnrollmentEditForm;
import edu.msstate.nsparc.wings.integration.forms.employerEnrollment.EmployerEnrollmentSearchForm;
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
 * Check functionality of the Employer Service Enrollment module for different user roles. Check only employers that are not tied to the Trade or Petition
 * (Area Director, Manager, Staff, Intake Staff, Trade Administrator, Rapid Response Administrator, LVER) and also those who don't care if they tied or not
 * (Administrator, Everify administrator, project code administrator, DVOP, Executive, LWDA Staff, WIOA Provider)
 * Created by a.vnuchko on 14.07.2016.
 */

@TestCase(id = "WINGS-11101")
public class TC_26_11_Employer_Service_Enr_Not_Tied_Roles extends BaseWingsTest {
    Petition petition;
    Boolean neutral, ifTied;
    String serviceTitle, dateCreation, dateResult, result;
    Integer agoDate = 5;

    public void main() {

        //Role - administrator
        User user = new User(Roles.ADMIN);
        commonEmployerServiceEnrollmentSteps(user, false);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonEmployerServiceEnrollmentSteps(user, false);

        //Role - trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonEmployerServiceEnrollmentSteps(user, false);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        commonEmployerServiceEnrollmentSteps(user, false);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonEmployerServiceEnrollmentSteps(user, false);

        //Role - staff
        user.setNewUser(Roles.STAFF);
        commonEmployerServiceEnrollmentSteps(user, false);


        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonEmployerServiceEnrollmentSteps(user, false);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonEmployerServiceEnrollmentSteps(user, false);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonEmployerServiceEnrollmentSteps(user, false);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonEmployerServiceEnrollmentSteps(user, false);

        //Role - DVOP
        user.setNewUser(Roles.DVOP);
        commonEmployerServiceEnrollmentSteps(user, false);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonEmployerServiceEnrollmentSteps(user, false);

        //Role - executive
        user.setNewUser(Roles.EXECUTIVE);
        commonEmployerServiceEnrollmentSteps(user, false);
    }

    /**
     * Describes common steps for checking employer service enrollment entity
     *
     * @param user - current user
     * @param tied - if employer is tied to Trade or petition
     */
    protected void commonEmployerServiceEnrollmentSteps(User user, Boolean tied) {
        if (tied) {
            ifTied = user.getEmployerServiceEnrollment().getEseCheckTied(); //to make difference, if user tide to the petition or not.
        } else {
            ifTied = user.getEmployerServiceEnrollment().getEseCheckNotTied();
        }
        neutral = user.getEmployerServiceEnrollment().getEseCheckNeutral();

        //(!) Check if user can create new employer service enrollment
        if (user.getEmployerServiceEnrollment().getEseCreate()) {
            logStep("Create new employer service enrollment");
            AccountUtils.init();
            petition = new Petition(PetitionType.RTAA);
            serviceTitle = "AKservice";
            dateCreation = CommonFunctions.getDaysAgoDate(agoDate);
            dateResult = CommonFunctions.getCurrentDate();
            result = Constants.COMPLETED;
            //If user has to be tied to the petition, we should create petition.
            if (tied) {
                TradeEnrollmentSteps.createPetition(petition, Roles.ADMIN);
            } else {
                EmployerSteps.createEmployer(petition.getEmployer(), user.getRole());
            }
            createServiceEnrollment(user.getRole(), petition.getEmployer(), serviceTitle);
        }


        //(!) Check view functionality
        logStep("Check view functionality");
        BaseWingsSteps.logInAs(user.getRole());
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_SERVICE_ENROLLMENT);

        //(!) It is required to confirm pop-up, if user can create employer service enrollment
        if (user.getEmployerServiceEnrollment().getEseCreate()) {
            BaseWingsSteps.popClick(Popup.Search);
        }

        EmployerEnrollmentSearchForm searchPage = new EmployerEnrollmentSearchForm();
        searchPage.performSearch(petition.getEmployer(), serviceTitle);
        searchPage.openFirstSearchResult();

        EmployerEnrollmentDetailsForm detailsPage = new EmployerEnrollmentDetailsForm();
        detailsPage.validateEnrollmentData(petition.getEmployer(), serviceTitle, dateCreation, dateResult, result);

        //(!) Check edit functionality
        if (user.getEmployerServiceEnrollment().getEseEdit() && (neutral || ifTied)) {
            dateCreation = CommonFunctions.getDaysAgoDate(agoDate * 2);
            detailsPage.clickButton(Buttons.Edit);
            EmployerEnrollmentEditForm editPage = new EmployerEnrollmentEditForm();
            editPage.editCreationDate(dateCreation);
            detailsPage.validateEnrollmentData(petition.getEmployer(), serviceTitle, dateCreation, dateResult, result);
        } else {
           BaseWingsForm.BaseButton.EDIT.getButton().assertIsNotPresent();
        }
        BaseNavigationSteps.logout();
    }

    /**
     * Create new service enrollment
     *
     * @param role         - current user role
     * @param employer     - employer
     * @param serviceTitle - service title
     */
    private void createServiceEnrollment(Roles role, Employer employer, String serviceTitle) {
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_SERVICE_ENROLLMENT, Popup.Create);

        divideMessage("select employer");
        EmployerEnrollmentCreationForm creationPage = new EmployerEnrollmentCreationForm();
        creationPage.selectEmployer(employer);

        divideMessage("Select service");
        creationPage.selectService(serviceTitle);

        divideMessage("Select schedule - no and input creation date");
        creationPage.chooseScheduledService(false);
        creationPage.inputCreationDate(dateCreation);

        divideMessage("Select ended - yes, input end date and result");
        creationPage.chooseEndedService(true);
        creationPage.inputDateResult(dateResult);
        creationPage.selectResult(result);

        creationPage.clickButton(Buttons.Create);

        //(!) Check, if user has permissions to create employer service enrollment depending on employer tide or not to the Trade or Petition.
        if (neutral || ifTied) { //
            creationPage.clickButton(Buttons.Done);
        }
        BaseNavigationSteps.logout();
    }
}
