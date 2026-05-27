package domain.piece;

import domain.location.Location;

public class Pawn extends Piece {

  public Pawn(PieceColor color) {
    super(PieceType.PAWN, color);
  }

  @Override
  public Piece makeCopy() {
    return new Pawn(getColor());
  }

  @Override
  public boolean isValidMoveShape(Location from, Location to) {
    int rankDelta = to.getY() - from.getY();
    int fileDelta = to.getX() - from.getX();
    int forward = getColor() == PieceColor.WHITE ? -1 : 1;
    return fileDelta == 0 && rankDelta == forward;
  }
}
