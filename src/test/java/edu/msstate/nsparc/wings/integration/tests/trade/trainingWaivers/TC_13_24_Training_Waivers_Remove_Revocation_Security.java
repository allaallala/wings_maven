package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.wings.integration.storage.TrainingWaiverObjects;
import edu.msstate.nsparc.xray.info.TestCase;
import org.testng.Assert;


@TestCase(id = "WINGS-10829")
public class TC_13_24_Training_Waivers_Remove_Revocation_Security extends BaseWingsTest {

    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedTrainingWaiverWithRevocation();

        logStep("Log in as Trade Director and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.TRADEDIRECTOR);

        logStep("Expand Revocation section and check 'Edit' and 'Remove' buttons are unavailable");
        TrainingWaiverDetailsForm waiverDetailsForm = new TrainingWaiverDetailsForm();
        waiverDetailsForm.expandWaiverRevocationSection();
        waiverDetailsForm.checkEditRemoveRevocationDisabledPresent(Constants.TRUE);

        logStep("Select any revocation item and check 'Edit' and 'Remove' buttons are available");
        waiverDetailsForm.clickRevocation();
        waiverDetailsForm.checkEditRemoveRevocationDisabledPresent(Constants.FALSE);

        logStep("Click 'Remove' button to delete revocation");
        waiverDetailsForm.removeRevocation();

        logStep("Check that waiver status was changed to 'Expired'");
        Assert.assertTrue(waiverDetailsForm.getWaiverStatusText().contains("Expired"));
    }
}
