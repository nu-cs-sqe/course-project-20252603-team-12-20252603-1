package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class KnightTest {

    @Test
    void Constructor_OnWhiteKnight_TypeIsKnight() {
        Knight knight = new Knight(PieceColor.WHITE);

        assertEquals(PieceType.KNIGHT, knight.getType());
    }

    @Test
    void Constructor_OnWhiteKnight_ColorIsWhite() {
        Knight knight = new Knight(PieceColor.WHITE);

        assertEquals(PieceColor.WHITE, knight.getColor());
    }
}
