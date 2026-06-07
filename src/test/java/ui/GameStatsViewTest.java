package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import org.junit.jupiter.api.Test;

class GameStatsViewTest {

    @Test
    void Constructor_OnBothNamesNonEmpty_CurrentPlayerLabelShowsPlayerOneName() {
        GameStatsView view = new GameStatsView("Alice", "Bob", Locale.ENGLISH);

        String expected = "Alice";
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnBothNamesNonEmpty_MatchupLabelShowsVersusLine() {
        GameStatsView view = new GameStatsView("Alice", "Bob", Locale.ENGLISH);

        String expected = "Alice versus Bob";
        String actual = view.getGameStateLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnBothNamesEmpty_CurrentPlayerLabelEmpty() {
        GameStatsView view = new GameStatsView("", "", Locale.ENGLISH);

        String expected = "";
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnBothNamesEmpty_MatchupLabelShowsVersusSeparator() {
        GameStatsView view = new GameStatsView("", "", Locale.ENGLISH);

        String expected = " versus ";
        String actual = view.getGameStateLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnOneNameEmptyOtherNonEmpty_CurrentPlayerLabelEmpty() {
        GameStatsView view = new GameStatsView("", "Bob", Locale.ENGLISH);

        String expected = "";
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnOneNameEmptyOtherNonEmpty_MatchupLabelShowsVersusLine() {
        GameStatsView view = new GameStatsView("", "Bob", Locale.ENGLISH);

        String expected = " versus Bob";
        String actual = view.getGameStateLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnEqualNames_CurrentPlayerLabelShowsName() {
        GameStatsView view = new GameStatsView("Pat", "Pat", Locale.ENGLISH);

        String expected = "Pat";
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void Constructor_OnEqualNames_MatchupLabelShowsVersusLine() {
        GameStatsView view = new GameStatsView("Pat", "Pat", Locale.ENGLISH);

        String expected = "Pat versus Pat";
        String actual = view.getGameStateLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void UpdateCurrentPlayerLabel_OnTypicalName_LabelTextMatches() {
        GameStatsView view = new GameStatsView("Alice", "Bob", Locale.ENGLISH);
        view.updateCurrentPlayerLabel("Carol");

        String expected = "Carol";
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void UpdateCurrentPlayerLabel_OnEmptyString_LabelShowsEmptyPolicy() {
        GameStatsView view = new GameStatsView("Alice", "Bob", Locale.ENGLISH);
        view.updateCurrentPlayerLabel("");

        String expected = "";
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void UpdateCurrentPlayerLabel_OnWhitespaceOnly_LabelShowsWhitespacePolicy() {
        GameStatsView view = new GameStatsView("Alice", "Bob", Locale.ENGLISH);
        String whitespaceOnly = "   ";
        view.updateCurrentPlayerLabel(whitespaceOnly);

        String expected = whitespaceOnly;
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void UpdateCurrentPlayerLabel_SecondCallOverwritesFirst_LabelShowsLatest() {
        GameStatsView view = new GameStatsView("Pat", "Pat", Locale.ENGLISH);
        view.updateCurrentPlayerLabel("Alice");
        view.updateCurrentPlayerLabel("Bob");

        String expected = "Bob";
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }

    @Test
    void UpdateCurrentPlayerLabel_OnLongName_NoExceptionAndLabelUpdated() {
        String longName = "a".repeat(500);
        GameStatsView view = new GameStatsView("Alice", "Bob", Locale.ENGLISH);
        view.updateCurrentPlayerLabel(longName);

        String expected = longName;
        String actual = view.getCurrentPlayerLabelText();
        assertEquals(expected, actual);
    }
}
