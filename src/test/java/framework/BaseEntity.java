package framework;

import webdriver.Browser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.fail;

/**
 * Base class for any entities.
 */
public abstract class BaseEntity {
    protected static final PropertiesResourceManager global = new PropertiesResourceManager("global.properties");

    private static ThreadLocal<ArrayList<String>> listError = ThreadLocal.withInitial(ArrayList::new);
    protected static ThreadLocal<ArrayList<File>> listWithScreenshots = ThreadLocal.withInitial(ArrayList::new);

    protected static final PropertiesResourceManager properties = new PropertiesResourceManager("selenium.properties", "global.properties");
    private static String ROLE_PATH = null;

    // default values
    private static final String PROPERTIES_FILE = "selenium.properties";
    private static final String LOCALRUN = "localrun";
    private static final String DEFAULT_BROWSER_URL = "http://www.google.com";
    private static final String DEFAULT_PAGE_LOAD_TIMEOUT = "120000";
    private static final String DEFAULT_SCREENSHOTS_FOLDER = global.getProperty("screenshotsFolder");
    private static final String DEFAULT_LDAP_HOST = "localhost";
    private static final String DEFAULT_LDAP_PORT = "10389";

    // class variables
    private static String browserURL;
    private static String timeoutForPageLoad;
    private static String screenshotsFolder;
    private static String ldapHost;
    private static int ldapPort;

    /**
     * Inits properties
     */
    protected static void initProperties() {

        PropertiesResourceManager props;
        props = new PropertiesResourceManager(PROPERTIES_FILE, "global.properties");
        timeoutForPageLoad = props.getProperty("defaultPageLoadTimeout", DEFAULT_PAGE_LOAD_TIMEOUT);
        ldapHost = props.getProperty("ldapHost", DEFAULT_LDAP_HOST);
        ldapPort = Integer.parseInt(props.getProperty("ldapPort", DEFAULT_LDAP_PORT));
        props = new PropertiesResourceManager("global.properties");
        browserURL = CommonFunctions.regexGetMatch(props.getProperty("urlLoginPage", DEFAULT_BROWSER_URL), "^http://[\\w\\.]+");
        screenshotsFolder = System.getProperty("screenshotsFolder", DEFAULT_SCREENSHOTS_FOLDER);
        if (!screenshotsFolder.matches("^\\\\.*")) {
            File f = new File(screenshotsFolder);
            if (f.mkdirs()) {
                Logger.getInstance().info("File is created");
            }
        }
    }

    public static String getRolePath(String role) {
        return "rolepermissions\\" + role + ".properties";
    }

    /**
     * Gets LDAP host
     *
     * @return value of LDAP host
     */
    public static String getLdapHost() {
        return ldapHost;
    }

    /**
     * Gets LDAP port
     *
     * @return value of LDAP port
     */
    public static int getLdapPort() {
        return ldapPort;
    }

    // ==============================================================================================
    // logging

    /**
     * Gets list of errors occured in test
     *
     * @return array list of errors.
     */
    public static List<String> getErrorsList() {
        return listError.get();
    }

    /**
     * Gets logger
     *
     * @return instance of Logger
     */
    public static Logger getLogger() {
        return Logger.getInstance();
    }

    public static Browser getBrowser() {
        return Browser.getInstance();
    }

    /**
     * Format message
     *
     * @param message message to format
     * @return formatted message
     */
    protected String formatLogMessage(String message) {
        return message;
    }

    /**
     * Logs the message into console in specified format
     *
     * @param message - message
     */
    protected void info(String message) {
        Logger.getInstance().info(formatLogMessage(message));
    }

    /**
     * Logs the error message into console in specified format
     *
     * @param message - message
     */
    protected void error(String message) {
        Logger.getInstance().error(formatLogMessage(message));
    }

    /**
     * Logs the fatal message (stops the execution) into console in specified format
     *
     * @param message - message
     */
    protected void fatal(String message) {
        Logger.getInstance().fatal(formatLogMessage(message));
        fail(message);
    }

    /**
     * Divide message.
     *
     * @param message - message to display
     */
    public static void divideMessage(String message) {
        Logger.getInstance().info("-------------------------------------------------------------------");
        Logger.getInstance().info("----- " + message + " -----");
        Logger.getInstance().info("-------------------------------------------------------------------");
    }


    public static void scrollUp() {
        Browser.getDriver().executeScript("window.scrollTo(0, 0)");
    }

    public void scrollDown() {
        Browser.getDriver().executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
}
