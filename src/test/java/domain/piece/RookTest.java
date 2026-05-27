package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import domain.location.Location;
import org.junit.jupiter.api.Test;

class RookTest {

    @Test
    void ConstructorOnWhiteRookTypeIsRook() {
        Rook rook = new Rook(PieceColor.WHITE);

        PieceType expected = PieceType.ROOK;
        PieceType actual = rook.getType();
        assertEquals(expected, actual);
    }

    @Test
    void ConstructorOnWhiteRookColorIsWhite() {
        Rook rook = new Rook(PieceColor.WHITE);

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = rook.getColor();
        assertEquals(expected, actual);
    }

    @Test
    void ConstructorOnWhiteRookCanJumpIsFalse() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertFalse(rook.canJump());
    }

    @Test
    void MakeCopyOnWhiteRookCopyTypeIsRook() {
        Rook rook = new Rook(PieceColor.WHITE);

        PieceType expected = PieceType.ROOK;
        PieceType actual = rook.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackRookCopyTypeIsRook() {
        Rook rook = new Rook(PieceColor.BLACK);

        PieceType expected = PieceType.ROOK;
        PieceType actual = rook.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackRookCopyColorIsBlack() {
        Rook rook = new Rook(PieceColor.BLACK);

        PieceColor expected = PieceColor.BLACK;
        PieceColor actual = rook.makeCopy().getColor();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackRookCopyCanJumpIsFalse() {
        Rook rook = new Rook(PieceColor.BLACK);

        assertFalse(rook.makeCopy().canJump());
    }

    @Test
    void MakeCopyOnBlackRookCopyIsDifferentObject() {
        Rook rook = new Rook(PieceColor.BLACK);

        assertNotSame(rook, rook.makeCopy());
    }

    @Test
    void IsValidMoveShape_OnRook_HorizontalMoveIsTrue() {
        Rook rook = new Rook(PieceColor.WHITE);

        Location from = new Location(0, 7);
        Location to = new Location(3, 7);

        boolean expected = true;
        boolean actual = rook.isValidMoveShape(from, to);
        assertEquals(expected, actual);
    }

    @Test
    void IsValidMoveShape_OnRook_VerticalMoveIsTrue() {
        Rook rook = new Rook(PieceColor.WHITE);

        Location from = new Location(0, 7);
        Location to = new Location(0, 4);

        boolean expected = true;
        boolean actual = rook.isValidMoveShape(from, to);
        assertEquals(expected, actual);
    }
}
