package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverCreationForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10806")
public class TC_13_01_Training_Waivers_Validate_Required_Fields extends BaseWingsTest {

    public void main() {

        TradeEnrollment tradeEnrollment = TradeEnrollmentObjects.getCreatedTradeEnrollment();

        logStep("Log in as Staff and open Training Waiver creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Create);

        logStep("Click 'Create' button");
        TrainingWaiverCreationForm trainingWaiverCreationForm = new TrainingWaiverCreationForm();
        trainingWaiverCreationForm.clickButton(Buttons.Create);

        logStep("Validate creation form with error message is displayed");
        //trainingWaiverCreationForm.assertIsOpen();
        trainingWaiverCreationForm.checkErrorsPresent();

        logStep("Select participant and trade enrollment");
        trainingWaiverCreationForm.selectParticipantAndTradeEnrollment(tradeEnrollment.getParticipant());

        logStep("Click 'Create' button");
        trainingWaiverCreationForm.clickButton(Buttons.Create);

        logStep("Validate creation form with error message is displayed");
        //trainingWaiverCreationForm.assertIsOpen();
        trainingWaiverCreationForm.checkErrorsPresent();
    }
}
