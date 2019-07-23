package by.training.task1.service.properties;

import by.training.task1.dao.filereader.FileWriteReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties class.
 */
public final class ApplicationProperties {
    /**
     * Events logger.
     */
    private static Logger logger = LogManager.getLogger(FileWriteReader.class);

    private ApplicationProperties() {
    }

    /**
     * Get method.
     *
     * @return Properties instance.
     */
    public static Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = ApplicationProperties.class.
                getClassLoader().getResourceAsStream(
                "application.properties")) {
            if (inputStream == null) {
                String msg = "File configuration \"application.properties\" "
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
