package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails;

import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;

import java.io.File;

public class DocumentSsSectionForm extends BaseParticipantSsDetailsForm {

    private ComboBox cbDocumentType = new ComboBox(By.id("tempAddEditDocument.document.docType"), "Document type");
    private TextBox txbFilePath = new TextBox(By.id("tempAddEditDocument.docFile"), "Input path to file");
    private Button btnAdd = new Button(By.id("update"), "Add");
    private Div dvUploadsAvailable = new Div(By.xpath("//span[@class='label label-info pull-right'][contains(.,'Uploads Available')]"), "Available uploads");
    private String docStatusXpath = "//table//tr[1]//span";
    private String docFileXpath = "//table[@class='span12']//tr//td[contains(.,'%1$s')]";


    public DocumentSsSectionForm() {
        super(By.xpath("//h3[contains(.,'My Documents')]"), "My Documents");
    }


    public void uploadDocument(String pathFile, String documentType) {
        inputUploadData(pathFile, documentType);
        btnAdd.clickAndWait();
    }

    public void inputUploadData(String pathFile, String documentType) {
        CommonFunctions.saveFile(pathFile);
        clickButton(ParticipantDetailsButtons.ADD_DOCUMENT);
        cbDocumentType.select(documentType);
        txbFilePath.type(new File(pathFile).getAbsolutePath());
    }

    public void validateUploadedDocument(String file, String state, Integer quantity) {
        String fileCutExtension = CommonFunctions.regexGetMatch(file, "(\\w{1,}(_)?)+(\\w{1,})?");
        for (int i = 1; i <= quantity; i++) {
            TableCell tbcDocStatus = new TableCell(By.xpath(docStatusXpath), "Status of the uploaded document");
            TableCell tbcFile = new TableCell(By.xpath(String.format(docFileXpath, CommonFunctions.getCurrentDate())), "Name and date of the uploaded file");
            if (tbcDocStatus.getText().contains(state) && tbcFile.getText().contains(fileCutExtension + " - " + CommonFunctions.getCurrentDate())) {
                break;
            }
        }
    }

    public void validateUploadedText(Integer uploadedQuantity) {
        CustomAssertion.softTrue("Incorrect quantity of available documents", dvUploadsAvailable.getText().contains(String.format("Uploads Available: %1$d of 21", uploadedQuantity)));
    }
}
