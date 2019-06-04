package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.employerEnrollment.EmployerEnrollmentCreationForm;
import edu.msstate.nsparc.wings.integration.forms.employerEnrollment.EmployerEnrollmentSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.CommonFunctions;


/**
 * Try to create employer service enrollment, but cancel creation. Check, that home page is opened
 * Created by a.vnuchko on 17.03.2017.
 */

@TestCase(id = "WINGS-11263")
public class TC_38_24_Employer_Service_Enrollment_Create_Cancel extends BaseWingsTest {
    protected String serviceTitle = "Business Information";

    public void main(){
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(employer, Roles.ADMIN);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_SERVICE_ENROLLMENT, Popup.Create);

        logStep("Select employer, fill in all required fields");
        EmployerEnrollmentCreationForm creationPage = new EmployerEnrollmentCreationForm();
        creationPage.selectEmployer(employer);
        creationPage.selectService(serviceTitle);
        creationPage.chooseScheduledService(Constants.TRUE);
        creationPage.inputCreationDate(CommonFunctions.getCurrentDate());

        logStep("Cancel");
        creationPage.clickButton(Buttons.Cancel);
        creationPage.areYouSure(Popup.Yes);
        new StaffHomeForm();
        BaseNavigationSteps.logout();

        logResult("Enrollment Service isn't created. Home page is opened");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_SERVICE_ENROLLMENT, Popup.Search);

        EmployerEnrollmentSearchForm searchPage = new EmployerEnrollmentSearchForm();
        searchPage.selectEmployer(employer);
        searchPage.noSearchResults();
    }
}
