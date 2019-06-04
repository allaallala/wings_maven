package edu.msstate.nsparc.wings.integration.forms.jobSearch;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This is the base form for Job Search forms
 */
public class JobSearchBaseForm extends BaseWingsForm {
    private TextBox txbCompanyName = new TextBox("css=input[id='companyName']", "Company Name");

    /**
     * Constructor with defined locator and title
     *
     * @param locator - locator of the form
     * @param title   - title of the form
     */
    public JobSearchBaseForm(By locator, String title) {
        super(locator, title);
    }

    /**
     * Input company name
     *
     * @param companyName - company name
     */
    public void inputCompanyName(String companyName) {
        txbCompanyName.type(companyName);
    }

    /**
     * Get company text
     *
     * @return - company text
     */
    public String getCompanyText() {
        return txbCompanyName.getText();
    }
}
