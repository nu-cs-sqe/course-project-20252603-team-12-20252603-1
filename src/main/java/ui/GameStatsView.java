package ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameStatsView extends JPanel {

  private static final String MATCHUP_SEPARATOR = " vs ";

  private final JLabel currentPlayerLabel;
  private final JLabel gameStateLabel;

  public GameStatsView(String player1Name, String player2Name) {
    currentPlayerLabel = new JLabel(player1Name);
    gameStateLabel = new JLabel(formatMatchupLine(player1Name, player2Name));
    add(currentPlayerLabel);
    add(gameStateLabel);
  }

  private static String formatMatchupLine(String player1Name, String player2Name) {
    return player1Name + MATCHUP_SEPARATOR + player2Name;
  }

  String getCurrentPlayerLabelText() {
    return currentPlayerLabel.getText();
  }

  String getGameStateLabelText() {
    return gameStateLabel.getText();
  }

  public void updateCurrentPlayerLabel(String playerName) {
    currentPlayerLabel.setText(playerName);
  }
}
