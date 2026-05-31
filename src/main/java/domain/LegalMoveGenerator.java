package domain;

import domain.location.Location;
import domain.move.Move;
import java.util.List;

public interface LegalMoveGenerator {

    List<Move> generateLegalMoves(Location from);
}
