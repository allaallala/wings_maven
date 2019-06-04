package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import org.openqa.selenium.By;

public class ClassificationSSForm extends ParticipantCreationForm {

    public ClassificationSSForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Classification')]"), "Participant S-S Creation: Classification");
    }

    public void fillCertificationSectionAndContinue(boolean veteran, boolean mng) {
        fillCertificationSection(veteran, mng);
        clickButton(Buttons.Continue);
    }

    public void fillCertificationSection(boolean veteran, boolean mng) {
        if (veteran) {
            rbVeteranYes.click();
        } else {
            rbVeteranNo.click();
        }
        rbDisabledNo.click();

        rbDoYouHaveJobNo.click();
        if (mng) {
            rbNationalGuardYes.click();
        } else {
            rbNationalGuardNo.click();
        }
    }

    /**
     * Check errors on the third page
     */
    public void checkErrorsThirdPage() {
        checkWarnText(SELECTION_REQUIRED, dvVeteranWarn);
        checkWarnText(SELECTION_REQUIRED, dvNationalWarn);
        checkWarnText(SELECTION_REQUIRED, dvDisabilityWarn);
        checkWarnText(SELECTION_REQUIRED, dvJobWarn);
    }
}
