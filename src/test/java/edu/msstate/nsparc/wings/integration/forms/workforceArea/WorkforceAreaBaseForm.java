package edu.msstate.nsparc.wings.integration.forms.workforceArea;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.models.administrative.LWIA;
import framework.elements.ComboBox;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Workforce Area forms
 */
public class WorkforceAreaBaseForm extends BaseWingsForm {

    private TextBox txbAreaName = new TextBox("css=input#lwiaName", "Area Name");
    private TextBox txbAreaCode = new TextBox("css=input#lwiaCode", "Area Code");
    private TextBox txbAreaDirector = new TextBox("css=input#boardAdmin", "Area Director");
    private TextBox txbLineOne = new TextBox("css=input[id='contactInformation.addressByResidenceAddr.lineOne']", "Area Office Address - Line One");
    private TextBox txbLineTwo = new TextBox("css=input[id='contactInformation.addressByResidenceAddr.lineTwo']", "Area Office Address - Line Two");
    private TextBox txbCity = new TextBox("css=input[id='contactInformation.addressByResidenceAddr.city']", "City");
    private TextBox txbZipPostalCode = new TextBox("css=input[id='contactInformation.addressByResidenceAddr.zipcode']", "Zip/Postal Code");
    private TextBox txbPhoneNumber = new TextBox(By.id("contactInformation.primaryPhone"), "Primary phone");
    private TextBox txbExt = new TextBox("css=input[id='contactInformation.extNumber']", "Ext");
    private TextBox txbEmail = new TextBox("css=input[id='contactInformation.emailAddress']", "Email");
    private ComboBox cmbState = new ComboBox("css=select[id='contactInformation.addressByResidenceAddr.state']", "State");
    private ComboBox cmbCountry = new ComboBox("css=select[id='contactInformation.addressByResidenceAddr.country']", "Country");
    private String[] details, address;

    /**
     * Constructor of the form with defined locator
     *
     * @param locator   - locator of the form
     * @param formTitle - title of the form.
     */
    public WorkforceAreaBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Input workforce area name
     *
     * @param areaName - area name
     */
    public void inputWorkforceArea(String areaName) {
        txbAreaName.type(areaName);
    }

    /**
     * Input phone number
     *
     * @param phoneNumber - phone number
     */
    public void inputPhoneNumber(String phoneNumber) {
        txbPhoneNumber.type(phoneNumber);
    }

    /**
     * Input workforce area data
     *
     * @param lwia - LWIA
     */
    public void inputData(LWIA lwia) {
        details = lwia.getDetails();
        address = lwia.getAddress();
        inputWorkforceArea(details[0]);
        txbAreaCode.type(details[1]);
        txbAreaDirector.type(details[2]);
        txbExt.type(details[3]);
        txbEmail.type(details[4]);
        txbPhoneNumber.type(details[5]);

        //Edit address
        txbLineOne.type(address[0]);
        txbLineTwo.type(address[1]);
        txbCity.type(address[2]);
        txbZipPostalCode.type(address[3]);
        cmbCountry.select(address[5]);
        cmbState.waitForIsElementPresent();
        cmbState.select(address[4]);
    }
}
