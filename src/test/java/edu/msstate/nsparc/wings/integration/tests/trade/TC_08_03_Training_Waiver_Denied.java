package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10704")
public class TC_08_03_Training_Waiver_Denied extends BaseWingsTest {

    public void main() {
        TrainingWaiver waiver = new TrainingWaiver();
        TradeEnrollmentSteps.createTradeEnrollment(waiver.getTradeEnrollment(), Roles.ADMIN);
        waiver.setEligible(false);
        waiver.setWaiverStatus("Denied");

        logStep("Log in as Admin and open Training Waiver creation page");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Create);

        logStep("Fill out required fields, complete creation and check new Training Waiver");
        TrainingWaiverSteps.completeTrainingWaiverCreationAndValidate(waiver);
    }
}
