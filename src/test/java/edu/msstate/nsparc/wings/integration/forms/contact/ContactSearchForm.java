package edu.msstate.nsparc.wings.integration.forms.contact;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import framework.elements.Button;
import framework.elements.Link;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
This form is opened via Employers -> Wagner-Peyser -> Contacts -> Search
 */
public class ContactSearchForm extends ContactBaseForm {

    private Button btnEmployerLookup = new Button(By.xpath("//span[@id='employerlookup']//a//button | //div[@id='employerlookUp']//a//button"),
            "Employer Lookup");
    private Link lnkContactTypeDate = new Link("css=table[id='results-table'] tbody a", "Open Contact");
    private TableCell tbcEmployerLink = new TableCell(By.xpath("//table[@id='results-table']//td[2]"), "Employer link");

    /**
     * Default constructor
     */
    public ContactSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Contact Search')]"), "Contact Search");
    }

    /**
     * This method is used to search for created Contact
     * @param companyName Company name of employer
     * @param currentDate Date when contact created
     * @param contactMethod Contact method
     */
    public void searchJustCreatedContact(String companyName, String currentDate, String contactMethod) {
        btnEmployerLookup.clickAndWait();
        EmployerSearchForm employerSearchForm = new EmployerSearchForm();
        employerSearchForm.selectAndReturnEmployerResult(companyName);
        inputJobContactDateMethod(Constants.TRUE, currentDate, contactMethod);
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * This method is used to search and select Employer from look-up
     * @param companyName Desired company name
     * @return Company name of selected employer
     */
    public String selectAndReturnEmployer(String companyName) {
        btnEmployerLookup.clickAndWait();
        EmployerSearchForm employerSearchForm = new EmployerSearchForm();
        return employerSearchForm.selectAndReturnEmployerResult(companyName);
    }

    /**
     * Get employeer text
     * @return employeer text
     */
    public String getEmployerText() {
        return tbcEmployerLink.getText();
    }

    /**
     * Get contact type date text
     * @return contact type date text.
     */
    public String getContactTypeText() {
      return lnkContactTypeDate.getText();
    }
}
