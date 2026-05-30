package ui;

import domain.BoardInitializer;
import domain.StandardBoardInitializer;

public class WelcomeController {

    private final WelcomeView welcomeView;

    public WelcomeController() {
        welcomeView = new WelcomeView();
        welcomeView.setStartGameAction(this::startGame);
    }

    public void show() {
        welcomeView.setVisible(true);
    }

    void startGame() {
        if (welcomeView.getPlayer1Name().isEmpty() || welcomeView.getPlayer2Name().isEmpty()) {
            welcomeView.showError("Player names cannot be empty.");
            return;
        }
        welcomeView.setVisible(false);
        welcomeView.dispose();
    }

    BoardInitializer selectedInitializer() {
        return new StandardBoardInitializer();
    }

    WelcomeView getWelcomeView() {
        return welcomeView;
    }
}
