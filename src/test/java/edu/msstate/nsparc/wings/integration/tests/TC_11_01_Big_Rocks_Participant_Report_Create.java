package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.bigRocks.BigRocksParticipantReportCreationForm;
import edu.msstate.nsparc.wings.integration.forms.bigRocks.BigRocksParticipantReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;

import static org.testng.AssertJUnit.assertTrue;

@TestCase(id = "WINGS-10732")
public class TC_11_01_Big_Rocks_Participant_Report_Create extends BaseWingsTest {

    public void main() {
        logStep("Log in WINGS");
        BaseWingsSteps.logInAs(Roles.STAFF);

        logStep("Navigate to Participant Big Rocks report");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_BR_PARTICIPANT_REPORT);

        logStep("Select start date");
        BigRocksParticipantReportCreationForm bigRocksCreationForm = new BigRocksParticipantReportCreationForm();
        bigRocksCreationForm.inputStartDate(CommonFunctions.getCurrentDate());

        logStep("Click on Create button");
        bigRocksCreationForm.clickButton(Buttons.Create);

        logStep("Validate that Report is displayed");
        BigRocksParticipantReportForm participantReport = new BigRocksParticipantReportForm();
        assertTrue("Assert Participant Report frame is present failed", participantReport.isReportPresent());
    }
}
