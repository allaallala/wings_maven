package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10642")
public class TC_06_02_Activity_Manager_Pcode_Role_Check extends BaseWingsTest {

    private static final String EXPECTED_USER_TYPE = "Project Code Administrator";


    //sub-task WINGS-2778
    public void main () {
        logStep("Log in WINGS as Admin");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Open Activity Manager search form");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_ACTIVITY_MANAGER_ACCOUNTS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Fill 'Unemployment Services System Username' field for user with 'PCODE_ADMIN' role");
        ActivityManagerSearchForm searchForm = new ActivityManagerSearchForm();
        searchForm.inputUsername(AccountUtils.getProjectCodeAdminAccount());

        logStep("Click on Search button");
        searchForm.clickButton(Buttons.Search);

        logStep("Open found record");
        searchForm.openFirstSearchResult();

        logStep("Check displayed 'User Type'");
        ActivityManagerDetailsForm detailsForm = new ActivityManagerDetailsForm();
        assertEquals("Incorrect 'User Type' is displayed", EXPECTED_USER_TYPE, detailsForm.getUserType());

        logEnd();
    }
}
