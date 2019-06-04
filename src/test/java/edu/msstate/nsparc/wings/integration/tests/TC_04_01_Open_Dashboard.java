package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;

@TestCase(id = "WINGS-10580")
public class TC_04_01_Open_Dashboard extends BaseWingsTest {

    public void main() {

        logStep("Login to the system as staff");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Check records tables");
        StaffHomeForm staffHomeForm = new StaffHomeForm();
        CustomAssertion.softTrue("Your Unresulted Referrals Table is absent",
                staffHomeForm.checkUnresReferTablePresent());
        CustomAssertion.softTrue("Your Incomplete Participant Records Table is absent",
                staffHomeForm.checkUnfinishedPartipPresent());
    }
}
