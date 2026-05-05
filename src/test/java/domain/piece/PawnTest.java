package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PawnTest {

    @Test
    void Constructor_OnWhitePawn_TypeIsPawn() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        PieceType expected = PieceType.PAWN;
        PieceType actual = pawn.getType();
        assertEquals(expected, actual);
    }
}
