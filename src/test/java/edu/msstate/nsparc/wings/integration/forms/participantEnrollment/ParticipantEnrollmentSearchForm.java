package edu.msstate.nsparc.wings.integration.forms.participantEnrollment;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.forms.service.ServiceSearchForm;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.ComboBox;
import framework.elements.Link;
import framework.elements.TableCell;
import org.openqa.selenium.By;

import static org.testng.AssertJUnit.assertTrue;

/**
 * This form is opened via Participants -> Participant Service Enrollment -> Search
 */
public class ParticipantEnrollmentSearchForm extends ParticipantEnrollmentBaseForm {

    private final String specifiedRowLocatorXpath = "//table[@id='results-table']/tbody/tr[%1$s]";
    private final String partialTableCellXpath = "//table[@id='results-table']//td[%1$d]";

    private final ComboBox cmbProgram = new ComboBox("id=program", "Program");
    private final ComboBox cmbStatus = new ComboBox(By.id("serviceStatus"), "Status");
    private final ComboBox cmbResult = new ComboBox(By.id("result"), "Result");

    private final Link lnkParticipantName = new Link(By.xpath("//table[@id='results-table']//td/a"), "Participant Name");
    private final TableCell tbcServiceLabel = new TableCell(By.xpath(String.format(partialTableCellXpath, 3)), "Service Name");
    private final TableCell tbcCreationDateLabel = new TableCell(By.xpath(String.format(partialTableCellXpath, 4)), "Creation Date");
    private final TableCell tbcServiceCenter = new TableCell(By.xpath(String.format(partialTableCellXpath, 7)), "Service Center");

    private final Button btnRemoveParticipantCross = new Button(By.xpath("//span[@id='participantLookup']/a[@class='powerLookupRemoveButton']"), "Remove participant button");
    private final Button btnRemoveServiceCross = new Button(By.xpath("//span[@id='serviceLookup']/a[@class='powerLookupRemoveButton']"), "Remove service button");

    /**
     * Default constructor
     */
    public ParticipantEnrollmentSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Enrollment Search')]"), "Enrollment Search");
    }

    /**
     * This method is used for Participant Enrollment record searching
     *
     * @param participant Participant with enrollment
     * @param serviceName Service name
     */
    public void performSearch(Participant participant, String serviceName) {
        selectParticipant(participant);
        selectService(serviceName);
        clickAndWait(BaseButton.SEARCH);
    }

    public void performSearchAndOpenByUser(User user, Participant participant, String serviceName) {
        clickAndWait(BaseButton.PARTICIPANT_LOOK_UP);
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndOpenByUser(user, participant);

        selectService(serviceName);
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * This method is used for searching and selecting Service in look-up
     *
     * @param serviceName Name of the service that will be selected
     */
    public void selectService(String serviceName) {
        clickAndWait(BaseButton.SERVICE_LOOK_UP);
        ServiceSearchForm serviceSearchForm = new ServiceSearchForm();
        serviceSearchForm.selectAndReturnParticipantServiceRadioButton(serviceName);
    }

    /**
     * Select required program
     *
     * @param program - program to select
     */
    public void selectProgram(String program) {
        cmbProgram.select(program);
    }

    /**
     * Select status
     *
     * @param status - status of the participant enrollment
     */
    public void selectStatus(String status) {
        cmbStatus.select(status);
    }

    /**
     * Select result
     *
     * @param result - result of the participant enrollment
     */
    public void selectResult(String result) {
        cmbResult.select(result);
    }

    /**
     * Check participant name
     *
     * @return true if participant name is present and visible.
     */
    public Boolean checkParticipantName() {
        return lnkParticipantName.isPresent();
    }

    /**
     * Get participant text
     *
     * @return participant text.
     */
    public String getParticipantText() {
        return lnkParticipantName.getText().trim();
    }

    /**
     * Get service center text
     *
     * @return service center text.
     */
    public String getServiceCenterText() {
        return tbcServiceCenter.getText().trim();
    }

    /**
     * Check some fields
     *
     * @param partipName   - participant name
     * @param service      - service name
     * @param creationDate - creation date.
     */
    public void checkSomeFields(String partipName, String service, String creationDate) {
        CustomAssertion.softAssertEquals(lnkParticipantName.getText(), partipName, "Incorrect participant name");
        CustomAssertion.softAssertEquals(tbcServiceLabel.getText(), service, "Incorrect service label");
        CustomAssertion.softAssertContains(tbcCreationDateLabel.getText(), creationDate, "Incorrect creation date");
    }

    public void validateFirstSearchResult(Participant participant, String serviceName, String creationDate, String status) {
        SearchResultsForm resultsForm = new SearchResultsForm();
        CustomAssertion.softTrue("Incorrect participant name", resultsForm.getFirstRowRecordText(2).contains(
                participant.getFirstName() + " " + participant.getLastName()));
        CustomAssertion.softTrue("Incorrect service name", resultsForm.getFirstRowRecordText(3).contains(
                serviceName));
        CustomAssertion.softTrue("Incorrect creation date & status", resultsForm.getFirstRowRecordText(4).contains(
                creationDate + "\n" + status));
        CustomAssertion.softTrue("Incorrect result date & status", resultsForm.getFirstRowRecordText(5).contains(
                creationDate + "\n" + status));
        CustomAssertion.softTrue("Incorrect staff member", resultsForm.getFirstRowRecordText(6).contains(
                Constants.DEFAULT_ADMIN));
        CustomAssertion.softTrue("Incorrect service center", resultsForm.getFirstRowRecordText(7).contains(
                Constants.DEFAULT_REGION));
    }

    /**
     * Remove searched participant
     */
    public void removeSearchedParticipant() {
        btnRemoveParticipantCross.clickAndWait();
    }

    /**
     * Remove searched service.
     */
    public void removeSearchedService() {
        btnRemoveServiceCross.clickAndWait();
    }

    /**
     * Validate information in choser row of the results table.
     *
     * @param rowNumber - number of row
     * @param args      - args
     */
    public void validateSearchResultsInChosenRow(String rowNumber, String... args) {
        TableCell tbcItemChosenRow;
        String rowLocator = String.format(specifiedRowLocatorXpath, rowNumber);
        String specifiedData = rowLocator + "/td[%1$d]";
        for (int i = 1; i <= args.length; i++) {
            tbcItemChosenRow = new TableCell(By.xpath(String.format(specifiedData, i + 1)), "Item in the chosen row");
            assertTrue("Item found assertion failed!", tbcItemChosenRow.getText().toLowerCase().contains(args[i - 1].toLowerCase()));
        }
    }
}
