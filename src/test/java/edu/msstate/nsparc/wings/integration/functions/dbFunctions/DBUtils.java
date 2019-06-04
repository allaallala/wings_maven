package edu.msstate.nsparc.wings.integration.functions.dbFunctions;

import edu.msstate.nsparc.wings.integration.enums.Database;
import org.sql2o.Connection;
import org.sql2o.Query;

public class DBUtils {

    public static void executeUpdate(Database database, String query) {
        try(Connection connection = DBSource.getInstance().getConnection(database);
            Query updateQuery = connection.createQuery(query)) {
            updateQuery.executeUpdate();
        }
    }

    public static int executeIntegerValue(Database database, String query) {
        try(Connection connection = DBSource.getInstance().getConnection(database);
            Query selectQuery = connection.createQuery(query)) {
            return selectQuery.executeScalar(Integer.class);
        }
    }

    public static String executeStringValue(Database database, String query) {
        try(Connection connection = DBSource.getInstance().getConnection(database);
            Query selectQuery = connection.createQuery(query)) {
            return selectQuery.executeScalar(String.class);
        }
    }
}