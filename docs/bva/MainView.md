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
| Collaborators                | **Pointers** (`GameStatsView`, `BoardView`, `BoardController`)    |
| Content pane                 | **Collections** — layout regions for stats and board              |
| Readiness                    | **Cases** (`WHITE_TURN`); **Boolean** (`hasSelection() == false`) |

### Step 3: Concrete boundary values

- **Strings:** `player1Name = "Alice"`, `player2Name = "Bob"`.
- **Pointers:** mock `Board` (nice mock or stubbed 8×8 snapshot); real `Board` from caller after initializer.

### Step 4: Test cases

| ID     | User-story tie-in                            |
| ------ | -------------------------------------------- |
| MV-TC1 | Controller exposes injected board snapshot   |
| MV-TC2 | Frame wires stats, board, labels, and layout |
| MV-TC3 | Initial turn and selection state from board  |

> **Removed (redundant):** Per-mode (`Standard` / `FischerRandom`) duplicates; snapshot pass-through; separate `instanceof` / label / layout / registration tests that repeated the same constructor setup; `getRegisteredBoardView()` vs `getBoardView()`.

- **MV-TC1: Constructor_OnAliceAndBob_BoardControllerExposesSnapshot** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: mock `Board` returns 8×8 snapshot; frame just constructed
  - **Expected output**: `getBoardController().getBoardSnapshot().length == 8`

- **MV-TC2: Constructor_OnAliceAndBob_WiresStatsBoardAndLayout** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)` (via `-configureMainView`, `-addGameStatsView`, `-addBoardViewToContentPane`, `-registerBoardViewWithController`)
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`; mock `Board`; frame constructed
  - **Expected output**: `GameStatsView` and `BoardView` present; current player label `"Alice"`; matchup `"Alice vs Bob"`; stats in `BorderLayout.NORTH`, board in `CENTER`

- **MV-TC3: Constructor_InitialReadinessFromInjectedBoard** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: mock `Board` returns `WHITE_TURN`; no clicks yet
  - **Expected output**: `getBoardController().getCurrentGameState() == GameState.WHITE_TURN` and `hasSelection() == false`

---
