package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerCreateForm;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerEditForm;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.administrative.Staff;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Check Staff functionality for different user roles (external and internal).
 * Created by a.vnuchko on 05.07.2016.
 */

@TestCase(id = "WINGS-11094")
public class TC_26_04_Staff_Roles extends BaseWingsTest {
    Staff staff;
    Staff staffAnother;

    public void main() {

        //Role - administrator
        User user = new User(Roles.ADMIN);
        commonStaffSteps(user);

        //Role - area director
        user.setNewUser(Roles.AREADIRECTOR);
        commonStaffSteps(user);

        //Role - manager
        user.setNewUser(Roles.MANAGER);
        commonStaffSteps(user);

        //Role - staff
        user.setNewUser(Roles.STAFF);
        commonStaffSteps(user);

        //Role - intake staff
        user.setNewUser(Roles.INTAKESTAFF);
        commonStaffSteps(user);

        //Role - trade director
        user.setNewUser(Roles.TRADEDIRECTOR);
        commonStaffSteps(user);

        //Role - rapid response administrator
        user.setNewUser(Roles.RRADMIN);
        commonStaffSteps(user);

        //Role - everify administrator
        user.setNewUser(Roles.EVERIFY);
        commonStaffSteps(user);

        //Role - WIOA administrator
        user.setNewUser(Roles.WIOA);
        commonStaffSteps(user);

        //Role - WIOA administrator PLUS
        user.setNewUser(Roles.WIOAPLUS);
        commonStaffSteps(user);

        //Role - project code administrator
        user.setNewUser(Roles.PCADMIN);
        commonStaffSteps(user);

        //Role - DVOP veteran
        user.setNewUser(Roles.DVOPVETERAN);
        commonStaffSteps(user);

        //Role - LVER
        user.setNewUser(Roles.LVER);
        commonStaffSteps(user);

        //Role - executive
        user.setNewUser(Roles.EXECUTIVE);
        commonStaffSteps(user);

        //Role - WIOA provider
        user.setNewUser(Roles.WIOAPROVIDER);
        commonStaffSteps(user);

        //Role - LWDA staff
        user.setNewUser(Roles.LWDASTAFF);
        commonStaffSteps(user);
    }

    /**
     * Describes common steps for checking user permissions.
     *
     * @param user - current user.
     */
    private void commonStaffSteps(User user) {
        //(!) Create new staff, if user has permissions to do it.
        if (user.getStaff().getStaffCreate()) {
            logStep("Create new staff");
            AccountUtils.init();
            staff = new Staff(user.getRole());
            BaseWingsSteps.openCreationSearchForm(user.getRole(), WingsTopMenu.WingsStaffMenuItem.A_ACTIVITY_MANAGER_ACCOUNTS, Popup.Create);
            createStaff(user, staff);

            //(!) Create new staff using [Create Another], if user has permissions to do it.
            if (user.getStaff().getStaffViewCreateAnotherStaff()) {
                logStep("Create new staff wia [Create Another]");
                AccountUtils.init();
                staffAnother = new Staff(user.getRole());
                ActivityManagerDetailsForm detailsPage = new ActivityManagerDetailsForm();
                detailsPage.clickCreateAnother();
                createStaff(user, staffAnother);
            }
            BaseNavigationSteps.logout();
        }

        checkViewEditFunctionality(user);
    }

    /**
     * Create staff for specific user role
     *
     * @param user  - current user
     * @param staff - staff
     */
    private void createStaff(User user, Staff staff) {
        ActivityManagerCreateForm activityManagerCreateForm = new ActivityManagerCreateForm();
        activityManagerCreateForm.fillActivityManagerDetails(staff);
        if (user.getStaff().getStaffCreateStaffUserType()) {
            activityManagerCreateForm.selectUserType(staff.getUserType());
        }
        activityManagerCreateForm.fillUsernameAndWorkspace(staff);
        activityManagerCreateForm.clickButton(Buttons.Create);
    }

    /**
     * Check view and edit functionality.
     *
     * @param user - current user.
     */
    private void checkViewEditFunctionality(User user) {
        BaseWingsSteps.logInAs(user.getRole());

        //(!) Check view functionality.
        if (user.getStaff().getStaffView()) {
            logStep("Check view functionality");
            info("Navigate to " + WingsTopMenu.WingsStaffMenuItem.A_ACTIVITY_MANAGER_ACCOUNTS.name());
            new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_ACTIVITY_MANAGER_ACCOUNTS);

            if (user.getStaff().getStaffCreate()) {
                BaseWingsSteps.popClick(Popup.Search);
            }

            ActivityManagerSearchForm searchPage = new ActivityManagerSearchForm();

            //(!) Staff seach disabled account.
            searchPage.checkSearchDisabled(user);

            searchPage.inputFirstLastName(staff.getFirstName(), staff.getLastName());
            searchPage.clickButton(Buttons.Search);
            searchPage.openFirstSearchResult();

            ActivityManagerDetailsForm detailsPage = new ActivityManagerDetailsForm();
            detailsPage.validateData(staff);

            //(!) Check, that buttons [Edit], [Audit] are present or not on the page.
            logStep("Check buttons [Edit], [Audit");
            detailsPage.checkButtonsPresent(user.getStaff().getStaffViewEditButton(), user.getStaff().getStaffViewAuditButton());

            //(!) Check access username visibility
            logStep("Check access username visibility");
            detailsPage.checkAccessUsername(staff, user.getStaff().getStaffAccessUsernameVisibility());

            //(!) Check reset username visibility
            logStep("Check reset username visibility");
            detailsPage.checkResetUsername(user.getStaff().getStaffViewResetUsername());

            //(!) Check edit functionality.
            if (user.getStaff().getStaffEdit()) {
                logStep("Check edit functionality");
                detailsPage.clickButton(Buttons.Edit);
                ActivityManagerEditForm editPage = new ActivityManagerEditForm();
                editPage.editFirstLastUsername(staff);
                editPage.clickButton(Buttons.Save);
                detailsPage.validateData(staff);
                detailsPage.validateUsername(staff.getStaffAccount());
            }

        } else {
            new StaffHomeForm().menuNotPresent(WingsTopMenu.WingsStaffMenuItem.A_ACTIVITY_MANAGER_ACCOUNTS, Constants.RANDOM_ONE);
        }
        BaseNavigationSteps.logout();
    }
}
