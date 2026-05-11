package domain.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

class PawnTest {

	@Test
	void Constructor_OnWhitePawn_TypeIsPawn() {
		Pawn pawn = new Pawn(PieceColor.WHITE);

		PieceType expected = PieceType.PAWN;
		PieceType actual = pawn.getType();
		assertEquals(expected, actual);
	}

	@Test
	void Constructor_OnWhitePawn_ColorIsWhite() {
		Pawn pawn = new Pawn(PieceColor.WHITE);

		PieceColor expected = PieceColor.WHITE;
		PieceColor actual = pawn.getColor();
		assertEquals(expected, actual);
	}

	@Test
	void Constructor_OnWhitePawn_CanJumpIsFalse() {
		Pawn pawn = new Pawn(PieceColor.WHITE);

		assertFalse(pawn.canJump());
	}

	@Test
	void MakeCopy_OnWhitePawn_CopyTypeIsPawn() {
		Pawn pawn = new Pawn(PieceColor.WHITE);

		PieceType expected = PieceType.PAWN;
		PieceType actual = pawn.makeCopy().getType();
		assertEquals(expected, actual);
	}

	@Test
	void MakeCopy_OnBlackPawn_CopyTypeIsPawn() {
		Pawn pawn = new Pawn(PieceColor.BLACK);

		PieceType expected = PieceType.PAWN;
		PieceType actual = pawn.makeCopy().getType();
		assertEquals(expected, actual);
	}

	@Test
	void MakeCopy_OnBlackPawn_CopyColorIsBlack() {
		Pawn pawn = new Pawn(PieceColor.BLACK);

		PieceColor expected = PieceColor.BLACK;
		PieceColor actual = pawn.makeCopy().getColor();
		assertEquals(expected, actual);
	}

	@Test
	void MakeCopy_OnBlackPawn_CopyCanJumpIsFalse() {
		Pawn pawn = new Pawn(PieceColor.BLACK);

		assertFalse(pawn.makeCopy().canJump());
	}

	@Test
	void MakeCopy_OnBlackPawn_CopyIsDifferentObject() {
		Pawn pawn = new Pawn(PieceColor.BLACK);

		assertNotSame(pawn, pawn.makeCopy());
	}
}
