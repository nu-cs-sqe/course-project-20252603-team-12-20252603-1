package ui;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Loads UI strings from {@code messages.properties} for a given locale.
 *
 * <p>Missing keys return {@code !key!} so untranslated labels are visible during development.
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
     * @return bundle value, or {@code !key!} when the key is absent
     */
    public String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
