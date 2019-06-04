package edu.msstate.nsparc.wings.integration.forms.everify;

import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.Everify;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Div;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
This form is opened via Participants -> Wagner-Peyser -> E-Verify -> Search for record -> Open it
 */
public class EverifyDetailsForm extends EverifyBaseForm {

    private String xpathDoc = "//td[contains(text(),'%1$s')]/following-sibling::td[contains(text(),'%2$s')]";
    private Button btnDelete = new Button("css=input[value='Delete']", "Delete");

    private Div dvValidationError = new Div(By.id("id.errors"), "Validation errors found on page");
    private String validationErrorText = "Validation errors found on page.";
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private TableCell tbcDocIdentityType;
    private TableCell tbcDocIdentityNumber;
    private TableCell tbcDocEmploymentType;
    private TableCell tbcDocEmploymentNumber;
    private TableCell tbcEverifyDate = new TableCell(String.format(detailPath, "E-Verify Date:"), "Everify date");
    private TableCell tbcStatusDate = new TableCell(String.format(detailPath, "Status Date:"), "Status date");
    private TableCell tbcCaseNumber = new TableCell(String.format(detailPath, "Case Number:"), "Case Number");
    private TableCell tbcStatus = new TableCell(String.format(detailPath, "Status:"), "Status");


    public EverifyDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'E Verify Detail')]"), "E-Verify View");
    }

    public void deleteRecord() {
        btnDelete.click();
        areYouSure(Popup.Yes);
    }

    public String getStatusText() {
        return tbcStatus.getText();
    }

    public String getCaseNumberText() {
        return tbcCaseNumber.getText();
    }

    public void checkButtons(User user) {
        checkButtonsPresent(user.getEverify().getEverifyViewEditButton(), user.getEverify().getEverifyViewAuditButton());
        divideMessage("Delete");
        ifButton(user.getEverify().getEverifyViewDeleteButton(), btnDelete);
        if (user.getEverify().getEverifyViewDeleteButton()) {
            btnDelete.click();
            areYouSure(Popup.No);
        }
    }

    public void validateData(Everify everify) {
        //Document Establishing Identity
        tbcDocIdentityType = new TableCell(String.format(xpathDoc, "Document Type:", everify.getDocumentIdentityType()), "Document identity type value");
        tbcDocIdentityNumber = new TableCell(String.format(xpathDoc, "Number:", everify.getDocumentIdentityTypeNumber()), "Document identity type number value");
        CustomAssertion.softAssertEquals(tbcDocIdentityType.getText(), everify.getDocumentIdentityType(), "Incorrect document identity type text");
        CustomAssertion.softAssertEquals(tbcDocIdentityNumber.getText(), everify.getDocumentIdentityTypeNumber(), "Incorrect document identity type number text");

        //Document Establishing Employment Authorization
        tbcDocEmploymentType = new TableCell(String.format(xpathDoc, "Document Type:", everify.getDocumentEmploymentType()), "Document employment type value");
        tbcDocEmploymentNumber = new TableCell(String.format(xpathDoc, "Number:", everify.getDocumentEmploymentTypeNumber()), "Document employment type number value");
        CustomAssertion.softAssertEquals(tbcDocEmploymentType.getText(), everify.getDocumentEmploymentType(), "Incorrect document employment type text");
        CustomAssertion.softAssertEquals(tbcDocEmploymentNumber.getText(), everify.getDocumentEmploymentTypeNumber(), "Incorrect document employment type number text");
        CustomAssertion.softAssertEquals(tbcEverifyDate.getText(), everify.getEverifyDate(), "Incorrect everify date text");
        CustomAssertion.softAssertEquals(tbcStatusDate.getText(), everify.getStatusDate(), "Incorrect status date text");
        CustomAssertion.softAssertEquals(tbcCaseNumber.getText(), everify.getCaseNumber(), "Incorrect case number text");
        CustomAssertion.softAssertEquals(tbcStatus.getText(), everify.getStatus(), "Incorrect status text");
    }
}
