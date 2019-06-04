package edu.msstate.nsparc.wings.integration.steps;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactCreationForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerCreationForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerEditForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventCreationForm;
import edu.msstate.nsparc.wings.integration.forms.rapidResponseEvent.RapidResponseEventDetailsForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.employer.RapidResponseEvent;
import framework.CommonFunctions;

public class EmployerSteps extends BaseNavigationSteps {
    private static final String NEW_COUNTY = "Oktibbeha";

    public static void createEmployer(Employer employer, Roles role) {
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS, Popup.Create);
        EmployerCreationForm employerCreationForm = new EmployerCreationForm();
        employerCreationForm.createEmployer(employer);
        logout();
    }



    public static Employer createEmployerSelfRegistration() {
        Employer employer = new Employer();
        employer.getAddress().setCounty(NEW_COUNTY);
        LoginForm loginForm = new LoginForm();
        loginForm.loginEmployer();
        EmployerCreationForm employerCreationForm = new EmployerCreationForm(Constants.EMPLOYER_SS);
        employerCreationForm.createEmployerSelfServices(employer);
        logout();
        return employer;
    }

    public static String editEmployerName(Employer employer) {
        BaseWingsSteps.logInAs(Roles.STAFF);
        navigateToEmployerRecordsSearch();
        EmployerEditForm employerEditForm = openEmployerEditForm(employer);
        return employerEditForm.editCompanyNameAndSaveChanges();
    }

    /**
     * This method is used for editing Employers Federal Contractor status
     * @param employer Employer that will have his status changed
     */
    public static void editFederalContractor(Employer employer) {
        BaseWingsSteps.logInAs(Roles.STAFF);
        navigateToEmployerRecordsSearch();
        EmployerEditForm employerEditForm = openEmployerEditForm(employer);
        employerEditForm.editFederalContractor(true);
    }

    public static Employer setEmployerValidAddress(Employer employer) {
        String city = "Natchez";
        String zip = "39120";
        String lineOne = "Lynda Lee Drive";
        employer.getAddress().setCity(city);
        employer.getAddress().setZipCode(zip);
        employer.getAddress().setLineOne(lineOne);
        return employer;
    }

    public static void createRapidResponseEvent(RapidResponseEvent event, Roles role) {
        EmployerSteps.createEmployer(event.getEmployer(), Roles.STAFF);
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.E_RAPID_RESPONSE_EVENTS, Popup.Create);
        RapidResponseEventCreationForm creationForm = new RapidResponseEventCreationForm();
        creationForm.fillOutCreationForm(event);
        creationForm.clickButton(Buttons.Create);
        RapidResponseEventDetailsForm detailsForm = new RapidResponseEventDetailsForm();
        detailsForm.validateInformation(event);
        BaseNavigationSteps.logout();
    }

    public static void createContact(Employer employer, Roles role) {
        String phone = "Phone";
        BaseWingsSteps.openCreationSearchForm(role, WingsTopMenu.WingsStaffMenuItem.E_WP_CONTACTS, Popup.Create);
        ContactCreationForm creationForm = new ContactCreationForm();
        creationForm.selectEmployer(employer);
        creationForm.inputJobContactDateMethod(true, CommonFunctions.getCurrentDate(), phone);
        creationForm.clickButton(Buttons.Create);
        creationForm.clickButton(Buttons.Done);
        BaseNavigationSteps.logout();
    }
}
