package domain.piece;

import domain.location.Location;

public class Bishop extends Piece {

  public Bishop(PieceColor color) {
    super(PieceType.BISHOP, color);
  }

  @Override
  public Piece makeCopy() {
    return new Bishop(getColor());
  }

  @Override
  public boolean isValidMoveShape(Location from, Location to) {
    int absRankDelta = Math.abs(to.getY() - from.getY());
    int absFileDelta = Math.abs(to.getX() - from.getX());
    return absRankDelta != 0 && absRankDelta == absFileDelta;
  }
}
