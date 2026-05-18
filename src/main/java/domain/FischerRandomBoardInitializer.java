package domain;

import domain.piece.PieceType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FischerRandomBoardInitializer implements BoardInitializer {

  private final Random random;

  public FischerRandomBoardInitializer(Random random) {
    this.random = random;
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
    PieceType[] byFile = new PieceType[8];
    for (int file = 0; file < 8; file++) {
      byFile[file] = PieceType.NONE;
    }

    int[] lightFiles = {0, 2, 4, 6};
    int lightBishopFile = lightFiles[random.nextInt(4)];
    byFile[lightBishopFile] = PieceType.BISHOP;

    int[] darkFiles = {1, 3, 5, 7};
    int darkBishopFile = darkFiles[random.nextInt(4)];
    byFile[darkBishopFile] = PieceType.BISHOP;

    List<Integer> remaining = new ArrayList<>();
    for (int file = 0; file < 8; file++) {
      if (byFile[file] == PieceType.NONE) {
        remaining.add(file);
      }
    }

    int queenIdx = random.nextInt(3);
    byFile[remaining.get(queenIdx)] = PieceType.QUEEN;
    remaining.remove(queenIdx);

    int knight1Idx = random.nextInt(4);
    byFile[remaining.get(knight1Idx)] = PieceType.KNIGHT;
    remaining.remove(knight1Idx);

    int knight2Idx = random.nextInt(3);
    byFile[remaining.get(knight2Idx)] = PieceType.KNIGHT;
    remaining.remove(knight2Idx);

    byFile[remaining.get(0)] = PieceType.ROOK;
    byFile[remaining.get(1)] = PieceType.KING;
    byFile[remaining.get(2)] = PieceType.ROOK;

    return byFile;
  }
}
