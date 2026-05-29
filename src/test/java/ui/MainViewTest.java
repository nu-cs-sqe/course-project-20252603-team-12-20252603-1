package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.Board;
import domain.gamestate.GameState;
import domain.piece.NonePiece;
import domain.piece.Piece;
import java.awt.BorderLayout;
import java.awt.Container;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import javax.swing.JFrame;

class MainViewTest {

  private static final int BOARD_SIZE = 8;

  @Test
  void Constructor_OnAliceAndBob_BoardControllerExposesSnapshot() {
    Board boardMock = stubSnapshot(eightByEightGrid());
    MainView view = new MainView("Alice", "Bob", new BoardController(boardMock));

    int expected = BOARD_SIZE;
    int actual = view.getBoardController().getBoardSnapshot().length;
    assertEquals(expected, actual);
    EasyMock.verify(boardMock);
  }

  @Test
  void Constructor_OnAliceAndBob_WiresStatsBoardAndLayout() {
    Board boardMock = replayNiceBoard();
    MainView view = new MainView("Alice", "Bob", new BoardController(boardMock));

    boolean wired =
        view.getGameStatsView() instanceof GameStatsView
            && view.getBoardView() instanceof BoardView
            && "Alice".equals(view.getGameStatsView().getCurrentPlayerLabelText())
            && "Alice vs Bob".equals(view.getGameStatsView().getGameStateLabelText());
    boolean frameConfigured =
      "Chess".equals(view.getTitle())
        && view.getDefaultCloseOperation() == JFrame.EXIT_ON_CLOSE;
    Container contentPane = view.getContentPane();
    BorderLayout layout = (BorderLayout) contentPane.getLayout();
    boolean layoutOk =
        BorderLayout.NORTH.equals(layout.getConstraints(view.getGameStatsView()))
            && BorderLayout.CENTER.equals(layout.getConstraints(view.getBoardView()));
    assertTrue(wired && frameConfigured && layoutOk);
    EasyMock.verify(boardMock);
  }

  @Test
  void Constructor_InitialReadinessFromInjectedController() {
    Board boardMock = EasyMock.createNiceMock(Board.class);
    EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.WHITE_TURN);
    EasyMock.replay(boardMock);
    MainView view = new MainView("Alice", "Bob", new BoardController(boardMock));

    GameState expectedState = GameState.WHITE_TURN;
    GameState actualState = view.getBoardController().getCurrentGameState();
    assertEquals(expectedState, actualState);

    boolean expectedSelection = false;
    boolean actualSelection = view.getBoardController().hasSelection();
    assertEquals(expectedSelection, actualSelection);
    EasyMock.verify(boardMock);
  }

  private static Board replayNiceBoard() {
    Board boardMock = EasyMock.createNiceMock(Board.class);
    EasyMock.replay(boardMock);
    return boardMock;
  }

  private static Board stubSnapshot(Piece[][] snapshot) {
    Board boardMock = EasyMock.createMock(Board.class);
    EasyMock.expect(boardMock.getSnapshot()).andReturn(snapshot);
    EasyMock.replay(boardMock);
    return boardMock;
  }

  private static Piece[][] eightByEightGrid() {
    Piece[][] grid = new Piece[BOARD_SIZE][BOARD_SIZE];
    for (int rank = 0; rank < BOARD_SIZE; rank++) {
      for (int file = 0; file < BOARD_SIZE; file++) {
        grid[rank][file] = new NonePiece();
      }
    }
    return grid;
  }
}
