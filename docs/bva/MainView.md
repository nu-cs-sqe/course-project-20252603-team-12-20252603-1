# BVA Analysis for MainView

Package: `ui.MainView`

**Testing:** Unit tests inject a mock `domain.Board` (EasyMock). `MainView` does not construct `Board` or initializers—that is the caller’s responsibility (e.g. `WelcomeView` after PR #74).

## Method: `MainView(String player1Name, String player2Name, GameStartMode mode, Board board)`

### Step 1: Inputs and outputs

| Input / state | Equivalence classes                                                                           |
| ------------- | --------------------------------------------------------------------------------------------- |
| `player1Name` | **Strings**                                                                                   |
| `player2Name` | **Strings**                                                                                   |
| `mode`        | **Cases** — `STANDARD`; `FISCHER_RANDOM`                                                      |
| `board`       | **Pointers** — mock `Board` in unit tests; real `Board` from caller in integration            |
| Frame         | newly constructed; no clicks; no moves applied                                                |
| Outputs       | collaborators wired; stats labels; board displayed in content pane; game ready via controller |

### Step 2: Catalog types

| Variable / output            | Catalog type                                                      |
| ---------------------------- | ----------------------------------------------------------------- |
| `mode`                       | **Cases**                                                         |
| `player1Name`, `player2Name` | **Strings**                                                       |
| Collaborators                | **Pointers**                                                      |
| `boardView` registration     | **Pairs of references**                                           |
| Content pane                 | **Collections** — size **> 1**                                    |
| Readiness                    | **Cases** (`WHITE_TURN`); **Boolean** (`hasSelection() == false`) |

### Step 3: Concrete boundary values

- **Cases:** `STANDARD`; `FISCHER_RANDOM`.
- **Strings:** `player1Name = "Alice"`, `player2Name = "Bob"`.
- **Pointers (Fischer):** `chess960Random = new Random(1L)` in four-arg constructor for deterministic smoke.

### Step 4: Test cases — UI composition (MV-TC1–MV-TC7)

| ID       | User-story tie-in                                      |
| -------- | ------------------------------------------------------ |
| MV-TC1–3 | System displays initialized game (collaborators exist) |
| MV-TC4–5 | Players shown; white / player 1 indicated at start     |
| MV-TC6–7 | Board displayed and connected to controller            |

- **MV-TC1: Constructor_OnAliceAndBobStandardMode_BoardControllerWired** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, GameStartMode, Board)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, `mode = STANDARD`, mock `Board` returns 8×8 snapshot, frame just constructed
  - **Expected output**: `getBoardController().getBoardSnapshot().length == 8`

- **MV-TC2: Constructor_OnAliceAndBobFischerRandomMode_BoardViewWired** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, GameStartMode, Board)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, `mode = FISCHER_RANDOM`, frame just constructed
  - **Expected output**: `getBoardView()` is a `BoardView` instance

- **MV-TC3: Constructor_OnAliceAndBobStandardMode_GameStatsViewWired** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, GameStartMode, Board)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, `mode = STANDARD`, mock `Board`, frame just constructed
  - **Expected output**: `getGameStatsView()` is a `GameStatsView` instance

- **MV-TC4: Constructor_OnAliceAndBobStandardMode_CurrentPlayerLabelIsAlice** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, GameStartMode, Board)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, `mode = STANDARD`, frame just constructed
  - **Expected output**: `getGameStatsView().getCurrentPlayerLabelText()` equals `"Alice"`

- **MV-TC5: Constructor_OnAliceAndBobFischerRandomMode_MatchupLabelIsAliceVsBob** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, GameStartMode, Board)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, `mode = FISCHER_RANDOM`, mock `Board`, frame just constructed
  - **Expected output**: `getGameStatsView().getGameStateLabelText()` equals `"Alice vs Bob"`

- **MV-TC6: Constructor_OnAliceAndBobStandardMode_ContentPaneHasBoardViewAndGameStatsView** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, GameStartMode, Board)` (via `-configureMainView`, `-addGameStatsView`, `-addBoardView`)
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, `mode = STANDARD`, unit test does not call `setVisible(true)`
  - **Expected output**: content pane contains at least one `BoardView` and at least one `GameStatsView`

- **MV-TC7: Constructor_OnAliceAndBobFischerRandomMode_RegisteredBoardViewSameInstance** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, GameStartMode, Board)` (via `-addBoardView` → `setBoardView`)
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, `mode = FISCHER_RANDOM`, frame just constructed
  - **Expected output**: `getRegisteredBoardView() == getBoardView()`

---

## Method: `MainView` — mode selects initializer (integration smokes)

### Step 4: Test cases (MV-TC8–MV-TC9)

One smoke per use case: `MainView` chose the correct initializer; full placement rules stay in domain BVA.

- **MV-TC8: Constructor_StandardMode_SnapshotMatchesStandardInitializer** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, GameStartMode, Board)` with `mode = STANDARD`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, `mode = STANDARD`, no clicks yet
  - **Expected output**: `getBoardController().getBoardSnapshot()` is cell-wise equal to the test-built standard starting grid (type and color per cell) on the injected mock `Board`

- **MV-TC9: Constructor_FischerRandomMode_SnapshotMatchesFischerRandomInitializer** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, GameStartMode, Board)` with `mode = FISCHER_RANDOM` (future four-arg `Random` when caller supplies board from seeded initializer)
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, seed `1L`, no clicks yet
  - **Expected output**: `getBoardController().getBoardSnapshot()` is cell-wise equal to the test-built Chess960 seed-`1L` grid on the injected mock `Board`

---

## Method: `MainView` — game ready for first white move (MV-TC10–MV-TC13)

Readiness is part of the user story; asserted through the wired `BoardController` (not click handling).

- **MV-TC10: Constructor_StandardMode_CurrentGameStateWhiteTurn** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, GameStartMode, Board)` with `mode = STANDARD`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, `mode = STANDARD`, no clicks yet
  - **Expected output**: `getBoardController().getCurrentGameState() == GameState.WHITE_TURN`

- **MV-TC11: Constructor_StandardMode_HasSelectionFalse** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, GameStartMode, Board)` with `mode = STANDARD`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, `mode = STANDARD`, no clicks yet
  - **Expected output**: `getBoardController().hasSelection() == false`

- **MV-TC12: Constructor_FischerRandomMode_CurrentGameStateWhiteTurn** ( :x: )
  - **Method(s) under test**: `MainView(String, String, GameStartMode, Board)` with `mode = FISCHER_RANDOM` (future four-arg `Random` when caller supplies board from seeded initializer)
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, seed `1L`, no clicks yet
  - **Expected output**: `getBoardController().getCurrentGameState() == GameState.WHITE_TURN`

- **MV-TC13: Constructor_FischerRandomMode_HasSelectionFalse** ( :x: )
  - **Method(s) under test**: `MainView(String, String, GameStartMode, Board)` with `mode = FISCHER_RANDOM` (future four-arg `Random` when caller supplies board from seeded initializer)
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, seed `1L`, no clicks yet
  - **Expected output**: `getBoardController().hasSelection() == false`

---
