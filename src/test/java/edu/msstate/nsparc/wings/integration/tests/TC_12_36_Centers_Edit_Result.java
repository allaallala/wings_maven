package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterEditForm;
import edu.msstate.nsparc.wings.integration.forms.jobCenter.CenterSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.administrative.ServiceCenters;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10792")

public class TC_12_36_Centers_Edit_Result extends BaseWingsTest {

    public void main () {
        ServiceCenters centers = new ServiceCenters(Roles.ADMIN);

        logStep("Login to the System");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Remember current Job Center");
        String current = "test";

        logStep("Advanced->Centers->Search");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.A_CENTERS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Fill in filters that help you to find all centers you need");
        CenterSearchForm centerSearchForm = new CenterSearchForm();
        centerSearchForm.performSearch(current);

        logStep("Click Center Name Link");
        centerSearchForm = new CenterSearchForm();
        centerSearchForm.openFirstSearchResult();

        logStep("Open center edit form");
        CenterDetailsForm centerDetailsForm = new CenterDetailsForm();
        centerDetailsForm.clickButton(Buttons.Edit);

        logStep("Edit some center participantSSDetails");
        CenterEditForm centerEditForm = new CenterEditForm();
        centerEditForm.editCenterDetails(centers);

        logStep("Save changes made");
        centerEditForm.clickButton(Buttons.Save);

        logStep("Validate participantSSDetails");
        centerDetailsForm.validateDetails(centers);
    }
}
