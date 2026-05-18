package domain;

import domain.piece.PieceType;

public class Chess960FixedBoardInitializer implements BoardInitializer {

  private static final PieceType[] BACK_RANK = {
    PieceType.ROOK,
    PieceType.BISHOP,
    PieceType.KNIGHT,
    PieceType.QUEEN,
    PieceType.KING,
    PieceType.KNIGHT,
    PieceType.BISHOP,
    PieceType.ROOK
  };

  @Override
  public PieceType[][] getBoardLayout() {
    PieceType[][] layout = new PieceType[8][8];
    fillEmpty(layout);

    for (int col = 0; col < 8; col++) {
      layout[0][col] = BACK_RANK[col];
      layout[7][col] = BACK_RANK[col];
    }

    for (int col = 0; col < 8; col++) {
      layout[1][col] = PieceType.PAWN;
      layout[6][col] = PieceType.PAWN;
    }

    return layout;
  }

  private static void fillEmpty(PieceType[][] layout) {
    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 8; col++) {
        layout[row][col] = PieceType.NONE;
      }
    }
  }
}
