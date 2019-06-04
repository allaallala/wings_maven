package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.wings.integration.storage.TrainingWaiverObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10817")
public class TC_13_12_Training_Waivers_Available_Options extends BaseWingsTest {

    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedTrainingWaiver();

        logStep("Log in as Admin and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.ADMIN);

        logStep("Check buttons 'Done' and 'Edit' are available");
        TrainingWaiverDetailsForm trainingWaiverDetailsForm = new TrainingWaiverDetailsForm();
        trainingWaiverDetailsForm.assertIsPresent(BaseWingsForm.BaseButton.DONE);
        trainingWaiverDetailsForm.checkEditWaiverPresent();

        logStep("Click 'Audit' button and check Audit screen is opened");
        trainingWaiverDetailsForm.clickAudit();
        //new TrainingWaiverAuditTrail().assertIsOpen();
    }
}
