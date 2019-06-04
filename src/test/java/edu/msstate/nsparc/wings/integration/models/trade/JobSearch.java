package edu.msstate.nsparc.wings.integration.models.trade;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.PetitionType;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

/**
 * Job Search entity
 */
public class JobSearch {
    //variables connected with user role
    private Boolean jobSearchCreate;
    private Boolean jobSearchEdit;
    private Boolean jobSearchView;
    private Boolean jobSearchAddExpense;
    private Boolean jobSearchEditExpense;
    private static final String JOB_SEARCH_CREATE = "jobSearchCreate";
    private static final String JOB_SEARCH_EDIT = "jobSearchEdit";
    private static final String JOB_SEARCH_VIEW = "jobSearchView";
    private static final String JOB_SEARCH_ADD_EXPENSE = "jobSearchAddExpense";
    private static final String JOB_SEARCH_EDIT_EXPENSE = "jobSearchEditExpense";

    private TradeEnrollment tradeEnrollment;
    private String companyName;
    private String lineOne;
    private String city;
    private String state;
    private String zipCode;
    private String county;
    private String contactName;
    private String phoneNumber;
    private String email;
    private String mileage;
    private String applicationDate;
    private String interviewDate;
    private boolean approved;
    private String statusDeterminationDate;

    /**
     * Job search data
     */
    public JobSearch() {
        tradeEnrollment = new TradeEnrollment(PetitionType.RTAA);
        generateRandomData();
    }

    /**
     * Specifies user role permissions
     * @param role - user role
     */
    public JobSearch(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        jobSearchCreate = Boolean.valueOf(prop.getProperty(JOB_SEARCH_CREATE));
        jobSearchEdit = Boolean.valueOf(prop.getProperty(JOB_SEARCH_EDIT));
        jobSearchView = Boolean.valueOf(prop.getProperty(JOB_SEARCH_VIEW));
        jobSearchAddExpense = Boolean.valueOf(prop.getProperty(JOB_SEARCH_ADD_EXPENSE));
        jobSearchEditExpense = Boolean.valueOf(prop.getProperty(JOB_SEARCH_EDIT_EXPENSE));
    }

    /**
     * Job search without adding tradeEnrollment
     * @param enrl - trade enrl
     */
    public JobSearch(Boolean enrl) {
        if (enrl) {
            tradeEnrollment = new TradeEnrollment(PetitionType.RTAA);
        }
        generateRandomData();
    }

    /**
     * Generate random data for job search
     */
    private void generateRandomData() {
        companyName = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        lineOne = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        city = "Starkville";
        state = "Mississippi";
        zipCode = "39759";
        county = "Adams";
        contactName = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        phoneNumber = CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH);
        email = "wingsqa@gmail.com";
        mileage = CommonFunctions.getRandomIntegerNumber(Constants.CODE_LENGTH);
        applicationDate = CommonFunctions.getCurrentDate();
        interviewDate = CommonFunctions.getCurrentDate();
    }

    public Boolean getJobSearchCreate() {
        return jobSearchCreate;
    }

    public Boolean getJobSearchEdit() {
        return jobSearchEdit;
    }

    public Boolean getJobSearchView() {
        return jobSearchView;
    }

    public Boolean getJobSearchAddExpense() {
        return jobSearchAddExpense;
    }

    public Boolean getJobSearchEditExpense() {
        return jobSearchEditExpense;
    }

    public TradeEnrollment getTradeEnrollment() {
        return tradeEnrollment;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLineOne() {
        return lineOne;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCounty() {
        return county;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getMileage() {
        return mileage;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getStatusDeterminationDate() {
        return statusDeterminationDate;
    }

    public void setApproved(boolean approvedYes) {
        this.approved = approvedYes;
    }

    public void setStatusDeterminationDate(String statusDetDate) {
        this.statusDeterminationDate = statusDetDate;
    }

    public void setCompanyName(String compName) {
        this.companyName = compName;
    }
}
