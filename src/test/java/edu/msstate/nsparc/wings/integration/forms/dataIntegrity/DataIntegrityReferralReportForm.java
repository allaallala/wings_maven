package edu.msstate.nsparc.wings.integration.forms.dataIntegrity;

import framework.elements.ComboBox;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
This form is opened via Reports -> Data Integrity -> Referral Report -> Create
 */
public class DataIntegrityReferralReportForm extends DataIntegrityBaseForm {

    private ComboBox cmbDatesType = new ComboBox("id=checkDates", "Dates type");
    private TextBox tbDateFrom = new TextBox(By.id("minDateCreation"), "Date from");

    /**
     * Default constructor
     */
    public DataIntegrityReferralReportForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Referral Webreport')]"), "Data Integrity - Referral Report");
    }

    /**
     * Select date types
     * @param value - type
     */
    public void selectDateTypes(String value) {
        cmbDatesType.select(value);
    }

    /**
     * Input date from
     * @param dateFrom - start date.
     */
    public void inputDateFrom(String dateFrom) {
        if (tbDateFrom.isPresent()) {
            tbDateFrom.type(dateFrom);
        }
    }
}
