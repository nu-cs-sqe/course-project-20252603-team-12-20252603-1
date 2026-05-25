package ui;

import domain.Board;
import java.awt.BorderLayout;
import javax.swing.JFrame;

public class MainView extends JFrame {

  private final BoardController boardController;
  private final BoardView boardView;
  private final GameStatsView gameStatsView;

  /**
   * @param mode start mode from the welcome flow; reserved for future UI branching. Board layout is
   *     supplied by the caller via {@code board} (initializer runs before this view is constructed).
   */
  public MainView(String player1Name, String player2Name, GameStartMode mode, Board board) {
    boardController = new BoardController(board);
    boardView = new BoardView(boardController);
    gameStatsView = new GameStatsView(player1Name, player2Name);
    configureMainView();
  }

  private void configureMainView() {
    addGameStatsView();
    addBoardView();
  }

  private void addGameStatsView() {
    getContentPane().add(gameStatsView, BorderLayout.NORTH);
  }

  private void addBoardView() {
    getContentPane().add(boardView, BorderLayout.CENTER);
    boardController.setBoardView(boardView);
  }

  BoardView getRegisteredBoardView() {
    return boardController.getBoardView();
  }

  BoardController getBoardController() {
    return boardController;
  }

  BoardView getBoardView() {
    return boardView;
  }

  GameStatsView getGameStatsView() {
    return gameStatsView;
  }
}
