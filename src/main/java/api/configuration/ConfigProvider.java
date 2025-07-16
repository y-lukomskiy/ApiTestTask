package api.configuration;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Log4j2
@NoArgsConstructor
public class ConfigProvider {
    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "config.properties";
    private static ConfigProvider instance;

    static {
        try (InputStream input = ConfigProvider.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
            } else {
                log.error("Unable to find " + CONFIG_FILE);
            }
        } catch (IOException e) {
            log.error("Error loading configuration: {}", e.getMessage());
        }
    }

    public static synchronized ConfigProvider getInstance() {
        if (instance == null) {
            instance = new ConfigProvider();
        }
        return instance;
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public String getBaseUrl() {
        return getProperty("base.url", "https://fakerestapi.azurewebsites.net");
    }

    public String getApiPath() {
        return getProperty("base.path", "/api/v1");
    }

    public int getRequestTimeout() {
        String timeout = getProperty("request.timeout", "15000");
        return Integer.parseInt(timeout);
    }
}