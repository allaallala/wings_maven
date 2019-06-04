package edu.msstate.nsparc.wings.integration.forms.common;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import framework.BaseForm;
import webdriver.Browser;
import framework.elements.ComboBox;
import framework.elements.Div;
import framework.elements.TextBox;
import org.openqa.selenium.By;

public class ContactInformationForm extends BaseForm {
    private final Div divLoading = new Div(By.xpath("//img[@id='loading-indicator'] | //h1[text()='Loading...']"), "Loading image");
    // Location
    private TextBox txbLocationLineOne = new TextBox("//input[@name='contactInformation.addressByResidenceAddr.lineOne'] |"
            + " //input[@name='contactInformationByEmployerContactInfo.addressByResidenceAddr.lineOne']", "Location Address - Line One");
    private TextBox txbLocationLineTwo = new TextBox("//input[@name='contactInformation.addressByResidenceAddr.lineTwo'] |"
            + " //input[@name='contactInformationByEmployerContactInfo.addressByResidenceAddr.lineTwo']", "Location Address - Line Two");
    private ComboBox cmbLocationState = new ComboBox(By.xpath("//select[@id='contactInformation.addressByResidenceAddr.state'] |"
            + "//select[@id='contactInformationByEmployerContactInfo.addressByResidenceAddr.state']"), "Location Address - State");
    private TextBox txbLocationCity = new TextBox("//input[@name='contactInformation.addressByResidenceAddr.city'] |"
            + " //input[@name='contactInformationByEmployerContactInfo.addressByResidenceAddr.city']", "Location Address - City");
    private TextBox txbLocationZipCode = new TextBox("//input[@name='contactInformation.addressByResidenceAddr.zipcode'] |"
            + "//input[@name='contactInformationByEmployerContactInfo.addressByResidenceAddr.zipcode']", "Location Address - Zip Code");
    private ComboBox cmbLocationCounty = new ComboBox(By.xpath("//select[@id='contactInformation.addressByResidenceAddr.county'] |"
            + "//select[@id='contactInformationByEmployerContactInfo.addressByResidenceAddr.county']"), "County");

    public ContactInformationForm() {
        super(By.xpath("//*[contains(@id, 'contactInfo') or contains(@name, 'contactInfo')]"), "Contact Information form");
    }

     public void inputLocationCityZip(String locationOne, String locationTwo, String city, String zipCode) {
        txbLocationLineOne.type(locationOne);
        txbLocationLineTwo.type(locationTwo);
        txbLocationCity.type(city);
        txbLocationZipCode.type(zipCode);
    }

    public void selectLocationState(String locationState) {
        cmbLocationState.select(locationState);
    }

    public void inputLocationCity(String locationCity) {
        txbLocationCity.type(locationCity);
    }

    public String getLocationCity() {
        return txbLocationCity.getValue();
    }

    public void inputZipCode(String locationCity) {
        txbLocationZipCode.type(locationCity);
    }

    public String getZipCode() {
        return txbLocationZipCode.getValue();
    }

    public void selectLocationCounty(String locationCounty) {
        cmbLocationCounty.select(locationCounty);
    }

    public void checkLocationCountyPresenceState(Boolean present) {
        divLoading.waitForNotVisible();
        if (present) {
            cmbLocationCounty.assertIsPresentAndVisible();
        } else {
            cmbLocationCounty.assertIsNotPresent();
        }
    }

    public void clickLocationCounty() {
        cmbLocationCounty.click();
    }

    public boolean isLocationCountyPresent() {
        return cmbLocationCounty.isPresent();
    }
}
