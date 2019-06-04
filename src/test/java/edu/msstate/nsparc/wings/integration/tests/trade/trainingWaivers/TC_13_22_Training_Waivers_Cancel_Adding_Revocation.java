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


@TestCase(id = "WINGS-10827")
public class TC_13_22_Training_Waivers_Cancel_Adding_Revocation extends BaseWingsTest {

    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedTrainingWaiver();

        logStep("Log in as Staff and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.STAFF);

        logStep("Expand Revocation section and fill out Revoke form");
        TrainingWaiverDetailsForm waiverDetailsForm = new TrainingWaiverDetailsForm();
        waiverDetailsForm.expandWaiverRevocationSection();
        waiverDetailsForm.clickAddRevocation();
        waiverDetailsForm.fillOutRevokeTrainingWaiverForm(trainingWaiver);

        logStep("Click 'Cancel' button");
        waiverDetailsForm.clickButton(Buttons.Cancel);
        waiverDetailsForm.areYouSure(Popup.Yes);

        logStep("Check Revocation wasn't created");
        waiverDetailsForm.expandWaiverRevocationSection();
        waiverDetailsForm.noSearchResults();
    }
}
