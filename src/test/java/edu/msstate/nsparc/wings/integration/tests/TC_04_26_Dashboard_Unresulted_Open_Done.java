package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.referral.ReferralDetailsForm;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10612")
public class TC_04_26_Dashboard_Unresulted_Open_Done extends TC_04_23_Dashboard_Unresulted_Cancel {

    public void main() {

        checkUnresultedReferral();
        ReferralDetailsForm referralDetailsForm = new ReferralDetailsForm();
        referralDetailsForm.clickButton(Buttons.Done);

        logStep("Validate, that Home page is opened");
        StaffHomeForm staffHomeForm = new StaffHomeForm();
        CustomAssertion.softTrue("Unfinished participants are not present", staffHomeForm.checkUnfinishedPartipPresent());
        CustomAssertion.softTrue("Unresulted referrals are not present", staffHomeForm.checkUnresReferTablePresent());
        CustomAssertion.softTrue("Current location head is not present", staffHomeForm.checkLocationHeadPresent());

        BaseNavigationSteps.logout();
        logEnd();
    }
}
