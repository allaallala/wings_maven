package edu.msstate.nsparc.wings.integration.tests.trade.rapidResponse;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventEditForm;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.employer.RapidResponseEvent;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Check rapid response event functionality for internal user roles
 * Created by a.vnuchko on 01.02.2016.
 */

@TestCase(id = "WINGS-10881")
public class TC_16_07_Rapid_Response_Roles_Check extends BaseWingsTest {
    private RapidResponseEvent rre;
    private Integer daysAgo = 1;

    public void main(){
        //Role - administrator
        User user = new User(Roles.ADMIN);
        commonRapidResponseSteps(user);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        commonRapidResponseSteps(user);

        //Role - MDES manager
        user.setNewUser(Roles.MANAGER);
        commonRapidResponseSteps(user);

        //Role - MDES staff
        user.setNewUser(Roles.STAFF);
        commonRapidResponseSteps(user);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonRapidResponseSteps(user);

        //Role -  trade administrator
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonRapidResponseSteps(user);

        //Role - everify admin
        user.setNewUser(Roles.EVERIFY);
        commonRapidResponseSteps(user);

        //Role - WIOA admin
        user.setNewUser(Roles.WIOA);
        commonRapidResponseSteps(user);

        //Role - WIOA admin PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonRapidResponseSteps(user);

        //Role - PC admin
        user.setNewUser(Roles.PCADMIN);
        commonRapidResponseSteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonRapidResponseSteps(user);

        //Role - executive.
        user.setNewUser(Roles.EXECUTIVE);
        commonRapidResponseSteps(user);

        //Role - rapid response admin
        user.setNewUser(Roles.RRADMIN);
        commonRapidResponseSteps(user);

        //Role - administrator
        user.setNewUser(Roles.LWDASTAFF);
        commonRapidResponseSteps(user);

        //Role - area director
        user.setNewUser(Roles.WIOAPROVIDER);
        commonRapidResponseSteps(user);

        //Role - DVOP veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonRapidResponseSteps(user);
    }

    /**
     * Common steps for checking user permissions
     * @param user - current user
     */
    private void commonRapidResponseSteps(User user){
        //(!) Create new Rapid Response Event
        logStep("Create new Rapid response event, if possible");
        if(user.getRre().getRRCreate()){
            AccountUtils.init();
            rre = new RapidResponseEvent();
            EmployerSteps.createRapidResponseEvent(rre, user.getRole());
        }

        checkOtherFunctionality(user);
    }
    /**
     * Check other functionality: edit, view, audit.
     * @param user - current user.
     */
    private void checkOtherFunctionality(User user){
        String affectedEmployee = CommonFunctions.getRandomIntegerNumber(2);
        String servedEmployee = CommonFunctions.getRandomIntegerNumber(2);
        divideMessage("Check other functionality");
        logStep("Check View functionality");
        BaseWingsSteps.logInAs(user.getRole());
        info("Navigate to " + WingsTopMenu.WingsStaffMenuItem.E_RAPID_RESPONSE_EVENTS.name());
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_RAPID_RESPONSE_EVENTS);

        //(!) If user can create Rapid Response Event - > he should confirm pop-up window.
        if(user.getRre().getRRCreate()){
            BaseWingsSteps.popClick(Popup.Search);
        }

        RapidResponseEventSearchForm searchPage = new RapidResponseEventSearchForm();
        searchPage.performSearch(rre);
        searchPage.openFirstSearchResult();

        //(!) Check detailed information about rre
        RapidResponseEventDetailsForm detailsPage = new RapidResponseEventDetailsForm();
        detailsPage.validateInformation(rre);

        //(!) Check buttons on the detailed rapid response event page.
        logStep("Check [Audit], [Edit] buttons on the detail page");
        detailsPage.checkButtonsPresent(user.getRre().getRRViewEditButton(), user.getRre().getRrViewAuditButton());

        //(!) Check edit functionality.
        if(user.getRre().getRREdit()){
            logStep("Check edit functionality");

            //new values to edit
            rre.setAffectedEmployee(affectedEmployee);
            rre.setServedEmployee(servedEmployee);
            detailsPage.clickButton(Buttons.Edit);
            RapidResponseEventEditForm editPage = new RapidResponseEventEditForm();
            editPage.fillOutEditForm(rre);

            //(!) Check date notification edit.
            logStep("Check that is possible to edit [Date Notification]");
            if(user.getRre().getRREditDateNotification()){
                rre.setNotificationDate(CommonFunctions.getDaysAgoDate(daysAgo)); //input date to check edit
                editPage.inputNotificationDate(rre.getNotificationDate());
                daysAgo++;
            }

            divideMessage("Save changes and validate it");
            editPage.clickButton(Buttons.Save);
            detailsPage.validateInformation(rre);
        }
        BaseNavigationSteps.logout();
    }
}
