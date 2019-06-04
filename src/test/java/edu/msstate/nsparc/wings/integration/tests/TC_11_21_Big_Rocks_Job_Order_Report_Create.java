package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.bigRocks.BigRocksJobOrderReportCreationForm;
import edu.msstate.nsparc.wings.integration.forms.bigRocks.BigRocksJobOrderReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10750")
public class TC_11_21_Big_Rocks_Job_Order_Report_Create extends BaseWingsTest {

    public void main() {

        logStep("Open dashboard");
        TC_04_01_Open_Dashboard openDashboardTest = new TC_04_01_Open_Dashboard();
        openDashboardTest.main();

        logStep("Referrals->Big rocks->Job order->Report");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_BR_JOB_ORDER_REPORT);

        logStep("Create big rock job order");
        BigRocksJobOrderReportCreationForm bigRocksJobOrderReportCreationForm = new BigRocksJobOrderReportCreationForm();
        bigRocksJobOrderReportCreationForm.clickButton(Buttons.Create);
        BigRocksJobOrderReportForm bigRocksJobOrderReportForm = new BigRocksJobOrderReportForm();
        assertTrue("Assert report frame is present failed", bigRocksJobOrderReportForm.isReportPresent());
    }
}
