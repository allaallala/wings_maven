package edu.msstate.nsparc.wings.integration.forms.referral;

import org.openqa.selenium.By;

/**
 * This form is opened wia Participants -> Wagner-Peyser -> Referrals -> Open detail form -> Print
 * Created by a.vnuchko on 04.07.2016.
 */
public class ReferralPrintForm extends ReferralBaseForm {
    /**
     * Default constructor
     */
    public ReferralPrintForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Referral Print')]"),"Referral print");

    }
}
