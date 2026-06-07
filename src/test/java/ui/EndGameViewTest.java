package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.GraphicsEnvironment;
import java.util.Locale;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class EndGameViewTest {

    @SuppressFBWarnings(
            value = "RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT",
            justification = "Return value not needed; called for side-effectful headless check")
    @BeforeAll
    static void requireDisplay() {
        assumeTrue(
                !GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadless(),
                "Skipping EndGameView tests: no display available");
    }

    @Test
    void Constructor_OnEnglishLocale_WindowTitleFromBundle() {
        EndGameView view = new EndGameView("Alice wins!", Locale.ENGLISH);

        String expected = "Game Over";
        String actual = view.getTitle();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnEnglishLocale_PlayAgainButtonFromBundle() {
        EndGameView view = new EndGameView("Alice wins!", Locale.ENGLISH);

        String expected = "Play Again";
        String actual = view.getPlayAgainButtonText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnSpanishLocale_WindowTitleFromBundle() {
        EndGameView view = new EndGameView("Alice wins!", Locale.forLanguageTag("es"));

        String expected = "Fin del juego";
        String actual = view.getTitle();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnSpanishLocale_PlayAgainButtonFromBundle() {
        EndGameView view = new EndGameView("Alice wins!", Locale.forLanguageTag("es"));

        String expected = "Jugar de nuevo";
        String actual = view.getPlayAgainButtonText();
        assertEquals(expected, actual);
    }
}
