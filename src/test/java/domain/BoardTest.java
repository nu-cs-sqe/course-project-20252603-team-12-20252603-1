package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import domain.gamestate.GameState;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import java.util.Arrays;
import org.easymock.EasyMock;
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
            "1, 0, PAWN",
            "3, 0, NONE"
    })
    void Constructor_WhenInitializerHasPieceTypeAtPosition_PieceTypeMatches(
            int row, int col, PieceType expectedType) {
        PieceType[][] layout = new PieceType[8][8];
        for (PieceType[] r : layout) {
            Arrays.fill(r, PieceType.NONE);
        }
        layout[row][col] = expectedType;
        BoardInitializer initializer = EasyMock.createMock(BoardInitializer.class);
        EasyMock.expect(initializer.getBoardLayout()).andReturn(layout);
        EasyMock.replay(initializer);
        Board board = new Board(initializer);
        assertEquals(expectedType, board.getSnapshot()[row][col].getType());
        EasyMock.verify(initializer);
    }

    @Test
    void Constructor_WhenInitializerHasNonNoneTypeInTopHalf_PieceColorIsBlack() {
        PieceType[][] layout = new PieceType[8][8];
        for (PieceType[] r : layout) {
            Arrays.fill(r, PieceType.NONE);
        }
        layout[0][0] = PieceType.ROOK;
        BoardInitializer initializer = EasyMock.createMock(BoardInitializer.class);
        EasyMock.expect(initializer.getBoardLayout()).andReturn(layout);
        EasyMock.replay(initializer);
        Board board = new Board(initializer);
        assertEquals(PieceColor.BLACK, board.getSnapshot()[0][0].getColor());
        EasyMock.verify(initializer);
    }

    @Test
    void Constructor_WhenInitializerHasNonNoneTypeInBottomHalf_PieceColorIsWhite() {
        PieceType[][] layout = new PieceType[8][8];
        for (PieceType[] r : layout) {
            Arrays.fill(r, PieceType.NONE);
        }
        layout[7][0] = PieceType.ROOK;
        BoardInitializer initializer = EasyMock.createMock(BoardInitializer.class);
        EasyMock.expect(initializer.getBoardLayout()).andReturn(layout);
        EasyMock.replay(initializer);
        Board board = new Board(initializer);
        assertEquals(PieceColor.WHITE, board.getSnapshot()[7][0].getColor());
        EasyMock.verify(initializer);
    }

    @Test
    void Constructor_OnNewBoard_GameStateIsWhiteTurn() {
        Board board = new Board(new StandardBoardInitializer());
        assertEquals(GameState.WHITE_TURN, board.getCurrentGameState());
    }

    @Test
    void GetCurrentGameState_AfterSwitchTurn_ReturnsBlackTurn() {
        Board board = new Board(new StandardBoardInitializer());
        board.switchTurn();
        assertEquals(GameState.BLACK_TURN, board.getCurrentGameState());
    }

    @Test
    void GetSnapshot_ReturnedOuterArrayIsDifferentObject() {
        Board board = new Board(new StandardBoardInitializer());
        Piece[][] snapshot1 = board.getSnapshot();
        Piece[][] snapshot2 = board.getSnapshot();
        assertNotSame(snapshot1, snapshot2);
    }

    @Test
    void GetSnapshot_ReturnedRowArrayIsDifferentObject() {
        Board board = new Board(new StandardBoardInitializer());
        Piece[] row1 = board.getSnapshot()[0];
        Piece[] row2 = board.getSnapshot()[0];
        assertNotSame(row1, row2);
    }

    @Test
    void GetSnapshot_SnapshotContentMatchesBoardState() {
        Board board = new Board(new StandardBoardInitializer());
        Piece piece = board.getSnapshot()[7][4];
        assertEquals(PieceType.KING, piece.getType());
        assertEquals(PieceColor.WHITE, piece.getColor());
    }

    @Test
    void GetSnapshot_ModifySnapshotDoesNotAffectBoard() {
        Board board = new Board(new StandardBoardInitializer());
        Piece[][] snapshot = board.getSnapshot();
        snapshot[7][0] = null;
        Piece piece = board.getSnapshot()[7][0];
        assertEquals(PieceType.ROOK, piece.getType());
        assertEquals(PieceColor.WHITE, piece.getColor());
    }

    @Test
    void GetSnapshot_ReturnedPieceIsDifferentObjectWithSameContents() {
        Board board = new Board(new StandardBoardInitializer());
        Piece piece1 = board.getSnapshot()[0][0];
        Piece piece2 = board.getSnapshot()[0][0];
        assertNotSame(piece1, piece2);
        assertEquals(piece1.getType(), piece2.getType());
        assertEquals(piece1.getColor(), piece2.getColor());
    }

    @Test
    void SwitchTurn_FromBlackTurn_GameStateIsWhiteTurn() {
        Board board = new Board(new StandardBoardInitializer());
        board.switchTurn();
        board.switchTurn();
        assertEquals(GameState.WHITE_TURN, board.getCurrentGameState());
    }
}
