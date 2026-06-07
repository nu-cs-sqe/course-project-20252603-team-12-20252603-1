package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.piece.PieceType;
import java.util.Random;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class FischerRandomBoardInitializerTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 7})
    void GetBoardLayout_BackRankContainsAllRequiredPieceTypes(int row) {
        Random random = createRandomMock();
        FischerRandomBoardInitializer initializer = new FischerRandomBoardInitializer(random);
        PieceType[] backRank = initializer.getBoardLayout()[row];

        assertEquals(PieceType.ROOK, backRank[0]);
        assertEquals(PieceType.KNIGHT, backRank[1]);
        assertEquals(PieceType.BISHOP, backRank[2]);
        assertEquals(PieceType.KING, backRank[3]);
        assertEquals(PieceType.KNIGHT, backRank[4]);
        assertEquals(PieceType.BISHOP, backRank[5]);
        assertEquals(PieceType.ROOK, backRank[6]);
        assertEquals(PieceType.QUEEN, backRank[7]);
        EasyMock.verify(random);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 7})
    void GetBoardLayout_BishopsAreOnOppositeColoredSquares(int row) {
        Random random = createRandomMock();
        FischerRandomBoardInitializer initializer = new FischerRandomBoardInitializer(random);
        PieceType[] backRank = initializer.getBoardLayout()[row];

        assertEquals(PieceType.BISHOP, backRank[2]);
        assertEquals(PieceType.BISHOP, backRank[5]);
        assertNotEquals(2 % 2, 5 % 2);
        EasyMock.verify(random);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 7})
    void GetBoardLayout_KingIsBetweenRooks(int row) {
        Random random = createRandomMock();
        FischerRandomBoardInitializer initializer = new FischerRandomBoardInitializer(random);
        PieceType[] backRank = initializer.getBoardLayout()[row];

        assertEquals(PieceType.ROOK, backRank[0]);
        assertEquals(PieceType.KING, backRank[3]);
        assertEquals(PieceType.ROOK, backRank[6]);
        assertTrue(0 < 3 && 3 < 6);
        EasyMock.verify(random);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 0", "1, 1", "1, 2", "1, 3", "1, 4", "1, 5", "1, 6", "1, 7",
            "6, 0", "6, 1", "6, 2", "6, 3", "6, 4", "6, 5", "6, 6", "6, 7"
    })
    void GetBoardLayout_PieceTypeAtPawnRankIsCorrect(int row, int col) {
        Random random = createRandomMock();
        FischerRandomBoardInitializer initializer = new FischerRandomBoardInitializer(random);
        assertEquals(PieceType.PAWN, initializer.getBoardLayout()[row][col]);
        EasyMock.verify(random);
    }

    @Test
    void GetBoardLayout_EmptyPositionPieceTypeIsNone() {
        Random random = createRandomMock();
        FischerRandomBoardInitializer initializer = new FischerRandomBoardInitializer(random);
        assertEquals(PieceType.NONE, initializer.getBoardLayout()[3][0]);
        EasyMock.verify(random);
    }

    private Random createRandomMock() {
        Random random = EasyMock.createMock(Random.class);
        EasyMock.expect(random.nextInt(4)).andReturn(1);
        EasyMock.expect(random.nextInt(4)).andReturn(2);
        EasyMock.expect(random.nextInt(6)).andReturn(5);
        EasyMock.expect(random.nextInt(5)).andReturn(1);
        EasyMock.expect(random.nextInt(4)).andReturn(2);
        EasyMock.replay(random);
        return random;
    }
}
