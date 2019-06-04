package edu.msstate.nsparc.wings.integration.tests.employer;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.xray.info.TestCase;

@TestCase(id = "WINGS-10504")
public class TC_01_05_Employer_Create_Incorrect_Username extends BaseWingsTest {

    private static final String INCORRECT_PASS = "12345";

    public void main() {
        info("Preconditions: there is a Unemployment Services System account with username and password");
        logStep("Enter incorrect username");
        //TODO login/logout tests should be updated
        /*LoginForm loginForm = new LoginForm();
        loginForm.login(AccountUtils.getEmployerAccount() + "incorrect", AccountUtils.getDefaultPassword());

        logStep("Check, that error message is shown");
        //assertTrue("Error message isn't shown", LoginForm.TBC_LOGIN_ERROR.isPresent());

        logStep("Enter incorrect password");
        loginForm = new LoginForm();
        loginForm.login(AccountUtils.getEmployerAccount(), INCORRECT_PASS);

        logStep("Check, that error message is shown");
        assertTrue("Error message isn't shown", LoginForm.TBC_LOGIN_ERROR.isPresent());*/
    }
}
