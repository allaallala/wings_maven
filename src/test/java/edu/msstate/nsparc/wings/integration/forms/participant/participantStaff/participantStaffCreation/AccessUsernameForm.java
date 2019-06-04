package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation;

import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.elements.Button;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

public class AccessUsernameForm extends ParticipantCreationForm {

    // Page #8
    private RadioButton rbHaveAccess = new RadioButton(By.xpath("//input[@value='y']"), "Have Unemployment Services System");
    private TextBox txbAccessUsername = new TextBox(By.id("applicationUser.username"), "Unemployment Services System Username");
    private Button btnCreateParticipant = new Button(By.xpath("//button[@title='Create']"), "Create");


    public AccessUsernameForm() {
        super(By.xpath("//h1[contains(.,'Participant Creation: Access Username')]"), "Participant Creation: Access Username");
    }

    /**
     * This method is used for filling fields on last page of Participant creation process
     *
     * @param participant Data for creation
     */
    public void fillMsAccessAccount(Participant participant) {
        rbHaveAccess.click();
        waitDivLoading();
        txbAccessUsername.waitForIsElementPresent();
        txbAccessUsername.type(participant.getParticipantAccount());
    }

    /**
     * Complete registration
     */
    public void clickFinish() {
        btnCreateParticipant.clickAndWait();
    }

    public void fillMsAccessAccountAndSave(Participant participant) {
        fillMsAccessAccount(participant);
        clickFinish();
    }
}
