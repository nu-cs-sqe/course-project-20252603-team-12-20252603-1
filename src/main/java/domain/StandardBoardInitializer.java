package domain;

import domain.piece.PieceType;

public class StandardBoardInitializer implements BoardInitializer {

    private static final int BOARD_SIZE = 8;

    @Override
    public PieceType[][] getBoardLayout() {
        PieceType[][] layout = new PieceType[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                layout[row][col] = PieceType.NONE;
            }
        }

        layout[0][0] = PieceType.ROOK;
        layout[0][1] = PieceType.KNIGHT;
        layout[0][2] = PieceType.BISHOP;
        layout[0][3] = PieceType.QUEEN;
        layout[0][4] = PieceType.KING;
        layout[0][5] = PieceType.BISHOP;
        layout[0][6] = PieceType.KNIGHT;
        layout[0][7] = PieceType.ROOK;

        for (int col = 0; col < BOARD_SIZE; col++) {
            layout[1][col] = PieceType.PAWN;
        }

        for (int col = 0; col < BOARD_SIZE; col++) {
            layout[6][col] = PieceType.PAWN;
        }

        layout[7][0] = PieceType.ROOK;
        layout[7][1] = PieceType.KNIGHT;
        layout[7][2] = PieceType.BISHOP;
        layout[7][3] = PieceType.QUEEN;
        layout[7][4] = PieceType.KING;
        layout[7][5] = PieceType.BISHOP;
        layout[7][6] = PieceType.KNIGHT;
        layout[7][7] = PieceType.ROOK;

        return layout;
    }
}
