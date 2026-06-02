package ui;

import domain.Board;
import domain.gamestate.GameState;
import domain.location.Location;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import java.util.Optional;

public class BoardController {

  private static final int BOARD_SIZE = 8;

  private final String player1Name;
  private final String player2Name;
  private final Board board;
  private MainView mainView;
  private BoardView boardView;
  private Optional<Location> lastSelectedLoc;

  public BoardController(String player1Name, String player2Name, Board board) {
    this.player1Name = player1Name;
    this.player2Name = player2Name;
    this.board = board;
    lastSelectedLoc = Optional.empty();
  }

  public BoardController(Board board) {
    this("", "", board);
  }

  public void show() {
    mainView = new MainView(player1Name, player2Name, this);
    mainView.setVisible(true);
    updateCurrentPlayerLabel();
  }

  private void updateCurrentPlayerLabel() {
    if (mainView == null) {
      return;
    }
    String text = board.getCurrentGameState() == GameState.WHITE_TURN
        ? player1Name
        : player2Name;
    mainView.getGameStatsView().updateCurrentPlayerLabel(text);
  }

  MainView getMainView() {
    return mainView;
  }

  public void setBoardView(BoardView boardView) {
    this.boardView = boardView;
  }

  BoardView getBoardView() {
    return boardView;
  }

  public boolean hasSelection() {
    return lastSelectedLoc.isPresent();
  }

  public Optional<Location> getSelectedLocation() {
    return lastSelectedLoc;
  }

  public void handleSquareClick(Location loc) {
    if (!isInBounds(loc)) {
      return;
    }
    GameState state = board.getCurrentGameState();
    if (state != GameState.WHITE_TURN && state != GameState.BLACK_TURN) {
      return;
    }
    PieceColor currentColor =
        (state == GameState.WHITE_TURN) ? PieceColor.WHITE : PieceColor.BLACK;
    handleSourceClick(loc, currentColor);
  }

  private void handleSourceClick(Location loc, PieceColor currentColor) {
    int file = loc.getX();
    int rank = loc.getY();
    Piece at = board.getPieceAt(rank, file);
    if (at.getType() == PieceType.NONE || at.getColor() != currentColor) {
      return;
    }
    lastSelectedLoc = Optional.of(loc);
    repaintBoardView();
  }

  private void repaintBoardView() {
    if (boardView != null) {
      boardView.repaint();
    }
  }

  private static boolean isInBounds(Location loc) {
    int x = loc.getX();
    int y = loc.getY();
    return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
  }

  public Piece[][] getBoardSnapshot() {
    return board.getSnapshot();
  }

  public GameState getCurrentGameState() {
    return board.getCurrentGameState();
  }
}
