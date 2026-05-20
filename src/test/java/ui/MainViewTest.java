package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.Board;
import domain.piece.NonePiece;
import domain.piece.Piece;
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
