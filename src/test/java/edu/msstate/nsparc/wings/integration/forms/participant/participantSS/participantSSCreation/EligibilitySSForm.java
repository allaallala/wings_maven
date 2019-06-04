package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import org.openqa.selenium.By;

public class EligibilitySSForm extends ParticipantCreationForm {

    public EligibilitySSForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Eligibility')]"), "Participant S-S Creation: Eligibility");
    }

    public void fillFirstPageSelfServicesAndContinue(String ssn, String dateOfBirth) {
        fillFirstPageSelfServices(ssn, dateOfBirth);
        clickButton(Buttons.Continue);
    }

    public void fillFirstPageSelfServices(String ssn, String dateOfBirth) {
        rbUnitedStatesCitizenYes.click();
        txbSSN.type(ssn);
        txbDateOfBirth.type(dateOfBirth);

    }

    public void checkErrorsFirstPage() {
        checkWarnText(SELECTION_REQUIRED, dvTextCitizenWarn);
        checkWarnText(DATE_BIRTH, dvDateBirthWarn);
    }
}
