package edu.msstate.nsparc.wings.integration.forms.wiaTraining;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.ComboBox;
import org.openqa.selenium.By;

/**
 * This is the base form for WIA Training forms
 */
public class WIATrainingBaseForm extends BaseWingsForm {

    private ComboBox cmbResultTraining = new ComboBox("css=select[id='trainingResult']", "Result");

    /**
     * Constructor of the form with defined locator
     * @param locator - locator of the form
     */
    public WIATrainingBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Select result training
     * @param optionValue - value to select
     */
    public void selectResultTraining(String optionValue){
        cmbResultTraining.select(optionValue);
    }
}
