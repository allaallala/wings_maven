package edu.msstate.nsparc.wings.integration.tests.trade;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TradeEnrollmentSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10451")
public class TC_08_01_Training_Waiver_Issued_Revoked_Full_Workflow extends BaseWingsTest {

    public void main() {
        TrainingWaiver waiver = new TrainingWaiver();
        TradeEnrollmentSteps.createTradeEnrollment(waiver.getTradeEnrollment(), Roles.ADMIN);

        logStep("Log in as Admin and open Training Waiver creation page");
        BaseWingsSteps.openCreationSearchForm(Roles.ADMIN, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Create);

        logStep("Fill out required fields, complete creation and check new Training Waiver");
        TrainingWaiverSteps.completeTrainingWaiverCreationAndValidate(waiver);

        logStep("Open Waiver detail page using 'Search'");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(waiver);

        logStep("Edit Training Waiver fields and validate new info");
        waiver.setWaiverReason("Funds Not Available or at a Reasonable Cost");
        TrainingWaiverSteps.editTrainingWaiverAndValidate(waiver);

        logStep("Expand Waiver Revocations section");
        TrainingWaiverDetailsForm detailsForm = new TrainingWaiverDetailsForm();
        detailsForm.expandWaiverRevocationSection();

        logStep("Add Revocation to a Waiver");
        detailsForm.addRevocation(waiver);

        logStep("Make sure Waiver status has changed");
        detailsForm.validateInformation(waiver);

        logStep("Expand Waiver Revocations section");
        detailsForm.expandWaiverRevocationSection();

        logStep("Make sure Revocation was added");
        CustomAssertion.softTrue("Incorrect initial revocation reason",
                detailsForm.getRevocationsText().contains(waiver.getRevocation().getRevocationReason()));

        logStep("Edit Revocation Reason");
        waiver.getRevocation().setRevocationReason("Is Employed");
        detailsForm.editRevocation(waiver);

        logStep("Make sure the Revocation Reason was changed");
        detailsForm.expandWaiverRevocationSection();
        CustomAssertion.softTrue("Incorrect changed revocation reason",
                detailsForm.getRevocationsText().contains(waiver.getRevocation().getRevocationReason()));
    }
}
