package edu.msstate.nsparc.wings.integration.models.trade;


import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.PreviousJobType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.User;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.participant.PreviousJob;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

/**
 * Trade enrollment entity
 */
public class TradeEnrollment {
    private Petition petition;
    private Participant participant;
    private String applicationDate;
    private String eligibilityDeterminationDate;
    private String denialDate;
    private String denialReason;
    private String appealDate;
    private String appealNumber;
    private String appealDecision;
    private Boolean isOvertuned;

    private boolean eligible;
    private String insuranceStatus;
    private PreviousJob separationJob;
    private static final Integer ENROLLMENT_DATE = 35;

    //User permissions
    private Boolean teCreate;
    private Boolean teView;
    private Boolean teViewManageEncumbrances;
    private Boolean teViewEditDenialButton;
    private Boolean teViewAppealDenialButton;
    private Boolean teViewEditAppealButton;
    private Boolean teViewEditTradeEnrollmentButton;
    private Boolean teViewAuditButton;
    private Boolean teEdit;
    private Boolean teFormsPrintSign;
    private Boolean teEmploymentSeparationView;
    private Boolean teEmploymentSeparationEdit;
    private Boolean teAddNewExpenditure;
    private Boolean teEditExpenditur;
    private static final String TE_CREATE = "teCreate";
    private static final String TE_VIEW = "teView";
    private static final String TE_VIEW_MANAGE_ENCUMBRANCES = "teViewManageEncumbrances";
    private static final String TE_VIEW_EDIT_DENIAL = "teViewEditDenialButton";
    private static final String TE_VIEW_APPEAL = "teViewAppealDenialButton";
    private static final String TE_VIEW_EDIT_APPEAL = "teViewEditAppealButton";
    private static final String TE_VIEW_EDIT_TRADE_ENROLLMENT = "teViewEditTradeEnrollmentButton";
    private static final String TE_VIEW_AUDIT = "teViewAuditButton";
    private static final String TE_EDIT = "teEdit";
    private static final String TE_FORMS_PRINT_SIGN = "teFormsPrintSign";
    private static final String TE_EMPLOYMENT_SEPARATION_WAGES = "teEmploymentSeparationView";
    private static final String TE_EMPLOYMENT_SEPARATION_EDIT = "teEmploymentSeparationEdit";
    private static final String TE_ADD_NEW_EXPENDITURE = "teAddNewExpenditure";
    private static final String TE_EDIT_EXPENDITURE = "teEditExpenditur";

    /**
     * Default trade enrollment
     */
    public TradeEnrollment() {
        this(PetitionType.RTAA);
    }

    /**
     * Specific constructor for
     * @param role - user role
     */
    public TradeEnrollment(Roles role) {
        this(PetitionType.RTAA);
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        teCreate = Boolean.valueOf(prop.getProperty(TE_CREATE));
        teView = Boolean.valueOf(prop.getProperty(TE_VIEW));
        teViewManageEncumbrances = Boolean.valueOf(prop.getProperty(TE_VIEW_MANAGE_ENCUMBRANCES));
        teViewEditDenialButton = Boolean.valueOf(prop.getProperty(TE_VIEW_EDIT_DENIAL));
        teViewAppealDenialButton = Boolean.valueOf(prop.getProperty(TE_VIEW_APPEAL));
        teViewEditAppealButton = Boolean.valueOf(prop.getProperty(TE_VIEW_EDIT_APPEAL));
        teViewEditTradeEnrollmentButton = Boolean.valueOf(prop.getProperty(TE_VIEW_EDIT_TRADE_ENROLLMENT));
        teViewAuditButton = Boolean.valueOf(prop.getProperty(TE_VIEW_AUDIT));
        teEdit = Boolean.valueOf(prop.getProperty(TE_EDIT));
        teFormsPrintSign = Boolean.valueOf(prop.getProperty(TE_FORMS_PRINT_SIGN));
        teEmploymentSeparationView = Boolean.valueOf(prop.getProperty(TE_EMPLOYMENT_SEPARATION_WAGES));
        teEmploymentSeparationEdit = Boolean.valueOf(prop.getProperty(TE_EMPLOYMENT_SEPARATION_EDIT));
        teAddNewExpenditure = Boolean.valueOf(prop.getProperty(TE_ADD_NEW_EXPENDITURE));
        teEditExpenditur = Boolean.valueOf(prop.getProperty(TE_EDIT_EXPENDITURE));
    }

    /**
     * Generate data for trade enrollment with specified petition type
     * @param petitionType petition type
     */
    public TradeEnrollment(PetitionType petitionType) {
        petition = new Petition(petitionType);
        participant = new Participant();
        generateRandomData();
    }

    public TradeEnrollment(User user) {
        petition = new Petition(PetitionType.RTAA);
        participant = user.getParticipant();
        generateRandomData();
    }

    /**
     * Generate data for trade enrollment with specified separation days
     * @param petitionType - petition type
     * @param separationDays - days before current data for start separation.
     */
    public TradeEnrollment(PetitionType petitionType, Integer separationDays) {
        petition = new Petition(petitionType);
        participant = new Participant();
        generateWithSpecifiedSeparation(separationDays);
    }

    /**
     * Generate random data for trade enrollment
     */
    private void generateRandomData() {
        applicationDate = CommonFunctions.getDaysAgoDate(ENROLLMENT_DATE);
        eligibilityDeterminationDate = CommonFunctions.getDaysAgoDate(ENROLLMENT_DATE);
        denialDate = CommonFunctions.getYesterdayDate();
        denialReason = "Buscandote";
        appealDate = CommonFunctions.getYesterdayDate();
        appealNumber = "667";
        appealDecision = "Overturned";
        isOvertuned = true;
        eligible = true;
        insuranceStatus = "Claimant Referred by WPRS";
        separationJob = new PreviousJob(PreviousJobType.SEPARATION, null);

    }

    /**
     * Generate random data for trade enrollment with specified separation date
      * @param separationDays - days before current date for start separation.
     */
    private void generateWithSpecifiedSeparation(Integer separationDays) {
        applicationDate = CommonFunctions.getDaysAgoDate(ENROLLMENT_DATE);
        eligibilityDeterminationDate = CommonFunctions.getDaysAgoDate(ENROLLMENT_DATE);
        eligible = true;
        insuranceStatus = "Claimant Referred by WPRS";
        separationJob = new PreviousJob(PreviousJobType.SEPARATION, separationDays);
    }

    public Petition getPetition() {
        return petition;
    }

    public Participant getParticipant() {
        return participant;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public String getEligibilityDeterminationDate() {
        return eligibilityDeterminationDate;
    }

    public String getDenialDate() {
        return denialDate;
    }

    public String getDenialReason() {
        return denialReason;
    }

    public String getAppealDate() {
        return appealDate;
    }

    public String getAppealNumber() {
        return appealNumber;
    }

    public String getAppealDecision() {
        return appealDecision;
    }

    public Boolean isOvertuned() {
        return isOvertuned;
    }

    public boolean isEligible() {
        return eligible;
    }

    public String getInsuranceStatus() {
        return insuranceStatus;
    }

    public PreviousJob getSeparationJob() {
        return separationJob;
    }

    public void setApplicationDate(String applDate) {
        this.applicationDate = applDate;
    }

    public void setAppealNumber(String newAppealNumber) {
        this.appealNumber = newAppealNumber;
    }

    public void setEligibilityDeterminationDate(String eligibilityDetDate) {
        this.eligibilityDeterminationDate = eligibilityDetDate;
    }

    public void setIneligibilityReason(String newReason) {
        this.denialReason = newReason;
    }

    public void setEligible(boolean elig) {
        this.eligible = elig;
    }

    public void setParticipant(Participant partip) {
        this.participant = partip;
    }

    /**
     * Get full name of trade enrollment in format string
     * @return full name of trade enrollment
     */
    public String getFullTradeEnrollmentName() {
        return String.format("%1$s - %2$s - Certified %3$s", petition.getNumber(),
                petition.getEmployer().getCompanyName(), petition.getCertificationDate());
    }

    //User permission getters

    public Boolean getTeCreate() {
        return teCreate;
    }

    public Boolean getTeView() {
        return teView;
    }

    public Boolean getTeViewManageEncumbrances() {
        return teViewManageEncumbrances;
    }

    public Boolean getTeViewEditDenialButton() {
        return teViewEditDenialButton;
    }

    public Boolean getTeViewAppealDenialButton() {
        return teViewAppealDenialButton;
    }

    public Boolean getTeViewEditAppealButton() {
        return teViewEditAppealButton;
    }

    public Boolean getTeViewEditTradeEnrollmentButton() {
        return teViewEditTradeEnrollmentButton;
    }

    public Boolean getTeViewAuditButton() {
        return teViewAuditButton;
    }

    public Boolean getTeEdit() {
        return teEdit;
    }

    public Boolean getTeFormsPrintSign() {
        return teFormsPrintSign;
    }

    public Boolean getTeEmploymentSeparationView() {
        return teEmploymentSeparationView;
    }

    public Boolean getTeEmploymentSeparationEdit() {
        return teEmploymentSeparationEdit;
    }

    public Boolean getTeAddNewExpenditure() {
        return teAddNewExpenditure;
    }

    public Boolean getTeEditExpenditur() {
        return teEditExpenditur;
    }
}
