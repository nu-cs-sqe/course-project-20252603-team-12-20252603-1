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

class MainViewTest {

  @Test
  void Constructor_OnAliceAndBobStandardMode_BoardControllerWired() {
    Board boardMock = stubSnapshot(eightByEightGrid());
    MainView view = new MainView("Alice", "Bob", GameStartMode.STANDARD, boardMock);

    int expected = 8;
    int actual = view.getBoardController().getBoardSnapshot().length;
    assertEquals(expected, actual);
    EasyMock.verify(boardMock);
  }

  @Test
  void Constructor_OnAliceAndBobFischerRandomMode_BoardViewWired() {
    Board boardMock = replayNiceBoard();
    MainView view = new MainView("Alice", "Bob", GameStartMode.FISCHER_RANDOM, boardMock);

    boolean actual = view.getBoardView() instanceof BoardView;
    assertTrue(actual);
    EasyMock.verify(boardMock);
  }

  @Test
  void Constructor_OnAliceAndBobStandardMode_GameStatsViewWired() {
    Board boardMock = replayNiceBoard();
    MainView view = new MainView("Alice", "Bob", GameStartMode.STANDARD, boardMock);

    boolean actual = view.getGameStatsView() instanceof GameStatsView;
    assertTrue(actual);
    EasyMock.verify(boardMock);
  }

  @Test
  void Constructor_OnAliceAndBobStandardMode_CurrentPlayerLabelIsAlice() {
    Board boardMock = replayNiceBoard();
    MainView view = new MainView("Alice", "Bob", GameStartMode.STANDARD, boardMock);

    String expected = "Alice";
    String actual = view.getGameStatsView().getCurrentPlayerLabelText();
    assertEquals(expected, actual);
    EasyMock.verify(boardMock);
  }

  @Test
  void Constructor_OnAliceAndBobFischerRandomMode_MatchupLabelIsAliceVsBob() {
    Board boardMock = replayNiceBoard();
    MainView view = new MainView("Alice", "Bob", GameStartMode.FISCHER_RANDOM, boardMock);

    String expected = "Alice vs Bob";
    String actual = view.getGameStatsView().getGameStateLabelText();
    assertEquals(expected, actual);
    EasyMock.verify(boardMock);
  }

  @Test
  void Constructor_OnAliceAndBobStandardMode_ContentPaneHasBoardViewAndGameStatsView() {
    Board boardMock = replayNiceBoard();
    MainView view = new MainView("Alice", "Bob", GameStartMode.STANDARD, boardMock);

    Container contentPane = view.getContentPane();
    BorderLayout layout = (BorderLayout) contentPane.getLayout();
    boolean actual =
        BorderLayout.NORTH.equals(layout.getConstraints(view.getGameStatsView()))
            && BorderLayout.CENTER.equals(layout.getConstraints(view.getBoardView()));
    assertTrue(actual);
    EasyMock.verify(boardMock);
  }

  @Test
  void Constructor_OnAliceAndBobFischerRandomMode_RegisteredBoardViewSameInstance() {
    Board boardMock = replayNiceBoard();
    MainView view = new MainView("Alice", "Bob", GameStartMode.FISCHER_RANDOM, boardMock);

    BoardView expected = view.getBoardView();
    BoardView actual = view.getRegisteredBoardView();
    assertEquals(expected, actual);
    EasyMock.verify(boardMock);
  }

  @Test
  void Constructor_StandardMode_CurrentGameStateWhiteTurn() {
    Board boardMock = EasyMock.createNiceMock(Board.class);
    EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.WHITE_TURN);
    EasyMock.replay(boardMock);
    MainView view = new MainView("Alice", "Bob", GameStartMode.STANDARD, boardMock);

    GameState expected = GameState.WHITE_TURN;
    GameState actual = view.getBoardController().getCurrentGameState();
    assertEquals(expected, actual);
    EasyMock.verify(boardMock);
  }

  @Test
  void Constructor_StandardMode_HasSelectionFalse() {
    Board boardMock = replayNiceBoard();
    MainView view = new MainView("Alice", "Bob", GameStartMode.STANDARD, boardMock);

    boolean expected = false;
    boolean actual = view.getBoardController().hasSelection();
    assertEquals(expected, actual);
    EasyMock.verify(boardMock);
  }

  @Test
  void Constructor_FischerRandomMode_CurrentGameStateWhiteTurn() {
    Board boardMock = EasyMock.createNiceMock(Board.class);
    EasyMock.expect(boardMock.getCurrentGameState()).andReturn(GameState.WHITE_TURN);
    EasyMock.replay(boardMock);
    MainView view = new MainView("Alice", "Bob", GameStartMode.FISCHER_RANDOM, boardMock);

    GameState expected = GameState.WHITE_TURN;
    GameState actual = view.getBoardController().getCurrentGameState();
    assertEquals(expected, actual);
    EasyMock.verify(boardMock);
  }

  @Test
  void Constructor_FischerRandomMode_HasSelectionFalse() {
    Board boardMock = replayNiceBoard();
    MainView view = new MainView("Alice", "Bob", GameStartMode.FISCHER_RANDOM, boardMock);

    boolean expected = false;
    boolean actual = view.getBoardController().hasSelection();
    assertEquals(expected, actual);
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
    Piece[][] grid = new Piece[8][8];
    for (int rank = 0; rank < 8; rank++) {
      for (int file = 0; file < 8; file++) {
        grid[rank][file] = new NonePiece();
      }
    }
    return grid;
  }
}
