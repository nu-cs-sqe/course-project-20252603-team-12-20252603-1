package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    void IsInCheck_WhenKingNotAttacked_ReturnsFalse() {
        Piece[][] board = emptyBoard();
        board[4][4] = new King(PieceColor.WHITE);
        board[0][0] = new Rook(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = moveGenerator.isInCheck(PieceColor.WHITE);

        assertEquals(expected, actual);
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
    void GenerateAllLegalMovesForColor_WhenWhiteCheckmated_ReturnsZeroMoves() {
        Piece[][] board = emptyBoard();
        board[0][0] = new King(PieceColor.WHITE);
        board[0][7] = new Rook(PieceColor.BLACK);
        board[1][7] = new Rook(PieceColor.BLACK);
        board[7][0] = new King(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 0;
        int actual = moveGenerator.generateAllLegalMovesForColor(PieceColor.WHITE).size();

        assertEquals(expected, actual);
    }

    @Test
    void GenerateAllLegalMovesForColor_OnPawnWithOnlyOneStep_ReturnsOneMove() {
        Piece[][] board = emptyBoard();
        board[5][4] = new Pawn(PieceColor.WHITE);
        board[0][0] = new King(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 1;
        int actual = moveGenerator.generateAllLegalMovesForColor(PieceColor.WHITE).size();

        assertEquals(expected, actual);
    }

    @Test
    void GenerateAllLegalMovesForColor_OnKingAndBishop_ReturnsSeventeenMoves() {
        Piece[][] board = emptyBoard();
        board[2][2] = new King(PieceColor.WHITE);
        board[3][2] = new Bishop(PieceColor.WHITE);
        board[7][7] = new Rook(PieceColor.BLACK);
        board[0][0] = new King(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 17;
        int actual = moveGenerator.generateAllLegalMovesForColor(PieceColor.WHITE).size();

        assertEquals(expected, actual);
    }

    @Test
    void GenerateAllLegalMovesForColor_WhenOnlyKingInCheck_ReturnsSixMoves() {
        Piece[][] board = emptyBoard();
        board[4][4] = new King(PieceColor.WHITE);
        board[0][4] = new Rook(PieceColor.BLACK);
        board[7][7] = new King(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 6;
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
    void HasLegalMovesForColor_WhenInCheckWithLegalEscape_ReturnsTrue() {
        Piece[][] board = emptyBoard();
        board[4][4] = new King(PieceColor.WHITE);
        board[0][4] = new Rook(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = true;
        boolean actual = moveGenerator.hasLegalMovesForColor(PieceColor.WHITE);
        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnKingInCheck_ExcludesSquareStillInCheck() {
        Piece[][] board = emptyBoard();
        board[4][4] = new King(PieceColor.WHITE);
        board[0][4] = new Rook(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = hasMoveTo(
                moveGenerator.generateLegalMoves(new Location(4, 4)), 4, 3);
        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnKingInCheck_ReturnsSixEscapeMoves() {
        Piece[][] board = emptyBoard();
        board[4][4] = new King(PieceColor.WHITE);
        board[0][4] = new Rook(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 6;
        int actual = moveGenerator.generateLegalMoves(new Location(4, 4)).size();
        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnPinnedBishop_ExcludesMoveThatExposesKing() {
        Piece[][] board = emptyBoard();
        board[2][2] = new King(PieceColor.WHITE);
        board[3][2] = new Bishop(PieceColor.WHITE);
        board[7][2] = new Rook(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = hasMoveTo(moveGenerator.generateLegalMoves(new Location(2, 3)), 3, 4);
        assertEquals(expected, actual);
    }

    @Test
    void ApplyMoveToBoard_OnNormalMove_OriginalBoardUnchanged() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Knight(PieceColor.WHITE);
        Move move = new Move(new Location(4, 4), new Location(5, 6));

        MoveGenerator.applyMoveToBoard(board, move);

        PieceType expected = PieceType.KNIGHT;
        PieceType actual = board[4][4].getType();
        assertEquals(expected, actual);
    }

    @Test
    void ApplyMoveToBoard_OnNormalMove_SourceSquareIsEmpty() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Knight(PieceColor.WHITE);
        Move move = new Move(new Location(4, 4), new Location(5, 6));

        Piece[][] result = MoveGenerator.applyMoveToBoard(board, move);

        PieceType expected = PieceType.NONE;
        PieceType actual = result[4][4].getType();
        assertEquals(expected, actual);
    }

    @Test
    void ApplyMoveToBoard_OnPromotion_DestinationHasQueen() {
        Piece[][] board = emptyBoard();
        board[1][4] = new Pawn(PieceColor.WHITE);
        Move move = new Move(
                new Location(4, 1), new Location(4, 0), MoveType.PROMOTION, PieceType.QUEEN);

        Piece[][] result = MoveGenerator.applyMoveToBoard(board, move);

        PieceType expected = PieceType.QUEEN;
        PieceType actual = result[0][4].getType();
        assertEquals(expected, actual);
    }

    @Test
    void ApplyMoveToBoard_OnQueensideCastling_RelocatesKingAndRook() {
        Piece[][] board = emptyBoard();
        board[7][4] = new King(PieceColor.WHITE);
        board[7][0] = new Rook(PieceColor.WHITE);
        Move move = new Move(
                new Location(4, 7), new Location(2, 7), MoveType.CASTLING_QUEENSIDE);

        Piece[][] result = MoveGenerator.applyMoveToBoard(board, move);

        PieceType expectedKing = PieceType.KING;
        PieceType actualKing = result[7][2].getType();
        assertEquals(expectedKing, actualKing);

        PieceType expectedRook = PieceType.ROOK;
        PieceType actualRook = result[7][3].getType();
        assertEquals(expectedRook, actualRook);
    }

    @Test
    void ApplyMoveToBoard_OnKingsideCastling_RelocatesKingAndRook() {
        Piece[][] board = emptyBoard();
        board[7][4] = new King(PieceColor.WHITE);
        board[7][7] = new Rook(PieceColor.WHITE);
        Move move = new Move(
                new Location(4, 7), new Location(6, 7), MoveType.CASTLING_KINGSIDE);

        Piece[][] result = MoveGenerator.applyMoveToBoard(board, move);

        PieceType expectedKing = PieceType.KING;
        PieceType actualKing = result[7][6].getType();
        assertEquals(expectedKing, actualKing);

        PieceType expectedRook = PieceType.ROOK;
        PieceType actualRook = result[7][5].getType();
        assertEquals(expectedRook, actualRook);
    }

    @Test
    void ApplyMoveToBoard_OnEnPassant_RemovesCapturedPawn() {
        Piece[][] board = emptyBoard();
        board[3][4] = new Pawn(PieceColor.WHITE);
        board[3][5] = new Pawn(PieceColor.BLACK);
        Move move = new Move(new Location(4, 3), new Location(5, 2), MoveType.EN_PASSANT);

        Piece[][] result = MoveGenerator.applyMoveToBoard(board, move);

        PieceType expected = PieceType.NONE;
        PieceType actual = result[3][5].getType();
        assertEquals(expected, actual);
    }

    @Test
    void ApplyMoveToBoard_OnNormalMove_DestinationHasMovingPiece() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Knight(PieceColor.WHITE);
        Move move = new Move(new Location(4, 4), new Location(5, 6));

        Piece[][] result = MoveGenerator.applyMoveToBoard(board, move);

        PieceType expected = PieceType.KNIGHT;
        PieceType actual = result[6][5].getType();
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

    @Test
    void GenerateLegalMoves_OnWhitePawnWithEnPassantTarget_IncludesEnPassantMove() {
        Piece[][] board = emptyBoard();
        board[3][4] = new Pawn(PieceColor.WHITE);
        MoveGenerator moveGenerator =
                new MoveGenerator(board, Optional.of(new Location(5, 2)));

        boolean expected = true;
        boolean actual = hasMoveToWithType(
                moveGenerator.generateLegalMoves(new Location(4, 3)),
                5,
                2,
                MoveType.EN_PASSANT);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnWhitePawnCaptureToBackRank_ReturnsEightMoves() {
        Piece[][] board = emptyBoard();
        board[1][4] = new Pawn(PieceColor.WHITE);
        board[0][5] = new Rook(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 8;
        int actual = moveGenerator.generateLegalMoves(new Location(4, 1)).size();

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnWhitePawnWithoutEnPassantTarget_ExcludesEnPassantMove() {
        Piece[][] board = emptyBoard();
        board[3][4] = new Pawn(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = hasMoveType(
                moveGenerator.generateLegalMoves(new Location(4, 3)),
                MoveType.EN_PASSANT);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnEnPassantTargetWrongRank_ExcludesEnPassantMove() {
        Piece[][] board = emptyBoard();
        board[3][4] = new Pawn(PieceColor.WHITE);
        MoveGenerator moveGenerator =
                new MoveGenerator(board, Optional.of(new Location(5, 4)));

        boolean expected = false;
        boolean actual = hasMoveType(
                moveGenerator.generateLegalMoves(new Location(4, 3)),
                MoveType.EN_PASSANT);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnUnmovedKingWithClearKingsidePath_IncludesKingsideCastling() {
        Piece[][] board = emptyBoard();
        board[7][4] = new King(PieceColor.WHITE);
        board[7][7] = new Rook(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = true;
        boolean actual = hasMoveToWithType(
                moveGenerator.generateLegalMoves(new Location(4, 7)),
                6,
                7,
                MoveType.CASTLING_KINGSIDE);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnMovedKing_ExcludesCastlingMoves() {
        Piece[][] board = emptyBoard();
        board[7][4] = new King(PieceColor.WHITE);
        board[7][4].changeToMoved();
        board[7][7] = new Rook(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = hasMoveType(
                moveGenerator.generateLegalMoves(new Location(4, 7)),
                MoveType.CASTLING_KINGSIDE);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnUnmovedKingWithClearQueensidePath_IncludesQueensideCastling() {
        Piece[][] board = emptyBoard();
        board[7][4] = new King(PieceColor.WHITE);
        board[7][0] = new Rook(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = true;
        boolean actual = hasMoveToWithType(
                moveGenerator.generateLegalMoves(new Location(4, 7)),
                2,
                7,
                MoveType.CASTLING_QUEENSIDE);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnMovedKingsideRook_ExcludesKingsideCastling() {
        Piece[][] board = emptyBoard();
        board[7][4] = new King(PieceColor.WHITE);
        board[7][7] = new Rook(PieceColor.WHITE);
        board[7][7].changeToMoved();
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = hasMoveType(
                moveGenerator.generateLegalMoves(new Location(4, 7)),
                MoveType.CASTLING_KINGSIDE);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnKingsidePathSquareUnderAttack_ExcludesKingsideCastling() {
        Piece[][] board = emptyBoard();
        board[7][4] = new King(PieceColor.WHITE);
        board[7][7] = new Rook(PieceColor.WHITE);
        board[0][5] = new Rook(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = hasMoveType(
                moveGenerator.generateLegalMoves(new Location(4, 7)),
                MoveType.CASTLING_KINGSIDE);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnWhitePawnWithEnemyDiagonal_ReturnsTwoMoves() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Pawn(PieceColor.WHITE);
        board[3][5] = new Rook(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 2;
        int actual = moveGenerator.generateLegalMoves(new Location(4, 4)).size();

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnWhitePawnOneStepFromBackRank_ReturnsFourMoves() {
        Piece[][] board = emptyBoard();
        board[1][4] = new Pawn(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 4;
        int actual = moveGenerator.generateLegalMoves(new Location(4, 1)).size();

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnWhitePawnWithEnPassantTarget_ReturnsTwoMoves() {
        Piece[][] board = emptyBoard();
        board[3][4] = new Pawn(PieceColor.WHITE);
        MoveGenerator moveGenerator =
                new MoveGenerator(board, Optional.of(new Location(5, 2)));

        int expected = 2;
        int actual = moveGenerator.generateLegalMoves(new Location(4, 3)).size();

        assertEquals(expected, actual);
    }

    @Test
    void IsInCheck_WhenNoKingForColor_ReturnsFalse() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Rook(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = moveGenerator.isInCheck(PieceColor.WHITE);

        assertEquals(expected, actual);
    }

    @Test
    void IsInCheck_WhenPawnAttacksKing_ReturnsTrue() {
        Piece[][] board = emptyBoard();
        board[4][4] = new King(PieceColor.BLACK);
        board[5][3] = new Pawn(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = true;
        boolean actual = moveGenerator.isInCheck(PieceColor.BLACK);

        assertEquals(expected, actual);
    }

    @Test
    void IsInCheck_WhenKnightAttacksKing_ReturnsTrue() {
        Piece[][] board = emptyBoard();
        board[4][4] = new King(PieceColor.WHITE);
        board[5][6] = new Knight(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = true;
        boolean actual = moveGenerator.isInCheck(PieceColor.WHITE);

        assertEquals(expected, actual);
    }

    @Test
    void IsInCheck_WhenBishopAttacksKing_ReturnsTrue() {
        Piece[][] board = emptyBoard();
        board[4][4] = new King(PieceColor.WHITE);
        board[0][0] = new Bishop(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = true;
        boolean actual = moveGenerator.isInCheck(PieceColor.WHITE);

        assertEquals(expected, actual);
    }

    @Test
    void IsInCheck_WhenQueenAttacksKing_ReturnsTrue() {
        Piece[][] board = emptyBoard();
        board[4][4] = new King(PieceColor.WHITE);
        board[0][4] = new Queen(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = true;
        boolean actual = moveGenerator.isInCheck(PieceColor.WHITE);

        assertEquals(expected, actual);
    }

    @Test
    void IsInCheck_WhenAdjacentEnemyKingAttacksKing_ReturnsTrue() {
        Piece[][] board = emptyBoard();
        board[4][4] = new King(PieceColor.WHITE);
        board[5][5] = new King(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = true;
        boolean actual = moveGenerator.isInCheck(PieceColor.WHITE);

        assertEquals(expected, actual);
    }

    @Test
    void CreatePiece_OnRookType_ReturnsRook() {
        PieceType expected = PieceType.ROOK;
        PieceType actual = MoveGenerator.createPiece(PieceType.ROOK, PieceColor.WHITE).getType();

        assertEquals(expected, actual);
    }

    @Test
    void CreatePiece_OnBishopType_ReturnsBishop() {
        PieceType expected = PieceType.BISHOP;
        PieceType actual = MoveGenerator.createPiece(PieceType.BISHOP, PieceColor.WHITE).getType();

        assertEquals(expected, actual);
    }

    @Test
    void CreatePiece_OnKnightType_ReturnsKnight() {
        PieceType expected = PieceType.KNIGHT;
        PieceType actual = MoveGenerator.createPiece(PieceType.KNIGHT, PieceColor.WHITE).getType();

        assertEquals(expected, actual);
    }

    @Test
    void CreatePiece_OnPawnType_ReturnsPawn() {
        PieceType expected = PieceType.PAWN;
        PieceType actual = MoveGenerator.createPiece(PieceType.PAWN, PieceColor.WHITE).getType();

        assertEquals(expected, actual);
    }

    @Test
    void CreatePiece_OnKingType_ReturnsKing() {
        PieceType expected = PieceType.KING;
        PieceType actual = MoveGenerator.createPiece(PieceType.KING, PieceColor.WHITE).getType();

        assertEquals(expected, actual);
    }

    @Test
    void CreatePiece_OnNoneType_ReturnsNonePiece() {
        PieceType expected = PieceType.NONE;
        PieceType actual = MoveGenerator.createPiece(PieceType.NONE, PieceColor.WHITE).getType();

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnEmptySquare_ReturnsMutableEmptyList() {
        Piece[][] board = emptyBoard();
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        java.util.List<Move> moves = moveGenerator.generateLegalMoves(new Location(3, 3));
        int sizeBefore = moves.size();
        moves.add(new Move(new Location(0, 0), new Location(1, 1)));

        int expected = sizeBefore + 1;
        int actual = moves.size();

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnKnightBlockedByFriendly_ExcludesBlockedSquare() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Knight(PieceColor.WHITE);
        board[6][5] = new Pawn(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = hasMoveTo(
                moveGenerator.generateLegalMoves(new Location(4, 4)), 5, 6);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnRookBlockedByFriendly_ExcludesSquareBeyondFriendly() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Rook(PieceColor.WHITE);
        board[6][4] = new Pawn(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = hasMoveTo(
                moveGenerator.generateLegalMoves(new Location(4, 4)), 4, 7);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnKingBlockedByFriendly_ExcludesFriendlySquare() {
        Piece[][] board = emptyBoard();
        board[4][4] = new King(PieceColor.WHITE);
        board[5][5] = new Pawn(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = hasMoveTo(
                moveGenerator.generateLegalMoves(new Location(4, 4)), 5, 5);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnKingsideCastlingWithBlockingPiece_ExcludesKingsideCastling() {
        Piece[][] board = emptyBoard();
        board[7][4] = new King(PieceColor.WHITE);
        board[7][7] = new Rook(PieceColor.WHITE);
        board[7][5] = new Bishop(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = hasMoveType(
                moveGenerator.generateLegalMoves(new Location(4, 7)),
                MoveType.CASTLING_KINGSIDE);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnQueensideCastlingWithBlockingPiece_ExcludesQueensideCastling() {
        Piece[][] board = emptyBoard();
        board[7][4] = new King(PieceColor.WHITE);
        board[7][0] = new Rook(PieceColor.WHITE);
        board[7][1] = new Knight(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = hasMoveType(
                moveGenerator.generateLegalMoves(new Location(4, 7)),
                MoveType.CASTLING_QUEENSIDE);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnMovedQueensideRook_ExcludesQueensideCastling() {
        Piece[][] board = emptyBoard();
        board[7][4] = new King(PieceColor.WHITE);
        board[7][0] = new Rook(PieceColor.WHITE);
        board[7][0].changeToMoved();
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = hasMoveType(
                moveGenerator.generateLegalMoves(new Location(4, 7)),
                MoveType.CASTLING_QUEENSIDE);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnEnPassantTargetSameFile_ExcludesEnPassantMove() {
        Piece[][] board = emptyBoard();
        board[3][4] = new Pawn(PieceColor.WHITE);
        MoveGenerator moveGenerator =
                new MoveGenerator(board, Optional.of(new Location(4, 2)));

        boolean expected = false;
        boolean actual = hasMoveType(
                moveGenerator.generateLegalMoves(new Location(4, 3)),
                MoveType.EN_PASSANT);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnWhitePawnAtStart_IncludesTwoStepDestination() {
        Piece[][] board = emptyBoard();
        board[6][4] = new Pawn(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = true;
        boolean actual = hasMoveTo(
                moveGenerator.generateLegalMoves(new Location(4, 6)), 4, 4);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnWhitePawnAtStartWithBlockerAtTwoAhead_ExcludesTwoStep() {
        Piece[][] board = emptyBoard();
        board[6][4] = new Pawn(PieceColor.WHITE);
        board[4][4] = new Rook(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        boolean expected = false;
        boolean actual = hasMoveTo(
                moveGenerator.generateLegalMoves(new Location(4, 6)), 4, 4);

        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnWhitePawnWithFriendlyDiagonal_SkipsDiagonalCapture() {
        Piece[][] board = emptyBoard();
        board[4][4] = new Pawn(PieceColor.WHITE);
        board[3][5] = new Pawn(PieceColor.WHITE);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 1;
        int actual = moveGenerator.generateLegalMoves(new Location(4, 4)).size();

        assertEquals(expected, actual);
    }

    @Test
    void ApplyMoveToBoard_OnPromotionRook_DestinationHasRook() {
        Piece[][] board = emptyBoard();
        board[1][4] = new Pawn(PieceColor.WHITE);
        Move move = new Move(
                new Location(4, 1), new Location(4, 0), MoveType.PROMOTION, PieceType.ROOK);

        Piece[][] result = MoveGenerator.applyMoveToBoard(board, move);

        PieceType expected = PieceType.ROOK;
        PieceType actual = result[0][4].getType();
        assertEquals(expected, actual);
    }

    @Test
    void ApplyMoveToBoard_OnPromotionBishop_DestinationHasBishop() {
        Piece[][] board = emptyBoard();
        board[1][4] = new Pawn(PieceColor.WHITE);
        Move move = new Move(
                new Location(4, 1), new Location(4, 0), MoveType.PROMOTION, PieceType.BISHOP);

        Piece[][] result = MoveGenerator.applyMoveToBoard(board, move);

        PieceType expected = PieceType.BISHOP;
        PieceType actual = result[0][4].getType();
        assertEquals(expected, actual);
    }

    @Test
    void ApplyMoveToBoard_OnPromotionKnight_DestinationHasKnight() {
        Piece[][] board = emptyBoard();
        board[1][4] = new Pawn(PieceColor.WHITE);
        Move move = new Move(
                new Location(4, 1), new Location(4, 0), MoveType.PROMOTION, PieceType.KNIGHT);

        Piece[][] result = MoveGenerator.applyMoveToBoard(board, move);

        PieceType expected = PieceType.KNIGHT;
        PieceType actual = result[0][4].getType();
        assertEquals(expected, actual);
    }

    @Test
    void GenerateLegalMoves_OnBlackPawnAtStart_ReturnsOneAndTwoStepMoves() {
        Piece[][] board = emptyBoard();
        board[1][4] = new Pawn(PieceColor.BLACK);
        MoveGenerator moveGenerator = new MoveGenerator(board, Optional.empty());

        int expected = 2;
        int actual = moveGenerator.generateLegalMoves(new Location(4, 1)).size();

        assertEquals(expected, actual);
    }

    private static boolean hasMoveToWithType(
            java.util.List<Move> moves, int file, int rank, MoveType type) {
        for (Move move : moves) {
            if (move.getTo().getX() == file
                    && move.getTo().getY() == rank
                    && move.getType() == type) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasMoveType(java.util.List<Move> moves, MoveType type) {
        for (Move move : moves) {
            if (move.getType() == type) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasMoveTo(java.util.List<Move> moves, int file, int rank) {
        for (Move move : moves) {
            if (move.getTo().getX() == file && move.getTo().getY() == rank) {
                return true;
            }
        }
        return false;
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
