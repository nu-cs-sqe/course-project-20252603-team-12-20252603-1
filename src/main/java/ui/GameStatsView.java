package ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameStatsView extends JPanel {

    private final JLabel currentPlayerLabel;
    private final JLabel gameStateLabel;

    public GameStatsView(String player1Name, String player2Name) {
        currentPlayerLabel = new JLabel(player1Name);
        if (player1Name.isEmpty() && player2Name.isEmpty()) {
            gameStateLabel = new JLabel("");
        } else {
            gameStateLabel = new JLabel(player1Name + " vs " + player2Name);
        }
        add(currentPlayerLabel);
        add(gameStateLabel);
    }

    String getCurrentPlayerLabelText() {
        return currentPlayerLabel.getText();
    }

    String getGameStateLabelText() {
        return gameStateLabel.getText();
    }
}
