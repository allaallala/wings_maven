package edu.msstate.nsparc.wings.integration.forms.participant;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.ComboBox;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Participant forms
 */
public class ParticipantBaseForm extends BaseWingsForm {
    protected TextBox txbFirstName = new TextBox("id=firstName", "First Name");
    protected TextBox txbLastName = new TextBox("id=lastName", "Last Name");
    protected ComboBox cmbEditVeteranStatus = new ComboBox("id=veteranDetail.isEligibleVeteran",
            "Veteran status");
    protected RadioButton rbTransServiceMemberYes = new RadioButton("id=veteranDetail.isTransServMemb1",
            "Transitioning service member -yes");
    protected TextBox tbDateActualSeparation = new TextBox("id=veteranDetail.dateActualSeparation",
            "Date actual military separation");
    protected RadioButton rbCampaingVeteranYes = new RadioButton("id=veteranDetail.isCampaignVet1",
            "Campaing veteran yes");
    protected ComboBox cbDisabledVeteran = new ComboBox("id=veteranDetail.isDisabledVet",
            "Disabled veteran");
    protected TextBox txbEmail = new TextBox("css=input[id='contactInformation.emailAddress']",
            "E-mail Address");
    protected ComboBox cmbDriversLicenseClass = new ComboBox(By.id("driversLicense.dlClass"),
            "Driver's License Class");
    protected TextBox txbSSN = new TextBox("css=input#ssn", "SSN");

    public ParticipantBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }
}

