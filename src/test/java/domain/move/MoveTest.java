package domain.move;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import domain.location.Location;
import domain.piece.PieceType;
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

    @ParameterizedTest
    @EnumSource(MoveType.class)
    void Constructor_FourArg_WhenMoveTypeMatches_MoveTypeIsCorrect(MoveType type) {
        Move move = new Move(new Location(1, 2), new Location(3, 4), type, PieceType.QUEEN);

        assertEquals(type, move.getType());
    }

    @ParameterizedTest
    @EnumSource(PieceType.class)
    void Constructor_FourArg_WhenPromotionPieceMatches_PromotionTypeContainsPiece(PieceType piece) {
        Move move = new Move(new Location(1, 2), new Location(3, 4), MoveType.PROMOTION, piece);

        assertEquals(Optional.of(piece), move.getPromotionType());
    }

    @Test
    void WithPromotionType_WhenCalledOnNormalMove_ReturnedMoveTypeIsPromotion() {
        Move original = new Move(new Location(1, 2), new Location(3, 4), MoveType.NORMAL);

        Move result = original.withPromotionType(PieceType.QUEEN);

        assertEquals(MoveType.PROMOTION, result.getType());
    }

    @ParameterizedTest
    @EnumSource(PieceType.class)
    void WithPromotionType_WhenCalledWithPieceType_ReturnedMovePromotionTypeMatchesPiece(PieceType piece) {
        Move original = new Move(new Location(1, 2), new Location(3, 4), MoveType.NORMAL);

        Move result = original.withPromotionType(piece);

        assertEquals(Optional.of(piece), result.getPromotionType());
    }

    @Test
    void WithPromotionType_ReturnedMoveFromMatchesOriginal() {
        Move original = new Move(new Location(1, 2), new Location(3, 4), MoveType.NORMAL);

        Move result = original.withPromotionType(PieceType.QUEEN);

        assertEquals(1, result.getFrom().getX());
        assertEquals(2, result.getFrom().getY());
    }

    @Test
    void WithPromotionType_ReturnedMoveToMatchesOriginal() {
        Move original = new Move(new Location(1, 2), new Location(3, 4), MoveType.NORMAL);

        Move result = original.withPromotionType(PieceType.QUEEN);

        assertEquals(3, result.getTo().getX());
        assertEquals(4, result.getTo().getY());
    }
}
