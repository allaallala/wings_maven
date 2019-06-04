package edu.msstate.nsparc.wings.integration.forms.menu;

/**
 * Describes menu headers
 */
public class WingsTopMenu {

    private static final String WAGNER_PEYSER = "Wagner-Peyser";
    private static final String TRADE = "Trade";
    private static final String BIG_ROCKS = "Big Rocks";
    private static final String DATA_INTEGRITY = "Data Integrity";
    private static final String UTILITIES = "Utilities";
    private static final String ADMINISTRATIVE = "Administrative";


    public enum WingsStaffMenuItem {
        P_PARTICIPANT_RECORDS (WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"Participant Profiles"}),
        P_WP_CALL_INS(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{WAGNER_PEYSER, "Call-Ins"}),
        P_WP_JOB_MATCHING(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{WAGNER_PEYSER, "Job Matching"}),
        P_WP_REFERRALS(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{WAGNER_PEYSER, "Referrals"}),
        P_WP_REFERRAL_RESULTING(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{WAGNER_PEYSER, "Referral Resulting"}),
        P_WP_E_VERIFY(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{WAGNER_PEYSER, "E-Verify"}),
        P_PARTICIPANT_SERVICE_ENROLLMENT(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"Participant Service Enrollment"}),
        P_WIA_ENROLLMENT(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"WIOA", "WIOA Enrollments"}),
        P_ASSESSMENTS(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"Assessments"}),
        P_WIA_YOUTH_GOALS(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"WIOA", "Youth Goals"}),
        P_WIA_PROGRAM_OUTCOMES(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"Program Outcomes", "Edit for Participant"}),
        P_WIA_TRAINING (WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"WIOA", "WIOA Training"}),
        P_INDIVIDUAL_EMPLOYMENT_PLANS(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[] {"Individual Employment Plans"}),
        P_TRADE_TRADE_ENROLLMENTS(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{TRADE, "Trade Enrollments"}),
        P_TRADE_ATAA_RTAA_ENROLLMENTS(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{TRADE, "ATAA/RTAA Enrollments"}),
        P_TRADE_TRADE_TRAINING(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{TRADE, "Trade Training"}),
        P_TRADE_TRAINING_WAIVERS(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{TRADE, "Training Waivers"}),
        P_TRADE_JOB_SEARCH(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{TRADE, "Job Search"}),
        P_TRADE_RELOCATION(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{TRADE, "Relocation"}),
        P_CAREER_READINESS_CERTIFICATIONS(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"Career Readiness Certifications"}),
        E_EMPLOYER_RECORDS(WingsMenuItem.TopMenu.MENU_EMPLOYERS, new String[]{"Employer Profiles"}),
        E_WP_JOB_ORDERS(WingsMenuItem.TopMenu.MENU_EMPLOYERS, new String[]{WAGNER_PEYSER, "Job Orders"}),
        E_WP_CONTACTS(WingsMenuItem.TopMenu.MENU_EMPLOYERS, new String[]{WAGNER_PEYSER, "Contacts"}),
        E_TRADE_PETITIONS(WingsMenuItem.TopMenu.MENU_EMPLOYERS, new String[]{TRADE, "Petitions"}),
        E_RAPID_RESPONSE_EVENTS(WingsMenuItem.TopMenu.MENU_EMPLOYERS, new String[]{"Rapid Response Events"}),
        E_EMPLOYER_SERVICE_ENROLLMENT(WingsMenuItem.TopMenu.MENU_EMPLOYERS, new String[]{"Employer Service Enrollment"}),
        R_BR_PARTICIPANT_REPORT(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{BIG_ROCKS, "Participant Report"}),
        R_BR_WIA_REPORT(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{BIG_ROCKS, "WIOA Report"}),
        R_BR_EMPLOYER_REPORT(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{BIG_ROCKS, "Employer Report"}),
        R_BR_JOB_ORDER_REPORT(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{BIG_ROCKS, "Job Order Report"}),
        R_BR_SERVICES_REPORT(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{BIG_ROCKS, "Services Report"}),
        R_BR_REFERRAL_REPORT(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{BIG_ROCKS, "Referral Report"}),
        R_DI_PARTICIPANT_REPORT(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "Participant Report"}),
        R_DI_EMPLOYER_REPORT(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "Employer Report"}),
        R_DI_REFERRAL_REPORT(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "Referral Report"}),
        R_DI_JOB_ORDER_REPORT(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "Job Order Report"}),
        R_DI_WIA_ENROLLMENT_REPORT(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "WIOA Enrollment Report"}),
        R_DI_TRADE_ENROLLMENT_REPORT(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "Trade Enrollment Report"}),
        R_DI_TRAINING_WAIVER_REPORT(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "Training Waiver Report"}),
        R_DI_ATAARTAA_ENROLLMENT_REPORT(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "ATAA/RTAA Enrollment Report"}),
        R_DI_LDAP_SEARCH(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "LDAP Search"}),
        A_SERVICES(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{ADMINISTRATIVE, "Services"}),
        A_WORKFORCE_AREAS(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{ADMINISTRATIVE, "Workforce Areas"}),
        A_CENTERS(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{ADMINISTRATIVE, "Service Centers"}),
        A_ACTIVITY_MANAGER_ACCOUNTS(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{ADMINISTRATIVE, "Activity Manager Accounts"}),
        A_PARTICIPANT_PROFILE_MERGE(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{UTILITIES,"Participant Profile Merge"}),
        A_TRAINING_PROVIDERS(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{"Training", "Training Providers"}),
        A_COURSES(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{"Training", "Courses"}),
        A_PROJECT_CODES(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{"Training", "Project Codes"});

        WingsMenuItem.TopMenu topMenu;
        String[] subItem;

        WingsStaffMenuItem(WingsMenuItem.TopMenu topMenu, String[] subItem) {
            this.subItem = subItem;
            this.topMenu = topMenu;
        }

        public WingsMenuItem getMenuItem() {
            return new WingsMenuItem(topMenu, subItem);
        }
    }

    /*//Participants menu
    public WingsMenuItem P_PARTICIPANT_RECORDS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"Participant Profiles"});
    public WingsMenuItem P_WP_CALL_INS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{WAGNER_PEYSER, "Call-Ins"});
    public WingsMenuItem P_WP_JOB_MATCHING = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{WAGNER_PEYSER, "Job Matching"});
    public WingsMenuItem P_WP_REFERRALS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{WAGNER_PEYSER, "Referrals"});
    public WingsMenuItem P_WP_REFERRAL_RESULTING = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{WAGNER_PEYSER, "Referral Resulting"});
    public WingsMenuItem P_WP_E_VERIFY = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{WAGNER_PEYSER, "E-Verify"});
    public WingsMenuItem P_PARTICIPANT_SERVICE_ENROLLMENT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"Participant Service Enrollment"});
    public WingsMenuItem P_WIA_ENROLLMENT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"WIOA", "WIOA Enrollments"});
    public WingsMenuItem P_ASSESSMENTS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"Assessments"});
    public WingsMenuItem P_WIA_YOUTH_GOALS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"WIOA", "Youth Goals"});
    public WingsMenuItem P_WIA_PROGRAM_OUTCOMES = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"Program Outcomes", "Edit for Participant"});
    public WingsMenuItem P_WIA_TRAINING = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"WIOA", "WIOA Training"});
    public WingsMenuItem P_INDIVIDUAL_EMPLOYMENT_PLANS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[] {"Individual Employment Plans"});
    public WingsMenuItem P_TRADE_TRADE_ENROLLMENTS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{TRADE, "Trade Enrollments"});
    public WingsMenuItem P_TRADE_ATAA_RTAA_ENROLLMENTS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{TRADE, "ATAA/RTAA Enrollments"});
    public WingsMenuItem P_TRADE_TRADE_TRAINING = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{TRADE, "Trade Training"});
    public WingsMenuItem P_TRADE_TRAINING_WAIVERS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{TRADE, "Training Waivers"});
    public WingsMenuItem P_TRADE_JOB_SEARCH = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{TRADE, "Job Search"});
    public WingsMenuItem P_TRADE_RELOCATION = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{TRADE, "Relocation"});
    public WingsMenuItem P_CAREER_READINESS_CERTIFICATIONS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_PARTICIPANTS, new String[]{"Career Readiness Certifications"});
    // Employers menu
    public WingsMenuItem E_EMPLOYER_RECORDS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_EMPLOYERS, new String[]{"Employer Profiles"});
    public WingsMenuItem E_WP_JOB_ORDERS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_EMPLOYERS, new String[]{WAGNER_PEYSER, "Job Orders"});
    public WingsMenuItem E_WP_CONTACTS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_EMPLOYERS, new String[]{WAGNER_PEYSER, "Contacts"});
    public WingsMenuItem E_TRADE_PETITIONS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_EMPLOYERS, new String[]{TRADE, "Petitions"});
    public WingsMenuItem E_RAPID_RESPONSE_EVENTS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_EMPLOYERS, new String[]{"Rapid Response Events"});
    public WingsMenuItem E_EMPLOYER_SERVICE_ENROLLMENT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_EMPLOYERS, new String[]{"Employer Service Enrollment"});
    // Reports menu: Big Rocks
    public WingsMenuItem R_BR_PARTICIPANT_REPORT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{BIG_ROCKS, "Participant Report"});
    public WingsMenuItem R_BR_WIA_REPORT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{BIG_ROCKS, "WIOA Report"});
    public WingsMenuItem R_BR_EMPLOYER_REPORT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{BIG_ROCKS, "Employer Report"});
    public WingsMenuItem R_BR_JOB_ORDER_REPORT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{BIG_ROCKS, "Job Order Report"});
    public WingsMenuItem R_BR_SERVICES_REPORT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{BIG_ROCKS, "Services Report"});
    public WingsMenuItem R_BR_REFERRAL_REPORT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{BIG_ROCKS, "Referral Report"});
    // Reports menu: Data Integrity
    public WingsMenuItem R_DI_PARTICIPANT_REPORT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "Participant Report"});
    public WingsMenuItem R_DI_EMPLOYER_REPORT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "Employer Report"});
    public WingsMenuItem R_DI_REFERRAL_REPORT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "Referral Report"});
    public WingsMenuItem R_DI_JOB_ORDER_REPORT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "Job Order Report"});
    public WingsMenuItem R_DI_WIA_ENROLLMENT_REPORT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "WIOA Enrollment Report"});
    public WingsMenuItem R_DI_TRADE_ENROLLMENT_REPORT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "Trade Enrollment Report"});
    public WingsMenuItem R_DI_TRAINING_WAIVER_REPORT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "Training Waiver Report"});
    public WingsMenuItem R_DI_ATAARTAA_ENROLLMENT_REPORT = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "ATAA/RTAA Enrollment Report"});
    public WingsMenuItem R_DI_LDAP_SEARCH = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_REPORTS, new String[]{DATA_INTEGRITY, "LDAP Search"});

    // Advanced menu
    public WingsMenuItem A_SERVICES = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{ADMINISTRATIVE, "Services"});
    public WingsMenuItem A_WORKFORCE_AREAS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{ADMINISTRATIVE, "Workforce Areas"});
    public WingsMenuItem A_CENTERS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{ADMINISTRATIVE, "Service Centers"});
    public WingsMenuItem A_ACTIVITY_MANAGER_ACCOUNTS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{ADMINISTRATIVE, "Activity Manager Accounts"});
    public WingsMenuItem A_PARTICIPANT_PROFILE_MERGE = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{UTILITIES,"Participant Profile Merge"});
    public WingsMenuItem A_TRAINING_PROVIDERS = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{"Training", "Training Providers"});
    public WingsMenuItem A_COURSES = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{"Training", "Courses"});
    public WingsMenuItem A_PROJECT_CODES = new WingsMenuItem(WingsMenuItem.TopMenu.MENU_ADVANCED, new String[]{"Training", "Project Codes"});*/
}
