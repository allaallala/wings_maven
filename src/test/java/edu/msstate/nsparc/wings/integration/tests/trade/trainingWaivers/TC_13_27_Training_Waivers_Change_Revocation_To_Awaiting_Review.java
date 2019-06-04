package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.wings.integration.storage.TrainingWaiverObjects;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10832")
public class TC_13_27_Training_Waivers_Change_Revocation_To_Awaiting_Review extends BaseWingsTest {

    private static final Integer TEST_DATE = 26;
    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedExpiredTrainingWaiver();

        logStep("Log in as Trade Director and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.TRADEDIRECTOR);

        logStep("Add renewal with a date less than Waiver Expiration Date");
        TrainingWaiverDetailsForm detailsForm = new TrainingWaiverDetailsForm();
        detailsForm.expandWaiverRenewalsSection();
        // to perform Awaiting Review status we need to add a Renewal with special date:
        trainingWaiver.getRenewal().setRenewalDate(CommonFunctions.getDaysAgoDate(TEST_DATE));
        detailsForm.addRenewal(trainingWaiver);

        logStep("Check Training Waiver status changes to Awaiting Review");
        assertEquals("Status wasn't changed to Awaiting Review!", "Awaiting Review", detailsForm.getWaiverStatusText());
    }
}
