package edu.msstate.nsparc.wings.integration.models.trade;

import framework.CommonFunctions;

/**
 * Expenditure encumbrance entity
 */
public class ExpenditureEncumbrance {
    private String processDate;
    private String amount;
    private ExpenditureType type = ExpenditureType.EXPENSE;
    private String category = "Tuition";
    private String payee;
    private String receivedDate;
    private static final Integer PAYEE_LENGTH = 5;
    private String specificReason = CommonFunctions.getRandomAlphanumericalCode(PAYEE_LENGTH);

    /**
     * Default expenditure encumbrance
     */
    public ExpenditureEncumbrance() {
        generateRandomData();
    }

    /**
     * Expenditure encumbrance with specified expenditure type
     * @param expenditureType - expenditure type
     */
    public ExpenditureEncumbrance(ExpenditureType expenditureType) {
        this();
        this.type = expenditureType;
    }

    /**
     * Expenditure encumbrance with specified expenditure type and category
     * @param expenditureType - expenditure type
     * @param categoryExp - expenditure category
     */
    public ExpenditureEncumbrance(ExpenditureType expenditureType, String categoryExp) {
        this(expenditureType);
        this.category = categoryExp;
    }

    /**
     * Generate random data
     */
    public final void generateRandomData() {
        processDate = CommonFunctions.getCurrentDate();
        amount = CommonFunctions.getRandomIntegerNumber(2);
        payee = CommonFunctions.getRandomLiteralCode(PAYEE_LENGTH);
        receivedDate = CommonFunctions.getCurrentDate();
    }

    public void setEncumbranceType(ExpenditureType newType) {
        this.type = newType;
    }

    public String getProcessDate() {
        return processDate;
    }

    public String getAmount() {
        return amount;
    }

    public ExpenditureType getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getPayee() {
        return payee;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public String getSpecificReason() {
        return specificReason;
    }

    public enum ExpenditureType {
        EXPENSE, ENCUMBRANCE, DEOBLIGATION, REFUND, LOST_STOLEN;

        @Override
        public String toString() {
            //only capitalize the first letter
            String s = super.toString();
            return s.substring(0, 1) + s.substring(1).toLowerCase();
        }
    }
}
