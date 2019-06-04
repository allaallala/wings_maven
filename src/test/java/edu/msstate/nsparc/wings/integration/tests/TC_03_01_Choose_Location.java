package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.LoginForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffLocationForm;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10554")
public class TC_03_01_Choose_Location extends BaseWingsTest {

    public void main() {

        logStep("Login as staff");
        LoginForm loginForm = new LoginForm();
        loginForm.loginStaff();

        logStep("Choose location");
        StaffLocationForm staffLocationForm = new StaffLocationForm();
        String locationValue = staffLocationForm.getEligibleWorkplace();
        staffLocationForm.clickButton(Buttons.Continue);

        logStep("Check chosen data");
        StaffHomeForm staffHomeForm = new StaffHomeForm();
        CustomAssertion.softAssertContains(staffHomeForm.getLocationValueText(), locationValue, "Location value does not contain location text");
        CustomAssertion.softAssertContains(staffHomeForm.getLocationHeadText(), locationValue, "Location head does not contain location text");
    }
}
