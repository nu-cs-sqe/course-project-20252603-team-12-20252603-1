package domain;

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

}
