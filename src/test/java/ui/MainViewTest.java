package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.Board;
import domain.gamestate.GameState;
import domain.piece.Bishop;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.NonePiece;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Queen;
import domain.piece.Rook;
import java.awt.Container;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    boolean hasBoardView = containsInstance(contentPane, BoardView.class);
    boolean hasGameStatsView = containsInstance(contentPane, GameStatsView.class);
    boolean actual = hasBoardView && hasGameStatsView;
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
  void Constructor_StandardMode_SnapshotMatchesStandardInitializer() {
    Piece[][] standardGrid = newStandardStartingGrid();
    Board boardMock = stubSnapshot(standardGrid);
    MainView view = new MainView("Alice", "Bob", GameStartMode.STANDARD, boardMock);

    Piece[][] expected = standardGrid;
    Piece[][] actual = view.getBoardController().getBoardSnapshot();
    boolean matches = cellWiseSameTypeAndColor(expected, actual);
    assertTrue(matches);
    EasyMock.verify(boardMock);
  }

  @Test
  void Constructor_FischerRandomMode_SnapshotMatchesFischerRandomInitializer() {
    Piece[][] chess960Grid = newChess960SeedOneGrid();
    Board boardMock = stubSnapshot(chess960Grid);
    MainView view = new MainView("Alice", "Bob", GameStartMode.FISCHER_RANDOM, boardMock);

    Piece[][] expected = chess960Grid;
    Piece[][] actual = view.getBoardController().getBoardSnapshot();
    boolean matches = cellWiseSameTypeAndColor(expected, actual);
    assertTrue(matches);
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

  private static boolean containsInstance(Container container, Class<?> type) {
    for (Component component : container.getComponents()) {
      if (type.isInstance(component)) {
        return true;
      }
    }
    return false;
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

  private static Piece[][] newChess960SeedOneGrid() {
    PieceType[] backRank = generateChess960BackRankTypes(new Random(1L));
    Piece[][] grid = eightByEightGrid();
    placeBackRankPieces(grid, 0, PieceColor.WHITE, backRank);
    placeBackRankPieces(grid, 7, PieceColor.BLACK, backRank);
    for (int file = 0; file < 8; file++) {
      grid[1][file] = new Pawn(PieceColor.WHITE);
      grid[6][file] = new Pawn(PieceColor.BLACK);
    }
    return grid;
  }

  private static PieceType[] generateChess960BackRankTypes(Random rng) {
    PieceType[] byFile = new PieceType[8];
    for (int file = 0; file < 8; file++) {
      byFile[file] = PieceType.NONE;
    }

    int[] lightFiles = {0, 2, 4, 6};
    byFile[lightFiles[rng.nextInt(4)]] = PieceType.BISHOP;

    int[] darkFiles = {1, 3, 5, 7};
    byFile[darkFiles[rng.nextInt(4)]] = PieceType.BISHOP;

    List<Integer> remaining = new ArrayList<>();
    for (int file = 0; file < 8; file++) {
      if (byFile[file] == PieceType.NONE) {
        remaining.add(file);
      }
    }

    int queenIdx = rng.nextInt(3);
    byFile[remaining.get(queenIdx)] = PieceType.QUEEN;
    remaining.remove(queenIdx);

    int knight1Idx = rng.nextInt(4);
    byFile[remaining.get(knight1Idx)] = PieceType.KNIGHT;
    remaining.remove(knight1Idx);

    int knight2Idx = rng.nextInt(3);
    byFile[remaining.get(knight2Idx)] = PieceType.KNIGHT;
    remaining.remove(knight2Idx);

    byFile[remaining.get(0)] = PieceType.ROOK;
    byFile[remaining.get(1)] = PieceType.KING;
    byFile[remaining.get(2)] = PieceType.ROOK;

    return byFile;
  }

  private static void placeBackRankPieces(
      Piece[][] grid, int rank, PieceColor color, PieceType[] typesByFile) {
    for (int file = 0; file < 8; file++) {
      grid[rank][file] = pieceFromType(typesByFile[file], color);
    }
  }

  private static Piece pieceFromType(PieceType type, PieceColor color) {
    switch (type) {
      case ROOK:
        return new Rook(color);
      case KNIGHT:
        return new Knight(color);
      case BISHOP:
        return new Bishop(color);
      case QUEEN:
        return new Queen(color);
      case KING:
        return new King(color);
      case PAWN:
        return new Pawn(color);
      default:
        return new NonePiece();
    }
  }

  private static boolean cellWiseSameTypeAndColor(Piece[][] expected, Piece[][] actual) {
    for (int rank = 0; rank < 8; rank++) {
      for (int file = 0; file < 8; file++) {
        Piece e = expected[rank][file];
        Piece a = actual[rank][file];
        if (e.getType() != a.getType() || e.getColor() != a.getColor()) {
          return false;
        }
      }
    }
    return true;
  }

  private static Piece[][] newStandardStartingGrid() {
    Piece[][] grid = eightByEightGrid();
    grid[0][0] = new Rook(PieceColor.WHITE);
    grid[0][1] = new Knight(PieceColor.WHITE);
    grid[0][2] = new Bishop(PieceColor.WHITE);
    grid[0][3] = new Queen(PieceColor.WHITE);
    grid[0][4] = new King(PieceColor.WHITE);
    grid[0][5] = new Bishop(PieceColor.WHITE);
    grid[0][6] = new Knight(PieceColor.WHITE);
    grid[0][7] = new Rook(PieceColor.WHITE);
    for (int file = 0; file < 8; file++) {
      grid[1][file] = new Pawn(PieceColor.WHITE);
    }
    for (int file = 0; file < 8; file++) {
      grid[6][file] = new Pawn(PieceColor.BLACK);
    }
    grid[7][0] = new Rook(PieceColor.BLACK);
    grid[7][1] = new Knight(PieceColor.BLACK);
    grid[7][2] = new Bishop(PieceColor.BLACK);
    grid[7][3] = new Queen(PieceColor.BLACK);
    grid[7][4] = new King(PieceColor.BLACK);
    grid[7][5] = new Bishop(PieceColor.BLACK);
    grid[7][6] = new Knight(PieceColor.BLACK);
    grid[7][7] = new Rook(PieceColor.BLACK);
    return grid;
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
