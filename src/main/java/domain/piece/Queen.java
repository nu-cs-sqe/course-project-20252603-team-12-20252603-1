package domain.piece;

public class Queen extends Piece {

  public Queen(PieceColor color) {
    super(PieceType.QUEEN, color);
  }

  @Override
  public Piece makeCopy() {
    Queen copy = new Queen(getColor());
    if (hasMoved()) {
      copy.changeToMoved();
    }
    return copy;
  }
}
