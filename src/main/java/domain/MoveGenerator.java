package domain;

import domain.location.Location;
import domain.move.Move;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import domain.piece.Piece;
import domain.piece.PieceType;

public class MoveGenerator {

    private static final int BOARD_SIZE = 8;
    private static final int[][] KNIGHT_OFFSETS = {
        {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}
    };

    private final Piece[][] board;
    private final Optional<Location> enPassantTarget;

    public MoveGenerator(Piece[][] board, Optional<Location> enPassantTarget) {
        this.board = board;
        this.enPassantTarget = enPassantTarget;
    }

    public List<Move> generateLegalMoves(Location from) {
        Piece piece = board[from.getY()][from.getX()];
        if (piece.getType() == PieceType.NONE) {
            return new ArrayList<>();
        }
        if (piece.getType() == PieceType.KNIGHT) {
            return generateKnightMoves(from, piece);
        }
        return new ArrayList<>();
    }

    private List<Move> generateKnightMoves(Location from, Piece knight) {
        List<Move> moves = new ArrayList<>();
        int rank = from.getY();
        int file = from.getX();
        for (int[] offset : KNIGHT_OFFSETS) {
            int targetRank = rank + offset[0];
            int targetFile = file + offset[1];
            if (!isOnBoard(targetRank, targetFile)) {
                continue;
            }
            Piece target = board[targetRank][targetFile];
            if (target.getType() == PieceType.NONE || target.getColor() != knight.getColor()) {
                moves.add(new Move(from, new Location(targetFile, targetRank)));
            }
        }
        return moves;
    }

    private static boolean isOnBoard(int rank, int file) {
        return rank >= 0 && rank < BOARD_SIZE && file >= 0 && file < BOARD_SIZE;
    }
}
