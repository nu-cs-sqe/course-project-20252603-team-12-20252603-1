package ui;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class WelcomeView extends JFrame {

    private final JTextField player1NameField;
    private final JTextField player2NameField;

    public WelcomeView() {
        player1NameField = new JTextField();
        player2NameField = new JTextField();
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
}
