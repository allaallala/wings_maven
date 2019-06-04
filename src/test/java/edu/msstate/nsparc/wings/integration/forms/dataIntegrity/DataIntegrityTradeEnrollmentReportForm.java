package edu.msstate.nsparc.wings.integration.forms.dataIntegrity;

import framework.elements.ComboBox;
import org.openqa.selenium.By;

/**
This form is opened via Reports -> Data Integrity -> Trade Enrollment Report
 */
public class DataIntegrityTradeEnrollmentReportForm extends DataIntegrityParticipantReportForm {

    private ComboBox cmbReportType = new ComboBox(By.id("checkTradeEnrollment"), "Report Type");

    /**
     * Default constructor
     */
    public DataIntegrityTradeEnrollmentReportForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Trade Enrollment Webreport')])"), "Data Integrity - Trade Enrollment");
    }

    /**
     * Select report type of the trade enrollment
     * @param option - value
     */
    public void selectReportType(String option) {
        cmbReportType.select(option);
    }
}
