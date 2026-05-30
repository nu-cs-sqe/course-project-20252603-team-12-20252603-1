package domain.move;

import domain.location.Location;
import domain.piece.PieceType;
import java.util.Optional;

public class Move {

    private final Location from;
    private final Location to;
    private final MoveType type;
    private final Optional<PieceType> promotionType;

    public Move(Location from, Location to) {
        this.from = from;
        this.to = to;
        this.type = MoveType.NORMAL;
        this.promotionType = Optional.empty();
    }

    public Move(Location from, Location to, MoveType type) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.promotionType = Optional.empty();
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

    public Optional<PieceType> getPromotionType() {
        return promotionType;
    }
}
