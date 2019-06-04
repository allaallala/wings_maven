package edu.msstate.nsparc.wings.integration.forms.workforceArea;

import edu.msstate.nsparc.wings.integration.models.administrative.LWIA;
import org.openqa.selenium.By;

/**
 * This form is opened via Advanced -> Workforce Area -> Search for record -> Open record -> Click on Edit button
 */
public class WorkforceAreaEditForm extends WorkforceAreaBaseForm {

    /**
     * Default constructor
     */
    public WorkforceAreaEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'LWIA Edit')]"), "Workforce Area Edit Form");
    }

    /**
     * This method is used for editing Workforce area participantSSDetails
     *
     * @param lwia - LWIA object
     */
    public void editWorkforceAreaDetails(LWIA lwia) {
        //Edit participantSSDetails
        inputData(lwia);
    }
}
