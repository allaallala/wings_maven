package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import org.openqa.selenium.By;

public class AccomplishmentsSSForm extends ParticipantCreationForm {

    public AccomplishmentsSSForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Accomplishments')]"), "Participant S-S Creation: Accomplishments");
    }

    public void fillAccomplishmentSectionAndContinue() {
        fillAccomplishmentSection();
        clickButton(Buttons.Continue);
    }

    public void fillAccomplishmentSection() {
        cmbHighestGrade.selectFirst();
        rbDriversLicenseNo.click();
        if (btnApply.isPresent()) {
            btnApply.clickAndWait();
        }
    }

    public void fillAccomplishmentSection(Participant participant) {
        cmbHighestGrade.select(participant.getGrade());
        rbDriversLicenseNo.click();
        if (btnApply.isPresent()) {
            btnApply.clickAndWait();
        }
    }

    public void fillAccomplishmentSectionAndContinue(Participant participant) {
        fillAccomplishmentSection(participant);
        clickButton(Buttons.Continue);
    }

    /**
     * Check errors on the third page
     */
    public void checkErrorsFourthPage() {
        checkWarnText(HIGHEST_GRADE, dvHighestGrade);
    }

}
