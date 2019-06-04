package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.wings.integration.storage.TrainingWaiverObjects;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10819")
public class TC_13_14_Training_Waivers_TE_Deadline_Date extends BaseWingsTest {

    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedTrainingWaiver();

        logStep("Log in as Admin and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.ADMIN);

        logStep("Open Edit Training Waiver form");
        TrainingWaiverDetailsForm trainingWaiverDetailsForm = new TrainingWaiverDetailsForm();
        String tradeDeadline = trainingWaiverDetailsForm.getTradeEnrlDeadline();
        trainingWaiverDetailsForm.editWaiver();
        TrainingWaiverEditForm editForm = new TrainingWaiverEditForm();

        logStep("Validate TE deadline date is equal to the Latest Possible Issue Date of Training Waiver");
        String issueDate = editForm.getPossibleDateText().replaceAll(" - [\\w ]+", "");
        info("Trade Enrollment deadline date is: " + tradeDeadline);
        info("Latest Possible Issue Date of Training Waiver is: " + issueDate);
        assertEquals("Trade Enrollment Deadline is not equal to Training Waiver Issue Date!", tradeDeadline, issueDate);
    }
}
