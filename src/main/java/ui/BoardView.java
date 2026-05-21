package ui;

import domain.piece.PieceColor;
import domain.piece.PieceType;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumMap;
import java.util.Map;
import javax.swing.JPanel;

public class BoardView extends JPanel {

    private static final int BOARD_SIZE = 8;
    private static final int TILE_SIZE = 75;
    private static final Color LIGHT_SQUARE_COLOR = new Color(240, 217, 181);
    private static final Color DARK_SQUARE_COLOR = new Color(181, 136, 99);
    private static final Color SELECTED_SQUARE_COLOR = new Color(186, 202, 68);

    private int selectedRow;
    private int selectedCol;

    private final Map<PieceType, Image> whitePieceImages = new EnumMap<>(PieceType.class);
    private final Map<PieceType, Image> blackPieceImages = new EnumMap<>(PieceType.class);

    private BoardController boardController;

    public BoardView(BoardController boardController) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private void drawBoard(Graphics g) {
    }

    private void loadPieceImages() {
    }

    private void loadOnePieceImage(PieceType pieceType, PieceColor color, String imagePath) {
    }

    private void drawPieces(Graphics g) {
    }

    private void drawSelectedSquare(Graphics g) {
    }

    private class BoardMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
        }
    }
}
