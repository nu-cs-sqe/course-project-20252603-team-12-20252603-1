package domain;

import domain.piece.PieceType;

public interface BoardInitializer {
    PieceType[][] getBoardLayout();
}
