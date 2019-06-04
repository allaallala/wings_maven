package edu.msstate.nsparc.wings.integration.models.trade.trainings;

import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

/**
 * This class represents Training Waiver entity
 */
public class TrainingWaiver {
    private TradeEnrollment tradeEnrollment;
    private boolean eligible;
    private String issueDate;
    private String waiverReason;
    private String waiverStatus;
    private TrainingWaiverRevocation revocation;
    private TrainingWaiverRenewal renewal;
    private String denialReason;
    private String expired = "Expired";

    private static final Integer ISSUE_DATE = 105;
    private static final Integer PETITION_DATE = 334;
    private static final Integer SEPARATION_DATE_START = 805;
    private static final Integer SEPARATION_DATE_END = 218;
    private static final Integer TRADE_ENRL_DATE = 13;
    public  static final Integer RENEWAL_DATE_START = 85;
    private static final Integer RENEWAL_DATE_END = 43;
    private static final Integer RECALUCULATED_DATE = 60;

    //User permissions
    private Boolean twCreate;
    private Boolean twEdit;
    private Boolean twView;
    private Boolean twAddRenewal;
    private Boolean twEditRenewal;
    private Boolean twAddRevocation;
    private Boolean twEditRevocation;
    private Boolean twPrintSign;
    private static final String TW_CREATE = "twCreate";
    private static final String TW_EDIT = "twEdit";
    private static final String TW_VIEW = "twView";
    private static final String TW_ADD_RENEWAL = "twAddRenewal";
    private static final String TW_EDIT_RENEWAL = "twEditRenewal";
    private static final String TW_ADD_REVOCATION = "twAddRevocation";
    private static final String TW_EDIT_REVOCATION = "twEditRevocation";
    private static final String TW_PRINT_SIGN = "twPrintSign";

    /**
     * Construct new training waiver
     */
    public TrainingWaiver() {
        tradeEnrollment = new TradeEnrollment();
        generateRandomData();
    }

    /**
     * Constructor for checking user permissions
     * @param role - user role
     */
    public TrainingWaiver(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        twCreate = Boolean.valueOf(prop.getProperty(TW_CREATE));
        twEdit = Boolean.valueOf(prop.getProperty(TW_EDIT));
        twView = Boolean.valueOf(prop.getProperty(TW_VIEW));
        twAddRenewal = Boolean.valueOf(prop.getProperty(TW_ADD_RENEWAL));
        twEditRenewal = Boolean.valueOf(prop.getProperty(TW_EDIT_RENEWAL));
        twAddRevocation = Boolean.valueOf(prop.getProperty(TW_ADD_REVOCATION));
        twEditRevocation = Boolean.valueOf(prop.getProperty(TW_EDIT_REVOCATION));
        twPrintSign = Boolean.valueOf(prop.getProperty(TW_PRINT_SIGN));
    }

    /**
     * Construct new training waiver
     * @param type - petition type
     */
    public TrainingWaiver(PetitionType type) {
        tradeEnrollment = new TradeEnrollment(type);
        generateRandomData();
    }

    /**
     * Construct new training waiver
     * @param tradeEnrl - trade enrollment to create
     */
    public TrainingWaiver(TradeEnrollment tradeEnrl) {
        this.tradeEnrollment = tradeEnrl;
        generateRandomData();
        if (tradeEnrl.getPetition().getType() == PetitionType.ATAA_LOW) {
            issueDate = CommonFunctions.getDaysAgoDate(ISSUE_DATE);
        }
    }

    /**
     * Generates random data for training waiver
     */
    private void generateRandomData() {
        eligible = true;
        waiverReason = "Training Will Start in 60 Days";
        waiverStatus = "Issued";
        denialReason = "Did not meet any of the criteria for which a waiver may be issued";
        revocation = new TrainingWaiverRevocation();
        renewal = new TrainingWaiverRenewal();
        recalculateDates(0);
    }

    /**
     * Recalculating Waiver dates with provided offset
     * @param datesDelta How many days to substitute from the initial dates
    */
    private void recalculateDates(int datesDelta) {
        // Petition Dates
        tradeEnrollment.getPetition().setDecisionDate(CommonFunctions.getDaysAgoDate(PETITION_DATE));
        tradeEnrollment.getPetition().setFileDate(CommonFunctions.getDaysAgoDate(PETITION_DATE));
        tradeEnrollment.getPetition().setImpactDate(CommonFunctions.getDaysAgoDate(PETITION_DATE));

        // Separation Job Dates
        tradeEnrollment.getSeparationJob().setStartDate(CommonFunctions.getDaysAgoDate(SEPARATION_DATE_START + datesDelta));
        tradeEnrollment.getSeparationJob().setEndDate(CommonFunctions.getDaysAgoDate(SEPARATION_DATE_END + datesDelta));

        // Trade Enrollment Dates
        tradeEnrollment.setApplicationDate(CommonFunctions.getDaysAgoDate(TRADE_ENRL_DATE));
        tradeEnrollment.setEligibilityDeterminationDate(CommonFunctions.getDaysAgoDate(TRADE_ENRL_DATE));

        // Waiver Dates
        issueDate = CommonFunctions.getDaysAgoDate(RENEWAL_DATE_END + datesDelta);

        // Renewal = 85 days after Issue date
        renewal.setRenewalDate(CommonFunctions.getDaysAgoDate(datesDelta));
    }

    /**
     * Recalculate date and make waiver status as expired
     */
    public void initializeExpired() {
        recalculateDates(RECALUCULATED_DATE);
        waiverStatus = expired;
    }

    public TradeEnrollment getTradeEnrollment() {
        return tradeEnrollment;
    }

    public boolean isEligible() {
        return eligible;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getWaiverReason() {
        return waiverReason;
    }

    public String getWaiverStatus() {
        return waiverStatus;
    }

    public void setWaiverStatus(String waiverSts) {
        this.waiverStatus = waiverSts;
    }

    public void setWaiverReason(String waiverRsn) {
        this.waiverReason = waiverRsn;
    }

    public TrainingWaiverRevocation getRevocation() {
        return revocation;
    }

    public TrainingWaiverRenewal getRenewal() {
        return renewal;
    }

    public void setEligible(boolean eligbl) {
        this.eligible = eligbl;
    }

    public String getDenialReason() {
        return denialReason;
    }

    public void setIssueDate(String issueDte) {
        this.issueDate = issueDte;
    }

    /**
     * Training waiver revocation entity
     */
    public static class TrainingWaiverRevocation {
        private String revocationDate;
        private String revocationReason;

        /**
         * Constructour training waiver revocation
         */
        public TrainingWaiverRevocation() {
            revocationDate = CommonFunctions.getCurrentDate();
            revocationReason = "Has Been Recalled";
        }

        public String getRevocationDate() {
            return revocationDate;
        }

        public String getRevocationReason() {
            return revocationReason;
        }

        public void setRevocationReason(String revocationRsn) {
            this.revocationReason = revocationRsn;
        }

        public void setRevocationDate(String newRevocationDate) {
            this.revocationDate = newRevocationDate;
        }
    }

    /**
     * Training waiver renewal entity
     */
    public static class TrainingWaiverRenewal {
        private String renewalDate;
        private String renewalReason;

        /**
         * Constructor training waiver renewal
         */
        public TrainingWaiverRenewal() {
            renewalDate = CommonFunctions.getCurrentDate();
            renewalReason = "Health - Training not Possible/Able to Work";
        }

        public String getRenewalDate() {
            return renewalDate;
        }

        public String getRenewalReason() {
            return renewalReason;
        }

        public void setRenewalReason(String renewalRsn) {
            this.renewalReason = renewalRsn;
        }

        public void setRenewalDate(String renewalDte) {
            this.renewalDate = renewalDte;
        }
    }

    //User permission getters

    public Boolean getTwCreate() {
        return twCreate;
    }

    public Boolean getTwEdit() {
        return twEdit;
    }

    public Boolean getTwView() {
        return twView;
    }

    public Boolean getTwAddRenewal() {
        return twAddRenewal;
    }

    public Boolean getTwEditRenewal() {
        return twEditRenewal;
    }

    public Boolean getTwAddRevocation() {
        return twAddRevocation;
    }

    public Boolean getTwEditRevocation() {
        return twEditRevocation;
    }

    public Boolean getTwPrintSign() {
        return twPrintSign;
    }
}
