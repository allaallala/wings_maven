package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.wings.integration.storage.TrainingWaiverObjects;
import edu.msstate.nsparc.xray.info.TestCase;


@TestCase(id = "WINGS-10833")
public class TC_13_28_Training_Waivers_Add_Renewal_Revocation_To_Denied extends BaseWingsTest {

    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedIneligibleTrainingWaiver();

        logStep("Log in as Trade Director and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.TRADEDIRECTOR);

        logStep("Check it's impossible to manage renewals - Edit, Add, Remove button are are absent");
        TrainingWaiverDetailsForm detailsForm = new TrainingWaiverDetailsForm();
        detailsForm.validateImpossibleRenewals();

        logStep("Check it's impossible to manage revocations - Edit, Add, Remove button are are absent");
        detailsForm.validateImpossibleRevocations();
    }
}
