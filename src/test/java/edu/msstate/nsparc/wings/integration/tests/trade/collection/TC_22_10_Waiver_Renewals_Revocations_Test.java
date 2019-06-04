package edu.msstate.nsparc.wings.integration.tests.trade.collection;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverSearchForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Check, that preview for the waiver renewals and revocations can be opened from the training waiver and contains correct information
 * Created by a.vnuchko on 14.10.2015.
 */

@TestCase(id = "WINGS-11034")
public class TC_22_10_Waiver_Renewals_Revocations_Test extends BaseWingsTest {

    public void main() {
        TrainingWaiver waiv = new TrainingWaiver();
        waiv.initializeExpired();
        waiv.getRenewal().setRenewalDate(CommonFunctions.getDaysAgoDate(Constants.DAYS_AGO_WAIVER));
        waiv.getRevocation().setRevocationDate(CommonFunctions.getDaysAgoDate(Constants.DAYS_AGO_WAIVER));
        TrainingSteps.createTrainingWaiverWithRenewalRevocation(waiv);

        logStep("Log in WINGS and open required page");
        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.P_TRADE_TRAINING_WAIVERS, Popup.Search);

        logStep("Check that the preview can be successfully opened for the Waiver Renewals and Revocations "
                + "(from the Training Waiver participantSSDetails page) and contains the correct information");
        TrainingWaiverSearchForm searchPage = new TrainingWaiverSearchForm();
        searchPage.performSearchAndOpen(waiv);

        TrainingWaiverDetailsForm detailsPage = new TrainingWaiverDetailsForm();
        detailsPage.validateRenewalsRevocations(waiv);
    }
}
