package edu.msstate.nsparc.wings.integration.forms.tradeTraining;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TradeTraining;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Trade -> Trade Training -> Search for record -> Open record -> Click on Edit button
 */
public class TradeTrainingEditForm extends TradeTrainingBaseForm {
    // items for Trade Training Edit Form
    private TextBox txbDateAnticipCompletetion = new TextBox("css=input[id='dateAnticipatedCompletion']", "Date anticipated completion");
    private TextBox txbFirstDayTraining = new TextBox("css=input[id='dateTrainingStart']", "First Day of Training");
    private TextBox txbLastDayOfTraining = new TextBox("css=input[id='dateTrainingEnd']", "Last Day of Training");
    private RadioButton rbResultPassed = new RadioButton("css=input[id='outcome1']", "Result - Passed");
    private RadioButton rbResultFailed = new RadioButton("css=input[id='outcome2']", "Result - Failed");

    /**
     * Default constructor
     */
    public TradeTrainingEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Trade Training Edit')]"), "Trade Training Edit");
    }

    /**
     * Editing Trade Training participantSSDetails
     * @param training Object with new participantSSDetails
     */
    public void editDetails(TradeTraining training) {
        cmbTrainingResult.select(training.getResult());
        if (Constants.COMPLETED.equalsIgnoreCase(training.getResult())) {
            txbLastDayOfTraining.type(training.getLastDayOfTraining());
            if (training.isResultPassed()) {
                rbResultPassed.click();
            } else {
                rbResultFailed.click();
            }
        }
    }

    /**
     * Input date anticipated completion
     * @param date - date
     */
    public void inputDateCompletion(String date){
        txbDateAnticipCompletetion.type(date);
    }

    /**
     * Input first day of training
     * @param date - day of training.
     */
    public void inputFirstDayTraining(String date){
        txbFirstDayTraining.type(date);
    }
}
