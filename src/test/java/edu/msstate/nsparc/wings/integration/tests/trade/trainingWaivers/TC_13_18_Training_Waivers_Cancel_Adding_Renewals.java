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


@TestCase(id = "WINGS-10823")
public class TC_13_18_Training_Waivers_Cancel_Adding_Renewals extends BaseWingsTest {

    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedExpiredTrainingWaiver();

        logStep("Log in as Trade Director and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.TRADEDIRECTOR);

        logStep("Expand Waiver Renewals section");
        TrainingWaiverDetailsForm detailsForm = new TrainingWaiverDetailsForm();
        detailsForm.expandWaiverRenewalsSection();

        logStep("Click Add button in Renewals section");
        detailsForm.clickAddRenewal();

        logStep("Fill out fields");
        detailsForm.inputRenewalReasonDate(trainingWaiver.getRenewal().getRenewalDate(), trainingWaiver.getRenewal().getRenewalReason());

        logStep("Click Cancel button");
        detailsForm.clickButton(Buttons.Cancel);
        detailsForm.areYouSure(Popup.Yes);

        logStep("Check Renewals wasn't created");
        detailsForm.expandWaiverRenewalsSection();
        detailsForm.noSearchResults();
    }
}
