package edu.msstate.nsparc.wings.integration.forms.trainingProvider;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProviderLocation;
import framework.elements.Button;
import framework.elements.CheckBox;
import org.openqa.selenium.By;

/**
 * This form is only available for Project Code Admin
 * This form is opened via Advanced -> Training Providers -> Create
 */
public class TrainingProviderCreationForm extends TrainingProviderBaseForm {

    private CheckBox cbPdo = new CheckBox("//input[@name='isPDO']", "Professional Development Opportunity Provider");
    private Button btnAddLocation = new Button("id=addLocation", "Add Location");
    private Button btnDisable = new Button("//a/img[@title='Disable']", "Disable");
    private Button btnEnable = new Button("//a/img[@title='Enable']", "Enable");
    private Button btnDisabledEdit = new Button("//button[@disabled='disabled'][contains(.,'Edit')]", "Disabled Edit");
    private Button btnDisabledRemove = new Button("//button[@disabled='disabled'][contains(.,'Remove')]", "Disabled Edit");

    private String[] codes = {"a", "b", "z", "g", "y"};

    /**
     * Default constructor.
     */
    public TrainingProviderCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Training Provider Creation')]"), "Training provider creation");
    }

    /**
     * Click [Enable]
     */
    public void clickEnable() {
        btnEnable.isPresent();
        btnEnable.click();
    }

    /**
     * Click [Add location] button.
     */
    public void clickLocationAdd() {
        btnAddLocation.clickAndWait();
    }

    /**
     * Click [Disable]
     */
    public void clickDisable() {
        btnDisable.click();
    }

    /**
     * Check [Disable] button is present
     */
    public void checkDisable() {
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        btnDisable.isPresent();
    }

    /**
     * Click check box PDO.
     */
    public void clickPdo() {
        cbPdo.click();
    }


    /**
     * Check, that [Edit] and [Remove] buttons are unavailable to click
     */
    public void checkEditRemoveDisabled() {
        btnDisabledEdit.isPresent();
        btnDisabledRemove.isPresent();
    }

    /**
     * This method is used for filling text fields at the top of Creation form
     *
     * @param trainingProvider Training provider object with data for creation
     */
    public void fillTextFields(TrainingProvider trainingProvider) {
        inputProviderName(trainingProvider.getName());
        if (txbTrainingProviderCode.isPresent()) {
            inputProviderCode(trainingProvider.getCode());
        }
        inputFein(trainingProvider.getFein());
        inputVendorNumber(trainingProvider.getDfa());
    }

    /**
     * This method is used for filling all fields required for Training Provider creation
     *
     * @param trainingProvider Training provider object with data for creation
     */
    public void fillAllFields(TrainingProvider trainingProvider) {
        fillTextFields(trainingProvider);

        for (TrainingProviderLocation loc : trainingProvider.getLocations()) {
            addLocation(loc);
        }
    }

    /**
     * Add location to the semester
     *
     * @param location - location
     */
    private void addLocation(TrainingProviderLocation location) {
        clickLocationAdd();
        TrainingProviderLocationForm trainingProviderLocationForm = new TrainingProviderLocationForm();
        trainingProviderLocationForm.addLocation(location);
    }

    /**
     * This method is used for filling all fields required for Training Provider creation
     *
     * @param trainingProvider Training provider object with data for creation
     * @param active           Indicates if Provider location should have active course
     */
    public void fillAllFields(TrainingProvider trainingProvider, boolean active) {
        fillTextFields(trainingProvider);

        clickLocationAdd();
        TrainingProviderLocationForm trainingProviderLocationForm = new TrainingProviderLocationForm();
        trainingProviderLocationForm.selectRandomWiaCourse(active);
        trainingProviderLocationForm.fillOtherInformation();
        trainingProviderLocationForm.clickAdd();
    }

    /**
     * This method is used for filling all fields required for Training Provider creation
     * Training Provider will have 10 locations
     *
     * @param trainingProvider Training provider object with data for creation
     */
    public void fillAllFieldsAndCreate10Locations(TrainingProvider trainingProvider) {
        fillTextFields(trainingProvider);
        //add 10 locations
        for (int i = 1; i <= Constants.EMAIL_LENGTH; i++) {
            //add 5 locations with numeric codes
            clickLocationAdd();
            TrainingProviderLocationForm trainingProviderLocationForm = new TrainingProviderLocationForm();
            TrainingProviderLocation tempLocation = new TrainingProviderLocation();
            tempLocation.setCode(String.valueOf(i));
            trainingProviderLocationForm.fillOtherInformation(tempLocation);
            trainingProviderLocationForm.clickAdd();
        }
        for (int i = 1; i <= Constants.EMAIL_LENGTH; i++) {
            //add 5 locations with alphabetic codes
            clickLocationAdd();
            TrainingProviderLocationForm trainingProviderLocationForm = new TrainingProviderLocationForm();
            TrainingProviderLocation tempLocation = new TrainingProviderLocation();
            tempLocation.setCode(codes[i - 1]);
            trainingProviderLocationForm.fillOtherInformation(tempLocation);
            trainingProviderLocationForm.clickAdd();
        }
    }

    public TrainingProviderLocationForm addLoc(TrainingProviderLocation loc) {
        TrainingProviderCreationForm providerCreationPage = new TrainingProviderCreationForm();
        providerCreationPage.clickLocationAdd();
        TrainingProviderLocationForm providerLocationPage = new TrainingProviderLocationForm();
        providerLocationPage.fillOtherInformation(loc);
        providerLocationPage.clickAdd();
        return new TrainingProviderLocationForm();
    }
}
