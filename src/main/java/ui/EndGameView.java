package ui;

import javax.swing.JFrame;

class EndGameView extends JFrame {

    private Runnable playAgainAction = () -> {};

    EndGameView(String resultMessage) {
        buildUi(resultMessage);
    }

    void setPlayAgainAction(Runnable action) {
        this.playAgainAction = action;
    }

    private void buildUi(String resultMessage) {
    }
}
