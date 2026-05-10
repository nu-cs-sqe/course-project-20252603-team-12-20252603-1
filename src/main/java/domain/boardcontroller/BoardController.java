package domain.boardcontroller;

import domain.location.Location;
import domain.piece.Piece;

public class BoardController {

    private final Piece[][] pieces;
    private Location lastSelectedLoc;

    public BoardController() {
        pieces = new Piece[8][8];
        lastSelectedLoc = null;
    }

    public boolean hasSelection() {
        return lastSelectedLoc != null;
    }

    public Piece[][] getBoardSnapshot() {
        return pieces;
    }
}
