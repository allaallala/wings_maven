package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.wings.integration.storage.TrainingWaiverObjects;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10821")
public class TC_13_16_Training_Waivers_Cancel_Edit extends BaseWingsTest {

    private static final String NEW_WAIVER_REASON = "Funds Not Available or at a Reasonable Cost";

    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedTrainingWaiver();

        logStep("Log in as Trade Director and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.TRADEDIRECTOR);

        logStep("Click Edit button");
        TrainingWaiverDetailsForm trainingWaiverDetailsForm = new TrainingWaiverDetailsForm();
        trainingWaiverDetailsForm.editWaiver();

        logStep("Edit any field and click Cancel");
        TrainingWaiverEditForm trainingWaiverEditForm = new TrainingWaiverEditForm();
        trainingWaiverEditForm.editWaiverReason(NEW_WAIVER_REASON);
        trainingWaiverEditForm.clickButton(Buttons.Cancel);
        trainingWaiverEditForm.areYouSure(Popup.Yes);

        logStep("Validate the changes are not saved");
        assertEquals("Waiver Reason assertion failed!", trainingWaiver.getWaiverReason(), trainingWaiverDetailsForm.getReasonWaiverText());
    }
}
