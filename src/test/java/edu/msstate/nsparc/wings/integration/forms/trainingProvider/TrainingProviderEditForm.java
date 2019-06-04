package edu.msstate.nsparc.wings.integration.forms.trainingProvider;

import edu.msstate.nsparc.wings.integration.functions.dbFunctions.AdvancedSqlFunctions;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import org.openqa.selenium.By;

/**
 * This form is only available for Project Code Admin
 * This form is opened via Advanced -> Training Providers -> Search for record -> Open record -> Click on Edit button
 */
public class TrainingProviderEditForm extends TrainingProviderBaseForm {

    /**
     * Default constructor
     */
    public TrainingProviderEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Training Provider Edit')]"), "Training Provider Edit");
    }

    /**
     * This method is used for changing Training provider basic information
     *
     * @param trainingProvider New values for Training Provider
     */
    public void editTrainingProviderDetails(TrainingProvider trainingProvider) {
        AdvancedSqlFunctions.resetTrainingProviderCode(trainingProvider.getCode());
        inputProviderName(trainingProvider.getName());
        inputProviderCode(trainingProvider.getCode());
        inputVendorNumber(trainingProvider.getDfa());
    }
}
