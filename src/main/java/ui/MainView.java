package ui;

import domain.Board;
import javax.swing.JFrame;

public class MainView extends JFrame {

  private final BoardController boardController;
  private final BoardView boardView;
  private final GameStatsView gameStatsView;

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
    getContentPane().add(gameStatsView);
  }

  private void addBoardView() {
    getContentPane().add(boardView);
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
