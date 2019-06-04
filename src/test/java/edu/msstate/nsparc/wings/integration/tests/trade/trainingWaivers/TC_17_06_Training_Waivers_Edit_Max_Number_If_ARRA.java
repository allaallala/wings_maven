package edu.msstate.nsparc.wings.integration.tests.trade.trainingWaivers;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import edu.msstate.nsparc.wings.integration.steps.TrainingSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.CommonFunctions;


/**
 * Create training waiver with arra, change number of renewals and check, that changes are saved.
 * Created by a.vnuchko on 08.07.2015.
 */

@TestCase(id = "WINGS-10910")
public class TC_17_06_Training_Waivers_Edit_Max_Number_If_ARRA extends TC_17_05_Training_Waivers_Edit_Max_Number_Ifnon_ARRA {

    public void main() {
        TradeEnrollment tradeEnrollment = new TradeEnrollment(PetitionType.ATAA_HIGH);
        TrainingWaiver trainingWaiver = new TrainingWaiver(tradeEnrollment);
        trainingWaiver.initializeExpired();
        trainingWaiver.getRenewal().setRenewalDate(CommonFunctions.getDaysAgoDate(Constants.DAYS_AGO_WAIVER));
        trainingWaiver.getRevocation().setRevocationDate(CommonFunctions.getDaysAgoDate(Constants.DAYS_AGO_WAIVER));
        TrainingSteps.createTrainingWaiver(trainingWaiver);

        repeatedSteps(trainingWaiver);
    }
}
