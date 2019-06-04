package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.PopUpMenu;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerCreateForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffLocationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.administrative.Staff;
import framework.BaseTest;

/**
 * Class provides methods with common steps:
 * - open entity creation pages;
 * - open search pages for each modules;
 * - open entity detail pages;
 */
public abstract class BaseWingsSteps extends BaseTest {
    static String search = "search";

    /**
     * Log in as pointed user
     * @param role User role in system
     */
    public static void logInAs(Roles role) {
        divideMessage("Log in as a " + role);
        LoginForm loginForm = new LoginForm();
        switch (role) {
            case ADMIN:
                loginForm.loginAdmin();
                break;
            case AREADIRECTOR:
                loginForm.loginAreaDirector();
                break;
            case MANAGER:
                loginForm.loginManager();
                break;
            case STAFF:
                loginForm.loginStaff();
                break;
            case TRADEDIRECTOR:
                loginForm.loginTradeDirector();
                break;
            case RRADMIN:
                loginForm.loginRapidResponseDirector();
                break;
            case PCADMIN:
                loginForm.loginProjectCodeAdmin();
                break;
            case DVOP:
                loginForm.loginDvop();
                break;
            case DVOPVETERAN:
                loginForm.loginDvop();
                break;
            case LVER:
                 loginForm.loginLver();
                break;
            case EXECUTIVE:
                loginForm.loginExecutive();
                break;
            case LWDASTAFF:
                loginForm.loginLWDAStaff();
                break;
            case WIOAPROVIDER:
                loginForm.loginWIOAProv();
                break;
            case WIOA:
                loginForm.loginWIOAAdmin();
                break;
            case WIOAPLUS:
                loginForm.loginWIOAAdminPLUS();
                break;
            case INTAKESTAFF:
                loginForm.loginIntakeStaff();
                break;
            case EVERIFY:
                loginForm.loginEVerifyAdmin();
                break;
            default:
                break;
        }
        StaffLocationForm staffPage = new StaffLocationForm();
        staffPage.clickButton(Buttons.Continue);
    }


    public static void openCreationSearchForm(Roles role, WingsTopMenu.WingsStaffMenuItem menu, Popup ppType) {
        logInAs(role);

        divideMessage("Navigate to " + menu.name());
        new StaffHomeForm().clickMenu(menu);

        popClick(ppType);
    }

    public static void openSearchForm(Roles role, WingsTopMenu.WingsStaffMenuItem menu) {
        logInAs(role);

        divideMessage("Navigate to " + menu.name());
        new StaffLocationForm().clickMenu(menu);
    }

    /**
     * Choose create or search in the pop-up depends on what we want to make.
     * @param type - search/create.
     */
    public static void popClick(Popup type) {
        PopUpMenu pop;
        divideMessage("Click [" + type.getValue() + "] in the pop-up");
        if (type.getValue().contains(search)) {
            pop = new PopUpMenu();
            pop.clickSearch();
        } else {
            pop = new PopUpMenu();
            pop.clickCreate();
        }
    }

    public static void createStaffAccount(Staff staff) {
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_ACTIVITY_MANAGER_ACCOUNTS, Popup.Create);

        ActivityManagerCreateForm activityManagerCreateForm = new ActivityManagerCreateForm();
        activityManagerCreateForm.fillAllActivityManagerDetails(staff);

        activityManagerCreateForm.clickButton(Buttons.Create);
        activityManagerCreateForm.clickButton(Buttons.Done);

        BaseNavigationSteps.logout();
    }
}
