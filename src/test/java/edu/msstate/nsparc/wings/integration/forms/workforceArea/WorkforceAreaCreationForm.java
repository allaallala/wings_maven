package edu.msstate.nsparc.wings.integration.forms.workforceArea;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.models.administrative.LWIA;
import org.openqa.selenium.By;

/**
 * This form is opened wia Advanced -> Workforce Area -> Create.
 * Created by a.vnuchko on 05.07.2016.
 */
public class WorkforceAreaCreationForm extends WorkforceAreaBaseForm {
    public WorkforceAreaCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'LWIA Creation')]"), "LWIA creation form");
    }

    /**
     * Create workforce area
     *
     * @param lwia - lWIA
     */
    public void createWorkForce(LWIA lwia) {
        inputData(lwia);
        inputPhoneNumber(lwia.getDetails()[5]);
        clickButton(Buttons.Create);
        WorkforceAreaDetailsForm detailsForm = new WorkforceAreaDetailsForm();
        detailsForm.validateWorkforceArea(lwia);
    }
}
