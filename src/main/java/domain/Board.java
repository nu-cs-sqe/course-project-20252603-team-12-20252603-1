package domain;

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
import java.util.List;
import java.util.Optional;

public class Board {

    private static final int BOARD_SIZE = 8;
    private static final int BLACK_RANK_ROWS = 4;
    private static final int KINGSIDE_KING_DEST_FILE = 6;
    private static final int QUEENSIDE_KING_DEST_FILE = 2;
    private static final int KINGSIDE_ROOK_DEST_FILE = 5;
    private static final int QUEENSIDE_ROOK_DEST_FILE = 3;

    private final Piece[][] pieces = new Piece[BOARD_SIZE][BOARD_SIZE];
    private GameState currentGameState = GameState.WHITE_TURN;
    private Optional<Location> enPassantTarget = Optional.empty();

    void setEnPassantTarget(Optional<Location> enPassantTarget) {
        this.enPassantTarget = enPassantTarget;
    }

    public Board(Piece[][] initialPieces) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                pieces[row][col] = initialPieces[row][col].makeCopy();
            }
        }
    }

    public Board(BoardInitializer initializer) {
        PieceType[][] layout = initializer.getBoardLayout();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                PieceColor color = (row < BLACK_RANK_ROWS) ? PieceColor.BLACK : PieceColor.WHITE;
                pieces[row][col] = createPiece(layout[row][col], color);
            }
        }
    }

    private Piece createPiece(PieceType type, PieceColor color) {
        switch (type) {
            case ROOK: return new Rook(color);
            case KNIGHT: return new Knight(color);
            case BISHOP: return new Bishop(color);
            case QUEEN: return new Queen(color);
            case KING: return new King(color);
            case PAWN: return new Pawn(color);
            default: return new NonePiece();
        }
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public Optional<Location> getEnPassantTarget() {
        return enPassantTarget;
    }

    public void switchTurn() {
        if (currentGameState == GameState.WHITE_TURN) {
            currentGameState = GameState.BLACK_TURN;
        } else {
            currentGameState = GameState.WHITE_TURN;
        }
    }

    public Piece getPieceAt(int rank, int file) {
        return pieces[rank][file].makeCopy();
    }

    public Piece[][] getSnapshot() {
        Piece[][] snapshot = new Piece[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                snapshot[row][col] = pieces[row][col].makeCopy();
            }
        }
        return snapshot;
    }

    public List<Move> getLegalMoves(Location from) {
        return new MoveGenerator(pieces, enPassantTarget).generateLegalMoves(from);
    }

    public void makeMove(Move move) {
        Piece movingPiece = pieces[move.getFrom().getY()][move.getFrom().getX()];
        applyMoveToInternalState(move);
        updateEnPassantTarget(move, movingPiece);
        if (moveCreatesCheckmate(movingPiece.getColor())) {
            currentGameState =
                    movingPiece.getColor() == PieceColor.WHITE
                            ? GameState.WHITE_WIN : GameState.BLACK_WIN;
            return;
        }
        switchTurn();
    }

    private boolean moveCreatesCheckmate(PieceColor movingColor) {
        PieceColor opponentColor =
                movingColor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
        MoveGenerator moveGenerator = new MoveGenerator(pieces, enPassantTarget);
        return moveGenerator.isInCheck(opponentColor)
                && !moveGenerator.hasLegalMovesForColor(opponentColor);
    }

    private void applyMoveToInternalState(Move move) {
        int fromRank = move.getFrom().getY();
        int fromFile = move.getFrom().getX();
        int toRank = move.getTo().getY();
        int toFile = move.getTo().getX();
        if (move.getType() == MoveType.EN_PASSANT) {
            pieces[toRank][toFile] = pieces[fromRank][fromFile];
            pieces[fromRank][fromFile] = new NonePiece();
            pieces[fromRank][toFile] = new NonePiece();
            pieces[toRank][toFile].changeToMoved();
            return;
        }
        if (move.getType() == MoveType.CASTLING_KINGSIDE) {
            executeCastling(
                    fromRank, fromFile,
                    KINGSIDE_KING_DEST_FILE, KINGSIDE_ROOK_DEST_FILE, true);
            return;
        }
        if (move.getType() == MoveType.CASTLING_QUEENSIDE) {
            executeCastling(
                    fromRank, fromFile,
                    QUEENSIDE_KING_DEST_FILE, QUEENSIDE_ROOK_DEST_FILE, false);
            return;
        }
        if (move.getType() == MoveType.PROMOTION) {
            PieceType promotionType = move.getPromotionType().orElse(PieceType.QUEEN);
            PieceColor color = pieces[fromRank][fromFile].getColor();
            pieces[toRank][toFile] = createPiece(promotionType, color);
            pieces[fromRank][fromFile] = new NonePiece();
            return;
        }
        pieces[toRank][toFile] = pieces[fromRank][fromFile];
        pieces[fromRank][fromFile] = new NonePiece();
        pieces[toRank][toFile].changeToMoved();
    }

    private void executeCastling(
            int rank, int kingFile, int kingDestFile, int rookDestFile, boolean kingside) {
        int rookFile = findCastlingRookFile(rank, kingFile, kingside);
        Piece king = pieces[rank][kingFile];
        pieces[rank][kingFile] = new NonePiece();
        Piece rook = pieces[rank][rookFile];
        pieces[rank][rookFile] = new NonePiece();
        king.changeToMoved();
        rook.changeToMoved();
        pieces[rank][kingDestFile] = king;
        pieces[rank][rookDestFile] = rook;
    }

    private int findCastlingRookFile(int rank, int kingFile, boolean kingside) {
        PieceColor color = pieces[rank][kingFile].getColor();
        if (kingside) {
            for (int f = BOARD_SIZE - 1; f > kingFile; f--) {
                Piece p = pieces[rank][f];
                if (p.getType() == PieceType.ROOK && p.getColor() == color && !p.hasMoved()) {
                    return f;
                }
            }
        } else {
            for (int f = 0; f < kingFile; f++) {
                Piece p = pieces[rank][f];
                if (p.getType() == PieceType.ROOK && p.getColor() == color && !p.hasMoved()) {
                    return f;
                }
            }
        }
        throw new IllegalStateException(
                "No unmoved castling rook found on rank " + rank + " kingside=" + kingside);
    }

    private void updateEnPassantTarget(Move move, Piece movedPiece) {
        if (movedPiece.getType() != PieceType.PAWN || move.getType() != MoveType.NORMAL) {
            enPassantTarget = Optional.empty();
            return;
        }
        int rankDiff = move.getTo().getY() - move.getFrom().getY();
        if (Math.abs(rankDiff) == 2) {
            int epRank = move.getFrom().getY() + rankDiff / 2;
            enPassantTarget = Optional.of(new Location(move.getFrom().getX(), epRank));
        } else {
            enPassantTarget = Optional.empty();
        }
    }
}
