package edu.msstate.nsparc.wings.integration.forms.contact;

import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import framework.elements.Button;
import framework.elements.TextArea;
import org.openqa.selenium.By;

/**
This form is opened via Employers -> Wagner-Peyser -> Contacts -> Search for Contact -> Open Contact
    -> Click on Edit button
 */
public class ContactEditForm extends ContactBaseForm {

    private TextArea description = new TextArea("css=textarea#description", "Description");
    private TextArea result = new TextArea("css=textarea#result", "Result");
    private Button btnRemoveEmployer = new Button("css=div[id='employerlookup'] a[class='powerLookupRemoveButton']", "Remove Employer");

    public ContactEditForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Contact Edit')]"), "Contact Edit");
    }

    public String selectAndReturnEmployer(String companyName, String contactMethod) {
        // Remove old Employer
        btnRemoveEmployer.click();
        clickAndWait(BaseButton.EMPLOYER_LOOK_UP);
        // Select new Employer
        clickAndWait(BaseButton.EMPLOYER_LOOK_UP);

        EmployerSearchForm employerSearchForm = new EmployerSearchForm();
        String editedCompanyName = employerSearchForm.selectAndReturnEmployerResult(companyName);
        // Select contact method
        selectContactMethod(contactMethod);
        return editedCompanyName;
    }

    public void inputDescriptionResult(String descriptionText, String resultText) {
        description.type(descriptionText);
        result.type(resultText);
    }
}
