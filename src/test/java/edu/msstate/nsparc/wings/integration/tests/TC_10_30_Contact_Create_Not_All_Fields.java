package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactCreationForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10727")
public class TC_10_30_Contact_Create_Not_All_Fields extends BaseWingsTest {

    private static final String CURRENT_DATE = CommonFunctions.getCurrentDate();
    private static final String CONTACT_METHOD = "Phone";
    private static final String ERROR_MESSAGE = "Employer is required.";

    public void main() {

        logStep("Log in WINGS and navigate to required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_CONTACTS, Popup.Create);

        logStep("Don't select Employer");
        ContactCreationForm contactCreationForm = new ContactCreationForm();
        contactCreationForm.inputJobContactDateMethod(Constants.TRUE, CURRENT_DATE, CONTACT_METHOD);

        logStep("Create");
        contactCreationForm.clickButton(Buttons.Create);

        logStep("Check warning message");
        assertEquals("Error message assert fail", ERROR_MESSAGE, contactCreationForm.getEmployerError());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
