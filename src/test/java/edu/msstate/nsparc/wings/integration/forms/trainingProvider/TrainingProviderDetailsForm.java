package edu.msstate.nsparc.wings.integration.forms.trainingProvider;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProvider;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingProviderLocation;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Link;
import framework.elements.Span;
import org.openqa.selenium.By;

/**
 * This form is opened via Advanced -> Training Providers -> Search for record -> Open record
 */
public class TrainingProviderDetailsForm extends TrainingProviderBaseForm {

    private Span spnProviderName = new Span("css=span.float-left", "Provider name");
    private Span spnFein = new Span(By.xpath("//form[@id='providerForm']//tbody//tr[4]//td[2]"), "FEIN name");
    private Span spnDfa = new Span(By.xpath("//form[@id='providerForm']//tbody//tr[4]//td[4]"), "DFA name");
    private Span spnWarningText = new Span("//div[@class='blue-border']", "Warning text about deletion");
    private Link lnkViewTradeTrainingEnrl = new Link("//a[.='View Trade Training Enrollments']", "View trade training enrollment");
    private Link lnkViewWiaTrainingEnrl = new Link("//a[.='View WIA Training Enrollments']", "View WIA training enrollment");
    private Button btnEditLocation = new Button("css=button[id='editLocation']", "Edit Location");
    private Button btnDelete = new Button("//input[@value='Delete']", "Button delete");
    private Button rbFirstResult = new Button("id=selectedRadio1", "First location in the list");
    private Button btnApproveWia = new Button("//img[@title='Approve for WIA']", "Approve for WIA button");
    private Button btnApproveTradeLocation = new Button("//img[@title='Approve for Trade']", "Approve for Trade button");
    private Button btnApproveForTrade = new Button(By.id("approveForTrade"), "Approve for trade");
    private Button btnDisable = new Button("//img[@title='Disable']/..", "Disable security");
    private Button btnEditProvider = new Button(By.xpath("//button[@id='edit']"), "Edit provider");
    private Button btnAddLocation = new Button("id=addLocation", "Add Location");
    private Button btnApproveForTrade2 = new Button("//tr[2]//a/img[@title='Approve for Trade']", "Approve for Trade");
    private Button btnApproveFT = new Button("//button[@alt='Approve for Trade']", "Approve for Trade");


    /**
     * Default constructor
     */
    public TrainingProviderDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Training Provider Detail')]"), "Training Provider Detail");
    }

    /**
     * This method is used for getting Training Provider name from Details form
     *
     * @return Training Provider name
     */
    public String getName() {
        String text = spnProviderName.getText();
        return CommonFunctions.regexGetMatch(text, "[\\w]+");
    }

    /**
     * Get warning text
     *
     * @return - warning text
     */
    public String getWarningText() {
        return spnWarningText.getText();
    }

    /**
     * Edit location button.
     */
    public void clickEditLocation() {
        btnEditLocation.clickAndWait();
    }

    /**
     * Edit training provider
     *
     * @param train - training provider to edit.
     * @param name  - training provider name
     * @param fein  - training provider fein
     * @param dfa   - training provider dfa.
     */
    public void editProvider(TrainingProvider train, String name, String fein, String dfa) {
        btnEditProvider.clickAndWait();
        TrainingProviderEditForm editPage = new TrainingProviderEditForm();

        //Set new values of the training provider
        train.setName(name);
        train.setFein(fein);
        train.setDfa(dfa);

        //Edit training provider
        editPage.inputProviderName(train.getName());
        editPage.inputFein(train.getFein());
        editPage.inputVendorNumber(train.getDfa());
        clickButton(Buttons.Save);

        //Validate information
        validateInformation(train);
    }

    /**
     * This method is used for checking expected and actual Provider parameters
     *
     * @param trainingProvider Expected data
     */
    public void validateInformation(TrainingProvider trainingProvider) {
        CustomAssertion.softAssertEquals(getName(), trainingProvider.getName(), "Provider name assert fail");
        CustomAssertion.softAssertEquals(spnFein.getText().substring(5), trainingProvider.getFein().substring(5), "FEIN_LENGTH assert fail");
        CustomAssertion.softAssertEquals(spnDfa.getText(), trainingProvider.getDfa(), "DFA assert fail");
        clickAndWait(BaseButton.DONE);
    }

    /**
     * This method is used for getting Provider Location codes from Details form
     *
     * @return String array with codes
     */
    public String[] getLocationCodes() {
        SearchResultsForm resultsForm = new SearchResultsForm();
        // getting number of locations
        int count = resultsForm.getResultRowsCount();
        String[] locationCodes = new String[count];
        String locationCodesColumn = "2";
        // for each location
        for (int i = 0; i < count; i++) {
            // getting location code
            locationCodes[i] = resultsForm.getRecordText(i + 1, locationCodesColumn);
        }
        return locationCodes;
    }

    /**
     * Validates location data in the table
     *
     * @param provider - training provider
     */
    public void validateLocation(TrainingProvider provider) {
        TrainingProviderLocation location = provider.getLocations().get(0);
        SearchResultsForm resultsForm = new SearchResultsForm();
        CustomAssertion.softAssertEquals(resultsForm.getTableContentText(), location.getCode(), "Location code is incorrect");
        CustomAssertion.softAssertEquals(resultsForm.getFirstRowRecordText(2), location.getName(), "Location name is incorrect");
        CustomAssertion.softAssertEquals(resultsForm.getFirstRowRecordText(3), location.getCity()
                + ", " + location.getState() + " " + location.getZipCode(), "Location address is incorrect");
    }

    /**
     * Approve first location in the list for WIA.
     */
    public void approveFirstForWia() {
        rbFirstResult.click();
        btnDisable.click();
        btnApproveWia.click();
    }

    /**
     * Approve first location in the list for Trade.
     */
    public void approveFirstForTrade() {
        btnApproveTradeLocation.click();
    }

    /**
     * View all training enrollments connected with chosen training provider
     *
     * @param type - type of enrollments.
     */
    public void viewProviderEnrollments(String type) {
        if (type.contains(Constants.WIOA)) {
            lnkViewWiaTrainingEnrl.clickAndWait();
        } else {
            lnkViewTradeTrainingEnrl.clickAndWait();
        }
    }

    /**
     * Delete record
     */
    public void deleteRecord() {
        btnDelete.click();
        areYouSure(Popup.Yes);
    }

    /**
     * Approve training provider for trade
     */
    public void approveForTrade() {
        if (btnApproveForTrade.isPresent()) {
            btnApproveForTrade.clickAndWait();
        }
        if (btnApproveTradeLocation.isPresent()) {
            btnApproveTradeLocation.clickAndWait();
        }
    }

    public void clickLocationAdd() {
        btnAddLocation.clickAndWait();
    }

    /**
     * Select created location and make it trade approved.
     */
    public void clickApproveForTrade() {
        btnApproveForTrade2.click();
        btnApproveFT.clickAndWait();
    }
}
