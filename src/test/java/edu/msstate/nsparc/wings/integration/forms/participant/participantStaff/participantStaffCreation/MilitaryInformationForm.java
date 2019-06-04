package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import framework.CommonFunctions;
import org.openqa.selenium.By;

public class MilitaryInformationForm extends ParticipantCreationForm {

    public MilitaryInformationForm() {
        super(By.xpath("//h1[contains(.,'Military Information')]"), "Participant Creation: Military Information");
    }

    public void selectVeteranStatus(String veteranStatus) {
        cmbEditVeteranStatus.select(veteranStatus);
    }

    public void fillInEligibleVeteranAndContinue() {
        cmbEditVeteranStatus.select(veteranStatus);
        if (btnApply.isPresent()) {
            btnApply.clickAndWait();
        }
        rbTransServiceMemberYes.click();
        tbDateActualSeparation.type(CommonFunctions.getYesterdayDate());
        rbCampaingVeteranYes.click();
        cbDisabledVeteran.select(answerNo);
        clickButton(Buttons.Continue);

    }

    public void fillInNotEligibleVeteranAndContinue() {

        cmbEditVeteranStatus.select(ineligibleVeteran);
        if (btnApply.isPresent()) {
            btnApply.clickAndWait();
        }
        clickButton(Buttons.Continue);
    }
}
