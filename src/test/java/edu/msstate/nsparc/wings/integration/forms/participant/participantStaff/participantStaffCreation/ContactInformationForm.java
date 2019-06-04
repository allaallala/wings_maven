package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.elements.ComboBox;
import webdriver.Browser;
import org.openqa.selenium.By;

public class ContactInformationForm extends ParticipantCreationForm {
    private ComboBox cmbLocationState = new ComboBox(By.xpath("//select[@id='contactInformation.addressByResidenceAddr.state'] |"
            + "//select[@id='contactInformationByEmployerContactInfo.addressByResidenceAddr.state']"), "Location Address - State");

    private ComboBox cmbLocationCounty = new ComboBox(By.xpath("//select[@id='contactInformation.addressByResidenceAddr.county'] |"
            + "//select[@id='contactInformationByEmployerContactInfo.addressByResidenceAddr.county']"), "County");
    public ContactInformationForm() {
        super(By.xpath("//h1[contains(.,'Participant Creation: Contact Information')]"), "Participant Creation: Contact Information");
    }

    private void fillLocations(Participant participant) {
        chkEmail.click();
        chkPhone.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        if (rbJobVites.isPresent()) {
            rbJobVites.click();
        }
        inputLocationCityZip(participant.getAddress().getLineOne(), participant.getAddress().getCity(), participant.getAddress().getZipCode());
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        cmbLocationState.select(participant.getAddress().getState());

        if (btnApplyState.isPresent()) {
            btnApplyState.clickAndWait();
        }
        cmbLocationCounty.select(participant.getAddress().getCounty());
    }

    private void fillEmail(Participant participant) {
        txbEmail.type(participant.getEmail());
        if (chkAgreeEmail.isPresent()) {
            chkAgreeEmail.click();
        }
        if (txbEmailConfirm.isPresent()) {
            txbEmailConfirm.type(participant.getEmail());
        }
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        clickButton(Buttons.Continue);
        if (h1EmailVerification.isPresent()) {
            clickButton(Buttons.Continue);
        }
    }

    public void fillThirdPage(Participant participant) {
        fillLocations(participant);
        rbSameAddressYes.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        txbPhone.type(participant.getPrimaryPhone());
        fillEmail(participant);
    }

    public void fillContactInformationAndContinue(Participant participant) {
        fillThirdPage(participant);
        clickButton(Buttons.Continue);
    }
}
