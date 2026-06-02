package ui;

import domain.Board;
import domain.BoardInitializer;
import domain.FischerRandomBoardInitializer;
import domain.StandardBoardInitializer;
import java.util.Random;

public class WelcomeController {

    private final WelcomeView welcomeView;

    public WelcomeController() {
        welcomeView = new WelcomeView();
        welcomeView.setStartGameAction(this::startGame);
    }

    WelcomeView getWelcomeView() {
        return welcomeView;
    }

    public void show() {
        welcomeView.setVisible(true);
    }

    private void startGame() {
        String player1Name = welcomeView.getPlayer1Name();
        String player2Name = welcomeView.getPlayer2Name();
        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            welcomeView.showError("Player name cannot be empty");
            return;
        }
        closeWelcomeView();
        new BoardController(player1Name, player2Name, new Board(selectedInitializer())).show();
    }

    BoardInitializer selectedInitializer() {
        return welcomeView.isChess960Selected()
            ? new FischerRandomBoardInitializer(new Random())
            : new StandardBoardInitializer();
    }

    private void closeWelcomeView() {
        welcomeView.setVisible(false);
        welcomeView.dispose();
    }
}
