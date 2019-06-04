package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.wings.integration.storage.TrainingWaiverObjects;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10822")
public class TC_13_17_Training_Waivers_Positive_Validation extends BaseWingsTest {

    private static final String NEW_ISSUE_DATE = CommonFunctions.getDaysAgoDate(44);

    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedTrainingWaiver();

        logStep("Log in as Trade Director and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.TRADEDIRECTOR);

        logStep("Click Edit button");
        TrainingWaiverDetailsForm trainingWaiverDetailsForm = new TrainingWaiverDetailsForm();
        trainingWaiverDetailsForm.editWaiver();

        logStep("Input different correct date in Issue Date field");
        TrainingWaiverEditForm trainingWaiverEditForm = new TrainingWaiverEditForm();
        trainingWaiver.setIssueDate(NEW_ISSUE_DATE);
        trainingWaiverEditForm.inputIssueDate(trainingWaiver.getIssueDate());

        logStep("Save and check changes were saved");
        trainingWaiverEditForm.clickButton(Buttons.Save);
        trainingWaiverEditForm.passParticipationRecalculationPage();
        assertEquals("Issue Date assertion failed!", trainingWaiver.getIssueDate(), trainingWaiverDetailsForm.getIssueDateText());
    }
}
