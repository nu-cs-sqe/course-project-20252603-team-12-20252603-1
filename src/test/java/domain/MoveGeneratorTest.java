package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import domain.location.Location;
import domain.piece.Bishop;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.Queen;
import domain.piece.Rook;
import domain.piece.NonePiece;
import domain.piece.Piece;
import domain.piece.Pawn;
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

    @Test
    void IsInCheck_WhenRookAttacksKing_ReturnsTrue() {
        Piece[][] board = emptyBoard();
        board[4][4] = new King(PieceColor.WHITE);
        board[0][4] = new Rook(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = true;
        boolean actual = moveGenerator.isInCheck(PieceColor.WHITE);

        assertEquals(expected, actual);
    }

    @Test
    void HasLegalMovesForColor_OnMovableWhitePiece_ReturnsTrue() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Knight(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = true;
        boolean actual = moveGenerator.hasLegalMovesForColor(PieceColor.WHITE);

        assertEquals(expected, actual);
    }

    @Test
    void HasLegalMovesForColor_OnNoPiecesForColor_ReturnsFalse() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Knight(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = moveGenerator.hasLegalMovesForColor(PieceColor.BLACK);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateAllLegalMovesForColor_OnSingleWhiteKnight_ReturnsEightMoves() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Knight(PieceColor.WHITE);
        board[0][0] = new King(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 8;
        int actual = moveGenerator.generateAllLegalMovesForColor(PieceColor.WHITE).size();

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnWhitePawnAtStart_ReturnsOneAndTwoStepMoves() {
        Piece[][] board = emptyBoard();
        board[6][4] = new Pawn(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 2;
        int actual = moveGenerator.generateLegalMoves(new Location(4, 6)).size();

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnKingAtCenter_ReturnsEightMoves() {
        Piece[][] board = emptyBoard();
        board[4][4] = new King(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 8;
        int actual = moveGenerator.generateLegalMoves(new Location(4, 4)).size();

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnQueenAtCenter_ReturnsTwentySevenMoves() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Queen(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 27;
        int actual = moveGenerator.generateLegalMoves(new Location(4, 4)).size();

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnRookAtCenter_ReturnsFourteenMoves() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Rook(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 14;
        int actual = moveGenerator.generateLegalMoves(new Location(4, 4)).size();

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnBishopAtCenter_ReturnsThirteenMoves() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Bishop(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 13;
        int actual = moveGenerator.generateLegalMoves(new Location(4, 4)).size();

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnKnightAtCenter_ReturnsEightMoves() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Knight(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 8;
        int actual = moveGenerator.generateLegalMoves(new Location(4, 4)).size();

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnEmptySquare_ReturnsEmptyList() {
        Piece[][] board = emptyBoard();
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 0;
        int actual = moveGenerator.generateLegalMoves(new Location(3, 3)).size();

        assertEquals(expected, actual);
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
