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
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class BoardController {

    private final Piece[][] pieces;
    private Optional<Location> lastSelectedLoc;
    private GameState currentGameState;

    public BoardController() {
        this(StartingPositionKind.STANDARD);
    }

    public BoardController(StartingPositionKind startingPositionKind) {
        pieces = new Piece[8][8];
        lastSelectedLoc = Optional.empty();
        currentGameState = GameState.WHITE_TURN;
        if (startingPositionKind == StartingPositionKind.STANDARD) {
            placeStandardStartingPosition();
        } else {
            placeChess960FixedStartingPosition();
        }
    }

    public BoardController(long chess960Seed) {
        pieces = new Piece[8][8];
        lastSelectedLoc = Optional.empty();
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
        PieceType[] byFile = generateChess960BackRank(rng);
        fillBackRankFromTypes(0, PieceColor.WHITE, byFile);
        fillBackRankFromTypes(7, PieceColor.BLACK, byFile);
        for (int file = 0; file < 8; file++) {
            pieces[1][file] = new Pawn(PieceColor.WHITE);
            pieces[6][file] = new Pawn(PieceColor.BLACK);
        }
    }

    private PieceType[] generateChess960BackRank(Random rng) {
        PieceType[] byFile = new PieceType[8];
        for (int file = 0; file < 8; file++) {
            byFile[file] = PieceType.NONE;
        }

        int[] lightFiles = {0, 2, 4, 6};
        int lightBishopFile = lightFiles[rng.nextInt(4)];
        byFile[lightBishopFile] = PieceType.BISHOP;
        
        int[] darkFiles = {1, 3, 5, 7};
        int darkBishopFile = darkFiles[rng.nextInt(4)];
        byFile[darkBishopFile] = PieceType.BISHOP;
        
        List<Integer> remaining = new ArrayList<>();
        for (int file = 0; file < 8; file++) {
            if (byFile[file] == PieceType.NONE) {
                remaining.add(file);
            }
        }
        
        int queenIdx = rng.nextInt(3);
        byFile[remaining.get(queenIdx)] = PieceType.QUEEN;
        remaining.remove(queenIdx);
        
        int knight1Idx = rng.nextInt(4);
        byFile[remaining.get(knight1Idx)] = PieceType.KNIGHT;
        remaining.remove(knight1Idx);
        
        int knight2Idx = rng.nextInt(3);
        byFile[remaining.get(knight2Idx)] = PieceType.KNIGHT;
        remaining.remove(knight2Idx);
        
        byFile[remaining.get(0)] = PieceType.ROOK;
        byFile[remaining.get(1)] = PieceType.KING;
        byFile[remaining.get(2)] = PieceType.ROOK;
        
        return byFile;
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
        return lastSelectedLoc.isPresent();
    }

    public Location getSelectedLocation() {
        return lastSelectedLoc.orElse(null);
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
        lastSelectedLoc = Optional.of(loc);
    }

    private static boolean isInBounds(Location loc) {
        int x = loc.getX();
        int y = loc.getY();
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public Piece[][] getBoardSnapshot() {
        Piece[][] copy = new Piece[8][8];
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                copy[rank][file] = pieces[rank][file];
            }
        }
        return copy;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }
}
