package domain;

import domain.gamestate.GameState;
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
import domain.location.Location;

public class Board {

    private static final int BOARD_SIZE = 8;
    private static final int BLACK_RANK_ROWS = 4;

    private final Piece[][] pieces = new Piece[BOARD_SIZE][BOARD_SIZE];
    private GameState currentGameState = GameState.WHITE_TURN;

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

    public void switchTurn() {
        if (currentGameState == GameState.WHITE_TURN) {
            currentGameState = GameState.BLACK_TURN;
        } else {
            currentGameState = GameState.WHITE_TURN;
        }
    }

    public Piece getPieceAt(int rank, int file) {
        return copyPiecePreservingState(pieces[rank][file]);
    }

    public Piece[][] getSnapshot() {
        Piece[][] snapshot = new Piece[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                snapshot[row][col] = copyPiecePreservingState(pieces[row][col]);
            }
        }
        return snapshot;
    }

    public boolean movePiece(Location from, Location to) {
        int fromRank = from.getY();
        int fromFile = from.getX();
        int toRank = to.getY();
        int toFile = to.getX();

        Piece fromPiece = pieces[fromRank][fromFile];
        Piece toPiece = pieces[toRank][toFile];
        if (toPiece.getType() != PieceType.NONE && fromPiece.isSameColor(toPiece)) {
            return false;
        }
        if (!isValidMoveShape(fromPiece, fromRank, fromFile, toRank, toFile, toPiece)) {
            return false;
        }
        if (!hasNoObstacle(fromPiece, fromRank, fromFile, toRank, toFile)) {
            return false;
        }

        Piece movedPiece = fromPiece.makeCopy();
        movedPiece.changeToMoved();
        pieces[toRank][toFile] = movedPiece;
        pieces[fromRank][fromFile] = new NonePiece();
        return true;
    }

    private boolean isValidMoveShape(Piece piece, int fromRank, int fromFile, int toRank, int toFile, Piece toPiece) {
        int rankDelta = toRank - fromRank;
        int fileDelta = toFile - fromFile;
        int absRankDelta = Math.abs(rankDelta);
        int absFileDelta = Math.abs(fileDelta);

        PieceType type = piece.getType();
        if (type == PieceType.KNIGHT) {
            return (absRankDelta == 2 && absFileDelta == 1)
                    || (absRankDelta == 1 && absFileDelta == 2);
        }
        if (type == PieceType.ROOK) {
            return (rankDelta == 0 && fileDelta != 0)
                    || (fileDelta == 0 && rankDelta != 0);
        }
        if (type == PieceType.PAWN) {
            int forward = piece.getColor() == PieceColor.WHITE ? -1 : 1;
            boolean isForwardMove = fileDelta == 0 && rankDelta == forward && toPiece.getType() == PieceType.NONE;
            boolean isDiagonalCapture = absFileDelta == 1
                    && rankDelta == forward
                    && toPiece.getType() != PieceType.NONE
                    && !piece.isSameColor(toPiece);
            return isForwardMove || isDiagonalCapture;
        }
        return false;
    }

    private boolean hasNoObstacle(Piece piece, int fromRank, int fromFile, int toRank, int toFile) {
        if (piece.canJump()) {
            return true;
        }

        int rankStep = Integer.compare(toRank, fromRank);
        int fileStep = Integer.compare(toFile, fromFile);
        int rank = fromRank + rankStep;
        int file = fromFile + fileStep;
        while (rank != toRank || file != toFile) {
            if (pieces[rank][file].getType() != PieceType.NONE) {
                return false;
            }
            rank += rankStep;
            file += fileStep;
        }
        return true;
    }

    private Piece copyPiecePreservingState(Piece sourcePiece) {
        Piece copiedPiece = sourcePiece.makeCopy();
        if (sourcePiece.hasMoved()) {
            copiedPiece.changeToMoved();
        }
        return copiedPiece;
    }
}
