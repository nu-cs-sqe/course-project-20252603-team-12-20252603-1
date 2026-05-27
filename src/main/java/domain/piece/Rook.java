package domain.piece;

import domain.location.Location;

public class Rook extends Piece {

  public Rook(PieceColor color) {
    super(PieceType.ROOK, color);
  }

  @Override
  public Piece makeCopy() {
    return new Rook(getColor());
  }

  @Override
  public boolean isValidMoveShape(Location from, Location to) {
    int rankDelta = to.getY() - from.getY();
    int fileDelta = to.getX() - from.getX();
    return rankDelta == 0 && fileDelta != 0;
  }
}
