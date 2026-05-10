package domain.boardcontroller;

import domain.gamestate.GameState;
import domain.location.Location;
import domain.piece.Bishop;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.Queen;
import domain.piece.Rook;

public class BoardController {

    private final Piece[][] pieces;
    private Location lastSelectedLoc;
    private GameState currentGameState;

    public BoardController() {
        this(StartingPositionKind.STANDARD);
    }

    public BoardController(StartingPositionKind startingPositionKind) {
        pieces = new Piece[8][8];
        lastSelectedLoc = null;
        currentGameState = GameState.WHITE_TURN;
        if (startingPositionKind == StartingPositionKind.STANDARD) {
            placeStandardStartingPosition();
        } else {
            placeChess960StartingPosition();
        }
    }

    /** Rank 0 = white back rank; rank 7 = black back rank (standard chess orientation). */
    private void placeStandardStartingPosition() {
        pieces[0][0] = new Rook(PieceColor.WHITE);
        pieces[0][1] = new Knight(PieceColor.WHITE);
        pieces[0][2] = new Bishop(PieceColor.WHITE);
        pieces[0][3] = new Queen(PieceColor.WHITE);
        pieces[0][4] = new King(PieceColor.WHITE);
        pieces[0][5] = new Bishop(PieceColor.WHITE);
        pieces[0][6] = new Knight(PieceColor.WHITE);
        pieces[0][7] = new Rook(PieceColor.WHITE);
        for (int file = 0; file < 8; file++) {
            pieces[1][file] = new Pawn(PieceColor.WHITE);
        }

        for (int file = 0; file < 8; file++) {
            pieces[6][file] = new Pawn(PieceColor.BLACK);
        }

        pieces[7][0] = new Rook(PieceColor.BLACK);
        pieces[7][1] = new Knight(PieceColor.BLACK);
        pieces[7][2] = new Bishop(PieceColor.BLACK);
        pieces[7][3] = new Queen(PieceColor.BLACK);
        pieces[7][4] = new King(PieceColor.BLACK);
        pieces[7][5] = new Bishop(PieceColor.BLACK);
        pieces[7][6] = new Knight(PieceColor.BLACK);
        pieces[7][7] = new Rook(PieceColor.BLACK);
    }

    /**
     * Fixed legal Chess960 rank (SP-style): {@code R,B,N,Q,K,N,B,R} — bishops on files 1 and 6 (opposite
     * square colors); king between rooks. Black back rank mirrors piece types.
     */
    private void placeChess960StartingPosition() {
        pieces[0][0] = new Rook(PieceColor.WHITE);
        pieces[0][1] = new Bishop(PieceColor.WHITE);
        pieces[0][2] = new Knight(PieceColor.WHITE);
        pieces[0][3] = new Queen(PieceColor.WHITE);
        pieces[0][4] = new King(PieceColor.WHITE);
        pieces[0][5] = new Knight(PieceColor.WHITE);
        pieces[0][6] = new Bishop(PieceColor.WHITE);
        pieces[0][7] = new Rook(PieceColor.WHITE);
        for (int file = 0; file < 8; file++) {
            pieces[1][file] = new Pawn(PieceColor.WHITE);
        }

        for (int file = 0; file < 8; file++) {
            pieces[6][file] = new Pawn(PieceColor.BLACK);
        }

        pieces[7][0] = new Rook(PieceColor.BLACK);
        pieces[7][1] = new Bishop(PieceColor.BLACK);
        pieces[7][2] = new Knight(PieceColor.BLACK);
        pieces[7][3] = new Queen(PieceColor.BLACK);
        pieces[7][4] = new King(PieceColor.BLACK);
        pieces[7][5] = new Knight(PieceColor.BLACK);
        pieces[7][6] = new Bishop(PieceColor.BLACK);
        pieces[7][7] = new Rook(PieceColor.BLACK);
    }

    public boolean hasSelection() {
        return lastSelectedLoc != null;
    }

    public Piece[][] getBoardSnapshot() {
        return pieces;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }
}
