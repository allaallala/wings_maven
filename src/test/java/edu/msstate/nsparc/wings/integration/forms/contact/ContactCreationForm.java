package edu.msstate.nsparc.wings.integration.forms.contact;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import framework.elements.Span;
import org.openqa.selenium.By;

/**
This form is opened via Employers -> Wagner-Peyser -> Contacts -> Create
 */
public class ContactCreationForm extends ContactBaseForm {

    private Span spnEmployerError = new Span("css=span[id='employer.errors']", "Employer Error");

    public ContactCreationForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Contact Creation')]"), "Contact Creation");
    }

    public String fillContactData(String companyNameForSearch, String contactDate, String contactMethod) {
        clickAndWait(BaseButton.EMPLOYER_LOOK_UP);
        EmployerSearchForm employerSearchForm = new EmployerSearchForm();
        String companyName = employerSearchForm.selectAndReturnEmployerResult(companyNameForSearch);
        inputJobContactDateMethod(Constants.TRUE, contactDate, contactMethod);
        return companyName;
    }

    public String getEmployerError() {
        return spnEmployerError.getText();
    }
}
