package edu.msstate.nsparc.wings.integration.forms.tradeEnrollment;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import framework.elements.Button;
import framework.elements.ComboBox;
import framework.elements.RadioButton;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Trade -> Trade Enrollments -> Search for record -> Open record -> Edit
 */
public class TradeEnrollmentEditForm extends TradeEnrollmentBaseForm {

    private RadioButton rbFirstEmploymentSeparation = new RadioButton("id=selectedRadio1", "Employment at Separation");
    private Button btnRemoveSeparation = new Button("id=removePreviousJob", "Remove");
    private Button btnAddPreviousJob = new Button("css=button[id='addPreviousJob']", "Add Previous Job");
    private ComboBox cbAddEmploymentRecord = new ComboBox(By.id("selectedPreviousJobId"), "Choose an employment record");

    /**
     * Default constructor
     */
    public TradeEnrollmentEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Trade Enrollment Edit')]"), "Trade Enrollment Edit");
    }

    /**
     * Filling out form fields with provided data
     *
     * @param tradeEnrollment Object with Trade Enrollment data
     */
    public void fillOutEditForm(TradeEnrollment tradeEnrollment) {
        inputApplicationDate(tradeEnrollment.getApplicationDate());
        txbEligibilityDeterminationDate.type(tradeEnrollment.getEligibilityDeterminationDate());
    }

    /**
     * This method is used for handling "Participation Period Recalculation" page that might be opened after edit
     */
    public void finishEditing() {
        clickAndWait(BaseButton.SAVE_CHANGES);
        passParticipationRecalculationPage();
    }

    /**
     * Add previous job.
     */
    public void addPreviousJob() {
        btnAddPreviousJob.clickAndWait();
    }

    /**
     * Remove separation job.
     */
    public void removeSeparationJob() {
        btnRemoveSeparation.clickAndWait();
    }

    /**
     * Select first job edit.
     */
    public void selectFirstJobEdit() {
        rbFirstEmploymentSeparation.click();
    }

    /**
     * Add first employment record and mark it as qualifying separation.
     */
    public void addEmploymentRecord() {
        while (cbAddEmploymentRecord.isPresent()) {
            btnAddPreviousJob.clickAndWait();
        }
    }

    /**
     * Change status to eligible or ineligible, depending on parameter
     *
     * @param trd - trade enrollment
     */
    public void changeEligibilityStatus(TradeEnrollment trd) {
        if (trd.isEligible()) {
            rbEligible.click();
            clickButton(Buttons.Save);
        } else {
            rbIneligible.click();
            inputApplicationDate(trd.getDenialDate());
            trd.setApplicationDate(trd.getDenialDate()); //after changing application date, it's necessary to change date
            txbEligibilityDeterminationDate.type(trd.getDenialDate());
            trd.setEligibilityDeterminationDate(trd.getDenialDate()); //after changing status determination date, it's necessary to change date
            txbIneligibilityReason.type(trd.getDenialReason());
            trd.setIneligibilityReason(trd.getDenialReason());
            clickButton(Buttons.Save);
            passParticipationRecalculationPage();
        }
    }

}
