package edu.msstate.nsparc.wings.integration.forms;

import edu.msstate.nsparc.wings.integration.base.BaseWingsForm;
import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.models.trade.ExpenditureEncumbrance;
import framework.customassertions.CustomAssertion;
import framework.elements.Div;
import framework.elements.TableCell;
import org.openqa.selenium.By;

/**
 * Preview for expenditure encumbrance.
 */
public class PreviewExpenditureEncumbrance extends BaseWingsForm {
    private Div dvValidationError = new Div(By.id("id.errors"), "Validation errors found on page");
    private String validationErrorText = "Validation errors found on page.";
    private String detailPath = "//td[contains(.,'%1$s')]/following-sibling::td";

    private String xpathExpenditure = "//*[@id='arresults-table']" + detailPath;
    private TableCell tbcTransactionCategory = new TableCell(By.xpath(String.format(xpathExpenditure, "Transaction Category")), "Transaction Category");
    private TableCell tbcProcessDate = new TableCell(By.xpath(String.format(xpathExpenditure, "Process Date")), "Process Date");
    private TableCell tbcTransactionAmount = new TableCell(By.xpath(String.format(xpathExpenditure, "Transaction Amount")), "Transaction Amount");
    private TableCell tbcTransactionType = new TableCell(By.xpath(String.format(xpathExpenditure, "Transaction Type")), "Transaction Type");
    private TableCell tbcSpecificReason = new TableCell(String.format(detailPath, "Specific Reason"), "Specific Reason");

    /**
     * Default constructor
     */
    public PreviewExpenditureEncumbrance() {
        super(By.xpath("//h1[contains(.,'Expenditure/Encumbrance')]"), "Preview page");
    }

    /**
     * Validate expenditure encumbrance information
     *
     * @param expenditureEncumbrance - expenditure encumbrance information
     */
    public void validateExpenditureEncumbranceInformation(ExpenditureEncumbrance expenditureEncumbrance) {
        CustomAssertion.softAssertEquals(tbcTransactionCategory.getText(), expenditureEncumbrance.getCategory(), "Incorrect transaction category");
        CustomAssertion.softAssertEquals(tbcProcessDate.getText(), expenditureEncumbrance.getProcessDate(), "Incorrect process date");
        CustomAssertion.softAssertContains(tbcTransactionAmount.getText(), expenditureEncumbrance.getAmount(), "Incorrect transaction amount");
        CustomAssertion.softAssertContains(tbcTransactionType.getText(), expenditureEncumbrance.getType().toString(), "Incorrect transaction type");
    }

    /**
     * Validate specific reason information.
     *
     * @param expenditureEncumbrance - expenditure encumbrance
     */
    public void validateSpecificReasonInformation(ExpenditureEncumbrance expenditureEncumbrance) {
        checkField(tbcSpecificReason, expenditureEncumbrance.getSpecificReason(), Constants.TRUE);
    }
}
