package domain.piece;

import domain.location.Location;

public class Knight extends Piece {

  public Knight(PieceColor color) {
    super(PieceType.KNIGHT, color);
  }

  @Override
  public Piece makeCopy() {
    return new Knight(getColor());
  }

  @Override
  public boolean isValidMoveShape(Location from, Location to) {
    int absRankDelta = Math.abs(to.getY() - from.getY());
    int absFileDelta = Math.abs(to.getX() - from.getX());
    return absRankDelta == 2 && absFileDelta == 1;
  }
}
