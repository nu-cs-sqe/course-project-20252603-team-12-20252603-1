package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

class QueenTest {

    @Test
    void ConstructorOnWhiteQueenTypeIsQueen() {
        Queen queen = new Queen(PieceColor.WHITE);

        PieceType expected = PieceType.QUEEN;
        PieceType actual = queen.getType();
        assertEquals(expected, actual);
    }

    @Test
    void ConstructorOnWhiteQueenColorIsWhite() {
        Queen queen = new Queen(PieceColor.WHITE);

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = queen.getColor();
        assertEquals(expected, actual);
    }

    @Test
    void ConstructorOnWhiteQueenCanJumpIsFalse() {
        Queen queen = new Queen(PieceColor.WHITE);

        assertFalse(queen.canJump());
    }

    @Test
    void MakeCopyOnWhiteQueenCopyTypeIsQueen() {
        Queen queen = new Queen(PieceColor.WHITE);

        PieceType expected = PieceType.QUEEN;
        PieceType actual = queen.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackQueenCopyTypeIsQueen() {
        Queen queen = new Queen(PieceColor.BLACK);

        PieceType expected = PieceType.QUEEN;
        PieceType actual = queen.makeCopy().getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackQueenCopyColorIsBlack() {
        Queen queen = new Queen(PieceColor.BLACK);

        PieceColor expected = PieceColor.BLACK;
        PieceColor actual = queen.makeCopy().getColor();
        assertEquals(expected, actual);
    }

    @Test
    void MakeCopyOnBlackQueenCopyCanJumpIsFalse() {
        Queen queen = new Queen(PieceColor.BLACK);

        assertFalse(queen.makeCopy().canJump());
    }

    @Test
    void MakeCopyOnBlackQueenCopyIsDifferentObject() {
        Queen queen = new Queen(PieceColor.BLACK);

        assertNotSame(queen, queen.makeCopy());
    }
}
