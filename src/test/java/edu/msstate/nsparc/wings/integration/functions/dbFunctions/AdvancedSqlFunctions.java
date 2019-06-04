package edu.msstate.nsparc.wings.integration.functions.dbFunctions;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Database;
import edu.msstate.nsparc.wings.integration.enums.SqlQueries.InsertQuery;
import edu.msstate.nsparc.wings.integration.enums.SqlQueries.SelectQuery;
import edu.msstate.nsparc.wings.integration.enums.SqlQueries.UpdateQuery;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.participant.Participant;
import org.sql2o.Connection;
import org.sql2o.Query;

import java.util.List;

public class AdvancedSqlFunctions {

    public static String[] getPdoProviderCreatedDate(String title) {
        final String columnName = "NAME";
        final String columnDateCreated = "DATE_CREATED";
        final String columnId = "ID";
        final int rowIndex = 0;

        String[] result = {null, null, null};

        try(Connection connection = DBSource.getInstance().getConnection(Database.MDES);
            Query query = connection.createQuery(String.format(SelectQuery.SELECT_PDO_PROVIDER_CREATED_DATE.getQuery(), title))) {

            result[0] = query.executeAndFetchTable().rows().get(rowIndex).getString(columnName);
            result[1] = query.executeAndFetchTable().rows().get(rowIndex).getString(columnDateCreated);
            result[2] = query.executeAndFetchTable().rows().get(rowIndex).getString(columnId);
        }
        return result;
    }

    public static void resetCourseCode(String code) {
        String query = String.format(UpdateQuery.UPDATE_COURSE.getQuery(), code);
        DBUtils.executeUpdate(Database.MDES, query);
    }

    public static void resetTrainingProviderCode(String code) {
        String query = String.format(UpdateQuery.UPDATE_TRAINING_PROVIDER.getQuery(), code);
        DBUtils.executeUpdate(Database.MDES, query);
    }

    public static void changeServiceEnrollmentDate(Participant participant, String date) {
        String queryPostedEnrollment = String.format(UpdateQuery.UPDATE_SERVICE_ENROLLMENT_POSTED.getQuery(), participant.getLastName(),
                participant.getFirstName(), date);

        String queryResultEnrollment = String.format(UpdateQuery.UPDATE_SERVICE_ENROLLMENT_RESULT.getQuery(), participant.getLastName(),
                participant.getFirstName(), date);
        DBUtils.executeUpdate(Database.MDES, queryPostedEnrollment);
        DBUtils.executeUpdate(Database.MDES, queryResultEnrollment);
    }

    public static void changeServiceEnrollmentDate(Employer employer) {
        String queryUpdateServiceEnrollmentPosted = String.format(UpdateQuery.UPDATE_SERVICE_ENROLLMENT_EMPLOYER_POSTED.getQuery(),
                employer.getCompanyName());
        String queryUpdateServiceEnrollmentResult = String.format(UpdateQuery.UPDATE_SERVICE_ENROLLMENT_EMPLOYER_RESULT.getQuery(),
                employer.getCompanyName());

        DBUtils.executeUpdate(Database.MDES, queryUpdateServiceEnrollmentPosted);
        DBUtils.executeUpdate(Database.MDES, queryUpdateServiceEnrollmentResult);
    }

    public static int getCountTrainingConnectedCourse(String courseName, String trainingType) {
        String query;
        if (trainingType.contains(Constants.WIOA.toUpperCase())) {
            query = String.format(SelectQuery.SELECT_WIA_QUERY_COURSE.getQuery(), courseName);
        } else {
            query = String.format(SelectQuery.SELECT_TRADE_QUERY_COURSE.getQuery(), courseName);
        }

        return DBUtils.executeIntegerValue(Database.MDES, query);
    }

    public static int getActiveInactiveCoursesNumber(int active) {
        String query = String.format(SelectQuery.SELECT_COURSE_COUNT.getQuery(), active);
        return DBUtils.executeIntegerValue(Database.MDES, query);
    }

    public static int getCountTrainingConnectedProvider(String providerName, String trainingType) {
        String query;
        if (trainingType.contains(Constants.WIOA)) {
            query = String.format(SelectQuery.SELECT_WIA_QUERY_PROVIDER.getQuery(), providerName);
        } else {
            query = String.format(SelectQuery.SELECT_TRADE_QUERY_PROVIDER.getQuery(), providerName);
        }
        return DBUtils.executeIntegerValue(Database.MDES, query);
    }

    public static int getTrainingProviderCount() {
        return DBUtils.executeIntegerValue(Database.MDES, SelectQuery.SELECT_TRAINING_PROVIDER_COUNT.getQuery());
    }

    public static int getNumberOfJobCenters() {
        return DBUtils.executeIntegerValue(Database.MDES, SelectQuery.SELECT_NUMBER_OF_JOB_CENTERS.getQuery());
    }

    public static void insertPdo(String title, String dateCreatedPosted, String dateFinished) {
        String query = String.format(InsertQuery.INSERT_NEW_PDO.getQuery(), title, dateCreatedPosted, dateFinished);
        DBUtils.executeUpdate(Database.MDES, query);
    }

    public static List<String> getJobCentresNamesFromDB() {
         try(Connection connection = DBSource.getInstance().getConnection(Database.MDES);
             Query selectQuery = connection.createQuery(SelectQuery.SELECT_CENTER_NAME.getQuery())) {
            return selectQuery.executeScalarList(String.class);
        }
    }

    public static String[] getLocationCodesOrderedByASC(String trainingProviderName) {
        String[] locationCodes = {null, null, null, null, null, null, null, null, null, null};
        try(Connection connection = DBSource.getInstance().getConnection(Database.MDES);
            Query selectQuery = connection.createQuery(String.format(SelectQuery.SELECT_CODE_LOCATION.getQuery(), trainingProviderName))) {
            selectQuery.executeScalarList(String.class).toArray(locationCodes);
            return locationCodes;
        }
    }

    public static void resetAccount(String account) {
        String query = String.format(UpdateQuery.UPDATE_APPLICATION_USER.getQuery(), account);
        DBUtils.executeUpdate(Database.MDES, query);
    }
}