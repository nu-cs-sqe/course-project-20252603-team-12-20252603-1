package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class WelcomeView extends JFrame {

    private final JTextField player1NameField;
    private final JTextField player2NameField;
    private final JLabel errorLabel;
    private Runnable startGameAction = () -> {};
    private boolean chess960Selected = false;

    public WelcomeView() {
        player1NameField = new JTextField();
        player2NameField = new JTextField();
        errorLabel = new JLabel();
    }

    public String getPlayer1Name() {
        return player1NameField.getText();
    }

    public String getPlayer2Name() {
        return player2NameField.getText();
    }

    void setPlayer1Name(String name) {
        player1NameField.setText(name);
    }

    void setPlayer2Name(String name) {
        player2NameField.setText(name);
    }

    public boolean isChess960Selected() {
        return chess960Selected;
    }

    void setChess960Selected(boolean selected) {
        chess960Selected = selected;
    }

    public void setStartGameAction(Runnable action) {
        this.startGameAction = action;
    }

    void clickStartGame() {
        startGameAction.run();
    }

    public void showError(String message) {
        errorLabel.setText(message);
    }

    String getErrorText() {
        return errorLabel.getText();
    }
}
