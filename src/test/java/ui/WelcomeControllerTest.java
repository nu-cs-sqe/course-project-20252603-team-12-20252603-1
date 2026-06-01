package ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import domain.FischerRandomBoardInitializer;
import domain.StandardBoardInitializer;
import org.junit.jupiter.api.Test;

class WelcomeControllerTest {

    @Test
    void Constructor_FreshInstance_WelcomeViewNotVisible() {
        WelcomeController controller = new WelcomeController();
        assertFalse(controller.getWelcomeView().isVisible());
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
        controller.startGame();
        assertFalse(controller.getWelcomeView().isDisplayable());
    }

    @Test
    void StartGame_EmptyPlayer1Name_GameDoesNotStart() {
        WelcomeController controller = new WelcomeController();
        controller.show();
        controller.getWelcomeView().setPlayer1Name("");
        controller.getWelcomeView().setPlayer2Name("Bob");
        controller.startGame();
        assertTrue(controller.getWelcomeView().isDisplayable());
        assertNotEquals("", controller.getWelcomeView().getErrorText());
    }

    @Test
    void StartGame_EmptyPlayer2Name_GameDoesNotStart() {
        WelcomeController controller = new WelcomeController();
        controller.show();
        controller.getWelcomeView().setPlayer1Name("Alice");
        controller.getWelcomeView().setPlayer2Name("");
        controller.startGame();
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

}
