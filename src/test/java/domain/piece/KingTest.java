package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

class KingTest {

    @Test
    void Constructor_OnWhiteKing_TypeIsKing() {
        King king = new King(PieceColor.WHITE);

        PieceType expected = PieceType.KING;
        PieceType actual = king.getType();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnWhiteKing_ColorIsWhite() {
        King king = new King(PieceColor.WHITE);

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = king.getColor();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnWhiteKing_CanJumpIsFalse() {
        King king = new King(PieceColor.WHITE);

        assertFalse(king.canJump());
    }

    @Test
    void MakeCopy_OnWhiteKing_CopyTypeIsKing() {
        King king = new King(PieceColor.WHITE);

        PieceType expected = PieceType.KING;
        PieceType actual = king.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopy_OnBlackKing_CopyTypeIsKing() {
        King king = new King(PieceColor.BLACK);

        PieceType expected = PieceType.KING;
        PieceType actual = king.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopy_OnBlackKing_CopyColorIsBlack() {
        King king = new King(PieceColor.BLACK);

        PieceColor expected = PieceColor.BLACK;
        PieceColor actual = king.makeCopy().getColor();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopy_OnBlackKing_CopyCanJumpIsFalse() {
        King king = new King(PieceColor.BLACK);

        assertFalse(king.makeCopy().canJump());
    }

    @Test
    void MakeCopy_OnBlackKing_CopyIsDifferentObject() {
        King king = new King(PieceColor.BLACK);

        assertNotSame(king, king.makeCopy());
    }
}