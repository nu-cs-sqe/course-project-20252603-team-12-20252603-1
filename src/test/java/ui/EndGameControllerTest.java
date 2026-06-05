package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.JFrame;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

class EndGameControllerTest {

    @Test
    void Constructor_WithEmptyResultMessage_ShowHidesMainView() {
        JFrame mainView = EasyMock.createMock(JFrame.class);
        mainView.setVisible(false);
        EasyMock.expectLastCall().once();
        EasyMock.replay(mainView);

        EndGameController controller = new EndGameController("", mainView);
        controller.show();

        EasyMock.verify(mainView);
    }

    @Test
    void Constructor_WithNonEmptyResultMessage_ShowHidesMainViewAndDisplaysEndGameView() {
        JFrame mainView = EasyMock.createMock(JFrame.class);
        mainView.setVisible(false);
        EasyMock.expectLastCall().once();
        EasyMock.replay(mainView);

        EndGameController controller = new EndGameController("Alice wins!", mainView);
        controller.show();

        boolean expected = true;
        boolean actual = controller.getEndGameView().isVisible();
        assertEquals(expected, actual);
        EasyMock.verify(mainView);
    }
}
