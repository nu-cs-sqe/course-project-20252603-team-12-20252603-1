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

public class Board {

    private final Piece[][] pieces = new Piece[8][8];
    private GameState currentGameState = GameState.WHITE_TURN;

    public Board(Piece[][] initialPieces) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                pieces[row][col] = initialPieces[row][col].makeCopy();
            }
        }
    }

    public Board(BoardInitializer initializer) {
        PieceType[][] layout = initializer.getBoardLayout();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                PieceColor color = (row < 4) ? PieceColor.BLACK : PieceColor.WHITE;
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

    public Piece[][] getSnapshot() {
        Piece[][] snapshot = new Piece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                snapshot[row][col] = pieces[row][col].makeCopy();
            }
        }
        return snapshot;
    }
}
