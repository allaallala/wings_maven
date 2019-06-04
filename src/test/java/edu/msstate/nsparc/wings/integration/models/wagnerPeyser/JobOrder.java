package edu.msstate.nsparc.wings.integration.models.wagnerPeyser;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import framework.AccountUtils;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

/**
This class represents Job Order entity
 */
public class JobOrder {
    //variables connected with user permissions
    private Boolean joCreate;
    private Boolean joCreateStaffAccount;
    private Boolean joVerifyRelease;
    private Boolean joViewEditBasicInformation;
    private Boolean joViewEditJobRequirements;
    private Boolean joViewEditStaffInformation;
    private Boolean joViewClone;
    private Boolean joViewAuditButton;
    private Boolean joClone;
    private Boolean joCloneStaffLookup;
    private Boolean joEdit;
    private Boolean jobOrderEditShowDisclaimer;
    private Boolean jobOrderEditStaffLookup;
    private static final String JO_CREATE = "joCreate";
    private static final String JO_CREATE_STAFF = "joCreateStaffAccount";
    private static final String JO_VERIFY_RELEASE = "joVerifyRelease";
    private static final String JO_VIEW_EDIT_BASIC_INFORMATION = "joViewEditBasicInformation";
    private static final String JO_VIEW_EDIT_JOB_REQUIREMENTS = "joViewEditJobRequirements";
    private static final String JO_VIEW_EDIT_STAFF_INFORMATION = "joViewEditStaffInformation";
    private static final String JO_VIEW_CLONE = "joViewClone";
    private static final String JO_VIEW_AUDIT = "joViewAuditButton";
    private static final String JO_CLONE = "joClone";
    private static final String JO_CLONE_STAFF = "joCloneStaffLookup";
    private static final String JO_EDIT = "joEdit";
    private static final String JO_EDIT_SHOW_DISCLAIMER = "jobOrderEditShowDisclaimer";
    private static final String JO_EDIT_STAFF = "jobOrderEditStaffLookup";

    //Other fields
    private Employer employer;
    private String jobTitle;
    private String creationDate;
    private String closeDate;
    private String status;
    private String jobID;
    private String city;
    private String zipCode;
    private String osocCode;
    private String restrictedFirstName;
    private String restrictedLastName;
    private boolean isRequiredAcademic;
    private String requiredAcademic;
    private Question question;
    private boolean isRequiredToApplyOnline;
    private String searchState;
    private String description;
    private String quantity;
    private String openDate;

    /**
     * Specifies user permissions
     * @param role - user role
     */
    public JobOrder(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        joCreate = Boolean.valueOf(prop.getProperty(JO_CREATE));
        joCreateStaffAccount = Boolean.valueOf(prop.getProperty(JO_CREATE_STAFF));
        joVerifyRelease = Boolean.valueOf(prop.getProperty(JO_VERIFY_RELEASE));
        joViewEditBasicInformation = Boolean.valueOf(prop.getProperty(JO_VIEW_EDIT_BASIC_INFORMATION));
        joViewEditJobRequirements = Boolean.valueOf(prop.getProperty(JO_VIEW_EDIT_JOB_REQUIREMENTS));
        joViewEditStaffInformation = Boolean.valueOf(prop.getProperty(JO_VIEW_EDIT_STAFF_INFORMATION));
        joViewClone = Boolean.valueOf(prop.getProperty(JO_VIEW_CLONE));
        joViewAuditButton = Boolean.valueOf(prop.getProperty(JO_VIEW_AUDIT));
        joClone = Boolean.valueOf(prop.getProperty(JO_CLONE));
        joCloneStaffLookup = Boolean.valueOf(prop.getProperty(JO_CLONE_STAFF));
        joEdit = Boolean.valueOf(prop.getProperty(JO_EDIT));
        jobOrderEditShowDisclaimer = Boolean.valueOf(prop.getProperty(JO_EDIT_SHOW_DISCLAIMER));
        jobOrderEditStaffLookup = Boolean.valueOf(prop.getProperty(JO_EDIT_STAFF));
        jobTitle = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
        employer = new Employer(AccountUtils.getEmployerAccount());
        question = new Question();
        creationDate = CommonFunctions.getCurrentDate();
        closeDate = CommonFunctions.getNextWeekDate();
        osocCode = "Cooks, Restaurant";
        restrictedFirstName = "executive";
        restrictedLastName = "Feb";
        openDate = CommonFunctions.getCurrentDate();
    }

    /**
     * Generate
     * @param account - employer account
     */
    public JobOrder(String account) {
        jobTitle = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
        employer = new Employer(account);
        question = new Question();
        searchState = "Any";
        osocCode = "Cooks, Restaurant";
        description = "Automation";
        quantity = "10";
        openDate = CommonFunctions.getCurrentDate();
    }

    /**
     * Default job order
     */
    public JobOrder() {
        jobTitle = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
        employer = new Employer();
        creationDate = CommonFunctions.getCurrentDate();
        closeDate = CommonFunctions.getNextWeekDate();
        status = "Pending";
        question = new Question();
        searchState = "Any";
        description = "Automation";
        quantity = "10";
        osocCode = "Cooks, Restaurant";
        openDate = CommonFunctions.getCurrentDate();
    }

    public void setJobID(String jobId) {
        this.jobID = jobId;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setJobRestrictedFName(String newRestrictedFname) {
        this.restrictedFirstName = newRestrictedFname;
    }

    public void setJobRestrictedLName(String newRestrictedLname) {
        this.restrictedLastName = newRestrictedLname;
    }

    //User permissions

    public Boolean getCreate() {
        return joCreate;
    }

    public Boolean getCloneStaffLookup() {
        return joCloneStaffLookup;
    }

    public Boolean getJoVerifyRelease() {
        return joVerifyRelease;
    }

    public Boolean getViewEditBasicInformation() {
        return joViewEditBasicInformation;
    }

    public Boolean getViewEditJobRequirements() {
        return joViewEditJobRequirements;
    }

    public Boolean getViewEditStaffInformation() {
        return joViewEditStaffInformation;
    }

    public Boolean getViewClone() {
        return joViewClone;
    }

    public Boolean getViewAuditButton() {
        return joViewAuditButton;
    }

    public Boolean getClone() {
        return joClone;
    }

    public Boolean getEdit() {
        return joEdit;
    }

    public Boolean getEditStaffLookup() {
        return jobOrderEditStaffLookup;
    }


    /**
     * Question entity
     */
    public static class Question {
        private String text;
        private String correctAnswer;

        /**
         * Default Question
         */
        public Question() {
            text = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
            correctAnswer = CommonFunctions.getRandomLiteralCode(Constants.ADDRESS_LINE_LENGTH);
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public String getText() {
            return text;
        }

    }

    public String getOrderSearchState() {
        return searchState;
    }

    public boolean getRequiredApplyOnline() {
        return isRequiredToApplyOnline;
    }

    public void setRequiredApplyOnline(boolean type) {
        this.isRequiredToApplyOnline = type;
    }

    public void setRequiredAcademic(String newRequiredAcademic) {
        this.requiredAcademic = newRequiredAcademic;
    }

    public void setEmployer(Employer newEmployer) {
        this.employer = newEmployer;
    }

    public void setJobTitle(String newJobTitle) {
        this.jobTitle = newJobTitle;
    }

    public void setCreationDate(String newCreationDate) {
        this.creationDate = newCreationDate;
    }

    public void setCloseDate(String newCloseDate) {
        this.closeDate = newCloseDate;
    }

    public void setZipCode(String newZipCode) {
        this.zipCode = newZipCode;
    }

    public void setCity(String newCity) {
        this.city = newCity;
    }

    public void setIfAcademicRequired(Boolean newAcademic) {
        this.isRequiredAcademic = newAcademic;
    }

    public Question getQuestion() {
        return question;
    }

    public Employer getEmployer() {
        return employer;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public String getStatus() {
        return status;
    }

    public String getJobID() {
        return jobID;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getRequiredAcademic() {
        return requiredAcademic;
    }

    public Boolean getIfAcademicRequired() {
        return isRequiredAcademic;
    }

    public String getOsocCode() {
        return osocCode;
    }

    public String getDescription() {
        return description;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getRestrictedFirstName() {
        return restrictedFirstName;
    }

    public String getRestrictedLastName() {
        return restrictedLastName;
    }
}
