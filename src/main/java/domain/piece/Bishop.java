package domain.piece;

public class Bishop extends Piece {

  public Bishop(PieceColor color) {
    super(PieceType.BISHOP, color);
  }

  @Override
  public Piece makeCopy() {
    Bishop copy = new Bishop(getColor());
    if (hasMoved()) {
      copy.changeToMoved();
    }
    return copy;
  }
}
