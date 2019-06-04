package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerCreationForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


// Author: d.poznyak

@TestCase(id = "WINGS-10711")
public class TC_08_18_Employer_Create_Cancel extends BaseWingsTest {

    public void main() {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS, Popup.Create);

        logStep("Cancel");
        EmployerCreationForm employerCreationForm = new EmployerCreationForm();
        employerCreationForm.clickButton(Buttons.Cancel);
        employerCreationForm.areYouSure(Popup.Yes);

        logStep("Check, that Home page is opened");
        StaffHomeForm staffHomeForm = new StaffHomeForm();
        CustomAssertion.softTrue("Unfinished participants are not present", staffHomeForm.checkUnfinishedPartipPresent());
        CustomAssertion.softTrue("Unresulted referrals are not present", staffHomeForm.checkUnresReferTablePresent());
        CustomAssertion.softTrue("Current location head is not present", staffHomeForm.checkLocationHeadPresent());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
