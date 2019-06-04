package edu.msstate.nsparc.wings.integration.functions.dbFunctions;

import aqa.properties.PropertiesResourceManager;
import edu.msstate.nsparc.wings.integration.enums.Database;
import framework.Logger;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

public class DBSource {
    private static ThreadLocal<DBSource> instance = new ThreadLocal<>();
    private static PropertiesResourceManager props = new PropertiesResourceManager("database.properties");

    private String serverUrl;
    private String userName;
    private String password;
    private String driver;

    private DBSource() {
    }

    private Sql2o getSqlUrl(String serverUrl, String userName, String password) {
        return new Sql2o(serverUrl, userName, password);
    }

    private enum DBProps {
        IP("%1$sip"),
        PORT("%1$sport"),
        BASE_NAME("%1$sbaseName"),
        SERVER_URL("%1$sserverUrl"),
        USERNAME("%1$suserName"),
        DRIVER("%1$sdriverName"),
        PASSWORD("%1$spassword");

        String prop;

        DBProps(String prop){
            this.prop = prop;
        }

        private String getValue(){
            return prop;
        }
    }

    private String getDBProp(Database database, DBProps dbProps) {
        return props.getProperty(String.format(dbProps.getValue(), database.toString()));
    }

    private void initializeDB(Database database) {
        String ip = getDBProp(database, DBProps.IP);
        String port = getDBProp(database, DBProps.PORT);
        String baseName = getDBProp(database, DBProps.BASE_NAME);
        String serverUrlProp = getDBProp(database, DBProps.SERVER_URL);
        userName = getDBProp(database, DBProps.USERNAME);
        password = getDBProp(database, DBProps.PASSWORD);
        serverUrl = String.format(serverUrlProp, ip, port, baseName);
        driver = getDBProp(database, DBProps.DRIVER);
    }

    public static DBSource getInstance() {
        if (instance.get() == null) {
            instance.set(new DBSource());
        }
        return instance.get();
    }

    public Connection getConnection(Database database) {
        initializeDB(database);
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = getSqlUrl(serverUrl, userName, password).open();
        } catch (ClassNotFoundException e) {
            Logger.getInstance().warn(e.getMessage());
        }

        return connection;
    }
}