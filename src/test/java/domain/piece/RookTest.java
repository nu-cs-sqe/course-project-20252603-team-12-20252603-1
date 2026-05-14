package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

class RookTest {

  @Test
  void constructorOnWhiteRookTypeIsRook() {
    Rook rook = new Rook(PieceColor.WHITE);

    PieceType expected = PieceType.ROOK;
    PieceType actual = rook.getType();
    assertEquals(expected, actual);
  }

  @Test
  void constructorOnWhiteRookColorIsWhite() {
    Rook rook = new Rook(PieceColor.WHITE);

    PieceColor expected = PieceColor.WHITE;
    PieceColor actual = rook.getColor();
    assertEquals(expected, actual);
  }

  @Test
  void constructorOnWhiteRookCanJumpIsFalse() {
    Rook rook = new Rook(PieceColor.WHITE);

    assertFalse(rook.canJump());
  }

  @Test
  void makeCopyOnWhiteRookCopyTypeIsRook() {
    Rook rook = new Rook(PieceColor.WHITE);

    PieceType expected = PieceType.ROOK;
    PieceType actual = rook.makeCopy().getType();
    assertEquals(expected, actual);
  }

  @Test
  void makeCopyOnBlackRookCopyTypeIsRook() {
    Rook rook = new Rook(PieceColor.BLACK);

    PieceType expected = PieceType.ROOK;
    PieceType actual = rook.makeCopy().getType();
    assertEquals(expected, actual);
  }

  @Test
  void makeCopyOnBlackRookCopyColorIsBlack() {
    Rook rook = new Rook(PieceColor.BLACK);

    PieceColor expected = PieceColor.BLACK;
    PieceColor actual = rook.makeCopy().getColor();
    assertEquals(expected, actual);
  }

  @Test
  void makeCopyOnBlackRookCopyCanJumpIsFalse() {
    Rook rook = new Rook(PieceColor.BLACK);

    assertFalse(rook.makeCopy().canJump());
  }

  @Test
  void makeCopyOnBlackRookCopyIsDifferentObject() {
    Rook rook = new Rook(PieceColor.BLACK);

    assertNotSame(rook, rook.makeCopy());
  }
}
