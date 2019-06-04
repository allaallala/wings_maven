package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.dataIntegrity.DataIntegrityTrainingWaiverReportForm;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsMenuItem;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.BaseNavigationSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10763")
public class TC_12_02_Data_Integrity_Training_Waiver extends BaseWingsTest {

    private static final String EXPIRED_TYPE = "Expired Waivers";
    private static final String FIRST_NAME = "Auto";
    private static final String LAST_NAME = "Admin";

    public void main() {
        TrainingWaiver waiver = new TrainingWaiver();
        waiver.initializeExpired();
        TrainingSteps.createTrainingWaiver(waiver);

        logStep("Log in WINGS as Admin");
        BaseNavigationSteps.loginAdminDashboard();

        logStep("Reports->Data integrity->Training Waiver report");
        new StaffHomeForm().clickMenu(WingsTopMenu.WingsStaffMenuItem.R_DI_TRAINING_WAIVER_REPORT);

        logStep("Select report type");
        DataIntegrityTrainingWaiverReportForm reportForm = new DataIntegrityTrainingWaiverReportForm();
        reportForm.selectReportType(EXPIRED_TYPE);

        logStep("Select Staff from lookup");
        reportForm.checkLimit();

        logStep("Click Search");
        reportForm.clickButton(Buttons.Search);

        logStep("Validate search results");
        reportForm.validateParticipantSearchReverse(waiver.getTradeEnrollment().getParticipant());
    }
}
