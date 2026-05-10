package domain.boardcontroller;

import domain.gamestate.GameState;
import domain.location.Location;
import domain.piece.Bishop;
import domain.piece.King;
import domain.piece.Knight;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Queen;
import domain.piece.Rook;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
            placeChess960FixedStartingPosition();
        }
    }

    public BoardController(long chess960Seed) {
        pieces = new Piece[8][8];
        lastSelectedLoc = null;
        currentGameState = GameState.WHITE_TURN;
        placeChess960SeededStartingPosition(chess960Seed);
    }

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

    private void placeChess960FixedStartingPosition() {
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

    private void placeChess960SeededStartingPosition(long seed) {
        Random rng = new Random(seed);
        List<PieceType> multiset =
                new ArrayList<>(
                        Arrays.asList(
                                PieceType.ROOK,
                                PieceType.ROOK,
                                PieceType.KNIGHT,
                                PieceType.KNIGHT,
                                PieceType.BISHOP,
                                PieceType.BISHOP,
                                PieceType.QUEEN,
                                PieceType.KING));
        for (int attempt = 0; attempt < 50_000; attempt++) {
            List<PieceType> bag = new ArrayList<>(multiset);
            Collections.shuffle(bag, rng);
            PieceType[] byFile = bag.toArray(new PieceType[0]);
            if (isLegalChess960WhiteBackRank(byFile)) {
                fillBackRankFromTypes(0, PieceColor.WHITE, byFile);
                fillBackRankFromTypes(7, PieceColor.BLACK, byFile);
                for (int file = 0; file < 8; file++) {
                    pieces[1][file] = new Pawn(PieceColor.WHITE);
                    pieces[6][file] = new Pawn(PieceColor.BLACK);
                }
                return;
            }
        }
        throw new IllegalStateException("Chess960: no valid SP for seed " + seed);
    }

    private static boolean isLegalChess960WhiteBackRank(PieceType[] byFile) {
        int[] rooks = filesOfType(byFile, PieceType.ROOK, 2);
        int[] bishops = filesOfType(byFile, PieceType.BISHOP, 2);
        int[] king = filesOfType(byFile, PieceType.KING, 1);
        if (rooks == null || bishops == null || king == null) {
            return false;
        }
        if ((bishops[0] % 2) == (bishops[1] % 2)) {
            return false;
        }
        int rookLo = Math.min(rooks[0], rooks[1]);
        int rookHi = Math.max(rooks[0], rooks[1]);
        return king[0] > rookLo && king[0] < rookHi;
    }

    private static int[] filesOfType(PieceType[] byFile, PieceType type, int expectedCount) {
        int[] files = new int[expectedCount];
        int found = 0;
        for (int file = 0; file < 8; file++) {
            if (byFile[file] == type) {
                if (found >= expectedCount) {
                    return null;
                }
                files[found++] = file;
            }
        }
        return found == expectedCount ? files : null;
    }

    private void fillBackRankFromTypes(int rank, PieceColor color, PieceType[] byFile) {
        for (int file = 0; file < 8; file++) {
            pieces[rank][file] = createPiece(byFile[file], color);
        }
    }

    private static Piece createPiece(PieceType type, PieceColor color) {
        switch (type) {
            case ROOK:
                return new Rook(color);
            case KNIGHT:
                return new Knight(color);
            case BISHOP:
                return new Bishop(color);
            case QUEEN:
                return new Queen(color);
            case KING:
                return new King(color);
            case PAWN:
            default:
                throw new IllegalArgumentException(type.name());
        }
    }

    public boolean hasSelection() {
        return lastSelectedLoc != null;
    }

    public Location getSelectedLocation() {
        return lastSelectedLoc;
    }

    public void handleSquareClick(Location loc) {
        if (!isInBounds(loc)) {
            return;
        }
        int file = loc.getX();
        int rank = loc.getY();
        Piece at = pieces[rank][file];
        if (at == null || currentGameState != GameState.WHITE_TURN) {
            return;
        }
        if (at.getColor() != PieceColor.WHITE) {
            return;
        }
        lastSelectedLoc = loc;
    }

    private static boolean isInBounds(Location loc) {
        int x = loc.getX();
        int y = loc.getY();
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public Piece[][] getBoardSnapshot() {
        return pieces;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }
}
