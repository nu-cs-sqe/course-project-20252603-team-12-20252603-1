package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.awt.Window;
import java.util.Locale;
import java.util.function.Consumer;
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
    void Show_HidesMainView() {
        final JFrame mainView = EasyMock.createMock(JFrame.class);
        mainView.setVisible(false);
        EasyMock.expectLastCall().once();
        EasyMock.replay(mainView);

        EndGameController controller = new EndGameController("", mainView, Locale.ENGLISH);
        controller.show();

        EasyMock.verify(mainView);
    }

    @Test
    void Show_DisplaysEndGameView() {
        final JFrame mainView = EasyMock.createMock(JFrame.class);
        mainView.setVisible(false);
        EasyMock.expectLastCall().once();
        EasyMock.replay(mainView);

        EndGameController controller =
                new EndGameController("Alice wins!", mainView, Locale.ENGLISH);
        controller.show();

        boolean expected = true;
        boolean actual = controller.getEndGameView().isVisible();
        assertEquals(expected, actual);
        EasyMock.verify(mainView);
    }

    @Test
    void SetEndGameView_WhenCalled_WiresPlayAgainAction() {
        final JFrame mainView = EasyMock.createMock(JFrame.class);
        final EndGameView endGameView = EasyMock.createMock(EndGameView.class);
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
        final JFrame mainView = EasyMock.createMock(JFrame.class);
        final EndGameView endGameView = EasyMock.createMock(EndGameView.class);
        endGameView.setPlayAgainAction(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        EasyMock.replay(mainView, endGameView);

        EndGameController controller = new EndGameController("", mainView, Locale.ENGLISH);
        controller.setEndGameView(endGameView);

        assertSame(endGameView, controller.getEndGameView());
        EasyMock.verify(mainView, endGameView);
    }

    @Test
    void PlayAgain_WhenCalled_DisposesEndGameView() {
        final JFrame mainView = EasyMock.createMock(JFrame.class);
        final EndGameView endGameView = EasyMock.createMock(EndGameView.class);
        endGameView.setPlayAgainAction(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        endGameView.dispose();
        EasyMock.expectLastCall().once();
        EasyMock.replay(mainView, endGameView);

        EndGameController controller = new EndGameController("", mainView, Locale.ENGLISH);
        controller.setEndGameView(endGameView);
        controller.setPlayAgainAction(loc -> {});
        controller.playAgain();

        EasyMock.verify(mainView, endGameView);
    }

    @Test
    void PlayAgain_OnEnglishLocale_InvokesActionWithEnglishLocale() {
        final JFrame mainView = EasyMock.createMock(JFrame.class);
        final EndGameView endGameView = EasyMock.createMock(EndGameView.class);
        @SuppressWarnings("unchecked")
        final Consumer<Locale> playAgainAction = EasyMock.createMock(Consumer.class);
        endGameView.setPlayAgainAction(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        endGameView.dispose();
        EasyMock.expectLastCall().once();
        Capture<Locale> localeCapture = EasyMock.newCapture();
        playAgainAction.accept(EasyMock.capture(localeCapture));
        EasyMock.expectLastCall().once();
        EasyMock.replay(mainView, endGameView, playAgainAction);

        EndGameController controller = new EndGameController("", mainView, Locale.ENGLISH);
        controller.setEndGameView(endGameView);
        controller.setPlayAgainAction(playAgainAction);
        controller.playAgain();

        assertEquals(Locale.ENGLISH, localeCapture.getValue());
        EasyMock.verify(mainView, endGameView, playAgainAction);
    }

    @Test
    void PlayAgain_OnSpanishLocale_InvokesActionWithSpanishLocale() {
        final JFrame mainView = EasyMock.createMock(JFrame.class);
        final EndGameView endGameView = EasyMock.createMock(EndGameView.class);
        @SuppressWarnings("unchecked")
        final Consumer<Locale> playAgainAction = EasyMock.createMock(Consumer.class);
        endGameView.setPlayAgainAction(EasyMock.anyObject());
        EasyMock.expectLastCall().once();
        endGameView.dispose();
        EasyMock.expectLastCall().once();
        Capture<Locale> localeCapture = EasyMock.newCapture();
        playAgainAction.accept(EasyMock.capture(localeCapture));
        EasyMock.expectLastCall().once();
        EasyMock.replay(mainView, endGameView, playAgainAction);

        EndGameController controller =
                new EndGameController("", mainView, Locale.forLanguageTag("es"));
        controller.setEndGameView(endGameView);
        controller.setPlayAgainAction(playAgainAction);
        controller.playAgain();

        assertEquals(Locale.forLanguageTag("es"), localeCapture.getValue());
        EasyMock.verify(mainView, endGameView, playAgainAction);
    }
}
