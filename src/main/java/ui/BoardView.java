package ui;

import domain.location.Location;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import java.awt.Color;
import java.awt.Dimension;
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
        this.boardController = boardController;
        addMouseListener(new BoardMouseListener());
        setPreferredSize(new Dimension(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private void drawBoard(Graphics g) {
        // untestable: graphics rendering
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Color color = (row + col) % 2 == 0 ? LIGHT_SQUARE_COLOR : DARK_SQUARE_COLOR;
                g.setColor(color);
                g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
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
            int file = e.getX() / TILE_SIZE;
            int rank = (BOARD_SIZE - 1) - e.getY() / TILE_SIZE;
            boardController.handleSquareClick(new Location(file, rank));
        }
    }
}
