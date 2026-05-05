package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

class RookTest {

    @Test
    void Constructor_OnWhiteRook_TypeIsRook() {
        Rook rook = new Rook(PieceColor.WHITE);

        PieceType expected = PieceType.ROOK;
        PieceType actual = rook.getType();
        assertEquals(expected, actual);
    }
}
