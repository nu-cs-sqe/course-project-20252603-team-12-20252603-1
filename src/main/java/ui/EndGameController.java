package ui;

import java.util.Locale;
import javax.swing.JFrame;

class EndGameController {

    private final EndGameView endGameView;
    private final JFrame mainView;

    EndGameController(String resultMessage, JFrame mainView) {
        this(resultMessage, mainView, Locale.ENGLISH);
    }

    EndGameController(String resultMessage, JFrame mainView, Locale locale) {
        this.mainView = mainView;
        endGameView = new EndGameView(resultMessage, locale);
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
