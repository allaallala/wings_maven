package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactCreationForm;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10437")
public class TC_10_29_Contact_Create extends BaseWingsTest {

    private static final String COMPANY_NAME = "ompany";
    private static final String CURRENT_DATE = CommonFunctions.getYesterdayDate();
    private static final String CONTACT_METHOD = "Phone";

    public void main() {
        logStep("Log in WINGS and navigate to required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_CONTACTS, Popup.Create);

        logStep("Fill out creation form");
        ContactCreationForm contactCreationForm = new ContactCreationForm();
        String companyName = contactCreationForm.fillContactData(COMPANY_NAME, CURRENT_DATE, CONTACT_METHOD);

        logStep("Click [Create] button");
        contactCreationForm.clickButton(Buttons.Create);

        logStep("Choose 'Search' in the pop-up");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_WP_CONTACTS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search created contacts");
        ContactSearchForm searchForm = new ContactSearchForm();
        searchForm.searchJustCreatedContact(COMPANY_NAME, CURRENT_DATE, CONTACT_METHOD);

        logStep("Check data found");
        CustomAssertion.softTrue("Employer field does not contain company name", searchForm.getEmployerText().trim().contains(companyName));
        CustomAssertion.softTrue("Contact type does not contain date", searchForm.getContactTypeText().trim().contains(CommonFunctions.getDaysAgoDate(1, "yyyy-MM-dd")));
        CustomAssertion.softTrue("Contact type does not contain contact method", searchForm.getContactTypeText().trim().contains(CONTACT_METHOD));
    }
}
