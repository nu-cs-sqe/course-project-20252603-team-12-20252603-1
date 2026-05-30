package domain.move;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.location.Location;
import org.junit.jupiter.api.Test;

class MoveTest {

    @Test
    void Constructor_TwoArg_MoveTypeIsNormal() {
        Move move = new Move(new Location(1, 2), new Location(3, 4));

        assertEquals(MoveType.NORMAL, move.getType());
    }
}
