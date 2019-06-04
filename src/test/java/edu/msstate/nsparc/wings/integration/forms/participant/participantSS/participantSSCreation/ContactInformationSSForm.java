package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import org.openqa.selenium.By;

public class ContactInformationSSForm extends ParticipantCreationForm {

    public ContactInformationSSForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Contact Information')]"), "Participant S-S Creation: Contact Information");
    }

    private void fillEmail(Participant participant) {
        txbEmail.type(participant.getEmail());
        if (chkAgreeEmail.isPresent()) {
            chkAgreeEmail.click();
        }
        if (txbEmailConfirm.isPresent()) {
            txbEmailConfirm.type(participant.getEmail());
        }
    }

    /**
     * Fill out locations data
     *
     * @param participant - participant
     */
    private void fillLocations(Participant participant) {
        chkEmail.click();
        chkPhone.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        if (rbJobVites.isPresent()) {
            rbJobVites.click();
        }
        inputLocationCityZip(participant.getAddress().getLineOne(), participant.getAddress().getCity(),
                participant.getAddress().getZipCode());
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        selectLocationState(participant.getAddress().getState());
        if (btnApplyState.isPresent()) {
            btnApplyState.clickAndWait();
        }
        selectLocation(participant.getAddress().getCounty());
    }

    private void copyLocation(Participant partp) {
        tbCopiedAddress.type(partp.getAddress().getLineOne());
        tbCopiedCity.type(partp.getAddress().getCity());
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        cbCopiedState.select(partp.getAddress().getState());
        tbCopiedZip.type(partp.getAddress().getZipCode());
        cbLocationCounty.select(partp.getAddress().getCounty());
    }


    public void fillContactInformationForm(Participant participant) {
        fillLocations(participant);
        btnCopyAddress.clickAndWait();
        txbPhone.type(participant.getPrimaryPhone());
        copyLocation(participant);
        fillEmail(participant);
    }

    public void fillContactInformationFormAndContinue(Participant participant) {
        fillContactInformationForm(participant);
        clickButton(Buttons.Continue);
    }

    /**
     * Check errors on the address page.
     */
    public void checkErrorsAddressPage() {
        checkWarnText(LINE_ONE, dvResLineOneWarn);
        checkWarnText(CITY, dvResCityWarn);
        checkWarnText(STATE, dvResStateWarn);
        checkWarnText(ZIP, dvResZipWarn);
        checkWarnText(LINE_ONE, dvMailLineOneWarn);
        checkWarnText(CITY, dvMailCityWarn);
        checkWarnText(STATE, dvMailStateWarn);
        checkWarnText(ZIP, dvMailZipWarn);
    }

}
