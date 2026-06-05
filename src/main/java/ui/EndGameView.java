package ui;

import javax.swing.JFrame;

class EndGameView extends JFrame {
    private final String resultMessage;

    EndGameView(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    String getResultMessage() {
        return resultMessage;
    }
}
