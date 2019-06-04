package edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderStaffCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderCreationForm;
import framework.CommonFunctions;
import framework.elements.RadioButton;
import framework.elements.TableCell;
import org.openqa.selenium.By;

public class StaffInformationStaffForm extends JobOrderCreationForm {
    private RadioButton rbAllowDocument = new RadioButton("css=input[id='allowDoc1']",
            "Document Attachment");
    private TableCell tbcJobID = new TableCell("//td[contains(text(),'Job Order Number:')]/following-sibling::td",
            "Job ID table cell");

    public StaffInformationStaffForm() {
        super(By.xpath("//h1[contains(.,'Job Order Creation: Staff Information')]"), "Create Opening: Staff Information");
    }

    public void fillInStaffInformationAndSave() {
        fillInStaffInformation();

        clickButton(Buttons.Create);
        clickButton(Buttons.Done);
    }

    public void fillInStaffInformation() {
        rbOverrideNonVeteranDate.click();
        rbAllowDocument.click();
        txbDateNonVetRelease.type(CommonFunctions.getTomorrowDate());
    }

    public String fillInStaffInfoAngGetJobOrderId(Boolean allowOnline) {
        fillInStaffInformation();
        clickAllowOnline(allowOnline);
        rbAllowDocument.click();
        clickJobDevelopmentNo();
        clickAndWait(BaseButton.CREATE);
        String jobID = tbcJobID.getText();
        clickButton(Buttons.Done);

        return jobID;
    }
}
