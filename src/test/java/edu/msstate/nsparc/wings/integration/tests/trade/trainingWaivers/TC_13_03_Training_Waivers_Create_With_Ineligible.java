package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverCreationForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.storage.TradeEnrollmentObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10808")
public class TC_13_03_Training_Waivers_Create_With_Ineligible extends BaseWingsTest {


    public void main() {

        TradeEnrollment tradeEnrollment = TradeEnrollmentObjects.getCreatedTradeEnrollment();

        logStep("Log in as Staff and open Training Waiver creation form");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Create);

        logStep("Select Participant a nd Trade Enrollment");
        TrainingWaiverCreationForm trainingWaiverCreationForm = new TrainingWaiverCreationForm();
        trainingWaiverCreationForm.selectParticipantAndTradeEnrollment(tradeEnrollment.getParticipant());

        logStep("Select 'Ineligible' radio button");
        trainingWaiverCreationForm.clickIneligible();

        logStep("Validate the list of 'Other Reason(s) for Ineligibility' is displayed");
        trainingWaiverCreationForm.validateIneligibilityReasonsList();
    }
}
