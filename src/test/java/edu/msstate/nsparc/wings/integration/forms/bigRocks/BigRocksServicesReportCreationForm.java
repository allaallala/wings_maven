package edu.msstate.nsparc.wings.integration.forms.bigRocks;

import org.openqa.selenium.By;

/**
This form is opened via Reports -> Big Rocks -> Services Report -> Create
 */
public class BigRocksServicesReportCreationForm extends BigRocksReportBaseForm {
    /**
     * Default constructor
     */
    public BigRocksServicesReportCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Service Enrollment Report')]"), "Services Report");
    }
}
