package domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import domain.location.Location;
import domain.piece.Knight;
import domain.piece.NonePiece;
import domain.piece.Piece;
import domain.piece.PieceColor;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class MoveGeneratorTest {

    private static final int BOARD_SIZE = 8;

    @Test
    void Constructor_WithBoardAndEmptyEnPassant_GenerateLegalMovesUsable() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Knight(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        assertNotNull(moveGenerator.generateLegalMoves(new Location(4, 4)));
    }

    private static Piece[][] emptyBoard() {
        Piece[][] board = new Piece[BOARD_SIZE][BOARD_SIZE];
        for (int rank = 0; rank < BOARD_SIZE; rank++) {
            for (int file = 0; file < BOARD_SIZE; file++) {
                board[rank][file] = new NonePiece();
            }
        }
        return board;
    }
}
