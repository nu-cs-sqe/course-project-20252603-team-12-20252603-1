package domain.piece;

import domain.location.Location;

public class NonePiece extends Piece {

    public NonePiece() {
        super(PieceType.NONE, PieceColor.WHITE);
    }

    @Override
    public Piece makeCopy() {
        return new NonePiece();
    }

    @Override
    public boolean isValidMoveShape(Location from, Location to) {
        return false;
    }
}
