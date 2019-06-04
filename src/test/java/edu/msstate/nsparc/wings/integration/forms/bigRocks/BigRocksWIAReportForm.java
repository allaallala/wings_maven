package edu.msstate.nsparc.wings.integration.forms.bigRocks;

import org.openqa.selenium.By;

/**
 * Describes the WIA Report form.
 * Created by a.vnuchko on 19.10.2016.
 */
public class BigRocksWIAReportForm extends BigRocksReportBaseForm {

    /**
     * Constructor of the form with defined locator
     */
    public BigRocksWIAReportForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'WIOA Report Parameters')]"), "WIOA Report Parameters");
    }
}
