package domain.piece;

import domain.location.Location;

public class Queen extends Piece {

  public Queen(PieceColor color) {
    super(PieceType.QUEEN, color);
  }

  @Override
  public Piece makeCopy() {
    return new Queen(getColor());
  }

  @Override
  public boolean isValidMoveShape(Location from, Location to) {
    int rankDelta = to.getY() - from.getY();
    int fileDelta = to.getX() - from.getX();
    return rankDelta == 0 && fileDelta != 0;
  }
}
