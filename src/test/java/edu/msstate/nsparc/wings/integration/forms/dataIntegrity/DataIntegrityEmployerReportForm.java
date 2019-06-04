package edu.msstate.nsparc.wings.integration.forms.dataIntegrity;

import framework.elements.ComboBox;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
This form is opened via Reports -> Data Integrity -> Employer Report -> Create
 */
public class DataIntegrityEmployerReportForm extends DataIntegrityBaseForm {


    private ComboBox cmbUnfinished = new ComboBox("id=showUnfinished", "Unfinished");
    private TextBox txbCompanyName = new TextBox("css=input#employerName", "Employer Name");

    /**
     * Default constructor
     */
    public DataIntegrityEmployerReportForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Employer Webreport')]"), "Data Integrity - Employer Report");
    }

    /**
     * Input company name
     * @param companyName - name of the company
     */
    public void inputCompanyName(String companyName) {
        txbCompanyName.type(companyName);
    }



    /**
     * Select unfinished value
     * @param option - value to be selected
     */
    public void selectUnfinished(String option) {
        cmbUnfinished.waitForIsElementPresent();
        cmbUnfinished.select(option);
    }
}
