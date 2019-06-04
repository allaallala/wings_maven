package framework;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class required for getting values from property file
 */
public final class PropertiesResourceManager {

    private Properties properties = new Properties();

    /**
     * Constructor
     * @param resourceName - resource file
     */
    public PropertiesResourceManager(String resourceName) {
        properties = appendFromResource(properties, resourceName);
    }

    /**
     * Constructor
     * @param defaultResourceName - default resource file
     * @param resourceName - resource file
     */
    public PropertiesResourceManager(String defaultResourceName, String resourceName) {
        this(defaultResourceName);
        properties = appendFromResource(new Properties(properties), resourceName);
    }

    /**
     * Gets data from resources
     * @param objProperties property object
     * @param resourceName resource fi le
     * @return new property object
     */
    private Properties appendFromResource(Properties objProperties, String resourceName) {
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
        if (inStream != null) {
            try {
                objProperties.load(inStream);
                inStream.close();
            } catch (IOException e) {
                BaseEntity.getLogger().info(e);
            }
        } else {
            Logger.getInstance().error(String.format("Resource \"%1$s\" could not be found", resourceName));
        }
        return objProperties;
    }

    /**
     * Gets property
     * @param key - name of the parameter
     * @return value of some parameter
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Gets Boolean property
     * @param key - name of the parameter
     * @return value of some parameter
     */
    public Boolean getBooleanProperty(String key) {
        return Boolean.valueOf(properties.getProperty(key));
    }

    /**
     * Gets property
     * @param key - name of the parameter
     * @param defaultValue - default value
     * @return value of some parameter
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
