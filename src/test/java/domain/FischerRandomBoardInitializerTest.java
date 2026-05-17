package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.piece.PieceType;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FischerRandomBoardInitializerTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 7})
    void GetBoardLayout_BackRankContainsAllRequiredPieceTypes(int row) {
        FischerRandomBoardInitializer initializer = new FischerRandomBoardInitializer();
        Map<PieceType, Long> counts = Arrays.stream(initializer.getBoardLayout()[row])
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        assertEquals(2L, counts.get(PieceType.ROOK));
        assertEquals(2L, counts.get(PieceType.KNIGHT));
        assertEquals(2L, counts.get(PieceType.BISHOP));
        assertEquals(1L, counts.get(PieceType.QUEEN));
        assertEquals(1L, counts.get(PieceType.KING));
    }
}
