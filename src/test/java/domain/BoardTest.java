package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import domain.piece.Piece;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void GetSnapshot_OnFreshBoard_OuterArrayIsDifferentObject() {
        Board board = new Board();

        Piece[][] snapshot1 = board.getSnapshot();
        Piece[][] snapshot2 = board.getSnapshot();
        assertNotSame(snapshot1, snapshot2);
    }

    @Test
    void GetSnapshot_OnFreshBoard_RowArrayIsDifferentObject() {
        Board board = new Board();

        Piece[] row1 = board.getSnapshot()[0];
        Piece[] row2 = board.getSnapshot()[0];
        assertNotSame(row1, row2);
    }

    @Test
    void GetSnapshot_OnFreshBoard_PieceIsDifferentObjectWithSameContents() {
        Board board = new Board();

        Piece piece1 = board.getSnapshot()[0][0];
        Piece piece2 = board.getSnapshot()[0][0];
        assertNotSame(piece1, piece2);
        assertEquals(piece1.getType(), piece2.getType());
        assertEquals(piece1.getColor(), piece2.getColor());
    }

}
