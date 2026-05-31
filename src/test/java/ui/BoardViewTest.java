package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.location.Location;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

class BoardViewTest {

    @Test
    void MouseClicked_AtBottomPixel_CallsHandleSquareClickWithRankZero() {
        BoardController mockController = EasyMock.createMock(BoardController.class);
        Capture<Location> cap = EasyMock.newCapture();
        mockController.handleSquareClick(EasyMock.capture(cap));
        EasyMock.expectLastCall().once();
        EasyMock.replay(mockController);

        BoardView view = new BoardView(mockController);
        MouseListener listener = view.getMouseListeners()[0];
        MouseEvent event = new MouseEvent(view, MouseEvent.MOUSE_CLICKED, 0, 0, 0, 599, 1, false);
        listener.mouseClicked(event);

        int expected = 0;
        int actual = cap.getValue().getY();
        assertEquals(expected, actual);

        EasyMock.verify(mockController);
    }

    @Test
    void MouseClicked_AtFirstPixelOfSecondTileRow_CallsHandleSquareClickWithRankSix() {
        BoardController mockController = EasyMock.createMock(BoardController.class);
        Capture<Location> cap = EasyMock.newCapture();
        mockController.handleSquareClick(EasyMock.capture(cap));
        EasyMock.expectLastCall().once();
        EasyMock.replay(mockController);

        BoardView view = new BoardView(mockController);
        MouseListener listener = view.getMouseListeners()[0];
        MouseEvent event = new MouseEvent(view, MouseEvent.MOUSE_CLICKED, 0, 0, 0, 75, 1, false);
        listener.mouseClicked(event);

        int expected = 6;
        int actual = cap.getValue().getY();
        assertEquals(expected, actual);

        EasyMock.verify(mockController);
    }

    @Test
    void MouseClicked_AtLastPixelOfTopTileRow_CallsHandleSquareClickWithRankSeven() {
        BoardController mockController = EasyMock.createMock(BoardController.class);
        Capture<Location> cap = EasyMock.newCapture();
        mockController.handleSquareClick(EasyMock.capture(cap));
        EasyMock.expectLastCall().once();
        EasyMock.replay(mockController);

        BoardView view = new BoardView(mockController);
        MouseListener listener = view.getMouseListeners()[0];
        MouseEvent event = new MouseEvent(view, MouseEvent.MOUSE_CLICKED, 0, 0, 0, 74, 1, false);
        listener.mouseClicked(event);

        int expected = 7;
        int actual = cap.getValue().getY();
        assertEquals(expected, actual);

        EasyMock.verify(mockController);
    }

    @Test
    void MouseClicked_AtTopPixel_CallsHandleSquareClickWithRankSeven() {
        BoardController mockController = EasyMock.createMock(BoardController.class);
        Capture<Location> cap = EasyMock.newCapture();
        mockController.handleSquareClick(EasyMock.capture(cap));
        EasyMock.expectLastCall().once();
        EasyMock.replay(mockController);

        BoardView view = new BoardView(mockController);
        MouseListener listener = view.getMouseListeners()[0];
        MouseEvent event = new MouseEvent(view, MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, 1, false);
        listener.mouseClicked(event);

        int expected = 7;
        int actual = cap.getValue().getY();
        assertEquals(expected, actual);

        EasyMock.verify(mockController);
    }

    @Test
    void MouseClicked_AtLastPixelOfBoard_CallsHandleSquareClickWithFileSeven() {
        BoardController mockController = EasyMock.createMock(BoardController.class);
        Capture<Location> cap = EasyMock.newCapture();
        mockController.handleSquareClick(EasyMock.capture(cap));
        EasyMock.expectLastCall().once();
        EasyMock.replay(mockController);

        BoardView view = new BoardView(mockController);
        MouseListener listener = view.getMouseListeners()[0];
        MouseEvent event = new MouseEvent(view, MouseEvent.MOUSE_CLICKED, 0, 0, 599, 599, 1, false);
        listener.mouseClicked(event);

        int expected = 7;
        int actual = cap.getValue().getX();
        assertEquals(expected, actual);

        EasyMock.verify(mockController);
    }

    @Test
    void MouseClicked_AtFirstPixelOfSecondTile_CallsHandleSquareClickWithFileOne() {
        BoardController mockController = EasyMock.createMock(BoardController.class);
        Capture<Location> cap = EasyMock.newCapture();
        mockController.handleSquareClick(EasyMock.capture(cap));
        EasyMock.expectLastCall().once();
        EasyMock.replay(mockController);

        BoardView view = new BoardView(mockController);
        MouseListener listener = view.getMouseListeners()[0];
        MouseEvent event = new MouseEvent(view, MouseEvent.MOUSE_CLICKED, 0, 0, 75, 0, 1, false);
        listener.mouseClicked(event);

        int expected = 1;
        int actual = cap.getValue().getX();
        assertEquals(expected, actual);

        EasyMock.verify(mockController);
    }

    @Test
    void MouseClicked_AtLastPixelOfFirstTile_CallsHandleSquareClickWithFileZero() {
        BoardController mockController = EasyMock.createMock(BoardController.class);
        Capture<Location> cap = EasyMock.newCapture();
        mockController.handleSquareClick(EasyMock.capture(cap));
        EasyMock.expectLastCall().once();
        EasyMock.replay(mockController);

        BoardView view = new BoardView(mockController);
        MouseListener listener = view.getMouseListeners()[0];
        MouseEvent event = new MouseEvent(view, MouseEvent.MOUSE_CLICKED, 0, 0, 74, 0, 1, false);
        listener.mouseClicked(event);

        int expected = 0;
        int actual = cap.getValue().getX();
        assertEquals(expected, actual);

        EasyMock.verify(mockController);
    }

    @Test
    void MousePressed_AtLeftmostPixel_CallsHandleSquareClickWithFileZero() {
        BoardController mockController = EasyMock.createMock(BoardController.class);
        Capture<Location> cap = EasyMock.newCapture();
        mockController.handleSquareClick(EasyMock.capture(cap));
        EasyMock.expectLastCall().once();
        EasyMock.replay(mockController);

        BoardView view = new BoardView(mockController);
        MouseListener listener = view.getMouseListeners()[0];
        MouseEvent event = new MouseEvent(view, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 1, false);
        listener.mousePressed(event);

        int expected = 0;
        int actual = cap.getValue().getX();
        assertEquals(expected, actual);

        EasyMock.verify(mockController);
    }

    @Test
    void Constructor_WithValidController_PreferredHeightIsBoardSizeTimesTileSize() {
        BoardController mockController = EasyMock.createNiceMock(BoardController.class);
        EasyMock.replay(mockController);
        BoardView view = new BoardView(mockController);

        int expected = 600;
        int actual = view.getPreferredSize().height;
        assertEquals(expected, actual);

        EasyMock.verify(mockController);
    }

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
