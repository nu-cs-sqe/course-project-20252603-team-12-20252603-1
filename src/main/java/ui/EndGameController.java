package ui;

import javax.swing.JFrame;

class EndGameController {

    private final JFrame mainView;

    EndGameController(String resultMessage, JFrame mainView) {
        this.mainView = mainView;
    }

    void show() {
        mainView.setVisible(false);
    }
}
