package domain.boardcontroller;

import domain.location.Location;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.Rook;

public class BoardController {

    private final Piece[][] pieces;
    private Location lastSelectedLoc;

    public BoardController() {
        pieces = new Piece[8][8];
        lastSelectedLoc = null;
        placeStandardCornerRooks();
    }

    private void placeStandardCornerRooks() {
        pieces[0][0] = new Rook(PieceColor.WHITE);
        pieces[0][7] = new Rook(PieceColor.WHITE);
        pieces[7][0] = new Rook(PieceColor.BLACK);
        pieces[7][7] = new Rook(PieceColor.BLACK);
    }

    public boolean hasSelection() {
        return lastSelectedLoc != null;
    }

    public Piece[][] getBoardSnapshot() {
        return pieces;
    }
}
