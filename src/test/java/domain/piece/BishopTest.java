package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import domain.location.Location;
import org.junit.jupiter.api.Test;

class BishopTest {

    @Test
    void ConstructorOnWhiteBishopTypeIsBishop() {
        Bishop bishop = new Bishop(PieceColor.WHITE);

        PieceType expected = PieceType.BISHOP;
        PieceType actual = bishop.getType();
        assertEquals(expected, actual);
    }

    @Test
    void ConstructorOnWhiteBishopColorIsWhite() {
        Bishop bishop = new Bishop(PieceColor.WHITE);

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = bishop.getColor();
        assertEquals(expected, actual);
    }

    @Test
    void ConstructorOnWhiteBishopCanJumpIsFalse() {
        Bishop bishop = new Bishop(PieceColor.WHITE);

        assertFalse(bishop.canJump());
    }

    @Test
    void MakeCopyOnWhiteBishopCopyTypeIsBishop() {
        Bishop bishop = new Bishop(PieceColor.WHITE);

        PieceType expected = PieceType.BISHOP;
        PieceType actual = bishop.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackBishopCopyTypeIsBishop() {
        Bishop bishop = new Bishop(PieceColor.BLACK);

        PieceType expected = PieceType.BISHOP;
        PieceType actual = bishop.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackBishopCopyColorIsBlack() {
        Bishop bishop = new Bishop(PieceColor.BLACK);

        PieceColor expected = PieceColor.BLACK;
        PieceColor actual = bishop.makeCopy().getColor();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackBishopCopyCanJumpIsFalse() {
        Bishop bishop = new Bishop(PieceColor.BLACK);

        assertFalse(bishop.makeCopy().canJump());
    }

    @Test
    void MakeCopyOnBlackBishopCopyIsDifferentObject() {
        Bishop bishop = new Bishop(PieceColor.BLACK);

        assertNotSame(bishop, bishop.makeCopy());
    }

    @Test
    void IsValidMoveShape_OnBishop_DiagonalMoveIsTrue() {
        Bishop bishop = new Bishop(PieceColor.WHITE);

        Location from = new Location(2, 7);
        Location to = new Location(5, 4);

        boolean expected = true;
        boolean actual = bishop.isValidMoveShape(from, to);
        assertEquals(expected, actual);
    }

    @Test
    void IsValidMoveShape_OnBishop_HorizontalMoveIsFalse() {
        Bishop bishop = new Bishop(PieceColor.WHITE);

        Location from = new Location(2, 7);
        Location to = new Location(5, 7);

        boolean expected = false;
        boolean actual = bishop.isValidMoveShape(from, to);
        assertEquals(expected, actual);
    }
}
