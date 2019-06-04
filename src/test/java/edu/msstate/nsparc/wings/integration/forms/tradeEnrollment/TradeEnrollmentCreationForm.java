package edu.msstate.nsparc.wings.integration.forms.tradeEnrollment;

import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import framework.elements.Button;
import framework.elements.ComboBox;
import framework.elements.RadioButton;
import org.openqa.selenium.By;

/**
This form is opened via Participants -> Trade -> Trade Enrollments -> Create
 */
public class TradeEnrollmentCreationForm extends TradeEnrollmentBaseForm {

    private ComboBox cmbInsuranceStatus = new ComboBox("css=select[id='uiStatus']", "Insurance Status");

    private Button btnAddPreviousJob = new Button("css=button[id='addPreviousJob']", "Add Previous Job");
    private RadioButton rbFirstPreviousJob = new RadioButton("css=table[id='results-table'] input", "First Previous Job");

    /**
     * Default constructor
     */
    public TradeEnrollmentCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Trade Enrollment Creation')]"), "Trade Enrollment Creation");
    }

    /**
     * Filling out form fields with provided data
     * @param tradeEnrollment Object with Enrollment data
     */
    public void fillOutCreationForm(TradeEnrollment tradeEnrollment) {
        selectParticipant(tradeEnrollment.getParticipant());
        selectPetition(tradeEnrollment.getPetition());

        inputApplicationDate(tradeEnrollment.getApplicationDate());
        cmbInsuranceStatus.select(tradeEnrollment.getInsuranceStatus());
        if (tradeEnrollment.isEligible()) {
            rbEligible.click();
        } else {
            rbIneligible.click();
            txbIneligibilityReason.type(tradeEnrollment.getFullTradeEnrollmentName());
        }
        txbEligibilityDeterminationDate.type(tradeEnrollment.getEligibilityDeterminationDate());

        btnAddPreviousJob.clickAndWait();
        rbFirstPreviousJob.click();
        markQualifyingSeparation();
    }

    /**
     * Comlpetes creation of trade enrollment
     */
    public void completeCreation() {
        clickAndWait(BaseButton.CREATE);

        passParticipationRecalculationPage();
    }
}
