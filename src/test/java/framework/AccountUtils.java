package framework;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import netscape.ldap.*;

/**
 * This class is user for getting accounts in different modules
 */
public class AccountUtils extends BaseEntity {
    private static final String EXTERNAL = "external";
    private static final String INTERNAL = "internal";
    private static final String SECRET_WORD = "secret";
    private static final String LDAP_HOST = "uid=admin,ou=system";
    private static ThreadLocal<String> employerAccount = new ThreadLocal<>();
    private static ThreadLocal<String> participantAccount = new ThreadLocal<>();
    private static ThreadLocal<String> staffAccount = new ThreadLocal<>();
    private static final Integer LDAP_NUMBER = 3;
    private static final String pass = "password";


    /**
     * Inits data to create account
     */
    public static synchronized void init() {
        employerAccount.set(CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH));
        createEmployerLdapAccount(employerAccount.get());
        participantAccount.set(CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH));
        createParticipantLdapAccount(participantAccount.get());
        staffAccount.set(CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH));
        createStaffLdapAccount(staffAccount.get());
    }

    public static synchronized void initEmployer() {
        employerAccount.set(CommonFunctions.getRandomLiteralCode(Constants.ACCOUNT_LENGTH));
        createEmployerLdapAccount(employerAccount.get());
    }

    /**
     * Set account data to null
     */
    public static void destroy() {
        deleteLDAPAccount(employerAccount.get(), EXTERNAL);
        employerAccount.set(null);
        deleteLDAPAccount(participantAccount.get(), EXTERNAL);
        participantAccount.set(null);
        deleteLDAPAccount(staffAccount.get(), INTERNAL);
        staffAccount.set(null);
    }

    /**
     * Gets youth provider account
     *
     * @return youth provider account
     */
    public static String getYouthProviderAccount() {
        return "yprovider1";
    }

    /**
     * Gets pc admin account
     *
     * @return pc admin account
     */
    public static String getProjectCodeAdminAccount() {
        return "pcadmin";
    }

    /**
     * Gets admin account
     *
     * @return admin account
     */
    public static String getAdminAccount() {
        return "adminNovember";
    }

    /**
     * Gets admin1 account (for approving documents upload)
     *
     * @return admin1 account
     */
    public static String getAdminAccount1() {
        return "admin1";
    }

    /**
     * Gets area director account
     *
     * @return area director acc
     */
    public static String getAreaDirectorAccount() {
        return "areaDirector";
    }

    /**
     * Gets staff account
     *
     * @return staff account
     */
    public static String getStaffAccount() {
        return "autostaff";
    }

    /**
     * Gets staff second account
     *
     * @return additional staff account
     */
    public static String getStaff02Account() {
        return "autostaff02";
    }


    /**
     * Gets rrdirector account
     *
     * @return rrdirector account
     */
    public static String getRapidResponseDirectorAccount() {
        return "rrdirector";
    }

    /**
     * Gets trade director account
     *
     * @return trade director account
     */
    public static String getTradeDirectorAccount() {
        return "tradedirector";
    }

    /**
     * Gets executive account
     *
     * @return executive account
     */
    public static String getExecutiveAccount() {
        return "executiveFeb";
    }

    /**
     * Gets manager account
     *
     * @return manager account
     */
    public static String getManagerAccount() {
        return "qamanager";
    }

    /**
     * Gets DVOP account
     *
     * @return DVOP account
     */
    public static String getDVOPAccount() {
        return "dvopFeb";
    }

    /**
     * Gets LVER account
     *
     * @return LVER account
     */
    public static String getLVERAccount() {
        return "lverFeb";
    }

    /**
     * Gets Intake Staff account
     *
     * @return Intake Staff account
     */
    public static String getIntakeStaffAccount() {
        return "intakeStaff";
    }

    /**
     * Gets EVerify Admin account
     *
     * @return EVerify Admin account
     */
    public static String getEVerifyAdminAccount() {
        return "eVerifyAdmin";
    }

    /**
     * Gets WIOA Admin account
     *
     * @return WIOA Admin account
     */
    public static String getWIOAAdminAccount() {
        return "WIOAAdmin";
    }

    public static String getWIOAAdminPLUSAccount() {
        return "WIOAAdminPLUS";
    }

    /**
     * Gets LWDA Staff account
     *
     * @return LWDA Staff account
     */
    public static String getLWDAStaffAccount() {
        return "AKlwdastaff";
    }

    /**
     * Gets WIOA Provider account
     *
     * @return WIOA Provider account
     */
    public static String getWIOAProvAccount() {
        return "AKwioapro";
    }

    /**
     * Gets employer account
     *
     * @return employer account
     */
    public static String getEmployerAccount() {
        return employerAccount.get();
    }

    /**
     * Gets participant account
     *
     * @return participant account
     */
    public static String getParticipantAccount() {
        return participantAccount.get();
    }

    /**
     * Gets random staff account
     *
     * @return staff account
     */
    public static String getRandomStaffAccount() {
        return staffAccount.get();
    }

    /**
     * Gets default password
     *
     * @return default password
     */
    public static String getDefaultPassword() {
        return pass;
    }

    /**
     * Create participant LDAP account
     *
     * @param username - participant name
     */
    private static void createParticipantLdapAccount(String username) {
        createLDAPAccount(username, pass, "claimant", EXTERNAL);
    }

    /**
     * Create employer LDAP account
     *
     * @param username employer name
     */
    private static void createEmployerLdapAccount(String username) {
        createLDAPAccount(username, pass, "dummyemployer", EXTERNAL);
    }

    /**
     * Create LDAP account for role 'STAFF'
     *
     * @param username - staff username
     */
    private static void createStaffLdapAccount(String username) {
        createLDAPAccount(username, pass, "claimstaker", INTERNAL);
    }

    /**
     * Create LDAP account for role 'STAFF'
     *
     * @param username         - staff username
     * @param passwordString   - password
     * @param role             - role
     * @param externalInternal - external or internal user
     */
    private static void createLDAPAccount(String username, String passwordString, String role, String externalInternal) {
        LDAPConnection ld = new LDAPConnection();
        try {
            ld.connect(LDAP_NUMBER, BaseEntity.getLdapHost(), BaseEntity.getLdapPort(), LDAP_HOST, SECRET_WORD);
            LDAPAttributeSet attributes = new LDAPAttributeSet();
            LDAPAttribute objectClass = new LDAPAttribute("objectClass", "accessuser");
            attributes.add(objectClass);
            LDAPAttribute accessRoleList = new LDAPAttribute("accessrolelist", role);
            attributes.add(accessRoleList);
            LDAPAttribute accessuserid = new LDAPAttribute("accessuserid", username);
            attributes.add(accessuserid);
            LDAPAttribute password = new LDAPAttribute("userPassword", passwordString);
            attributes.add(password);
            LDAPAttribute accessEmailAddressStatus = new LDAPAttribute("accessemailaddressstatus", "VERF");
            attributes.add(accessEmailAddressStatus);
            LDAPAttribute accessEmailId = new LDAPAttribute("accessemailid", "wingsauto@wings.com");
            attributes.add(accessEmailId);

            LDAPEntry myEntry = new LDAPEntry("accessuserid=" + username + ",ou=" + externalInternal + ",ou=users,dc=access,dc=mdes,dc=ms,dc=gov", attributes);
            ld.add(myEntry);
        } catch (LDAPException lde) {
            BaseEntity.getLogger().info(lde);
        } finally {
            if (ld.isConnected()) {
                try {
                    ld.disconnect();
                } catch (LDAPException e) {
                    BaseEntity.getLogger().info(e);
                }
            }
        }
    }

    /**
     * Delete ldap account for role 'Staff'
     *
     * @param userName         - user account
     * @param externalInternal - internal or external user.
     */
    private static void deleteLDAPAccount(String userName, String externalInternal) {
        LDAPConnection ld = new LDAPConnection();
        try {
            ld.connect(LDAP_NUMBER, BaseEntity.getLdapHost(), BaseEntity.getLdapPort(), LDAP_HOST, SECRET_WORD);
            ld.delete("accessuserid=" + userName + ",ou=" + externalInternal + ",ou=users,dc=access,dc=mdes,dc=ms,dc=gov");
        } catch (LDAPException lde) {
            BaseEntity.getLogger().info(lde);
        } finally {
            if (ld.isConnected()) {
                try {
                    ld.disconnect();
                } catch (LDAPException e) {
                    BaseEntity.getLogger().info(e);
                }
            }
        }
    }
}
