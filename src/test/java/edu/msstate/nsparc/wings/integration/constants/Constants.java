package edu.msstate.nsparc.wings.integration.constants;

/**
 * Created by a.vnuchko on 19.06.2015.
 * Different useful constants.
 */
public class Constants {
    //Common
    public static final Integer RECORDS_ON_PAGE = 11;
    public static final Integer ZERO = 0;
    public static final Integer RANDOM_ONE = 1;
    public static final Integer RANDOM_TWO = 2;
    public static final Integer RANDOM_THREE = 3;
    public static final Integer DAYS_MONTH = 30;
    public static final Integer DAYS_YEAR = 365;
    public static final Integer DAYS_HALF_YEAR = 180;
    public static final Integer YEARS_ADULT = 21;
    public static final Integer DAYS_AGO_WAIVER = 20;
    public static final Integer HOURS_MILES = 40;

    public static final String WIOA = "wioa";
    public static final String WP = "wp";
    public static final String TRADE = "Trade";
    public static final String CLONE = "Clone";
    public static final String YES_ANSWER = "Yes";
    public static final String NO_ANSWER = "No";
    public static final String EMPTY = "";
    public static final String ANY = "Any";
    public static final String COMPLETED = "Completed";
    public static final String IN_PROGRESS = "In Progress";

    public static final String BASE_FORMAT = "MM/dd/yyyy";
    public static final String DB_FORMAT = "yyyy-MM-dd";
    public static final String SQL_FORMAT = "yyyyMMdd";

    //WIOA
    public static final String WIA_EDUCATIONAL_STATUS = "Not attending school; high school graduate/GED";
    public static final String WIA_TOOL_USED = "Other, Not Listed";
    public static final String EDIT = "Edit";

    public static final String EVERIFY = "Everify";
    public static final String REFERRALS = "Referrals";
    public static final String REFERRAL_RESULT = "ReferralResulting";
    public static final String PARTP_SERVICE_ENRL = "PrtpServiceEnrollment";
    public static final String IEP = "IEP";
    public static final String ASSESSMENT_WP = "AssessmentWP";
    public static final String WIA_ENROLLMENT = "WIAEnrollment";
    public static final String WIA_TRAINING = "WIATraining";
    public static final String YOUTH_GOALS = "YouthGoals";
    public static final String ASSESSMENT_WIA = "AssessmentWIA";
    public static final String TRADE_ENROLLMENTS = "TradeEnrollments";
    public static final String ATRT_ENRLMS = "ATRTEnrollments";
    public static final String TRADE_TRAINING = "TradeTraining";
    public static final String TRAINING_WAIVERS = "TrainingWaivers";
    public static final String JOB_SEARCH = "JobSearch";
    public static final String RELOCATION = "Relocation";
    public static final String ASSESSMENT_TRADE = "AssessmentTrade";
    public static final String PARTICIPANT = "participant";
    public static final String EMPLOYER = "employer";

    //Training Waiver
    public static final String REVOKED = "Revoked";

    //Trade Enrollment
    public static final String ELIGIBLE = "Eligible";
    public static final String INELIGIBLE = "Ineligible";

    //Participant S-S titles of the modules on the participantSSDetails page.
    public static final String PARTICIPANT_SS = "participant SS";
    public static final String EMPLOYER_SS = "employer SS";
    public static final String ELIGIBILITY = "Eligibility";
    public static final String IDENTIFICATION = "Identification";
    public static final String CLASSIFICATION = "Classification";
    public static final String ACCOMPLISHMENTS = "Accomplishments";
    public static final String CONTACT = "Contact Information";
    public static final String PREFERENCES = "Employment Preferences";
    public static final String MILITARY = "Military";
    public static final String ACADEMIC = "Academic";
    public static final String EMPLOYMENT = "Employment";
    public static final String JOB_ORDER_SS = "job order SS";

    //specific data for objects
    public static final Integer ACCOUNT_LENGTH = 15;
    public static final Integer ADDRESS_LINE_LENGTH = 20;
    public static final Integer CONTACT_LENGTH = 10;
    public static final Integer FEIN_LENGTH = 9;
    public static final Integer CODE_LENGTH = 3;
    public static final Integer ORIGINAL_DETAILS_LENGTH = 4;
    public static final Integer EMAIL_LENGTH = 5;
    public static final Integer EAN_LENGTH = 6;
    public static final Integer FAMILY_LENGTH = 2;
    public static final String AMOUNT_REGEX = "(\\d{1,}\\,)?\\d{1,}\\.\\d{1,}";
    public static final String COUNT_REGEX = "\\d{1,}?\\,?(\\d{1,})?";
    public static final String NUMBERS_REGEX = "\\d{1,}";
    public static final String PARTICIPANT_ID_REGEX = "\\d{6,}";

    //Test data
    public static final Integer DAYS_IN_PAST = 89;
    public static final Integer DAYS_OUTCOME_QUARTER = 99;
    public static final Integer SECOND_TEST_DATE = 2;
    public static final Integer SEMESTER_DATE = 40;
    public static final String DEFAULT_ADMIN = "Admin November";
    public static final String DEFAULT_REGION = "Golden Triangle Region";

    public static final Boolean TRUE = true;
    public static final Boolean FALSE = false;
}
