package domain.piece;

public class King extends Piece {

    public King(PieceColor color) {
        super(PieceType.KING, color);
    }

    @Override
    public Piece makeCopy() {
        return new King(getColor());
    }
}