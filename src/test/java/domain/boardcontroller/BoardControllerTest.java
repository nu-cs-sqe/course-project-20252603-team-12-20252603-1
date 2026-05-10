package domain.boardcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
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
        Piece[][] expectedGrid = newStandardCornerRookSeedGrid();
        BoardController controller = new BoardController();
        Piece[][] snapshot = controller.getBoardSnapshot();

        boolean expected = true;
        boolean actual = cellWiseSameTypeAndColor(expectedGrid, snapshot);
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

    private static Piece[][] newStandardCornerRookSeedGrid() {
        Piece[][] grid = new Piece[8][8];
        grid[0][0] = new Rook(PieceColor.WHITE);
        grid[0][7] = new Rook(PieceColor.WHITE);
        grid[7][0] = new Rook(PieceColor.BLACK);
        grid[7][7] = new Rook(PieceColor.BLACK);
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
}
