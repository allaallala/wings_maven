package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactEditForm;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10730")
public class TC_10_40_Contact_Edit extends BaseWingsTest {

    private static final String CONTACT_METHOD = "Other";

    public void main() {
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(employer, Roles.STAFF);

        TC_10_29_Contact_Create createContactTest = new TC_10_29_Contact_Create();
        createContactTest.main();

        logStep("Open contact participantSSDetails form");
        ContactSearchForm contactSearchForm = new ContactSearchForm();
        contactSearchForm.openFirstSearchResult();

        logStep("Click [Edit] button");
        ContactDetailsForm contactDetailsForm = new ContactDetailsForm();
        contactDetailsForm.clickButton(Buttons.Edit);

        logStep("Edit some data and click [Save Changes]");
        ContactEditForm contactEditForm = new ContactEditForm();
        String editedCompanyName = contactEditForm.selectAndReturnEmployer(employer.getCompanyName(), CONTACT_METHOD);
        contactEditForm.clickButton(Buttons.Save);

        logStep("Check information changed");
        contactDetailsForm = new ContactDetailsForm();
        CustomAssertion.softAssertEquals(contactDetailsForm.getContactMethodLabelText(), CONTACT_METHOD, "Assert Contact Method Failed");
        CustomAssertion.softTrue("Assert Employer Name Failed", contactDetailsForm.getEmployerLabelText().contains(editedCompanyName));
        contactDetailsForm.clickButton(Buttons.Done);

        logStep("Employer -> Wagner-Peyser->Contacts");
        new ContactEditForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_WP_CONTACTS);
        BaseWingsSteps.popClick(Popup.Search);

        logStep("Search for created contact");
        contactSearchForm = new ContactSearchForm();
        contactSearchForm.searchJustCreatedContact(editedCompanyName, Constants.EMPTY, CONTACT_METHOD);
        CustomAssertion.softTrue("Assert Contact Method Failed", contactSearchForm.getContactTypeText().contains(CONTACT_METHOD));
        CustomAssertion.softAssertEquals(contactSearchForm.getEmployerText(), editedCompanyName,"Assert Company Name Failed");
    }
}
