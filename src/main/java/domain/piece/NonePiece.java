package domain.piece;

public class NonePiece extends Piece {

    public NonePiece() {
        super(PieceType.NONE, PieceColor.WHITE);
    }

    @Override
    public Piece makeCopy() {
        return new NonePiece();
    }
}
