package edu.msstate.nsparc.wings.integration.forms.relocation;

import edu.msstate.nsparc.wings.integration.forms.common.ContactInformationForm;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import framework.elements.ComboBox;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Trade -> Relocation -> Create
 */
public class RelocationCreateForm extends RelocationBaseForm {

    private ComboBox cmbTradeEnrollment = new ComboBox("css=select[id='selectedTradeEnrollmentId']", "Trade Enrollment");
    private TextBox txbRelocationDate = new TextBox("css=input[id='dateRelocation']", "Relocation Date");
    private TextBox txbRelocationDistance = new TextBox("css=input[id='mileage']", "Relocation Distance");
    private TextBox txbEmployerName = new TextBox("css=input[id='employerName']", "Employer Name");

    private TextBox txbLocationLineOne = new TextBox("css=input[id='contactInformationByEmployerContactInfo.addressByResidenceAddr.lineOne']", "Location - Line One");
    private TextBox txbLocationCity = new TextBox("css=input[id='contactInformationByEmployerContactInfo.addressByResidenceAddr.city']", "Location - City");
    private TextBox txbLocationZipCode = new TextBox("css=input[id='contactInformationByEmployerContactInfo.addressByResidenceAddr.zipcode']", "Location - Zip Code");

    private TextBox txbContactName = new TextBox("css=input[id='employerContactName']", "Contact Name");
    private TextBox txbPhoneNumber = new TextBox("css=input[id='contactInformationByEmployerContactInfo.primaryPhone']", "Phone Number");

    private TextBox txbOldAddressLineOne = new TextBox("css=input[id='oldAddress.lineOne']", "Old Address - Line One");
    private TextBox txbOldAddressCity = new TextBox("css=input[id='oldAddress.city']", "Old Address - City");
    private ComboBox cmbOldAddressState = new ComboBox("css=select[id='oldAddress.state']", "Old Address - State");
    private TextBox txbOldAddressZipCode = new TextBox("css=input[id='oldAddress.zipcode']", "Old Address - Zip Code");
    private ComboBox cmbOldAddressCounty = new ComboBox("css=select[id='oldAddress.county']", "Old Address - County");

    private TextBox txbNewAddressLineOne = new TextBox("css=input[id='newAddress.lineOne']", "New Address - Line One");
    private TextBox txbNewAddressCity = new TextBox("css=input[id='newAddress.city']", "New Address - City");
    private ComboBox cmbNewAddressState = new ComboBox("css=select[id='newAddress.state']", "New Address - State");
    private TextBox txbNewAddressZipCode = new TextBox("css=input[id='newAddress.zipcode']", "New Address - Zip Code");
    private ComboBox cmbNewAddressCounty = new ComboBox("css=select[id='newAddress.county']", "New Address - County");

    /**
     * Default constructor
     */
    public RelocationCreateForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Relocation Creation')]"), "Relocation Creation");
    }

    /**
     * Select participant and trade enrollment
     *
     * @param relocation Object with Relocation data
     */
    private void fillOutParticipantInfo(Relocation relocation) {
        selectParticipant(relocation.getTradeEnrollment().getParticipant());
        cmbTradeEnrollment.selectFirst();
    }

    /**
     * Fill out application, relocation dates and relocation distance
     *
     * @param relocation Object with Relocation data
     */
    private void fillOutDateInfo(Relocation relocation) {
        inputApplicationDate(relocation.getApplicationDate());
        txbRelocationDate.type(relocation.getRelocationDate());
        txbRelocationDistance.type(relocation.getRelocationDistance());
    }

    /**
     * Fill out employer name, contact, phone
     *
     * @param relocation Object with Relocation data
     */
    private void fillOutEmployerInfo(Relocation relocation) {
        txbEmployerName.type(relocation.getEmployerName());
        txbContactName.type(relocation.getContactName());
        txbPhoneNumber.type(relocation.getPhoneNumber());
    }

    /**
     * Fill out location fields
     *
     * @param relocation Object with Relocation data
     */
    private void fillOutLocationInfo(Relocation relocation) {
        txbLocationLineOne.type(relocation.getLocationAddress().getLineOne());
        txbLocationCity.type(relocation.getLocationAddress().getCity());
        ContactInformationForm infoForm = new ContactInformationForm();
        infoForm.selectLocationState(relocation.getLocationAddress().getState());
        txbLocationZipCode.type(relocation.getLocationAddress().getZipCode());
        if (infoForm.isLocationCountyPresent()) {
            infoForm.selectLocationCounty(relocation.getLocationAddress().getCounty());
        }
    }

    /**
     * Fill out old address fields
     *
     * @param relocation Object with Relocation data
     */
    private void fillOutOldAddressInfo(Relocation relocation) {
        txbOldAddressLineOne.type(relocation.getOldAddress().getLineOne());
        txbOldAddressCity.type(relocation.getOldAddress().getCity());
        cmbOldAddressState.select(relocation.getOldAddress().getState());
        txbOldAddressZipCode.type(relocation.getOldAddress().getZipCode());
        if (cmbOldAddressCounty.isPresent()) {
            cmbOldAddressCounty.select(relocation.getOldAddress().getCounty());
        }
    }

    /**
     * Fill out new address fields
     *
     * @param relocation Object with Relocation data
     */
    private void fillOutNewAddressInfo(Relocation relocation) {
        txbNewAddressLineOne.type(relocation.getNewAddress().getLineOne());
        txbNewAddressCity.type(relocation.getNewAddress().getCity());
        cmbNewAddressState.select(relocation.getNewAddress().getState());
        txbNewAddressZipCode.type(relocation.getNewAddress().getZipCode());
        if (cmbNewAddressCounty.isPresent()) {
            cmbNewAddressCounty.select(relocation.getNewAddress().getCounty());
        }
    }

    /**
     * Filling out the creation form
     *
     * @param relocation Object with Relocation data
     */
    public void fillOutCreationForm(Relocation relocation) {
        fillOutParticipantInfo(relocation);
        fillOutDateInfo(relocation);
        fillOutEmployerInfo(relocation);
        fillOutLocationInfo(relocation);
        fillOutOldAddressInfo(relocation);
        fillOutNewAddressInfo(relocation);
    }

    /**
     * Fill out relocation creation form without selecting participant
     *
     * @param relocation Object with Relocation data
     */
    public void fillOutCreationFormWithoutParticipant(Relocation relocation) {
        fillOutDateInfo(relocation);
        fillOutEmployerInfo(relocation);
        fillOutLocationInfo(relocation);
        fillOutOldAddressInfo(relocation);
        fillOutNewAddressInfo(relocation);
    }
}
