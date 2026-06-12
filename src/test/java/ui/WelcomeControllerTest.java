package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Window;
import java.util.Locale;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class WelcomeControllerTest {

    @AfterEach
    void disposeOpenUiWindows() {
        for (Window window : Window.getWindows()) {
            if (window instanceof WelcomeView || window instanceof MainView) {
                window.dispose();
            }
        }
    }

    @Test
    void Show_WhenCalled_WelcomeViewBecomesVisible() {
        WelcomeController controller = new WelcomeController();
        controller.show();
        assertTrue(controller.getWelcomeView().isVisible());
    }

    @Test
    void SetWelcomeView_WhenCalled_WiresStartGameAction() {
        final WelcomeView welcomeView = EasyMock.createMock(WelcomeView.class);
        Capture<Runnable> action = EasyMock.newCapture();
        welcomeView.setStartGameAction(EasyMock.capture(action));
        EasyMock.expectLastCall().once();
        EasyMock.replay(welcomeView);

        WelcomeController controller = new WelcomeController();
        controller.setWelcomeView(welcomeView);

        EasyMock.verify(welcomeView);
        assertNotNull(action.getValue());
    }

    @Test
    void GetWelcomeView_AfterSetWelcomeView_ReturnsSameView() {
        final WelcomeView welcomeView = EasyMock.createMock(WelcomeView.class);
        welcomeView.setStartGameAction(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        EasyMock.replay(welcomeView);

        WelcomeController controller = new WelcomeController();
        controller.setWelcomeView(welcomeView);

        assertSame(welcomeView, controller.getWelcomeView());
        EasyMock.verify(welcomeView);
    }

    @Test
    void StartGame_EmptyPlayer1Name_ShowsErrorAndDoesNotLaunch() {
        final WelcomeView welcomeView = EasyMock.createMock(WelcomeView.class);
        welcomeView.setStartGameAction(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        EasyMock.expect(welcomeView.getPlayer1Name()).andReturn("");
        EasyMock.expect(welcomeView.getPlayer2Name()).andReturn("Bob");
        EasyMock.expect(welcomeView.getSelectedLocale()).andReturn(Locale.ENGLISH);
        welcomeView.showError(EasyMock.anyString());
        EasyMock.expectLastCall().once();
        EasyMock.replay(welcomeView);

        WelcomeController controller = new WelcomeController();
        controller.setWelcomeView(welcomeView);
        controller.startGame();

        EasyMock.verify(welcomeView);
    }

    @Test
    void StartGame_EmptyPlayer2Name_ShowsErrorAndDoesNotLaunch() {
        final WelcomeView welcomeView = EasyMock.createMock(WelcomeView.class);
        welcomeView.setStartGameAction(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        EasyMock.expect(welcomeView.getPlayer1Name()).andReturn("Alice");
        EasyMock.expect(welcomeView.getPlayer2Name()).andReturn("");
        EasyMock.expect(welcomeView.getSelectedLocale()).andReturn(Locale.ENGLISH);
        welcomeView.showError(EasyMock.anyString());
        EasyMock.expectLastCall().once();
        EasyMock.replay(welcomeView);

        WelcomeController controller = new WelcomeController();
        controller.setWelcomeView(welcomeView);
        controller.startGame();

        EasyMock.verify(welcomeView);
    }

    @Test
    void StartGame_EmptyName_ErrorTextFromEnglishBundle() {
        final WelcomeView welcomeView = EasyMock.createMock(WelcomeView.class);
        welcomeView.setStartGameAction(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        EasyMock.expect(welcomeView.getPlayer1Name()).andReturn("");
        EasyMock.expect(welcomeView.getPlayer2Name()).andReturn("Bob");
        EasyMock.expect(welcomeView.getSelectedLocale()).andReturn(Locale.ENGLISH);
        Capture<String> error = EasyMock.newCapture();
        welcomeView.showError(EasyMock.capture(error));
        EasyMock.expectLastCall().once();
        EasyMock.replay(welcomeView);

        WelcomeController controller = new WelcomeController();
        controller.setWelcomeView(welcomeView);
        controller.startGame();

        assertEquals("Player name cannot be empty", error.getValue());
        EasyMock.verify(welcomeView);
    }

    @Test
    void StartGame_EmptyName_ErrorTextFromSpanishBundle() {
        final WelcomeView welcomeView = EasyMock.createMock(WelcomeView.class);
        welcomeView.setStartGameAction(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        EasyMock.expect(welcomeView.getPlayer1Name()).andReturn("");
        EasyMock.expect(welcomeView.getPlayer2Name()).andReturn("Bob");
        EasyMock.expect(welcomeView.getSelectedLocale()).andReturn(Locale.forLanguageTag("es"));
        Capture<String> error = EasyMock.newCapture();
        welcomeView.showError(EasyMock.capture(error));
        EasyMock.expectLastCall().once();
        EasyMock.replay(welcomeView);

        WelcomeController controller = new WelcomeController();
        controller.setWelcomeView(welcomeView);
        controller.startGame();

        assertEquals("El nombre del jugador no puede estar vacío", error.getValue());
        EasyMock.verify(welcomeView);
    }

    @Test
    void StartGame_NonEmptyNames_ClosesWelcomeView() {
        final WelcomeView welcomeView = EasyMock.createMock(WelcomeView.class);
        welcomeView.setStartGameAction(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        EasyMock.expect(welcomeView.getPlayer1Name()).andReturn("Alice");
        EasyMock.expect(welcomeView.getPlayer2Name()).andReturn("Bob");
        welcomeView.setVisible(false);
        EasyMock.expectLastCall().once();
        welcomeView.dispose();
        EasyMock.expectLastCall().once();
        EasyMock.expect(welcomeView.isChess960Selected()).andReturn(false);
        EasyMock.expect(welcomeView.getSelectedLocale()).andReturn(Locale.ENGLISH);
        EasyMock.replay(welcomeView);

        WelcomeController controller = new WelcomeController();
        controller.setWelcomeView(welcomeView);
        controller.setGameLauncher((p1, p2, init, loc) -> {});
        controller.startGame();

        EasyMock.verify(welcomeView);
    }

    @Test
    void StartGame_NonEmptyNames_InvokesLauncherOnce() {
        final WelcomeView welcomeView = EasyMock.createMock(WelcomeView.class);
        final WelcomeController.GameLauncher launcher =
                EasyMock.createMock(WelcomeController.GameLauncher.class);
        welcomeView.setStartGameAction(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        EasyMock.expect(welcomeView.getPlayer1Name()).andReturn("Alice");
        EasyMock.expect(welcomeView.getPlayer2Name()).andReturn("Bob");
        welcomeView.setVisible(false);
        EasyMock.expectLastCall().once();
        welcomeView.dispose();
        EasyMock.expectLastCall().once();
        EasyMock.expect(welcomeView.isChess960Selected()).andReturn(false);
        EasyMock.expect(welcomeView.getSelectedLocale()).andReturn(Locale.ENGLISH);
        launcher.launch(EasyMock.anyObject(), EasyMock.anyObject(),
                EasyMock.anyObject(), EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        EasyMock.replay(welcomeView, launcher);

        WelcomeController controller = new WelcomeController();
        controller.setWelcomeView(welcomeView);
        controller.setGameLauncher(launcher);
        controller.startGame();

        EasyMock.verify(welcomeView, launcher);
    }
}
