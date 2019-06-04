package edu.msstate.nsparc.wings.integration.forms.relocation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import framework.elements.ComboBox;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Trade -> Relocation -> Search for record -> Open record -> Click on Edit button
 */
public class RelocationEditForm extends RelocationBaseForm {

    private RadioButton rbApproved = new RadioButton("css=input[id='isApproved1']", "Approved");
    private RadioButton rbDenied = new RadioButton("css=input[id='isApproved2']", "Denied");
    private TextBox txbStatusDeterminationDate = new TextBox("css=input[id='dateStatusDetermination']", "Status Determination Date");
    private TextBox txbReasonDenied = new TextBox("id=deniedReason", "Reason Denied");
    private TextBox txbRelocationDate = new TextBox("css=input[id='dateRelocation']", "Relocation Date");
    private TextBox txbApplicationDate = new TextBox("css=input[id='dateApplication']", "Application Date");

    private ComboBox cmbOldAddressState = new ComboBox("css=select[id='oldAddress.state']", "Old Address - State");
    private ComboBox cmbOldAddressCounty = new ComboBox("css=select[id='oldAddress.county']", "Old Address - County");

    private ComboBox cmbNewAddressState = new ComboBox("css=select[id='newAddress.state']", "New Address - State");
    private ComboBox cmbNewAddressCounty = new ComboBox("css=select[id='newAddress.county']", "New Address - County");

    /**
     * Default constructor
     */
    public RelocationEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Relocation Edit')]"), "Relocation Edit");
    }

    /**
     * Editing Relocation participantSSDetails
     *
     * @param relocation Object with new participantSSDetails
     */
    public void editDetails(Relocation relocation) {
        if (relocation.isApproved()) {
            rbApproved.click();
        } else {
            rbDenied.click();
            txbReasonDenied.type(relocation.getReasonDenied());
        }
        txbStatusDeterminationDate.type(relocation.getStatusDeterminationDate());
    }

    /**
     * Edit Relocation Date
     *
     * @param relocation Relocation object
     */
    public void editRelocationDate(Relocation relocation) {
        txbRelocationDate.type(relocation.getRelocationDate());
    }

    /**
     * Edit application date
     *
     * @param relocation Relocation object
     */
    public void editApplicationDate(Relocation relocation) {
        txbApplicationDate.type(relocation.getApplicationDate());
    }

    /**
     * Edit Relocation data: Relocation Date and Status related fields
     *
     * @param relocation Relocation object
     */
    public void editRelocationDateAndStatusAndSave(Relocation relocation) {
        editRelocationDate(relocation);
        editDetails(relocation);
        clickAndWait(BaseButton.SAVE_CHANGES);
    }

    /**
     * Select old/new state county
     *
     * @param valueSelect - value to be selected.
     */
    public void selectStateCounty(String valueSelect) {
        selectLocationState(valueSelect);
        cmbOldAddressState.select(valueSelect);
        cmbNewAddressState.select(valueSelect);

        info("Check that 'County' combo-box is appeared for Employer Information, Old Address, New Address");
        checkLocationCountyPresent(Constants.TRUE);
        cmbOldAddressCounty.isPresent();
        cmbNewAddressCounty.isPresent();
    }
}
