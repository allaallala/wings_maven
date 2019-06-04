package edu.msstate.nsparc.wings.integration.forms.bigRocks;

import org.openqa.selenium.By;

/**
This form is opened via Reports -> Big Rocks -> Services Report -> Create
 */
public class BigRocksServicesReportForm extends BigRocksReportBaseForm {
    /**
     * Default constructor
     */
    public BigRocksServicesReportForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Services Report')]"), "Services Report");
    }
}
