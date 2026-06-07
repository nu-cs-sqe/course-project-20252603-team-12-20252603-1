package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

    private static final int ENGLISH_LANGUAGE_INDEX = 0;
    private static final int SPANISH_LANGUAGE_INDEX = 1;

    private Messages messages;
    private JTextField player1NameField;
    private JTextField player2NameField;
    private JRadioButton standardRadioButton;
    private JRadioButton chess960RadioButton;
    private JLabel welcomeTitleLabel;
    private JLabel player1Label;
    private JLabel player2Label;
    private JButton startGameButton;
    private JLabel languageLabel;
    private JComboBox<String> languageComboBox;
    private JLabel errorLabel = new JLabel("");
    private Runnable startGameAction = () -> {};

    /**
     * @param locale non-null locale used to load UI strings
     */
    public WelcomeView(Locale locale) {
        messages = new Messages(locale);
        player1NameField    = new JTextField();
        player2NameField    = new JTextField();
        standardRadioButton = new JRadioButton();
        chess960RadioButton = new JRadioButton();
        standardRadioButton.setSelected(true);
        createWelcomeScreenUi(locale);
    }

    Locale getSelectedLocale() {
        if (languageComboBox.getSelectedIndex() == SPANISH_LANGUAGE_INDEX) {
            return Locale.forLanguageTag("es");
        }
        return Locale.ENGLISH;
    }

    void selectLanguageIndex(int index) {
        languageComboBox.setSelectedIndex(index);
    }

    String getLanguageLabelText() {
        return languageLabel.getText();
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

    String getWelcomeTitleText() {
        return welcomeTitleLabel.getText();
    }

    String getPlayer1LabelText() {
        return player1Label.getText();
    }

    String getPlayer2LabelText() {
        return player2Label.getText();
    }

    String getStandardModeLabelText() {
        return standardRadioButton.getText();
    }

    String getChess960ModeLabelText() {
        return chess960RadioButton.getText();
    }

    String getStartGameButtonText() {
        return startGameButton.getText();
    }

    private void createWelcomeScreenUi(Locale initialLocale) {
        // untestable: Swing UI assembly
        JPanel panel = buildMainPanel();
        addTitle(panel);
        addPlayerNameFields(panel);
        addModeSelector(panel);
        addLanguageSelector(panel, initialLocale);
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
        welcomeTitleLabel = new JLabel(messages.getString("welcomeTitle"));
        welcomeTitleLabel.setFont(TITLE_FONT);
        welcomeTitleLabel.setForeground(TEXT_COLOR);
        welcomeTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcomeTitleLabel);
        panel.add(Box.createVerticalStrut(45));
    }

    private void addPlayerNameFields(JPanel panel) {
        player1Label = addPlayerNameField(
                panel, messages.getString("player1Label"), player1NameField);
        player2Label = addPlayerNameField(
                panel, messages.getString("player2Label"), player2NameField);
    }

    private JLabel addPlayerNameField(JPanel panel, String labelText, JTextField field) {
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
        return label;
    }

    private void addModeSelector(JPanel panel) {
        standardRadioButton.setText(messages.getString("standardMode"));
        chess960RadioButton.setText(messages.getString("chess960Mode"));
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

    private void addLanguageSelector(JPanel panel, Locale initialLocale) {
        languageLabel = new JLabel(messages.getString("languageLabel"));
        languageLabel.setFont(LABEL_FONT);
        languageLabel.setForeground(TEXT_COLOR);
        languageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(languageLabel);
        panel.add(Box.createVerticalStrut(5));

        String[] languageOptions = {
            messages.getString("languageEnglish"),
            messages.getString("languageSpanish")
        };
        languageComboBox = new JComboBox<>(languageOptions);
        languageComboBox.setFont(FIELD_FONT);
        languageComboBox.setMaximumSize(new Dimension(180, 36));
        languageComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        languageComboBox.setSelectedIndex(languageIndexFor(initialLocale));
        languageComboBox.addActionListener(e -> applyLocale(getSelectedLocale()));
        panel.add(languageComboBox);
        panel.add(Box.createVerticalStrut(18));
    }

    private static int languageIndexFor(Locale locale) {
        if ("es".equals(locale.getLanguage())) {
            return SPANISH_LANGUAGE_INDEX;
        }
        return ENGLISH_LANGUAGE_INDEX;
    }

    private void applyLocale(Locale locale) {
        messages = new Messages(locale);
        welcomeTitleLabel.setText(messages.getString("welcomeTitle"));
        player1Label.setText(messages.getString("player1Label"));
        player2Label.setText(messages.getString("player2Label"));
        standardRadioButton.setText(messages.getString("standardMode"));
        chess960RadioButton.setText(messages.getString("chess960Mode"));
        startGameButton.setText(messages.getString("startGame"));
        languageLabel.setText(messages.getString("languageLabel"));
        setTitle(messages.getString("appTitle"));
    }

    private void addStartButton(JPanel panel) {
        startGameButton = new JButton(messages.getString("startGame"));
        startGameButton.setFont(BUTTON_FONT);
        startGameButton.setBackground(ACCENT_COLOR);
        startGameButton.setForeground(TEXT_COLOR);
        startGameButton.setFocusPainted(false);
        startGameButton.setOpaque(true);
        startGameButton.setBorderPainted(false);
        startGameButton.setMaximumSize(new Dimension(180, 42));
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGameButton.addActionListener(e -> clickStartGame());
        panel.add(startGameButton);
    }

    private void addErrorLabel(JPanel panel) {
        errorLabel.setForeground(new Color(200, 50, 50));
        errorLabel.setFont(LABEL_FONT);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(10));
        panel.add(errorLabel);
    }

    private void configureWindow(JPanel panel) {
        setTitle(messages.getString("appTitle"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND);
        getContentPane().add(panel);
        setSize(600, 600);
        setLocationRelativeTo(null);
    }
}
