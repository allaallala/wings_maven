package edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import framework.elements.Button;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
This form is opened via Participants -> Trade -> ATAA/RTAA Enrollments -> Search for record -> Open record -> Edit
 */
public class AtaaRtaaEnrollmentEditForm extends AtaaRtaaEnrollmentBaseForm {

    private RadioButton rbEligible = new RadioButton("css=input[id='isATAARTAAEligible1']", "Eligible");
    private RadioButton rbIneligible = new RadioButton("css=input[id='isATAARTAAEligible2']", "Ineligible");
    private TextBox txbEligibilityDeterminationDate = new TextBox("css=input[id='dateATAAEligibilityDetermination']", "Eligibility Determination Date");
    private TextBox txbUIExhaustionDate = new TextBox("css=input[id='dateUIExhaustion']", "UI Exhaustion Date");
    private TextBox txbIneligibilityReason = new TextBox("id=ineligibilityReasons", "Other Reason(s) For Ineligibility");
    private RadioButton rbReemployment = new RadioButton(By.id("selectedRadio1"), "First qualifying reemployment");
    private Button btnRemove = new Button(By.id("removePreviousJob"), "Remove previous job");
    private Button btnAdd = new Button(By.id("addPreviousJob"), "Add previous job");
    private Button btnMarkQualifying = new Button(By.id("markQualifyingReEmployment"), "Mark as qualifying reemployment");

    /**
     * Default constructor.
     */
    public AtaaRtaaEnrollmentEditForm() {
        super(By.xpath("//h1[contains(text(),'Editing ATAA/RTAA Enrollment for')]"), "Editing ATAA/RTAA Enrollment");
    }

    /**
     * Changing Enrollment eligibility with provided data
     * @param enrollment Object with Enrollment data
     */
    public void changeEligibility(AtaaRtaaEnrollment enrollment) {
        selectStatus(enrollment);
        txbEligibilityDeterminationDate.type(enrollment.getEligibilityDeterminationDate());
    }

    /**
     * Select Status by clicking Eligible or Ineligible radio button
     * @param enrollment Object with AtaaRtaaEnrollment data
     */
    public void selectStatus(AtaaRtaaEnrollment enrollment) {
        if (enrollment.isEligible()) {
            rbEligible.click();
        } else {
            rbIneligible.click();
            txbIneligibilityReason.type(enrollment.getIneligibilityReason());
        }
    }

    /**
     * This method is used for handling "Participation Period Recalculation" page that might be opened after edit
     */
    public void finishEditing() {
        clickAndWait(BaseButton.SAVE_CHANGES);
        if (isPresent(BaseButton.SAVE_CHANGES)) {
            clickAndWait(BaseButton.SAVE_CHANGES);
        }
    }

    /**
     * Input exhaustion date
     * @param exhaustionDate - exhaustion date
     */
    public void inputExhaustionDate(String exhaustionDate) {
        txbUIExhaustionDate.type(exhaustionDate);
    }

    /**
     * Input eligibility determination date
     * @param enrollment - ataa/ rtaa enrollment
     */
    public void inputDeterminationDate(AtaaRtaaEnrollment enrollment) {
        txbEligibilityDeterminationDate.type(enrollment.getEligibilityDeterminationDate());
    }

    /**
     * Remove reemployment, than add it and check, that it is succefully added.
     */
    public void removeAddReemployment() {
        rbReemployment.click();
        btnRemove.clickAndWait();
        btnAdd.clickAndWait();
        rbReemployment.click();
        btnMarkQualifying.clickAndWait();
        clickButton(Buttons.Save);
    }
}