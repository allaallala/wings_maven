package edu.msstate.nsparc.wings.integration.forms.ataaRtaaEnrollment;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.trade.AtaaRtaaEnrollment;
import framework.elements.RadioButton;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * This is the base form for ATAA/RTAA Enrollment forms
 */
public class AtaaRtaaEnrollmentBaseForm extends BaseWingsForm {

    private final TableCell tbcVerifiedReemploymentColumn = new TableCell(By.xpath("//table[@id='results-table']//td[4]"), "Verified/Qualifying Re-Employment");
    private final TableCell tbcDeterminationDateError = new TableCell(By.xpath("//span[@id='dateATAAEligibilityDetermination.errors']"), "Determination date error");
    private final RadioButton rbWithholdFederalIncomeTax = new RadioButton("css=input[id='isFedIncomeTaxWithheld1']", "Withhold Federal Income Tax - Yes");
    private final RadioButton rbDoNotWithholdFederalIncomeTax = new RadioButton("css=input[id='isFedIncomeTaxWithheld2']", "Withhold Federal Income Tax - No");
    private final static String VERIFIED = "Verified";
    private final static String REEMPLOYMENT = "Qualifying Re-Employment";

    /**
     * Constructor of the form
     * @param locator - locator of the form
     * @param formTitle - title of the form.
     */
    public AtaaRtaaEnrollmentBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Check, if REEMPLOYMENT is qualifeed
     */
    public void checkQualifyingReEmployment() {
        checkField(tbcVerifiedReemploymentColumn, VERIFIED, Constants.FALSE);
        checkField(tbcVerifiedReemploymentColumn, REEMPLOYMENT, Constants.FALSE);
    }

    /**
     * Check, if REEMPLOYMENT is unmarked
     */
    public void checkUnmarkedQualifyingReEmployment() {
        checkField(tbcVerifiedReemploymentColumn, VERIFIED, Constants.TRUE);
    }

    /**
     * Select federal tax with hold
     * @param ataaRtaaEnrollment - ATAA / RTAA enrollment
     */
    protected void selectWithholdFederalTax(AtaaRtaaEnrollment ataaRtaaEnrollment) {
        if (ataaRtaaEnrollment.isWithholdTax()) {
            rbWithholdFederalIncomeTax.click();
        } else {
            rbDoNotWithholdFederalIncomeTax.click();
        }
    }

    /**
     * Get determination date error
     * @return error text
     */
    public String getDeterminationDateError() {
        return tbcDeterminationDateError.getText();
    }

    /**
     * Select with hold federal tax
     */
    public void selectHoldFederalIncome() {
        rbWithholdFederalIncomeTax.click();
    }
}
