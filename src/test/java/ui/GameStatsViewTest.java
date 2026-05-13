package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GameStatsViewTest {

    @Test
    void Constructor_OnBothNamesNonEmpty_CurrentPlayerLabelShowsPlayerOneName() {
        GameStatsView view = new GameStatsView("Alice", "Bob");

        String expected = "Alice";
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }
}
