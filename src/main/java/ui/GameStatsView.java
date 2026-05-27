package ui;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel showing current player and matchup line for game initialization.
 *
 * <p>All {@link String} parameters are non-null; callers must not pass {@code null}.
 */
public class GameStatsView extends JPanel {

  private final ResourceBundle bundle;
  private final JLabel currentPlayerLabel;
  private final JLabel gameStateLabel;

  /**
   * @param player1Name non-null player one name (may be empty)
   * @param player2Name non-null player two name (may be empty)
   * @param locale non-null locale used to load UI strings
   */
  public GameStatsView(String player1Name, String player2Name, Locale locale) {
    bundle = ResourceBundle.getBundle("messages", locale);
    currentPlayerLabel = new JLabel(player1Name);
    gameStateLabel = new JLabel(formatMatchupLine(player1Name, player2Name));
    add(currentPlayerLabel);
    add(gameStateLabel);
  }

  private String formatMatchupLine(String player1Name, String player2Name) {
    return MessageFormat.format(bundle.getString("matchupPattern"), player1Name, player2Name);
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
}
