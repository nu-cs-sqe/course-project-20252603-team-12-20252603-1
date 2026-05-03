package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class KnightTest {

    @Test
    void Constructor_OnWhiteKnight_TypeIsKnight() {
        Knight knight = new Knight(PieceColor.WHITE);

        assertEquals(PieceType.KNIGHT, knight.getType());
    }

    @Test
    void Constructor_OnWhiteKnight_ColorIsWhite() {
        Knight knight = new Knight(PieceColor.WHITE);

        assertEquals(PieceColor.WHITE, knight.getColor());
    }

    @Test
    void Constructor_OnWhiteKnight_CanJumpIsTrue() {
        Knight knight = new Knight(PieceColor.WHITE);

        assertTrue(knight.canJump());
    }

    @Test
    void MakeCopy_OnWhiteKnight_CopyTypeIsKnight() {
        Knight knight = new Knight(PieceColor.WHITE);

        assertEquals(PieceType.KNIGHT, knight.makeCopy().getType());
    }

    @Test
    void MakeCopy_OnBlackKnight_CopyTypeIsKnight() {
        Knight knight = new Knight(PieceColor.BLACK);

        assertEquals(PieceType.KNIGHT, knight.makeCopy().getType());
    }

    @Test
    void MakeCopy_OnBlackKnight_CopyColorIsBlack() {
        Knight knight = new Knight(PieceColor.BLACK);

        assertEquals(PieceColor.BLACK, knight.makeCopy().getColor());
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
