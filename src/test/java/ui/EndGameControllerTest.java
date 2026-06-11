package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.awt.Window;
import java.util.Locale;
import javax.swing.JFrame;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class EndGameControllerTest {

    @AfterEach
    void disposeOpenUiWindows() {
        for (Window window : Window.getWindows()) {
            if (window instanceof WelcomeView || window instanceof EndGameView) {
                window.dispose();
            }
        }
    }

    @Test
    void Constructor_WithEmptyResultMessage_ShowHidesMainView() {
        JFrame mainView = EasyMock.createMock(JFrame.class);
        mainView.setVisible(false);
        EasyMock.expectLastCall().once();
        EasyMock.replay(mainView);

        EndGameController controller = new EndGameController("", mainView, Locale.ENGLISH);
        controller.show();

        EasyMock.verify(mainView);
    }

    @Test
    void Constructor_WithNonEmptyResultMessage_ShowHidesMainViewAndDisplaysEndGameView() {
        JFrame mainView = EasyMock.createMock(JFrame.class);
        mainView.setVisible(false);
        EasyMock.expectLastCall().once();
        EasyMock.replay(mainView);

        EndGameController controller = new EndGameController("Alice wins!", mainView, Locale.ENGLISH);
        controller.show();

        boolean expected = true;
        boolean actual = controller.getEndGameView().isVisible();
        assertEquals(expected, actual);
        EasyMock.verify(mainView);
    }

    @Test
    void SetEndGameView_WhenCalled_WiresPlayAgainAction() {
        JFrame mainView = EasyMock.createMock(JFrame.class);
        EndGameView endGameView = EasyMock.createMock(EndGameView.class);
        Capture<Runnable> action = EasyMock.newCapture();
        endGameView.setPlayAgainAction(EasyMock.capture(action));
        EasyMock.expectLastCall().once();
        EasyMock.replay(mainView, endGameView);

        EndGameController controller = new EndGameController("", mainView, Locale.ENGLISH);
        controller.setEndGameView(endGameView);

        EasyMock.verify(mainView, endGameView);
        assertNotNull(action.getValue());
    }

    @Test
    void GetEndGameView_AfterSetEndGameView_ReturnsSameView() {
        JFrame mainView = EasyMock.createMock(JFrame.class);
        EndGameView endGameView = EasyMock.createMock(EndGameView.class);
        endGameView.setPlayAgainAction(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        EasyMock.replay(mainView, endGameView);

        EndGameController controller = new EndGameController("", mainView, Locale.ENGLISH);
        controller.setEndGameView(endGameView);

        assertSame(endGameView, controller.getEndGameView());
        EasyMock.verify(mainView, endGameView);
    }

    @Test
    void PlayAgain_WhenShowHasBeenCalled_EndGameViewIsDisposed() {
        JFrame mainView = EasyMock.createMock(JFrame.class);
        mainView.setVisible(false);
        EasyMock.expectLastCall().once();
        EasyMock.replay(mainView);

        EndGameController controller = new EndGameController("Alice wins!", mainView, Locale.ENGLISH);
        controller.show();
        controller.getEndGameView().clickPlayAgain();

        boolean expected = false;
        boolean actual = controller.getEndGameView().isDisplayable();
        assertEquals(expected, actual);
        EasyMock.verify(mainView);
    }

    @Test
    void PlayAgain_WhenShowHasBeenCalled_WelcomeViewIsVisible() {
        JFrame mainView = EasyMock.createMock(JFrame.class);
        mainView.setVisible(false);
        EasyMock.expectLastCall().once();
        EasyMock.replay(mainView);

        EndGameController controller = new EndGameController("Alice wins!", mainView, Locale.ENGLISH);
        controller.show();
        controller.getEndGameView().clickPlayAgain();

        boolean expected = true;
        boolean actual = isAnyWindowOfTypeVisible(WelcomeView.class);
        assertEquals(expected, actual);
        EasyMock.verify(mainView);
    }

    @Test
    void PlayAgain_OnSpanishLocale_WelcomeViewTitleFromSpanishBundle() {
        JFrame mainView = EasyMock.createMock(JFrame.class);
        mainView.setVisible(false);
        EasyMock.expectLastCall().once();
        EasyMock.replay(mainView);

        EndGameController controller = new EndGameController(
                "¡Alice gana!", mainView, Locale.forLanguageTag("es"));
        controller.show();
        controller.getEndGameView().clickPlayAgain();

        String expected = "Ajedrez";
        String actual = findVisibleWelcomeViewTitle();
        assertEquals(expected, actual);
        EasyMock.verify(mainView);
    }

    private static boolean isAnyWindowOfTypeVisible(Class<?> type) {
        for (Window window : Window.getWindows()) {
            if (type.isInstance(window) && window.isVisible()) {
                return true;
            }
        }
        return false;
    }

    private static String findVisibleWelcomeViewTitle() {
        for (Window window : Window.getWindows()) {
            if (window instanceof WelcomeView && window.isVisible()) {
                WelcomeView welcomeView = (WelcomeView) window;
                return welcomeView.getTitle();
            }
        }
        return "";
    }
}
