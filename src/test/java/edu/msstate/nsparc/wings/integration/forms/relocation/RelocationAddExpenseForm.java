package edu.msstate.nsparc.wings.integration.forms.relocation;

import edu.msstate.nsparc.wings.integration.models.trade.RelocationExpense;
import framework.elements.CheckBox;
import framework.elements.RadioButton;
import framework.elements.TableCell;
import framework.elements.TextBox;
import org.openqa.selenium.By;

/**
 * This class describes relocation add expense form
 */
public class RelocationAddExpenseForm extends RelocationBaseForm {

    public static final String DEFAULT_LUMP_FOR_PETITION_MORE_80000 = "1,250.00";
    public static final String DEFAULT_LUMP_FOR_PETITION_70000_TO_79999 = "1,500.00";
    private String maxSum = "1250";

    // Required fields
    private final RadioButton rbExpenseApproved = new RadioButton("id=tmpRelocationExpense.isApproved1", "Approved");
    private final RadioButton rbExpenseDenied = new RadioButton("id=tmpRelocationExpense.isApproved2", "Denied");
    private final TextBox txbExpenseStatusDeterminationDate = new TextBox("id=tmpRelocationExpense.dateStatusDetermination", "Status Determination Date");
    private final TextBox txbExpenseReasonDenied = new TextBox("id=tmpRelocationExpense.deniedReason", "Reason Denied");
    private final TextBox txbExpensePayee = new TextBox("id=tmpRelocationExpense.payee", "Payee");
    private final TextBox txbExpenseEmployerReimbursedAmount = new TextBox("id=tmpRelocationExpense.employerReimbursedAmount", "Employer Reimbursed Amount");

    public final CheckBox chkIncludeLumpSum = new CheckBox("id=tmpRelocationExpense.isLumpSum1", "Include Lump Sum?");
    private final CheckBox chkOverride = new CheckBox("id=tmpRelocationExpense.isOverride1", "Override?");
    private final TextBox txbLumpSumAmountPaid = new TextBox("id=tmpRelocationExpense.amountLumpSum", "Lump Sum Amount Paid");
    public final TableCell tbcLumpSumAmountPaid = new TableCell("//td[contains(text(),'Lump Sum Amount Paid:')]/following-sibling::td[1]", "Lump Sum Amount Paid");

    private final TableCell tbcMaxLumpSumIs1250ErrorMessage = new TableCell("//span[contains(text(),'Maximum Lump Sum Amount for this Trade Enrollment is $ 1,250')]",
            "Maximum Lump Sum Amount for this Trade Enrollment is $ 1,250 ");

    private final TableCell tbcMaxLumpSumIs1500ErrorMessage = new TableCell("//span[contains(text(),'Maximum Lump Sum Amount for this Trade Enrollment is $ 1,500')]",
            "Maximum Lump Sum Amount for this Trade Enrollment is $ 1,500 ");

    /**
     * Default constructor
     */
    public RelocationAddExpenseForm() {
        super(By.xpath("//h1[contains(.,'Add Relocation Expense')]"), "Add Relocation Expense");
    }

    /**
     * Constructor of the form with defined locator and title of the form
     *
     * @param locator - locator of the form
     * @param title   - title of the form
     */
    public RelocationAddExpenseForm(By locator, String title) {
        super(locator, title);
    }

    /**
     * Click [Override] check box.
     */
    public void clickOverrideCheckbox() {
        chkOverride.click();
    }

    /**
     * Click the 'Override' checkbox and check the 'Include Lump Sum' is available to edit
     */
    public void checkIncludeLumpSumAvailableEdit() {
        chkOverride.click();
        txbLumpSumAmountPaid.isPresent();
    }

    /**
     * Input lump sum amount
     *
     * @param lumpAmount - lump amount to type
     */
    public void inputLumpSum(String lumpAmount) {
        txbLumpSumAmountPaid.type(lumpAmount);
    }

    /**
     * Fill out required fields
     *
     * @param relocationExpense RelocationExpense object
     */
    public void fillExpenseFormAndSave(RelocationExpense relocationExpense) {
        fillOutStatusRelatedFields(relocationExpense);
        fillOutPayeeInfo(relocationExpense);
        clickAdd();
    }

    /**
     * Fill out Status related fields
     *
     * @param relocationExpense RelocationExpense object
     */
    public void fillOutStatusRelatedFields(RelocationExpense relocationExpense) {
        if (relocationExpense.isApproved()) {
            rbExpenseApproved.click();
        } else {
            rbExpenseDenied.click();
            txbExpenseReasonDenied.type(relocationExpense.getReasonDenied());
        }
        txbExpenseStatusDeterminationDate.type(relocationExpense.getStatusDeterminationDate());
    }

    /**
     * Fill out Payee and Amount required fields
     *
     * @param relocationExpense RelocationExpense object
     */
    public void fillOutPayeeInfo(RelocationExpense relocationExpense) {
        txbExpensePayee.type(relocationExpense.getPayee());
        txbExpenseRequestedAmount.type(relocationExpense.getRequestedAmount());
        txbExpenseEmployerReimbursedAmount.type(relocationExpense.getEmployerReimbursedAmount());
    }

    /**
     * Check, that max lump sum error message is present on the page.
     *
     * @param sum - sum.
     */
    public void checkMaxLumpSumPresent(String sum) {
        if (sum.contains(maxSum)) {
            tbcMaxLumpSumIs1250ErrorMessage.isPresent();
        } else {
            tbcMaxLumpSumIs1500ErrorMessage.isPresent();
        }
    }
}
