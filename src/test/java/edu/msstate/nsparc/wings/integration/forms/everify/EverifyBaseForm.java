package edu.msstate.nsparc.wings.integration.forms.everify;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.ComboBox;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for E-Verify forms
 */
public class EverifyBaseForm extends BaseWingsForm {

    private ComboBox cmbStatus = new ComboBox("css=select[id='status']", "Status");
    private TextBox txbCaseNumber = new TextBox("css=input[id='caseNumber']", "Case Number");
    private ComboBox cmbThirdDocumentType = new ComboBox("name=doc3Type", "Third Document Type");
    private ComboBox cmbFirstDocumentType = new ComboBox("css=select[id='documentOneType']", "First Document Type");
    private TextBox txbFirstDocumentNumber = new TextBox("css=input[id='documentOneNumber']", "First Document Number");
    private ComboBox cmbSecondDocumentType = new ComboBox("css=select[id='documentTwoType']", "Second Document Type");
    private TextBox txbSecondDocumentNumber = new TextBox("css=input[id='documentTwoNumber']", "Second Document Number");


    public EverifyBaseForm(By locator, String title) {
        super(locator, title);
    }

    public void selectStatus(String option) {
        cmbStatus.select(option);
    }

    public void inputCaseNumber(String caseNumber) {
        txbCaseNumber.clear();
        txbCaseNumber.type(caseNumber);
    }

    public void inputFirstSecondDocumentNumbers(String firstSelect, String firstDoc, String secondSelect, String secondDoc) {
        if (cmbFirstDocumentType.isPresent()) {
            cmbFirstDocumentType.select(firstSelect);
            BaseOtherElement.LOADING.getElement().waitForNotVisible();
            txbFirstDocumentNumber.type(firstDoc);
            if (cmbSecondDocumentType.isPresent()) {
                cmbSecondDocumentType.select(secondSelect);
            } else {
                cmbThirdDocumentType.select(secondSelect);
            }
            txbSecondDocumentNumber.type(secondDoc);
        }
    }

}
