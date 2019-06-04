package edu.msstate.nsparc.wings.integration.forms.jobCenter;

import edu.msstate.nsparc.wings.integration.models.administrative.ServiceCenters;
import framework.CommonFunctions;
import framework.elements.ComboBox;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Advanced -> Service Centers -> Search -> Find record and open it -> Click on Edit button
 */
public class CenterEditForm extends CenterBaseForm {
    private TextBox txbMailingAddressLineOne = new TextBox("css=input[id='contactInformation.addressByMailingAddr.lineOne']",
            "MailingAddress - Line One");
    private TextBox txbMailingAddressLineTwo = new TextBox("css=input[id='contactInformation.addressByMailingAddr.lineTwo']",
            "MailingAddress - Line Two");
    private TextBox txbMailingAddressCity = new TextBox("css=input[id='contactInformation.addressByMailingAddr.city']",
            "MailingAddress - City");
    private TextBox txbMailingAddressZipPostalCode = new TextBox("css=input[id='contactInformation.addressByMailingAddr.zipcode']",
            "MailingAddress - Zip/Postal Code");

    private TextBox txbPhoneNumber = new TextBox("css=input[id='contactInformation.primaryPhone']", "Phone Number");
    private TextBox txbExt = new TextBox("css=input[id='contactInformation.extNumber']", "Ext");
    private TextBox txbFaxNumber = new TextBox("css=input[id='contactInformation.faxNumber']", "Fax Number");
    private TextBox txbEmailAddress = new TextBox("css=input[id='contactInformation.emailAddress']", "Email Address");

    private ComboBox cmbMailingAddressState = new ComboBox("css=select[id='contactInformation.addressByMailingAddr.state']",
            "MailingAddress - State");
    private ComboBox cmbMailingAddressCountry = new ComboBox("css=select[id='contactInformation.addressByMailingAddr.country']",
            "MailingAddress - Country ");

    /**
     * Default constructor
     */
    public CenterEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'One Stop Center Edit')]"), "One Stop Center Edit");
    }

    /**
     * This method is used for edition Center participantSSDetails, Mailing and Location addresses
     *
     * @param centers - service center parameters to edit
     */
    public void editCenterDetails(ServiceCenters centers) {
        txbPhoneNumber.type(centers.getPhoneNumber());
        txbExt.type(centers.getExt());
        txbFaxNumber.type(centers.getFaxNumber());
        txbEmailAddress.type(centers.getEmailAddress());
        //Edit Location Address
        txbLocationAddressLineOne.type(centers.getLineOne());
        txbLocationAddressLineTwo.type(centers.getLineTwo());
        txbLocationAddressCity.type(centers.getCity());
        txbLocationAddressZipPostalCode.type(centers.getZip());
        cmbLocationAddressCountry.select(centers.getCountry());
        cmbLocationAddressState.waitForIsElementPresent();
        cmbLocationAddressState.select(centers.getState());
        //Edit Mailing Address
        txbMailingAddressLineOne.type(centers.getLineOne());
        txbMailingAddressLineTwo.type(centers.getLineTwo());
        txbMailingAddressCity.type(centers.getCity());
        txbMailingAddressZipPostalCode.type(centers.getZip());
        cmbMailingAddressCountry.select(centers.getCountry());
        cmbMailingAddressState.waitForIsElementPresent();
        cmbMailingAddressState.select(centers.getState());
    }

    /**
     * Edit some parameters
     *
     * @param center - service center.
     */
    public void editSomeParameters(ServiceCenters center) {
        String newLine = CommonFunctions.getRandomAlphanumericalCode(10);
        String newCity = CommonFunctions.getRandomAlphanumericalCode(6);
        String newZip = CommonFunctions.getRandomIntegerNumber(5);
        txbLocationAddressLineOne.type(CommonFunctions.getRandomAlphanumericalCode(10));
        txbLocationAddressCity.type(CommonFunctions.getRandomAlphanumericalCode(6));
        txbLocationAddressZipPostalCode.type(CommonFunctions.getRandomIntegerNumber(5));
        center.setLineOne(newLine);
        center.setCity(newCity);
        center.setZip(newZip);
    }

    /**
     * Inputs new value to the service center name field
     *
     * @param newName - new name.
     */
    public void editName(String newName) {
        txbServiceCenterName.type(newName);

    }
}
