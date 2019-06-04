package edu.msstate.nsparc.wings.integration.forms.everify;

import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.Everify;
import framework.customassertions.CustomAssertion;
import framework.elements.ComboBox;
import framework.elements.Div;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
This form is opened via Participants -> Wagner-Peyser -> E-Verify -> Search for record -> Open it -> Click on Edit
 */
public class EverifyEditForm extends EverifyBaseForm {
    private Div dvValidationError = new Div(By.id("id.errors"), "Validation errors found on page");
    private String validationErrorText = "Validation errors found on page.";
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private TextBox tbEverifyDateSelection = new TextBox("id=dateEverify", "Date everify selection textbox");
    private TextBox tbEditCaseNumberInput = new TextBox("id=caseNumber", "Input case number");
    private TextBox txbStatusDate = new TextBox(By.id("statusDate"), "Status Date");
    private Div dvStatusDateError = new Div(By.id("statusDate.errors"), "Error status page");
    private String statusErrorText = "Status Date cannot be in the future.";

    //Header of the document
    private ComboBox cbDocIndentityType = new ComboBox("id=documentOneType", "Document establishing identity type");
    private ComboBox cbDocEmploymentType = new ComboBox("id=documentTwoType", "Document establishing employment authorization type");

    //Establishing section numbers
    private TextBox tbNumberDocIdentityType = new TextBox("id=documentOneNumber", "Document identity type number");
    private TextBox tbNumberDocEmploymentType = new TextBox("id=documentTwoNumber", "Document employment authorization number");

    /**
     * Default consturctor
     */
    public EverifyEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'E Verify Edit')]"), "E-Verify Edit");
    }

    /**
     * Edit current docs header, if user has permissions to do this
     * @param user - current user
     * @param docOneHeader - document identity type value
     * @param docTwoHeader - document employment authorization type value
     * @param everify - E-Verify
     */
    public void editDocsHeader(User user, String docOneHeader, String docTwoHeader, Everify everify) {
        divideMessage("Check current docs header edit");
        if (user.getEverify().getEverifyEditCurrentDocs()) {
            cbDocIndentityType.select(docOneHeader);
            cbDocEmploymentType.select(docTwoHeader);
            everify.setDocumentIdentityType(docOneHeader);
            everify.setDocumentEmploymentType(docTwoHeader);
        } else {
            CustomAssertion.softTrue("Document indentity type can be edited!", !cbDocIndentityType.isPresent());
            CustomAssertion.softTrue("Document employment authorization type can be edited!", !cbDocEmploymentType.isPresent());
        }
    }

    /**
     * Edit establishment section (input new values to the Number field value
     * @param user - current user
     * @param newNumberValue - number value to edit
     * @param everify - everify
     */
    public void editEstablishmentSection(User user, String newNumberValue, Everify everify) {
        if (user.getEverify().getEverifyEditEstablishmentSection()) {
            tbNumberDocIdentityType.type(newNumberValue);
            tbNumberDocEmploymentType.type(newNumberValue);
            everify.setDocumentIdentityTypeNumber(newNumberValue);
            everify.setDocumentEmploymentTypeNumber(newNumberValue);
        }

    }

    /**
     * Edit date selection, if user has permissions to do this. Else check, that only view functionality is available.
     * @param user - current user
     * @param date - date selection
     * @param everify - everify
     */
    public void editDateSelection(User user, String date, Everify everify) {
        if (user.getEverify().getEverifyEditDateSelection()) {
            tbEverifyDateSelection.type(date);
            everify.setEverifyDate(date);
        } else {
            CustomAssertion.softTrue("Everify date selection is present and can be edited!",!tbEverifyDateSelection.isPresent());
        }
    }

    /**
     * Edit case number input
     * @param user - current user
     * @param numberInput - number input
     * @param everify - everify
     */
    public void editCaseNumberInput(User user, String numberInput, Everify everify) {
        if (user.getEverify().getEverifyEditCaseNumberInput()) {
            tbEditCaseNumberInput.type(numberInput);
            everify.setCaseNumber(numberInput);
        } else {
            CustomAssertion.softTrue("Everify date selection is present and can be edited!", !tbEditCaseNumberInput.isPresent());
        }
    }

    /**
     * Change status date
     * @param date - new status date
     */
    public void editStatusDate(String date) {
        txbStatusDate.type(date);
        txbStatusDate.submitByEnter();
        scrollDown();
    }

    /**
     * Validate status date error text
     */
    public void validateErrorStatusDate() {
        CustomAssertion.softAssertContains(dvValidationError.getText(), validationErrorText, "Incorrect validation errors on page");
        CustomAssertion.softAssertContains(dvStatusDateError.getText(), statusErrorText, "Incorrect validation errors on page");
    }
}
