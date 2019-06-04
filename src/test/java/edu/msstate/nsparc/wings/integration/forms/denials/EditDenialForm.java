package edu.msstate.nsparc.wings.integration.forms.denials;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import org.openqa.selenium.By;

/**
 * Edit denial form
 */
public class EditDenialForm extends BaseWingsForm {

    /**
     * Default constructor
     */
    public EditDenialForm() {
        super(By.xpath("//h1[contains(.,'Editing Denial')]"), "Editing Denial");
    }
}
