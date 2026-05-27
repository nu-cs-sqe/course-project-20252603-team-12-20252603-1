package ui;

import domain.Board;
import java.awt.BorderLayout;
import java.util.Locale;
import javax.swing.JFrame;

public class MainView extends JFrame {

  private final BoardController boardController;
  private final BoardView boardView;
  private final GameStatsView gameStatsView;

  public MainView(String player1Name, String player2Name, Board board, Locale locale) {
    boardController = new BoardController(board);
    boardView = new BoardView(boardController);
    gameStatsView = new GameStatsView(player1Name, player2Name, locale);
    configureMainView();
  }

  private void configureMainView() {
    addGameStatsView();
    addBoardViewToContentPane();
    registerBoardViewWithController();
  }

  private void addGameStatsView() {
    getContentPane().add(gameStatsView, BorderLayout.NORTH);
  }

  private void addBoardViewToContentPane() {
    getContentPane().add(boardView, BorderLayout.CENTER);
  }

  private void registerBoardViewWithController() {
    boardController.setBoardView(boardView);
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
