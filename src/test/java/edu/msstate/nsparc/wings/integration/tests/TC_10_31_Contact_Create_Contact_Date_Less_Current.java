package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactCreationForm;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


/**
 * On creating a contact, input contact date less than current, try to create. Check, that new contact is created.
 * Created by a.vnuchko on 05.04.2017.
 */
@TestCase(id = "WINGS-10728")
public class TC_10_31_Contact_Create_Contact_Date_Less_Current extends BaseWingsTest {
    private String company = "Company";
    private String contactDate = CommonFunctions.getYesterdayDate();
    private String contactMethod = "Phone";

    public void main(){
        logStep("Log in WINGS and navigate to required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_CONTACTS, Popup.Create);

        logStep("Select employer and  type of contact. Select contact date < current date");
        ContactCreationForm contactCreationForm = new ContactCreationForm();
        String companyName = contactCreationForm.fillContactData(company, contactDate, contactMethod);

        logStep("Click [Create] button");
        contactCreationForm.clickButton(Buttons.Create);
        BaseNavigationSteps.logout();

        logResult("The new contact is created");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_CONTACTS, Popup.Search);
        ContactSearchForm searchForm = new ContactSearchForm();
        searchForm.searchJustCreatedContact(company, contactDate, contactMethod);
        CustomAssertion.softTrue("Employer field does not contain company name", searchForm.getEmployerText().trim().contains(companyName));
    }
}
