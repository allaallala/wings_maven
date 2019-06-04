package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import org.openqa.selenium.By;

/**
 * This form represents Cancel Menu window that is opened after pressing Cancel button on Edit form with changes
 */
public class CancelMenuForm extends BaseWingsForm {
    /**
     * Constructor
     */
    public CancelMenuForm() {
        super(By.xpath("//table[@id='cancelMenu']"), "Cancel Menu form");
    }
}
