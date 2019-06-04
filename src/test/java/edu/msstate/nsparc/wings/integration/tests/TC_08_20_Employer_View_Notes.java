package edu.msstate.nsparc.wings.integration.tests;

import edu.msstate.nsparc.wings.integration.base.BaseWingsTest;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.forms.NotesForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerDetailsForm;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.menu.WingsTopMenu;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.steps.BaseWingsSteps;
import edu.msstate.nsparc.wings.integration.steps.EmployerSteps;
import edu.msstate.nsparc.xray.info.TestCase;
import framework.AccountUtils;
import webdriver.Browser;
import framework.CommonFunctions;

import static org.testng.Assert.assertNotSame;
import static org.testng.AssertJUnit.assertTrue;

// Author: d.poznyak

@TestCase(id = "WINGS-10713")
public class TC_08_20_Employer_View_Notes extends BaseWingsTest {

    private String text = CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH);

    public void main() {
        info("We need to create Employer first");
        Employer employer = new Employer(AccountUtils.getEmployerAccount());
        EmployerSteps.createEmployer(employer, Roles.STAFF);

        BaseWingsSteps.openCreationSearchForm(Roles.STAFF, WingsTopMenu.WingsStaffMenuItem.E_EMPLOYER_RECORDS, Popup.Search);

        logStep("Select some records and open it");
        EmployerSearchForm employerSearchForm = new EmployerSearchForm();
        employerSearchForm.performSearch(employer);
        employerSearchForm.openFirstSearchResult();

        logStep("Notes->Add some data");
        EmployerDetailsForm employerDetailsForm = new EmployerDetailsForm();
        String currentCount = employerDetailsForm.getNotesCount();
        NotesForm notesForm = new NotesForm();
        notesForm.addNote(text);
        Browser.getInstance().waitForPageToLoad();

        logStep("Check, that notes are saved");
        assertTrue("Note wasn't saved", notesForm.checkNote(text));
        assertNotSame("Notes Count wasn't changed", currentCount, employerDetailsForm.getNotesCount());
        logEnd();
    }
}
