package edu.msstate.nsparc.wings.integration.forms.referral;

import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.ComboBox;
import framework.elements.Link;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Wagner-Peyser -> Referral Resulting
 */
public class ReferralResultForm extends ReferralBaseForm {

    private Button btnProcessReferrals = new Button("id=processReferrals", "Process Referrals");
    private ComboBox cmbResultReferral = new ComboBox(By.xpath("//select[@name='referralResult1']"), "Result");
    private TextBox txbResultDate = new TextBox(By.xpath("//input[@name='resultDate1']"), "Result Date");
    private Link lnkParticipantName = new Link("css=table#results-table > tbody a", "Participant Name");
    private TextBox txbLastNameBegin = new TextBox(By.id("tempSearchName"), "Participant Last Name Begin With");
    private TextBox txbCreationDateFrom = new TextBox(By.id("dateCreationFrom"), "Creation Date From");
    private TextBox txbCreationDateTo = new TextBox(By.id("dateCreationTo"), "Creation Date To");

    /**
     * Default constructor
     */
    public ReferralResultForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Referral Result')]"), "Referral Result");
    }

    /**
     * This method is used for searching and selecting Participant from look-up
     *
     * @param participant Participant that will be selected
     */
    public void selectParticipant(Participant participant) {
        BaseButton.PARTICIPANT_LOOK_UP.getButton().waitForIsElementPresent();
        BaseButton.PARTICIPANT_LOOK_UP.getButton().clickAndWait();
        ParticipantSearchForm participantSearchForm = new ParticipantSearchForm();
        participantSearchForm.performSearchAndSelect(participant);
    }

    /**
     * This method is used for searching and selecting Job Order from look-up
     *
     * @param jobOrder Job Order that will be selected
     */
    public void selectJobOrder(JobOrder jobOrder) {
        BaseButton.PARTICIPANT_LOOK_UP.JOB_ORDER_LOOK_UP.getButton().waitForIsElementPresent();
        BaseButton.PARTICIPANT_LOOK_UP.getButton().clickAndWait();
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();
        jobOrderSearchForm.performSearchAndReturn(jobOrder);
    }

    /**
     * This method is used for Referral searching by Participant
     *
     * @param participant Participant that will be used for search
     */
    public void performSearch(Participant participant) {
        if (BaseButton.PARTICIPANT_LOOK_UP.getButton().isPresent()) {
            selectParticipant(participant);
        }
        clickAndWait(BaseButton.SEARCH);
        // Checking results
        checkSearchResult();
    }

    /**
     * This method is used for checking that Referral was found
     */
    public void checkSearchResult() {
        info("Checking, that Referral was found");
        CustomAssertion.softTrue("Referral search assert fail", lnkParticipantName.isPresent());
        info("Search result: OK");
    }

    /**
     * This method is used for Referral searching by Job Order
     *
     * @param jobOrder Job Order that will be used for search
     */
    public void performSearch(JobOrder jobOrder) {
        selectJobOrder(jobOrder);
        clickAndWait(BaseButton.SEARCH);
        // Checking results
        checkSearchResult();
    }

    /**
     * Input result date
     *
     * @param date - date
     */
    public void inputResultDate(String date) {
        txbResultDate.type(date);
    }

    /**
     * Input Participant Last Name Beginning
     *
     * @param beginWith
     */
    public void inputLastNameBegin(String beginWith) {
        txbLastNameBegin.type(beginWith);
    }

    /**
     * Input creation dates in range
     *
     * @param date - creation date
     */
    public void inputCreationDateRange(String date) {
        txbCreationDateFrom.type(date);
        txbCreationDateTo.type(date);
    }

    /**
     * Process referrals
     */
    public void processReferrals() {
        btnProcessReferrals.clickAndWait();
    }

    /**
     * Select result referral
     *
     * @param resultRef - result referral to select
     */
    public void selectResultRef(String resultRef) {
        cmbResultReferral.select(resultRef);
    }

    /**
     * Validate that all fields are cleared.
     */
    public void validateFieldsCleared() {
        CustomAssertion.softTrue("Last name initials is not cleared", txbLastNameBegin.getText().equals(""));
        CustomAssertion.softTrue("Participant field is not cleared", BaseButton.PARTICIPANT_LOOK_UP.getButton().isPresent());
        CustomAssertion.softTrue("Job Order field is not cleared", BaseButton.JOB_ORDER_LOOK_UP.getButton().isPresent());
        CustomAssertion.softTrue("Creator Service Center is not cleared", BaseButton.SERVICE_CENTER_LOOK_UP.getButton().isPresent());
        CustomAssertion.softTrue("Creation Date From is not cleared", txbCreationDateFrom.getText().equals(""));
        CustomAssertion.softTrue("Creation date to is not cleared", txbCreationDateTo.getText().equals(""));
    }
}

