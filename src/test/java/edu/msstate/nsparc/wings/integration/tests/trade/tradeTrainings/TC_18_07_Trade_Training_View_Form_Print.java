package edu.msstate.nsparc.wings.integration.tests.trade.tradeTrainings;

import edu.msstate.nsparc.wings.integration.forms.tradeTraining.TradeTrainingDetailsForm;
import edu.msstate.nsparc.xray.info.TestCase;


/**
 * Created by a.vnuchko on 16.07.2015.
 * Open detailed page of the created trade training, check [Print] button is present.
 */

@TestCase(id = "WINGS-10930")
public class TC_18_07_Trade_Training_View_Form_Print extends TC_18_06_Trade_Trainings_View_Notes {

    public void main(){
        repeatedSteps();

        logStep("Check, that print button is present.");
        TradeTrainingDetailsForm detailsPage = new TradeTrainingDetailsForm();
        detailsPage.checkPrintPresent();
     }
}
