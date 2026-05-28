package ui;

import domain.gamestate.GameState;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel showing current player and matchup line for game initialization.
 *
 * <p>All {@link String} parameters are non-null; callers must not pass {@code null}.
 */
public class GameStatsView extends JPanel {

  private static final String MATCHUP_SEPARATOR = " vs ";

  private final String player1Name;
  private final String player2Name;
  private final JLabel currentPlayerLabel;
  private final JLabel gameStateLabel;

  /**
   * @param player1Name non-null player one name (may be empty)
   * @param player2Name non-null player two name (may be empty)
   */
  public GameStatsView(String player1Name, String player2Name) {
    this.player1Name = player1Name;
    this.player2Name = player2Name;
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

  /**
   * @param playerName non-null name to display (may be empty)
   */
  public void updateCurrentPlayerLabel(String playerName) {
    currentPlayerLabel.setText(playerName);
  }

  void updateCurrentPlayerLabel(GameState currentGameState) {
    switch (currentGameState) {
      case WHITE_TURN:
        updateCurrentPlayerLabel(player1Name);
        break;
      case BLACK_TURN:
        updateCurrentPlayerLabel(player2Name);
        break;
      default:
        throw new IllegalArgumentException("Current player label requires an active turn");
    }
  }
}
