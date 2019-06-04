package edu.msstate.nsparc.wings.integration.forms.trainingProvider;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Training Provider forms
 */
public class TrainingProviderBaseForm extends BaseWingsForm {
    private Button btnRemove = new Button("id=removeLocation", "Remove");
    private TextBox txbTrainingProviderName = new TextBox(By.id("providerName"), "Training Provider Name");
    private TextBox txbFEIN = new TextBox("id=federalId", "FEIN_LENGTH");
    protected TextBox txbTrainingProviderCode = new TextBox("css=input#providerCode", "Training Provider Code");
    private TextBox txbDFAVendorNumber = new TextBox("css=input#vendorNumber", "DFA Vendor Number");
    private Button btnEditProvider = new Button(By.xpath("//button[@id='edit']"), "Edit provider");

    /**
     * Constructor of the form with defined locator
     *
     * @param locator - locator of the form.
     */
    public TrainingProviderBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Input provider name
     *
     * @param providerName - provider name
     */
    public void inputProviderName(String providerName) {
        txbTrainingProviderName.type(providerName);
    }

    /**
     * Input fein
     *
     * @param fein - fein
     */
    public void inputFein(String fein) {
        txbFEIN.type(fein);
    }

    /**
     * Input vendor number.
     *
     * @param vendorNumber - vendor number
     */
    public void inputVendorNumber(String vendorNumber) {
        txbDFAVendorNumber.type(vendorNumber);
    }

    /**
     * Input training provider code
     *
     * @param providerCode - provider code
     */
    public void inputProviderCode(String providerCode) {
        txbTrainingProviderCode.type(providerCode);
    }

    /**
     * Check training provider values
     *
     * @param name - training provider name
     * @param fein - provider fein
     * @param dfa  - provider dfa
     */
    public void checkProviderValues(String name, String fein, String dfa) {
        CustomAssertion.softAssertEquals(txbTrainingProviderName.getValue(), name, "Incorrect training provider name");
        CustomAssertion.softAssertEquals(txbFEIN.getValue(), fein, "Incorrect FEIN");
        CustomAssertion.softAssertEquals(txbDFAVendorNumber.getValue(), dfa, "Incorrect DFA");
    }

    /**
     * Remove location
     */
    public void removeLocation() {
        btnRemove.clickAndWait();
    }

    /**
     * Edit provider
     */
    public void editProvider() {
        btnEditProvider.clickAndWait();
    }
}

