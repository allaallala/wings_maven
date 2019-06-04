package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerCreationForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import framework.Logger;
import framework.customassertions.CustomAssertion;

@TestCase(id = "WINGS-10410")
public class TC_01_01_Employer_Create extends BaseWingsTest {

    @Override
    public void main() {
        Employer employer = createEmployer();

        logStep("Login with created Employer record");
        LoginForm loginPage = new LoginForm();
        loginPage.loginEmployer();

        logStep("Validate all Employer information");
        EmployerHomePage employerHomePage = new EmployerHomePage(employer.getCompanyName());
        employerHomePage.clickMyProfile();
        EmployerDetailsForm detailsForm = new EmployerDetailsForm(employer.getCompanyName());
        CustomAssertion.softTrue("Incorrect address line one", detailsForm.getLocationAddressText().trim().contains(employer.getAddress().getLineOne()));
        CustomAssertion.softTrue("Incorrect address city", detailsForm.getLocationAddressText().trim().contains(employer.getAddress().getCity()));
        CustomAssertion.softTrue("Incorrect address zip code", detailsForm.getLocationAddressText().trim().contains(employer.getAddress().getZipCode()));
        CustomAssertion.softTrue("Incorrect address company name", detailsForm.getCompanyNameText().contains(employer.getCompanyName()));
    }

    protected Employer createEmployer() {
        logStep("Login to the System with Unemployment Services System Username and Password");
        LoginForm loginPage = new LoginForm();
        loginPage.loginEmployer();

        logStep("Fill in all required fields and Create");
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerCreationForm employerCreationForm = new EmployerCreationForm(Constants.EMPLOYER_SS);
        employerCreationForm.createEmployerSelfServices(employer);
        BaseNavigationSteps.logout();
        return employer;
    }
}
