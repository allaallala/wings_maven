package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import framework.elements.Button;
import org.openqa.selenium.By;

public class EmploymentHistoryForm extends ParticipantCreationForm {

    //Employment history
    private Button btnAddEmploymentHistory = new Button("id=addPreviousJob", "Add Employment");

    public EmploymentHistoryForm() {
        super(By.xpath("//h1[contains(.,'Participant Creation: Employment History')]"), "Participant Creation: Employment History");
    }

    public void addEmploymentHistory() {
        btnAddEmploymentHistory.clickAndWait();
    }

    public void skipEmploymentHistorySection() {
        clickButton(Buttons.Continue);
    }
}
