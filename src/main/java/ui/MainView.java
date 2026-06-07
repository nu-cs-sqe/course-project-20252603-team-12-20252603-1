package ui;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.BorderLayout;
import java.util.Locale;
import javax.swing.JFrame;

public class MainView extends JFrame {

    private final BoardController boardController;
    private final BoardView boardView;
    private final GameStatsView gameStatsView;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Intentional shared reference for collaboration")
    public MainView(
            String player1Name,
            String player2Name,
            BoardController boardController,
            Locale locale) {
        this.boardController = boardController;
        boardView = new BoardView(boardController);
        gameStatsView = new GameStatsView(player1Name, player2Name, locale);
        configureMainView(locale);
    }

    private void configureMainView(Locale locale) {
        Messages messages = new Messages(locale);
        setTitle(messages.getString("appTitle"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addGameStatsView();
        addBoardViewToContentPane();
        registerBoardViewWithController();
        pack();
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
