package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffLocationForm;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10496")
public class TC_01_01_Login extends BaseWingsTest {

    public void main() {

        logStep("Log in WINGS as Staff");
        LoginForm loginForm = new LoginForm();
        loginForm.loginStaff();

        logStep("Check that 'Choose Location' form is displayed");
        StaffLocationForm staffLocationForm = new StaffLocationForm();

        logStep("Validate links and data on Home page");
        staffLocationForm.validateHeaderData();
    }
}
