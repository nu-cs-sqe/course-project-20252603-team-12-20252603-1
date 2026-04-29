package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
}