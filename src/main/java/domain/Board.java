package domain;

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

public class Board {

  private static final int BOARD_SIZE = 8;

  private final Piece[][] pieces = new Piece[BOARD_SIZE][BOARD_SIZE];
  private GameState currentGameState = GameState.WHITE_TURN;

  public Board(BoardInitializer initializer) {
    PieceType[][] layout = initializer.getBoardLayout();
    for (int row = 0; row < BOARD_SIZE; row++) {
      for (int col = 0; col < BOARD_SIZE; col++) {
        pieces[row][col] = createPiece(layout[row][col], colorForRow(row));
      }
    }
  }

  private static PieceColor colorForRow(int row) {
    if (row < 2) {
      return PieceColor.WHITE;
    }
    if (row > 5) {
      return PieceColor.BLACK;
    }
    return PieceColor.WHITE;
  }

  private static Piece createPiece(PieceType type, PieceColor color) {
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

  public Piece getPieceAt(int rank, int file) {
    return pieces[rank][file];
  }

  public GameState getCurrentGameState() {
    return currentGameState;
  }

  public Piece[][] getSnapshot() {
    Piece[][] snapshot = new Piece[BOARD_SIZE][BOARD_SIZE];
    for (int row = 0; row < BOARD_SIZE; row++) {
      for (int col = 0; col < BOARD_SIZE; col++) {
        snapshot[row][col] = pieces[row][col].makeCopy();
      }
    }
    return snapshot;
  }
}
