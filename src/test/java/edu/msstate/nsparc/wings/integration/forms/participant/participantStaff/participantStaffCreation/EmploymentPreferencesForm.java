package edu.msstate.nsparc.wings.integration.forms.participant.participantStaff.participantStaffCreation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantCreationForm;
import framework.elements.TextBox;
import org.openqa.selenium.By;

public class EmploymentPreferencesForm extends ParticipantCreationForm {

    private TextBox txbHoursPerWeek = new TextBox(By.id("jobInterest.hoursPerWeek"), "Hours per week");
    private TextBox txbWillingRelocate = new TextBox(By.id("jobInterest.maxDistRelocation"), "Max distance relocation");
    private TextBox txbWillingCommute = new TextBox(By.id("jobInterest.maxDistCommute"), "Max distance commute");

    public EmploymentPreferencesForm() {
        super(By.xpath("//h1[contains(.,'Participant Creation: Employment Preferences')]"), "Participant Creation: Employment Preferences");
    }


    public void fillEmploymentPref() {
        txbHoursPerWeek.type(Constants.HOURS_MILES.toString());
        txbWillingRelocate.type(Constants.HOURS_MILES.toString());
        txbWillingCommute.type(Constants.HOURS_MILES.toString());
        clickButton(Buttons.Continue);
    }
}
