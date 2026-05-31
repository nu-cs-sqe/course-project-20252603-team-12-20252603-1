package domain.piece;

public class Rook extends Piece {

  public Rook(PieceColor color) {
    super(PieceType.ROOK, color);
  }

  @Override
  public Piece makeCopy() {
    Rook copy = new Rook(getColor());
    if (hasMoved()) {
      copy.changeToMoved();
    }
    return copy;
  }
}
