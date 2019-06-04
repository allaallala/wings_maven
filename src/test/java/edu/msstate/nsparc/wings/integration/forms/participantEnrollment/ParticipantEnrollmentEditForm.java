package edu.msstate.nsparc.wings.integration.forms.participantEnrollment;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Participant Service Enrollment -> Search for record -> Open record -> Edit
 */
public class ParticipantEnrollmentEditForm extends ParticipantEnrollmentBaseForm {

    private TextBox txbAnswer = new TextBox("css=form#serviceEnrollment input", "Question Answer");
    private TextBox txbServiceBegin = new TextBox("id=datePosted", "Service begin date");
    private TextBox txbServiceEnd = new TextBox("id=dateResult", "Service end date");

    /**
     * Default constructor
     */
    public ParticipantEnrollmentEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Enrollment Edit')]"), "Enrollment Edit");
    }

    /**
     * Answer question
     *
     * @param answer - answer.
     */
    public void answerQuestion(String answer) {
        txbAnswer.type(answer);
    }

    /**
     * Input begin service
     *
     * @param beginService - begin service date
     * @param endService   - end service date
     */
    public void inputBeginEndService(String beginService, String endService) {
        txbServiceBegin.type(beginService);
        txbServiceEnd.type(endService);
    }

    public void inputServiceSave(String beginService, String endService) {
        inputBeginEndService(beginService, endService);
        clickButton(Buttons.Save);
    }
}
