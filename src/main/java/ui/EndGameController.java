package ui;

import javax.swing.JFrame;

class EndGameController {

    private final EndGameView endGameView;
    private final JFrame mainView;

    EndGameController(String resultMessage, JFrame mainView) {
        this.mainView = mainView;
        endGameView = new EndGameView(resultMessage);
        endGameView.setPlayAgainAction(this::playAgain);
    }

    private void playAgain() {
        endGameView.dispose();
        new WelcomeController().show();
    }

    EndGameView getEndGameView() {
        return endGameView;
    }

    void show() {
        mainView.setVisible(false);
        endGameView.setVisible(true);
    }
}
