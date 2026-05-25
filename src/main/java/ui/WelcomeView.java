package ui;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class WelcomeView extends JFrame {

    private JTextField player1NameField;
    private JTextField player2NameField;

    public WelcomeView() {
        player1NameField = new JTextField();
        player2NameField = new JTextField();
    }

    public String getPlayer1Name() {
        return player1NameField.getText();
    }

    public String getPlayer2Name() {
        return "";
    }

    void setPlayer1Name(String name) {
        player1NameField.setText(name);
    }

    void setPlayer2Name(String name) {
    }

    private void createWelcomeScreenUI() {
        // untestable: Swing UI assembly
    }
}
