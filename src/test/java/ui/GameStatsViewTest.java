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
    void Constructor_OnBothNamesEmpty_CurrentPlayerLabelEmpty() {
        GameStatsView view = new GameStatsView("", "");

        String expected = "";
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnBothNamesEmpty_MatchupLabelShowsVersusSeparator() {
        GameStatsView view = new GameStatsView("", "");

        String expected = " vs ";
        String actual = view.getGameStateLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnOneNameEmptyOtherNonEmpty_CurrentPlayerLabelEmpty() {
        GameStatsView view = new GameStatsView("", "Bob");

        String expected = "";
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnOneNameEmptyOtherNonEmpty_MatchupLabelShowsVersusLine() {
        GameStatsView view = new GameStatsView("", "Bob");

        String expected = " vs Bob";
        String actual = view.getGameStateLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnEqualNames_CurrentPlayerLabelShowsName() {
        GameStatsView view = new GameStatsView("Pat", "Pat");

        String expected = "Pat";
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnEqualNames_MatchupLabelShowsVersusLine() {
        GameStatsView view = new GameStatsView("Pat", "Pat");

        String expected = "Pat vs Pat";
        String actual = view.getGameStateLabelText();
        assertEquals(expected, actual);
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

    @Test
    void UpdateCurrentPlayerLabel_OnWhitespaceOnly_LabelShowsWhitespacePolicy() {
        GameStatsView view = new GameStatsView("Alice", "Bob");
        String whitespaceOnly = "   ";
        view.updateCurrentPlayerLabel(whitespaceOnly);

        String expected = whitespaceOnly;
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void UpdateCurrentPlayerLabel_SecondCallOverwritesFirst_LabelShowsLatest() {
        GameStatsView view = new GameStatsView("Pat", "Pat");
        view.updateCurrentPlayerLabel("Alice");
        view.updateCurrentPlayerLabel("Bob");

        String expected = "Bob";
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void UpdateCurrentPlayerLabel_OnLongName_NoExceptionAndLabelUpdated() {
        String longName = "a".repeat(500);
        GameStatsView view = new GameStatsView("Alice", "Bob");
        view.updateCurrentPlayerLabel(longName);

        String expected = longName;
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }
}
