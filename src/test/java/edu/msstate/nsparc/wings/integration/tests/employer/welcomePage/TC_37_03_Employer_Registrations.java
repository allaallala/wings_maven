package edu.msstate.nsparc.wings.integration.tests.employer.welcomePage;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Check, that correct pages are displayed after clicking Job Seeker: Register Now or Employer: Register Now.
 * Created by User on 26.02.2017.
 */

@TestCase(id = "WINGS-11240")
public class TC_37_03_Employer_Registrations extends BaseWingsTest {

    public void main(){
        logStep("Navigate to Welcome Page");
        LoginForm loginPage = new LoginForm();

        logStep("Click 'New User? Register for Free' link (with normal resolution)");
        loginPage.clickNewUser();

        logStep("Click [Job Seeker: Register Now] button");
        loginPage.clickJobSeeker();

        logResult("Access MS page for Participant is displayed");
        //TODO Incomplete: 404 error page is displayed on trying to click [Job Seeker: register now] or [Employer: register now]

    }

}
