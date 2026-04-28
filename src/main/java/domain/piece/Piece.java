package domain.piece;

public abstract class Piece {

    private final PieceType type;
    private final PieceColor color;
    protected final boolean canJump;
    private boolean hasMoved;

    public Piece(PieceType type, PieceColor color) {
        this.type = type;
        this.color = color;
        this.canJump = (PieceType.KNIGHT == type);
        this.hasMoved = false;
    }
}
