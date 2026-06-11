package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class EndGameView extends JFrame {

    private static final Color BACKGROUND = new Color(30, 20, 12);
    private static final Color ACCENT_COLOR = new Color(181, 136, 99);
    private static final Color TEXT_COLOR = new Color(240, 217, 181);
    private static final Font RESULT_FONT = new Font("Serif", Font.BOLD, 36);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 16);

    private JButton playAgainButton;
    private Runnable playAgainAction = () -> {};

    EndGameView(String resultMessage, Locale locale) {
        buildUi(resultMessage, locale);
    }

    void setPlayAgainAction(Runnable action) {
        this.playAgainAction = action;
    }

    void clickPlayAgain() {
        playAgainAction.run();
    }

    String getPlayAgainButtonText() {
        return playAgainButton.getText();
    }

    private void buildUi(String resultMessage, Locale locale) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));

        JLabel resultLabel = new JLabel(resultMessage);
        resultLabel.setFont(RESULT_FONT);
        resultLabel.setForeground(TEXT_COLOR);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(resultLabel);
        panel.add(Box.createVerticalStrut(40));

        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        String playAgain = bundle.getString("playAgain");
        final String gameOverTitle = bundle.getString("gameOverTitle");
        playAgainButton = new JButton(playAgain);
        playAgainButton.setFont(BUTTON_FONT);
        playAgainButton.setBackground(ACCENT_COLOR);
        playAgainButton.setForeground(TEXT_COLOR);
        playAgainButton.setFocusPainted(false);
        playAgainButton.setOpaque(true);
        playAgainButton.setBorderPainted(false);
        playAgainButton.setMaximumSize(new Dimension(180, 42));
        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgainButton.addActionListener(e -> clickPlayAgain());
        panel.add(playAgainButton);

        setTitle(gameOverTitle);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND);
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(null);
    }
}
