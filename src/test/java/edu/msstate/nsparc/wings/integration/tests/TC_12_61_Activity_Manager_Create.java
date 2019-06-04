package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerCreateForm;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.administrative.Staff;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10438")
public class TC_12_61_Activity_Manager_Create extends BaseWingsTest {

    private static final String ACCESS_MS_USERNAME_MESSAGE = "Unemployment Services System Username is required.";
    private static final String PRIMARY_WORKSPACE_MESSAGE = "Primary Workplace is required.";


    //sub-task WINGS-2484
    public void main () {

        Staff staff = new Staff(AccountUtils.getRandomStaffAccount());

        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Advanced->Activity Manager accounts->Create");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_ACTIVITY_MANAGER_ACCOUNTS);
        BaseWingsSteps.popClick(Popup.Create);

        logStep("Fill in all fields except Unemployment Services System Username and Primary Workspace (select user type=Wagner-Peyser Staff)");
        ActivityManagerCreateForm activityManagerCreateForm = new ActivityManagerCreateForm();
        activityManagerCreateForm.fillActivityManagerDetails(staff);
        activityManagerCreateForm.selectUserType(staff.getUserType());
        logStep("Try to create");
        activityManagerCreateForm.clickButton(Buttons.Create);

        logStep("Check error messages");
        CustomAssertion.softTrue("Incorrect Unemployment Services System Username error message!",
                activityManagerCreateForm.getUssUsernameMessage().equals(ACCESS_MS_USERNAME_MESSAGE));
        CustomAssertion.softTrue("Incorrect Primary Workspace error message!",
                activityManagerCreateForm.getPwMessage().equals(PRIMARY_WORKSPACE_MESSAGE));

        logStep("Fill in all fields (select user type=Wagner-Peyser Staff)");
        activityManagerCreateForm.fillAllActivityManagerDetails(staff);
        logStep("Create");
        activityManagerCreateForm.clickButton(Buttons.Create);
        logStep("Validate displayed information");
        ActivityManagerDetailsForm activityManagerDetailsForm = new ActivityManagerDetailsForm();
        activityManagerDetailsForm.validateActivityManagerDetails(staff);

        logEnd();
    }
}
