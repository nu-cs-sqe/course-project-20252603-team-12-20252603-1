package domain.piece;

public class Knight extends Piece {

    public Knight(PieceColor color) {
        super(PieceType.KNIGHT, color);
    }

    @Override
    public Piece makeCopy() {
        return new Knight(getColor());
    }
}
