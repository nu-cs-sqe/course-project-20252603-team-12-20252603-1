package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class NonePieceTest {

    @Test
    void Constructor_OnNonePiece_TypeIsNone() {
        NonePiece nonePiece = new NonePiece();

        PieceType expected = PieceType.NONE;
        PieceType actual = nonePiece.getType();

        assertEquals(expected, actual);
    }
}
