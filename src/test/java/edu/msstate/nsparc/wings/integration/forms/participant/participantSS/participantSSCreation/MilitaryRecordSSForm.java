package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import framework.CommonFunctions;
import framework.elements.ComboBox;
import org.openqa.selenium.By;

public class MilitaryRecordSSForm extends ParticipantCreationForm {

    private ComboBox cmbNationalGuard = new ComboBox(By.xpath("//input[@value='NATIONAL_GUARD']"), "National Guard ");

    public MilitaryRecordSSForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Military Record')]"), "Participant S-S Creation: Military Record");
    }

    public void fillMilitarySelfServicePageAndContinue() {
        rbMarineCorps.click();
        tbMilitaryDateBegin.type(CommonFunctions.getYesterdayDate());
        btnAddMilitary.clickAndWait();
        clickButton(Buttons.Continue);
    }


    public void fillInNotEligibleVeteranAndContinue() {
        cmbNationalGuard.click();
        clickButton(Buttons.Continue);
    }
}
