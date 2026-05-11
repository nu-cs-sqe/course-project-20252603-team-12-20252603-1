package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

class BishopTest {

	@Test
	void Constructor_OnWhiteBishop_TypeIsBishop() {
		Bishop bishop = new Bishop(PieceColor.WHITE);

		PieceType expected = PieceType.BISHOP;
		PieceType actual = bishop.getType();
		assertEquals(expected, actual);
	}

	@Test
	void Constructor_OnWhiteBishop_ColorIsWhite() {
		Bishop bishop = new Bishop(PieceColor.WHITE);

		PieceColor expected = PieceColor.WHITE;
		PieceColor actual = bishop.getColor();
		assertEquals(expected, actual);
	}

	@Test
	void Constructor_OnWhiteBishop_CanJumpIsFalse() {
		Bishop bishop = new Bishop(PieceColor.WHITE);

		assertFalse(bishop.canJump());
	}

	@Test
	void MakeCopy_OnWhiteBishop_CopyTypeIsBishop() {
		Bishop bishop = new Bishop(PieceColor.WHITE);

		PieceType expected = PieceType.BISHOP;
		PieceType actual = bishop.makeCopy().getType();
		assertEquals(expected, actual);
	}

	@Test
	void MakeCopy_OnBlackBishop_CopyTypeIsBishop() {
		Bishop bishop = new Bishop(PieceColor.BLACK);

		PieceType expected = PieceType.BISHOP;
		PieceType actual = bishop.makeCopy().getType();
		assertEquals(expected, actual);
	}

	@Test
	void MakeCopy_OnBlackBishop_CopyColorIsBlack() {
		Bishop bishop = new Bishop(PieceColor.BLACK);

		PieceColor expected = PieceColor.BLACK;
		PieceColor actual = bishop.makeCopy().getColor();
		assertEquals(expected, actual);
	}

	@Test
	void MakeCopy_OnBlackBishop_CopyCanJumpIsFalse() {
		Bishop bishop = new Bishop(PieceColor.BLACK);

		assertFalse(bishop.makeCopy().canJump());
	}

	@Test
	void MakeCopy_OnBlackBishop_CopyIsDifferentObject() {
		Bishop bishop = new Bishop(PieceColor.BLACK);

		assertNotSame(bishop, bishop.makeCopy());
	}
}
