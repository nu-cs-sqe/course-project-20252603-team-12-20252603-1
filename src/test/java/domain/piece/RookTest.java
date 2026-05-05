package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

class RookTest {

    @Test
    void Constructor_OnWhiteRook_TypeIsRook() {
        Rook rook = new Rook(PieceColor.WHITE);

        PieceType expected = PieceType.ROOK;
        PieceType actual = rook.getType();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnWhiteRook_ColorIsWhite() {
        Rook rook = new Rook(PieceColor.WHITE);

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = rook.getColor();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnWhiteRook_CanJumpIsFalse() {
        Rook rook = new Rook(PieceColor.WHITE);

        assertFalse(rook.canJump());
    }

    @Test
    void MakeCopy_OnWhiteRook_CopyTypeIsRook() {
        Rook rook = new Rook(PieceColor.WHITE);

        PieceType expected = PieceType.ROOK;
        PieceType actual = rook.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopy_OnBlackRook_CopyTypeIsRook() {
        Rook rook = new Rook(PieceColor.BLACK);

        PieceType expected = PieceType.ROOK;
        PieceType actual = rook.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopy_OnBlackRook_CopyColorIsBlack() {
        Rook rook = new Rook(PieceColor.BLACK);

        PieceColor expected = PieceColor.BLACK;
        PieceColor actual = rook.makeCopy().getColor();
        assertEquals(expected, actual);
    }
}
