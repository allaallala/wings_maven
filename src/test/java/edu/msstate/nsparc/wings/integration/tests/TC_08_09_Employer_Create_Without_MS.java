package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerCreationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.AssertJUnit.assertTrue;

// Author: d.poznyak

@TestCase(id = "WINGS-10709")
public class TC_08_09_Employer_Create_Without_MS extends BaseWingsTest {

    public void main() {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS, Popup.Create);

        logStep("Try to create Employer record");
        EmployerCreationForm employerCreationForm = new EmployerCreationForm();
        employerCreationForm.clickButton(Buttons.Continue);

        logStep("Check, that warning message is shown");
        assertTrue("Error message wasn't displayed", employerCreationForm.warnMessagePresent());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
