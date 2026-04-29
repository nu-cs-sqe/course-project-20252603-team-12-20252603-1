package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}