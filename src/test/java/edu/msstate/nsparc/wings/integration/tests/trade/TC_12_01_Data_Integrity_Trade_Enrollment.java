package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.dataIntegrity.DataIntegrityTradeEnrollmentReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.functions.dbFunctions.ParticipantSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10761")
public class TC_12_01_Data_Integrity_Trade_Enrollment extends BaseWingsTest {

    String status = "Status Not Determined";

    public void main() {
        TradeEnrollment tradeEnrollment = new TradeEnrollment();
        TradeEnrollmentSteps.createTradeEnrollment(tradeEnrollment, Roles.ADMIN);
        info("Reset Trade Enrollment status");
        ParticipantSqlFunctions.resetTradeEnrollmentStatus(tradeEnrollment);

        logStep("Log in WINGS as Admin");
        BaseWingsSteps.logInAs(Roles.WIOA);

        logStep("Reports->Data integrity->Trade Enrollment report");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_TRADE_ENROLLMENT_REPORT);

        logStep("Select report type");
        DataIntegrityTradeEnrollmentReportForm reportForm = new DataIntegrityTradeEnrollmentReportForm();
        reportForm.selectReportType(status);

        logStep("Select Petitions from lookup");
        reportForm.selectPetition(tradeEnrollment.getPetition());

        logStep("Click Search");
        reportForm.clickButton(Buttons.Search);

        logStep("Validate search results");
        reportForm.validateParticipantSearchResults(tradeEnrollment.getParticipant());
    }
}
