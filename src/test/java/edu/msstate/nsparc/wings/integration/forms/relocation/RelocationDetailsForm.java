package edu.msstate.nsparc.wings.integration.forms.relocation;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.trade.Relocation;
import edu.msstate.nsparc.wings.integration.models.trade.RelocationCalculateExpense;
import edu.msstate.nsparc.wings.integration.models.trade.RelocationExpense;
import framework.customassertions.CustomAssertion;
import framework.elements.Button;
import framework.elements.Div;
import framework.elements.RadioButton;
import framework.elements.TableCell;
import org.openqa.selenium.By;

import static org.testng.Assert.assertEquals;

/**
 * This form is opened via Participants -> Trade -> Relocation -> Search for record -> Open record
 */
public class RelocationDetailsForm extends RelocationBaseForm {
    private Div dvValidationError = new Div(By.id("id.errors"), "Validation errors found on page");
    private String validationErrorText = "Validation errors found on page.";
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private TableCell tbcParticipant = new TableCell(String.format(detailPath, "Participant:"), "Participant");
    private TableCell tbcTradeEnrollment = new TableCell(String.format(detailPath, "Approved Trade Enrollment:"), "Trade Enrollment");
    private TableCell tbcApplicationDate = new TableCell(String.format(detailPath, "Application Date:"), "Application Date");
    private TableCell tbcRelocationDate = new TableCell(String.format(detailPath, "Relocation Date:"), "Relocation Date");
    private TableCell tbcRelocationDistance = new TableCell(String.format(detailPath, "Relocation Distance:"), "Relocation Distance");
    private TableCell tbcEmployerName = new TableCell(String.format(detailPath, "Employer Name:"), "Employer Name");

    private TableCell tbcStatus = new TableCell(String.format(detailPath, "Status:"), "Status");
    private TableCell tbcStatusDeterminationDate = new TableCell(String.format(detailPath, "Status Determination Date"), "Status Determination Date");

    // Expenses table
    private String expenseXpath = "//table[@id='results-table']//tr[last()]//td[%1$d]";
    private TableCell tbcLastAddedExpenseProcessDate = new TableCell(By.xpath(String.format(expenseXpath, 2)), "Expenses: Process Date");
    private TableCell tbcLastAddedExpenseStatus = new TableCell(By.xpath(String.format(expenseXpath, 3)), "Expenses: Status");
    private TableCell tbcLastAddedExpenseStatusDeterminationDate = new TableCell(By.xpath(String.format(expenseXpath, 4)), "Expense: Status Determination Date");
    private TableCell tbcLastAddedExpenseAmount = new TableCell(By.xpath(String.format(expenseXpath, 5)), "Expense: Amount");
    private RadioButton rbSelectFirstExpense = new RadioButton("id=selectedRadio1", "Select Expense");
    private RadioButton rbSelectLastAddedExpense = new RadioButton("//tr[@class='dt-row-odd' or @class='dt-row-even'][last()]//input[@class='radio']", "Select Expense");

    // Expenses total amounts
    private TableCell tbcTotalRequestedAmount = new TableCell(String.format(detailPath, "Total Requested Amount:"), "Total Requested Amount");
    private TableCell tbcTotalEmployerReimbursedAmount = new TableCell(String.format(detailPath, "Total Employer Reimbursed Amount:"), "Total Employer Reimbursed Amount");
    private TableCell tbcTotalLumpSumAmountPaid = new TableCell(String.format(detailPath, "Total Lump Sum Amount Paid:"), "Total Lump Sum Amount Paid");
    private TableCell tbcTotalAmountToBePaid = new TableCell(String.format(detailPath, "Total Amount to be Paid:"), "Total Amount to be Paid");

    // Expenses buttons
    private Button btnAddExpence = new Button(By.xpath("//button[@id='addExpense']"), "Add");
    private Button btnEditExpence = new Button("id=editExpense", "Edit");
    private Button btnRemoveExpence = new Button("id=removeExpense", "Remove");
    // Disabled buttons
    private Button btnAddExpenceDisabled = new Button(By.xpath("//button[@id='addExpense' and @disabled='disabled']"), "Add (inactive)");
    private Button btnEditExpenceDisabled = new Button(By.xpath("//button[@id='editExpense' and @disabled='disabled']"), "Edit (inactive)");
    private Button btnRemoveExpenceDisabled = new Button(By.xpath("//button[@id='removeExpense' and @disabled='disabled']"), "Remove (inactive)");

    private Button btnEditRelocation = new Button("css=button[id='editRelocation']", "Edit");
    private String approved = "Approved";
    private String denied = "Denied";

    /**
     * Default constructor
     */
    public RelocationDetailsForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Relocation Detail')]"), "Relocation Detail");
    }

    /**
     * Click [Add expence] button.
     */
    public void addExpence() {
        btnAddExpence.clickAndWait();
    }

    /**
     * Click [Remove] expence
     */
    public void removeExpence() {
        btnRemoveExpence.click();
    }

    /**
     * Click [Edit] expence
     */
    public void editExpence() {
        btnEditExpence.clickAndWait();
    }

    /**
     * Click last added expence radio button.
     */
    public void clickLastAddedExpence() {
        rbSelectLastAddedExpense.click();
    }

    /**
     * Get last add expense amount text
     *
     * @return last add expense amount text
     */
    public String getLastAddedExpenseAmountText() {
        return tbcLastAddedExpenseAmount.getText();
    }

    /**
     * Get last added expense status text
     *
     * @return last added expense status text
     */
    public String getLastAddedExpenseStatusText() {
        return tbcLastAddedExpenseStatus.getText();
    }

    /**
     * Check, that expence buttons are not present.
     */
    public void expenceButtonsNotPresent() {
        btnAddExpence.assertIsNotPresent();
        btnEditExpence.assertIsNotPresent();
        btnRemoveExpence.assertIsNotPresent();
    }

    /**
     * Check buttons on the page depends on user's permissions.
     *
     * @param user - current user.
     */
    public void checkButtonsPresent(User user) {
        divideMessage("Check [Edit]");
        ifButton(user.getRelocation().getRelocationEdit(), btnEditRelocation);
        divideMessage("Check [Edit expense]");
        ifButton(user.getRelocation().getRelocationEditExpense(), btnEditExpence);
        divideMessage("Check [Add] expense");
        ifButton(user.getRelocation().getRelocationAddExpense(), btnAddExpence);
    }

    /**
     * Check, that [Add] expence button is disabled or enabled.
     *
     * @param present - true, if disabled.
     */
    public void checkAddButtonDisabled(Boolean present) {
        if (present) {
            btnAddExpenceDisabled.assertIsPresent();
        } else {
            btnAddExpenceDisabled.assertIsNotPresent();
        }
    }

    /**
     * Check, that [Edit] and [Remove] expence buttons are present and disabled or not
     *
     * @param present - true, if disabled buttons are present.
     */
    public void checkEditRemoveDisabledPresent(Boolean present) {
        if (present) {
            btnEditExpenceDisabled.isPresent();
            btnRemoveExpenceDisabled.isPresent();
        } else {
            btnEditExpence.isPresent();
            btnRemoveExpence.isPresent();
        }
    }


    /**
     * Click Edit button and fill out required fileds with saving
     *
     * @param relocationExpense RelocationExpense object
     */
    public void editExistedExpense(RelocationExpense relocationExpense) {
        btnEditExpence.clickAndWait();
        RelocationEditExpenseForm editExpenseForm = new RelocationEditExpenseForm();
        editExpenseForm.fillExpenseFormAndSave(relocationExpense);
    }

    /**
     * Edit relocation
     */
    public void editRelocation() {
        btnEditRelocation.clickAndWait();
    }

    /**
     * Check, that edit relocation button is not present on the page.
     */
    public void checkRelocationNotPresent() {
        btnEditRelocation.assertIsNotPresent();
    }

    /**
     * Select first available expense and edit all required fields
     *
     * @param relocationExpense RelocationExpense object
     */
    public void editFirstAddedExpense(RelocationExpense relocationExpense) {
        rbSelectFirstExpense.click();
        editExistedExpense(relocationExpense);
    }

    /**
     * Edit Requested Amount filed and save
     *
     * @param newRequestedAmount New Requested Amount value
     */
    public void editRequestedAmount(String newRequestedAmount) {
        rbSelectLastAddedExpense.click();
        btnEditExpence.clickAndWait();
        RelocationEditExpenseForm editExpenseForm = new RelocationEditExpenseForm();
        editExpenseForm.txbExpenseRequestedAmount.type(newRequestedAmount);
        clickAndWait(BaseButton.SAVE_CHANGES);
    }

    /**
     * Select last added expense and remove it
     */
    public void removeLastAddedExpense() {
        rbSelectLastAddedExpense.click();
        btnRemoveExpence.click();
    }

    /**
     * Validating displayed Relocation information
     *
     * @param relocation Object with expected information
     */
    public void validateInformation(Relocation relocation) {
        checkField(tbcParticipant, relocation.getTradeEnrollment().getParticipant().getFirstName(), Constants.FALSE);
        checkField(tbcTradeEnrollment, relocation.getTradeEnrollment().getPetition().getNumber(), Constants.FALSE);
        checkField(tbcApplicationDate, relocation.getApplicationDate(), Constants.FALSE);
        checkField(tbcRelocationDate, relocation.getRelocationDate(), Constants.FALSE);
        checkField(tbcRelocationDistance, relocation.getRelocationDistance(), Constants.FALSE);
        checkField(tbcEmployerName, relocation.getEmployerName(), Constants.FALSE);

        if (relocation.getStatusDeterminationDate() != null && tbcStatus.isPresent()) {
            checkField(tbcStatus, relocation.isApproved() ? approved : denied, Constants.FALSE);
            checkField(tbcStatusDeterminationDate, relocation.getStatusDeterminationDate(), Constants.FALSE);
        }
    }

    /**
     * Validate last added expense
     *
     * @param relocationExpense RelocationExpense object
     */
    public void validateLastAddedExpenseData(RelocationExpense relocationExpense) {
        checkField(tbcLastAddedExpenseProcessDate, relocationExpense.getProcessDate(), Constants.FALSE);
        checkField(tbcLastAddedExpenseStatus, relocationExpense.getStatusName(), Constants.FALSE);
        checkField(tbcLastAddedExpenseStatusDeterminationDate, relocationExpense.getStatusDeterminationDate(), Constants.FALSE);
        assertEquals("Incorrect expense amount!", reformatExpenseAmount(getLastAddedExpenseAmountText()),
                relocationExpense.getRequestedAmount());
    }

    /**
     * Reformat displayed expense amount value: cut comma, dot, $ and zeros
     *
     * @param displayedAmount Amount displayed on the page
     * @return Formatted amount
     */
    private String reformatExpenseAmount(String displayedAmount) {
        return displayedAmount.replaceAll("[$,]", "").replaceFirst("\\.\\d+", "");
    }

    /**
     * Validate total Requested Amount, Employer Reimbursed Amount, Lump Sum Amount Paid and Total Amount to be Paid
     *
     * @param expenses RelocationExpenses array
     */
    public void validateTotalAmounts(RelocationExpense[] expenses) {
        RelocationCalculateExpense calculateExpense = new RelocationCalculateExpense();
        calculateExpense.calculateTotalAmountToBePaid(expenses);
        assertEquals("Total Requested Amount is incorrect!", calculateExpense.getFormattedTotalRequestedAmount(),
                reformatExpenseAmount(tbcTotalRequestedAmount.getText()));
        assertEquals("Total Employer Reimbursed Amount is incorrect!", calculateExpense.getFormattedTotalEmployerReimbursedAmount(),
                reformatExpenseAmount(tbcTotalEmployerReimbursedAmount.getText()));
        assertEquals("Total Lump Sum Amount Paid is incorrect!", calculateExpense.getFormattedTotalLumpSumAmountPaid(),
                reformatExpenseAmount(tbcTotalLumpSumAmountPaid.getText()));
        assertEquals("Total Amount To Be Paid is incorrect!", calculateExpense.getFormattedTotalAmountToBePaid(),
                reformatExpenseAmount(tbcTotalAmountToBePaid.getText()));
    }

    /**
     * Check total Requested Amount, Employer Reimbursed Amount, Lump Sum Amount Paid and Total Amount to be Paid if amount is the same for these parameters
     *
     * @param totalAmount - total amount
     */
    public void checkAmounts(String totalAmount) {
        CustomAssertion.softAssertEquals(tbcTotalRequestedAmount.getText(), totalAmount, "Incorrect total requested amount");
        CustomAssertion.softAssertEquals(tbcTotalEmployerReimbursedAmount.getText(), totalAmount, "Incorrect employer reimburse requested amount");
        CustomAssertion.softAssertEquals(tbcTotalLumpSumAmountPaid.getText(), totalAmount, "Incorrect lump sum amount");
        CustomAssertion.softAssertEquals(tbcTotalAmountToBePaid.getText(), totalAmount, "Incorrect amount be paid");
    }

    /**
     * Get total amount to be paid
     *
     * @return total amount to be paid
     */
    public String getTotalAmountToBePaid() {
        return tbcTotalAmountToBePaid.getText();
    }
}
