package edu.msstate.nsparc.wings.integration.enums.SqlQueries;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum SelectQuery implements ISqlQuery{
    SELECT_ADDRESSES("selectAddresses.sql"),
    SELECT_ASSESSMENT_COUNT("selectAssessmentCount.sql"),
    SELECT_ATAA_RTAA_ENROLLMENT_COUNT("selectAtaaRtaaEnrollmentCount.sql"),
    SELECT_CAREER_READINESS_CERTIFICATION_COUNT("selectCareerReadinessCertificationCount.sql"),
    SELECT_CENTER_NAME("selectCenterName.sql"),
    SELECT_CODE_LOCATION("selectCodeLocation.sql"),
    SELECT_CONTACT_INFO("selectContactInfo.sql"),
    SELECT_COURSE_COUNT("selectCourseCount.sql"),
    SELECT_EMPLOYER_ENROLLMENT_RESULT("selectEmployerEnrollmentResult.sql"),
    SELECT_EMPLOYER_ID("selectEmployerId.sql"),
    SELECT_EMPTY_CONTACT_INFO("selectEmptyContactInfo.sql"),
    SELECT_INDIVIDUAL_EMPLOYMENT_PLAN_COUNT("selectIndividualEmploymentPlanCount.sql"),
    SELECT_JOB_ORDER_ID("selectJobOrderId.sql"),
    SELECT_LAST_DRIVER_LICENSE("selectLastDriverLicense.sql"),
    SELECT_LIST_OF_APPLICATIONS_SORTED_BY_DATE("selectListOfApplicationsSortedByDate.sql"),
    SELECT_LIST_OF_EMPLOYERS("selectListOfEmployers.sql"),
    SELECT_LIST_OF_JOB_TITLES("selectListOfJobTitles.sql"),
    SELECT_NUMBER_OF_JOB_CENTERS("selectNumberOfJobCenters.sql"),
    SELECT_NUMBER_OF_NOTES("selectNumberOfNotes.sql"),
    SELECT_OSOC_CODE_PARTICIPANT("selectOsocCodeParticipant.sql"),
    SELECT_PARTICIPANT_ID_FNAME("selectParticipantIdFname.sql"),
    SELECT_PDO_PROVIDER_CREATED_DATE("selectPdoProviderCreatedDate.sql"),
    SELECT_PREVIOUS_JOB_ID("selectPreviousJobId.sql"),
    SELECT_RAPID_RESPONSE_EVENT_COUNT("selectRapidResponseEventCount.sql"),
    SELECT_SERVICE_ENROLLMENT_JOB_ORDER("selectServiceEnrollmentJobOrder.sql"),
    SELECT_STAFF_NAME("selectStaffName.sql"),
    SELECT_TRADE_QUERY_COURSE("selectTradeQueryCourse.sql"),
    SELECT_TRADE_QUERY_PROVIDER("selectTradeQueryProvider.sql"),
    SELECT_TRADE_TRAINING_COUNT("selectTradeTrainingCount.sql"),
    SELECT_TRAINING_COURSES_COUNT("selectTrainingCoursesCount.sql"),
    SELECT_TRAINING_PROVIDER_COUNT("selectTrainingProviderCount.sql"),
    SELECT_USER_ID("selectUserId.sql"),
    SELECT_WAGE_PAYMENTS_CONNECTED_ENROLLMENT("selectWagePaymentsConnectedEnrollment.sql"),
    SELECT_WAGE_SUBSIDIES_CONNECTED_ENROLLMENT("selectWageSubsidiesConnectedEnrollment.sql"),
    SELECT_WIA_QUERY_COURSE("selectWiaQueryCourse.sql"),
    SELECT_WIA_QUERY_PROVIDER("selectWiaQueryProvider.sql");

    private Path path;

    SelectQuery(String fileName) {
        this.path = Paths.get("select", fileName);
    }

    public String getQuery() {
        return getQuery(path);
    }
}
