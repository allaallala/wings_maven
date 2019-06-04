package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10729")
public class TC_10_34_Contact_Search extends BaseWingsTest {

    private static final String COMPANY_NAME = "ompany";

    public void main() {
        String companyName = searchContact(COMPANY_NAME, Constants.TRUE);
        logStep("Validate search results");
        ContactSearchForm contactSearchForm = new ContactSearchForm();
        contactSearchForm.validateSearchResults(companyName);
    }

    /**
     * Search for contact
     * @param companyName - company name
     * @param isCompanyNeeded - is company needed
     * @return company name
     */
    public String searchContact (String companyName, boolean isCompanyNeeded) {
        String companyForSearch = null;

        logStep("Log in WINGS and navigate to required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_CONTACTS, Popup.Search);

        logStep("Search for contact");
        ContactSearchForm contactSearchForm = new ContactSearchForm();
        if (isCompanyNeeded) {
            companyForSearch = contactSearchForm.selectAndReturnEmployer(companyName);
        }
        contactSearchForm.clickButton(Buttons.Search);
        return companyForSearch;
    }
}
