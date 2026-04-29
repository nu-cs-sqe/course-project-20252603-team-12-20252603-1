package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

class KingTest {

    @Test
    void Constructor_OnWhiteKing_TypeIsKing() {
        King king = new King(PieceColor.WHITE);

        assertEquals(PieceType.KING, king.getType());
    }

    @Test
    void Constructor_OnWhiteKing_ColorIsWhite() {
        King king = new King(PieceColor.WHITE);

        assertEquals(PieceColor.WHITE, king.getColor());
    }

    @Test
    void Constructor_OnWhiteKing_CanJumpIsFalse() {
        King king = new King(PieceColor.WHITE);

        assertFalse(king.canJump());
    }

    @Test
    void MakeCopy_OnWhiteKing_CopyTypeIsKing() {
        King king = new King(PieceColor.WHITE);

        assertEquals(PieceType.KING, king.makeCopy().getType());
    }

    @Test
    void MakeCopy_OnBlackKing_CopyTypeIsKing() {
        King king = new King(PieceColor.BLACK);

        assertEquals(PieceType.KING, king.makeCopy().getType());
    }

    @Test
    void MakeCopy_OnBlackKing_CopyColorIsBlack() {
        King king = new King(PieceColor.BLACK);

        assertEquals(PieceColor.BLACK, king.makeCopy().getColor());
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