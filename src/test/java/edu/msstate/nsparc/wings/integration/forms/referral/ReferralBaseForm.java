package edu.msstate.nsparc.wings.integration.forms.referral;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.ComboBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Referral forms
 */
public class ReferralBaseForm extends BaseWingsForm {

    protected ComboBox cmbReferralResult = new ComboBox("css=select[id='refResult']", "Referral Result");

    /**
     * Constructor of the form with defined locator
     *
     * @param locator   - locator of the form
     * @param formTitle - title of the form.
     */
    public ReferralBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Check result value in combobox
     *
     * @param value - value in the combobox
     * @return true, if value is present
     */
    public boolean checkResultValue(String value) {
        return cmbReferralResult.checkValue(value);
    }
}
