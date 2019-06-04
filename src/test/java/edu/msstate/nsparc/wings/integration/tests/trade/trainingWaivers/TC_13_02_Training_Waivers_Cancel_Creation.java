package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.home.StaffHomeForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverCreationForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10807")
public class TC_13_02_Training_Waivers_Cancel_Creation extends BaseWingsTest {

    public void main() {

        TradeEnrollment tradeEnrollment = TradeEnrollmentObjects.getCreatedTradeEnrollment();

        logStep("Log in as Staff and open Training Waiver creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Create);

        logStep("Fill out required fields");
        TrainingWaiverCreationForm trainingWaiverCreationForm = new TrainingWaiverCreationForm();
        trainingWaiverCreationForm.selectParticipantAndTradeEnrollment(tradeEnrollment.getParticipant());

        logStep("Click 'Cancel' and confirm canceling");
        trainingWaiverCreationForm.clickButton(Buttons.Cancel);
        trainingWaiverCreationForm.areYouSure(Popup.Yes);

        logStep("The Staff Home screen should be shown");
        new StaffHomeForm();
    }
}
