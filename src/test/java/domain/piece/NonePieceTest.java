package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import domain.location.Location;
import org.junit.jupiter.api.Test;

class NonePieceTest {

    @Test
    void Constructor_OnNonePiece_TypeIsNone() {
        NonePiece nonePiece = new NonePiece();

        PieceType expected = PieceType.NONE;
        PieceType actual = nonePiece.getType();

        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnNonePiece_CanJumpIsFalse() {
        NonePiece nonePiece = new NonePiece();

        assertFalse(nonePiece.canJump());
    }

    @Test
    void MakeCopy_OnNonePiece_CopyTypeIsNone() {
        NonePiece nonePiece = new NonePiece();

        PieceType expected = PieceType.NONE;
        PieceType actual = nonePiece.makeCopy().getType();

        assertEquals(expected, actual);
    }

    @Test
    void MakeCopy_OnNonePiece_CopyCanJumpIsFalse() {
        NonePiece nonePiece = new NonePiece();

        assertFalse(nonePiece.makeCopy().canJump());
    }

    @Test
    void MakeCopy_OnNonePiece_CopyIsDifferentObject() {
        NonePiece nonePiece = new NonePiece();

        assertNotSame(nonePiece, nonePiece.makeCopy());
    }

    @Test
    void IsValidMoveShape_OnNonePiece_AnyMoveIsFalse() {
        NonePiece nonePiece = new NonePiece();

        Location from = new Location(0, 0);
        Location to = new Location(0, 1);

        boolean expected = false;
        boolean actual = nonePiece.isValidMoveShape(from, to);
        assertEquals(expected, actual);
    }
}
