package edu.msstate.nsparc.wings.integration.tests.participant.welcomePage;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.xray.info.TestCase;

import static framework.customassertions.CustomAssertion.softTrue;

/**
 * Checking New User page navigation
 * Created by a.korotkin on 10.11.2016.
 */

@TestCase(id = "WINGS-11179")
public class TC_32_04_WP_New_User_Page extends BaseWingsTest {

    public void main(){
        logStep("Open Candidate Search");
        LoginForm loginForm = new LoginForm();
        loginForm.clickNewUser();
        softTrue("Incorrect page is displayed", loginForm.jobSeekerIsPresent());
    }
}
