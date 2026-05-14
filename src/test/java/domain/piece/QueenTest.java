package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

class QueenTest {

  @Test
  void constructorOnWhiteQueenTypeIsQueen() {
    Queen queen = new Queen(PieceColor.WHITE);

    PieceType expected = PieceType.QUEEN;
    PieceType actual = queen.getType();
    assertEquals(expected, actual);
  }

  @Test
  void constructorOnWhiteQueenColorIsWhite() {
    Queen queen = new Queen(PieceColor.WHITE);

    PieceColor expected = PieceColor.WHITE;
    PieceColor actual = queen.getColor();
    assertEquals(expected, actual);
  }

  @Test
  void constructorOnWhiteQueenCanJumpIsFalse() {
    Queen queen = new Queen(PieceColor.WHITE);

    assertFalse(queen.canJump());
  }

  @Test
  void makeCopyOnWhiteQueenCopyTypeIsQueen() {
    Queen queen = new Queen(PieceColor.WHITE);

    PieceType expected = PieceType.QUEEN;
    PieceType actual = queen.makeCopy().getType();
    assertEquals(expected, actual);
  }

  @Test
  void makeCopyOnBlackQueenCopyTypeIsQueen() {
    Queen queen = new Queen(PieceColor.BLACK);

    PieceType expected = PieceType.QUEEN;
    PieceType actual = queen.makeCopy().getType();
    assertEquals(expected, actual);
  }

  @Test
  void makeCopyOnBlackQueenCopyColorIsBlack() {
    Queen queen = new Queen(PieceColor.BLACK);

    PieceColor expected = PieceColor.BLACK;
    PieceColor actual = queen.makeCopy().getColor();
    assertEquals(expected, actual);
  }

  @Test
  void makeCopyOnBlackQueenCopyCanJumpIsFalse() {
    Queen queen = new Queen(PieceColor.BLACK);

    assertFalse(queen.makeCopy().canJump());
  }

  @Test
  void makeCopyOnBlackQueenCopyIsDifferentObject() {
    Queen queen = new Queen(PieceColor.BLACK);

    assertNotSame(queen, queen.makeCopy());
  }
}
