package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.CommonFunctions;
import org.openqa.selenium.By;

public class AcademicRecordSSForm extends ParticipantCreationForm {

    public AcademicRecordSSForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Academic Record')]"), "Participant S-S Creation: Academic Record");
    }


    public void fillEducationSelfServicePageAndContinue(Participant participant) {
        fillEducationSelfServicePage(participant);
        clickButton(Buttons.Continue);
    }


    public void fillEducationSelfServicePage(Participant participant) {
        participant.setGpa(participantGPA);
        txbSchoolName.type(participant.getCertification());
        txbSchoolCity.type(participant.getAddress().getCity());
        txbZip.type(participant.getAddress().getZipCode());
        cmbState.select(participant.getAddress().getState());
        txbDateSchoolStart.type(CommonFunctions.getYesterdayDate());
        txbDateGraduated.type(CommonFunctions.getCurrentDate());
        cmbDegree.select(participant.getDegree());
        txbGpa.type(participantGPA);
        btnAddAcademicRecord.clickAndWait();
        btnEditAddedRecord.isPresent();
    }
}
