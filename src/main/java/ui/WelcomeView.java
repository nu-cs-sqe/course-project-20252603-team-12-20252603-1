package ui;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class WelcomeView extends JFrame {

    private JTextField player1NameField;
    private JTextField player2NameField;
    private JCheckBox chess960CheckBox;

    public WelcomeView() {
        player1NameField = new JTextField();
        player2NameField = new JTextField();
        createWelcomeScreenUI();
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
        return false;
    }

    void setChess960Selected(boolean selected) {
    }

    private void createWelcomeScreenUI() {
        // untestable: Swing UI assembly
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(3, 2, 10, 10));
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new javax.swing.JLabel("Player 1 Name:"));
        panel.add(player1NameField);
        panel.add(new javax.swing.JLabel("Player 2 Name:"));
        panel.add(player2NameField);

        javax.swing.JButton startButton = new javax.swing.JButton("Start Game");
        panel.add(new javax.swing.JLabel());
        panel.add(startButton);

        setTitle("Chess");
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
    }
}
