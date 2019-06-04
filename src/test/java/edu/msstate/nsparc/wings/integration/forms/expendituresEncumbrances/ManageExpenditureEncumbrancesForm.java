package edu.msstate.nsparc.wings.integration.forms.expendituresEncumbrances;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.enums.Buttons;
import edu.msstate.nsparc.wings.integration.enums.Popup;
import edu.msstate.nsparc.wings.integration.forms.PreviewExpenditureEncumbrance;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import framework.customassertions.CustomAssertion;
import framework.elements.*;
import org.openqa.selenium.By;

import static org.testng.Assert.assertEquals;


/**
 * This form is opened from Trade Enrollment participantSSDetails form by clicking on 'Manage Expenditures Encumbrances' button
 */
public class ManageExpenditureEncumbrancesForm extends BaseWingsForm {
    private float balance = 0;
    // Add / Edit
    private Button btnAdd = new Button("css=button[id='addEncExp']", "Add");
    private Button btnEdit = new Button("css=button[id='editEncExp']", "Edit");
    private TextBox txbProcessDate = new TextBox("css=input[id='dateProcess']", "Process Date");
    private TextBox txbTransactionAmount = new TextBox("css=input[id='transactionAmount']", "Transaction Amount");
    private RadioButton rbExpense = new RadioButton("css=input[value='EXPENSE']", "Expense");
    private RadioButton rbEncumbrance = new RadioButton("css=input[value='ENCUMBRANCE']", "Encumbrance");
    private RadioButton rbDeobligation  = new RadioButton("css=input[value='DEOBLIGATION']", "Deobligation");
    private RadioButton rbRefund  = new RadioButton("css=input[value='REFUND']", "Refund");
    private RadioButton rbLostStolen  = new RadioButton("css=input[value='LOST_STOLEN']", "Lost/Stolen");
    private ComboBox cmbCategory = new ComboBox(By.xpath("//select[@id='transactionCategory.id'] | //select[@id='tempTransactionCategory.id']"), "Transaction Category");
    private TextBox txbPayee = new TextBox("css=input[id='payee']", "Payee");
    private TextBox txbReceivedDate = new TextBox("css=input[id='dateReceived']", "Received Date");
    private TextBox txbSpecificReason = new TextBox(By.id("otherCategory"), "Specific Reason");

    // Search
    private TextBox txbProcessDateFrom = new TextBox(By.xpath("//input[@id='minDateProcess']"), "Process Date - From");
    private TextBox txbProcessDateTo = new TextBox(By.xpath("//input[@id='maxDateProcess']"), "Process Date - To");
    private ComboBox cmbTransactionType = new ComboBox("css=select[id='transactionType']", "Transaction Type");
    private TableCell tbcLastExpenditureDetails = new TableCell(By.xpath("//table[@id='results-table']//tbody//tr"), "Last added Expenditure Details");
    private RadioButton rbFirstSearchResult = new RadioButton(By.xpath("//input[@id='selectedRadio1']"), "First Search Result");
    private RadioButton rbLastAddedExpenditure = new RadioButton("//tr[@class='dt-row-odd' or @class='dt-row-even'][last()]//input", "Last Search Result");
    private Button btnRemove = new Button("//input[@value='Remove']", "Remove");

    // Search table
    private TableCell tbcExpEncCategory = new TableCell(By.xpath("//table[@id='results-table']//td[5]"), "Transaction Category");
    private Link lnkLastPage = new Link("//a[contains(text(),'Last')]", "Last");
    private Button btnDisabledRemove = new Button(By.xpath("//*[@id='removeEncExp' and @disabled]"), "Remove (disabled)");
    private Button btnDisabledEdit = new Button(By.xpath("//*[@id='editEncExp' and @disabled]"), "Edit (disabled)");
    private Button btnOpenLastExpenditurePreviewPage = new Button("//tr[@class='dt-row-odd' or @class='dt-row-even'][last()]//img", "Preview button");
    private String expenditureLocator = "//*[@id='results-table']/tbody/tr[%1$d]/td[6]";

    public ManageExpenditureEncumbrancesForm() {
        super(By.xpath("//span[@id='breadCrumb'][contains(.,'Trade Enrollment Detail')]"), "Manage Expenditures Encumbrances");
    }

    public void openLastExpenditurePreview() {
        btnOpenLastExpenditurePreviewPage.click();
    }

    public void checkFirstResult(Boolean present) {
        if (present) {
            rbFirstSearchResult.isPresent();
        } else {
            rbFirstSearchResult.waitForNotVisible();
        }
    }

    public void validateCategoryAndSpecificReason(ExpenditureEncumbrance expenditureEncumbrance) {
        CustomAssertion.softAssertContains(tbcExpEncCategory.getText(),expenditureEncumbrance.getCategory(),
                "Assertion category fail!");
        CustomAssertion.softAssertContains(tbcExpEncCategory.getText(),expenditureEncumbrance.getSpecificReason(),
                "Assertion Specific reason fail!");
       click(BaseButton.OPEN_PREVIEW_PAGE);
        PreviewExpenditureEncumbrance previewPage = new PreviewExpenditureEncumbrance();
        previewPage.validateSpecificReasonInformation(expenditureEncumbrance);
    }

    public float calculateBalance(ExpenditureEncumbrance expenditureEncumbrance) {
        ManageExpenditureEncumbrancesForm encumbrancesForm = new ManageExpenditureEncumbrancesForm();
        balance += encumbrancesForm.impactBalance(expenditureEncumbrance);
        return balance;
    }

    private float getFormattedBalance() {
        float formattedBalance = balance;
        if (balance < 0) {
            formattedBalance *= -1;
        }
        return formattedBalance;
    }

    private float impactBalance(ExpenditureEncumbrance expenditureEncumbrance) {
        float tempBalance = 0;
        switch (expenditureEncumbrance.getType()) {
            case EXPENSE:
                tempBalance -= Float.parseFloat(expenditureEncumbrance.getAmount());
                break;
            case DEOBLIGATION:
                tempBalance -= Float.parseFloat(expenditureEncumbrance.getAmount());
                break;
            case ENCUMBRANCE:
                tempBalance += Float.parseFloat(expenditureEncumbrance.getAmount());
                break;
            case REFUND:
                tempBalance += Float.parseFloat(expenditureEncumbrance.getAmount());
                break;
            default:
                return tempBalance;
        }
        return tempBalance;
    }

    public void addExpenditure(ExpenditureEncumbrance expenditureEncumbrance) {
        btnAdd.clickAndWait();
        fillExpenditureDetails(expenditureEncumbrance);
        clickAndWait(BaseButton.CREATE);
    }

    public void cancelAddExpenditure(ExpenditureEncumbrance expenditureEncumbrance) {
        btnAdd.clickAndWait();
        fillExpenditureDetails(expenditureEncumbrance);
        clickAndWait(BaseButton.CANCEL);
        areYouSure(Popup.Yes);
    }

    public void performSearch(ExpenditureEncumbrance expenditureEncumbrance) {
        fillSearchCriteria(expenditureEncumbrance);
        clickButton(Buttons.Search);
    }

    public void fillSearchCriteria(ExpenditureEncumbrance expenditureEncumbrance) {
        txbProcessDateFrom.type(expenditureEncumbrance.getProcessDate());
        txbProcessDateTo.type(expenditureEncumbrance.getProcessDate());
        cmbTransactionType.select(expenditureEncumbrance.getType().toString());
    }

    public void editExpenditure(ExpenditureEncumbrance expenditureEncumbrance) {
        selectExpenditureAndOpenEditForm();
        fillExpenditureDetails(expenditureEncumbrance);

        clickAndWait(BaseButton.SAVE_CHANGES);
    }

    public void selectExpenditureAndOpenEditForm() {
        rbFirstSearchResult.click();
        btnEdit.clickAndWait();
    }

    public void clickFirstSearchResult() {
        rbFirstSearchResult.click();
    }

    public void selectLastExpenditureAndOpenEditForm() {
        rbLastAddedExpenditure.click();
        btnEdit.clickAndWait();
    }

    public void fillExpenditureDetails(ExpenditureEncumbrance expenditureEncumbrance) {
        txbProcessDate.type(expenditureEncumbrance.getProcessDate());
        txbTransactionAmount.type(expenditureEncumbrance.getAmount());
        switch (expenditureEncumbrance.getType()) {
            case EXPENSE:
                rbExpense.click();
                break;
            case ENCUMBRANCE:
                rbEncumbrance.click();
                break;
            case DEOBLIGATION:
                rbDeobligation.click();
                break;
            case REFUND:
                rbRefund.click();
                break;
            case LOST_STOLEN:
                rbLostStolen.click();
                break;
            default:
                break;
        }
        cmbCategory.select(expenditureEncumbrance.getCategory());

        if (txbPayee.isPresent() && txbReceivedDate.isPresent()) {
            fillInExpenseFields(expenditureEncumbrance);
        }
        BaseOtherElement.LOADING.getElement().waitForNotVisible();
        if (txbSpecificReason.isPresent()) {
            fillInMiscellaneousFields(expenditureEncumbrance);
        }
    }

    public void fillInMiscellaneousFields(ExpenditureEncumbrance expenditureEncumbrance) {
        txbSpecificReason.type(expenditureEncumbrance.getSpecificReason());
    }

    private void fillInExpenseFields(ExpenditureEncumbrance expenditureEncumbrance) {
        txbPayee.type(expenditureEncumbrance.getPayee());
        txbReceivedDate.type(expenditureEncumbrance.getReceivedDate());
    }

    public void changeExpenditureType(ExpenditureEncumbrance expenditureEncumbrance) {
        if (expenditureEncumbrance.getType() == ExpenditureEncumbrance.ExpenditureType.EXPENSE) {
            rbEncumbrance.click();
        } else {
            rbExpense.click();
        }
    }

    public void validateExpenditureEditForm(ExpenditureEncumbrance expenditureEncumbrance) {

        CustomAssertion.softAssertEquals(txbProcessDate.getValue(), expenditureEncumbrance.getProcessDate(),
                "Assert Process Date failed!");
        CustomAssertion.softAssertEquals(cmbCategory.getSelectedLabel(), expenditureEncumbrance.getCategory(),
                "Assert Category failed!");
        CustomAssertion.softAssertEquals(txbTransactionAmount.getValue().substring(0, 2), expenditureEncumbrance.getAmount(),
                "Assert Transaction Amount failed!");

        if (txbPayee.isPresent() && txbReceivedDate.isPresent()) {
            validateExpenseExpenditureEditForm(expenditureEncumbrance);
        }

        if (isPresentMiscellaneousField()) {
            validateSpecificReason(expenditureEncumbrance);
        }
    }

    public boolean isPresentMiscellaneousField() {
        return txbSpecificReason.isPresent();
    }

    private void validateExpenseExpenditureEditForm(ExpenditureEncumbrance expenditureEncumbrance) {
        CustomAssertion.softAssertEquals(txbPayee.getValue(), expenditureEncumbrance.getPayee(),"Assert Payee failed!");
        CustomAssertion.softAssertEquals(txbReceivedDate.getValue(), expenditureEncumbrance.getReceivedDate(),"Assert Received Date failed!");
    }

    private void validateSpecificReason(ExpenditureEncumbrance expenditureEncumbrance) {
        assertEquals("Assert Specific Reason failed!", expenditureEncumbrance.getSpecificReason(), txbSpecificReason.getValue());
    }

    public void validateExpenditureDetails(ExpenditureEncumbrance expenditureEncumbrance) {
        if (lnkLastPage.isPresent()) {
            lnkLastPage.clickAndWait();
        }
        CustomAssertion.softAssertContains(tbcLastExpenditureDetails.getText(), expenditureEncumbrance.getAmount(),
                "Incorrect encumbrance amount");
        CustomAssertion.softAssertContains(tbcLastExpenditureDetails.getText(), expenditureEncumbrance.getType().toString(),
                "Incorrect encumbrance type");
        CustomAssertion.softAssertContains(tbcLastExpenditureDetails.getText(), expenditureEncumbrance.getCategory(),
                "Incorrect encumbrance category");
    }

    public void validateProcessDate(ExpenditureEncumbrance expenditureEncumbrance) {
        CustomAssertion.softAssertContains(tbcLastExpenditureDetails.getText(), expenditureEncumbrance.getProcessDate(),
                "Incorrect process date");
    }

    /**
     * Removing Expenditure record
     */
    public void removeExpenditure() {
        rbFirstSearchResult.click();
        btnRemove.waitForIsElementPresent();
        btnRemove.click();
        areYouSure(Popup.Yes);
    }

    /**
     * Validating default values of search parameters
     */
    public void validateDefaultSearchParameters() {
        ManageExpenditureEncumbrancesForm manageExpenditureEncumbrancesForm = new ManageExpenditureEncumbrancesForm();
        CustomAssertion.softAssertEquals(manageExpenditureEncumbrancesForm.txbProcessDateFrom.getValue(), "",
                "Process Date From isn't empty");
        CustomAssertion.softAssertEquals(manageExpenditureEncumbrancesForm.txbProcessDateTo.getValue(), "",
                "Process Date To isn't empty");
        CustomAssertion.softAssertEquals(manageExpenditureEncumbrancesForm.cmbTransactionType.getSelectedLabel(), "Any",
                "Transaction Type isn't default type");
    }

    public void cancelRemoveExpenditure() {
        btnRemove.click();
        areYouSure(Popup.No);
    }

    public void validateDisabledButtons() {
        btnDisabledRemove.isPresent();
        btnDisabledEdit.isPresent();
    }

    public void selectTransactionCategory(String categoryName) {
        cmbCategory.select(categoryName);
    }

    public void validateBalanceInRow(ExpenditureEncumbrance expenditureEncumbrance, int rowNumber) {
        TableCell tbcExpenditureSearchResultRow = new TableCell(By.xpath(String.format(expenditureLocator, rowNumber)), "Expenditure type: "
                + expenditureEncumbrance.getType());
        calculateBalance(expenditureEncumbrance);
        CustomAssertion.softTrue("Incorrect expenditure result",tbcExpenditureSearchResultRow.getText().trim().contains("$"
                + Float.toString(getFormattedBalance())));
        info("Expected balance: $" + balance + ", Actual balance: " + tbcExpenditureSearchResultRow.getText());
    }
}
