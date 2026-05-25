package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.awt.GraphicsEnvironment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WelcomeViewTest {

    @BeforeAll
    static void requireDisplay() {
        System.setProperty("java.awt.headless", "false");
        assumeTrue(
            !GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadless(),
            "Skipping WelcomeView tests: no display available");
    }

    @Test
    void Constructor_OnFreshWelcomeView_Player1NameIsEmpty() {
        WelcomeView view = new WelcomeView();

        String expected = "";
        String actual = view.getPlayer1Name();
        assertEquals(expected, actual);
    }
}
