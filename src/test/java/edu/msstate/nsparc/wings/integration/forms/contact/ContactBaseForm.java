package edu.msstate.nsparc.wings.integration.forms.contact;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.ComboBox;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Contact forms
 */
public class ContactBaseForm extends BaseWingsForm {

    private RadioButton rbJobDevelopmentYes = new RadioButton("id=isJobDevelopment1", "Job Development - Yes");
    private RadioButton rbJobDevelopmentNo = new RadioButton("id=isJobDevelopment2", "Job Development - No");
    private TextBox txbContactDate = new TextBox("id=dateContactActual", "Contact Date");
    private ComboBox cmbContactMethod = new ComboBox("id=contactMethod", "Contact Method");

    /**
     * Constructor of the form with specified locator
     * @param locator - locator of the form.
     * @param formTitle - title of the form.
     */
    public ContactBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Input contact date and method, click if job development
     * @param state - true/ false
     * @param contactDate - contact date
     * @param contactMethod - contact method.
     */
    public void inputJobContactDateMethod(Boolean state, String contactDate, String contactMethod) {
        if (state) {
            rbJobDevelopmentYes.click();
        } else {
            rbJobDevelopmentNo.click();
        }
        txbContactDate.type(contactDate);
        selectContactMethod(contactMethod);
    }

    /**
     * Select contact method
     * @param method - contact method.
     */
    public void selectContactMethod(String method) {
        cmbContactMethod.select(method);
    }
}
