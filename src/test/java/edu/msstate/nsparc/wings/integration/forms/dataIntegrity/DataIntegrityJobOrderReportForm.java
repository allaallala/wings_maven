package edu.msstate.nsparc.wings.integration.forms.dataIntegrity;

import org.openqa.selenium.By;

/**
This form is opened via Reports -> Data Integrity -> Job Order Report -> Create
 */
public class DataIntegrityJobOrderReportForm extends DataIntegrityBaseForm {

    /**
     * Default constructor
     */
    public DataIntegrityJobOrderReportForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Job Order Webreport')]"), "Data Integrity - Job Order Report");
    }
}
