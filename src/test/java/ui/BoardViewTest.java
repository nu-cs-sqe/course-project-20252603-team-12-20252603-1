package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

class BoardViewTest {

    @Test
    void Constructor_WithValidController_PreferredWidthIsBoardSizeTimesTileSize() {
        BoardController mockController = EasyMock.createNiceMock(BoardController.class);
        EasyMock.replay(mockController);
        BoardView view = new BoardView(mockController);

        int expected = 600;
        int actual = view.getPreferredSize().width;
        assertEquals(expected, actual);

        EasyMock.verify(mockController);
    }

    @Test
    void Constructor_WithValidController_RegistersExactlyOneMouseListener() {
        BoardController mockController = EasyMock.createNiceMock(BoardController.class);
        EasyMock.replay(mockController);
        BoardView view = new BoardView(mockController);

        int expected = 1;
        int actual = view.getMouseListeners().length;
        assertEquals(expected, actual);

        EasyMock.verify(mockController);
    }
}
