package domain.boardcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
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
}
