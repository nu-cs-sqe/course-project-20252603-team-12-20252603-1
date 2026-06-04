package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.gamestate.GameState;
import domain.location.Location;
import domain.move.Move;
import domain.move.MoveType;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

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
    void GetLegalMoves_WhenCalled_MatchesMoveGenerator() {
        Piece[][] layout = emptyPieceGrid();
        layout[3][5] = new Pawn(PieceColor.WHITE);
        Board board = new Board(layout);
        Optional<Location> enPassantTarget = Optional.of(new Location(2, 4));
        board.setEnPassantTarget(enPassantTarget);
        Location from = new Location(5, 3);

        List<Move> expected =
                new MoveGenerator(board.getSnapshot(), enPassantTarget).generateLegalMoves(from);
        List<Move> actual = board.getLegalMoves(from);

        int expectedSize = expected.size();
        int actualSize = actual.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void GetLegalMoves_OnCenterKnight_ReturnsEightMoves() {
        Piece[][] layout = emptyPieceGrid();
        layout[4][4] = new Knight(PieceColor.WHITE);
        Board board = new Board(layout);
        Location from = new Location(4, 4);

        int expected = 8;
        int actual = board.getLegalMoves(from).size();
        assertEquals(expected, actual);
    }

    @Test
    void GetLegalMoves_OnEmptySquare_ReturnsEmptyList() {
        Piece[][] layout = emptyPieceGrid();
        Board board = new Board(layout);
        Location from = new Location(3, 3);

        int expected = 0;
        int actual = board.getLegalMoves(from).size();
        assertEquals(expected, actual);
    }

    @Test
    void MakeMove_AfterBlackMove_GameStateIsWhiteTurn() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[1][4] = new Pawn(PieceColor.BLACK);
        layout[0][0] = new King(PieceColor.BLACK);
        layout[7][7] = new King(PieceColor.WHITE);
        Board board = new Board(layout);
        board.switchTurn();
        Move move = new Move(new Location(4, 1), new Location(4, 2));

        board.makeMove(move);

        GameState expected = GameState.WHITE_TURN;
        GameState actual = board.getCurrentGameState();
        assertEquals(expected, actual);
    }

    @Test
    void MakeMove_AfterWhiteMove_GameStateIsBlackTurn() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[6][4] = new Pawn(PieceColor.WHITE);
        layout[7][7] = new King(PieceColor.WHITE);
        layout[0][0] = new King(PieceColor.BLACK);
        Board board = new Board(layout);
        Move move = new Move(new Location(4, 6), new Location(4, 5));

        board.makeMove(move);

        GameState expected = GameState.BLACK_TURN;
        GameState actual = board.getCurrentGameState();
        assertEquals(expected, actual);
    }

    @Test
    void MakeMove_OnNormalMove_SourceSquareIsEmpty() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[6][4] = new Pawn(PieceColor.WHITE);
        Board board = new Board(layout);
        Move move = new Move(new Location(4, 6), new Location(4, 5));

        board.makeMove(move);

        PieceType expected = PieceType.NONE;
        PieceType actual = board.getPieceAt(6, 4).getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeMove_OnNormalMove_PieceAtDestination() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[6][4] = new Pawn(PieceColor.WHITE);
        Board board = new Board(layout);
        Move move = new Move(new Location(4, 6), new Location(4, 5));

        board.makeMove(move);

        PieceType expected = PieceType.PAWN;
        PieceType actual = board.getPieceAt(5, 4).getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeMove_OnEnPassantMove_DestinationHasMovingPawn() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[3][4] = new Pawn(PieceColor.WHITE);
        layout[3][5] = new Pawn(PieceColor.BLACK);
        Board board = new Board(layout);
        Move move = new Move(new Location(4, 3), new Location(5, 2), MoveType.EN_PASSANT);

        board.makeMove(move);

        PieceType expected = PieceType.PAWN;
        PieceType actual = board.getPieceAt(2, 5).getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeMove_OnEnPassantMove_CapturedPawnSquareIsEmpty() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[3][4] = new Pawn(PieceColor.WHITE);
        layout[3][5] = new Pawn(PieceColor.BLACK);
        Board board = new Board(layout);
        Move move = new Move(new Location(4, 3), new Location(5, 2), MoveType.EN_PASSANT);

        board.makeMove(move);

        PieceType expected = PieceType.NONE;
        PieceType actual = board.getPieceAt(3, 5).getType();
        assertEquals(expected, actual);
    }

    @Test
    void MakeMove_OnKingsideCastling_KingAndRookReachCastledSquares() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[7][4] = new King(PieceColor.WHITE);
        layout[7][7] = new Rook(PieceColor.WHITE);
        Board board = new Board(layout);
        Move move = new Move(new Location(4, 7), new Location(6, 7), MoveType.CASTLING_KINGSIDE);

        board.makeMove(move);

        PieceType expectedKing = PieceType.KING;
        PieceType actualKing = board.getPieceAt(7, 6).getType();
        assertEquals(expectedKing, actualKing);

        PieceType expectedRook = PieceType.ROOK;
        PieceType actualRook = board.getPieceAt(7, 5).getType();
        assertEquals(expectedRook, actualRook);
    }

    @Test
    void MakeMove_OnKingsideCastlingWithoutUnmovedRook_ThrowsIllegalStateException() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[7][4] = new King(PieceColor.WHITE);
        Board board = new Board(layout);
        Move move = new Move(new Location(4, 7), new Location(6, 7), MoveType.CASTLING_KINGSIDE);

        assertThrows(IllegalStateException.class, () -> board.makeMove(move));
    }

    @Test
    void MakeMove_OnPromotionMove_PromotedPieceAtDestinationIsQueen() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[1][4] = new Pawn(PieceColor.WHITE);
        Board board = new Board(layout);
        Move move = new Move(new Location(4, 1), new Location(4, 0), MoveType.PROMOTION);

        board.makeMove(move);

        assertEquals(PieceType.QUEEN, board.getPieceAt(0, 4).getType());
    }

    @Test
    void MakeMove_OnQueensideCastling_KingAndRookReachCastledSquares() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[7][4] = new King(PieceColor.WHITE);
        layout[7][0] = new Rook(PieceColor.WHITE);
        Board board = new Board(layout);
        Move move = new Move(new Location(4, 7), new Location(2, 7), MoveType.CASTLING_QUEENSIDE);

        board.makeMove(move);

        PieceType expectedKing = PieceType.KING;
        PieceType actualKing = board.getPieceAt(7, 2).getType();
        assertEquals(expectedKing, actualKing);

        PieceType expectedRook = PieceType.ROOK;
        PieceType actualRook = board.getPieceAt(7, 3).getType();
        assertEquals(expectedRook, actualRook);
    }

    @Test
    void MakeMove_OnTwoStepPawnMove_SetsEnPassantTargetForOpponentCapture() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[6][4] = new Pawn(PieceColor.WHITE);
        Board board = new Board(layout);
        Move whiteDoubleStep = new Move(new Location(4, 6), new Location(4, 4));

        board.makeMove(whiteDoubleStep);

        Optional<Location> target = board.getEnPassantTarget();

        boolean expectedPresent = true;
        boolean actualPresent = target.isPresent();
        assertEquals(expectedPresent, actualPresent);

        int expectedFile = 4;
        int actualFile = target.get().getX();
        assertEquals(expectedFile, actualFile);

        int expectedRank = 5;
        int actualRank = target.get().getY();
        assertEquals(expectedRank, actualRank);
    }

    @Test
    void MakeMove_OnNonDoubleStepMove_ClearsEnPassantTarget() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        layout[7][1] = new Knight(PieceColor.WHITE);
        Board board = new Board(layout);
        board.setEnPassantTarget(Optional.of(new Location(4, 5)));
        Move knightMove = new Move(new Location(1, 7), new Location(2, 5));

        board.makeMove(knightMove);

        boolean expected = false;
        boolean actual = board.getEnPassantTarget().isPresent();
        assertEquals(expected, actual);
    }

    private static Piece[][] emptyPieceGrid() {
        Piece[][] layout = new Piece[8][8];
        for (Piece[] row : layout) {
            Arrays.fill(row, new NonePiece());
        }
        return layout;
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
    void GetCurrentGameState_AfterWhiteCheckmate_ReturnsWhiteWin() {
        Piece[][] layout = emptyPieceGrid();
        layout[0][0] = new King(PieceColor.BLACK);
        layout[0][1] = new Pawn(PieceColor.BLACK);
        layout[1][0] = new Pawn(PieceColor.BLACK);
        layout[1][1] = new Pawn(PieceColor.BLACK);
        layout[2][0] = new Rook(PieceColor.WHITE);
        layout[4][0] = new Knight(PieceColor.WHITE);
        Board board = new Board(layout);

        board.makeMove(new Move(new Location(0, 4), new Location(1, 2)));

        assertEquals(GameState.WHITE_WIN, board.getCurrentGameState());
    }

    @Test
    void GetCurrentGameState_AfterBlackCheckmate_ReturnsBlackWin() {
        Piece[][] layout = emptyPieceGrid();
        layout[7][4] = new King(PieceColor.WHITE);
        layout[6][0] = new Rook(PieceColor.BLACK);
        layout[0][7] = new Rook(PieceColor.BLACK);
        Board board = new Board(layout);
        board.switchTurn();

        board.makeMove(new Move(new Location(7, 0), new Location(7, 7)));

        assertEquals(GameState.BLACK_WIN, board.getCurrentGameState());
    }

    @Test
    void GetCurrentGameState_AfterStalemate_ReturnsDraw() {
        Piece[][] layout = emptyPieceGrid();
        layout[0][0] = new King(PieceColor.BLACK);
        layout[0][1] = new Pawn(PieceColor.BLACK);
        layout[1][0] = new Pawn(PieceColor.BLACK);
        layout[1][1] = new Pawn(PieceColor.BLACK);
        layout[2][0] = new Pawn(PieceColor.WHITE);
        layout[2][1] = new Pawn(PieceColor.WHITE);
        layout[7][7] = new Rook(PieceColor.WHITE);
        Board board = new Board(layout);

        board.makeMove(new Move(new Location(7, 7), new Location(6, 7)));

        assertEquals(GameState.DRAW, board.getCurrentGameState());
    }

    @Test
    void MakeMove_WhenWhiteCausesCheckmate_GameStateIsWhiteWin() {
        Piece[][] layout = emptyPieceGrid();
        layout[0][0] = new King(PieceColor.BLACK);
        layout[0][1] = new Pawn(PieceColor.BLACK);
        layout[1][0] = new Pawn(PieceColor.BLACK);
        layout[1][1] = new Pawn(PieceColor.BLACK);
        layout[2][0] = new Rook(PieceColor.WHITE);
        layout[4][0] = new Knight(PieceColor.WHITE);
        Board board = new Board(layout);

        board.makeMove(new Move(new Location(0, 4), new Location(1, 2)));

        assertEquals(GameState.WHITE_WIN, board.getCurrentGameState());
    }

    @Test
    void MakeMove_WhenBlackCausesCheckmate_GameStateIsBlackWin() {
        Piece[][] layout = emptyPieceGrid();
        layout[7][4] = new King(PieceColor.WHITE);
        layout[6][0] = new Rook(PieceColor.BLACK);
        layout[0][7] = new Rook(PieceColor.BLACK);
        Board board = new Board(layout);
        board.switchTurn();

        board.makeMove(new Move(new Location(7, 0), new Location(7, 7)));

        assertEquals(GameState.BLACK_WIN, board.getCurrentGameState());
    }

    @Test
    void MakeMove_WhenMoveCausesStalemate_GameStateIsDraw() {
        Piece[][] layout = emptyPieceGrid();
        layout[0][0] = new King(PieceColor.BLACK);
        layout[0][1] = new Pawn(PieceColor.BLACK);
        layout[1][0] = new Pawn(PieceColor.BLACK);
        layout[1][1] = new Pawn(PieceColor.BLACK);
        layout[2][0] = new Pawn(PieceColor.WHITE);
        layout[2][1] = new Pawn(PieceColor.WHITE);
        layout[7][7] = new Rook(PieceColor.WHITE);
        Board board = new Board(layout);

        board.makeMove(new Move(new Location(7, 7), new Location(6, 7)));

        assertEquals(GameState.DRAW, board.getCurrentGameState());
    }

    @Test
    void MakeMove_WhenOnlyKingsRemain_GameStateIsDraw() {
        Piece[][] layout = emptyPieceGrid();
        layout[7][0] = new King(PieceColor.WHITE);
        layout[4][4] = new Queen(PieceColor.WHITE);
        layout[1][1] = new King(PieceColor.BLACK);
        layout[2][2] = new Queen(PieceColor.BLACK);
        Board board = new Board(layout);

        board.makeMove(new Move(new Location(4, 4), new Location(2, 2)));
        board.makeMove(new Move(new Location(1, 1), new Location(2, 2)));

        assertEquals(GameState.DRAW, board.getCurrentGameState());
    }

    @Test
    void MakeMove_WhenHalfMoveClockReachesLimit_GameStateIsDraw() {
        Piece[][] layout = emptyPieceGrid();
        layout[7][7] = new King(PieceColor.WHITE);
        layout[5][5] = new Knight(PieceColor.WHITE);
        layout[0][0] = new King(PieceColor.BLACK);
        Board board = new Board(layout);
        board.halfMoveClock = 99;

        board.makeMove(new Move(new Location(5, 5), new Location(4, 3)));

        assertEquals(100, board.getHalfMoveClock());
        assertEquals(GameState.DRAW, board.getCurrentGameState());
    }

    static Stream<Arguments> halfMoveClockProvider() {
        Piece[][] layout1 = emptyPieceGrid();
        layout1[7][7] = new King(PieceColor.WHITE);
        layout1[5][5] = new Knight(PieceColor.WHITE);
        layout1[0][0] = new King(PieceColor.BLACK);
        Board board1 = new Board(layout1);

        Piece[][] layout2 = emptyPieceGrid();
        layout2[7][7] = new King(PieceColor.WHITE);
        layout2[6][4] = new Pawn(PieceColor.WHITE);
        layout2[0][0] = new King(PieceColor.BLACK);
        Board board2 = new Board(layout2);
        board2.halfMoveClock = 5;

        Piece[][] layout3 = emptyPieceGrid();
        layout3[7][7] = new King(PieceColor.WHITE);
        layout3[5][5] = new Knight(PieceColor.WHITE);
        layout3[3][4] = new Pawn(PieceColor.BLACK);
        layout3[0][0] = new King(PieceColor.BLACK);
        Board board3 = new Board(layout3);
        board3.halfMoveClock = 5;

        return Stream.of(
                Arguments.of(board1, new Move(new Location(5, 5), new Location(4, 3)), 1),
                Arguments.of(board2, new Move(new Location(4, 6), new Location(4, 5)), 0),
                Arguments.of(board3, new Move(new Location(5, 5), new Location(4, 3)), 0)
        );
    }

    @ParameterizedTest
    @MethodSource("halfMoveClockProvider")
    void MakeMove_HalfMoveClockUpdatesBasedOnMoveType(
            Board board, Move move, int expectedClock) {
        board.makeMove(move);
        assertEquals(expectedClock, board.getHalfMoveClock());
    }

    static Stream<Arguments> updateGameStateCheckmateProvider() {
        Piece[][] layout1 = emptyPieceGrid();
        layout1[7][4] = new King(PieceColor.BLACK);
        layout1[6][0] = new Rook(PieceColor.WHITE);
        layout1[7][7] = new Rook(PieceColor.WHITE);
        Board board1 = new Board(layout1);

        Piece[][] layout2 = emptyPieceGrid();
        layout2[7][4] = new King(PieceColor.WHITE);
        layout2[6][0] = new Rook(PieceColor.BLACK);
        layout2[7][7] = new Rook(PieceColor.BLACK);
        Board board2 = new Board(layout2);

        return Stream.of(
                Arguments.of(board1, PieceColor.WHITE, GameState.WHITE_WIN),
                Arguments.of(board2, PieceColor.BLACK, GameState.BLACK_WIN)
        );
    }

    @ParameterizedTest
    @MethodSource("updateGameStateCheckmateProvider")
    void UpdateGameState_WhenNextPlayerCheckmated_SetsWinState(
            Board board, PieceColor justMovedColor, GameState expected) {
        board.updateGameState(justMovedColor);
        assertEquals(expected, board.getCurrentGameState());
    }

    @Test
    void UpdateGameState_WhenNextHasNoMovesAndNotInCheck_GameStateIsDraw() {
        Piece[][] layout = emptyPieceGrid();
        layout[0][0] = new King(PieceColor.BLACK);
        layout[0][1] = new Pawn(PieceColor.BLACK);
        layout[1][0] = new Pawn(PieceColor.BLACK);
        layout[1][1] = new Pawn(PieceColor.BLACK);
        layout[2][0] = new Pawn(PieceColor.WHITE);
        layout[2][1] = new Pawn(PieceColor.WHITE);
        layout[6][7] = new Rook(PieceColor.WHITE);
        Board board = new Board(layout);

        board.updateGameState(PieceColor.WHITE);

        assertEquals(GameState.DRAW, board.getCurrentGameState());
    }

    @Test
    void UpdateGameState_WhenInsufficientMaterial_GameStateIsDraw() {
        Piece[][] layout = emptyPieceGrid();
        layout[7][0] = new King(PieceColor.WHITE);
        layout[0][7] = new King(PieceColor.BLACK);
        Board board = new Board(layout);

        board.updateGameState(PieceColor.WHITE);

        assertEquals(GameState.DRAW, board.getCurrentGameState());
    }

    @Test
    void UpdateGameState_WhenHalfMoveClockAtLimit_GameStateIsDraw() {
        Piece[][] layout = emptyPieceGrid();
        layout[7][0] = new King(PieceColor.WHITE);
        layout[0][7] = new King(PieceColor.BLACK);
        layout[5][5] = new Rook(PieceColor.WHITE);
        Board board = new Board(layout);
        board.halfMoveClock = 100;

        board.updateGameState(PieceColor.WHITE);

        assertEquals(GameState.DRAW, board.getCurrentGameState());
    }

    @Test
    void UpdateGameState_WhenHalfMoveClockBelowLimit_GameStateIsNextTurn() {
        Piece[][] layout = emptyPieceGrid();
        layout[7][0] = new King(PieceColor.WHITE);
        layout[0][7] = new King(PieceColor.BLACK);
        layout[5][5] = new Rook(PieceColor.WHITE);
        Board board = new Board(layout);
        board.halfMoveClock = 99;

        board.updateGameState(PieceColor.WHITE);

        assertEquals(GameState.BLACK_TURN, board.getCurrentGameState());
    }

    static Stream<Arguments> updateGameStateContinuesProvider() {
        Piece[][] layout1 = emptyPieceGrid();
        layout1[7][0] = new King(PieceColor.WHITE);
        layout1[0][7] = new King(PieceColor.BLACK);
        layout1[5][5] = new Rook(PieceColor.WHITE);
        Board board1 = new Board(layout1);

        Piece[][] layout2 = emptyPieceGrid();
        layout2[7][0] = new King(PieceColor.WHITE);
        layout2[0][7] = new King(PieceColor.BLACK);
        layout2[5][5] = new Rook(PieceColor.BLACK);
        Board board2 = new Board(layout2);
        board2.halfMoveClock = 1;

        return Stream.of(
                Arguments.of(board1, PieceColor.WHITE, GameState.BLACK_TURN),
                Arguments.of(board2, PieceColor.BLACK, GameState.WHITE_TURN)
        );
    }

    @ParameterizedTest
    @MethodSource("updateGameStateContinuesProvider")
    void UpdateGameState_WhenGameContinues_SetsNextTurn(
            Board board, PieceColor justMovedColor, GameState expected) {
        board.updateGameState(justMovedColor);
        assertEquals(expected, board.getCurrentGameState());
    }

    static Stream<Arguments> isInsufficientMaterialProvider() {
        Piece[][] layout1 = emptyPieceGrid();
        layout1[7][0] = new King(PieceColor.WHITE);
        layout1[0][7] = new King(PieceColor.BLACK);

        Piece[][] layout2 = emptyPieceGrid();
        layout2[7][0] = new King(PieceColor.WHITE);
        layout2[5][5] = new Bishop(PieceColor.WHITE);
        layout2[0][7] = new King(PieceColor.BLACK);

        Piece[][] layout3 = emptyPieceGrid();
        layout3[7][0] = new King(PieceColor.WHITE);
        layout3[5][5] = new Knight(PieceColor.WHITE);
        layout3[0][7] = new King(PieceColor.BLACK);

        Piece[][] layout4 = emptyPieceGrid();
        layout4[7][0] = new King(PieceColor.WHITE);
        layout4[5][5] = new Pawn(PieceColor.WHITE);
        layout4[0][7] = new King(PieceColor.BLACK);

        Piece[][] layout5 = emptyPieceGrid();
        layout5[7][0] = new King(PieceColor.WHITE);
        layout5[5][5] = new Bishop(PieceColor.WHITE);
        layout5[0][7] = new King(PieceColor.BLACK);
        layout5[2][2] = new Bishop(PieceColor.BLACK);

        return Stream.of(
                Arguments.of(new Board(layout1), true),
                Arguments.of(new Board(layout2), true),
                Arguments.of(new Board(layout3), true),
                Arguments.of(new Board(layout4), false),
                Arguments.of(new Board(layout5), false)
        );
    }

    @ParameterizedTest
    @MethodSource("isInsufficientMaterialProvider")
    void IsInsufficientMaterial_WithVariousPieceCombinations_ReturnsCorrectResult(Board board, boolean expected) {
        assertEquals(expected, board.isInsufficientMaterial());
    }

    static Stream<Arguments> isCaptureProvider() {
        Piece[][] layout1 = emptyPieceGrid();
        layout1[3][4] = new Pawn(PieceColor.WHITE);
        layout1[3][5] = new Pawn(PieceColor.BLACK);
        Board board1 = new Board(layout1);
        Move enPassant = new Move(new Location(4, 3), new Location(5, 2), MoveType.EN_PASSANT);

        Piece[][] layout2 = emptyPieceGrid();
        layout2[6][4] = new Pawn(PieceColor.WHITE);
        Board board2 = new Board(layout2);
        Move normalToEmpty = new Move(new Location(4, 6), new Location(4, 5));

        Piece[][] layout3 = emptyPieceGrid();
        layout3[5][3] = new Knight(PieceColor.WHITE);
        layout3[3][4] = new Pawn(PieceColor.BLACK);
        Board board3 = new Board(layout3);
        Move normalToOccupied = new Move(new Location(3, 5), new Location(4, 3));

        return Stream.of(
                Arguments.of(board1, enPassant, true),
                Arguments.of(board2, normalToEmpty, false),
                Arguments.of(board3, normalToOccupied, true)
        );
    }

    @ParameterizedTest
    @MethodSource("isCaptureProvider")
    void IsCapture_WithVariousMoveTypes_ReturnsCorrectResult(
            Board board, Move move, boolean expected) {
        assertEquals(expected, board.isCapture(move));
    }

    @ParameterizedTest
    @CsvSource({"WHITE_TURN, WHITE", "BLACK_TURN, BLACK"})
    void CurrentPlayerColor_WithGivenGameState_ReturnsCorrectColor(
            GameState state, PieceColor expected) {
        Piece[][] layout = emptyPieceGrid();
        layout[7][0] = new King(PieceColor.WHITE);
        layout[0][7] = new King(PieceColor.BLACK);
        Board board = new Board(layout);
        if (state == GameState.BLACK_TURN) {
            board.switchTurn();
        }
        assertEquals(expected, board.currentPlayerColor());
    }

}
