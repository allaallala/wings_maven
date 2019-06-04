package edu.msstate.nsparc.wings.integration.tests.employer.dashboard;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.ResourcesAndInformationForm;
import edu.msstate.nsparc.wings.integration.forms.home.EmployerHomePage;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Login chosen employer, Click 'Resource & Information' and check text in all the sections and buttons [Learn more].
 * Created by User on 26.02.2017.
 */

@TestCase(id = "WINGS-11241")
public class TC_37_04_Validate_RI_Sections_Text extends BaseWingsTest {

    public void main(){

        Employer employer = EmployerSteps.createEmployerSelfRegistration();

        logStep("Login to the System");
        LoginForm loginForm = new LoginForm();
        loginForm.loginEmployer();

        logStep("Resources & Information->Resources & Information");
        EmployerHomePage homePage = new EmployerHomePage(employer.getCompanyName());
        ResourcesAndInformationForm resPage = homePage.openResourceInformationForm();

        logResult("Check text in the sections and buttons 'Learn More'");
        resPage.checkButtonsPresent();
        resPage.checkTextSections();

    }

}
