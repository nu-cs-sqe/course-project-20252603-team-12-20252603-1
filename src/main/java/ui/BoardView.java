package ui;

import domain.location.Location;
import domain.move.Move;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

public class BoardView extends JPanel {

    private static final int BOARD_SIZE = 8;
    private static final int TILE_SIZE = 75;
    private static final Color LIGHT_SQUARE_COLOR = new Color(240, 217, 181);
    private static final Color DARK_SQUARE_COLOR = new Color(181, 136, 99);
    private static final Color SELECTED_SQUARE_COLOR = new Color(186, 202, 68);
    private static final Color LEGAL_MOVE_DOT_COLOR = new Color(0, 0, 0, 100);
    private static final Color LEGAL_CAPTURE_COLOR = new Color(186, 202, 68, 160);
    private static final float LEGAL_MOVE_ALPHA = 0.85f;

    private final Map<PieceType, Image> whitePieceImages = new EnumMap<>(PieceType.class);
    private final Map<PieceType, Image> blackPieceImages = new EnumMap<>(PieceType.class);

    private BoardController boardController;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Intentional shared reference for collaboration")
    public BoardView(BoardController boardController) {
        this.boardController = boardController;
        addMouseListener(new BoardMouseListener());
        setPreferredSize(new Dimension(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE));
        loadPieceImages();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // untestable: graphics rendering
        super.paintComponent(g);
        drawBoard(g);
        drawSelectedSquare(g);
        drawLegalMoveHighlights(g);
        drawPieces(g);
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

    private void drawSelectedSquare(Graphics g) {
        // untestable: graphics rendering
        java.util.Optional<Location> sel = boardController.getSelectedLocation();
        if (!sel.isPresent()) {
            return;
        }
        Location loc = sel.get();
        int col = loc.getX();
        int row = loc.getY();
        g.setColor(SELECTED_SQUARE_COLOR);
        g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    private void drawLegalMoveHighlights(Graphics g) {
        // untestable: graphics rendering
        List<Move> moves = boardController.getLegalMovesForSelection();
        if (moves.isEmpty()) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Composite originalComposite = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, LEGAL_MOVE_ALPHA));

        domain.piece.Piece[][] snapshot = boardController.getBoardSnapshot();
        for (Move move : moves) {
            int file = move.getTo().getX();
            int screenRow = move.getTo().getY();
            boolean isCapture =
                    snapshot[move.getTo().getY()][move.getTo().getX()].getType() != PieceType.NONE;
            if (isCapture) {
                g2d.setColor(LEGAL_CAPTURE_COLOR);
                g2d.fillRect(file * TILE_SIZE, screenRow * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            } else {
                int dotSize = TILE_SIZE / 3;
                int offset = (TILE_SIZE - dotSize) / 2;
                g2d.setColor(LEGAL_MOVE_DOT_COLOR);
                g2d.fillOval(
                        file * TILE_SIZE + offset,
                        screenRow * TILE_SIZE + offset,
                        dotSize, dotSize);
            }
        }
        g2d.setComposite(originalComposite);
    }

    private void loadPieceImages() {
        // untestable: I/O / resource loading
        PieceType[] types = {PieceType.PAWN, PieceType.ROOK, PieceType.KNIGHT,
                             PieceType.BISHOP, PieceType.QUEEN, PieceType.KING};
        for (PieceType type : types) {
            loadOnePieceImage(type, PieceColor.WHITE,
                    "pieces/white_" + type.name().toLowerCase() + ".png");
            loadOnePieceImage(type, PieceColor.BLACK,
                    "pieces/black_" + type.name().toLowerCase() + ".png");
        }
    }

    private void loadOnePieceImage(PieceType pieceType, PieceColor color, String imagePath) {
        // untestable: I/O / resource loading
        java.net.URL resource = getClass().getClassLoader().getResource(imagePath);
        Image image = new javax.swing.ImageIcon(resource).getImage();
        if (color == PieceColor.WHITE) {
            whitePieceImages.put(pieceType, image);
        } else {
            blackPieceImages.put(pieceType, image);
        }
    }

    private void drawPieces(Graphics g) {
        // untestable: graphics rendering
        domain.piece.Piece[][] snapshot = boardController.getBoardSnapshot();
        for (int rank = 0; rank < BOARD_SIZE; rank++) {
            for (int file = 0; file < BOARD_SIZE; file++) {
                domain.piece.Piece piece = snapshot[rank][file];
                if (piece.getType() == PieceType.NONE) {
                    continue;
                }
                Map<PieceType, Image> images =
                        piece.getColor() == PieceColor.WHITE ? whitePieceImages : blackPieceImages;
                Image img = images.get(piece.getType());
                if (img == null) {
                    throw new IllegalStateException("No image loaded for piece: " + piece.getType());
                }
                int screenRow = rank;
                g.drawImage(img, file * TILE_SIZE, screenRow * TILE_SIZE,
                        TILE_SIZE, TILE_SIZE, this);
            }
        }
    }

    private class BoardMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int file = e.getX() / TILE_SIZE;
            int rank = e.getY() / TILE_SIZE;
            boardController.handleSquareClick(new Location(file, rank));
        }
    }
}
