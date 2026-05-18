package domain.boardcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.gamestate.GameState;
import domain.location.Location;
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
import java.util.Optional;
import org.junit.jupiter.api.Test;

class BoardControllerTest {

    @Test
    void Constructor_FreshInstance_LastSelectedUnset() {
        BoardController controller = new BoardController();

        boolean expected = false;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
    }

    @Test
    void GetSelectedLocation_FreshInstance_ReturnsEmpty() {
        BoardController controller = new BoardController();

        Optional<Location> expected = Optional.empty();
        Optional<Location> actual = controller.getSelectedLocation();
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_AfterStandardInit_EightByEightGrid() {
        BoardController controller = new BoardController();

        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = isEightByEight(snapshot);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_AfterStandardInit_CornerCellsOccupied() {
        BoardController controller = new BoardController();

        Piece[][] snapshot = controller.getBoardSnapshot();
        Piece[][] expectedGrid = newStandardStartingGrid();

        boolean expected = true;
        boolean actual = cellWiseSameTypeAndColor(expectedGrid, snapshot);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_MatchesBoardSnapshot_Cellwise() {
        Piece[][] expectedGrid = newStandardStartingGrid();
        BoardController controller = new BoardController();
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = cellWiseSameTypeAndColor(expectedGrid, snapshot);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_StandardStart_ExactlySixteenWhitePieces() {
        BoardController controller = new BoardController();

        Piece[][] snapshot = controller.getBoardSnapshot();

        int expected = 16;
        int actual = countPiecesOfColor(snapshot, PieceColor.WHITE);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_StandardStart_ExactlySixteenBlackPieces() {
        BoardController controller = new BoardController();

        Piece[][] snapshot = controller.getBoardSnapshot();

        int expected = 16;
        int actual = countPiecesOfColor(snapshot, PieceColor.BLACK);
        assertEquals(expected, actual);
    }

    @Test
    void GameStart_Standard_WhiteTurn() {
        BoardController controller = new BoardController();

        GameState expectedState = GameState.WHITE_TURN;
        GameState actualState = controller.getCurrentGameState();
        assertEquals(expectedState, actualState);
    }

    @Test
    void GameStart_Standard_NoOccupiedPieceHasMoved() {
        BoardController controller = new BoardController();

        boolean expected = true;
        boolean actual = everyOccupiedPieceHasNotMoved(controller.getBoardSnapshot());
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_AfterStandardInit_ReturnsIndependentCopy() {
        BoardController controller = new BoardController();

        Piece[][] snapshot1 = controller.getBoardSnapshot();
        Piece[][] snapshot2 = controller.getBoardSnapshot();

        boolean expected = false;
        boolean actual = snapshot1 == snapshot2;
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_BishopsOnOppositeColorSquares_WhiteBackRank() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);

        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = bishopsOnOppositeColorSquaresOnRank(snapshot, 0, PieceColor.WHITE);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_BishopsOnOppositeColorSquares_BlackBackRank() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);

        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = bishopsOnOppositeColorSquaresOnRank(snapshot, 7, PieceColor.BLACK);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_KingStrictlyBetweenRooksOnBackRank_WhiteBackRank() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);

        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = kingStrictlyBetweenOwnRooksOnRank(snapshot, 0, PieceColor.WHITE);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_KingStrictlyBetweenRooksOnBackRank_BlackBackRank() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);

        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = kingStrictlyBetweenOwnRooksOnRank(snapshot, 7, PieceColor.BLACK);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_BackRanksMirrorPieceTypes_BackRankTypesMirror() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);

        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960BackRanksMirrorPieceTypes(snapshot);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_BackRanksMirrorPieceTypes_StandardPawnRows() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);

        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960StandardPawnRows(snapshot);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_OneQueenTwoKnightsOnBackRank_WhiteBackRank() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);

        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960BackRankMajorPieceCounts(snapshot, 0, PieceColor.WHITE);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_OneQueenTwoKnightsOnBackRank_BlackBackRank() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);

        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960BackRankMajorPieceCounts(snapshot, 7, PieceColor.BLACK);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_BishopsOppositeColorSquares_WhiteBackRank() {
        BoardController controller = new BoardController(1L);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = bishopsOnOppositeColorSquaresOnRank(snapshot, 0, PieceColor.WHITE);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_BishopsOppositeColorSquares_BlackBackRank() {
        BoardController controller = new BoardController(1L);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = bishopsOnOppositeColorSquaresOnRank(snapshot, 7, PieceColor.BLACK);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_KingStrictlyBetweenRooks_WhiteBackRank() {
        BoardController controller = new BoardController(1L);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = kingStrictlyBetweenOwnRooksOnRank(snapshot, 0, PieceColor.WHITE);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_KingStrictlyBetweenRooks_BlackBackRank() {
        BoardController controller = new BoardController(1L);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = kingStrictlyBetweenOwnRooksOnRank(snapshot, 7, PieceColor.BLACK);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_BackRanksMirrorPieceTypes() {
        BoardController controller = new BoardController(1L);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960BackRanksMirrorPieceTypes(snapshot);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_StandardPawnRows() {
        BoardController controller = new BoardController(1L);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960StandardPawnRows(snapshot);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_OneQueenTwoKnightsOnBackRank_WhiteBackRank() {
        BoardController controller = new BoardController(1L);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960BackRankMajorPieceCounts(snapshot, 0, PieceColor.WHITE);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_OneQueenTwoKnightsOnBackRank_BlackBackRank() {
        BoardController controller = new BoardController(1L);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960BackRankMajorPieceCounts(snapshot, 7, PieceColor.BLACK);
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnWhitePiece_HasSelection() {
        BoardController controller = new BoardController();
        Location clicked = new Location(0, 1);

        controller.handleSquareClick(clicked);

        boolean expected = true;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnWhitePiece_SelectedLocationMatches() {
        BoardController controller = new BoardController();
        Location clicked = new Location(0, 1);

        controller.handleSquareClick(clicked);

        boolean expected = true;
        boolean actual = selectedLocationMatches(controller.getSelectedLocation(), clicked);
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnWhitePiece_BoardUnchanged() {
        BoardController controller = new BoardController();
        Location clicked = new Location(0, 1);

        controller.handleSquareClick(clicked);

        boolean expected = true;
        boolean actual =
                cellWiseSameTypeAndColor(newStandardStartingGrid(), controller.getBoardSnapshot());
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnBlackPiece_NoSelectionAfterClick() {
        BoardController controller = new BoardController();

        controller.handleSquareClick(new Location(0, 6));

        boolean expected = false;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnBlackPiece_TurnRemainsWhite() {
        BoardController controller = new BoardController();

        controller.handleSquareClick(new Location(0, 6));

        GameState expected = GameState.WHITE_TURN;
        GameState actual = controller.getCurrentGameState();
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnBlackPiece_BoardUnchanged() {
        BoardController controller = new BoardController();

        controller.handleSquareClick(new Location(0, 6));

        boolean expected = true;
        boolean actual =
                cellWiseSameTypeAndColor(newStandardStartingGrid(), controller.getBoardSnapshot());
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnEmptySquare_NoSelectionAfterClick() {
        BoardController controller = new BoardController();

        controller.handleSquareClick(new Location(3, 3));

        boolean expected = false;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnEmptySquare_TurnRemainsWhite() {
        BoardController controller = new BoardController();

        controller.handleSquareClick(new Location(3, 3));

        GameState expected = GameState.WHITE_TURN;
        GameState actual = controller.getCurrentGameState();
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnEmptySquare_BoardUnchanged() {
        BoardController controller = new BoardController();

        controller.handleSquareClick(new Location(3, 3));

        boolean expected = true;
        boolean actual =
                cellWiseSameTypeAndColor(newStandardStartingGrid(), controller.getBoardSnapshot());
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_InvalidLocation_NoSelectionAfterClick() {
        BoardController controller = new BoardController();

        controller.handleSquareClick(new Location(-1, 0));

        boolean expected = false;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_InvalidLocation_TurnRemainsWhite() {
        BoardController controller = new BoardController();

        controller.handleSquareClick(new Location(-1, 0));

        GameState expected = GameState.WHITE_TURN;
        GameState actual = controller.getCurrentGameState();
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_InvalidLocation_BoardUnchanged() {
        BoardController controller = new BoardController();

        controller.handleSquareClick(new Location(-1, 0));

        boolean expected = true;
        boolean actual =
                cellWiseSameTypeAndColor(newStandardStartingGrid(), controller.getBoardSnapshot());
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_Chess960Start_FirstWhiteSelectionSamePolicy() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);

        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = cellWiseSameTypeAndColor(newChess960FixedStartingGrid(), snapshot);
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_Chess960Start_FirstWhiteSelection_SelectsAndKeepsTurn() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);
        Location clicked = new Location(0, 1);

        controller.handleSquareClick(clicked);

        boolean expected = true;
        boolean actual = controller.hasSelection();
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_Chess960Start_FirstWhiteSelection_SelectedLocationMatches() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);
        Location clicked = new Location(0, 1);

        controller.handleSquareClick(clicked);

        boolean expected = true;
        boolean actual = selectedLocationMatches(controller.getSelectedLocation(), clicked);
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_Chess960Start_FirstWhiteSelection_TurnRemainsWhite() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);
        Location clicked = new Location(0, 1);

        controller.handleSquareClick(clicked);

        GameState expected = GameState.WHITE_TURN;
        GameState actual = controller.getCurrentGameState();
        assertEquals(expected, actual);
    }

    private static boolean isEightByEight(Piece[][] snapshot) {
        if (snapshot == null || snapshot.length != 8) {
            return false;
        }
        for (Piece[] row : snapshot) {
            if (row == null || row.length != 8) {
                return false;
            }
        }
        return true;
    }

    private static Piece[][] newStandardStartingGrid() {
        Piece[][] grid = emptyGrid();
        grid[0][0] = new Rook(PieceColor.WHITE);
        grid[0][1] = new Knight(PieceColor.WHITE);
        grid[0][2] = new Bishop(PieceColor.WHITE);
        grid[0][3] = new Queen(PieceColor.WHITE);
        grid[0][4] = new King(PieceColor.WHITE);
        grid[0][5] = new Bishop(PieceColor.WHITE);
        grid[0][6] = new Knight(PieceColor.WHITE);
        grid[0][7] = new Rook(PieceColor.WHITE);
        for (int file = 0; file < 8; file++) {
            grid[1][file] = new Pawn(PieceColor.WHITE);
        }

        for (int file = 0; file < 8; file++) {
            grid[6][file] = new Pawn(PieceColor.BLACK);
        }

        grid[7][0] = new Rook(PieceColor.BLACK);
        grid[7][1] = new Knight(PieceColor.BLACK);
        grid[7][2] = new Bishop(PieceColor.BLACK);
        grid[7][3] = new Queen(PieceColor.BLACK);
        grid[7][4] = new King(PieceColor.BLACK);
        grid[7][5] = new Bishop(PieceColor.BLACK);
        grid[7][6] = new Knight(PieceColor.BLACK);
        grid[7][7] = new Rook(PieceColor.BLACK);
        return grid;
    }

    /** Mirrors {@link BoardController} fixed Chess960 layout (BC-TC8 seed path); separate instances for comparisons. */
    private static Piece[][] newChess960FixedStartingGrid() {
        Piece[][] grid = emptyGrid();
        grid[0][0] = new Rook(PieceColor.WHITE);
        grid[0][1] = new Bishop(PieceColor.WHITE);
        grid[0][2] = new Knight(PieceColor.WHITE);
        grid[0][3] = new Queen(PieceColor.WHITE);
        grid[0][4] = new King(PieceColor.WHITE);
        grid[0][5] = new Knight(PieceColor.WHITE);
        grid[0][6] = new Bishop(PieceColor.WHITE);
        grid[0][7] = new Rook(PieceColor.WHITE);
        for (int file = 0; file < 8; file++) {
            grid[1][file] = new Pawn(PieceColor.WHITE);
        }

        for (int file = 0; file < 8; file++) {
            grid[6][file] = new Pawn(PieceColor.BLACK);
        }

        grid[7][0] = new Rook(PieceColor.BLACK);
        grid[7][1] = new Bishop(PieceColor.BLACK);
        grid[7][2] = new Knight(PieceColor.BLACK);
        grid[7][3] = new Queen(PieceColor.BLACK);
        grid[7][4] = new King(PieceColor.BLACK);
        grid[7][5] = new Knight(PieceColor.BLACK);
        grid[7][6] = new Bishop(PieceColor.BLACK);
        grid[7][7] = new Rook(PieceColor.BLACK);
        return grid;
    }

    private static boolean selectedLocationMatches(
            Optional<Location> actual, Location expected) {
        return actual.isPresent()
                && actual.get().getX() == expected.getX()
                && actual.get().getY() == expected.getY();
    }

    private static Piece[][] emptyGrid() {
        Piece[][] grid = new Piece[8][8];
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                grid[rank][file] = new NonePiece();
            }
        }
        return grid;
    }

    private static boolean cellWiseSameTypeAndColor(Piece[][] expected, Piece[][] actual) {
        if (!isEightByEight(expected) || !isEightByEight(actual)) {
            return false;
        }
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Piece e = expected[rank][file];
                Piece a = actual[rank][file];
                if (e.getType() != a.getType() || e.getColor() != a.getColor()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int countPiecesOfColor(Piece[][] snapshot, PieceColor color) {
        int count = 0;
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Piece piece = snapshot[rank][file];
                if (piece.getType() != PieceType.NONE && piece.getColor() == color) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean everyOccupiedPieceHasNotMoved(Piece[][] snapshot) {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Piece piece = snapshot[rank][file];
                if (piece.getType() != PieceType.NONE && piece.hasMoved()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean bishopsOnOppositeColorSquaresOnRank(
            Piece[][] snapshot, int rank, PieceColor color) {
        int firstFile = -1;
        int secondFile = -1;
        for (int file = 0; file < 8; file++) {
            Piece piece = snapshot[rank][file];
            if (piece.getType() == PieceType.BISHOP && piece.getColor() == color) {
                if (firstFile < 0) {
                    firstFile = file;
                } else if (secondFile < 0) {
                    secondFile = file;
                } else {
                    return false;
                }
            }
        }
        if (firstFile < 0 || secondFile < 0) {
            return false;
        }
        int parityFirst = (firstFile + rank) % 2;
        int paritySecond = (secondFile + rank) % 2;
        return parityFirst != paritySecond;
    }

    private static boolean kingStrictlyBetweenOwnRooksOnRank(
            Piece[][] snapshot, int rank, PieceColor color) {
        int rookA = -1;
        int rookB = -1;
        int kingFile = -1;
        for (int file = 0; file < 8; file++) {
            Piece piece = snapshot[rank][file];
            if (piece.getType() == PieceType.NONE || piece.getColor() != color) {
                continue;
            }
            if (piece.getType() == PieceType.ROOK) {
                if (rookA < 0) {
                    rookA = file;
                } else if (rookB < 0) {
                    rookB = file;
                } else {
                    return false;
                }
            } else if (piece.getType() == PieceType.KING) {
                if (kingFile >= 0) {
                    return false;
                }
                kingFile = file;
            }
        }
        if (rookA < 0 || rookB < 0 || kingFile < 0) {
            return false;
        }
        int rookMin = Math.min(rookA, rookB);
        int rookMax = Math.max(rookA, rookB);
        return kingFile > rookMin && kingFile < rookMax;
    }

    private static boolean chess960BackRanksMirrorPieceTypes(Piece[][] snapshot) {
        for (int file = 0; file < 8; file++) {
            Piece whiteBack = snapshot[0][file];
            Piece blackBack = snapshot[7][file];
            if (whiteBack.getType() == PieceType.NONE || blackBack.getType() == PieceType.NONE) {
                return false;
            }
            if (whiteBack.getType() != blackBack.getType()) {
                return false;
            }
            if (whiteBack.getColor() != PieceColor.WHITE || blackBack.getColor() != PieceColor.BLACK) {
                return false;
            }
        }
        return true;
    }

    private static boolean chess960StandardPawnRows(Piece[][] snapshot) {
        for (int file = 0; file < 8; file++) {
            Piece whitePawn = snapshot[1][file];
            Piece blackPawn = snapshot[6][file];
            if (whitePawn.getType() != PieceType.PAWN || whitePawn.getColor() != PieceColor.WHITE) {
                return false;
            }
            if (blackPawn.getType() != PieceType.PAWN || blackPawn.getColor() != PieceColor.BLACK) {
                return false;
            }
        }
        return true;
    }

    private static boolean chess960BackRankMajorPieceCounts(
            Piece[][] snapshot, int rank, PieceColor color) {
        int queens = 0;
        int knights = 0;
        int bishops = 0;
        int rooks = 0;
        int kings = 0;
        for (int file = 0; file < 8; file++) {
            Piece piece = snapshot[rank][file];
            if (piece.getType() == PieceType.NONE || piece.getColor() != color) {
                return false;
            }
            PieceType type = piece.getType();
            if (type == PieceType.PAWN) {
                return false;
            }
            if (type == PieceType.QUEEN) {
                queens++;
            } else if (type == PieceType.KNIGHT) {
                knights++;
            } else if (type == PieceType.BISHOP) {
                bishops++;
            } else if (type == PieceType.ROOK) {
                rooks++;
            } else if (type == PieceType.KING) {
                kings++;
            }
        }
        return queens == 1
                && knights == 2
                && bishops == 2
                && rooks == 2
                && kings == 1;
    }
}
