package edu.msstate.nsparc.wings.integration.forms.dataIntegrity;

import framework.elements.ComboBox;
import org.openqa.selenium.By;

/**
 * Describes data integrity WIA report form
 * Created by a.vnuchko on 28.10.2016.
 */
public class DataIntegrityWiaReportForm extends DataIntegrityBaseForm {
    private ComboBox cmbInvalidDate = new ComboBox(By.id("checkInvalidData"), "Invalid Data");

    public DataIntegrityWiaReportForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'WIOA Enrollment Webreport')]"), "Data Integrity - WIA Enrollment Report");
    }

    /**
     * Select invalid data type on the form
     * @param dateType - date type.
     */
    public void selectInvalidData(String dateType){
        cmbInvalidDate.select(dateType);
    }
}
