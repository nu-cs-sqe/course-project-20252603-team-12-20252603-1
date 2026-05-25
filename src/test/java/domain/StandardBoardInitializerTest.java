package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.piece.PieceType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StandardBoardInitializerTest {

    @ParameterizedTest
    @CsvSource({
            "0, 0, ROOK",
            "0, 1, KNIGHT",
            "0, 2, BISHOP",
            "0, 3, QUEEN",
            "0, 4, KING",
            "0, 5, BISHOP",
            "0, 6, KNIGHT",
            "0, 7, ROOK",
            "1, 0, PAWN",
            "1, 1, PAWN",
            "1, 2, PAWN",
            "1, 3, PAWN",
            "1, 4, PAWN",
            "1, 5, PAWN",
            "1, 6, PAWN",
            "1, 7, PAWN",
            "3, 0, NONE",
            "6, 0, PAWN",
            "6, 1, PAWN",
            "6, 2, PAWN",
            "6, 3, PAWN",
            "6, 4, PAWN",
            "6, 5, PAWN",
            "6, 6, PAWN",
            "6, 7, PAWN",
            "7, 0, ROOK",
            "7, 1, KNIGHT",
            "7, 2, BISHOP",
            "7, 3, QUEEN",
            "7, 4, KING",
            "7, 5, BISHOP",
            "7, 6, KNIGHT",
            "7, 7, ROOK"
    })
    void GetBoardLayout_PieceTypeAtPositionIsCorrect(int row, int col, PieceType expectedType) {
        StandardBoardInitializer initializer = new StandardBoardInitializer();
        assertEquals(expectedType, initializer.getBoardLayout()[row][col]);
    }
}
