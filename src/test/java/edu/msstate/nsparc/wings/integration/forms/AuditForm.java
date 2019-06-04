package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is available for Admin
 * It can be accessed from Employer or Participant participantSSDetails form by pressing "Audit" button
 */
public class AuditForm extends BaseWingsForm {

    public static final TableCell TBC_AUDIT_TABLE = new TableCell("css=table#auditRecordTable", "Audit Table");

    /**
     * Constructor
     */
    public AuditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Audit Trail')]"), "Audit Trail");
    }
}
