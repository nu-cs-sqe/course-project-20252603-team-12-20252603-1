package domain.piece;

import domain.location.Location;

public class King extends Piece {

  public King(PieceColor color) {
    super(PieceType.KING, color);
  }

  @Override
  public Piece makeCopy() {
    return new King(getColor());
  }

  @Override
  public boolean isValidMoveShape(Location from, Location to) {
    int absRankDelta = Math.abs(to.getY() - from.getY());
    int absFileDelta = Math.abs(to.getX() - from.getX());
    return absRankDelta <= 1 && absFileDelta <= 1
        && (absRankDelta != 0 || absFileDelta != 0);
  }
}
