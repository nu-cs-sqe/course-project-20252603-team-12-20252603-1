package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import domain.gamestate.GameState;
import domain.piece.Bishop;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.NonePiece;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Queen;
import domain.piece.Rook;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import domain.location.Location;
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

    static Stream<Arguments> pieceTypeAtPositionProvider() {
        return Stream.of(
                Arguments.of(new Rook(PieceColor.BLACK),   0, 0, PieceType.ROOK),
                Arguments.of(new Knight(PieceColor.BLACK), 0, 1, PieceType.KNIGHT),
                Arguments.of(new Bishop(PieceColor.BLACK), 0, 2, PieceType.BISHOP),
                Arguments.of(new Queen(PieceColor.BLACK),  0, 3, PieceType.QUEEN),
                Arguments.of(new King(PieceColor.BLACK),   0, 4, PieceType.KING),
                Arguments.of(new Pawn(PieceColor.BLACK),   1, 0, PieceType.PAWN),
                Arguments.of(new NonePiece(),              7, 0, PieceType.NONE),
                Arguments.of(new Rook(PieceColor.BLACK),   0, 7, PieceType.ROOK)
        );
    }

    @ParameterizedTest
    @MethodSource("pieceTypeAtPositionProvider")
    void Constructor_WhenPieceArrayHasPieceAtPosition_PieceTypeIsPiece(
            Piece piece, int row, int col, PieceType expectedType) {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] r : layout) Arrays.fill(r, new NonePiece());
        layout[row][col] = piece;
        Board board = new Board(layout);
        assertEquals(expectedType, board.getSnapshot()[row][col].getType());
    }

    @Test
    void Constructor_WhenPieceArrayHasBlackPieceAtPosition_PieceColorIsBlack() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] r : layout) Arrays.fill(r, new NonePiece());
        layout[7][0] = new Rook(PieceColor.BLACK);
        Board board = new Board(layout);
        assertEquals(PieceColor.BLACK, board.getSnapshot()[7][0].getColor());
    }

    @Test
    void Constructor_WhenPieceArrayHasWhitePieceAtPosition_PieceColorIsWhite() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] r : layout) Arrays.fill(r, new NonePiece());
        layout[0][0] = new Rook(PieceColor.WHITE);
        Board board = new Board(layout);
        assertEquals(PieceColor.WHITE, board.getSnapshot()[0][0].getColor());
    }

    @Test
    void Constructor_WithPieceArray_OnNewBoard_GameStateIsWhiteTurn() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] r : layout) Arrays.fill(r, new NonePiece());
        Board board = new Board(layout);
        assertEquals(GameState.WHITE_TURN, board.getCurrentGameState());
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
        snapshot[7][0] = new NonePiece();
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

    static Stream<Arguments> getPieceAtTypeProvider() {
        return Stream.of(
                Arguments.of(new Rook(PieceColor.BLACK),   0, 0, PieceType.ROOK),
                Arguments.of(new Knight(PieceColor.BLACK), 0, 1, PieceType.KNIGHT),
                Arguments.of(new Bishop(PieceColor.BLACK), 0, 2, PieceType.BISHOP),
                Arguments.of(new Queen(PieceColor.BLACK),  0, 3, PieceType.QUEEN),
                Arguments.of(new King(PieceColor.BLACK),   0, 4, PieceType.KING),
                Arguments.of(new Pawn(PieceColor.BLACK),   1, 0, PieceType.PAWN),
                Arguments.of(new NonePiece(),              7, 0, PieceType.NONE)
        );
    }

    @ParameterizedTest
    @MethodSource("getPieceAtTypeProvider")
    void GetPieceAt_WhenBoardHasPieceAtPosition_PieceTypeMatches(
            Piece piece, int rank, int file, PieceType expectedType) {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] r : layout) Arrays.fill(r, new NonePiece());
        layout[rank][file] = piece;
        Board board = new Board(layout);
        assertEquals(expectedType, board.getPieceAt(rank, file).getType());
    }

    @Test
    void GetPieceAt_AtRankZeroFileZero_PieceColorIsBlack() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] r : layout) Arrays.fill(r, new NonePiece());
        layout[0][0] = new Rook(PieceColor.BLACK);
        Board board = new Board(layout);
        assertEquals(PieceColor.BLACK, board.getPieceAt(0, 0).getColor());
    }

    @Test
    void GetPieceAt_AtRankSevenFileSeven_PieceColorIsWhite() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] r : layout) Arrays.fill(r, new NonePiece());
        layout[7][7] = new Rook(PieceColor.WHITE);
        Board board = new Board(layout);
        assertEquals(PieceColor.WHITE, board.getPieceAt(7, 7).getColor());
    }

    @Test
    void GetPieceAt_ReturnedPieceIsDifferentObject() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] r : layout) Arrays.fill(r, new NonePiece());
        layout[0][0] = new Rook(PieceColor.BLACK);
        Board board = new Board(layout);
        Piece piece1 = board.getPieceAt(0, 0);
        Piece piece2 = board.getPieceAt(0, 0);
        assertNotSame(piece1, piece2);
        assertEquals(piece1.getType(), piece2.getType());
        assertEquals(piece1.getColor(), piece2.getColor());
    }

    @Test
    void MovePiece_LegalPawnForwardOneToEmptySquare_ReturnsTrue() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[6][4] = new Pawn(PieceColor.WHITE);

        Board board = new Board(layout);

        Location from = new Location(4, 6);
        Location to = new Location(4, 5);

        boolean actual = board.movePiece(from, to);

        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    void MovePiece_LegalPawnForwardOneToEmptySquare_OriginBecomesNone() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[6][4] = new Pawn(PieceColor.WHITE);

        Board board = new Board(layout);

        Location from = new Location(4, 6);
        Location to = new Location(4, 5);

        board.movePiece(from, to);

        PieceType expected = PieceType.NONE;
        PieceType actual = board.getPieceAt(6, 4).getType();
        assertEquals(expected, actual);
    }

    @Test
    void MovePiece_LegalPawnForwardOneToEmptySquare_DestinationHasPawn() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[6][4] = new Pawn(PieceColor.WHITE);

        Board board = new Board(layout);

        Location from = new Location(4, 6);
        Location to = new Location(4, 5);

        board.movePiece(from, to);

        PieceType expected = PieceType.PAWN;
        PieceType actual = board.getPieceAt(5, 4).getType();
        assertEquals(expected, actual);
    }

    @Test
    void MovePiece_LegalPawnForwardOneToEmptySquare_MovedPawnHasMovedTrue() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[6][4] = new Pawn(PieceColor.WHITE);

        Board board = new Board(layout);

        Location from = new Location(4, 6);
        Location to = new Location(4, 5);

        board.movePiece(from, to);

        boolean expected = true;
        boolean actual = board.getPieceAt(5, 4).hasMoved();
        assertEquals(expected, actual);
    }

    @Test
    void MovePiece_LegalKnightJumpOverPiece_ReturnsTrue() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[7][6] = new Knight(PieceColor.WHITE);
        layout[6][6] = new Pawn(PieceColor.WHITE);

        Board board = new Board(layout);

        Location from = new Location(6, 7);
        Location to = new Location(5, 5);

        boolean expected = true;
        boolean actual = board.movePiece(from, to);
        assertEquals(expected, actual);
    }

    @Test
    void MovePiece_DestinationOccupiedByFriendlyPiece_ReturnsFalse() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[7][0] = new Rook(PieceColor.WHITE);
        layout[7][3] = new Rook(PieceColor.WHITE);

        Board board = new Board(layout);

        Location from = new Location(0, 7);
        Location to = new Location(3, 7);

        boolean expected = false;
        boolean actual = board.movePiece(from, to);
        assertEquals(expected, actual);
    }

    @Test
    void MovePiece_DestinationOccupiedByFriendlyPiece_BoardUnchanged() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[7][0] = new Rook(PieceColor.WHITE);
        layout[7][3] = new Rook(PieceColor.WHITE);

        Board board = new Board(layout);
        Piece[][] beforeMove = board.getSnapshot();

        Location from = new Location(0, 7);
        Location to = new Location(3, 7);

        board.movePiece(from, to);

        Piece[][] afterMove = board.getSnapshot();
        boolean expected = true;
        boolean actual = boardsMatchByTypeAndColor(beforeMove, afterMove);
        assertEquals(expected, actual);
    }

    @Test
    void MovePiece_InvalidMoveShape_ReturnsFalse() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[6][4] = new Pawn(PieceColor.WHITE);

        Board board = new Board(layout);

        Location from = new Location(4, 6);
        Location to = new Location(5, 6);

        boolean expected = false;
        boolean actual = board.movePiece(from, to);
        assertEquals(expected, actual);
    }

    @Test
    void MovePiece_BlockedRookSlide_ReturnsFalse() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[7][0] = new Rook(PieceColor.WHITE);
        layout[7][1] = new Pawn(PieceColor.WHITE);

        Board board = new Board(layout);

        Location from = new Location(0, 7);
        Location to = new Location(3, 7);

        boolean expected = false;
        boolean actual = board.movePiece(from, to);
        assertEquals(expected, actual);
    }

    @Test
    void MovePiece_LegalRookSlideToEmptySquare_ReturnsTrue() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[7][0] = new Rook(PieceColor.WHITE);

        Board board = new Board(layout);

        Location from = new Location(0, 7);
        Location to = new Location(3, 7);

        boolean expected = true;
        boolean actual = board.movePiece(from, to);
        assertEquals(expected, actual);
    }

    @Test
    void MovePiece_LegalCapture_ReturnsTrue() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[4][4] = new Pawn(PieceColor.WHITE);
        layout[3][5] = new Pawn(PieceColor.BLACK);

        Board board = new Board(layout);

        Location from = new Location(4, 4);
        Location to = new Location(5, 3);

        boolean expected = true;
        boolean actual = board.movePiece(from, to);
        assertEquals(expected, actual);
    }

    @Test
    void MovePiece_LegalCapture_OriginBecomesNone() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[4][4] = new Pawn(PieceColor.WHITE);
        layout[3][5] = new Pawn(PieceColor.BLACK);

        Board board = new Board(layout);

        Location from = new Location(4, 4);
        Location to = new Location(5, 3);

        board.movePiece(from, to);

        PieceType expected = PieceType.NONE;
        PieceType actual = board.getPieceAt(4, 4).getType();
        assertEquals(expected, actual);
    }

    @Test
    void MovePiece_LegalCapture_DestinationHasWhitePawn() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[4][4] = new Pawn(PieceColor.WHITE);
        layout[3][5] = new Pawn(PieceColor.BLACK);

        Board board = new Board(layout);

        Location from = new Location(4, 4);
        Location to = new Location(5, 3);

        board.movePiece(from, to);

        PieceType expectedType = PieceType.PAWN;
        PieceType actualType = board.getPieceAt(3, 5).getType();
        assertEquals(expectedType, actualType);

        PieceColor expectedColor = PieceColor.WHITE;
        PieceColor actualColor = board.getPieceAt(3, 5).getColor();
        assertEquals(expectedColor, actualColor);
    }

    private boolean boardsMatchByTypeAndColor(Piece[][] left, Piece[][] right) {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Piece leftPiece = left[rank][file];
                Piece rightPiece = right[rank][file];
                if (leftPiece.getType() != rightPiece.getType()) {
                    return false;
                }
                if (leftPiece.getColor() != rightPiece.getColor()) {
                    return false;
                }
            }
        }
        return true;
    }

}
