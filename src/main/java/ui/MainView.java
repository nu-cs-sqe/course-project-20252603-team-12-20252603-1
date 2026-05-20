package ui;

import domain.Board;
import javax.swing.JFrame;

public class MainView extends JFrame {

  private final BoardController boardController;

  public MainView(String player1Name, String player2Name, GameStartMode mode, Board board) {
    boardController = new BoardController(board);
  }

  BoardController getBoardController() {
    return boardController;
  }
}
