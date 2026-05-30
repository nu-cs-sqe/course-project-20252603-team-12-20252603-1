package domain.move;

import domain.location.Location;

public class Move {

    private final Location from;
    private final Location to;
    private final MoveType type;

    public Move(Location from, Location to) {
        this.from = from;
        this.to = to;
        this.type = MoveType.NORMAL;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public MoveType getType() {
        return type;
    }
}
