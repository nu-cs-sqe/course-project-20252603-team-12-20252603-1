package ui;

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
}
