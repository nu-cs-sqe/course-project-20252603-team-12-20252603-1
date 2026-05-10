package domain;

import domain.piece.King;
import domain.piece.NonePiece;
import domain.piece.Piece;
import domain.piece.PieceColor;

public class Board {

    private final Piece[][] pieces = new Piece[8][8];

    public Board() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                pieces[row][col] = new NonePiece();
            }
        }
        pieces[0][0] = new King(PieceColor.BLACK);
        pieces[7][4] = new King(PieceColor.WHITE);
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
