package edu.msstate.nsparc.wings.integration.functions.dbFunctions;

import edu.msstate.nsparc.wings.integration.constants.Constants;
import edu.msstate.nsparc.wings.integration.enums.Database;
import edu.msstate.nsparc.wings.integration.enums.SqlQueries.InsertQuery;
import edu.msstate.nsparc.wings.integration.enums.SqlQueries.SelectQuery;
import edu.msstate.nsparc.wings.integration.enums.SqlQueries.UpdateQuery;
import edu.msstate.nsparc.wings.integration.models.employer.Employer;
import edu.msstate.nsparc.wings.integration.models.trade.Petition;
import edu.msstate.nsparc.wings.integration.models.wagnerPeyser.JobOrder;
import framework.AccountUtils;
import framework.CommonFunctions;
import org.sql2o.Connection;
import org.sql2o.Query;

import java.util.ArrayList;
import java.util.List;

public class EmployerSqlFunctions {

    private static List<String> getIDsOfEmployerAddress(Employer employer, Integer numOfAddresses) {
        String query = String.format(InsertQuery.INSERT_ADDRESS.getQuery(), employer.getAddress().getLineOne(),
                employer.getAddress().getLineOne(), employer.getAddress().getCity(), employer.getAddress().getZipCode(), DBConstants.ZERO, DBConstants.ZERO,
                CommonFunctions.getCurrentSQLDate());

        String selectQuery = String.format(SelectQuery.SELECT_ADDRESSES.getQuery(), employer.getAddress().getLineOne(), numOfAddresses);

        for (int i = 0; i < numOfAddresses; i++) {
            DBUtils.executeUpdate(Database.MDES, query);
        }

        try (Connection connection = DBSource.getInstance().getConnection(Database.MDES);
             Query selectAddressQuery = connection.createQuery(selectQuery)) {
            return selectAddressQuery.executeScalarList(String.class);
        }
    }

    private static List<String> getIDsOfEmployerContactInfo(Employer employer, String firstAddress, String secondAddress) {
        final int boundaryValue = 10;
        String query = String.format(InsertQuery.INSERT_CONTACT_INFO.getQuery(), firstAddress, secondAddress,
                employer.getTelephoneNumber(), null, DBConstants.QUOTES, DBConstants.QUOTES, DBConstants.QUOTES);

        String primary = CommonFunctions.getRandomIntegerNumber(boundaryValue);
        String secondary = CommonFunctions.getRandomIntegerNumber(boundaryValue);

        String queryInsertAnotherContact = String.format(InsertQuery.INSERT_CONTACT_INFO.getQuery(),
                null, null, primary, secondary, null, DBConstants.QUOTES, null);

        String querySelectContactInfo = String.format(SelectQuery.SELECT_CONTACT_INFO.getQuery(), firstAddress, secondAddress);
        String querySelectEmptyContact = String.format(SelectQuery.SELECT_EMPTY_CONTACT_INFO.getQuery(), primary, secondary);
        List<String> result = new ArrayList<>();

        DBUtils.executeUpdate(Database.MDES, query);
        DBUtils.executeUpdate(Database.MDES, queryInsertAnotherContact);
        result.add(DBUtils.executeStringValue(Database.MDES, querySelectContactInfo));
        result.add(DBUtils.executeStringValue(Database.MDES, querySelectEmptyContact));

        return result;
    }

    private static String getEmployerUserID(Employer employer) {
        String query = String.format(InsertQuery.INSERT_EMPLOYER_USER.getQuery(), employer.getEmployerAccount());
        String selectQuery = String.format(SelectQuery.SELECT_USER_ID.getQuery(), employer.getEmployerAccount());

        DBUtils.executeUpdate(Database.MDES, query);
        return DBUtils.executeStringValue(Database.MDES, selectQuery);
    }

    public static Employer createEmployerUsingSQL(Employer employer) {
        employer.setEmployerAccount(AccountUtils.getEmployerAccount());
        List<String> addresses = getIDsOfEmployerAddress(employer, 3);
        List<String> contactInfo = getIDsOfEmployerContactInfo(employer, addresses.get(2), addresses.get(1));
        String query = String.format(InsertQuery.INSERT_EMPLOYER.getQuery(), contactInfo.get(0), getEmployerUserID(employer),
                contactInfo.get(1), addresses.get(0), employer.getCompanyName(), CommonFunctions.getCurrentSQLDate());
        String selectQuery = String.format(SelectQuery.SELECT_EMPLOYER_ID.getQuery(), employer.getCompanyName());

        DBUtils.executeUpdate(Database.MDES, query);
        employer.setEmployerID(DBUtils.executeStringValue(Database.MDES, selectQuery));

        return employer;
    }

    private static void insertEmployerInformationInReaui(Employer employer) {
        String employerQuery = String.format(
                InsertQuery.INSERT_EMPLOYER_QUERY.getQuery(), employer.getEan(), employer.getCompanyName());

        String employerAccountQuery = String.format(InsertQuery.INSERT_EMPLOYER_ACCOUNT.getQuery(),
                employer.getFein(), employer.getEan().substring(0, 8), employer.getCompanyName());

        String mississippiEmployerQuery = String.format(InsertQuery.INSERT_MISSISSIPPI_EMPLOYER_QUERY.getQuery(),
                employer.getCompanyName());

        DBUtils.executeUpdate(Database.REAUI_MDES, employerQuery);
        DBUtils.executeUpdate(Database.REAUI_MDES, employerAccountQuery);
        DBUtils.executeUpdate(Database.REAUI_MDES, mississippiEmployerQuery);
    }

    public static int getEmployerServiceEnrollmentResult(String result) {
        String query = String.format(SelectQuery.SELECT_EMPLOYER_ENROLLMENT_RESULT.getQuery(), result);

        return DBUtils.executeIntegerValue(Database.MDES, query);
    }

    public static void setSameEmployerAccount(Employer employer, String account) {
        String query = String.format(UpdateQuery.UPDATE_APPLICATION.getQuery(), account, employer.getCompanyName());
        DBUtils.executeUpdate(Database.MDES, query);
    }

    private static String getDriverLicense(String category) {
        DBUtils.executeUpdate(Database.MDES, String.format(InsertQuery.INSERT_DRIVER_LICENSE.getQuery(),
                category));
        return DBUtils.executeStringValue(Database.MDES, SelectQuery.SELECT_LAST_DRIVER_LICENSE.getQuery());
    }

    private static String getEmptyContactInfo() {
        final int boundaryValue = 10;
        String primary = CommonFunctions.getRandomIntegerNumber(boundaryValue);
        String secondary = CommonFunctions.getRandomIntegerNumber(boundaryValue);
        String query = String.format(InsertQuery.INSERT_CONTACT_INFO.getQuery(), null, null, primary, secondary, null, DBConstants.QUOTES, null);
        String selectQuery = String.format(SelectQuery.SELECT_EMPTY_CONTACT_INFO.getQuery(), primary, secondary);

        DBUtils.executeUpdate(Database.MDES, query);
        return DBUtils.executeStringValue(Database.MDES, selectQuery);
    }

    private static String getJobOrderServiceEnrollment(String employerID) {
        String query = String.format(InsertQuery.INSERT_SERVICE_ENROLLMENT_JOB_ORDER.getQuery(), CommonFunctions.getCurrentSQLDate(),
                CommonFunctions.getCurrentSQLDate(), employerID);

        String querySelect = String.format(SelectQuery.SELECT_SERVICE_ENROLLMENT_JOB_ORDER.getQuery(), employerID);

        DBUtils.executeUpdate(Database.MDES, query);
        return DBUtils.executeStringValue(Database.MDES, querySelect);
    }

    public static JobOrder createJobOrderUsingSQL(JobOrder jobOrder) {
        final String category = "B";
        final int oneYearDaysForJODuration = 356;
        final int startJODay = 1;
        String query = String.format(InsertQuery.INSERT_JOB_ORDER.getQuery(), getDriverLicense(category),
                jobOrder.getEmployer().getEmployerID(), CommonFunctions.getCurrentSQLDate(), jobOrder.getDescription(),
                CommonFunctions.getDaysNextDate(CommonFunctions.getCurrentDate(), startJODay, Constants.SQL_FORMAT),
                CommonFunctions.getDaysNextDate(CommonFunctions.getCurrentDate(), oneYearDaysForJODuration, Constants.SQL_FORMAT),
                CommonFunctions.getCurrentSQLDate(), jobOrder.getJobTitle(), getEmptyContactInfo(),
                CommonFunctions.getCurrentSQLDate(),
                CommonFunctions.getCurrentSQLDate(), getJobOrderServiceEnrollment(jobOrder.getEmployer().getEmployerID()));
        DBUtils.executeUpdate(Database.MDES, query);
        String jobOrderId = DBUtils.executeStringValue(Database.MDES, String.format(SelectQuery.SELECT_JOB_ORDER_ID.getQuery(), jobOrder.getJobTitle()));
        jobOrder.setJobID(jobOrderId);

        return jobOrder;
    }

    public static int getRapidResponseEventCount() {
        return DBUtils.executeIntegerValue(Database.MDES, SelectQuery.SELECT_RAPID_RESPONSE_EVENT_COUNT.getQuery());
    }

    public static int getTradeTrainingCount() {
        return DBUtils.executeIntegerValue(Database.MDES, SelectQuery.SELECT_TRADE_TRAINING_COUNT.getQuery());
    }

    public static void setInvalidJobOrderCreationDate(JobOrder jobOrder) {
        String query = String.format(UpdateQuery.UPDATE_JOB_ORDER.getQuery(), jobOrder.getJobTitle(),
                jobOrder.getEmployer().getCompanyName());
        DBUtils.executeUpdate(Database.MDES, query);
    }

    public static void insertPetitionInformationInReaui(Petition petition) {
        if (petition.isOutOfState()) {
            return;
        }

        EmployerSqlFunctions.insertEmployerInformationInReaui(petition.getEmployer());

        String petitionQuery = String.format(InsertQuery.INSERT_PETITION_QUERY.getQuery(), petition.getNumber(),
                petition.getEmployer().getCompanyName(),
                CommonFunctions.changeDateFormat(petition.getCertificationDate(), DBConstants.BASE_DATE_FORMAT,
                        DBConstants.BD_DATE_FORMAT));

        DBUtils.executeUpdate(Database.REAUI_MDES, petitionQuery);
    }
}