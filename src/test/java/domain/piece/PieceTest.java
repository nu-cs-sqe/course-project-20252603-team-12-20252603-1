package domain.piece;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PieceTest {

    @Test
    void IsSameColor_OnSameColorPieces_ReturnsTrue() {
        Piece piece1 = new Pawn(PieceColor.WHITE);
        Piece piece2 = new Pawn(PieceColor.WHITE);

        assertTrue(piece1.isSameColor(piece2));
    }
}
