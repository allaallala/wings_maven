package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerEditForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.xray.info.TestCase;

import static org.testng.Assert.assertEquals;

@TestCase(id = "WINGS-10714")
public class TC_08_23_Employer_Edit extends BaseWingsTest {

    public void main() {
        TC_08_19_Employer_View employerViewTest = new TC_08_19_Employer_View();
        employerViewTest.main();

        logStep("Edit company information");
        EmployerDetailsForm employerDetailsForm = new EmployerDetailsForm();
        EmployerEditForm employerEditForm = employerDetailsForm.openEmployerEditForm();

        logStep("Edit company name and save changes");
        String editedEmployerName = employerEditForm.editCompanyNameAndSaveChanges();

        logStep("Complete edition");
        employerDetailsForm = new EmployerDetailsForm();
        assertEquals("Assert Employer Name Failed", editedEmployerName, employerDetailsForm.getEmployerName());
        employerDetailsForm.clickButton(Buttons.Done);

        logStep("Find edited company name");
        EmployerSearchForm employerSearchForm = new EmployerSearchForm();
        employerSearchForm.inputCompanyName(editedEmployerName);
        employerSearchForm.clickButton(Buttons.Search);

        logStep("Check, that changes are saved");
        assertEquals("Assert Employer Name failed", editedEmployerName, employerSearchForm.getEmployerNameText());
    }
}
