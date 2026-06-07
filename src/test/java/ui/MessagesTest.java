package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import org.junit.jupiter.api.Test;

class MessagesTest {

    @Test
    void GetString_OnKnownKey_ReturnsBundleValue() {
        Messages messages = new Messages(Locale.ENGLISH);

        String expected = "{0} versus {1}";
        String actual = messages.getString("matchupPattern");
        assertEquals(expected, actual);
    }

    @Test
    void GetString_OnMissingKey_ReturnsBangWrappedKey() {
        Messages messages = new Messages(Locale.ENGLISH);

        String expected = "!nonexistentKey!";
        String actual = messages.getString("nonexistentKey");
        assertEquals(expected, actual);
    }
}
