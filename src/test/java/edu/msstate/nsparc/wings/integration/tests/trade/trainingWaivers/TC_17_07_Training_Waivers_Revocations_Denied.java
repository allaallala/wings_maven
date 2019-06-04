package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create training waiver with status denied and check, that is impossible to add renewals, revocations
 * Created by a.vnuchko on 22.10.2015.
 */

@TestCase(id = "WINGS-10911")
public class TC_17_07_Training_Waivers_Revocations_Denied extends BaseWingsTest {
    private String status = "Denied";

    public void main() {


        info("Precondition: There is TW with status: Denied.");
        TrainingWaiver trainingWaiver = new TrainingWaiver();
        trainingWaiver.setEligible(false);
        trainingWaiver.setWaiverStatus(status);
        TrainingSteps.createTrainingWaiver(trainingWaiver);

        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.TRADEDIRECTOR);

        logStep("Try to add renewals and revocations");
        TrainingWaiverDetailsForm detailsPage = new TrainingWaiverDetailsForm();

        logResult("It is not possible to manage revocations and renewals: buttons Add, Edit, Remove are unavailable");
        detailsPage.validateImpossibleRenewRevoc();

    }
}
