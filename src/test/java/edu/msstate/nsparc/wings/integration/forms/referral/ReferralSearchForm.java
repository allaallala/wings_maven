package edu.msstate.nsparc.wings.integration.forms.referral;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.common.SearchResultsForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.ComboBox;
import framework.elements.Link;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via
 * Participants -> Wagner-Peyser -> Referrals -> Search
 */
public class ReferralSearchForm extends ReferralBaseForm {

    private Link lnkCancelParticipant = new Link("css=span#participantLookup > a", "Participant Look Up Cancel");
    private TableCell tbcResult = new TableCell("css=table#results-table td:nth-child(6)", "Referral Result");
    private ComboBox cbResult = new ComboBox(By.id("refResult"), "Referral result");
    private TextBox txbCreationDateFrom = new TextBox(By.id("minDateCreation"), "Min creation date");
    private TextBox txbCreationDateTo = new TextBox(By.id("maxDateCreation"), "Max creation date");

    /**
     * Constructor
     */
    public ReferralSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Referral Search')]"), "Referral Search");
    }

    /**
     * This method is used for clearing Participant selection in look-up
     */
    private void cancelParticipantLookUp() {
        if (!isPresent(BaseButton.PARTICIPANT_LOOK_UP)) {
            lnkCancelParticipant.click();
            BaseButton.PARTICIPANT_LOOK_UP.getButton().waitForIsElementPresent();
        }
    }

    /**
     * This method is used for Referral searching by participant
     *
     * @param participant Participant that will be used for Referral search
     */
    public void performSearch(Participant participant) {
        cancelParticipantLookUp();
        selectParticipant(participant);
        clickButton(Buttons.Search);
        // Checking results
        checkSearchResult();
    }

    /**
     * This method is used for Referral searching and opening
     *
     * @param participant Participant that will be used for Referral search
     */
    public void performSearchAndOpen(Participant participant) {
        performSearch(participant);
        new SearchResultsForm().openFirstSearchResult();
    }

    /**
     * This method is used for searching and selecting Participant from look-up
     *
     * @param participant Participant that will be selected
     */
    public void selectParticipant(Participant participant) {
        clickAndWait(BaseButton.PARTICIPANT_LOOK_UP);
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndSelect(participant);
    }

    /**
     * Perform search for job order
     *
     * @param jobOrder - job order
     */
    public void performSearchJobOrder(JobOrder jobOrder) {
        clickAndWait(BaseButton.JOB_ORDER_LOOK_UP);
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();
        jobOrderSearchForm.performSearchAndReturn(jobOrder);
    }

    /**
     * Select result
     *
     * @param result - result to select
     */
    public void selectResult(String result) {
        cbResult.select(result);
    }

    public void clearSomeFields() {
        cbResult.select("Any");
        txbCreationDateFrom.clear();
        txbCreationDateTo.clear();
    }

    /**
     * This method is used for checking if any Referral record was found
     */
    private void checkSearchResult() {
        info("Checking, that Referral was found");
        boolean isResultPresent = isFirstSearchResultPresent();
        CustomAssertion.softTrue("Referral search assert fail", isResultPresent);
        if (isResultPresent)
            info("Search result: OK");
    }

    /**
     * This method is used for getting Participant first name from search results
     *
     * @return First name
     */
    public String getFirstName() {
        String text = new SearchResultsForm().getFirstSearchResultLinkText();
        return CommonFunctions.regexGetMatch(text, "[\\w]+");
    }

    /**
     * This method is used for getting Participant last name from search results
     *
     * @return Last name
     */
    public String getLastName() {
        String text = new SearchResultsForm().getFirstSearchResultLinkText();
        return CommonFunctions.regexGetMatchGroup(text, "(\\w+)\\s+(\\w+)", 2);
    }

    /**
     * This method is used for getting Participant ssn from search results
     *
     * @return SSN
     */
    public String getSSNFromTitle() {
        String text = new SearchResultsForm().getFirstSearchResultLinkText();
        return CommonFunctions.regexGetMatch(text, "[\\d]+");
    }

    /**
     * Get result text on the search page
     *
     * @return result text
     */
    public String getResultText() {
        return tbcResult.getText().trim();
    }

    /**
     * Input creation date range
     *
     * @param date - date, when referral was created.
     */
    public void inputCreationDateRange(String date) {
        txbCreationDateFrom.type(CommonFunctions.getDaysNextDate(date, -1));
        txbCreationDateTo.type(date);
    }

    /**
     * Validate search results
     *
     * @param participant - participant to check
     * @param order       - job order to check
     * @param result      - expected result
     * @param date        - creation date
     */
    public void validateSearchResult(Participant participant, JobOrder order, String result, String date) {
        SearchResultsForm resultsForm = new SearchResultsForm();
        String expectedOrderNameTitle = resultsForm.getFirstRowRecordText(3).replace(CommonFunctions.regexGetMatch(resultsForm.getFirstRowRecordText(3), "\\(\\d{1,}\\)"), "");
        CustomAssertion.softAssertContains(resultsForm.getFirstRowRecordText(2), (
                participant.getFirstName() + " " + participant.getLastName()), "Incorrect participant name and surname");
        CustomAssertion.softAssertContains(expectedOrderNameTitle,
                order.getEmployer().getCompanyName(), "Incorrect company name");
        CustomAssertion.softAssertContains(expectedOrderNameTitle,
                order.getJobTitle(), "Incorrect job order title");
        CustomAssertion.softAssertContains(resultsForm.getFirstRowRecordText(4),
                date, "Incorrect creation date");
        CustomAssertion.softAssertContains(resultsForm.getFirstRowRecordText(6),
                result, "Incorrect result");
    }

    public void selectJobOrder(Employer employer) {
        info("Selecting Job Order");
        clickAndWait(BaseButton.JOB_ORDER_LOOK_UP);
        selectEmployer(employer);
        searchSelectResultAndReturn();
    }
}
