package edu.msstate.nsparc.wings.integration.forms.referral;

import framework.elements.RadioButton;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via
 * Participants -> Wagner-Peyser -> Referrals -> Search for record -> Open it -> Click on Edit button
 */
public class ReferralEditForm extends ReferralBaseForm {

    private TextBox txbCreationDate = new TextBox("css=input#dateCreation", "Creation Date");
    private RadioButton rbJobDevelopmentY = new RadioButton(By.id("isJobDevelopment1"), "Job Development - Yes");
    private TextBox txbAdditionalInfo = new TextBox(By.id("additionalInfo"), "Additional Information");

    /**
     * Default constructor
     */
    public ReferralEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Referral Edit')]"), "Referral Edit");
    }

    /**
     * This method is used for Referral Resulting
     *
     * @param result     Referral Result
     * @param resultDate Result Date
     */
    public void resultReferral(String result, String resultDate) {
        info("Select Result date and status");
        selectRefResult(result);
        if (!result.equals("Unresulted")) {
            inputDateResult(resultDate);
        }
    }

    /**
     * Input creation date
     *
     * @param date - creation date
     */
    public void inputCreationDate(String date) {
        txbCreationDate.type(date);
    }

    /**
     * Check combobox result referral
     *
     * @return combobox presented
     */
    public Boolean checkResultCbPresent() {
        return cmbReferralResult.isPresent();
    }

    /**
     * Select referral result
     *
     * @param referralResult - value to be selected
     */
    public void selectRefResult(String referralResult) {
        cmbReferralResult.select(referralResult);
    }

    /**
     * Click [Job Development: Yes]
     */
    public void makeJobDevelopmentYes() {
        rbJobDevelopmentY.click();
    }

    /**
     * Add additional information
     *
     * @param addInfo - information to add.
     */
    public void addAdditionalInfo(String addInfo) {
        txbAdditionalInfo.type(addInfo);
    }
}
