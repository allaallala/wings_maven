package edu.msstate.nsparc.wings.integration.forms.youthGoals;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.CommonFunctions;
import framework.elements.Div;
import framework.elements.Link;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> WIA -> Youth Goals -> Search for record -> Open record
 */
public class YouthGoalsDetailsForm extends YouthGoalsBaseForm {
    private Div dvValidationError = new Div(By.id("id.errors"), "Validation errors found on page");
    private String validationErrorText = "Validation errors found on page.";
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private String xpath = "//form[@id='command']" + detailPath;
    private TableCell tbcParticipant = new TableCell(By.xpath("//form[@id='command']//td[contains(.,'Participant')]/following-sibling::td/span"), "Participant");
    private TableCell tbcEnrollment = new TableCell(By.xpath(String.format(xpath, "WIA Enrollment")), "WIA Enrollment");
    private TableCell tbcYoProvider = new TableCell(By.xpath(String.format(xpath, "Youth Provider")), "Youth Provider");
    private TableCell tbcDateGoalSet = new TableCell(By.xpath(String.format(xpath, "Date Goal Set")), "Date Goal Set");
    private TableCell tbcGoalType = new TableCell(By.xpath(String.format(xpath, "Goal Type")), "Goal Type");
    private Link lnkAddNote = new Link("css=span#LinkNotesDiv > a", "Add note link");
    private String youthProvider = "Automation Youth Provider (123)";


    /**
     * Constructor.
     */
    public YouthGoalsDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Youth Goal Detail')]"), "Youth Goal Detail");
    }

    /**
     * Click add note.
     */
    public void clickAddNote() {
        lnkAddNote.click();
    }

    /**
     * Validate youth goals data
     *
     * @param partip   - participant
     * @param dateSet  - set date
     * @param typeGoal - type goal
     */
    public void validateYouthGoalData(Participant partip, String dateSet, String typeGoal) {
        checkField(tbcParticipant, partip.getNameForSearchPages(), Constants.TRUE);
        checkField(tbcEnrollment, String.format("%1$s Youth", CommonFunctions.getYesterdayDate()), Constants.FALSE);
        checkField(tbcYoProvider, youthProvider, true);
        checkField(tbcDateGoalSet, dateSet, true);
        checkField(tbcGoalType, typeGoal, true);

    }

    /**
     * Check, that specified buttons are present or not on the page
     *
     * @param btnEdit - Edit button present
     * @param audit   Audit button present
     */
    public void checkButtonsPresent(Boolean btnEdit, Boolean audit) {
        divideMessage("Edit");
        ifButton(btnEdit, BaseButton.EDIT.getButton());
        divideMessage("Audit");
        ifButton(audit, BaseButton.AUDIT.getButton());
    }
}
