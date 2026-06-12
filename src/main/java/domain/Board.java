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
    private static final int FIFTY_MOVE_HALF_MOVE_LIMIT = 100;

    private final Piece[][] pieces = new Piece[BOARD_SIZE][BOARD_SIZE];
    private GameState currentGameState = GameState.WHITE_TURN;
    private Optional<Location> enPassantTarget = Optional.empty();
    int halfMoveClock = 0;

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
        final PieceColor movingColor = currentPlayerColor();
        Piece movingPiece = pieces[move.getFrom().getY()][move.getFrom().getX()];
        boolean isPawnMove = movingPiece.getType() == PieceType.PAWN;
        boolean capture = isCapture(move);
        applyMoveToInternalState(move);
        updateEnPassantTarget(move, movingPiece);
        halfMoveClock = (isPawnMove || capture) ? 0 : halfMoveClock + 1;
        updateGameState(movingColor);
    }

    void updateGameState(PieceColor justMovedColor) {
        PieceColor nextColor = opponentOf(justMovedColor);
        MoveGenerator gen = new MoveGenerator(pieces, enPassantTarget);
        if (!gen.hasLegalMovesForColor(nextColor)) {
            currentGameState = gen.isInCheck(nextColor)
                    ? winStateFor(justMovedColor) : GameState.DRAW;
            return;
        }
        if (isInsufficientMaterial() || halfMoveClock >= FIFTY_MOVE_HALF_MOVE_LIMIT) {
            currentGameState = GameState.DRAW;
            return;
        }
        currentGameState = nextColor == PieceColor.WHITE
                ? GameState.WHITE_TURN : GameState.BLACK_TURN;
    }

    PieceColor currentPlayerColor() {
        return currentGameState == GameState.WHITE_TURN ? PieceColor.WHITE : PieceColor.BLACK;
    }

    int getHalfMoveClock() {
        return halfMoveClock;
    }

    boolean isCapture(Move move) {
        if (move.getType() == MoveType.EN_PASSANT) {
            return true;
        }
        return pieces[move.getTo().getY()][move.getTo().getX()].getType() != PieceType.NONE;
    }

    private PieceColor opponentOf(PieceColor color) {
        return color == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
    }

    private GameState winStateFor(PieceColor color) {
        return color == PieceColor.WHITE ? GameState.WHITE_WIN : GameState.BLACK_WIN;
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
            executeCastling(fromRank, fromFile, toFile,
                    KINGSIDE_KING_DEST_FILE, KINGSIDE_ROOK_DEST_FILE);
            return;
        }
        if (move.getType() == MoveType.CASTLING_QUEENSIDE) {
            executeCastling(fromRank, fromFile, toFile,
                    QUEENSIDE_KING_DEST_FILE, QUEENSIDE_ROOK_DEST_FILE);
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
            int rank, int kingFile, int rookFile, int kingDestFile, int rookDestFile) {
        Piece king = pieces[rank][kingFile];
        final Piece rook = pieces[rank][rookFile];
        pieces[rank][kingFile] = new NonePiece();
        pieces[rank][rookFile] = new NonePiece();
        king.changeToMoved();
        rook.changeToMoved();
        pieces[rank][kingDestFile] = king;
        pieces[rank][rookDestFile] = rook;
    }

    boolean isInsufficientMaterial() {
        int nonKingCount = 0;
        boolean hasMajorOrPawn = false;
        for (int rank = 0; rank < BOARD_SIZE; rank++) {
            for (int file = 0; file < BOARD_SIZE; file++) {
                Piece p = pieces[rank][file];
                if (p.getType() == PieceType.NONE || p.getType() == PieceType.KING) {
                    continue;
                }
                nonKingCount++;
                PieceType t = p.getType();
                if (t != PieceType.BISHOP && t != PieceType.KNIGHT) {
                    hasMajorOrPawn = true;
                }
            }
        }
        return !hasMajorOrPawn && nonKingCount <= 1;
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
