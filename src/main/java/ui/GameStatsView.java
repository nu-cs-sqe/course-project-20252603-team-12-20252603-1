package ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameStatsView extends JPanel {

    private final JLabel currentPlayerLabel;
    private final JLabel gameStateLabel;

    public GameStatsView(String player1Name, String player2Name) {
        currentPlayerLabel = new JLabel(player1Name);
        gameStateLabel = new JLabel("");
        add(currentPlayerLabel);
        add(gameStateLabel);
    }

    String getCurrentPlayerLabelText() {
        return currentPlayerLabel.getText();
    }
}
