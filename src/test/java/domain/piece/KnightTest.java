package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.location.Location;
import org.junit.jupiter.api.Test;

class KnightTest {

    @Test
    void ConstructorOnWhiteKnightTypeIsKnight() {
        Knight knight = new Knight(PieceColor.WHITE);

        PieceType expected = PieceType.KNIGHT;
        PieceType actual = knight.getType();

        assertEquals(expected, actual);
    }

    @Test
    void ConstructorOnWhiteKnightColorIsWhite() {
        Knight knight = new Knight(PieceColor.WHITE);

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = knight.getColor();

        assertEquals(expected, actual);
    }

    @Test
    void ConstructorOnWhiteKnightCanJumpIsTrue() {
        Knight knight = new Knight(PieceColor.WHITE);

        assertTrue(knight.canJump());
    }

    @Test
    void MakeCopyOnWhiteKnightCopyTypeIsKnight() {
        Knight knight = new Knight(PieceColor.WHITE);

        PieceType expected = PieceType.KNIGHT;
        PieceType actual = knight.makeCopy().getType();

        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackKnightCopyTypeIsKnight() {
        Knight knight = new Knight(PieceColor.BLACK);

        PieceType expected = PieceType.KNIGHT;
        PieceType actual = knight.makeCopy().getType();

        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackKnightCopyColorIsBlack() {
        Knight knight = new Knight(PieceColor.BLACK);

        PieceColor expected = PieceColor.BLACK;
        PieceColor actual = knight.makeCopy().getColor();

        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackKnightCopyCanJumpIsTrue() {
        Knight knight = new Knight(PieceColor.BLACK);

        assertTrue(knight.makeCopy().canJump());
    }

    @Test
    void MakeCopyOnBlackKnightCopyIsDifferentObject() {
        Knight knight = new Knight(PieceColor.BLACK);

        assertNotSame(knight, knight.makeCopy());
    }

    @Test
    void IsValidMoveShape_OnKnight_TwoOneMoveIsTrue() {
        Knight knight = new Knight(PieceColor.WHITE);

        Location from = new Location(6, 7);
        Location to = new Location(5, 5);

        boolean expected = true;
        boolean actual = knight.isValidMoveShape(from, to);
        assertEquals(expected, actual);
    }

    @Test
    void IsValidMoveShape_OnKnight_OneTwoMoveIsTrue() {
        Knight knight = new Knight(PieceColor.WHITE);

        Location from = new Location(6, 7);
        Location to = new Location(4, 6);

        boolean expected = true;
        boolean actual = knight.isValidMoveShape(from, to);
        assertEquals(expected, actual);
    }
}
