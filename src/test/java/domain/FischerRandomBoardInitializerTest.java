package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.piece.PieceType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class FischerRandomBoardInitializerTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 7})
    void GetBoardLayout_BlackBackRankContainsAllRequiredPieceTypes(int row) {
        FischerRandomBoardInitializer initializer = new FischerRandomBoardInitializer();
        Map<PieceType, Long> counts = Arrays.stream(initializer.getBoardLayout()[row])
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        assertEquals(2L, counts.get(PieceType.ROOK));
        assertEquals(2L, counts.get(PieceType.KNIGHT));
        assertEquals(2L, counts.get(PieceType.BISHOP));
        assertEquals(1L, counts.get(PieceType.QUEEN));
        assertEquals(1L, counts.get(PieceType.KING));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 7})
    void GetBoardLayout_BishopsAreOnOppositeColoredSquares(int row) {
        FischerRandomBoardInitializer initializer = new FischerRandomBoardInitializer();
        PieceType[] backRank = initializer.getBoardLayout()[row];

        List<Integer> bishopCols = new ArrayList<>();
        for (int col = 0; col < 8; col++) {
            if (backRank[col] == PieceType.BISHOP) bishopCols.add(col);
        }

        assertNotEquals(bishopCols.get(0) % 2, bishopCols.get(1) % 2);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 7})
    void GetBoardLayout_KingIsBetweenRooks(int row) {
        FischerRandomBoardInitializer initializer = new FischerRandomBoardInitializer();
        PieceType[] backRank = initializer.getBoardLayout()[row];

        int kingCol = -1;
        List<Integer> rookCols = new ArrayList<>();
        for (int col = 0; col < 8; col++) {
            if (backRank[col] == PieceType.KING) kingCol = col;
            if (backRank[col] == PieceType.ROOK) rookCols.add(col);
        }

        assertTrue(rookCols.get(0) < kingCol && kingCol < rookCols.get(1));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 0", "1, 1", "1, 2", "1, 3", "1, 4", "1, 5", "1, 6", "1, 7",
            "6, 0", "6, 1", "6, 2", "6, 3", "6, 4", "6, 5", "6, 6", "6, 7"
    })
    void GetBoardLayout_PieceTypeAtPawnRankIsCorrect(int row, int col) {
        FischerRandomBoardInitializer initializer = new FischerRandomBoardInitializer();
        assertEquals(PieceType.PAWN, initializer.getBoardLayout()[row][col]);
    }
}
