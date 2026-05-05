package domain.piece;

public class Rook extends Piece {

    public Rook(PieceColor color) {
        super(PieceType.ROOK, color);
    }

    @Override
    public Piece makeCopy() {
        return new Rook(getColor());
    }
}
