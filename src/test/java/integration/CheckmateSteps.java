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
        GameState actual = gameSteps.board.getCurrentGameState();
        boolean gameOver = actual == GameState.WHITE_WIN
                || actual == GameState.BLACK_WIN
                || actual == GameState.DRAW;
        assertTrue(gameOver);
    }

    @Then("black wins")
    public void black_wins() {
        GameState expected = GameState.BLACK_WIN;
        GameState actual = gameSteps.board.getCurrentGameState();
        assertEquals(expected, actual);
    }

    @Then("white wins")
    public void white_wins() {
        GameState expected = GameState.WHITE_WIN;
        GameState actual = gameSteps.board.getCurrentGameState();
        assertEquals(expected, actual);
    }
}
