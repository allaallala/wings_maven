package edu.msstate.nsparc.wings.integration.forms.trainingWaiver;

import org.openqa.selenium.By;

/**
 * Training waiver audit trail
 */
public class TrainingWaiverAuditTrail extends TrainingWaiverBaseForm {

    /**
     * Default constructor
     */
    public TrainingWaiverAuditTrail() {
        super(By.xpath("//h1"), "Audit Trail for Training Waiver");
    }
}
