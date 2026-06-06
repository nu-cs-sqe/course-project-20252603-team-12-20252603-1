package integration;

import domain.Board;
import domain.StandardBoardInitializer;
import domain.gamestate.GameState;
import domain.location.Location;
import domain.move.Move;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameSteps {

    Board board;

    @Given("a new standard chess game is started")
    public void a_new_standard_chess_game_is_started() {
        board = new Board(new StandardBoardInitializer());
    }

    @Then("the {word} king is at {word}")
    public void the_king_is_at(String color, String square) {
        Location loc = parseAlgebraic(square);
        var piece = board.getPieceAt(loc.getY(), loc.getX());
        PieceColor expected = color.equals("white") ? PieceColor.WHITE : PieceColor.BLACK;
        assertEquals(PieceType.KING, piece.getType());
        assertEquals(expected, piece.getColor());
    }

    @When("white moves {word} to {word}")
    public void white_moves_to(String from, String to) {
        board.makeMove(findMove(parseAlgebraic(from), parseAlgebraic(to)));
    }

    @When("black moves {word} to {word}")
    public void black_moves_to(String from, String to) {
        board.makeMove(findMove(parseAlgebraic(from), parseAlgebraic(to)));
    }

    @Then("it is white's turn")
    public void it_is_white_s_turn() {
        assertEquals(GameState.WHITE_TURN, board.getCurrentGameState());
    }

    Location parseAlgebraic(String square) {
        int file = square.charAt(0) - 'a';
        int rank = Character.getNumericValue(square.charAt(1));
        return new Location(file, 8 - rank);
    }

    Move findMove(Location from, Location to) {
        List<Move> moves = board.getLegalMoves(from);
        return moves.stream()
            .filter(m -> m.getTo().getX() == to.getX() && m.getTo().getY() == to.getY())
            .findFirst()
            .orElseThrow(() -> new AssertionError("No legal move from " + from + " to " + to));
    }
}
