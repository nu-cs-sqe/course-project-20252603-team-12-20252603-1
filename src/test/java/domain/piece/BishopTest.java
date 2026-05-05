package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BishopTest {

    @Test
    void Constructor_OnWhiteBishop_TypeIsBishop() {
        Bishop bishop = new Bishop(PieceColor.WHITE);

        PieceType expected = PieceType.BISHOP;
        PieceType actual = bishop.getType();
        assertEquals(expected, actual);
    }
}
