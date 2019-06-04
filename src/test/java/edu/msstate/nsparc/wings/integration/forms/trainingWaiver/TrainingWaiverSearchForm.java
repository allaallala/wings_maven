package edu.msstate.nsparc.wings.integration.forms.trainingWaiver;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.models.trade.trainings.TrainingWaiver;
import framework.customassertions.CustomAssertion;
import framework.elements.ComboBox;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This form is opened via Participants -> Trade -> Training Waivers -> Search
 */
public class TrainingWaiverSearchForm extends TrainingWaiverBaseForm {
    // Status options
    private static final String ISSUED = "Issued";
    private static final String RENEWED = "Renewed";
    private static final String EXPIRED = "Expired";
    private static final String DENIED = "Denied";
    private String[] statusArray = {Constants.REVOKED, "Issued", "Any", "Expired", "Renewed", "Awaiting Review", "Denied"};

    // Items on Training Waiver Search
    private final ComboBox cmbTrainingWaiverStatus = new ComboBox("id=trainingWaiverStatus", "Status");
    private final TextBox txbIssueDateFrom = new TextBox("id=minDateIssued", "Issue Date From");
    private final TextBox txbIssueDateTo = new TextBox("id=maxDateIssued", "Issue Date To");
    private final TextBox txbWaiverExpirationDateFrom = new TextBox("id=minDateExpiration", "Waiver Expiration Date From");
    private final TextBox txbWaiverExpirationDateTo = new TextBox("id=maxDateExpiration", "Waiver Expiration Date To");
    private final TableCell tbcWaiverReason = new TableCell("//th[contains(text(),'Waiver Reason')]", "Waiver Reason column header");

    //Items in result Table
    private TableCell tbcParticipant = new TableCell("//span[@modelclass='Participant']", "Value of the participant name, surname in the search result table");
    private TableCell tbcPetition = new TableCell(By.xpath("//table[@id='results-table']//tbody//td[3]/a"), "Value of the petition name in the search result table");
    private TableCell tbcIssueDate = new TableCell(By.xpath("//table[@id='results-table']//tbody//td[5]"), "Date of the issue date in the search result table");

    /**
     * Default constructor
     */
    public TrainingWaiverSearchForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Training Waiver Search')]"), "Training Waiver Search");
    }

    /**
     * Checking Waiver Expiration Date fields availability
     *
     * @param currentStatus Selected 'Status' value
     */
    private void checkWaiverExpirationDateField(String currentStatus) {
        waitForNotVisible(BaseOtherElement.LOADING);
        if (ISSUED.equals(currentStatus) || RENEWED.equals(currentStatus)) {
            txbWaiverExpirationDateFrom.assertIsNotPresent();
            txbWaiverExpirationDateTo.assertIsNotPresent();
        } else {
            txbWaiverExpirationDateFrom.assertIsPresentAndVisible();
            txbWaiverExpirationDateTo.assertIsPresentAndVisible();
        }
    }

    /**
     * Checking Waiver Reason column availability
     *
     * @param currentStatus Selected 'Status' value
     */
    private void checkWaiverReasonColumn(String currentStatus) {
        if (EXPIRED.equals(currentStatus) || DENIED.equals(currentStatus)) {
            tbcWaiverReason.assertIsNotPresent();
            tbcWaiverReason.assertIsNotPresent();
        } else {
            tbcWaiverReason.isPresent();
            tbcWaiverReason.isPresent();
        }
    }

    /**
     * Select a value in the Status drop-down
     *
     * @param status Status value
     */
    public void selectStatus(String status) {
        cmbTrainingWaiverStatus.select(status);
    }

    /**
     * Searching for the record
     *
     * @param waiver Waiver participantSSDetails
     */
    public void performSearch(TrainingWaiver waiver) {
        selectParticipant(waiver.getTradeEnrollment().getParticipant());
        selectInactivePetition(waiver.getTradeEnrollment().getPetition());
        clickAndWait(BaseButton.SEARCH);
    }

    /**
     * Searching for the record and opening it
     *
     * @param waiver Waiver participantSSDetails
     */
    public void performSearchAndOpen(TrainingWaiver waiver) {
        performSearch(waiver);
        openFirstSearchResult();
    }

    /**
     * Select Status and check Waiver Expiration Date fields availability
     */
    public void selectStatusAndCheckExpirationDate() {

        for (String current : statusArray) {
            selectStatus(current);
            checkWaiverExpirationDateField(current);
        }
    }

    /**
     * Select Status and check Waiver Reason column availability
     */
    public void selectStatusAndCheckReasonColumn() {
        for (String currentStatus : statusArray) {
            selectStatus(currentStatus);
            clickAndWait(BaseButton.SEARCH);
            checkWaiverReasonColumn(currentStatus);
        }
    }

    /**
     * Validate search result data
     *
     * @param tradeEnrl - trade enrollment
     * @param date      - issued date
     */
    public void validateSearchResult(TradeEnrollment tradeEnrl, String date) {
        info(tbcParticipant.getText());
        info(tbcPetition.getText());
        info(tbcIssueDate.getText());
        CustomAssertion.softAssertEquals(tbcIssueDate.getText(), date, "Incorrect issued date");
        CustomAssertion.softAssertEquals(tbcPetition.getText(), tradeEnrl.getPetition().getNumber() + " - " + tradeEnrl.getPetition().getEmployer().getCompanyName()
                + " - " + tradeEnrl.getPetition().getStatus() + " " + tradeEnrl.getPetition().getFileDate(), "Incorrect petition information");
        CustomAssertion.softAssertEquals(tbcParticipant.getText().substring(0, 31), tradeEnrl.getParticipant().getFirstName() + " " + tradeEnrl.getParticipant().getLastName(), "Incorrect participant information");
    }

    /**
     * Check, that all fields are cleared.
     */
    public void validateAllFieldsCleared() {
        CustomAssertion.softAssertEquals(BaseTextBox.PARTICIPANT.getText(), Constants.EMPTY, "Participant field wasn't cleared!");
        CustomAssertion.softAssertEquals(BaseTextBox.PETITION.getText(), Constants.EMPTY, "Petition is not correct");
        CustomAssertion.softAssertEquals(BaseTextBox.SERVICE_CENTER.getText(), Constants.EMPTY, "Service center is not correct");
        CustomAssertion.softAssertEquals(cmbTrainingWaiverStatus.getSelectedLabel(), Constants.ANY, "Incorrect training waiver status");
        CustomAssertion.softAssertEquals(txbIssueDateFrom.getText(), Constants.EMPTY, "Incorrect issued date from");
        CustomAssertion.softAssertEquals(txbIssueDateTo.getText(), Constants.EMPTY, "Incorrect issued date to");
        CustomAssertion.softAssertEquals(txbWaiverExpirationDateFrom.getText(), Constants.EMPTY, "Incorrect waiver expiration date from");
        CustomAssertion.softAssertEquals(txbWaiverExpirationDateTo.getText(), Constants.EMPTY, "Incorrect waiver expiration date to");
    }

    /**
     * Input status and issued date
     *
     * @param date - issued date
     */
    public void inputStatusIssuedDate(String date) {
        cmbTrainingWaiverStatus.selectFirst();
        txbIssueDateFrom.type(date);
        txbIssueDateTo.type(date);
    }
}
