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

    @Test
    void Constructor_OnBothNamesNonEmpty_MatchupLabelShowsVersusLine() {
        GameStatsView view = new GameStatsView("Alice", "Bob");

        String expected = "Alice vs Bob";
        String actual = view.getGameStateLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnBothNamesEmpty_InitialLabelsDefined() {
        GameStatsView view = new GameStatsView("", "");

        boolean expected = true;
        boolean actual =
                view.getCurrentPlayerLabelText().isEmpty()
                        && view.getGameStateLabelText().isEmpty();
        assertEquals(expected, actual);
    }
}
