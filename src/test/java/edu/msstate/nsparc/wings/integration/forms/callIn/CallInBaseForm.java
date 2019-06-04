package edu.msstate.nsparc.wings.integration.forms.callIn;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import org.openqa.selenium.By;

/**
 * This is the base form for Call In forms
 */
public class CallInBaseForm extends BaseWingsForm {

    /**
     * Constructor of the form with specified locator
     * @param locator - locator of the form.
     * @param formTitle - title of the form.
     */
    public CallInBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }
}
