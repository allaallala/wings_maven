package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.trainingWaiver.TrainingWaiverDetailsForm;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.wings.integration.steps.TrainingWaiverSteps;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10835")
public class TC_13_30_Training_Waivers_Add_Max_Renewals_For_nonARRA extends BaseWingsTest {

    private static final String MAX_RENEWALS_NUMBER = "5";

    public void main() {

        TradeEnrollment tradeEnrollment = new TradeEnrollment(PetitionType.ATAA_LOW);
        TrainingWaiver trainingWaiver = new TrainingWaiver(tradeEnrollment);
        TrainingSteps.createTrainingWaiver(trainingWaiver);

        logStep("Log in as Trade Director and open Training Waiver");
        TrainingWaiverSteps.openTrainingWaiverDetailPage(trainingWaiver, Roles.TRADEDIRECTOR);

        logStep("Expand Renewals section and check the 'Number of Allowed Renewals Remaining' is '5'");
        TrainingWaiverDetailsForm waiverDetailsForm = new TrainingWaiverDetailsForm();
        waiverDetailsForm.expandWaiverRenewalsSection();
        assertEquals("Max number of Renewals is not '5'!", MAX_RENEWALS_NUMBER, waiverDetailsForm.getNumberRenewalsRemaining());
    }
}
