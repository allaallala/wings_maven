package edu.msstate.nsparc.wings.integration.forms.everify;

import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.Everify;
import framework.customassertions.CustomAssertion;
import framework.elements.ComboBox;
import framework.elements.Div;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
This form is opened via Participants -> Wagner-Peyser -> E-Verify -> Create
 */
public class EverifyCreationForm extends EverifyBaseForm {

    private ComboBox cmbWorkerStatus = new ComboBox("css=select[id='tempCitizenshipStatus']", "U.S. Worker Status");
    private TextBox txbEverifyDate = new TextBox("id=dateEverify", "E-Verify Date");
    private TextBox txbStatusDate = new TextBox("id=statusDate", "Status Date");
    private Div dvError = new Div(By.id("dateEverify.errors"), "Error text");

    /**
     * Default constructor
     */
    public EverifyCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'E Verify Creation')]"), "E-verify Creation");
    }

    /**
     * This method is used for filling all required fields on E-Verify creation form
     * @param everify Everify object with data
     */
    public void fillRequiredFields(Everify everify) {
        inputFirstSecondDocumentNumbers(everify.getDocumentIdentityType(), everify.getDocumentIdentityTypeNumber(), everify.getDocumentEmploymentType(),
                everify.getDocumentEmploymentTypeNumber());

        inputEverifyDate(everify.getEverifyDate());
        inputCaseNumber(everify.getCaseNumber());
        inputStatusDate(everify.getStatusDate());
        selectStatus(everify.getStatus());
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        selectWorkerStatus(everify.getWorkerStatus());
    }

    /**
     * Input everify date
     * @param everifyDate - everify date
     */
    public void inputEverifyDate(String everifyDate) {
        txbEverifyDate.type(everifyDate);
    }

    /**
     * Input e-verify status date
     * @param statusDate - status date
     */
    public void inputStatusDate(String statusDate) {
        txbStatusDate.type(statusDate);
    }

    /**
     * Select worker status
     * @param workStatus - worker status
     */
    public void selectWorkerStatus(String workStatus) {
        cmbWorkerStatus.select(workStatus);
    }

    /**
     * Validate error text
     * @param errorText - error text
     */
    public void validateErrorText(String errorText) {
        CustomAssertion.softTrue("Incorrect error text", dvError.getText().equals(errorText));
    }
}
