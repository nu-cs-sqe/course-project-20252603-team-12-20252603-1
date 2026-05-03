package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class KnightTest {

    @Test
    void Constructor_OnWhiteKnight_TypeIsKnight() {
        Knight knight = new Knight(PieceColor.WHITE);

        PieceType expected = PieceType.KNIGHT;
        PieceType actual = knight.getType();

        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnWhiteKnight_ColorIsWhite() {
        Knight knight = new Knight(PieceColor.WHITE);

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = knight.getColor();

        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnWhiteKnight_CanJumpIsTrue() {
        Knight knight = new Knight(PieceColor.WHITE);

        assertTrue(knight.canJump());
    }

    @Test
    void MakeCopy_OnWhiteKnight_CopyTypeIsKnight() {
        Knight knight = new Knight(PieceColor.WHITE);

        PieceType expected = PieceType.KNIGHT;
        PieceType actual = knight.makeCopy().getType();

        assertEquals(expected, actual);
    }

    @Test
    void MakeCopy_OnBlackKnight_CopyTypeIsKnight() {
        Knight knight = new Knight(PieceColor.BLACK);

        PieceType expected = PieceType.KNIGHT;
        PieceType actual = knight.makeCopy().getType();

        assertEquals(expected, actual);
    }

    @Test
    void MakeCopy_OnBlackKnight_CopyColorIsBlack() {
        Knight knight = new Knight(PieceColor.BLACK);

        PieceColor expected = PieceColor.BLACK;
        PieceColor actual = knight.makeCopy().getColor();

        assertEquals(expected, actual);
    }

    @Test
    void MakeCopy_OnBlackKnight_CopyCanJumpIsTrue() {
        Knight knight = new Knight(PieceColor.BLACK);

        assertTrue(knight.makeCopy().canJump());
    }

    @Test
    void MakeCopy_OnBlackKnight_CopyIsDifferentObject() {
        Knight knight = new Knight(PieceColor.BLACK);

        assertNotSame(knight, knight.makeCopy());
    }
}
