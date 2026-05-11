package domain.piece;

public class Queen extends Piece {

	public Queen(PieceColor color) {
		super(PieceType.QUEEN, color);
	}

	@Override
	public Piece makeCopy() {
		return new Queen(getColor());
	}
}
