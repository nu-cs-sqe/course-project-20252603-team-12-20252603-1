package integration;

import domain.gamestate.GameState;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckmateSteps {

    private final GameSteps gameSteps;

    public CheckmateSteps(GameSteps gameSteps) {
        this.gameSteps = gameSteps;
    }

    @When("the following moves are made")
    public void the_following_moves_are_made(DataTable dataTable) {
        dataTable.asMaps().forEach(row -> {
            var from = gameSteps.parseAlgebraic(row.get("from"));
            var to = gameSteps.parseAlgebraic(row.get("to"));
            gameSteps.board.makeMove(gameSteps.findMove(from, to));
        });
    }

    @Then("the game is over")
    public void the_game_is_over() {
        GameState state = gameSteps.board.getCurrentGameState();
        assertTrue(state == GameState.WHITE_WIN
                || state == GameState.BLACK_WIN
                || state == GameState.DRAW);
    }

    @Then("black wins")
    public void black_wins() {
        assertEquals(GameState.BLACK_WIN, gameSteps.board.getCurrentGameState());
    }
}
