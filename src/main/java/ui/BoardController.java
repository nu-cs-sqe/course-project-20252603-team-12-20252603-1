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

  private final Board board;
  private BoardView boardView;
  private Optional<Location> lastSelectedLoc;

  public BoardController(Board board) {
    this.board = board;
    lastSelectedLoc = Optional.empty();
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
    if (lastSelectedLoc.isPresent()) {
      boolean moved = board.movePiece(lastSelectedLoc.get(), loc);
      if (moved) {
        board.switchTurn();
        lastSelectedLoc = Optional.empty();
        repaintBoardView();
      }
      return;
    }
    int file = loc.getX();
    int rank = loc.getY();
    Piece at = board.getPieceAt(rank, file);
    if (at.getType() == PieceType.NONE || board.getCurrentGameState() != GameState.WHITE_TURN) {
      return;
    }
    if (at.getColor() != PieceColor.WHITE) {
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
