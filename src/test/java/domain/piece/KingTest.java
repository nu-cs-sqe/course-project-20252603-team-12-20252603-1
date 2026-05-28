package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

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
}
