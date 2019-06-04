package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import org.openqa.selenium.By;

public class LookupProfileForm extends ParticipantCreationForm {

    public LookupProfileForm() {
        super(By.xpath("//h1[contains(.,'Lookup Profile')]"), "Participant Creation: Lookup Profile");
    }

    public void fillFirstPageAndContinue(String ssn, String dateOfBirth) {
        fillFirstPage(ssn, dateOfBirth);
        clickButton(Buttons.Continue);
    }

    public void fillFirstPage(String ssn, String dateOfBirth) {
        txbSSN.type(ssn);
        txbDateOfBirth.type(dateOfBirth);
    }
}
