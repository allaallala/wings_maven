package edu.msstate.nsparc.wings.integration.forms.trainingWaiver;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.*;
import org.openqa.selenium.By;

/**
 * This is the base class for Training Waivers forms
 */
public class TrainingWaiverBaseForm extends BaseWingsForm {

    private ComboBox cmbWaiverReason = new ComboBox("css=select[id='waiverReason']", "Waiver Reason");
    private Span spnValidationErrorsFoundOnPage = new Span("id=id.errors", "Validation errors found on page.");
    private TextBox txbIssueDate = new TextBox("css=input[id='dateIssued']", "Issue Date");
    private RadioButton rbIneligible = new RadioButton("css=input[id='isWaiverEligible2']", "Ineligible");
    private CheckBox chkFirstIneligibleReason = new CheckBox("id=isNotMeetingCriteria1",
            "Did not meet any of the criteria for which a waiver may be issued");

    /**
     * Constructor of the form with defined locator
     *
     * @param locator   - locator of the form
     * @param formTitle - title of the form.
     */
    public TrainingWaiverBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Select training waiver reason
     *
     * @param reason - reason to select
     */
    public void selectWaiverReason(String reason) {
        cmbWaiverReason.select(reason);
    }

    /**
     * Input issue date
     *
     * @param issueDate - issue date
     */
    public void inputIssueDate(String issueDate) {
        txbIssueDate.type(issueDate);
    }

    /**
     * Click Ineligible radio button.
     */
    public void clickIneligible() {
        rbIneligible.click();
        waitForNotVisible(BaseOtherElement.LOADING);
    }

    /**
     * Check first ineligible reason
     */
    public void checkFirstIneligibleReason() {
        chkFirstIneligibleReason.click();
    }

    /**
     * Check, that error message is present on page.
     */
    public void checkErrorsPresent() {
        spnValidationErrorsFoundOnPage.isPresent();
    }
}
