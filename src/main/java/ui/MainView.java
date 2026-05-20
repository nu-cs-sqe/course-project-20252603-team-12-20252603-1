package ui;

import domain.Board;
import javax.swing.JFrame;

public class MainView extends JFrame {

  private final BoardController boardController;
  private final BoardView boardView;

  public MainView(String player1Name, String player2Name, GameStartMode mode, Board board) {
    boardController = new BoardController(board);
    boardView = new BoardView();
  }

  BoardController getBoardController() {
    return boardController;
  }

  BoardView getBoardView() {
    return boardView;
  }
}
