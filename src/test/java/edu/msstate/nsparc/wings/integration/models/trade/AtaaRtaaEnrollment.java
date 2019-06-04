package edu.msstate.nsparc.wings.integration.models.trade;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.ParticipantType;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.PreviousJobType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.participant.PreviousJob;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This class describes ATAA/ RTAA Enrollment.
 */
public class AtaaRtaaEnrollment {
    private TradeEnrollment tradeEnrollment;
    private PreviousJob reemployment;
    private List<PreviousJob> reemploymentList = new ArrayList<>();
    private String applicationDate;
    private String uiExhaustionDate;
    private boolean exhaustionDateRequired;
    private boolean withholdTax;
    private boolean eligible;
    private String ineligibilityReason;
    private String eligibilityDeterminationDate;
    private String firstWageWeek;

    //User permissions
    private Boolean ataaCreate;
    private Boolean ataaView;
    private Boolean ataaViewManageWageSubsidiesButton;
    private Boolean ataaViewManageWagePaymentsButton;
    private Boolean ataaViewAppealDenial;
    private Boolean ataaViewEditDenial;
    private Boolean ataaViewEditAppeal;
    private Boolean ataaViewEditEnrollmentButton;
    private Boolean ataaViewAuditButton;
    private Boolean ataaEdit;
    private Boolean ataaFormsPrintSign;
    private Boolean ataaQualifyingReemploymentView;
    private Boolean ataaQualifyingReemploymentEdit;
    private Boolean ataaManageWageSubsidies;
    private Boolean ataaAddWageSubsidy;
    private Boolean ataaEditWageSubsidy;
    private Boolean ataaManageWagePayments;
    private static final String ATAA_CREATE = "ataaCreate";
    private static final String ATAA_VIEW = "ataaView";
    private static final String ATAA_VIEW_MANAGE_WAGE_SUBSIDIES = "ataaViewManageWageSubsidiesButton";
    private static final String ATAA_VIEW_MANAGE_PAYMENTS = "ataaViewManageWagePaymentsButton";
    private static final String ATAA_VIEW_APPEAL_DENIAL = "ataaViewAppealDenial";
    private static final String ATAA_VIEW_EDIT_DENIAL = "ataaViewEditDenial";
    private static final String ATAA_VIEW_EDIT_APPEAL = "ataaViewEditAppeal";
    private static final String ATAA_VIEW_EDIT_ENROLLMENT = "ataaViewEditEnrollmentButton";
    private static final String ATAA_VIEW_AUDIT = "ataaViewAuditButton";
    private static final String ATAA_EDIT = "ataaEdit";
    private static final String ATAA_FORMS_PRINT_SIGN = "ataaFormsPrintSign";
    private static final String ATAA_QUALIFYING_REEMPLOYMENT_VIEW = "ataaQualifyingReemploymentView";
    private static final String ATAA_QUALIFYING_REEMPLOYMENT_EDIT = "ataaQualifyingReemploymentEdit";
    private static final String ATAA_MANAGE_WAGE_SUBSIDIES = "ataaManageWageSubsidies";
    private static final String ATAA_ADD_WAGE_SUBSIDY = "ataaAddWageSubsidy";
    private static final String ATAA_EDIT_WAGE_SUBSIDY = "ataaEditWageSubsidy";
    private static final String ATAA_MANAGE_WAGE_PAYMENTS = "ataaManageWagePayments";

    /**
     * Constructor
     * @param type - petition type
     */
    public AtaaRtaaEnrollment(PetitionType type) {
        tradeEnrollment = new TradeEnrollment(type);
        tradeEnrollment.setParticipant(new Participant(ParticipantType.ATAA));
        reemployment = new PreviousJob(PreviousJobType.REEMPLOYMENT, null);
        reemploymentList.add(reemployment);
        generateRandomData();
    }

    /**
     * Constructor
     * @param type - petition type
     * @param days - date of start reemployment
     */
    public AtaaRtaaEnrollment(PetitionType type, Integer days) {
        tradeEnrollment = new TradeEnrollment(type, days + 2);
        tradeEnrollment.setParticipant(new Participant(ParticipantType.ATAA));
        reemployment = new PreviousJob(PreviousJobType.REEMPLOYMENT, days);
        reemploymentList.add(reemployment);
        generateRandomData();
    }

    /**
     * This constructor is defined for checking user permissions
     * @param role - user role
     */
    public AtaaRtaaEnrollment(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        ataaCreate = Boolean.valueOf(prop.getProperty(ATAA_CREATE));
        ataaView = Boolean.valueOf(prop.getProperty(ATAA_VIEW));
        ataaViewManageWageSubsidiesButton = Boolean.valueOf(prop.getProperty(ATAA_VIEW_MANAGE_WAGE_SUBSIDIES));
        ataaViewManageWagePaymentsButton = Boolean.valueOf(prop.getProperty(ATAA_VIEW_MANAGE_PAYMENTS));
        ataaViewAppealDenial = Boolean.valueOf(prop.getProperty(ATAA_VIEW_APPEAL_DENIAL));
        ataaViewEditDenial = Boolean.valueOf(prop.getProperty(ATAA_VIEW_EDIT_DENIAL));
        ataaViewEditAppeal = Boolean.valueOf(prop.getProperty(ATAA_VIEW_EDIT_APPEAL));
        ataaViewEditEnrollmentButton = Boolean.valueOf(prop.getProperty(ATAA_VIEW_EDIT_ENROLLMENT));
        ataaViewAuditButton = Boolean.valueOf(prop.getProperty(ATAA_VIEW_AUDIT));
        ataaEdit = Boolean.valueOf(prop.getProperty(ATAA_EDIT));
        ataaFormsPrintSign = Boolean.valueOf(prop.getProperty(ATAA_FORMS_PRINT_SIGN));
        ataaQualifyingReemploymentView = Boolean.valueOf(prop.getProperty(ATAA_QUALIFYING_REEMPLOYMENT_VIEW));
        ataaQualifyingReemploymentEdit = Boolean.valueOf(prop.getProperty(ATAA_QUALIFYING_REEMPLOYMENT_EDIT));
        ataaManageWageSubsidies = Boolean.valueOf(prop.getProperty(ATAA_MANAGE_WAGE_SUBSIDIES));
        ataaAddWageSubsidy = Boolean.valueOf(prop.getProperty(ATAA_ADD_WAGE_SUBSIDY));
        ataaEditWageSubsidy = Boolean.valueOf(prop.getProperty(ATAA_EDIT_WAGE_SUBSIDY));
        ataaManageWagePayments = Boolean.valueOf(prop.getProperty(ATAA_MANAGE_WAGE_PAYMENTS));
        tradeEnrollment = new TradeEnrollment(PetitionType.ATAA_HIGH);
        tradeEnrollment.setParticipant(new Participant(ParticipantType.ATAA));
        reemployment = new PreviousJob(PreviousJobType.REEMPLOYMENT, null);
        reemploymentList.add(reemployment);
        generateRandomData();
    }

    /**
     * Constructor
     * @param type - petition type
     * @param newReemploymentList reemployment list
     */
    public AtaaRtaaEnrollment(PetitionType type, List<PreviousJob> newReemploymentList) {
        this(type);
        this.reemploymentList.addAll(newReemploymentList);
    }

    /**
     * Generate random data for ataa/ rtaa enrollment
     */
    private void generateRandomData() {
        applicationDate = CommonFunctions.getDaysAgoDate(Constants.DAYS_MONTH);
        withholdTax = false;
        eligible = true;
        eligibilityDeterminationDate = CommonFunctions.getDaysAgoDate(Constants.DAYS_MONTH);
        uiExhaustionDate = eligibilityDeterminationDate;
        exhaustionDateRequired = false;
        ineligibilityReason = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
    }

    public TradeEnrollment getTradeEnrollment() {
        return tradeEnrollment;
    }

    public PreviousJob getReemployment() {
        return reemployment;
    }

    public List<PreviousJob> getFullReemploymentList() {
        return reemploymentList;
    }

    public Participant getParticipant() {
        return tradeEnrollment.getParticipant();
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public boolean isWithholdTax() {
        return withholdTax;
    }

    public Petition getPetition() {
        return tradeEnrollment.getPetition();
    }

    public boolean isEligible() {
        return eligible;
    }

    public String getEligibilityDeterminationDate() {
        return eligibilityDeterminationDate;
    }

    public String getEligibilityString() {
        if (eligible) {
            return Constants.ELIGIBLE;
        }
        return Constants.INELIGIBLE;
    }

    public String getWithholdTaxString() {
        if (withholdTax) {
            return Constants.YES_ANSWER;
        }
        return Constants.NO_ANSWER;
    }

    public String getUiExhaustionDate() {
        return uiExhaustionDate;
    }

    public String getFirstWageWeek() {
        return firstWageWeek;
    }

    public String getIneligibilityReason() {
        return ineligibilityReason;
    }

    public boolean getExhaustionDateRequired() {
        return exhaustionDateRequired;
    }

    public void setFirstWageWeek(String newFirstWageWeek) {
        this.firstWageWeek = newFirstWageWeek;
    }

    public void setEligible(boolean ifEligible) {
        this.eligible = ifEligible;
    }

    public void setExhaustionDateRequired(boolean newExhRequired) {
        this.exhaustionDateRequired = newExhRequired;
    }

    public void setUiExhaustionDate(String newUiexhaustionDate) {
        this.uiExhaustionDate = newUiexhaustionDate;
    }

    public void setEligibilityDeterminationDate(String newDeterminationDate) {
        this.eligibilityDeterminationDate = newDeterminationDate;
    }

    //User permission getters

    public Boolean getAtaaCreate() {
        return ataaCreate;
    }

    public Boolean getAtaaView() {
        return ataaView;
    }

    public Boolean getAtaaViewManageWageSubsidiesButton() {
        return ataaViewManageWageSubsidiesButton;
    }

    public Boolean getAtaaViewManageWagePaymentsButton() {
        return ataaViewManageWagePaymentsButton;
    }

    public Boolean getAtaaViewAppealDenial() {
        return ataaViewAppealDenial;
    }

    public Boolean getAtaaViewEditDenial() {
        return ataaViewEditDenial;
    }

    public Boolean getAtaaViewEditAppeal() {
        return ataaViewEditAppeal;
    }

    public Boolean getAtaaViewEditEnrollmentButton() {
        return ataaViewEditEnrollmentButton;
    }

    public Boolean getAtaaViewAuditButton() {
        return ataaViewAuditButton;
    }

    public Boolean getAtaaEdit() {
        return ataaEdit;
    }

    public Boolean getAtaaFormsPrintSign() {
        return ataaFormsPrintSign;
    }

    public Boolean getAtaaQualifyingReemploymentView() {
        return ataaQualifyingReemploymentView;
    }

    public Boolean getAtaaQualifyingReemploymentEdit() {
        return ataaQualifyingReemploymentEdit;
    }

    public Boolean getAtaaManageWageSubsidies() {
        return ataaManageWageSubsidies;
    }

    public Boolean getAtaaAddWageSubsidy() {
        return ataaAddWageSubsidy;
    }

    public Boolean getAtaaEditWageSubsidy() {
        return ataaEditWageSubsidy;
    }

    public Boolean getAtaaManageWagePayments() {
        return ataaManageWagePayments;
    }
}