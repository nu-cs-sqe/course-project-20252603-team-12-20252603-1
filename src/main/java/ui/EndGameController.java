package ui;

import javax.swing.JFrame;

class EndGameController {
    private final EndGameView endGameView;
    private final JFrame mainView;

    EndGameController(String resultMessage, JFrame mainView) {
        this.mainView = mainView;
        endGameView = new EndGameView(resultMessage);
    }

    void show() {
        mainView.setVisible(false);
        endGameView.setVisible(true);
    }
}
