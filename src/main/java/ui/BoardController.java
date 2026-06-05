package ui;

import domain.Board;
import domain.gamestate.GameState;
import domain.location.Location;
import domain.move.Move;
import domain.move.MoveType;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class BoardController {

    private static final int BOARD_SIZE = 8;

    private final String player1Name;
    private final String player2Name;
    private final Board board;
    private MainView mainView;
    private BoardView boardView;
    private Optional<Location> lastSelectedLoc;
    private Function<PieceColor, PieceType> promotionPicker = this::promptForPromotionPiece;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Intentional shared reference for collaboration")
    public BoardController(String player1Name, String player2Name, Board board) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.board = board;
        lastSelectedLoc = Optional.empty();
    }

    public void show() {
        mainView = new MainView(player1Name, player2Name, this);
        mainView.setVisible(true);
        updateCurrentPlayerLabel();
    }

    private void updateCurrentPlayerLabel() {
        if (mainView == null) {
            return;
        }
        String text;
        switch (board.getCurrentGameState()) {
            case WHITE_TURN:
                text = player1Name;
                break;
            case BLACK_TURN:
                text = player2Name;
                break;
            case WHITE_WIN:
                text = player1Name + " wins!";
                break;
            case BLACK_WIN:
                text = player2Name + " wins!";
                break;
            case DRAW:
                text = "Draw!";
                break;
            default:
                text = "";
                break;
        }
        mainView.getGameStatsView().updateCurrentPlayerLabel(text);
    }

    MainView getMainView() {
        return mainView;
    }

    void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    void setPromotionPicker(Function<PieceColor, PieceType> promotionPicker) {
        this.promotionPicker = promotionPicker;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Intentional shared reference for collaboration")
    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    BoardView getBoardView() {
        return boardView;
    }

    public boolean hasSelection() {
        return lastSelectedLoc.isPresent();
    }

    public Optional<Location> getSelectedLocation() {
        return lastSelectedLoc;
    }

    public List<Move> getLegalMovesForSelection() {
        if (!lastSelectedLoc.isPresent()) {
            return new ArrayList<>();
        }
        return board.getLegalMoves(lastSelectedLoc.get());
    }

    public void handleSquareClick(Location loc) {
        if (!isInBounds(loc)) {
            return;
        }
        GameState state = board.getCurrentGameState();
        if (state != GameState.WHITE_TURN && state != GameState.BLACK_TURN) {
            return;
        }
        PieceColor currentColor =
                (state == GameState.WHITE_TURN) ? PieceColor.WHITE : PieceColor.BLACK;
        if (lastSelectedLoc.isPresent()) {
            handleDestinationClick(loc, currentColor);
        } else {
            handleSourceClick(loc, currentColor);
        }
    }

    private void handleSourceClick(Location loc, PieceColor currentColor) {
        int file = loc.getX();
        int rank = loc.getY();
        Piece at = board.getPieceAt(rank, file);
        if (at.getType() == PieceType.NONE || at.getColor() != currentColor) {
            return;
        }
        lastSelectedLoc = Optional.of(loc);
        repaintBoardView();
    }

    private void handleDestinationClick(Location dest, PieceColor currentColor) {
        Location src = lastSelectedLoc.get();
        Piece destPiece = board.getPieceAt(dest.getY(), dest.getX());

        if (destPiece.getType() != PieceType.NONE && destPiece.getColor() == currentColor) {
            lastSelectedLoc = Optional.of(dest);
            repaintBoardView();
            return;
        }

        List<Move> legalMoves = board.getLegalMoves(src);
        Optional<Move> matchingMove = findMoveToDestination(legalMoves, dest);

        if (!matchingMove.isPresent()) {
            lastSelectedLoc = Optional.empty();
            repaintBoardView();
            return;
        }

        executeMove(matchingMove.get(), currentColor);
    }

    private void executeMove(Move move, PieceColor currentColor) {
        if (move.getType() == MoveType.PROMOTION) {
            PieceType choice = promotionPicker.apply(currentColor);
            move = move.withPromotionType(choice);
        }
        board.makeMove(move);
        lastSelectedLoc = Optional.empty();
        updateCurrentPlayerLabel();
        repaintBoardView();
        if (isGameOver()) {
            showEndGame();
        }
    }

    private Optional<Move> findMoveToDestination(List<Move> moves, Location dest) {
        for (Move move : moves) {
            if (move.getTo().getX() == dest.getX() && move.getTo().getY() == dest.getY()) {
                return Optional.of(move);
            }
        }
        return Optional.empty();
    }

    private boolean isGameOver() {
        GameState state = board.getCurrentGameState();
        return state == GameState.WHITE_WIN
                || state == GameState.BLACK_WIN
                || state == GameState.DRAW;
    }

    private PieceType promptForPromotionPiece(PieceColor color) {
        return new PromotionDialog(mainView, color).showAndGetChoice();
    }

    private void showEndGame() {
        new EndGameController(buildEndGameMessage(), mainView).show();
    }

    String buildEndGameMessage() {
        switch (board.getCurrentGameState()) {
            case WHITE_WIN: return player1Name + " wins!";
            case BLACK_WIN: return player2Name + " wins!";
            case DRAW: return "Draw!";
            default: return "";
        }
    }

    private void repaintBoardView() {
        if (boardView != null) {
            boardView.repaint();
        }
    }

    private static boolean isInBounds(Location loc) {
        int x = loc.getX();
        int y = loc.getY();
        return x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE;
    }

    public Piece[][] getBoardSnapshot() {
        return board.getSnapshot();
    }

    public GameState getCurrentGameState() {
        return board.getCurrentGameState();
    }
}
