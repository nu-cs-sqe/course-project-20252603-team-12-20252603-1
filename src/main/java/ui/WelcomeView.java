package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class WelcomeView extends JFrame {

    private static final Color BACKGROUND   = new Color(30, 20, 12);
    private static final Color ACCENT_COLOR = new Color(181, 136, 99);
    private static final Color TEXT_COLOR   = new Color(240, 217, 181);
    private static final Color FIELD_BG     = new Color(255, 248, 240);

    private static final Font TITLE_FONT  = new Font("Serif",     Font.BOLD,  52);
    private static final Font LABEL_FONT  = new Font("SansSerif", Font.PLAIN, 18);
    private static final Font FIELD_FONT  = new Font("SansSerif", Font.PLAIN, 16);
    private static final Font RADIO_FONT  = new Font("SansSerif", Font.PLAIN, 18);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD,  16);

    private JTextField player1NameField;
    private JTextField player2NameField;
    private JRadioButton standardRadioButton;
    private JRadioButton chess960RadioButton;
    private JLabel errorLabel = new JLabel("");
    private Runnable startGameAction = () -> {};

    public WelcomeView() {
        player1NameField    = new JTextField();
        player2NameField    = new JTextField();
        standardRadioButton = new JRadioButton();
        chess960RadioButton = new JRadioButton();
        standardRadioButton.setSelected(true);
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
        return chess960RadioButton.isSelected();
    }

    void setChess960Selected(boolean selected) {
        chess960RadioButton.setSelected(selected);
        standardRadioButton.setSelected(!selected);
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

    private void createWelcomeScreenUI() {
        // untestable: Swing UI assembly
        JPanel panel = buildMainPanel();
        addTitle(panel);
        addPlayerNameFields(panel);
        addModeSelector(panel);
        addStartButton(panel);
        addErrorLabel(panel);
        configureWindow(panel);
    }

    private JPanel buildMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(55, 90, 55, 90));
        return panel;
    }

    private void addTitle(JPanel panel) {
        JLabel title = new JLabel("♟  Chess  ♟");
        title.setFont(TITLE_FONT);
        title.setForeground(TEXT_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createVerticalStrut(45));
    }

    private void addPlayerNameFields(JPanel panel) {
        addPlayerNameField(panel, "Player 1", player1NameField);
        addPlayerNameField(panel, "Player 2", player2NameField);
    }

    private void addPlayerNameField(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));

        field.setFont(FIELD_FONT);
        field.setBackground(FIELD_BG);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        panel.add(field);
        panel.add(Box.createVerticalStrut(18));
    }

    private void addModeSelector(JPanel panel) {
        standardRadioButton.setText("Standard");
        chess960RadioButton.setText("Chess960");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(standardRadioButton);
        modeGroup.add(chess960RadioButton);
        panel.add(buildRadioPanel());
        panel.add(Box.createVerticalStrut(36));
    }

    private JPanel buildRadioPanel() {
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        radioPanel.setBackground(BACKGROUND);
        radioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        for (JRadioButton btn : new JRadioButton[]{standardRadioButton, chess960RadioButton}) {
            btn.setFont(RADIO_FONT);
            btn.setForeground(TEXT_COLOR);
            btn.setBackground(BACKGROUND);
            btn.setFocusPainted(false);
            radioPanel.add(btn);
        }
        return radioPanel;
    }

    private void addStartButton(JPanel panel) {
        JButton startButton = new JButton("Start Game");
        startButton.setFont(BUTTON_FONT);
        startButton.setBackground(ACCENT_COLOR);
        startButton.setForeground(TEXT_COLOR);
        startButton.setFocusPainted(false);
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        startButton.setMaximumSize(new Dimension(180, 42));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> clickStartGame());
        panel.add(startButton);
    }

    private void addErrorLabel(JPanel panel) {
        errorLabel.setForeground(new Color(200, 50, 50));
        errorLabel.setFont(LABEL_FONT);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(10));
        panel.add(errorLabel);
    }

    private void configureWindow(JPanel panel) {
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND);
        getContentPane().add(panel);
        setSize(600, 600);
        setLocationRelativeTo(null);
    }
}
