package domain.move;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import domain.location.Location;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class MoveTest {

    @Test
    void Constructor_TwoArg_MoveTypeIsNormal() {
        Move move = new Move(new Location(1, 2), new Location(3, 4));

        assertEquals(MoveType.NORMAL, move.getType());
    }

    @Test
    void Constructor_TwoArg_PromotionTypeIsEmpty() {
        Move move = new Move(new Location(1, 2), new Location(3, 4));

        assertFalse(move.getPromotionType().isPresent());
    }

    @ParameterizedTest
    @EnumSource(MoveType.class)
    void Constructor_ThreeArg_WhenMoveTypeMatches_MoveTypeIsCorrect(MoveType type) {
        Move move = new Move(new Location(1, 2), new Location(3, 4), type);

        assertEquals(type, move.getType());
    }

    @Test
    void Constructor_ThreeArg_WhenTypeIsPromotion_PromotionTypeIsEmpty() {
        Move move = new Move(new Location(1, 2), new Location(3, 4), MoveType.PROMOTION);

        assertFalse(move.getPromotionType().isPresent());
    }
}
