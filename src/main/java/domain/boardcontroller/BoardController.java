package domain.boardcontroller;

import domain.Board;
import domain.BoardInitializer;
import domain.Chess960FixedBoardInitializer;
import domain.FischerRandomBoardInitializer;
import domain.StandardBoardInitializer;
import domain.gamestate.GameState;
import domain.location.Location;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import java.util.Optional;
import java.util.Random;

public class BoardController {

  private static final int BOARD_SIZE = 8;

  private final Board board;
  private Optional<Location> lastSelectedLoc;

  public BoardController() {
    this(StartingPositionKind.STANDARD);
  }

  public BoardController(StartingPositionKind startingPositionKind) {
    board = createBoard(startingPositionKind);
    lastSelectedLoc = Optional.empty();
  }

  public BoardController(long chess960Seed) {
    board = new Board(new FischerRandomBoardInitializer(new Random(chess960Seed)));
    lastSelectedLoc = Optional.empty();
  }

  private static Board createBoard(StartingPositionKind startingPositionKind) {
    BoardInitializer initializer =
        startingPositionKind == StartingPositionKind.STANDARD
            ? new StandardBoardInitializer()
            : new Chess960FixedBoardInitializer();
    return new Board(initializer);
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
