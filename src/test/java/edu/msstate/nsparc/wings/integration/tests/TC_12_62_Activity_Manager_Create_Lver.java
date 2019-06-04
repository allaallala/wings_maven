package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerCreateForm;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.administrative.Staff;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


/**
 * Create Veteran Representative LVER user and check, that it's created succefully.
 * Created by a.vnuchko on 06.04.2017.
 */

@TestCase(id = "WINGS-10795")
public class TC_12_62_Activity_Manager_Create_Lver extends BaseWingsTest {

    public void main(){
        String userType = "Veteran Representative (LVER)";
        protectedSteps(userType);
    }

    /**
     * Repeated steps that can be used in different tests which inherits the current one.
     * @param userType - user type.
     */
    protected void protectedSteps(String userType){
        logStep("Preconditions: there is an Access MS account there is no user matched this username in WING");
        Staff staff = new Staff(AccountUtils.getRandomStaffAccount());

        logStep("Log in WINGS and navigate to required page");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.A_ACTIVITY_MANAGER_ACCOUNTS, Popup.Create);


        logStep("Select user type=Veteran Representative (LVER), add workforce areas and enter e-mail address");
        ActivityManagerCreateForm creationPage = new ActivityManagerCreateForm();
        creationPage.selectUserType(userType);

        logStep("Enter  Access MS username");
        creationPage.fillUsernameAndWorkspace(staff);

        logStep("Fill in all required fields");
        creationPage.fillActivityManagerDetails(staff);

        logStep("Create");
        creationPage.clickButton(Buttons.Create);

        logStep("Find this user");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_ACTIVITY_MANAGER_ACCOUNTS);
        BaseWingsSteps.popClick(Popup.Search);

        logResult("Account is created with available action = Veteran Representative (LVER)");
        ActivityManagerSearchForm searchPage = new ActivityManagerSearchForm();
        searchPage.performSearchAndReturn(staff.getStaffAccount());
        ActivityManagerDetailsForm detailsPage = new ActivityManagerDetailsForm();
        detailsPage.validateUsername(staff.getStaffAccount());
    }
}
