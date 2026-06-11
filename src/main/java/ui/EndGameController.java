package ui;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Locale;
import javax.swing.JFrame;

class EndGameController {

    private EndGameView endGameView;
    private final JFrame mainView;
    private final Locale locale;
    private final String resultMessage;

    EndGameController(String resultMessage, JFrame mainView) {
        this(resultMessage, mainView, Locale.ENGLISH);
    }

    EndGameController(String resultMessage, JFrame mainView, Locale locale) {
        this.resultMessage = resultMessage;
        this.mainView = mainView;
        this.locale = locale;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Intentional shared reference for collaboration")
    void setEndGameView(EndGameView endGameView) {
        this.endGameView = endGameView;
        endGameView.setPlayAgainAction(this::playAgain);
    }

    void playAgain() {
        endGameView.dispose();
        new WelcomeController(locale).show();
    }

    EndGameView getEndGameView() {
        return endGameView;
    }

    void show() {
        if (endGameView == null) {
            endGameView = new EndGameView(resultMessage, locale);
            endGameView.setPlayAgainAction(this::playAgain);
        }
        mainView.setVisible(false);
        endGameView.setVisible(true);
    }
}
