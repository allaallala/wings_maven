package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import framework.elements.RadioButton;
import org.openqa.selenium.By;

public class AdditionalInformationForm extends ParticipantCreationForm {

    private RadioButton rbDriversLicenseYes = new RadioButton(By.id("haveDriversLicense1"), "Drivers License - Yes");

    public AdditionalInformationForm() {
        super(By.xpath("//h1[contains(.,'Participant Creation: Additional Information')]"), "Participant Creation: Additional Information");
    }

    public void fillFourthPage(Boolean isDriver) {
        if (isDriver) {
            fillFourthPage();
        } else fillFourthPageNotDriver();

    }

    public void fillFourthPageAndContinue(Boolean isDriver) {
        fillFourthPage(isDriver);
        clickButton(Buttons.Continue);
    }

    /**
     * This method is used for filling fourth page of Participant creation process
     * Participant will have Drivers License
     */
    public void fillFourthPage() {
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        rbDriversLicenseYes.waitForIsElementPresent();
        rbDriversLicenseYes.clickAndWait();
        if (btnApply.isPresent()) {
            btnApply.clickAndWait();
        }
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        cmbDriversLicenseClass.waitForIsElementPresent();
        cmbDriversLicenseClass.select(driverLicense);
        fillFourthPageNotDriver();
    }

    public void fillFourthPageNotDriver() {
        rbSelectiveService.click();
        rbHomelessRunawayNo.click();
        rbOffenderNo.click();
        cmbSSI.select(ssi);
        rbFarmWorkerNo.click();
        rbJobCorpsNo.click();
        rbIndianNo.click();
        rbYouthNo.click();
        if (rbTitleVNo.isPresent()) {
            rbTitleVNo.click();
        }
    }

    public void fillFourthPageNotDriverAndContinue() {
        fillFourthPageNotDriver();
        clickButton(Buttons.Continue);
    }
}
