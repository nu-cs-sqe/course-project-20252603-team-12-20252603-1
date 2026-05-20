package domain;

import domain.gamestate.GameState;
import domain.piece.Piece;

/**
 * Board model contract (implemented by the Board feature branch). {@link ui.BoardController}
 * depends on this interface; unit tests substitute an EasyMock proxy.
 */
public interface Board {

  Piece[][] getSnapshot();

  GameState getCurrentGameState();

  Piece getPieceAt(int rank, int file);
}
