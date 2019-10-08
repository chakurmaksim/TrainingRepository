package by.training.certificationCenter.service.configuration;

import java.util.ResourceBundle;

public final class PathConfiguration {
    private static final ResourceBundle resourceBundle;

    static {
        resourceBundle = ResourceBundle.getBundle("pathConfig");
    }

    private PathConfiguration() {
    }

    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
