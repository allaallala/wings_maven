package edu.msstate.nsparc.wings.integration.forms.dataIntegrity;

import framework.elements.ComboBox;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
This form is opened via Reports -> Data Integrity -> Participant Report -> Create
 */
public class DataIntegrityParticipantReportForm extends DataIntegrityBaseForm {

    private ComboBox cmbApplicationUser = new ComboBox("id=checkApplicationUser", "Application user");
    private TextBox txbFirstName = new TextBox("css=input#firstName", "First name");
    private TextBox txbLastName = new TextBox("css=input#lastName", "Last name");

    /**
     * Constructor of the form with defined locator and form's name
     * @param locator - locator of the form
     * @param name - name of the form
     */
    DataIntegrityParticipantReportForm(By locator, String name) {
        super(locator, name);
    }

    /**
     * Default constructor
     */
    public DataIntegrityParticipantReportForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Participant Webreport')]"), "Participant report");
    }

    /**
     * Input fist name and last name
     * @param firstName - first name
     * @param lastName - last name
     */
    public void inputFirstLastName(String firstName, String lastName) {
        txbFirstName.type(firstName);
        txbLastName.type(lastName);
    }

    /**
     * Select application user
     * @param value - value to select.
     */
    public void selectApplicationUser(String value) {
        cmbApplicationUser.waitForIsElementPresent();
        cmbApplicationUser.select(value);
    }
}
