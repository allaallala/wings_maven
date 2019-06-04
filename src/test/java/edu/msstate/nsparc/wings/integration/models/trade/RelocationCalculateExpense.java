package edu.msstate.nsparc.wings.integration.models.trade;

/**
 * Relocation calculate expense entity
 */
public class RelocationCalculateExpense {

    private static final String DOT_AND_FOLLOWING_NUMBERS = "\\.\\d+";
    private static final String EMPTY_VALUE = "";
    private static final double PART_PAID = 0.9;
    private double totalRequestedAmount = 0;
    private double totalEmployerReimbursedAmount = 0;
    private double totalLumpSumAmountPaid = 0;
    private double totalAmountToBePaid = 0;

    /**
     * Calculate total amount to be paid
     * @param relocationExpenses relocation expense
     * @return total amount to be paid
     */
    public double calculateTotalAmountToBePaid(RelocationExpense[] relocationExpenses) {
        for (RelocationExpense expense : relocationExpenses) {
            if (expense.isApproved()) {
                totalRequestedAmount += Double.parseDouble(expense.getRequestedAmount());
                totalEmployerReimbursedAmount += Double.parseDouble(expense.getEmployerReimbursedAmount());
                totalLumpSumAmountPaid += Double.parseDouble(expense.getLumpSumAmount());
                totalAmountToBePaid = (totalRequestedAmount - totalEmployerReimbursedAmount) * PART_PAID + totalLumpSumAmountPaid;
            }
        }
        return totalAmountToBePaid;
    }

    /**
     * Get requested amount to format double
     * @return formatted amount
     */
    public String getFormattedTotalRequestedAmount() {
        return Double.toString(totalRequestedAmount).replaceAll(DOT_AND_FOLLOWING_NUMBERS, EMPTY_VALUE);
    }

    /**
     * Get employer reimburse amount to format double
     * @return formatted total employer reimburse amount
     */
    public String getFormattedTotalEmployerReimbursedAmount() {
        return Double.toString(totalEmployerReimbursedAmount).replaceAll(DOT_AND_FOLLOWING_NUMBERS, EMPTY_VALUE);
    }

    /**
     * Get formatted total lump sum amount to be paid.
     * @return formatted lump amount
     */
    public String getFormattedTotalLumpSumAmountPaid() {
        return Double.toString(totalLumpSumAmountPaid).replaceAll(DOT_AND_FOLLOWING_NUMBERS, EMPTY_VALUE);
    }

    /**
     * Get formatted total amount to be paid
     * @return amount to be paid
     */
    public String getFormattedTotalAmountToBePaid() {
        return Double.toString(totalAmountToBePaid).replaceAll(DOT_AND_FOLLOWING_NUMBERS, EMPTY_VALUE);
    }
}
