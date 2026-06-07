package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.FischerRandomBoardInitializer;
import domain.StandardBoardInitializer;
import java.awt.Window;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class WelcomeControllerTest {

    @AfterEach
    void disposeOpenMainViews() {
        for (Window window : Window.getWindows()) {
            if (window instanceof MainView) {
                ((MainView) window).dispose();
            }
        }
    }

    @Test
    void Constructor_FreshInstance_WelcomeViewNotVisible() {
        WelcomeController controller = new WelcomeController();
        assertFalse(controller.getWelcomeView().isVisible());
    }

    @Test
    void Constructor_OnFreshInstance_WelcomeViewUsesEnglishLocale() {
        WelcomeController controller = new WelcomeController();

        String expected = "Chess";
        String actual = controller.getWelcomeView().getTitle();
        assertEquals(expected, actual);
    }

    @Test
    void Show_WhenCalled_WelcomeViewBecomesVisible() {
        WelcomeController controller = new WelcomeController();
        controller.show();
        assertTrue(controller.getWelcomeView().isVisible());
    }

    @Test
    void StartGame_NonEmptyNames_WelcomeViewDisposed() {
        WelcomeController controller = new WelcomeController();
        controller.show();
        controller.getWelcomeView().setPlayer1Name("Alice");
        controller.getWelcomeView().setPlayer2Name("Bob");
        controller.getWelcomeView().clickStartGame();
        assertFalse(controller.getWelcomeView().isDisplayable());
    }

    @Test
    void StartGame_EmptyPlayer1Name_GameDoesNotStart() {
        WelcomeController controller = new WelcomeController();
        controller.show();
        controller.getWelcomeView().setPlayer1Name("");
        controller.getWelcomeView().setPlayer2Name("Bob");
        controller.getWelcomeView().clickStartGame();
        assertTrue(controller.getWelcomeView().isDisplayable());
        assertNotEquals("", controller.getWelcomeView().getErrorText());
    }

    @Test
    void StartGame_EmptyPlayer1Name_ErrorTextFromEnglishBundle() {
        WelcomeController controller = new WelcomeController();
        controller.show();
        controller.getWelcomeView().setPlayer1Name("");
        controller.getWelcomeView().setPlayer2Name("Bob");
        controller.getWelcomeView().clickStartGame();

        String expected = "Player name cannot be empty";
        String actual = controller.getWelcomeView().getErrorText();
        assertEquals(expected, actual);
    }

    @Test
    void StartGame_EmptyPlayer1Name_ErrorTextFromSpanishBundle() {
        WelcomeController controller = new WelcomeController(Locale.forLanguageTag("es"));
        controller.show();
        controller.getWelcomeView().setPlayer1Name("");
        controller.getWelcomeView().setPlayer2Name("Bob");
        controller.getWelcomeView().clickStartGame();

        String expected = "El nombre del jugador no puede estar vac\u00EDo";
        String actual = controller.getWelcomeView().getErrorText();
        assertEquals(expected, actual);
    }

    @Test
    void StartGame_EmptyPlayer2Name_GameDoesNotStart() {
        WelcomeController controller = new WelcomeController();
        controller.show();
        controller.getWelcomeView().setPlayer1Name("Alice");
        controller.getWelcomeView().setPlayer2Name("");
        controller.getWelcomeView().clickStartGame();
        assertTrue(controller.getWelcomeView().isDisplayable());
        assertNotEquals("", controller.getWelcomeView().getErrorText());
    }

    @Test
    void SelectedInitializer_StandardModeSelected_ReturnsStandardBoardInitializer() {
        WelcomeController controller = new WelcomeController();
        assertInstanceOf(StandardBoardInitializer.class, controller.selectedInitializer());
    }

    @Test
    void SelectedInitializer_Chess960ModeSelected_ReturnsFischerRandomBoardInitializer() {
        WelcomeController controller = new WelcomeController();
        controller.getWelcomeView().setChess960Selected(true);
        assertInstanceOf(FischerRandomBoardInitializer.class, controller.selectedInitializer());
    }

    @Test
    void Constructor_ActionWired_ClickingStartGameCallsStartGame() {
        WelcomeController controller = new WelcomeController();
        controller.show();
        controller.getWelcomeView().setPlayer1Name("Alice");
        controller.getWelcomeView().setPlayer2Name("Bob");
        controller.getWelcomeView().clickStartGame();
        assertFalse(controller.getWelcomeView().isDisplayable());
    }

    @Test
    void StartGame_NonEmptyNames_StartedBoardControllerNotNull() {
        WelcomeController controller = new WelcomeController();
        controller.show();
        controller.getWelcomeView().setPlayer1Name("Alice");
        controller.getWelcomeView().setPlayer2Name("Bob");

        controller.getWelcomeView().clickStartGame();

        assertNotNull(findVisibleMainView());
    }

    @Test
    void StartGame_NonEmptyNames_MainViewIsVisible() {
        WelcomeController controller = new WelcomeController();
        controller.show();
        controller.getWelcomeView().setPlayer1Name("Alice");
        controller.getWelcomeView().setPlayer2Name("Bob");

        controller.getWelcomeView().clickStartGame();

        MainView mainView = findVisibleMainView();
        assertNotNull(mainView);

        boolean expected = true;
        boolean actual = mainView.isVisible();
        assertEquals(expected, actual);
    }

    @Test
    void StartGame_NonEmptyNames_CurrentPlayerLabelShowsPlayer1Name() {
        WelcomeController controller = new WelcomeController();
        controller.show();
        controller.getWelcomeView().setPlayer1Name("Alice");
        controller.getWelcomeView().setPlayer2Name("Bob");

        controller.getWelcomeView().clickStartGame();

        MainView mainView = findVisibleMainView();
        assertNotNull(mainView);

        String expected = "Alice";
        String actual = mainView.getGameStatsView().getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }

    private static MainView findVisibleMainView() {
        for (Window window : Window.getWindows()) {
            if (window instanceof MainView) {
                MainView mainView = (MainView) window;
                if (mainView.isVisible()) {
                    return mainView;
                }
            }
        }
        return null;
    }
}
