# BVA Analysis for GameStatsView

Package: `ui.GameStatsView`

Scope: **Game Initialization** and **Move Piece current-player display**. `GameStatsView` is a view, so this BVA intentionally stays small: it checks visible label text, not board rules. `GameStatsView` does not own board state, legal-move validation, selection, move count, check detection, or piece movement. Its responsibility in the move story is to display the player whose turn is active after the controller/domain layer decides whether a move succeeded.

**API contract:** All `String` parameters are **non-null**. Callers must not pass `null`. Invalid `null` input is out of scope and is not a BVA boundary for this class.

## Move Story Analysis

For the move-piece story, the main `GameStatsView` action is updating the current-player label from the active `GameState`: after `BoardController` successfully completes a legal move and switches `WHITE_TURN` to `BLACK_TURN` or `BLACK_TURN` to `WHITE_TURN`, the UI layer should ask the stats view to show the matching player name.

Selection-only clicks, rejected illegal moves, opponent-piece clicks, empty-square clicks, and out-of-bounds clicks should not change the current-player label because no successful move occurred and the turn did not switch. Those behaviors are mainly `BoardController` / `MainView` integration responsibilities; this BVA documents the expected `GameStatsView` label result for that story boundary.

## Method / behavior: `GameStatsView(String player1Name, String player2Name)`

### Step 1: Identify inputs and outputs

| Input / output | Equivalence classes |
| --- | --- |
| `player1Name` | empty `""`; whitespace-only; short non-empty; long string |
| `player2Name` | empty `""`; whitespace-only; short non-empty; long string |
| Pair of names | both empty; one empty and one non-empty; equal non-empty names; distinct non-empty names |
| Initial current-player label | shows `player1Name`, because a new game starts with white / player one active |
| Matchup label | shows `"<player1Name> vs <player2Name>"`; separator remains even when one or both names are empty |

### Step 2: Identify data types

| Variable / output | Catalog type | Notes |
| --- | --- | --- |
| `player1Name`, `player2Name` | **Strings** | Empty, whitespace-only, short, long |
| Pair of names | **Pairs of variables** | The two names combine to form the matchup label |
| Initial current-player label | **Cases** | New standard and Chess960 games both begin with player one / white active |
| Matchup label text | **Strings** | Concatenation of both names with `" vs "` |

### Step 3: Choose boundary values

- Strings: `""`; `"   "`; `"A"`; `"Alice"`; `"a".repeat(500)`.
- Name pairs: `("", "")`; `("", "Bob")`; `("Pat", "Pat")`; `("Alice", "Bob")`.
- Game start cases: standard start and Chess960 start use the same labels; no separate view test is needed for setup mode.
- `null` is not a valid input under the class contract and should not be tested.

### Step 4: Test cases

- **GS-TC1: Constructor_OnBothNamesNonEmpty_CurrentPlayerLabelShowsPlayerOneName** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: standard new game; `player1Name == "Alice"` and `player2Name == "Bob"`
  - **Expected output**: current-player label text is `"Alice"`

- **GS-TC2: Constructor_OnBothNamesNonEmpty_MatchupLabelShowsVersusLine** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: standard new game; `player1Name == "Alice"` and `player2Name == "Bob"`
  - **Expected output**: matchup label text is `"Alice vs Bob"`

- **GS-TC3: Constructor_OnBothNamesEmpty_CurrentPlayerLabelEmpty** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: standard new game; both names are `""`
  - **Expected output**: current-player label text is `""`

- **GS-TC4: Constructor_OnBothNamesEmpty_MatchupLabelShowsVersusSeparator** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: standard new game; both names are `""`
  - **Expected output**: matchup label text is `" vs "`

- **GS-TC5: Constructor_OnOneNameEmptyOtherNonEmpty_CurrentPlayerLabelEmpty** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: standard new game; `player1Name == ""` and `player2Name == "Bob"`
  - **Expected output**: current-player label text is `""`

- **GS-TC6: Constructor_OnOneNameEmptyOtherNonEmpty_MatchupLabelShowsVersusLine** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: standard new game; `player1Name == ""` and `player2Name == "Bob"`
  - **Expected output**: matchup label text is `" vs Bob"`

- **GS-TC7: Constructor_OnEqualNames_CurrentPlayerLabelShowsName** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: standard new game; both names are `"Pat"`
  - **Expected output**: current-player label text is `"Pat"`

- **GS-TC8: Constructor_OnEqualNames_MatchupLabelShowsVersusLine** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: standard new game; both names are `"Pat"`
  - **Expected output**: matchup label text is `"Pat vs Pat"`

---

## Method: `updateCurrentPlayerLabel(String playerName)`

### Step 1: Identify inputs and outputs

| Input / state | Equivalence classes |
| --- | --- |
| `playerName` | empty `""`; whitespace-only; normal player name; long player name |
| `currentGameState` | `WHITE_TURN`; `BLACK_TURN` |
| Previous label text | same value as new label; different value from new label; updated more than once |
| Game event that causes update | successful legal move |
| Game event that should not cause update | click that does not complete a move: selection only, rejected move, opponent or empty square first click, out-of-bounds click |
| Setup mode | standard start; Chess960 start |
| Output | current-player label shows exactly the latest supplied active-player name |

### Step 2: Identify data types

| Variable / output | Catalog type | Notes |
| --- | --- | --- |
| `playerName` | **Strings** | Empty, whitespace-only, short, long |
| `currentGameState` | **Cases** | White turn vs black turn |
| Prior/current label text | **States** | Before update vs after update |
| Turn transition | **Cases** | Turn changed, turn unchanged |
| Move result | **Cases** | Successful move, no move completed |
| Setup mode | **Cases** | Standard and Chess960 use the same label update rule |

### Step 3: Choose boundary values

- Names: `"Alice"`; `"Bob"`; `""`; `"   "`; `"a".repeat(500)`.
- Current game state: `WHITE_TURN`; `BLACK_TURN`.
- Turn-display boundaries: successful move changes the label; no completed move leaves the label unchanged.
- Move-result boundaries: legal move; rejected move; selection-only click.
- Setup boundaries: standard opening position and valid Chess960 opening position.
- `null` is not a valid input under the class contract and should not be tested.

### Step 4: Test cases

- **GS-TC9: UpdateCurrentPlayerLabel_OnTypicalName_LabelTextMatches** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: constructed view currently shows `"Alice"`; update argument is `"Carol"`
  - **Expected output**: current-player label text is `"Carol"`

- **GS-TC10: UpdateCurrentPlayerLabel_OnEmptyString_LabelShowsEmptyPolicy** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: constructed view currently shows a non-empty name; update argument is `""`
  - **Expected output**: current-player label text is `""`

- **GS-TC11: UpdateCurrentPlayerLabel_OnWhitespaceOnly_LabelShowsWhitespacePolicy** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: constructed view; update argument is `"   "`
  - **Expected output**: current-player label text is exactly `"   "`

- **GS-TC12: UpdateCurrentPlayerLabel_SecondCallOverwritesFirst_LabelShowsLatest** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)` twice
  - **State of the system**: first update argument is `"Alice"`; second update argument is `"Bob"`
  - **Expected output**: current-player label text is `"Bob"`

- **GS-TC13: UpdateCurrentPlayerLabel_OnLongName_NoExceptionAndLabelUpdated** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: constructed view; update argument is 500 ASCII `'a'` characters
  - **Expected output**: current-player label text equals the full long name

- **GS-TC14: UpdateCurrentPlayerLabel_AfterSuccessfulMove_LabelShowsOpponentPlayerName** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(GameState)`
  - **State of the system**: current label shows active player `"Alice"`; a legal move succeeds and the controller/domain layer switches the turn to `BLACK_TURN`
  - **Expected output**: current-player label text is `"Bob"`

- **GS-TC15: UpdateCurrentPlayerLabel_OnWhiteTurn_LabelShowsPlayerOneName** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(GameState)`
  - **State of the system**: current game state is `WHITE_TURN`
  - **Expected output**: current-player label text is player one name `"Alice"`

- **GS-TC16: UpdateCurrentPlayerLabel_AfterChess960LegalMove_UsesSameOpponentNameRule** ( implemented in GS-TC14 )
  - **Method(s) under test**: `updateCurrentPlayerLabel(GameState)`
  - **State of the system**: valid Chess960 game; current label shows active player `"Alice"`; a legal move succeeds and the turn switches
  - **Expected output**: current-player label text is `"Bob"`; Chess960 setup does not change the view update rule, and setup mode is not a `GameStatsView` input
