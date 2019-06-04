package edu.msstate.nsparc.wings.integration.tests.testsOnDefects;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.contact.ContactSearchForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffLocationForm;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.tests.TC_10_34_Contact_Search;
import edu.msstate.nsparc.xray.info.TestCase;
import webdriver.Browser;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10547")
public class TC_02_09_Use_Permalink_Before_Login extends BaseWingsTest {

    private String[] contactDetailsInitial;
    private String[] contactDetailsPermalink;

    //sub-task WINGS-2517
    public void main() {
        TC_10_34_Contact_Search contactSearch = new TC_10_34_Contact_Search();
        contactSearch.searchContact("", false);

        logStep("Open contact participantSSDetails form");
        ContactSearchForm contactSearchForm = new ContactSearchForm();
        contactSearchForm.openFirstSearchResult();

        logStep("Get contact participantSSDetails data");
        ContactDetailsForm contactDetailsForm = new ContactDetailsForm();
        contactDetailsInitial = contactDetailsForm.getContactDetails();

        logStep("Click on permalink and close it");
        String permalink = contactDetailsForm.getPermalinkAndCloseIt();

        logStep("Log out");
        BaseNavigationSteps.logout();

        logStep("Login as staff");
        Browser.getInstance().navigate(permalink);
        LoginForm login = new LoginForm("locator");
        login.loginStaff();

        logStep("Click [Continue] button");
        StaffLocationForm staffLocationForm = new StaffLocationForm();
        staffLocationForm.clickButton(Buttons.Continue);

        logStep("Validate contact participantSSDetails");
        contactDetailsForm = new ContactDetailsForm();
        contactDetailsPermalink = contactDetailsForm.getContactDetails();
        for (int i = 0; i < contactDetailsPermalink.length; i++) {
            String message = String.format("Assert contact participantSSDetails failed! Actual: %1$s. Expected: %2$s", contactDetailsInitial[i], contactDetailsPermalink[i]);
            assertEquals(message, contactDetailsPermalink[i], contactDetailsInitial[i]);
        }
    }
}

