package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.wings.integration.storage.TrainingWaiverObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10820")
public class TC_13_15_Training_Waivers_Printing_Forms extends BaseWingsTest {

    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedTrainingWaiver();

        logStep("Log in as Admin and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.ADMIN);

        logStep("Check Print buttons are available for each form");
        TrainingWaiverDetailsForm trainingWaiverDetailsForm = new TrainingWaiverDetailsForm();
        // unable to check printing using selenium. Just check print buttons are available
        trainingWaiverDetailsForm.checkPrintPresent();
    }
}
