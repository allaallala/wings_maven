package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.ComboBox;
import org.openqa.selenium.By;

/**
 * To open this form you need to log in as Employer -> Click on 'View Applicants' button -> Search for record ->
 * Open record -> Click on 'Update Status' link
 */
public class UpdateStatusForm extends BaseWingsForm {

    private ComboBox cmbResultRef = new ComboBox("id=refResult", "Status");

    /**
     * Default constructor
     */
    public UpdateStatusForm() {
        super(By.xpath("//div[contains(.,'Update Application Status')]"), "Update Application Status");
    }

    public void selectResultRef(String value) {
        cmbResultRef.select(value);
    }
}
