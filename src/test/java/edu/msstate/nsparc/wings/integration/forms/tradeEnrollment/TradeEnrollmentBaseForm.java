package edu.msstate.nsparc.wings.integration.forms.tradeEnrollment;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.Button;
import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Trade Enrollment forms
 */
public class TradeEnrollmentBaseForm extends BaseWingsForm {

    protected TextBox txbEligibilityDeterminationDate = new TextBox("css=input[id='dateTAAEligibilityDetermination']", "Eligibility Determination Date");
    private Button btnMarkQualifyingSeparation = new Button("css=button[id='markQualifyingSeparation']", "Mark Qualifying Separation");
    protected RadioButton rbEligible = new RadioButton("css=input[id='isTAAEligible1']", "Eligible");
    protected RadioButton rbIneligible = new RadioButton("css=input[id='isTAAEligible2']", "Ineligible");
    protected TextBox txbIneligibilityReason = new TextBox("css=input[id='taaIneligibleReason']", "TAA Ineligibility Reason");

    /**
     * Constructor of the form with defined locator and title
     * @param locator - locator of the form
     * @param title - title of the form
     */
    public TradeEnrollmentBaseForm(By locator, String title) {
        super(locator, title);
    }

    /**
     * Mark qualifying separation
     */
    public void markQualifyingSeparation(){
        btnMarkQualifyingSeparation.clickAndWait();
    }
}
