package domain.piece;

public class Pawn extends Piece {

  public Pawn(PieceColor color) {
    super(PieceType.PAWN, color);
  }

  @Override
  public Piece makeCopy() {
    Pawn copy = new Pawn(getColor());
    if (hasMoved()) {
      copy.changeToMoved();
    }
    return copy;
  }
}
