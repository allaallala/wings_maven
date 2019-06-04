package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.activityManager.ActivityManagerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffLocationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.Assert.assertEquals;


@TestCase(id = "WINGS-10801")
public class TC_12_72_Activity_Manager_Search extends BaseWingsTest {

    private String lastName = "";
    private String firstName = "";

    public void main() {
        performSearch();

        logStep("Validate search result");
        assertEquals("Staff name assert fail", String.format("%1$s, %2$s", lastName, firstName), new SearchResultsForm().getFirstSearchResultLinkText());
        BaseNavigationSteps.logout();
    }

    /**
     * Search for activity manager
     */
    public void performSearch() {
        logStep("Login to the System");
        LoginForm login = new LoginForm();
        login.loginAdmin();
        StaffLocationForm staffLocationForm = new StaffLocationForm();
        String staff = staffLocationForm.getFirstLastNameText();
        staffLocationForm.clickButton(Buttons.Continue);

        logStep("Advanced->Activity Manager accounts->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_ACTIVITY_MANAGER_ACCOUNTS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Fill in filters that help you to find all Activity Manager account you need");
        ActivityManagerSearchForm activityManagerSearchForm = new ActivityManagerSearchForm();
        firstName = staff.split(" ")[0];
        lastName = staff.split(" ")[1];
        activityManagerSearchForm.inputFirstLastName(firstName, lastName);

        logStep("Search");
        activityManagerSearchForm.clickButton(Buttons.Search);
    }
}
