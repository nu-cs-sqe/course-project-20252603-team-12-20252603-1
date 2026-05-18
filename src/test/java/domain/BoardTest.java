package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.gamestate.GameState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardTest {

    @Test
    void GetCurrentGameState_AfterSwitchTurn_ReturnsBlackTurn() {
        Board board = new Board();
        board.switchTurn();
        assertEquals(GameState.BLACK_TURN, board.getCurrentGameState());
    }

    @Test
    void Constructor_OnNewBoard_GameStateIsWhiteTurn() {
        Board board = new Board();
        assertEquals(GameState.WHITE_TURN, board.getCurrentGameState());
    }

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
        assertEquals(PieceType.ROOK, piece.getType());
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

    @Test
    void SwitchTurn_FromBlackTurn_GameStateIsWhiteTurn() {
        Board board = new Board();
        board.switchTurn();
        board.switchTurn();
        assertEquals(GameState.WHITE_TURN, board.getCurrentGameState());
    }
}
