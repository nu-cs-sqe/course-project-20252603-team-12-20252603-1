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

    void clickPlayAgain() {
        playAgainAction.run();
    }

    private void buildUi(String resultMessage) {
    }
}
