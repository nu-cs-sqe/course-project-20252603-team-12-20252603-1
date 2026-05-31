package domain;

import domain.location.Location;
import domain.move.Move;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import domain.piece.Piece;
import domain.piece.PieceType;

public class MoveGenerator {

    private final Piece[][] board;
    private final Optional<Location> enPassantTarget;

    public MoveGenerator(Piece[][] board, Optional<Location> enPassantTarget) {
        this.board = board;
        this.enPassantTarget = enPassantTarget;
    }

    public List<Move> generateLegalMoves(Location from) {
        Piece piece = board[from.getY()][from.getX()];
        if (piece.getType() == PieceType.NONE) {
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }
}
