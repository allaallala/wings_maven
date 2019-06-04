package edu.msstate.nsparc.wings.integration.forms.callIn;

import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.forms.employer.EmployerSearchForm;
import edu.msstate.nsparc.wings.integration.forms.jobOrder.JobOrderSearchForm;
import edu.msstate.nsparc.wings.integration.forms.participant.ParticipantSearchForm;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.CommonFunctions;
import framework.customassertions.CustomAssertion;
import framework.elements.Link;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
This form is opened via Participants -> Wagner-Peyser -> Call-Ins -> Search
 */
public class CallInSearchForm extends CallInBaseForm {

    private Link lnkContactMethodLabel = new Link(By.xpath("//table[@id='results-table']//tbody//a[1]"), "Contact Method table cell");
    private TableCell tbcParticpantLabel = new TableCell(By.xpath("//table[@id='results-table']//td[2]"), "Participant table cell");
    private TableCell tbcJobOrderLabel = new TableCell(By.xpath("//table[@id='results-table']//td[3]"), "Job order table cell");
    private TableCell tbcResultLabel = new TableCell(By.xpath("//table[@id='results-table']//td[4]"), "Result table cell");

    public CallInSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Call In Search')]"), "Call In Search");
    }

    public void clickJobOrderLookUp() {
        clickAndWait(BaseButton.JOB_ORDER_LOOK_UP);
    }

    private ParticipantSearchForm clickParticipantLookUp() {
        clickAndWait(BaseButton.PARTICIPANT_LOOK_UP);
        return new ParticipantSearchForm();
    }

    public String getJobOrderTitle() {
        return tbcJobOrderLabel.getText().trim().split("[(]")[0].trim();
    }

    public void selectParticipant(String participantName) {
        ParticipantSearchForm participantSearchForm = clickParticipantLookUp();
        participantSearchForm.fillFirstName(participantName);
        participantSearchForm.fillLastName(participantName);
        searchSelectResultAndReturn();
    }

    public void selectAndReturnJobOrder(String companyName, String city, String zipCode, String jobTitle) {
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();
        EmployerSearchForm employerSearchForm = jobOrderSearchForm.clickEmployerLookUpButton();
        employerSearchForm.searchAndReturnEmployer(companyName, zipCode, city);
        jobOrderSearchForm.inputTitleDate(jobTitle, CommonFunctions.getCurrentDate(), CommonFunctions.getCurrentDate());
        clickAndWait(BaseButton.SEARCH);
        jobOrderSearchForm.clickJobItem();
        jobOrderSearchForm.clickJobOrderReturn();
    }

    public void selectJobOrder(JobOrder jobOrder) {
        clickAndWait(BaseButton.JOB_ORDER_LOOK_UP);
        JobOrderSearchForm jobOrderSearchForm = new JobOrderSearchForm();
        jobOrderSearchForm.performSearchAndReturn(jobOrder);
    }

    public void checkCallInData(String contactCheck, String participantName, String callInResult) {
        CustomAssertion.softAssertEquals(lnkContactMethodLabel.getText(),contactCheck,"Assert Contact Method failed");
        CustomAssertion.softAssertEquals(tbcParticpantLabel.getText(), participantName,"Assert Participant Name failed");
        CustomAssertion.softAssertEquals(tbcResultLabel.getText(), callInResult,"Assert Result failed");
    }

    /**
     * Perform search for call - in
     * @param order - job order
     * @param partip - participant.
     */
    public void performSearch(JobOrder order, Participant partip) {
        selectJobOrder(order);
        selectParticipant(partip);
        clickButton(Buttons.Search);
        openFirstSearchResult();
    }
}
