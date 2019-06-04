package edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment;

import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import framework.elements.*;
import org.openqa.selenium.By;

/**
This form is opened via Participants -> Trade -> ATAA/RTAA Enrollments -> Create
 */
public class AtaaRtaaEnrollmentCreationForm extends AtaaRtaaEnrollmentBaseForm {

    private ComboBox cmbTradeEnrollment = new ComboBox("css=select[id='selectedTradeEnrollmentId']", "Trade Enrollment");
    private ComboBox cmbPreviousJob = new ComboBox("css=select[id='selectedPreviousJobId']", "Qualifying Re-Employment");
    private Button btnAddPreviousJob = new Button("css=button[id='addPreviousJob']", "Add Previous Job");
    private RadioButton rbFirstPreviousJob = new RadioButton("css=table[id='results-table'] input", "First Previous Job");
    private Button btnMarkQualifyingReEmployment = new Button("css=button[id='markQualifyingReEmployment']", "Mark Qualifying Re-Employment");
    private Button btnUnmarkQualifyingReEmployment = new Button("css=button[id='unmarkQualifyingReEmployment']", "Unmark Qualifying Re-Employment");
    private Button btnRemovePreviousJob = new Button("id=removePreviousJob", "Remove");
    private TextBox txbExhaustedDate = new TextBox("id=dateUIExhaustion","Exhausted date");

    /**
     * Constructor
     */
    public AtaaRtaaEnrollmentCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'ATAA/RTAA Enrollment Creation')]"), "ATAA/RTAA Enrollment Creation");
    }

    /**
     * Filling out form fields with provided data
     * @param ataaRtaaEnrollment Object with Enrollment data
     */
    public void fillOutCreationForm(AtaaRtaaEnrollment ataaRtaaEnrollment) {
        selectParticipant(ataaRtaaEnrollment.getParticipant());
        fillOutSecondBlock(ataaRtaaEnrollment);

        if (cmbPreviousJob.isPresent()) {
            cmbPreviousJob.selectZero();
            addPreviousJob();
        }

        rbFirstPreviousJob.click();
        btnMarkQualifyingReEmployment.clickAndWait();
    }

    /**
     * Filling out another form of creation
     * @param ataaRtaaEnrollment - AtaaRtaaEnrollment object
     */
    public void fillOutCreateAnotherForm(AtaaRtaaEnrollment ataaRtaaEnrollment) {
        fillOutSecondBlock(ataaRtaaEnrollment);
        if (cmbPreviousJob.isPresent()) {
            cmbPreviousJob.selectZero();
            addPreviousJob();
        }
        rbFirstPreviousJob.click();
        btnMarkQualifyingReEmployment.clickAndWait();
    }

    /**
     * Fill out second block on creation form
     * @param ataaRtaaEnrollment - AtaaRtaaEnrollment object
     */
    private void fillOutSecondBlock(AtaaRtaaEnrollment ataaRtaaEnrollment) {
        cmbTradeEnrollment.selectFirst();
        if (ataaRtaaEnrollment.getExhaustionDateRequired()) {
            txbExhaustedDate.type(ataaRtaaEnrollment.getUiExhaustionDate());
        }
        inputApplicationDate(ataaRtaaEnrollment.getApplicationDate());
        selectWithholdFederalTax(ataaRtaaEnrollment);
    }


    public void completeCreation() {
        clickAndWait(BaseButton.CREATE);
        if (isPresent(BaseButton.SAVE_CHANGES)) {
            clickAndWait(BaseButton.SAVE_CHANGES);
        }
    }

    public void addPreviousJob() {
        btnAddPreviousJob.clickAndWait();
    }

    /**
     * Check, that job was added to the Qualifying Re-Employment table or deledeted from it.
     * @param state - job was added, job was deleted.
     */
    public void elementsPresent(Boolean state) {
        if (state) {
            rbFirstPreviousJob.assertIsPresentAndVisible();
            cmbPreviousJob.assertIsNotPresent();
        } else {
            rbFirstPreviousJob.assertIsNotPresent();
            cmbPreviousJob.assertIsPresentAndVisible();
        }
    }

    public void selectFirstPreviousJob() {
        rbFirstPreviousJob.click();
    }

    public void markUnmarkReemployment(Boolean mark) {
        if (mark) {
            btnMarkQualifyingReEmployment.clickAndWait();
        } else {
            btnUnmarkQualifyingReEmployment.clickAndWait();
        }
    }

    public void removePreviousJob() {
        btnRemovePreviousJob.clickAndWait();
    }
}
