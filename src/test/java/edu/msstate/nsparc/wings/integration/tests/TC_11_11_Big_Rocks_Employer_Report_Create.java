package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.bigRocks.BigRocksEmployerReportCreationForm;
import edu.msstate.nsparc.wings.integration.forms.bigRocks.BigRocksEmployerReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10741")
public class TC_11_11_Big_Rocks_Employer_Report_Create extends BaseWingsTest {

    public void main() {

        logStep("Login as staff");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Referrals->Big rocks->Employer->Report");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_BR_EMPLOYER_REPORT);

        logStep("Create big rocks employer report");
        BigRocksEmployerReportCreationForm bigRocksEmployerReportCreationForm = new BigRocksEmployerReportCreationForm();
        bigRocksEmployerReportCreationForm.clickButton(Buttons.Create);
        BigRocksEmployerReportForm bigRocksEmployerReportForm = new BigRocksEmployerReportForm();
        assertTrue("Assert report frame is present failed", bigRocksEmployerReportForm.isReportPresent());
    }
}
