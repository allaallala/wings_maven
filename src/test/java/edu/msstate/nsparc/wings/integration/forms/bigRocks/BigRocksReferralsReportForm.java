package edu.msstate.nsparc.wings.integration.forms.bigRocks;

import org.openqa.selenium.By;

/**
This form is opened via Reports -> Big Rocks -> Referral Report -> Create
 */
public class BigRocksReferralsReportForm extends BigRocksReportBaseForm {
    /**
     * Default constructor
     */
    public BigRocksReferralsReportForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Referral Report')]"), "Referral Report");
    }
}
