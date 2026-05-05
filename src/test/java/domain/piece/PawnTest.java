package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class PawnTest {

    @Test
    void Constructor_OnWhitePawn_TypeIsPawn() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        PieceType expected = PieceType.PAWN;
        PieceType actual = pawn.getType();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnWhitePawn_ColorIsWhite() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = pawn.getColor();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnWhitePawn_CanJumpIsFalse() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        assertFalse(pawn.canJump());
    }

    @Test
    void MakeCopy_OnWhitePawn_CopyTypeIsPawn() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        PieceType expected = PieceType.PAWN;
        PieceType actual = pawn.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopy_OnBlackPawn_CopyTypeIsPawn() {
        Pawn pawn = new Pawn(PieceColor.BLACK);

        PieceType expected = PieceType.PAWN;
        PieceType actual = pawn.makeCopy().getType();
        assertEquals(expected, actual);
    }
}
