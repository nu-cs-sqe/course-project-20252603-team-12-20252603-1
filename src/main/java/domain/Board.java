package domain;

import domain.gamestate.GameState;
import domain.piece.Bishop;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.NonePiece;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.Queen;
import domain.piece.Rook;

public class Board {

    private final Piece[][] pieces = new Piece[8][8];
    private GameState currentGameState = GameState.WHITE_TURN;

    public Board() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                pieces[row][col] = new NonePiece();
            }
        }

        pieces[0][0] = new Rook(PieceColor.BLACK);
        pieces[0][1] = new Knight(PieceColor.BLACK);
        pieces[0][2] = new Bishop(PieceColor.BLACK);
        pieces[0][3] = new Queen(PieceColor.BLACK);
        pieces[0][4] = new King(PieceColor.BLACK);
        pieces[0][5] = new Bishop(PieceColor.BLACK);
        pieces[0][6] = new Knight(PieceColor.BLACK);
        pieces[0][7] = new Rook(PieceColor.BLACK);

        for (int col = 0; col < 8; col++) {
            pieces[1][col] = new Pawn(PieceColor.BLACK);
        }

        for (int col = 0; col < 8; col++) {
            pieces[6][col] = new Pawn(PieceColor.WHITE);
        }

        pieces[7][0] = new Rook(PieceColor.WHITE);
        pieces[7][1] = new Knight(PieceColor.WHITE);
        pieces[7][2] = new Bishop(PieceColor.WHITE);
        pieces[7][3] = new Queen(PieceColor.WHITE);
        pieces[7][4] = new King(PieceColor.WHITE);
        pieces[7][5] = new Bishop(PieceColor.WHITE);
        pieces[7][6] = new Knight(PieceColor.WHITE);
        pieces[7][7] = new Rook(PieceColor.WHITE);
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
