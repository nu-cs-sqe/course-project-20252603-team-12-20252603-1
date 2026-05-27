package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import domain.location.Location;
import org.junit.jupiter.api.Test;

class PawnTest {

    @Test
    void ConstructorOnWhitePawnTypeIsPawn() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        PieceType expected = PieceType.PAWN;
        PieceType actual = pawn.getType();
        assertEquals(expected, actual);
    }

    @Test
    void ConstructorOnWhitePawnColorIsWhite() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = pawn.getColor();
        assertEquals(expected, actual);
    }

    @Test
    void ConstructorOnWhitePawnCanJumpIsFalse() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        assertFalse(pawn.canJump());
    }

    @Test
    void MakeCopyOnWhitePawnCopyTypeIsPawn() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        PieceType expected = PieceType.PAWN;
        PieceType actual = pawn.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackPawnCopyTypeIsPawn() {
        Pawn pawn = new Pawn(PieceColor.BLACK);

        PieceType expected = PieceType.PAWN;
        PieceType actual = pawn.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackPawnCopyColorIsBlack() {
        Pawn pawn = new Pawn(PieceColor.BLACK);

        PieceColor expected = PieceColor.BLACK;
        PieceColor actual = pawn.makeCopy().getColor();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackPawnCopyCanJumpIsFalse() {
        Pawn pawn = new Pawn(PieceColor.BLACK);

        assertFalse(pawn.makeCopy().canJump());
    }

    @Test
    void MakeCopyOnBlackPawnCopyIsDifferentObject() {
        Pawn pawn = new Pawn(PieceColor.BLACK);

        assertNotSame(pawn, pawn.makeCopy());
    }

    @Test
    void IsValidMoveShape_OnWhitePawn_OneStepForwardIsTrue() {
        Pawn pawn = new Pawn(PieceColor.WHITE);

        Location from = new Location(4, 6);
        Location to = new Location(4, 5);

        boolean expected = true;
        boolean actual = pawn.isValidMoveShape(from, to);
        assertEquals(expected, actual);
    }
}
