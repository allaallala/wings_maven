package edu.msstate.nsparc.wings.integration.functions.dbFunctions;

import edu.msstate.nsparc.wings.integration.enums.Database;
import edu.msstate.nsparc.wings.integration.enums.SqlQueries.InsertQuery;
import edu.msstate.nsparc.wings.integration.enums.SqlQueries.SelectQuery;
import edu.msstate.nsparc.wings.integration.enums.SqlQueries.UpdateQuery;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import edu.msstate.nsparc.wings.integration.models.trade.TradeEnrollment;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.CommonFunctions;
import org.sql2o.Connection;
import org.sql2o.Query;

public class ParticipantSqlFunctions {

    public static void insertParticipantInformationInReaui(Participant participant) {

        String claimantQuery = String.format(InsertQuery.INSERT_CLAIMANT.getQuery(),
                participant.getParticipantAccount(), participant.getSsn(), participant.getFirstName(),
                participant.getLastName(),
                CommonFunctions.changeDateFormat(participant.getParticipantBioInfo().getDateOfBirth(),
                        DBConstants.BASE_DATE_FORMAT, DBConstants.BD_DATE_FORMAT),
                participant.getTruncatedSsn());

        String claimantPaymentOptionQuery = String.format(InsertQuery.INSERT_CLAIMANT_PAYMENT.getQuery(), participant.getSsn());

        DBUtils.executeUpdate(Database.REAUI_MDES, claimantQuery);
        DBUtils.executeUpdate(Database.REAUI_MDES, claimantPaymentOptionQuery);
    }

    public static String getParticipantIdFname(String firstParticipantName) {
        String query = String.format(SelectQuery.SELECT_PARTICIPANT_ID_FNAME.getQuery(), firstParticipantName);
        return DBUtils.executeStringValue(Database.MDES, query);
    }


    public static String getUserFirstAndLastNameFromDB() {
        final String staffName = "autostaff";
        final String columnFirstName = "FIRST_NAME";
        final String columnLastName = "LAST_NAME";
        final int rowIndex = 0;

        String query = String.format(SelectQuery.SELECT_STAFF_NAME.getQuery(), staffName);

        try (Connection connection = DBSource.getInstance().getConnection(Database.MDES);
             Query selectFullNameQuery = connection.createQuery(query)) {
            return String.format("%1$s %2$s", selectFullNameQuery.executeAndFetchTable().rows().get(rowIndex).getString(columnFirstName),
                    selectFullNameQuery.executeAndFetchTable().rows().get(rowIndex).getString(columnLastName));
        }
    }

    public static void resetSsn(String ssn) {
        String query = String.format(UpdateQuery.UPDATE_PARTICIPANT.getQuery(), ssn);
        DBUtils.executeUpdate(Database.MDES, query);
    }

    public static void setParticipantSSN(Participant participant, String ssn) {
        String query = String.format(UpdateQuery.UPDATE_PARTICIPANT_SSN.getQuery(), ssn, participant.getLastName(),
                participant.getFirstName());
        DBUtils.executeUpdate(Database.MDES, query);
    }

    public static void setReleaseNonVeteranDate(JobOrder jobOrder) {
        String query = String.format(UpdateQuery.UPDATE_JOB_ORDER_RELEASE.getQuery(),
                CommonFunctions.getCurrentDate(DBConstants.BD_DATE_FORMAT), jobOrder.getJobTitle(), jobOrder.getEmployer().getCompanyName());

        DBUtils.executeUpdate(Database.MDES, query);
    }

    public static void changeHighestGradeCompletedToNone(Participant participant) {
        String query = String.format(UpdateQuery.UPDATE_PARTICIPANT_GRADE.getQuery(), participant.getLastName(),
                participant.getFirstName());

        DBUtils.executeUpdate(Database.MDES, query);
    }


    public static void resetTradeEnrollmentStatus(TradeEnrollment tradeEnrollment) {
        String queryUpdateTreEligible = String.format(UpdateQuery.UPDATE_RESET_TRE_ELIGIBLE.getQuery(),
                tradeEnrollment.getParticipant().getFirstName());
        String queryUpdateDetermination = String.format(UpdateQuery.UPDATE_RESET_DETERMINATION.getQuery(),
                tradeEnrollment.getParticipant().getFirstName());

        DBUtils.executeUpdate(Database.MDES, queryUpdateTreEligible);
        DBUtils.executeUpdate(Database.MDES, queryUpdateDetermination);
    }

    public static int getCountATRAEnrollments() {
        return DBUtils.executeIntegerValue(Database.MDES, SelectQuery.SELECT_ATAA_RTAA_ENROLLMENT_COUNT.getQuery());
    }

    public static int getNumberOfNotes(Participant participant) {
        String query = String.format(SelectQuery.SELECT_NUMBER_OF_NOTES.getQuery(), participant.getFirstName(), participant.getLastName());
        return DBUtils.executeIntegerValue(Database.MDES, query);
    }

    public static int getIndividualEmploymentPlanCount() {
        return DBUtils.executeIntegerValue(Database.MDES, SelectQuery.SELECT_INDIVIDUAL_EMPLOYMENT_PLAN_COUNT.getQuery());
    }


    public static int getCareerReadinessCertificationCount() {
        return DBUtils.executeIntegerValue(Database.MDES, SelectQuery.SELECT_CAREER_READINESS_CERTIFICATION_COUNT.getQuery());
    }

    public static int getAssessmentCount() {
        return DBUtils.executeIntegerValue(Database.MDES, SelectQuery.SELECT_ASSESSMENT_COUNT.getQuery());
    }

    public static void setAssessmentScores(Integer score, String participantName) {
        String query = String.format(UpdateQuery.UPDATE_ASSESSMENTS_SCORE.getQuery(), score, participantName);
        DBUtils.executeUpdate(Database.MDES, query);
    }

    public static String getOsocNameParticipantName(String firstParticipantName) {
        String query = String.format(SelectQuery.SELECT_OSOC_CODE_PARTICIPANT.getQuery(), firstParticipantName);
        return DBUtils.executeStringValue(Database.MDES, query);
    }

    public static int getCountWagePayments(String participantName) {
        String query = String.format(SelectQuery.SELECT_WAGE_PAYMENTS_CONNECTED_ENROLLMENT.getQuery(), participantName);
        return DBUtils.executeIntegerValue(Database.MDES, query);
    }

    public static int getCountWageSubsidies(String participantName) {
        String query = String.format(SelectQuery.SELECT_WAGE_SUBSIDIES_CONNECTED_ENROLLMENT.getQuery(), participantName);
        return DBUtils.executeIntegerValue(Database.MDES, query);
    }

    public static void setInvalidWia(Participant participant) {
        String query = String.format(UpdateQuery.UPDATE_WIA_ENROLLMENT.getQuery(),  participant.getFirstName(),
                participant.getLastName());
        DBUtils.executeUpdate(Database.MDES, query);
    }

    public static void setInvalidReferralCreationDate(Participant participant) {
        String query = String.format(UpdateQuery.UPDATE_REFERRAL.getQuery(), participant.getLastName(),
                participant.getFirstName());
        DBUtils.executeUpdate(Database.MDES, query);
    }
}