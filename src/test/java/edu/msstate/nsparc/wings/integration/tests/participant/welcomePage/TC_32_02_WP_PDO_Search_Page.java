package edu.msstate.nsparc.wings.integration.tests.participant.welcomePage;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.xray.info.TestCase;

import static framework.customassertions.CustomAssertion.softAssertEquals;

/**
 * Checking PDO page navigation
 * Created by a.korotkin on 09.11.2016.
 */

@TestCase(id = "WINGS-11177")
public class TC_32_02_WP_PDO_Search_Page extends BaseWingsTest {
    private String titleOfPage = "Professional Development Opportunities";

    public void main(){
        logStep("Open PDO Search");
        LoginForm loginForm = new LoginForm();
        loginForm.clickSeePDO();
        softAssertEquals(loginForm.getNameOfPage(), titleOfPage, "The search page is incorrect");
    }
}
