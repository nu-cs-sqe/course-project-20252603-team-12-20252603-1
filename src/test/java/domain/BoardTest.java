package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void GetSnapshot_ReturnedOuterArrayIsDifferentObject() {
        Board board = new Board();

        Piece[][] snapshot1 = board.getSnapshot();
        Piece[][] snapshot2 = board.getSnapshot();
        assertNotSame(snapshot1, snapshot2);
    }

    @Test
    void GetSnapshot_ReturnedRowArrayIsDifferentObject() {
        Board board = new Board();

        Piece[] row1 = board.getSnapshot()[0];
        Piece[] row2 = board.getSnapshot()[0];
        assertNotSame(row1, row2);
    }

    @Test
    void GetSnapshot_SnapshotContentMatchesBoardState() {
        Board board = new Board();

        Piece piece = board.getSnapshot()[7][4];
        assertEquals(PieceType.KING, piece.getType());
        assertEquals(PieceColor.WHITE, piece.getColor());
    }

    @Test
    void GetSnapshot_ModifySnapshotDoesNotAffectBoard() {
        Board board = new Board();

        Piece[][] snapshot = board.getSnapshot();
        snapshot[7][0] = null;
        Piece piece = board.getSnapshot()[7][0];
        assertEquals(PieceType.KNIGHT, piece.getType());
        assertEquals(PieceColor.WHITE, piece.getColor());
    }

    @Test
    void GetSnapshot_ReturnedPieceIsDifferentObjectWithSameContents() {
        Board board = new Board();

        Piece piece1 = board.getSnapshot()[0][0];
        Piece piece2 = board.getSnapshot()[0][0];
        assertNotSame(piece1, piece2);
        assertEquals(piece1.getType(), piece2.getType());
        assertEquals(piece1.getColor(), piece2.getColor());
    }

}
