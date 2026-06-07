package ui;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Loads UI strings from {@code messages.properties} for a given locale.
 */
public class Messages {

    private static final String BUNDLE_NAME = "messages";
    private final ResourceBundle bundle;

    /**
     * @param locale non-null locale used to select the resource bundle
     */
    public Messages(Locale locale) {
        bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
    }

    /**
     * @param key non-null property key
     * @return bundle value for the key
     */
    public String getString(String key) {
        return bundle.getString(key);
    }
}
