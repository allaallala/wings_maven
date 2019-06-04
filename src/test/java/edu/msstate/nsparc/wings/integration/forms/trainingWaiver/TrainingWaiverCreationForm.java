package edu.msstate.nsparc.wings.integration.forms.trainingWaiver;

import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import framework.elements.CheckBox;
import framework.elements.ComboBox;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Trade -> Training Waivers -> Create
 */
public class TrainingWaiverCreationForm extends TrainingWaiverBaseForm {

    private ComboBox cmbTradeEnrollment = new ComboBox("css=select[id='selectedTradeEnrollmentId']", "Trade Enrollment");
    private RadioButton rbEligible = new RadioButton("css=input[id='isWaiverEligible1']", "Eligible");

    // Other Reason(s) for Ineligibility:
    private CheckBox chkIsNotMeetingCriteria = new CheckBox("id=isNotMeetingCriteria1", "Did not meet any of the criteria for which a waiver may be issued");
    private CheckBox chkIsTrainingRefused = new CheckBox("id=isTrainingRefused1", "Training has been recommended, but participant refused such training");
    private CheckBox chkIsFailedToEnroll = new CheckBox("id=isFailedToEnroll1", "Failed to enroll in TAA approved training as scheduled without good cause");
    private CheckBox chkIsWorkedLessThan = new CheckBox("id=isWorkedLessThan1", "Participant worked less than 26 weeks at $30 or more per week");
    private CheckBox chkIsDeadlinePassed = new CheckBox("id=isDeadlinePassed1", "Deadline for getting waiver has passed");
    private CheckBox chkIsMonetarilyIneligible = new CheckBox(By.id("isMonetarilyIneligible1"), "Participant is monetarily ineligible");
    private CheckBox chkIsTRAExhausted = new CheckBox("id=isTRAExhausted1", "TRA has been exhausted");
    private TextBox tbOtherReason = new TextBox("id=ineligibilityReasons", "Other Reason(s) for Ineligibility");

    /**
     * Default constructor
     */
    public TrainingWaiverCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Training Waiver Creation')]"), "Training Waiver Creation");
    }

    /**
     * Input other reason
     *
     * @param otherReason - other reason
     */
    public void inputOtherReason(String otherReason) {
        tbOtherReason.type(otherReason);
    }

    /**
     * Click [Eligible] radio button.
     */
    public void clickEligible() {
        rbEligible.click();
    }

    /**
     * Check, that "worked less than" check box is present.
     */
    public void checkWorkedLessPresent() {
        chkIsWorkedLessThan.isPresent();
    }

    /**
     * Click deadline passed checkbox.
     */
    public void clickDeadlinePassed() {
        chkIsDeadlinePassed.click();
    }

    /**
     * Filling out form fields with provided data
     *
     * @param waiver Object with Waiver data
     */
    public void fillOutCreationForm(TrainingWaiver waiver) {
        selectParticipant(waiver.getTradeEnrollment().getParticipant());
        cmbTradeEnrollment.selectFirst();

        if (waiver.isEligible()) {
            clickEligible();
            selectWaiverReason(waiver.getWaiverReason());
        } else {
            clickIneligible();
            checkFirstIneligibleReason();
        }

        inputIssueDate(waiver.getIssueDate());
    }

    /**
     * Completes creation of the training waiver
     */
    public void completeCreation() {
        clickAndWait(BaseButton.CREATE);
        passParticipationRecalculationPage();
        new TrainingWaiverDetailsForm();
    }

    /**
     * Validate ineligibility reason list
     */
    public void validateIneligibilityReasonsList() {
        chkIsNotMeetingCriteria.isPresent();
        chkIsTrainingRefused.isPresent();
        chkIsFailedToEnroll.isPresent();
        chkIsWorkedLessThan.isPresent();
        chkIsDeadlinePassed.isPresent();
        chkIsMonetarilyIneligible.isPresent();
        chkIsTRAExhausted.isPresent();
    }

    /**
     * Select participant and trade enrollment
     *
     * @param participant - participant
     */
    public void selectParticipantAndTradeEnrollment(Participant participant) {
        selectParticipant(participant);
        cmbTradeEnrollment.selectFirst();
    }

    /**
     * Select check boxes on trying to create waiver with 'some' checkboxes filled.
     */
    public void selectSomeCheckboxType() {
        chkIsNotMeetingCriteria.click();
        chkIsFailedToEnroll.click();
    }

    /**
     * Select all check boxes on trying to create training waiver with 'all' checkboxes filled.
     */
    public void selectAllCheckboxType() {
        selectSomeCheckboxType();
        chkIsTrainingRefused.click();
        chkIsWorkedLessThan.click();
        chkIsDeadlinePassed.click();
        chkIsMonetarilyIneligible.click();
        chkIsTRAExhausted.click();
    }
}
