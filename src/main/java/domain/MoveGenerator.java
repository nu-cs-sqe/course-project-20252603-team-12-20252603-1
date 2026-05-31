package domain;

import domain.location.Location;
import domain.move.Move;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import domain.piece.NonePiece;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;

public class MoveGenerator {

    private static final int BOARD_SIZE = 8;
    private static final int[][] EIGHT_DIRECTIONS = {
        {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}
    };
    private static final int[][] ROOK_DIRS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final int[][] BISHOP_DIRS = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
    private static final int[][] KNIGHT_OFFSETS = {
        {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}
    };

    private final Piece[][] board;
    private final Optional<Location> enPassantTarget;

    public MoveGenerator(Piece[][] board, Optional<Location> enPassantTarget) {
        this.board = board;
        this.enPassantTarget = enPassantTarget;
    }

    public boolean isInCheck(PieceColor color) {
        Location kingLocation = findKing(color, board);
        if (kingLocation == null) {
            return false;
        }
        return isSquareAttackedBy(kingLocation, opponent(color), board);
    }

    public boolean hasLegalMovesForColor(PieceColor color) {
        for (int rank = 0; rank < BOARD_SIZE; rank++) {
            for (int file = 0; file < BOARD_SIZE; file++) {
                Piece piece = board[rank][file];
                if (piece.getType() != PieceType.NONE && piece.getColor() == color) {
                    if (!generateLegalMoves(new Location(file, rank)).isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<Move> generateAllLegalMovesForColor(PieceColor color) {
        List<Move> moves = new ArrayList<>();
        for (int rank = 0; rank < BOARD_SIZE; rank++) {
            for (int file = 0; file < BOARD_SIZE; file++) {
                Piece piece = board[rank][file];
                if (piece.getType() != PieceType.NONE && piece.getColor() == color) {
                    moves.addAll(generateLegalMoves(new Location(file, rank)));
                }
            }
        }
        return moves;
    }

    public List<Move> generateLegalMoves(Location from) {
        Piece piece = board[from.getY()][from.getX()];
        if (piece.getType() == PieceType.NONE) {
            return new ArrayList<>();
        }
        List<Move> pseudoLegal = generatePseudoLegalMoves(from, piece);
        return filterLegalMoves(pseudoLegal, piece.getColor());
    }

    private List<Move> generatePseudoLegalMoves(Location from, Piece piece) {
        if (piece.getType() == PieceType.KNIGHT) {
            return generateKnightMoves(from, piece);
        }
        if (piece.getType() == PieceType.BISHOP) {
            return generateSlidingMoves(from, piece, BISHOP_DIRS);
        }
        if (piece.getType() == PieceType.ROOK) {
            return generateSlidingMoves(from, piece, ROOK_DIRS);
        }
        if (piece.getType() == PieceType.QUEEN) {
            return generateSlidingMoves(from, piece, EIGHT_DIRECTIONS);
        }
        if (piece.getType() == PieceType.KING) {
            return generateKingMoves(from, piece);
        }
        if (piece.getType() == PieceType.PAWN) {
            return generatePawnMoves(from, piece);
        }
        return new ArrayList<>();
    }

    private List<Move> filterLegalMoves(List<Move> pseudoLegal, PieceColor movingColor) {
        List<Move> legal = new ArrayList<>();
        for (Move move : pseudoLegal) {
            if (!leavesOwnKingInCheck(move, movingColor)) {
                legal.add(move);
            }
        }
        return legal;
    }

    private boolean leavesOwnKingInCheck(Move move, PieceColor movingColor) {
        Piece[][] boardAfter = applyMoveToBoard(board, move);
        return new MoveGenerator(boardAfter, Optional.empty()).isInCheck(movingColor);
    }

    static Piece[][] applyMoveToBoard(Piece[][] original, Move move) {
        Piece[][] copy = deepCopy(original);
        int fromRank = move.getFrom().getY();
        int fromFile = move.getFrom().getX();
        int toRank = move.getTo().getY();
        int toFile = move.getTo().getX();
        copy[toRank][toFile] = copy[fromRank][fromFile];
        copy[fromRank][fromFile] = new NonePiece();
        return copy;
    }

    private static Piece[][] deepCopy(Piece[][] original) {
        Piece[][] copy = new Piece[BOARD_SIZE][BOARD_SIZE];
        for (int rank = 0; rank < BOARD_SIZE; rank++) {
            for (int file = 0; file < BOARD_SIZE; file++) {
                copy[rank][file] = original[rank][file].makeCopy();
            }
        }
        return copy;
    }

    private List<Move> generatePawnMoves(Location from, Piece pawn) {
        List<Move> moves = new ArrayList<>();
        int rank = from.getY();
        int file = from.getX();
        PieceColor color = pawn.getColor();
        int direction = (color == PieceColor.WHITE) ? -1 : 1;
        int startRank = (color == PieceColor.WHITE) ? 6 : 1;

        int oneAhead = rank + direction;
        if (!isOnBoard(oneAhead, file)) {
            return moves;
        }
        if (board[oneAhead][file].getType() != PieceType.NONE) {
            return moves;
        }
        moves.add(new Move(from, new Location(file, oneAhead)));

        int twoAhead = rank + 2 * direction;
        if (rank == startRank
                && isOnBoard(twoAhead, file)
                && board[twoAhead][file].getType() == PieceType.NONE) {
            moves.add(new Move(from, new Location(file, twoAhead)));
        }
        return moves;
    }

    private List<Move> generateKingMoves(Location from, Piece king) {
        List<Move> moves = new ArrayList<>();
        int rank = from.getY();
        int file = from.getX();
        for (int[] offset : EIGHT_DIRECTIONS) {
            int targetRank = rank + offset[0];
            int targetFile = file + offset[1];
            if (!isOnBoard(targetRank, targetFile)) {
                continue;
            }
            Piece target = board[targetRank][targetFile];
            if (target.getType() == PieceType.NONE || target.getColor() != king.getColor()) {
                moves.add(new Move(from, new Location(targetFile, targetRank)));
            }
        }
        return moves;
    }

    private List<Move> generateSlidingMoves(Location from, Piece piece, int[][] directions) {
        List<Move> moves = new ArrayList<>();
        int rank = from.getY();
        int file = from.getX();
        for (int[] direction : directions) {
            int currentRank = rank + direction[0];
            int currentFile = file + direction[1];
            while (isOnBoard(currentRank, currentFile)) {
                Piece target = board[currentRank][currentFile];
                if (target.getType() == PieceType.NONE) {
                    moves.add(new Move(from, new Location(currentFile, currentRank)));
                    currentRank += direction[0];
                    currentFile += direction[1];
                    continue;
                }
                if (target.getColor() != piece.getColor()) {
                    moves.add(new Move(from, new Location(currentFile, currentRank)));
                }
                break;
            }
        }
        return moves;
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

    private static boolean isSquareAttackedBy(
            Location square, PieceColor attackerColor, Piece[][] board) {
        return isSliderAttacking(square, attackerColor, board, ROOK_DIRS,
                PieceType.ROOK, PieceType.QUEEN)
            || isSliderAttacking(square, attackerColor, board, BISHOP_DIRS,
                PieceType.BISHOP, PieceType.QUEEN)
            || isKnightAttacking(square, attackerColor, board)
            || isPawnAttacking(square, attackerColor, board)
            || isKingAttacking(square, attackerColor, board);
    }

    private static boolean isSliderAttacking(
            Location square,
            PieceColor attackerColor,
            Piece[][] board,
            int[][] directions,
            PieceType... sliderTypes) {
        int rank = square.getY();
        int file = square.getX();
        for (int[] direction : directions) {
            int currentRank = rank + direction[0];
            int currentFile = file + direction[1];
            while (isOnBoard(currentRank, currentFile)) {
                Piece piece = board[currentRank][currentFile];
                if (piece.getType() == PieceType.NONE) {
                    currentRank += direction[0];
                    currentFile += direction[1];
                    continue;
                }
                if (piece.getColor() == attackerColor) {
                    for (PieceType sliderType : sliderTypes) {
                        if (piece.getType() == sliderType) {
                            return true;
                        }
                    }
                }
                break;
            }
        }
        return false;
    }

    private static boolean isKnightAttacking(
            Location square, PieceColor attackerColor, Piece[][] board) {
        int rank = square.getY();
        int file = square.getX();
        for (int[] offset : KNIGHT_OFFSETS) {
            int targetRank = rank + offset[0];
            int targetFile = file + offset[1];
            if (!isOnBoard(targetRank, targetFile)) {
                continue;
            }
            Piece piece = board[targetRank][targetFile];
            if (piece.getType() == PieceType.KNIGHT && piece.getColor() == attackerColor) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPawnAttacking(
            Location square, PieceColor attackerColor, Piece[][] board) {
        int rank = square.getY();
        int file = square.getX();
        int pawnRank = rank + (attackerColor == PieceColor.WHITE ? 1 : -1);
        for (int df : new int[] {-1, 1}) {
            int pawnFile = file + df;
            if (isOnBoard(pawnRank, pawnFile)) {
                Piece piece = board[pawnRank][pawnFile];
                if (piece.getType() == PieceType.PAWN && piece.getColor() == attackerColor) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isKingAttacking(
            Location square, PieceColor attackerColor, Piece[][] board) {
        int rank = square.getY();
        int file = square.getX();
        for (int[] offset : EIGHT_DIRECTIONS) {
            int targetRank = rank + offset[0];
            int targetFile = file + offset[1];
            if (!isOnBoard(targetRank, targetFile)) {
                continue;
            }
            Piece piece = board[targetRank][targetFile];
            if (piece.getType() == PieceType.KING && piece.getColor() == attackerColor) {
                return true;
            }
        }
        return false;
    }

    private static Location findKing(PieceColor color, Piece[][] board) {
        for (int rank = 0; rank < BOARD_SIZE; rank++) {
            for (int file = 0; file < BOARD_SIZE; file++) {
                Piece piece = board[rank][file];
                if (piece.getType() == PieceType.KING && piece.getColor() == color) {
                    return new Location(file, rank);
                }
            }
        }
        return null;
    }

    private static PieceColor opponent(PieceColor color) {
        return color == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
    }
}
