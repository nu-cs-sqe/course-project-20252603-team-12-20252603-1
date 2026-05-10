package domain.boardcontroller;

import domain.location.Location;

public class BoardController {

    private Location lastSelectedLoc;

    public BoardController() {
        lastSelectedLoc = null;
    }

    public boolean hasSelection() {
        return lastSelectedLoc != null;
    }
}
