package domain;

import domain.piece.PieceType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FischerRandomBoardInitializer implements BoardInitializer {

    private final Random random;

    public FischerRandomBoardInitializer() {
        this.random = new Random();
    }

    @Override
    public PieceType[][] getBoardLayout() {
        PieceType[][] layout = new PieceType[8][8];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                layout[row][col] = PieceType.NONE;
            }
        }

        PieceType[] backRank = generateBackRank();
        for (int col = 0; col < 8; col++) {
            layout[0][col] = backRank[col];
            layout[7][col] = backRank[col];
        }

        for (int col = 0; col < 8; col++) {
            layout[1][col] = PieceType.PAWN;
            layout[6][col] = PieceType.PAWN;
        }

        return layout;
    }

    private PieceType[] generateBackRank() {
        PieceType[] rank = new PieceType[8];
        for (int col = 0; col < 8; col++) {
            rank[col] = PieceType.NONE;
        }

        int[] evenCols = {0, 2, 4, 6};
        int[] oddCols = {1, 3, 5, 7};
        rank[evenCols[random.nextInt(4)]] = PieceType.BISHOP;
        rank[oddCols[random.nextInt(4)]] = PieceType.BISHOP;

        List<Integer> remaining = new ArrayList<>();
        for (int col = 0; col < 8; col++) {
            if (rank[col] == PieceType.NONE) remaining.add(col);
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
