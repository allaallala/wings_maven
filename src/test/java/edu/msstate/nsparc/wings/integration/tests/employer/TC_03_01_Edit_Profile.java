package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerEditForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10411")
public class TC_03_01_Edit_Profile extends BaseWingsTest {


    public void main() {
        info("We need to create Employer first");
        Employer empl = EmployerSteps.createEmployerSelfRegistration();

        logStep("Login employer");
        LoginForm loginForm = new LoginForm();
        loginForm.loginEmployer();

        logStep("Navigate to Employer Details Page");
        EmployerHomePage homePage = new EmployerHomePage(empl.getCompanyName());
        homePage.clickMyProfile();

        logStep("Click Edit Company Information button");
        EmployerDetailsForm detailsForm = new EmployerDetailsForm(empl.getCompanyName());
        detailsForm.clickEditCompany();
        EmployerEditForm editForm = new EmployerEditForm(Constants.EMPLOYER);

        logStep("Edit full Company Information");
        Employer employer = new Employer();
        editForm.editFullCompanyInformation(employer);

        logStep("Validate that information has been updated");
        CustomAssertion.softTrue("Incorrect company name", detailsForm.getCompanyNameText().contains(employer.getCompanyName()));
        CustomAssertion.softTrue("Incorrect fein", detailsForm.getFeinText().contains(employer.getFein().substring(Constants.EMAIL_LENGTH)));
        CustomAssertion.softTrue("Incorrect ean", detailsForm.getEanText().contains(employer.getEan().substring(Constants.EAN_LENGTH)));
        CustomAssertion.softTrue("Incorrect naics", detailsForm.getNaicsText().contains(employer.getNaics()));
        CustomAssertion.softTrue("Incorrect value federal contractro", detailsForm.getFederalContractorText().contains(Constants.YES_ANSWER));
        CustomAssertion.softTrue("Incorrect rehires text", detailsForm.getRehiresText().contains(Constants.YES_ANSWER));
    }
}
