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

    private static final int BOARD_SIZE = 8;

    private static final PieceType[] STANDARD_BACK_RANK = {
        PieceType.ROOK,
        PieceType.KNIGHT,
        PieceType.BISHOP,
        PieceType.QUEEN,
        PieceType.KING,
        PieceType.BISHOP,
        PieceType.KNIGHT,
        PieceType.ROOK
    };

    private static final PieceType[] CHESS960_FIXED_BACK_RANK = {
        PieceType.ROOK,
        PieceType.BISHOP,
        PieceType.KNIGHT,
        PieceType.QUEEN,
        PieceType.KING,
        PieceType.KNIGHT,
        PieceType.BISHOP,
        PieceType.ROOK
    };

    private final Piece[][] pieces;
    private Optional<Location> lastSelectedLoc;
    private GameState currentGameState;

    public BoardController() {
        this(StartingPositionKind.STANDARD);
    }

    public BoardController(StartingPositionKind startingPositionKind) {
        pieces = new Piece[BOARD_SIZE][BOARD_SIZE];
        initializeNewGameState();
        if (startingPositionKind == StartingPositionKind.STANDARD) {
            placeStandardStartingPosition();
        } else {
            placeChess960FixedStartingPosition();
        }
    }

    public BoardController(long chess960Seed) {
        pieces = new Piece[BOARD_SIZE][BOARD_SIZE];
        initializeNewGameState();
        placeChess960SeededStartingPosition(chess960Seed);
    }

    private void initializeNewGameState() {
        lastSelectedLoc = Optional.empty();
        currentGameState = GameState.WHITE_TURN;
    }

    private void placeStandardStartingPosition() {
        placeBackRank(0, PieceColor.WHITE, STANDARD_BACK_RANK);
        placeStartingPawns();
        placeBackRank(7, PieceColor.BLACK, STANDARD_BACK_RANK);
    }

    private void placeChess960FixedStartingPosition() {
        placeBackRank(0, PieceColor.WHITE, CHESS960_FIXED_BACK_RANK);
        placeStartingPawns();
        placeBackRank(7, PieceColor.BLACK, CHESS960_FIXED_BACK_RANK);
    }

    private void placeStartingPawns() {
        for (int file = 0; file < BOARD_SIZE; file++) {
            pieces[1][file] = new Pawn(PieceColor.WHITE);
            pieces[6][file] = new Pawn(PieceColor.BLACK);
        }
    }

    private void placeBackRank(int rank, PieceColor color, PieceType[] typesByFile) {
        for (int file = 0; file < BOARD_SIZE; file++) {
            pieces[rank][file] = createPiece(typesByFile[file], color);
        }
    }

    private void placeChess960SeededStartingPosition(long seed) {
        Random rng = new Random(seed);
        PieceType[] byFile = generateChess960BackRank(rng);
        placeBackRank(0, PieceColor.WHITE, byFile);
        placeBackRank(7, PieceColor.BLACK, byFile);
        placeStartingPawns();
    }

    private PieceType[] generateChess960BackRank(Random rng) {
        PieceType[] byFile = new PieceType[BOARD_SIZE];
        for (int file = 0; file < BOARD_SIZE; file++) {
            byFile[file] = PieceType.NONE;
        }

        int[] lightFiles = {0, 2, 4, 6};
        int lightBishopFile = lightFiles[rng.nextInt(4)];
        byFile[lightBishopFile] = PieceType.BISHOP;

        int[] darkFiles = {1, 3, 5, 7};
        int darkBishopFile = darkFiles[rng.nextInt(4)];
        byFile[darkBishopFile] = PieceType.BISHOP;

        List<Integer> remaining = new ArrayList<>();
        for (int file = 0; file < BOARD_SIZE; file++) {
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
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }

    public Piece[][] getBoardSnapshot() {
        Piece[][] copy = new Piece[BOARD_SIZE][BOARD_SIZE];
        for (int rank = 0; rank < BOARD_SIZE; rank++) {
            for (int file = 0; file < BOARD_SIZE; file++) {
                copy[rank][file] = pieces[rank][file];
            }
        }
        return copy;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }
}
