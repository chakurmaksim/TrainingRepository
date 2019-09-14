package by.training.certificationCenter.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration class.
 */
public class ApplicationConfiguration {
    /**
     * Events logger.
     */
    private static Logger logger = LogManager.getLogger(ApplicationConfiguration.class);

    /**
     * Get method.
     *
     * @return Properties instance.
     */
    public static Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = ApplicationConfiguration.class.
                getClassLoader().getResourceAsStream(
                "application.properties")) {
            if (inputStream == null) {
                String msg = "File configuration \"application.configuration\" "
                        + "is not found";
                logger.error(msg);
                System.exit(0);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return properties;
    }
}
