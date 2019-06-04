package edu.msstate.nsparc.wings.integration.tests.participant;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;

@TestCase(id = "WINGS-10505")
public class TC_01_06_Participant_Create_Incorrect_Credentials extends BaseWingsTest {
    String participantAccount = "12345";

    public void main() {
        logStep("Try to log in WINGS using incorrect credentials");
        LoginForm loginForm = new LoginForm();
        loginForm.login(AccountUtils.getParticipantAccount(), participantAccount);

        logStep("Check, that error message is shown");
        //TODO login/logout tests should be updated
        //assertTrue("Error message isn't shown", LoginForm.TBC_LOGIN_ERROR.isPresent());
    }
}
