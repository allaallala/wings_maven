package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactEditForm;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10731")
public class TC_10_42_Contact_Edit_All extends BaseWingsTest {

    private static final String NEW_CONTACT_METHOD = "Other";
    private static final String NEW_DESCRIPTION = "Automation Description";
    private static final String NEW_RESULT = "Automation Result";

    public void main() {
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(employer, Roles.STAFF);
        EmployerSteps.createContact(employer, Roles.STAFF);

        logStep("Log in WINGS and navigate to required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_WP_CONTACTS, Popup.Search);

        logStep("Select some parameters which help you to find contact");
        ContactSearchForm contactSearchForm = new ContactSearchForm();
        contactSearchForm.selectEmployer(employer);
        contactSearchForm.clickButton(Buttons.Search);

        logStep("Open contact");
        contactSearchForm.openFirstSearchResult();

        logStep("Edit");
        ContactDetailsForm detailsForm = new ContactDetailsForm();
        detailsForm.clickButton(Buttons.Edit);

        logStep("Edit all parameters");
        ContactEditForm contactEditForm = new ContactEditForm();
        contactEditForm.inputJobContactDateMethod(Constants.FALSE, CommonFunctions.getYesterdayDate(), NEW_CONTACT_METHOD);
        contactEditForm.inputDescriptionResult(NEW_DESCRIPTION, NEW_RESULT);

        logStep("Save changes");
        detailsForm.clickButton(Buttons.Save);

        logStep("Validate that changes are saved");
        detailsForm.validateDataChanges(NEW_CONTACT_METHOD, NEW_DESCRIPTION, NEW_RESULT);

        BaseNavigationSteps.logout();
        logEnd();
    }
}
