package ui;

import domain.Board;
import domain.BoardInitializer;
import domain.FischerRandomBoardInitializer;
import domain.StandardBoardInitializer;
import java.util.Random;

public class WelcomeController {

    private final WelcomeView welcomeView;
    private BoardController startedBoardController;

    public WelcomeController() {
        welcomeView = new WelcomeView();
        welcomeView.setStartGameAction(this::startGame);
    }

    public void show() {
        welcomeView.setVisible(true);
    }

    void startGame() {
        String player1Name = welcomeView.getPlayer1Name();
        String player2Name = welcomeView.getPlayer2Name();
        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            welcomeView.showError("Player names cannot be empty.");
            return;
        }
        startedBoardController =
                new BoardController(player1Name, player2Name, new Board(selectedInitializer()));
        closeWelcomeView();
        new MainView(player1Name, player2Name, startedBoardController).setVisible(true);
    }

    private void closeWelcomeView() {
        welcomeView.setVisible(false);
        welcomeView.dispose();
    }

    BoardInitializer selectedInitializer() {
        return welcomeView.isChess960Selected()
            ? new FischerRandomBoardInitializer(new Random())
            : new StandardBoardInitializer();
    }

    WelcomeView getWelcomeView() {
        return welcomeView;
    }

    BoardController getStartedBoardController() {
        return startedBoardController;
    }
}
