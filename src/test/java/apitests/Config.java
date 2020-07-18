package apitests;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
    private Map<String, String> map = new HashMap<>();

    public Config() {
        setConfig();
    }

    private String getKeyFromSystemProp() {
        return System.getProperty("key");
    }

    private String getTokenFromSystemProp() {
        return System.getProperty("token");
    }

    private void setConfig() {
        try {
            if (getKeyFromSystemProp() != null && getTokenFromSystemProp() != null) {
                map.put("key", getKeyFromSystemProp());
                map.put("token", getTokenFromSystemProp());
            } else {
                Properties loadPropFile = new Properties();
                loadPropFile.load(new FileInputStream("src/test/resources/config.properties"));

                map.put("key", loadPropFile.getProperty("key"));
                map.put("token", loadPropFile.getProperty("token"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getKey() {
        return map.get("key");
    }

    public String getToken() {
        return map.get("token");
    }
}