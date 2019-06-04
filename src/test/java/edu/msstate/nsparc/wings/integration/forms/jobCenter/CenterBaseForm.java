package edu.msstate.nsparc.wings.integration.forms.jobCenter;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.ComboBox;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Job Center forms
 */
public class CenterBaseForm extends BaseWingsForm {
    protected TextBox txbLocationAddressLineOne = new TextBox("css=input[id='contactInformation.addressByResidenceAddr.lineOne']",
            "LocationAddress - Line One");
    protected TextBox txbLocationAddressLineTwo = new TextBox("css=input[id='contactInformation.addressByResidenceAddr.lineTwo']",
            "LocationAddress - Line Two");
    protected TextBox txbLocationAddressCity = new TextBox("css=input[id='contactInformation.addressByResidenceAddr.city']",
            "LocationAddress - City");
    protected TextBox txbLocationAddressZipPostalCode = new TextBox("css=input[id='contactInformation.addressByResidenceAddr.zipcode']",
            "LocationAddress - Zip/Postal Code");
    protected ComboBox cmbLocationAddressState = new ComboBox("css=select[id='contactInformation.addressByResidenceAddr.state']",
            "LocationAddress - State");
    protected ComboBox cmbLocationAddressCountry = new ComboBox("css=select[id='contactInformation.addressByResidenceAddr.country']",
            "LocationAddress - Country");
    protected TextBox txbServiceCenterName = new TextBox(By.id("centerName"), "Service center name");

    /**
     * Default constructor of the form with specified locator
     * @param locator - locator
     * @param formTitle - title of the page
     */
    public CenterBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }
}
