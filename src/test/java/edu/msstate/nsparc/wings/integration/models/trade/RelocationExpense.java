package edu.msstate.nsparc.wings.integration.models.trade;

import framework.CommonFunctions;

/**
 * Relocation expense entity
 */
public class RelocationExpense {
    private static final int MAX_REQUESTED_LENGTH = 6; // max 'Requested Amount' value length is 6
    private static final int REIMBURSED_LESS_THAN_REQUESTED = 5; // 'Employer Reimbursed Amount' value must be less than or equal to 'Amount Requested'. By default we make it less.
    private static final String EMPTY_PROCESS_DATE = "";
    private static final String APPROVED_STATUS_NAME = "Approved";
    private static final String DENIED_STATUS_NAME = "Denied";

    private boolean approved;
    private String statusName = APPROVED_STATUS_NAME;
    private String statusDeterminationDate;
    private String reasonDenied;
    private String payee;
    private String requestedAmount;
    private String employerReimbursedAmount;
    private String processDate = CommonFunctions.getCurrentDate(); // 'Process Date' = current date (if approved)
    private String lumpSumAmount = "0";

    /**
     * Generates relocation expense data (approved or not)
     * @param isApproved - true/false
     */
    public RelocationExpense(boolean isApproved) {
        this.approved = isApproved;
        generateRequiredTextFieldsData();
        if (!approved) {
            this.reasonDenied = CommonFunctions.getRandomAlphanumericalCode(REIMBURSED_LESS_THAN_REQUESTED );
            this.processDate = EMPTY_PROCESS_DATE;
            this.statusName = DENIED_STATUS_NAME;
        }
    }

    /**
     * Generates relocation expense data (approved or not) and with specified relocation
     * @param isApproved - approved or not (true/false)
     * @param relocation - relocation
     */
    public RelocationExpense(boolean isApproved, Relocation relocation) {
        this(isApproved);
        switch (relocation.getTradeEnrollment().getPetition().getType()) {
            case ATAA_LOW:
                break;
            case ATAA_HIGH:
                lumpSumAmount = "1500";
                break;
            case RTAA:
                lumpSumAmount = "1250";
                break;
            default:
                break;
        }
    }

    /**
     * Generate text fields data (required)
     */
    private void generateRequiredTextFieldsData() {
        this.statusDeterminationDate = CommonFunctions.getCurrentDate();
        this.payee = CommonFunctions.getRandomAlphanumericalCode(REIMBURSED_LESS_THAN_REQUESTED);
        this.requestedAmount = CommonFunctions.getRandomIntegerNumber(MAX_REQUESTED_LENGTH);
        this.employerReimbursedAmount = CommonFunctions.getRandomIntegerNumber(REIMBURSED_LESS_THAN_REQUESTED);
    }

    public void setApproved(boolean approvedYes) {
        this.approved = approvedYes;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getStatusDeterminationDate() {
        return statusDeterminationDate;
    }

    public String getReasonDenied() {
        return reasonDenied;
    }

    public String getPayee() {
        return payee;
    }

    public String getRequestedAmount() {
        return requestedAmount;
    }

    public String getEmployerReimbursedAmount() {
        return employerReimbursedAmount;
    }

    public String getProcessDate() {
        return processDate;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getLumpSumAmount() {
        return lumpSumAmount;
    }

    public void setEmployerReimbursedAmount(String empReimburseAmount) {
        this.employerReimbursedAmount = empReimburseAmount;
    }

    public void setRequestedAmount(String requestedSum) {
        this.requestedAmount = requestedSum;
    }

    public void setReasonDenied(String deniedReason) {
        this.reasonDenied = deniedReason;
    }
}
