package ui;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class WelcomeView extends JFrame {

    private JTextField player1NameField;
    private JTextField player2NameField;
    private JRadioButton standardRadioButton;
    private JRadioButton chess960RadioButton;
    private Runnable startGameAction;

    public WelcomeView() {
        player1NameField   = new JTextField();
        player2NameField   = new JTextField();
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
        if (startGameAction != null) {
            startGameAction.run();
        }
    }

    private void createWelcomeScreenUI() {
        // untestable: Swing UI assembly
        java.awt.Color background  = new java.awt.Color(30, 20, 12);
        java.awt.Color accentColor = new java.awt.Color(181, 136, 99);
        java.awt.Color textColor   = new java.awt.Color(240, 217, 181);
        java.awt.Color fieldBg     = new java.awt.Color(255, 248, 240);

        java.awt.Font titleFont  = new java.awt.Font("Serif",     java.awt.Font.BOLD,  52);
        java.awt.Font labelFont  = new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 18);
        java.awt.Font fieldFont  = new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 16);
        java.awt.Font radioFont  = new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 18);
        java.awt.Font buttonFont = new java.awt.Font("SansSerif", java.awt.Font.BOLD,  16);

        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setBackground(background);
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(55, 90, 55, 90));

        javax.swing.JLabel title = new javax.swing.JLabel("♟  Chess  ♟");
        title.setFont(titleFont);
        title.setForeground(textColor);
        title.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(javax.swing.Box.createVerticalStrut(45));

        for (int i = 0; i < 2; i++) {
            javax.swing.JTextField field = (i == 0) ? player1NameField : player2NameField;
            String labelText = (i == 0) ? "Player 1" : "Player 2";

            javax.swing.JLabel lbl = new javax.swing.JLabel(labelText);
            lbl.setFont(labelFont);
            lbl.setForeground(textColor);
            lbl.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            panel.add(lbl);
            panel.add(javax.swing.Box.createVerticalStrut(5));

            field.setFont(fieldFont);
            field.setBackground(fieldBg);
            field.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 36));
            field.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(accentColor, 1),
                javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 8)));
            panel.add(field);
            panel.add(javax.swing.Box.createVerticalStrut(18));
        }

        javax.swing.ButtonGroup modeGroup = new javax.swing.ButtonGroup();
        modeGroup.add(standardRadioButton);
        modeGroup.add(chess960RadioButton);

        javax.swing.JPanel radioPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 0));
        radioPanel.setBackground(background);
        radioPanel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        for (JRadioButton btn : new JRadioButton[]{standardRadioButton, chess960RadioButton}) {
            btn.setFont(radioFont);
            btn.setForeground(textColor);
            btn.setBackground(background);
            btn.setFocusPainted(false);
            radioPanel.add(btn);
        }
        standardRadioButton.setText("Standard");
        chess960RadioButton.setText("Chess960");

        panel.add(radioPanel);
        panel.add(javax.swing.Box.createVerticalStrut(36));

        javax.swing.JButton startButton = new javax.swing.JButton("Start Game");
        startButton.setFont(buttonFont);
        startButton.setBackground(accentColor);
        startButton.setForeground(textColor);
        startButton.setFocusPainted(false);
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        startButton.setMaximumSize(new java.awt.Dimension(180, 42));
        startButton.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> {
            if (startGameAction != null) {
                startGameAction.run();
            }
        });
        panel.add(startButton);

        setTitle("Chess");
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(background);
        getContentPane().add(panel);
        setSize(600, 600);
        setLocationRelativeTo(null);
    }
}
