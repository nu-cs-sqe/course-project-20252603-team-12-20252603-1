package domain;

import static org.junit.jupiter.api.Assertions.assertNotSame;

import domain.piece.Piece;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void GetSnapshot_ReturnedOuterArrayIsDifferentObject() {
        Board board = new Board();

        Piece[][] snapshot1 = board.getSnapshot();
        Piece[][] snapshot2 = board.getSnapshot();
        assertNotSame(snapshot1, snapshot2);
    }

}
