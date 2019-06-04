package edu.msstate.nsparc.wings.integration.forms.dataIntegrity;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.common.TablePaginationForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.CheckBox;
import framework.elements.ComboBox;
import framework.elements.RadioButton;
import org.openqa.selenium.By;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

interface ILookUpFunction {
    boolean perform();
}

/**
 * This is the base form for Data Integrity Report forms
 */
public abstract class DataIntegrityBaseForm extends BaseWingsForm {

    private RadioButton rbDuplicates = new RadioButton("//input[@value='duplicates']", "Duplicates");
    private ComboBox cmbDuplicates = new ComboBox("css=select#checkDuplicates", "Duplicates");
    private RadioButton rbServiceEnrollments = new RadioButton("//input[@value='serviceEnrollments'] | //input[@value='serviceEnrollment']", "Service Enrollment");
    private ComboBox cmbServiceEnrollments = new ComboBox("//select[@name='checkServiceEnrollment'] | //select[@name='checkServiceEnrollments']", "Service Enrollment");
    private CheckBox chkLimit = new CheckBox("css=input#limitResults1","Limit # of Results to 500 ");
    private RadioButton rbUnfinishedRecords = new RadioButton("css=input#reportType1", "Unfinished Records");
    private RadioButton rbInvalidDates = new RadioButton(By.xpath("//td[contains(.,'Invalid Dates')]/input"), "Invalid Dates");
    private Button btnSearchServiceEnrollments = new Button("id=searchSE", "Search");

    /**
     * Default constructor
     * @param locator - locator of the form
     * @param formTitle - title of the form
     */
    DataIntegrityBaseForm(By locator, String formTitle) {
        super(locator, formTitle);
    }

    /**
     * Use assert to check if participant is displayed
     */
    private void checkParticipantWasFound() {
        assertFalse("Participant wasn't found", new SearchResultsForm().isNothingResult());
    }

    /**
     * Use assert to check if employer is displayed
     */
    private void checkEmployerWasFound() {
        assertFalse("Employer wasn't found", new SearchResultsForm().isNothingResult());
    }

    private boolean tryFindEntityOnPage(String targetName) {
        SearchResultsForm resultsForm = new SearchResultsForm();
        final String valuableColumn = "2";
        for (int j = 1; j <= resultsForm.getResultRowsCount(); j++) {
            String label = resultsForm.getRecordText(j, valuableColumn);
            if (isTargetFound(label, targetName)) {
                return true;
            }
        }
        return false;
    }

    private boolean tryFindEntityOnPageByColumn(String targetName, String column) {
        SearchResultsForm resultsForm = new SearchResultsForm();
        for (int j = 1; j <= resultsForm.getResultRowsCount(); j++) {
            String label = resultsForm.getRecordText(j, column);
            if (isTargetFound(label, targetName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if participant is displayed on certain page
     * @param participant Participant object
     */
    private boolean lookForParticipantOnPage(Participant participant) {
        checkParticipantWasFound();
        String targetParticipant = defineParticipantToBeFound(participant);
        return tryFindEntityOnPage(targetParticipant);
    }

    private boolean lookForParticipantOnPage(Participant participant, String column) {
        checkParticipantWasFound();
        String targetParticipant = defineParticipantToBeFound(participant);
        return tryFindEntityOnPageByColumn(targetParticipant, column);
    }

    /**
     * Check if employer is displayed on certain page
     * @param employer Employer object
     */
    private boolean lookForEmployerOnPage(Employer employer) {
        checkEmployerWasFound();
        String targetEmployer = defineEmployerToBeFound(employer);
        return tryFindEntityOnPage(targetEmployer);
    }

    private boolean lookForEmployerOnPageByColumn(Employer employer, String column) {
        checkEmployerWasFound();
        String targetEmployer = defineEmployerToBeFound(employer);
        return tryFindEntityOnPageByColumn(targetEmployer, column);
    }

    private void validateSearchResults(ILookUpFunction lookUpFunction, String entityName) {
        boolean targetFound = false;
        // check if there is more than 1 page of results
        if (TablePaginationForm.isPresent()) {
            int count = 0;
            int total = new TablePaginationForm().getLastPage();
            while (count <= total) {
                // check page
                targetFound = lookUpFunction.perform();
                // if participant found stop validation
                if (targetFound) {
                    break;
                }
                new TablePaginationForm().openNextPage();
                count++;
                total = new TablePaginationForm().getLastPage();
            }
        } else {
            targetFound = lookUpFunction.perform();
        }
        assertTrue(entityName + " wasn't found", targetFound);
    }

    /**
     * Open each page in search result table one by one, checking if employer is displayed.
     * @param employer Employer object
     */
    public void validateEmployerSearchResults(Employer employer) {
        validateSearchResults(() -> lookForEmployerOnPage(employer), "Employer");
    }

    public void validateEmployerSearchResultsByColumn(Employer employer, String columnNumber) {
        validateSearchResults(() -> lookForEmployerOnPageByColumn(employer, columnNumber), "Employer");
    }

    /**
     * Select unfinished records
     */
    public void checkUnfinishedRecords() {
        rbUnfinishedRecords.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
    }

    /**
     * Check invalid dates.
     */
    public void checkInvalidDates() {
        rbInvalidDates.click();
    }
    /**
     * Open each page in search result table one by one, checking if participant is displayed.
     * @param participant Participant participant
     */
    public void validateParticipantSearchResults(Participant participant) {
        validateSearchResults(() -> lookForParticipantOnPage(participant), "Participant");
    }

    public void validateParticipantSearchResultsByColumn(Participant participant, String column) {
        validateSearchResults(() -> lookForParticipantOnPage(participant, column), "Participant");
    }

    /**
     * Validate search participant results, counting from the end.
     * @param participant - participant
     */
    public void validateParticipantSearchReverse(Participant participant) {
        boolean targetFound = false;
        if (TablePaginationForm.isPresent()) {
            new TablePaginationForm().openLastPage();
            int total = new TablePaginationForm().getLastPage();
            while (total >= 1) {
                targetFound = lookForParticipantOnPage(participant, "1");
                if (targetFound) {
                    break;
                }
                new TablePaginationForm().openPreviousPage();
                total--;
            }
        } else {
            targetFound = lookForParticipantOnPage(participant, "1");
        }
        CustomAssertion.softTrue("Participant wasn't found", targetFound);
    }

    /**
     * @param participant Participant object
     * @return Participant string 'first name + last name'
     */
    private String defineParticipantToBeFound(Participant participant) {
        return String.format("%1$s %2$s", participant.getFirstName(), participant.getLastName());
    }

    /**
     * @param employer Employer object
     * @return employer's 'company'
     */
    private String defineEmployerToBeFound(Employer employer) {
        return String.format("%1$s", employer.getCompanyName());
    }

    /**
     * @param label String label name
     * @param target String name of participant or employer company
     * @return boolean if participant or employer was found
     */
    private boolean isTargetFound(String label, String target) {
        return label.contains(target);
    }
    /**
     * Select duplicate records
     * @param option value
     */
    public void selectDuplicatesRecords(String option) {
        rbDuplicates.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        cmbDuplicates.select(option);
    }
    /**
     * Select service enrollments
     * @param option - option to be selected.
     */
    public void selectServiceEnrollments(String option) {
        rbServiceEnrollments.click();
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        cmbServiceEnrollments.select(option);
    }
    /**
     * Click [Search Service Enrollment] button.
     */
    public void searchServiceEnrollment() {
        btnSearchServiceEnrollments.clickAndWait();
    }

    /**
     * Click service enrollment radio button.
     */
    public void clickServiceEnrollments() {
        rbServiceEnrollments.click();
    }

    /**
     * Check limit textbox
     */
    public void checkLimit() {
        chkLimit.click();
    }
}
