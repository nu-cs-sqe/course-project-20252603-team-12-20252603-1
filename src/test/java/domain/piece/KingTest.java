package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

class KingTest {

  @Test
  void constructorOnWhiteKingTypeIsKing() {
    King king = new King(PieceColor.WHITE);

    PieceType expected = PieceType.KING;
    PieceType actual = king.getType();
    assertEquals(expected, actual);
  }

  @Test
  void constructorOnWhiteKingColorIsWhite() {
    King king = new King(PieceColor.WHITE);

    PieceColor expected = PieceColor.WHITE;
    PieceColor actual = king.getColor();
    assertEquals(expected, actual);
  }

  @Test
  void constructorOnWhiteKingCanJumpIsFalse() {
    King king = new King(PieceColor.WHITE);

    assertFalse(king.canJump());
  }

  @Test
  void makeCopyOnWhiteKingCopyTypeIsKing() {
    King king = new King(PieceColor.WHITE);

    PieceType expected = PieceType.KING;
    PieceType actual = king.makeCopy().getType();
    assertEquals(expected, actual);
  }

  @Test
  void makeCopyOnBlackKingCopyTypeIsKing() {
    King king = new King(PieceColor.BLACK);

    PieceType expected = PieceType.KING;
    PieceType actual = king.makeCopy().getType();
    assertEquals(expected, actual);
  }

  @Test
  void makeCopyOnBlackKingCopyColorIsBlack() {
    King king = new King(PieceColor.BLACK);

    PieceColor expected = PieceColor.BLACK;
    PieceColor actual = king.makeCopy().getColor();
    assertEquals(expected, actual);
  }

  @Test
  void makeCopyOnBlackKingCopyCanJumpIsFalse() {
    King king = new King(PieceColor.BLACK);

    assertFalse(king.makeCopy().canJump());
  }

  @Test
  void makeCopyOnBlackKingCopyIsDifferentObject() {
    King king = new King(PieceColor.BLACK);

    assertNotSame(king, king.makeCopy());
  }
}
