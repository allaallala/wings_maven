package edu.msstate.nsparc.wings.integration.tests.staff;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Login to the systems using incorrect username or password. Check, that warning message is displayed.
 * Created by a.vnuchko on 01.03.2017.
 */

@TestCase(id = "WINGS-11242")
public class TC_38_01_Login_Incorrect extends BaseWingsTest {
    String incorrectLogin = CommonFunctions.getTimestamp()+"Aa";
    String incorrectPassword = CommonFunctions.getTimestamp()+"Bb";

    public void main(){
        /*logStep("Open the WINGS");
        LoginForm loginPage = new LoginForm();

        logStep("Enter incorrect username and correct password and confirm");
        loginPage.login(incorrectLogin, AccountUtils.getAdminAccount());

        logResult("The warning message appears");
        loginPage.validateWarningMessage();

        logStep("Enter correct username and incorrect password");
        loginPage.clickReturn();
        loginPage.login(AccountUtils.getAdminAccount(), incorrectPassword);

        logResult("The warning message appears");
        loginPage.validateWarningMessage();*/
    }
}
