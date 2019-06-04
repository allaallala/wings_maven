package edu.msstate.nsparc.wings.integration.forms.dataIntegrity;

import framework.elements.ComboBox;
import org.openqa.selenium.By;

/**
This form is opened via Reports -> Data Integrity -> Training Waiver Report
 */
public class DataIntegrityTrainingWaiverReportForm extends DataIntegrityParticipantReportForm {

    private ComboBox cmbReportType = new ComboBox("css=select[id='checkRevocation']", "Report Type");

    /**
     * Default constructor
     */
    public DataIntegrityTrainingWaiverReportForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Training Waiver Webreport')]"), "Data Integrity - Training Waiver Report");
    }

    /**
     * Select report type for training waiver
     * @param option - value to choose
     */
    public void selectReportType(String option) {
        cmbReportType.select(option);
    }
}
