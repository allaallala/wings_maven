package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerEditForm;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.administrative.Staff;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.Assert.assertEquals;


@TestCase(id = "WINGS-10802")
public class TC_12_82_Activity_Manager_Edit_Result extends BaseWingsTest {

    private static final String USER_TYPE = "Administrator";

    public void main() {


        Staff staff = new Staff(AccountUtils.getRandomStaffAccount());
        BaseWingsSteps.createStaffAccount(staff);

        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Advanced->Activity Manager accounts->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_ACTIVITY_MANAGER_ACCOUNTS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Fill in filters that help you to find all Activity Manager account you need");
        ActivityManagerSearchForm activityManagerSearchForm = new ActivityManagerSearchForm();
        activityManagerSearchForm.inputFirstLastName(staff.getFirstName(), staff.getLastName());
        activityManagerSearchForm.clickButton(Buttons.Search);

        logStep("Open one of them->Edit");
        activityManagerSearchForm.openFirstSearchResult();
        ActivityManagerDetailsForm activityManagerDetailsForm = new ActivityManagerDetailsForm();
        activityManagerDetailsForm.clickButton(Buttons.Edit);

        logStep("Change user type");
        ActivityManagerEditForm activityManagerEditForm = new ActivityManagerEditForm();
        activityManagerEditForm.selectUserType(USER_TYPE);

        logStep("Save changes");
        activityManagerEditForm.clickButton(Buttons.Save);

        logStep("Validate User Type");
        activityManagerDetailsForm = new ActivityManagerDetailsForm();
        assertEquals("Assert User Type Failed", USER_TYPE, activityManagerDetailsForm.getUserType());
    }
}
