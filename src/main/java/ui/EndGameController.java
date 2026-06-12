package ui;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Locale;
import java.util.function.Consumer;
import javax.swing.JFrame;

class EndGameController {

    private EndGameView endGameView;
    private final JFrame mainView;
    private final Locale locale;
    private final String resultMessage;
    private Consumer<Locale> playAgainAction = loc -> new WelcomeController(loc).show();

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

    void setPlayAgainAction(Consumer<Locale> playAgainAction) {
        this.playAgainAction = playAgainAction;
    }

    void playAgain() {
        endGameView.dispose();
        playAgainAction.accept(locale);
    }

    EndGameView getEndGameView() {
        return endGameView;
    }

    void show() {
        setEndGameView(new EndGameView(resultMessage, locale));
        mainView.setVisible(false);
        endGameView.setVisible(true);
    }
}
