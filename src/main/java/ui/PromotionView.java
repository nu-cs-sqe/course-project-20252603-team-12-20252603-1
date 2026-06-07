package ui;

import domain.piece.PieceColor;
import domain.piece.PieceType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class PromotionView {

    private static final Color BACKGROUND = new Color(30, 20, 12);
    private static final Color BUTTON_BG = new Color(181, 136, 99);
    private static final Color TEXT_COLOR = new Color(240, 217, 181);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 16);
    private static final int BUTTON_SIZE = 72;

    private final Messages messages;
    private final JDialog dialog;
    private final PieceColor color;
    private final JLabel promptLabel;
    private PieceType chosenType = PieceType.QUEEN;

    PromotionView(JFrame parent, PieceColor color) {
        this(parent, color, Locale.ENGLISH);
    }

    PromotionView(JFrame parent, PieceColor color, Locale locale) {
        // untestable: creates JDialog (Swing/AWT component)
        messages = new Messages(locale);
        this.color = color;
        this.dialog = new JDialog(parent, messages.getString("promotePawnTitle"), true);
        promptLabel = buildUi();
    }

    PieceType showAndGetChoice() {
        // untestable: UI/IO modal blocks on user input
        dialog.setVisible(true);
        return chosenType;
    }

    String getDialogTitleText() {
        return dialog.getTitle();
    }

    String getPromptLabelText() {
        return promptLabel.getText();
    }

    private JLabel buildUi() {
        // untestable: Swing layout construction
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel label = new JLabel("Choose promotion piece:");
        label.setForeground(TEXT_COLOR);
        label.setFont(LABEL_FONT);
        panel.add(label);
        for (PieceType type : new PieceType[]{
                PieceType.QUEEN, PieceType.ROOK, PieceType.BISHOP, PieceType.KNIGHT}) {
            panel.add(buildPromotionButton(type));
        }
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(dialog.getParent());
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        return label;
    }

    private JButton buildPromotionButton(PieceType type) {
        // untestable: Swing component construction
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        button.setBackground(BUTTON_BG);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        Image img = loadImage(type);
        button.setIcon(new ImageIcon(
                img.getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, Image.SCALE_SMOOTH)));
        button.addActionListener(e -> {
            chosenType = type;
            dialog.dispose();
        });
        return button;
    }

    private Image loadImage(PieceType type) {
        // untestable: classpath resource loading, no headless equivalent
        String prefix = (color == PieceColor.WHITE) ? "white" : "black";
        String path = "pieces/" + prefix + "_" + type.name().toLowerCase() + ".png";
        java.net.URL resource = getClass().getClassLoader().getResource(path);
        return new ImageIcon(resource).getImage();
    }
}
