package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import domain.location.Location;
import org.junit.jupiter.api.Test;

class KingTest {

    @Test
    void ConstructorOnWhiteKingTypeIsKing() {
        King king = new King(PieceColor.WHITE);

        PieceType expected = PieceType.KING;
        PieceType actual = king.getType();
        assertEquals(expected, actual);
    }

    @Test
    void ConstructorOnWhiteKingColorIsWhite() {
        King king = new King(PieceColor.WHITE);

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = king.getColor();
        assertEquals(expected, actual);
    }

    @Test
    void ConstructorOnWhiteKingCanJumpIsFalse() {
        King king = new King(PieceColor.WHITE);

        assertFalse(king.canJump());
    }

    @Test
    void MakeCopyOnWhiteKingCopyTypeIsKing() {
        King king = new King(PieceColor.WHITE);

        PieceType expected = PieceType.KING;
        PieceType actual = king.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackKingCopyTypeIsKing() {
        King king = new King(PieceColor.BLACK);

        PieceType expected = PieceType.KING;
        PieceType actual = king.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackKingCopyColorIsBlack() {
        King king = new King(PieceColor.BLACK);

        PieceColor expected = PieceColor.BLACK;
        PieceColor actual = king.makeCopy().getColor();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackKingCopyCanJumpIsFalse() {
        King king = new King(PieceColor.BLACK);

        assertFalse(king.makeCopy().canJump());
    }

    @Test
    void MakeCopyOnBlackKingCopyIsDifferentObject() {
        King king = new King(PieceColor.BLACK);

        assertNotSame(king, king.makeCopy());
    }

    @Test
    void IsValidMoveShape_OnKing_OneStepDiagonalIsTrue() {
        King king = new King(PieceColor.WHITE);

        Location from = new Location(4, 7);
        Location to = new Location(5, 6);

        boolean expected = true;
        boolean actual = king.isValidMoveShape(from, to);
        assertEquals(expected, actual);
    }

    @Test
    void IsValidMoveShape_OnKing_TwoStepMoveIsFalse() {
        King king = new King(PieceColor.WHITE);

        Location from = new Location(4, 7);
        Location to = new Location(4, 5);

        boolean expected = false;
        boolean actual = king.isValidMoveShape(from, to);
        assertEquals(expected, actual);
    }
}
