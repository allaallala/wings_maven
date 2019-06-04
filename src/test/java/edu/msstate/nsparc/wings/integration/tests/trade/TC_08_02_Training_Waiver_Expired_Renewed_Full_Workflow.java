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
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;


@TestCase(id = "WINGS-10702")
public class TC_08_02_Training_Waiver_Expired_Renewed_Full_Workflow extends BaseWingsTest {
    Integer agoDays = 20;
    public void main() {
        TrainingWaiver waiver = new TrainingWaiver();
        waiver.initializeExpired();
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

        logStep("Expand Waiver Renewals section");
        TrainingWaiverDetailsForm detailsForm = new TrainingWaiverDetailsForm();
        detailsForm.expandWaiverRenewalsSection();

        logStep("Add Renewal to a Waiver");
        waiver.getRenewal().setRenewalDate(CommonFunctions.getDaysAgoDate(agoDays));
        detailsForm.addRenewal(waiver);

        logStep("Make sure Waiver status has changed");
        detailsForm.validateInformation(waiver);

        logStep("Expand Waiver Renewals section");
        detailsForm.expandWaiverRenewalsSection();

        logStep("Make sure Renewal was added");
        CustomAssertion.softTrue("Incorrect initial renewal reason", detailsForm.getRenewals().contains(waiver.getRenewal().getRenewalReason()));

        logStep("Edit Renewal Reason");
        waiver.getRenewal().setRenewalReason("Training Will Start in 60 Days");
        detailsForm.editRenewal(waiver);

        logStep("Make sure the Renewal Reason was changed");
        detailsForm.expandWaiverRenewalsSection();
        CustomAssertion.softTrue("Incorrect  changed renewal reason", detailsForm.getRenewals().contains(waiver.getRenewal().getRenewalReason()));
    }
}

