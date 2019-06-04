package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSCreation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import org.openqa.selenium.By;

public class EmploymentPreferencesSSForm extends ParticipantCreationForm {

    public EmploymentPreferencesSSForm() {
        super(By.xpath("//div[@id='heading-title'][contains(.,'Employment Preferences')]"), "Participant S-S Creation: Employment Preferences");
    }


    public void fillEmploymentPrefAndContinue() {
        txbHoursPerWeek.type(Constants.HOURS_MILES.toString());
        txbWillingRelocate.type(Constants.HOURS_MILES.toString());
        txbWillingCommute.type(Constants.HOURS_MILES.toString());
        clickButton(Buttons.Continue);
    }
}
