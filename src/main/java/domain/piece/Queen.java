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
    int absRankDelta = Math.abs(rankDelta);
    int absFileDelta = Math.abs(fileDelta);
    boolean isHorizontalMove = rankDelta == 0 && fileDelta != 0;
    boolean isVerticalMove = fileDelta == 0 && rankDelta != 0;
    boolean isDiagonalMove = absRankDelta != 0 && absRankDelta == absFileDelta;
    return isHorizontalMove || isVerticalMove || isDiagonalMove;
  }
}
