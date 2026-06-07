package ui;

import domain.Board;
import domain.BoardInitializer;
import domain.FischerRandomBoardInitializer;
import domain.StandardBoardInitializer;
import java.util.Locale;
import java.util.Random;

public class WelcomeController {

    private final Messages messages;
    private final WelcomeView welcomeView;

    public WelcomeController() {
        this(Locale.ENGLISH);
    }

    WelcomeController(Locale locale) {
        messages = new Messages(locale);
        welcomeView = new WelcomeView(locale);
        welcomeView.setStartGameAction(this::startGame);
    }

    WelcomeView getWelcomeView() {
        return welcomeView;
    }

    public void show() {
        welcomeView.setVisible(true);
    }

    private void startGame() {
        String player1Name = welcomeView.getPlayer1Name();
        String player2Name = welcomeView.getPlayer2Name();
        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            welcomeView.showError(messages.getString("playerNameEmptyError"));
            return;
        }
        closeWelcomeView();
        new BoardController(player1Name, player2Name, new Board(selectedInitializer())).show();
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
