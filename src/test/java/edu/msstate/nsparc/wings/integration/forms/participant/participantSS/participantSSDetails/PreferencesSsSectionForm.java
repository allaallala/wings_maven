package edu.msstate.nsparc.wings.integration.forms.participant.participantSS.participantSSDetails;

import framework.customassertions.CustomAssertion;
import framework.elements.TableCell;
import org.openqa.selenium.By;

public class PreferencesSsSectionForm extends BaseParticipantSsDetailsForm {

    private TableCell tbcHoursWeek = new TableCell(By.xpath(String.format(parameterPath, "Hours per Week willing to Work")), "Hours per week");
    private TableCell tbcDistanceRelocate = new TableCell(By.xpath(String.format(parameterPath, "Distance willing to Relocate")), "Distance to Relocate");
    private TableCell tbcDesiredSalary = new TableCell(By.xpath(String.format(parameterPath, "Desired Salary:")), "Desired Salary");
    private TableCell tbcDistanceCommute = new TableCell(By.xpath(String.format(parameterPath, "Distance willing to Commute")), "Distance to Commute");

    public PreferencesSsSectionForm() {
        super(By.xpath("//h3[contains(.,'Employment Preferences')]"), "Employment Preferences");
    }

    public void validatePreferences(Boolean checkAll, String hoursWeek, String desiredSalary, String distanceRelocate, String distanceCommute) {
        CustomAssertion.softAssertContains(tbcDesiredSalary.getText(), desiredSalary, "Incorrect desired salary on the page.");
        if (checkAll) {
            CustomAssertion.softAssertContains(tbcHoursWeek.getText(), hoursWeek, "Incorrect hours per week");
            CustomAssertion.softAssertContains(tbcDistanceRelocate.getText(), distanceRelocate + " Miles", "Incorrect distance willing to relocate.");
            CustomAssertion.softAssertContains(tbcDistanceCommute.getText(), distanceCommute + " Miles", "Incorrect distance willing to commute");
        }
    }

    public void preferencesAreNotPresent() {
        tbcHoursWeek.softIsNotPresent();
        tbcDistanceRelocate.softIsNotPresent();
        tbcDistanceCommute.softIsNotPresent();
    }
}
