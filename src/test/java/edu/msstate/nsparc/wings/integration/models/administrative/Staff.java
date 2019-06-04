package edu.msstate.nsparc.wings.integration.models.administrative;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Roles;
import framework.AccountUtils;
import framework.BaseEntity;
import framework.CommonFunctions;
import framework.PropertiesResourceManager;

/**
This class represents Staff person
 */
public class Staff {
    private String staffAccount;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String phoneNumber;
    private String email;
    private String userType;

    //User permissions
    private Boolean staffCreate;
    private Boolean staffCreateStaffUserType;
    private Boolean staffView;
    private Boolean staffAccessUsernameVisibility;
    private Boolean staffViewResetUsername;
    private Boolean staffViewEditButton;
    private Boolean staffViewCreateAnotherStaff;
    private Boolean staffViewAuditButton;
    private Boolean staffEdit;
    private Boolean staffEditUserType;
    private Boolean staffSearchDisabledAccount;
    private static final String STAFF_CREATE = "staffCreate";
    private static final String STAFF_CREATE_STAFF_USERTYPE = "staffCreateStaffUserType";
    private static final String STAFF_VIEW = "staffView";
    private static final String STAFF_ACCESS_USERNAME = "staffAccessUsernameVisibility";
    private static final String STAFF_VIEW_RESET = "staffViewResetUsername";
    private static final String STAFF_VIEW_EDIT = "staffViewEditButton";
    private static final String STAFF_VIEW_CREATE_ANOTHER = "staffViewCreateAnotherStaff";
    private static final String STAFF_VIEW_AUDIT = "staffViewAuditButton";
    private static final String STAFF_EDIT = "staffEdit";
    private static final String STAFF_EDIT_USERTYPE = "staffEditUserType";
    private static final String STAFF_SEARCH_DISABLED_ACCOUNT = "staffSearchDisabledAccount";

    String userTypeStaff = "MDES Staff";
    String userYouthProvider = "Youth Provider";

    public Staff() {
        staffAccount = "";
    }

    /**
     * Constructor with user role
     * @param role - user role
     */
    public Staff(Roles role) {
        PropertiesResourceManager prop = new PropertiesResourceManager(BaseEntity.getRolePath(role.toString()));
        staffCreate = Boolean.valueOf(prop.getProperty(STAFF_CREATE));
        staffCreateStaffUserType = Boolean.valueOf(prop.getProperty(STAFF_CREATE_STAFF_USERTYPE));
        staffView = Boolean.valueOf(prop.getProperty(STAFF_VIEW));
        staffAccessUsernameVisibility = Boolean.valueOf(prop.getProperty(STAFF_ACCESS_USERNAME));
        staffViewResetUsername = Boolean.valueOf(prop.getProperty(STAFF_VIEW_RESET));
        staffViewEditButton = Boolean.valueOf(prop.getProperty(STAFF_VIEW_EDIT));
        staffViewCreateAnotherStaff = Boolean.valueOf(prop.getProperty(STAFF_VIEW_CREATE_ANOTHER));
        staffViewAuditButton = Boolean.valueOf(prop.getProperty(STAFF_VIEW_AUDIT));
        staffEdit = Boolean.valueOf(prop.getProperty(STAFF_EDIT));
        staffEditUserType = Boolean.valueOf(prop.getProperty(STAFF_EDIT_USERTYPE));
        staffSearchDisabledAccount = Boolean.valueOf(prop.getProperty(STAFF_SEARCH_DISABLED_ACCOUNT));
        staffAccount = AccountUtils.getRandomStaffAccount();
        generateRandomStaffData();
        userType = userTypeStaff;
    }

    /**
     * Staff constructor with specified account
     * @param account - staff account
     */
    public Staff(String account) {
        staffAccount = account;
        generateRandomStaffData();
        userType = userTypeStaff;
    }

    /**
     * Set staff account and user type
     * @param account account
     * @param userTp user type
     */
    public Staff(String account, String userTp) {
        this(account);
        this.userType = userTp;
    }

    /**
     * Makes youth provider (or not) for chosen user type
     * @param youthProvider - true/false
     */
    public Staff(boolean youthProvider) {
        staffAccount = AccountUtils.getRandomStaffAccount();
        generateRandomStaffData();
        if (youthProvider) {
            userType = userYouthProvider;
        }
    }

    /**
     * Generates random staff data
     */
    private void generateRandomStaffData() {
        firstName = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        lastName = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        jobTitle = CommonFunctions.getRandomLiteralCode(Constants.CONTACT_LENGTH);
        phoneNumber = CommonFunctions.getRandomIntegerNumber(Constants.CONTACT_LENGTH);
        email = CommonFunctions.getRandomLiteralCode(Constants.EMAIL_LENGTH) + "@" + CommonFunctions.getRandomLiteralCode(Constants.CODE_LENGTH)
                + "." + CommonFunctions.getRandomLiteralCode(Constants.CODE_LENGTH);
    }

    //User permissions block

    public Boolean getStaffCreate() {
        return staffCreate;
    }

    public Boolean getStaffCreateStaffUserType() {
        return staffCreateStaffUserType;
    }

    public Boolean getStaffViewCreateAnotherStaff() {
        return staffViewCreateAnotherStaff;
    }

    public Boolean getStaffView() {
        return staffView;
    }

    public Boolean getStaffAccessUsernameVisibility() {
        return staffAccessUsernameVisibility;
    }

    public Boolean getStaffViewResetUsername() {
        return staffViewResetUsername;
    }

    public Boolean getStaffViewEditButton() {
        return staffViewEditButton;
    }

    public Boolean getStaffViewAuditButton() {
        return staffViewAuditButton;
    }

    public Boolean getStaffEdit() {
        return staffEdit;
    }

    public Boolean getStaffEditUserType() {
        return staffEditUserType;
    }

    public Boolean getStaffSearchDisabledAccount() {
        return staffSearchDisabledAccount;
    }

    public void setLastName(String lastNm) {
        this.lastName = lastNm;
    }

    public void setFirstName(String firstNm) {
        this.firstName = firstNm;
    }

    public String getStaffAccount() {
        return staffAccount;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        return userType;
    }
}
