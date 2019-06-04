package edu.msstate.nsparc.wings.integration.forms.bigRocks;

import org.openqa.selenium.By;

/**
This form is opened via Reports -> Big Rocks -> Job Order Report -> Create
 */
public class BigRocksJobOrderReportForm extends BigRocksReportBaseForm {
    /**
     * Default constructor
      */
    public BigRocksJobOrderReportForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Job Order Report')]"), "Job Order Report");
    }
}
