package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import org.openqa.selenium.By;

public class IdentificationSSForm extends ParticipantCreationForm {

    public IdentificationSSForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Identification')]"), "Participant S-S Creation: Identification");
    }

    private void inputFirstLastName(String firstName, String lastName) {
        txbFirstName.type(firstName);
        txbLastName.type(lastName);
    }

    private void fillCommonFieldsOnSecondPage(String firstName, String lastName) {
        inputFirstLastName(firstName, lastName);
        rbGenderMale.click();
        rbHispanicDisclose.click();
        rbNativeDisclose.click();
        rbAsianDisclose.click();
        rbAfricanDisclose.click();
        rbHawaiDisclose.click();
        rbWhiteDisclose.click();
    }


    public void fillSecondPageSelfServicesAndContinue(String firstName, String lastName) {
        fillCommonFieldsOnSecondPage(firstName, lastName);
        clickButton(Buttons.Continue);
    }


    public void checkErrorsSecondPage() {
        checkWarnText(FIRST_NAME, dvFirstNameWarn);
        checkWarnText(LAST_NAME, dvLastNameWarn);
        checkWarnText(SELECTION_REQUIRED, dvGenderWarn);
        checkWarnText(SELECTION_REQUIRED, dvLatinoWarn);
        checkWarnText(SELECTION_REQUIRED, dvAsianWarn);
        checkWarnText(SELECTION_REQUIRED, dvHawaiianWarn);
        checkWarnText(SELECTION_REQUIRED, dvAlaskanWarn);
        checkWarnText(SELECTION_REQUIRED, dvBlackWarn);
        checkWarnText(SELECTION_REQUIRED, dvCaucasianWarn);
    }
}
