package ui;

import domain.Board;
import domain.BoardInitializer;
import domain.FischerRandomBoardInitializer;
import domain.StandardBoardInitializer;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class WelcomeController {

    private WelcomeView welcomeView;
    private final Locale locale;

    public WelcomeController() {
        this(Locale.ENGLISH);
    }

    WelcomeController(Locale locale) {
        this.locale = locale;
    }

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Intentional shared reference for collaboration")
    void setWelcomeView(WelcomeView welcomeView) {
        this.welcomeView = welcomeView;
        welcomeView.setStartGameAction(this::startGame);
    }

    WelcomeView getWelcomeView() {
        return welcomeView;
    }

    public void show() {
        setWelcomeView(new WelcomeView(locale));
        welcomeView.setVisible(true);
    }

    void startGame() {
        String player1Name = welcomeView.getPlayer1Name();
        String player2Name = welcomeView.getPlayer2Name();
        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            Locale selected = welcomeView.getSelectedLocale();
            ResourceBundle bundle = ResourceBundle.getBundle("messages", selected);
            String playerNameEmptyError = bundle.getString("playerNameEmptyError");
            welcomeView.showError(playerNameEmptyError);
            return;
        }
        closeWelcomeView();
        new BoardController(
                player1Name,
                player2Name,
                new Board(selectedInitializer()),
                welcomeView.getSelectedLocale()).show();
    }

    BoardInitializer selectedInitializer() {
        return welcomeView.isChess960Selected()
            ? new FischerRandomBoardInitializer(new Random())
            : new StandardBoardInitializer();
    }

    private void closeWelcomeView() {
        welcomeView.setVisible(false);
        welcomeView.dispose();
    }
}
