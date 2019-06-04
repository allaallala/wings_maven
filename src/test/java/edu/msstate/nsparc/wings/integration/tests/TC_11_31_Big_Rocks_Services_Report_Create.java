package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.bigRocks.BigRocksServicesReportCreationForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10751")
public class TC_11_31_Big_Rocks_Services_Report_Create extends BaseWingsTest {

    public void main() {

        logStep("Login as staff");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Referrals->Big rocks->Services->Report");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_BR_SERVICES_REPORT);

        logStep("Create big rocks services report");
        BigRocksServicesReportCreationForm bigRocksServicesReportCreationForm = new BigRocksServicesReportCreationForm();
        bigRocksServicesReportCreationForm.clickButton(Buttons.Create);
        bigRocksServicesReportCreationForm.isReportPresent();
    }
}
