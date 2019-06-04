package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffDetail;

import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantBaseForm;
import framework.elements.ComboBox;
import org.openqa.selenium.By;

public class EditAcademicHistoryForm extends ParticipantBaseForm {

    private ComboBox cmbGrade = new ComboBox("css=select#highestGrade", "Highest Grade Completed");

    public EditAcademicHistoryForm() {
        super(By.xpath("//h1[contains(.,'Academic History')]"), "Participant Creation: Employment History");
    }

    public void selectDegree(String degree) {
        cmbGrade.select(degree);
    }

}
