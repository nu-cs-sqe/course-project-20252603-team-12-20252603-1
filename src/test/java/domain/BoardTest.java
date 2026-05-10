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

    @ParameterizedTest
    @CsvSource({
            "0, 0, ROOK",
            "0, 1, KNIGHT",
            "0, 2, BISHOP",
            "0, 3, QUEEN",
            "0, 4, KING",
            "0, 5, BISHOP",
            "0, 6, KNIGHT",
            "0, 7, ROOK",
            "1, 0, PAWN",
            "1, 1, PAWN",
            "1, 2, PAWN",
            "1, 3, PAWN",
            "1, 4, PAWN",
            "1, 5, PAWN",
            "1, 6, PAWN",
            "1, 7, PAWN",
            "3, 0, NONE",
            "6, 0, PAWN",
            "6, 1, PAWN",
            "6, 2, PAWN",
            "6, 3, PAWN",
            "6, 4, PAWN",
            "6, 5, PAWN",
            "6, 6, PAWN",
            "6, 7, PAWN",
            "7, 0, ROOK",
            "7, 1, KNIGHT",
            "7, 2, BISHOP",
            "7, 3, QUEEN",
            "7, 4, KING",
            "7, 5, BISHOP",
            "7, 6, KNIGHT",
            "7, 7, ROOK"
    })
    void Constructor_OnNewBoard_PieceTypeAtPositionIsCorrect(int row, int col, PieceType expectedType) {
        Board board = new Board();
        assertEquals(expectedType, board.getSnapshot()[row][col].getType());
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, BLACK",
            "7, 0, WHITE"
    })
    void Constructor_OnNewBoard_PieceColorAtPositionIsCorrect(int row, int col, PieceColor expectedColor) {
        Board board = new Board();
        assertEquals(expectedColor, board.getSnapshot()[row][col].getColor());
    }

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
