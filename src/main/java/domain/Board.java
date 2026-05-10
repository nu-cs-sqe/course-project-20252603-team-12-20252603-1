package domain;

import domain.piece.Piece;

public class Board {

    public Piece[][] getSnapshot() {
        return new Piece[8][8];
    }
}
