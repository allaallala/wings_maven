package edu.msstate.nsparc.wings.integration.models.trade;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.Address;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

/**
 * Relocation entity
 */
public class Relocation {
    private TradeEnrollment tradeEnrollment;
    private String applicationDate;
    private String relocationDate;
    private String relocationDistance;
    private String employerName;
    private Address locationAddress;
    private String contactName;
    private String phoneNumber;
    private Address oldAddress;
    private Address newAddress;
    private boolean approved;
    private String statusDeterminationDate;
    private String reasonDenied;

    //permissions variables
    private Boolean relocCreate;
    private Boolean relocEdit;
    private Boolean relocationView;
    private Boolean relocationAddExpense;
    private Boolean relocationEditExpense;
    private static final String RELOC_CREATE = "relocCreate";
    private static final String RELOC_EDIT = "relocEdit";
    private static final String RELOC_VIEW = "relocationView";
    private static final String RELOC_ADD_EXPENSE = "relocationAddExpense";
    private static final String RELOC_EDIT_EXPENSE = "relocationEditExpense";

    /**
     * Default Relocation
     */
    public Relocation() {
        tradeEnrollment = new TradeEnrollment();
        generateRandomData();
    }

    /**
     * Specified user role permissions for relocation
     * @param role - user role.
     */
    public Relocation(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        relocCreate = Boolean.valueOf(prop.getProperty(RELOC_CREATE));
        relocEdit = Boolean.valueOf(prop.getProperty(RELOC_EDIT));
        relocationView = Boolean.valueOf(prop.getProperty(RELOC_VIEW));
        relocationAddExpense = Boolean.valueOf(prop.getProperty(RELOC_ADD_EXPENSE));
        relocationEditExpense = Boolean.valueOf(prop.getProperty(RELOC_EDIT_EXPENSE));
        tradeEnrollment = new TradeEnrollment();
        generateRandomData();
    }

    /**
     * Relocation data approved or not.
     * @param isApproved - approved/not approved (true/false)
     */
    public Relocation(boolean isApproved) {
        this();
        this.approved = isApproved;
        statusDeterminationDate = CommonFunctions.getCurrentDate();
        reasonDenied = CommonFunctions.getRandomAlphanumericalCode(Constants.EMAIL_LENGTH);
    }

    /**
     * Generates random data for relocation
     */
    private void generateRandomData() {
        applicationDate = CommonFunctions.getCurrentDate();
        relocationDate = CommonFunctions.getCurrentDate();
        relocationDistance = CommonFunctions.getRandomIntegerNumber(Constants.CODE_LENGTH);
        employerName = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        locationAddress = new Address();
        contactName = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        phoneNumber = CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH);
        oldAddress = new Address();
        newAddress = new Address();
    }

    public TradeEnrollment getTradeEnrollment() {
        return tradeEnrollment;
    }

    //User permissions

    public Boolean getRelocationCreate() {
        return relocCreate;
    }

    public Boolean getRelocationEdit() {
        return relocEdit;
    }

    public Boolean getRelocationView() {
        return relocationView;
    }

    public Boolean getRelocationAddExpense() {
        return relocationAddExpense;
    }

    public Boolean getRelocationEditExpense() {
        return relocationEditExpense;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public String getRelocationDate() {
        return relocationDate;
    }

    public String getRelocationDistance() {
        return relocationDistance;
    }

    public String getEmployerName() {
        return employerName;
    }

    public Address getLocationAddress() {
        return locationAddress;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Address getOldAddress() {
        return oldAddress;
    }

    public Address getNewAddress() {
        return newAddress;
    }

    public Boolean isApproved() {
        return approved;
    }

    public String getStatusDeterminationDate() {
        return statusDeterminationDate;
    }

    public void setStatusDeterminationDate(String statusDetDate) {
        this.statusDeterminationDate = statusDetDate;
    }

    public void setApproved(Boolean approvedYes) {
        this.approved = approvedYes;
    }

    public String getReasonDenied() {
        return reasonDenied;
    }

    public void setReasonDenied(String deniedReason) {
        this.reasonDenied = deniedReason;
    }

    public void setRelocationDate(String relDate) {
        this.relocationDate = relDate;
    }

    public void setApplicationDate(String applDate) {
        this.applicationDate = applDate;
    }

    public void setTradeEnrollment(TradeEnrollment tradeEnrl) {
        this.tradeEnrollment = tradeEnrl;
    }
}
