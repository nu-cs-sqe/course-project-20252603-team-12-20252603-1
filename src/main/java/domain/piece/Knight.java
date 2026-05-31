package domain.piece;

public class Knight extends Piece {

  public Knight(PieceColor color) {
    super(PieceType.KNIGHT, color);
  }

  @Override
  public Piece makeCopy() {
    Knight copy = new Knight(getColor());
    if (hasMoved()) {
      copy.changeToMoved();
    }
    return copy;
  }
}
