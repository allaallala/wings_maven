package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverEditForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Created by a.vnuchko on 08.07.2015.
 * Training Waivers: Renewals - Edit: Maximum number of Renewals if non-ARRA
 */

@TestCase(id = "WINGS-10909")
public class TC_17_05_Training_Waivers_Edit_Max_Number_Ifnon_ARRA extends BaseWingsTest {
    private static final String RENEWALS_REMAINING = "1";
    private static final String RENEWALS_ZERO = "0";

    public void main(){
        info("Precondition: Precondition: Create some Training Waivers  (non-ARRA) ");
        TradeEnrollment tradeEnrollment = new TradeEnrollment(PetitionType.ATAA_LOW);
        TrainingWaiver trainingWaiver = new TrainingWaiver(tradeEnrollment);
        trainingWaiver.getRenewal().setRenewalDate(CommonFunctions.getDaysAgoDate(TrainingWaiver.RENEWAL_DATE_START));
        TrainingSteps.createTrainingWaiver(trainingWaiver);

        repeatedSteps(trainingWaiver);
    }

    /**
     * Represents some steps that are repeated in the other test case
     * @param trainingWaiver - training waiver
     */
    protected void repeatedSteps(TrainingWaiver trainingWaiver){
        logStep("Log in as Staff and open Training Waiver Details form");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.TRADEDIRECTOR);

        logStep("Click the 'Edit'.");
        TrainingWaiverDetailsForm detailsPage = new TrainingWaiverDetailsForm();
        detailsPage.editWaiver();

        logStep("Change value in the 'Number of Allowed Renewals Remaining': enter any value (N). Save changes.");
        TrainingWaiverEditForm editPage = new TrainingWaiverEditForm();
        editPage.changeRenewalRemaining(RENEWALS_REMAINING);
        editPage.clickButton(Buttons.Save);

        logStep("Verify that maximum number of Renewals is N");
        detailsPage.validateAllowedRenewals(RENEWALS_REMAINING);

        logResult("Maximum number of Renewals is N. Button 'Add' is unavailable after adding Nth renewal");
        detailsPage.addRenewal(trainingWaiver);
        detailsPage.validateAllowedRenewals(RENEWALS_ZERO);
        detailsPage.validateDisabledRenewals();
    }
}
