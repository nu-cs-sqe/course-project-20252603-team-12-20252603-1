package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.GraphicsEnvironment;
import java.util.Locale;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WelcomeViewTest {

    @SuppressFBWarnings(
            value = "RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT",
            justification = "Return value not needed; called for side-effectful headless check")
    @BeforeAll
    static void requireDisplay() {
        assumeTrue(
                !GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadless(),
                "Skipping WelcomeView tests: no display available");
    }

    @Test
    void Constructor_OnFreshWelcomeView_Player1NameIsEmpty() {
        WelcomeView view = new WelcomeView(Locale.ENGLISH);

        String expected = "";
        String actual = view.getPlayer1Name();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnFreshWelcomeView_Player2NameIsEmpty() {
        WelcomeView view = new WelcomeView(Locale.ENGLISH);

        String expected = "";
        String actual = view.getPlayer2Name();
        assertEquals(expected, actual);
    }

    @Test
    void GetPlayer1Name_WhenFieldHasText_ReturnsEnteredName() {
        WelcomeView view = new WelcomeView(Locale.ENGLISH);
        view.setPlayer1Name("Alice");

        String expected = "Alice";
        String actual = view.getPlayer1Name();
        assertEquals(expected, actual);
    }

    @Test
    void GetPlayer2Name_WhenFieldHasText_ReturnsEnteredName() {
        WelcomeView view = new WelcomeView(Locale.ENGLISH);
        view.setPlayer2Name("Bob");

        String expected = "Bob";
        String actual = view.getPlayer2Name();
        assertEquals(expected, actual);
    }

    @Test
    void IsChess960Selected_OnFreshWelcomeView_ReturnsFalse() {
        WelcomeView view = new WelcomeView(Locale.ENGLISH);

        boolean expected = false;
        boolean actual = view.isChess960Selected();
        assertEquals(expected, actual);
    }

    @Test
    void IsChess960Selected_WhenChess960RadioIsSelected_ReturnsTrue() {
        WelcomeView view = new WelcomeView(Locale.ENGLISH);
        view.setChess960Selected(true);

        boolean expected = true;
        boolean actual = view.isChess960Selected();
        assertEquals(expected, actual);
    }

    @Test
    void SetStartGameAction_WhenStartGameClicked_ActionIsInvoked() {
        WelcomeView view = new WelcomeView(Locale.ENGLISH);
        int[] callCount = {0};
        view.setStartGameAction(() -> callCount[0]++);
        view.clickStartGame();

        int expected = 1;
        int actual = callCount[0];
        assertEquals(expected, actual);
    }

    @Test
    void ClickStartGame_WhenNoActionRegistered_DoesNotThrow() {
        WelcomeView view = new WelcomeView(Locale.ENGLISH);
        view.clickStartGame();
    }

    @Test
    void Constructor_OnEnglishLocale_WindowTitleFromBundle() {
        WelcomeView view = new WelcomeView(Locale.ENGLISH);

        String expected = "Chess";
        String actual = view.getTitle();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnEnglishLocale_WelcomeTitleFromBundle() {
        WelcomeView view = new WelcomeView(Locale.ENGLISH);

        String expected = "\u265F  Chess  \u265F";
        String actual = view.getWelcomeTitleText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnEnglishLocale_Player1LabelFromBundle() {
        WelcomeView view = new WelcomeView(Locale.ENGLISH);

        String expected = "Player 1";
        String actual = view.getPlayer1LabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnEnglishLocale_Player2LabelFromBundle() {
        WelcomeView view = new WelcomeView(Locale.ENGLISH);

        String expected = "Player 2";
        String actual = view.getPlayer2LabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnEnglishLocale_StandardModeLabelFromBundle() {
        WelcomeView view = new WelcomeView(Locale.ENGLISH);

        String expected = "Standard";
        String actual = view.getStandardModeLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnEnglishLocale_Chess960ModeLabelFromBundle() {
        WelcomeView view = new WelcomeView(Locale.ENGLISH);

        String expected = "Chess960";
        String actual = view.getChess960ModeLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnEnglishLocale_StartGameButtonFromBundle() {
        WelcomeView view = new WelcomeView(Locale.ENGLISH);

        String expected = "Start Game";
        String actual = view.getStartGameButtonText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnSpanishLocale_WindowTitleFromBundle() {
        WelcomeView view = new WelcomeView(Locale.forLanguageTag("es"));

        String expected = "Ajedrez";
        String actual = view.getTitle();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnSpanishLocale_WelcomeTitleFromBundle() {
        WelcomeView view = new WelcomeView(Locale.forLanguageTag("es"));

        String expected = "\u265F  Ajedrez  \u265F";
        String actual = view.getWelcomeTitleText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnSpanishLocale_Player1LabelFromBundle() {
        WelcomeView view = new WelcomeView(Locale.forLanguageTag("es"));

        String expected = "Jugador 1";
        String actual = view.getPlayer1LabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnSpanishLocale_Player2LabelFromBundle() {
        WelcomeView view = new WelcomeView(Locale.forLanguageTag("es"));

        String expected = "Jugador 2";
        String actual = view.getPlayer2LabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnSpanishLocale_StandardModeLabelFromBundle() {
        WelcomeView view = new WelcomeView(Locale.forLanguageTag("es"));

        String expected = "Est\u00E1ndar";
        String actual = view.getStandardModeLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnSpanishLocale_Chess960ModeLabelFromBundle() {
        WelcomeView view = new WelcomeView(Locale.forLanguageTag("es"));

        String expected = "Chess960";
        String actual = view.getChess960ModeLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnSpanishLocale_StartGameButtonFromBundle() {
        WelcomeView view = new WelcomeView(Locale.forLanguageTag("es"));

        String expected = "Iniciar juego";
        String actual = view.getStartGameButtonText();
        assertEquals(expected, actual);
    }
}
