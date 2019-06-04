package edu.msstate.nsparc.wings.integration.forms.bigRocks;

import org.openqa.selenium.By;

/**
This form is opened via Reports -> Big Rocks -> Job Order Report -> Create
 */
public class BigRocksJobOrderReportCreationForm extends BigRocksReportBaseForm {
    /**
     * Default constructor
     */
    public BigRocksJobOrderReportCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Job Order Report')]"), "Job Order Report");
    }
}
