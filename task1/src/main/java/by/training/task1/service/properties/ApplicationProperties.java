package by.training.task1.service.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {
    public static Properties getProperties() {
        Properties properties = new Properties();
        try(InputStream inputStream = ApplicationProperties.class.getClassLoader().
                getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                // логирование события
                System.out.println("File configuration \"application.properties\" is not found");
                System.exit(0);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            // логирование события
            e.printStackTrace();
        }
        return properties;
    }
}
