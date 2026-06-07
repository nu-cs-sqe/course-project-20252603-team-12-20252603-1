package domain;

import domain.piece.PieceType;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FischerRandomBoardInitializer implements BoardInitializer {

    private static final int BOARD_SIZE = 8;

    private final Random random;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Random has no copy constructor")
    public FischerRandomBoardInitializer(Random random) {
        this.random = random;
    }

    @Override
    public PieceType[][] getBoardLayout() {
        PieceType[][] layout = new PieceType[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                layout[row][col] = PieceType.NONE;
            }
        }

        PieceType[] backRank = generateBackRank();
        for (int col = 0; col < BOARD_SIZE; col++) {
            layout[0][col] = backRank[col];
            layout[7][col] = backRank[col];
        }

        for (int col = 0; col < BOARD_SIZE; col++) {
            layout[1][col] = PieceType.PAWN;
            layout[6][col] = PieceType.PAWN;
        }

        return layout;
    }

    private PieceType[] generateBackRank() {
        PieceType[] rank = new PieceType[BOARD_SIZE];
        for (int col = 0; col < BOARD_SIZE; col++) {
            rank[col] = PieceType.NONE;
        }

        int[] evenCols = {0, 2, 4, 6};
        int[] oddCols = {1, 3, 5, 7};
        rank[evenCols[random.nextInt(evenCols.length)]] = PieceType.BISHOP;
        rank[oddCols[random.nextInt(oddCols.length)]] = PieceType.BISHOP;

        List<Integer> remaining = new ArrayList<>();
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (rank[col] == PieceType.NONE) {
                remaining.add(col);
            }
        }

        rank[remaining.remove(random.nextInt(remaining.size()))] = PieceType.QUEEN;
        rank[remaining.remove(random.nextInt(remaining.size()))] = PieceType.KNIGHT;
        rank[remaining.remove(random.nextInt(remaining.size()))] = PieceType.KNIGHT;

        rank[remaining.get(0)] = PieceType.ROOK;
        rank[remaining.get(1)] = PieceType.KING;
        rank[remaining.get(2)] = PieceType.ROOK;

        return rank;
    }
}
