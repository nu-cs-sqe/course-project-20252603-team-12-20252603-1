package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

class QueenTest {

    @Test
    void Constructor_OnWhiteQueen_TypeIsQueen() {
        Queen queen = new Queen(PieceColor.WHITE);

        PieceType expected = PieceType.QUEEN;
        PieceType actual = queen.getType();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnWhiteQueen_ColorIsWhite() {
        Queen queen = new Queen(PieceColor.WHITE);

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = queen.getColor();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnWhiteQueen_CanJumpIsFalse() {
        Queen queen = new Queen(PieceColor.WHITE);

        assertFalse(queen.canJump());
    }

    @Test
    void MakeCopy_OnWhiteQueen_CopyTypeIsQueen() {
        Queen queen = new Queen(PieceColor.WHITE);

        PieceType expected = PieceType.QUEEN;
        PieceType actual = queen.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopy_OnBlackQueen_CopyTypeIsQueen() {
        Queen queen = new Queen(PieceColor.BLACK);

        PieceType expected = PieceType.QUEEN;
        PieceType actual = queen.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopy_OnBlackQueen_CopyColorIsBlack() {
        Queen queen = new Queen(PieceColor.BLACK);

        PieceColor expected = PieceColor.BLACK;
        PieceColor actual = queen.makeCopy().getColor();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopy_OnBlackQueen_CopyCanJumpIsFalse() {
        Queen queen = new Queen(PieceColor.BLACK);

        assertFalse(queen.makeCopy().canJump());
    }

    @Test
    void MakeCopy_OnBlackQueen_CopyIsDifferentObject() {
        Queen queen = new Queen(PieceColor.BLACK);

        assertNotSame(queen, queen.makeCopy());
    }
}
