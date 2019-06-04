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
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10831")
public class TC_13_26_Training_Waivers_Change_Revocation_To_Denied extends BaseWingsTest {
    String deniedStatus = "Denied";

    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedExpiredTrainingWaiver();

        logStep("Log in as Trade Director and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.TRADEDIRECTOR);

        logStep("Click Edit button");
        TrainingWaiverDetailsForm detailsForm = new TrainingWaiverDetailsForm();
        detailsForm.editWaiver();

        logStep("Select Ineligible radio button and fill out required fields");
        TrainingWaiverEditForm editForm = new TrainingWaiverEditForm();
        editForm.clickIneligible();
        editForm.checkFirstIneligibleReason();
        editForm.clickButton(Buttons.Save);

        logStep("Check status changes to Denied. Denial section is updated.");
        CustomAssertion.softAssertEquals(detailsForm.getWaiverStatusText(), deniedStatus, "Waiver status wasn't changed to Denied");
        CustomAssertion.softTrue("Incorrect denial reason", detailsForm.getDenialReasonText().contains(trainingWaiver.getDenialReason()));
    }
}
