package edu.msstate.nsparc.wings.integration.forms.employerEnrollment;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Employer Enrollment forms
 */
public class EmployerEnrollmentBaseForm extends BaseWingsForm {
    protected TextBox txbCreationDate = new TextBox("id=datePosted","Creation Date");
    /**
     * Constructor of the form with defined locator of the form
     * @param locator - locator of the form
     * @param formTitle - title of the form.
     */
    public EmployerEnrollmentBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }
}
