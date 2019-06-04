package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerCreationForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;


@TestCase(id = "WINGS-10435")
public class TC_08_02_Employer_Create extends BaseWingsTest {

    public void main() {
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS, Popup.Create);

        logStep("Fill in all required fields and Create");
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerCreationForm employerCreationForm = new EmployerCreationForm();
        employerCreationForm.createEmployer(employer);

        logStep("Find new created employer record via searching employer records");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS);
        BaseWingsSteps.popClick(Popup.Search);

        EmployerSearchForm employerSearchForm = new EmployerSearchForm();
        employerSearchForm.performSearch(employer);

        BaseNavigationSteps.logout();

        LoginForm loginPage = new LoginForm("locator");
        loginPage.loginEmployer();
        new EmployerHomePage(employer.getCompanyName());
    }
}
