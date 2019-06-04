package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.elements.ComboBox;
import org.openqa.selenium.By;

public class AcademicHistoryForm extends ParticipantCreationForm {

    private ComboBox cmbGrade = new ComboBox("css=select#highestGrade", "Highest Grade Completed");


    public AcademicHistoryForm() {
        super(By.xpath("//h1[contains(.,'Participant Creation: Academic History')]"), "Participant Creation: Academic History");
    }

    public void selectParticipantGrade(String valueToSelect) {
        cmbGrade.waitForIsElementPresent();
        cmbGrade.select(valueToSelect);
    }

    public void selectParticipantGradeAndContinue(Participant participant) {
        selectParticipantGrade(participant.getGrade());
        clickButton(Buttons.Continue);
    }

}
