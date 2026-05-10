package domain.boardcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.gamestate.GameState;
import domain.location.Location;
import domain.piece.Bishop;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Queen;
import domain.piece.Rook;
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

        boolean expected = true;
        boolean actual = cornersHaveStandardRooks(snapshot);
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
    void GameStart_Standard_WhiteTurnAndNoPieceHasMoved() {
        BoardController controller = new BoardController();

        GameState expectedState = GameState.WHITE_TURN;
        GameState actualState = controller.getCurrentGameState();
        assertEquals(expectedState, actualState);

        boolean expected = true;
        boolean actual = everyOccupiedPieceHasNotMoved(controller.getBoardSnapshot());
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_BishopsOnOppositeColorSquares() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);

        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual =
                bishopsOnOppositeColorSquaresOnRank(snapshot, 0, PieceColor.WHITE)
                        && bishopsOnOppositeColorSquaresOnRank(snapshot, 7, PieceColor.BLACK);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_KingStrictlyBetweenRooksOnBackRank() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);

        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual =
                kingStrictlyBetweenOwnRooksOnRank(snapshot, 0, PieceColor.WHITE)
                        && kingStrictlyBetweenOwnRooksOnRank(snapshot, 7, PieceColor.BLACK);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_BackRanksMirrorPieceTypes() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);

        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual =
                chess960BackRanksMirrorPieceTypes(snapshot)
                        && chess960StandardPawnRows(snapshot);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_OneQueenTwoKnightsOnBackRank() {
        BoardController controller = new BoardController(StartingPositionKind.CHESS960);

        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual =
                chess960BackRankMajorPieceCounts(snapshot, 0, PieceColor.WHITE)
                        && chess960BackRankMajorPieceCounts(snapshot, 7, PieceColor.BLACK);
        assertEquals(expected, actual);
    }

    @Test
    void GetBoardSnapshot_Chess960_SeedOne_PassesTc8ThroughTc11() {
        BoardController controller = new BoardController(1L);
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = chess960SnapshotSatisfiesTc8ThroughTc11(snapshot);
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnWhitePiece_Selects() {
        BoardController controller = new BoardController();
        Location clicked = new Location(0, 1);

        controller.handleSquareClick(clicked);

        boolean expected = true;
        boolean actual =
                controller.hasSelection()
                        && locationsEqual(controller.getSelectedLocation(), clicked)
                        && cellWiseSameTypeAndColor(
                                newStandardStartingGrid(), controller.getBoardSnapshot());
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnBlackPiece_NoBoardMutation() {
        BoardController controller = new BoardController();

        controller.handleSquareClick(new Location(0, 6));

        boolean expected = true;
        boolean actual =
                !controller.hasSelection()
                        && controller.getCurrentGameState() == GameState.WHITE_TURN
                        && cellWiseSameTypeAndColor(
                                newStandardStartingGrid(), controller.getBoardSnapshot());
        assertEquals(expected, actual);
    }

    @Test
    void HandleSquareClick_BeforeFirstMove_OnEmptySquare_NoMutation() {
        BoardController controller = new BoardController();

        controller.handleSquareClick(new Location(3, 3));

        boolean expected = true;
        boolean actual =
                !controller.hasSelection()
                        && controller.getCurrentGameState() == GameState.WHITE_TURN
                        && cellWiseSameTypeAndColor(
                                newStandardStartingGrid(), controller.getBoardSnapshot());
        assertEquals(expected, actual);
    }

    private static boolean chess960SnapshotSatisfiesTc8ThroughTc11(Piece[][] snapshot) {
        return bishopsOnOppositeColorSquaresOnRank(snapshot, 0, PieceColor.WHITE)
                && bishopsOnOppositeColorSquaresOnRank(snapshot, 7, PieceColor.BLACK)
                && kingStrictlyBetweenOwnRooksOnRank(snapshot, 0, PieceColor.WHITE)
                && kingStrictlyBetweenOwnRooksOnRank(snapshot, 7, PieceColor.BLACK)
                && chess960BackRanksMirrorPieceTypes(snapshot)
                && chess960StandardPawnRows(snapshot)
                && chess960BackRankMajorPieceCounts(snapshot, 0, PieceColor.WHITE)
                && chess960BackRankMajorPieceCounts(snapshot, 7, PieceColor.BLACK);
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

    private static boolean cornersHaveStandardRooks(Piece[][] snapshot) {
        return isRookOfColor(snapshot[0][0], PieceColor.WHITE)
                && isRookOfColor(snapshot[0][7], PieceColor.WHITE)
                && isRookOfColor(snapshot[7][0], PieceColor.BLACK)
                && isRookOfColor(snapshot[7][7], PieceColor.BLACK);
    }

    private static boolean isRookOfColor(Piece piece, PieceColor color) {
        return piece != null
                && piece.getType() == PieceType.ROOK
                && piece.getColor() == color;
    }

    private static Piece[][] newStandardStartingGrid() {
        Piece[][] grid = new Piece[8][8];
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

    private static boolean locationsEqual(Location a, Location b) {
        return a.getX() == b.getX() && a.getY() == b.getY();
    }

    private static boolean cellWiseSameTypeAndColor(Piece[][] expected, Piece[][] actual) {
        if (!isEightByEight(expected) || !isEightByEight(actual)) {
            return false;
        }
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Piece e = expected[rank][file];
                Piece a = actual[rank][file];
                if (e == null && a == null) {
                    continue;
                }
                if (e == null || a == null) {
                    return false;
                }
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
                if (piece != null && piece.getColor() == color) {
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
                if (piece != null && piece.hasMoved()) {
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
            if (piece != null
                    && piece.getType() == PieceType.BISHOP
                    && piece.getColor() == color) {
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
            if (piece == null || piece.getColor() != color) {
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
            if (whiteBack == null || blackBack == null) {
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
            if (whitePawn == null
                    || whitePawn.getType() != PieceType.PAWN
                    || whitePawn.getColor() != PieceColor.WHITE) {
                return false;
            }
            if (blackPawn == null
                    || blackPawn.getType() != PieceType.PAWN
                    || blackPawn.getColor() != PieceColor.BLACK) {
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
            if (piece == null || piece.getColor() != color) {
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
