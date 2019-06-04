package edu.msstate.nsparc.wings.integration.forms.bigRocks;

import org.openqa.selenium.By;

/**
This form is opened via Reports -> Big Rocks -> Employer Report -> Create
 */
public class BigRocksEmployerReportForm extends BigRocksReportBaseForm {
    /**
     * Default constructor
     */
    public BigRocksEmployerReportForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Employer Report')]"), "Employer Report");
    }
}
