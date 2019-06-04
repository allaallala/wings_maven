package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check logout functionality
 */

@TestCase(id = "WINGS-10536")
public class TC_02_01_Logout extends BaseWingsTest {

    public void main() {

        logStep("Log in WINGS as Staff");
        LoginForm loginForm = new LoginForm();
        loginForm.loginStaff();

        logStep("Click on Logout link");
        BaseNavigationSteps.logout();

        logStep("Check that Login form is displayed");
        //TODO login/logout tests should be updated
        /*CustomAssertion.softTrue("Assert Login Button Failed", LoginForm.BTN_LOGIN.isPresent());
        CustomAssertion.softTrue("Assert User Name TextBox Failed", LoginForm.TXB_USERNAME.isPresent());
        CustomAssertion.softTrue("Assert Password TextBox Failed", LoginForm.TXB_PASSWORD.isPresent());*/
    }
}
