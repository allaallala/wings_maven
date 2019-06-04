package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.wings.integration.storage.TrainingWaiverObjects;
import edu.msstate.nsparc.xray.info.TestCase;
import org.testng.Assert;


@TestCase(id = "WINGS-10826")
public class TC_13_21_Training_Waivers_Add_Revocation_To_Renewed extends BaseWingsTest {

    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedTrainingWaiverWithRenewal();

        logStep("Log in as Staff and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.STAFF);

        logStep("Expand Revocation section and add a revocation");
        TrainingWaiverDetailsForm waiverDetailsForm = new TrainingWaiverDetailsForm();
        waiverDetailsForm.expandWaiverRevocationSection();
        waiverDetailsForm.addRevocation(trainingWaiver);

        logStep("Make sure Waiver status has changed");
        waiverDetailsForm.validateInformation(trainingWaiver);

        logStep("Expand Waiver Revocations section");
        waiverDetailsForm.expandWaiverRevocationSection();

        logStep("Make sure Revocation was added");
        Assert.assertTrue(waiverDetailsForm.getRevocationsText().contains(trainingWaiver.getRevocation().getRevocationReason()));
    }
}
