package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail.participantDetailStaffSections.BaseParticipantDetailsForm;
import framework.CommonFunctions;
import framework.elements.Button;
import framework.elements.ComboBox;
import framework.elements.TextBox;
import org.openqa.selenium.By;

import java.io.File;

public class AddDocumentStaffForm extends BaseParticipantDetailsForm {

    private String documentType = "Resume";
    private Button btnAddDocument = new Button(By.id("addDocument"), "Add document");
    private ComboBox cmbDocumentType = new ComboBox(By.id("tempAddEditDocument.document.docType"), "Document type");
    private TextBox tbPathToFile = new TextBox(By.id("tempAddEditDocument.docFile"), "Path to file");

    public AddDocumentStaffForm() {
        super(By.xpath("//h1[contains(.,'Add Document')]"), "Add Document");
    }

    public void addDocument(String fileName) {
        CommonFunctions.saveFile(fileName);
        cmbDocumentType.select(documentType);
        tbPathToFile.type(new File(fileName).getAbsolutePath());
        clickButton(Buttons.Add);
    }
}
