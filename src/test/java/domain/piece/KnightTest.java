package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class KnightTest {

  @Test
  void constructorOnWhiteKnightTypeIsKnight() {
    Knight knight = new Knight(PieceColor.WHITE);

    PieceType expected = PieceType.KNIGHT;
    PieceType actual = knight.getType();

    assertEquals(expected, actual);
  }

  @Test
  void constructorOnWhiteKnightColorIsWhite() {
    Knight knight = new Knight(PieceColor.WHITE);

    PieceColor expected = PieceColor.WHITE;
    PieceColor actual = knight.getColor();

    assertEquals(expected, actual);
  }

  @Test
  void constructorOnWhiteKnightCanJumpIsTrue() {
    Knight knight = new Knight(PieceColor.WHITE);

    assertTrue(knight.canJump());
  }

  @Test
  void makeCopyOnWhiteKnightCopyTypeIsKnight() {
    Knight knight = new Knight(PieceColor.WHITE);

    PieceType expected = PieceType.KNIGHT;
    PieceType actual = knight.makeCopy().getType();

    assertEquals(expected, actual);
  }

  @Test
  void makeCopyOnBlackKnightCopyTypeIsKnight() {
    Knight knight = new Knight(PieceColor.BLACK);

    PieceType expected = PieceType.KNIGHT;
    PieceType actual = knight.makeCopy().getType();

    assertEquals(expected, actual);
  }

  @Test
  void makeCopyOnBlackKnightCopyColorIsBlack() {
    Knight knight = new Knight(PieceColor.BLACK);

    PieceColor expected = PieceColor.BLACK;
    PieceColor actual = knight.makeCopy().getColor();

    assertEquals(expected, actual);
  }

  @Test
  void makeCopyOnBlackKnightCopyCanJumpIsTrue() {
    Knight knight = new Knight(PieceColor.BLACK);

    assertTrue(knight.makeCopy().canJump());
  }

  @Test
  void makeCopyOnBlackKnightCopyIsDifferentObject() {
    Knight knight = new Knight(PieceColor.BLACK);

    assertNotSame(knight, knight.makeCopy());
  }
}
