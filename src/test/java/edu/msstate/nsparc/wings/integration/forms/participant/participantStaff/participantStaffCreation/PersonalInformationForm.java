package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.customassertions.CustomAssertion;
import org.openqa.selenium.By;

public class PersonalInformationForm extends ParticipantCreationForm {

    public PersonalInformationForm() {
        super(By.xpath("//h1[contains(.,'Participant Creation: Personal Information')]"), "Participant Creation: Personal Information");
    }

    private void inputFirstLastName(String firstName, String lastName) {
        txbFirstName.type(firstName);
        txbLastName.type(lastName);
    }

    public void selectCitizenship(String citizenship) {
        cmbUSCitizen.select(citizenship);
    }

    public void fillCommonFieldsOnSecondPage(Participant participant) {
        inputFirstLastName(participant.getFirstName(), participant.getLastName());
        rbGenderMale.click();
        rbHispanicDisclose.click();
        rbNativeDisclose.click();
        rbAsianDisclose.click();
        rbAfricanDisclose.click();
        rbHawaiDisclose.click();
        rbWhiteDisclose.click();
    }

    public void fillExactSecondPage(Participant participant, Boolean veteran, Boolean nguard) {
        selectCitizenship(participant.getCitizenship());
        fillCommonFieldsOnSecondPage(participant);
        if (veteran) {
            rbVeteranYes.click();
        } else {
            rbVeteranNo.click();
        }
        rbDisabledNo.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        if (nguard) {
            rbNationalGuardYes.click();
        } else {
            rbNationalGuardNo.click();
        }
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        selectEmploymentStatus(NOT_EMPLOYEED);
        if (rbDriversLicenseYes.isPresent()) {
            rbDriversLicenseYes.click();
            cmbDriversLicenseClass.waitForIsElementPresent();
            cmbDriversLicenseClass.select(driverLicense);
        }
    }

    /**
     * Select employment status
     * @param employmentStatus - employment status.
     */
    private void selectEmploymentStatus(String employmentStatus) {
        select(BaseComboBox.EMPLOYMENT_STATUS, employmentStatus);
    }

    public void fillSecondPageAndContinue(Participant participant, Boolean isVeteran, Boolean isNationalGuard) {
        fillSecondPage(participant, isVeteran, isNationalGuard);
        clickButton(Buttons.Continue);
    }

    public void fillSecondPage(Participant participant, Boolean isVeteran, Boolean isNationalGuard) {
        selectCitizenship(participant.getCitizenship());
        fillCommonFieldsOnSecondPage(participant);
        if (isVeteran) {
            rbVeteranYes.click();
        } else {
            rbVeteranNo.click();
        }
        rbDisabledNo.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();

        if (isNationalGuard) {
            rbNationalGuardYes.click();
        } else {
            rbNationalGuardNo.click();
        }
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        selectEmploymentStatus(NOT_EMPLOYEED);
        if (rbDriversLicenseYes.isPresent()) {
            rbDriversLicenseYes.click();
            cmbDriversLicenseClass.waitForIsElementPresent();
            cmbDriversLicenseClass.select(driverLicense);
        }
    }

    public void checkPartipData(Participant participant) {
        CustomAssertion.softAssertEquals(txbFirstName.getValue(), participant.getFirstName(),
                "First name assert fail");
        CustomAssertion.softAssertEquals(txbLastName.getValue(), participant.getLastName(),
                "Last name assert fail");
        CustomAssertion.softAssertEquals(txbSSN.getValue(), participant.getSsn(),
                "SSN assert fail");
        CustomAssertion.softAssertEquals(txbDateOfBirth.getValue(), participant.getParticipantBioInfo().getDateOfBirth(),
                "Date of birth assert fail");
    }
}

