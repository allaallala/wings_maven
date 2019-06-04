package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.wings.integration.storage.TrainingWaiverObjects;
import edu.msstate.nsparc.xray.info.TestCase;
import org.testng.Assert;


@TestCase(id = "WINGS-10828")
public class TC_13_23_Training_Waivers_Cancel_Editing_Revocation extends BaseWingsTest {

    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedTrainingWaiverWithRevocation();

        logStep("Log in as Trade Director and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.TRADEDIRECTOR);

        logStep("Expand Revocation section and click 'Edit' button");
        TrainingWaiverDetailsForm waiverDetailsForm = new TrainingWaiverDetailsForm();
        waiverDetailsForm.expandWaiverRevocationSection();
        waiverDetailsForm.clickRevocation();
        waiverDetailsForm.editRevocation("Is Employed");

        logStep("Edit Revocation form and click 'Cancel' button");
        waiverDetailsForm.clickButton(Buttons.Cancel);
        waiverDetailsForm.areYouSure(Popup.Yes);

        logStep("Make sure the Revocation Reason wasn't changed");
        waiverDetailsForm.expandWaiverRevocationSection();
        Assert.assertTrue(waiverDetailsForm.getRevocationsText().contains(trainingWaiver.getRevocation().getRevocationReason()));
    }
}
