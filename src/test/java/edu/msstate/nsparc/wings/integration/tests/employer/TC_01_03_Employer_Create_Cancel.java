package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerCreationForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10500")
public class TC_01_03_Employer_Create_Cancel extends BaseWingsTest {

    private static final String FEIN_NUMBER = "11-2222222";


    public void main () {

        Employer employer = new Employer(AccountUtils.getEmployerAccount());

        logStep("Login to the System");
        LoginForm loginForm = new LoginForm();
        loginForm.loginEmployer();

        logStep("Fill in some fields");
        EmployerCreationForm employerCreationForm = new EmployerCreationForm(Constants.EMPLOYER_SS);
        employerCreationForm.inputCompanyNameFein(employer.getCompanyName(), FEIN_NUMBER);
        employerCreationForm.clickButton(Buttons.Continue);

        logStep("Press Cancel Button");
        employerCreationForm.clickButton(Buttons.Cancel);
        employerCreationForm.areYouSure(Popup.Yes);

        logStep("Check, that account isn't created");
        loginForm = new LoginForm();
        loginForm.loginEmployer();
        employerCreationForm = new EmployerCreationForm(Constants.EMPLOYER_SS);
        assertTrue("Account is created", employerCreationForm.companyNamePresent());
    }
}
