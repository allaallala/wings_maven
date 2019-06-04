package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Create expired training waivers, try to add revocations. Check, that it is impossible
 * Created by a.vnuchko on 23.10.2015.
 */

@TestCase(id = "WINGS-10912")
public class TC_17_08_Training_Waivers_Expired extends BaseWingsTest {

    public void main(){


        info("Precondition: There is TW with status: Expired.");
        TrainingWaiver waiver = new TrainingWaiver();
        waiver.initializeExpired();
        TrainingSteps.createTrainingWaiver(waiver);

        TrainingWaiverSteps.openTrainingWaiverDetailPage(waiver, Roles.TRADEDIRECTOR);

        logStep("Try to add revocations");
        TrainingWaiverDetailsForm detailsPage = new TrainingWaiverDetailsForm();

        logResult("It ' s impossible to manage revocations: buttons 'Add', 'Edit', 'Remove' are unavailable");
        detailsPage.expandWaiverRevocationSection();
        detailsPage.addRevocation(waiver);
        detailsPage.validateRevocationsBlock(waiver);
    }
}
