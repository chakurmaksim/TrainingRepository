package by.training.certificationCenter.controller.resourceBundle;

import by.training.certificationCenter.service.configuration.PathConfiguration;

import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

public final class ResourceBundleWrapper {
    private static final ResourceBundleWrapper SINGLE_INSTANCE;
    private static final String ATTRIBUTE_NAME_LOCALE = "locale";
    private static final String RESOURCES_FILE_NAME = "pagecontent";
    static {
        SINGLE_INSTANCE = new ResourceBundleWrapper();
    }
    private ResourceBundleWrapper() {
    }

    public static ResourceBundleWrapper getSingleInstance() {
        return SINGLE_INSTANCE;
    }

    public ResourceBundle createResourceBundle(final HttpSession session) {
        String locale = (String) session.getAttribute(ATTRIBUTE_NAME_LOCALE);
        String[] localeArr = locale.split("_");
        Locale current;
        if (localeArr.length == 2) {
            current = new Locale(localeArr[0], localeArr[1]);
        } else {
            localeArr = PathConfiguration.getProperty("default.page.locale").
                    split("_");
            current = new Locale(localeArr[0], localeArr[1]);
        }
        ResourceBundle bundle = ResourceBundle.getBundle(
                RESOURCES_FILE_NAME, current);
        return bundle;
    }
}
