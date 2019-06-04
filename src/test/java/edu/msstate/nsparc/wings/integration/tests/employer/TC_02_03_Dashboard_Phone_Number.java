package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.ResourcesAndInformationForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10542")
public class TC_02_03_Dashboard_Phone_Number extends BaseWingsTest {

    private static final String HELP_INFORMATION_STRING = "For additional assistance, Please contact your nearest WIN Service Center.";


    public void main() {
        info("We need to create Employer first");
        Employer employer = EmployerSteps.createEmployerSelfRegistration();

        logStep("Login employer");
        LoginForm loginForm = new LoginForm();
        loginForm.loginEmployer();

        logStep("Navigate to Resources and Information");
        EmployerHomePage homePage = new EmployerHomePage(employer.getCompanyName());
        ResourcesAndInformationForm resourcesForm = homePage.openResourceInformationForm();

        logStep("Validate contact information is shown in Resources and Information");
        resourcesForm.checkField(ResourcesAndInformationForm.TBC_HELP_INFORMATION, HELP_INFORMATION_STRING, false);
    }
}
