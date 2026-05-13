package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void Constructor_OnOneNameEmptyOtherNonEmpty_InitialLabelsDefined() {
        GameStatsView view = new GameStatsView("", "Bob");

        boolean expected = true;
        boolean actual =
                view.getCurrentPlayerLabelText().isEmpty()
                        && view.getGameStateLabelText().equals(" vs Bob");
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnEqualNames_InitialLabelsShowSameSpelling() {
        GameStatsView view = new GameStatsView("Pat", "Pat");

        boolean expected = true;
        boolean actual =
                view.getCurrentPlayerLabelText().equals("Pat")
                        && view.getGameStateLabelText().equals("Pat vs Pat");
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnNullPlayerOneName_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GameStatsView(null, "Bob"));
    }

    @Test
    void UpdateCurrentPlayerLabel_OnTypicalName_LabelTextMatches() {
        GameStatsView view = new GameStatsView("Alice", "Bob");
        view.updateCurrentPlayerLabel("Carol");

        String expected = "Carol";
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void UpdateCurrentPlayerLabel_OnEmptyString_LabelShowsEmptyPolicy() {
        GameStatsView view = new GameStatsView("Alice", "Bob");
        view.updateCurrentPlayerLabel("");

        String expected = "";
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }
}
