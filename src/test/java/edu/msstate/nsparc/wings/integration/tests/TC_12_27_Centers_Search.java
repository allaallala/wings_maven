package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffLocationForm;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


// Author: d.poznyak

@TestCase(id = "WINGS-10790")

public class TC_12_27_Centers_Search extends BaseWingsTest {

    private String current = "";

    public void main() {
        performSearch();
        logStep("Validate search result");
        CenterSearchForm centerSearchForm = new CenterSearchForm();
        centerSearchForm.validateSearchResults(current);

        BaseNavigationSteps.logout();
    }

    /**
     * Search for centers
      */
    public void performSearch() {
      logStep("Login to the System");
      LoginForm login = new LoginForm();
      login.loginAdmin();
      StaffLocationForm staffLocationForm = new StaffLocationForm();
      staffLocationForm.clickButton(Buttons.Continue);

      logStep("Remember current Job Center");
      StaffHomeForm staffHomeForm = new StaffHomeForm();
      current = staffHomeForm.getLocationHeadText();

        logStep("Advanced->Centers->Search");
      new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_CENTERS);
        BaseWingsSteps.popClick(Popup.Search);

      logStep("Fill in filters that help you to find all centers you need and click [Search]");
      CenterSearchForm centerSearchForm = new CenterSearchForm();
      centerSearchForm.performSearch(current);
  }
 }
