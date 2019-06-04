package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.wings.integration.storage.TrainingWaiverObjects;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


@TestCase(id = "WINGS-10824")
public class TC_13_19_Training_Waivers_Edit_Remove_Renewals_Security extends BaseWingsTest {

    String date = CommonFunctions.getDaysAgoDate(20);

    public void main() {

        TrainingWaiver trainingWaiver = TrainingWaiverObjects.getCreatedTrainingWaiverWithRenewal();

        logStep("Log in as Trade Director and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.TRADEDIRECTOR);

        logStep("Expand Waiver Renewals section");
        TrainingWaiverDetailsForm detailsForm = new TrainingWaiverDetailsForm();
        detailsForm.expandWaiverRenewalsSection();

        logStep("Check 'Edit' and 'Remove' buttons are unavailable");
        detailsForm.checkEditRemoveRenewalsDisabledPresent(Constants.TRUE);
        trainingWaiver.getRenewal().setRenewalDate(date);
        detailsForm.addRenewal(trainingWaiver);

        logStep("Select any Waiver Renewal and check 'Edit' and 'Remove' buttons are available");
        detailsForm.expandWaiverRenewalsSection();
        detailsForm.clickRenewal();
        detailsForm.checkEditRemoveRenewalsDisabledPresent(Constants.TRUE);

        logStep("Click 'Remove' button to delete renewal");
        detailsForm.removeRenewal();
    }
}
