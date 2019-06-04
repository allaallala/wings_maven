package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.bigRocks.BigRocksReferralsReportCreationForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10752")
public class TC_11_41_Big_Rocks_Referrals_Report_Create extends BaseWingsTest {

    public void main() {
        logStep("Login to the system as Staff");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Referrals->Big rocks->Report");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_BR_REFERRAL_REPORT);

        logStep("Create new report");
        BigRocksReferralsReportCreationForm bigRocksReferralsReportCreationForm = new BigRocksReferralsReportCreationForm();
        bigRocksReferralsReportCreationForm.clickButton(Buttons.Create);
        assertTrue("Assert report frame is present failed", bigRocksReferralsReportCreationForm.isReportPresent());
    }
}
