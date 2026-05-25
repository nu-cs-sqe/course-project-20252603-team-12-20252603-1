# BVA Analysis for MainView

Package: `ui.MainView`

**Testing:** Unit tests inject a mock `domain.Board` (EasyMock). `MainView` does not construct `Board` or initializers—that is the caller’s responsibility (e.g. `WelcomeView` applies `StandardBoardInitializer` or `FischerRandomBoardInitializer` before passing the `Board`). Standard vs Fischer is **not** a `MainView` input; do not duplicate tests by start mode.

## Method: `MainView(String player1Name, String player2Name, Board board)`

### Step 1: Inputs and outputs

| Input / state | Equivalence classes                                                                |
| ------------- | ---------------------------------------------------------------------------------- |
| `player1Name` | **Strings**                                                                        |
| `player2Name` | **Strings**                                                                        |
| `board`       | **Pointers** — mock `Board` in unit tests; real `Board` from caller in integration |
| Frame         | newly constructed; no clicks; no moves applied                                       |
| Outputs       | collaborators wired; stats labels; board displayed in content pane; game ready via controller |

### Step 2: Catalog types

| Variable / output            | Catalog type                                                      |
| ---------------------------- | ----------------------------------------------------------------- |
| `player1Name`, `player2Name` | **Strings**                                                       |
| `board`                      | **Pointers**                                                      |
| Collaborators                | **Pointers**                                                      |
| `boardView` registration     | **Pairs of references**                                           |
| Content pane                 | **Collections** — layout regions for stats and board              |
| Readiness                    | **Cases** (`WHITE_TURN`); **Boolean** (`hasSelection() == false`) |

### Step 3: Concrete boundary values

- **Strings:** `player1Name = "Alice"`, `player2Name = "Bob"`.
- **Pointers:** mock `Board` (nice mock or stubbed 8×8 snapshot); real `Board` from caller after initializer.

### Step 4: Test cases — UI composition (MV-TC1–MV-TC5)

| ID       | User-story tie-in                                  |
| -------- | ---------------------------------------------------- |
| MV-TC1   | Controller wired to injected board                   |
| MV-TC2–3 | Player names shown on stats panel                    |
| MV-TC4–5 | Board on screen and registered with controller       |

> **Removed (redundant):** `BoardView` / `GameStatsView` `instanceof` smokes that differed only by Standard vs Fischer in the name; snapshot pass-through TCs; duplicate Fischer readiness TCs after `GameStartMode` was removed.

- **MV-TC1: Constructor_OnAliceAndBob_BoardControllerExposesSnapshot** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: mock `Board` returns 8×8 snapshot; frame just constructed
  - **Expected output**: `getBoardController().getBoardSnapshot().length == 8`

- **MV-TC2: Constructor_OnAliceAndBob_CurrentPlayerLabelIsAlice** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`
  - **Expected output**: `getGameStatsView().getCurrentPlayerLabelText()` equals `"Alice"`

- **MV-TC3: Constructor_OnAliceAndBob_MatchupLabelIsAliceVsBob** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`
  - **Expected output**: `getGameStatsView().getGameStateLabelText()` equals `"Alice vs Bob"`

- **MV-TC4: Constructor_OnAliceAndBob_ContentPaneLayoutNorthAndCenter** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)` (via `-configureMainView`, `-addGameStatsView`, `-addBoardViewToContentPane`)
  - **State of the system**: frame constructed; `setVisible` not called
  - **Expected output**: `GameStatsView` in `BorderLayout.NORTH`; `BoardView` in `BorderLayout.CENTER`

- **MV-TC5: Constructor_OnAliceAndBob_RegisteredBoardViewSameInstance** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)` (via `-registerBoardViewWithController`)
  - **State of the system**: frame just constructed
  - **Expected output**: `getRegisteredBoardView() == getBoardView()`

---

## Method: `MainView` — game ready for first white move (MV-TC6–MV-TC7)

Readiness is asserted through the wired `BoardController` (not click handling).

- **MV-TC6: Constructor_CurrentGameStateWhiteTurn** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: mock `Board` returns `WHITE_TURN`; no clicks yet
  - **Expected output**: `getBoardController().getCurrentGameState() == GameState.WHITE_TURN`

- **MV-TC7: Constructor_HasSelectionFalse** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: mock `Board`; no clicks yet
  - **Expected output**: `getBoardController().hasSelection() == false`

---
