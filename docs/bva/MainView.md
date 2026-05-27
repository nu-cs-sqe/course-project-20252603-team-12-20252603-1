# BVA Analysis for MainView

Package: `ui.MainView`

**Testing:** Unit tests inject a mock `domain.Board` (EasyMock). `MainView` does **not** construct or initialize `Board`; the caller passes an already-initialized `Board` (e.g. from `WelcomeView` after a domain initializer). **Do not** name tests or TCs by Standard vs Fischer—`MainView` has no mode input; one injected `Board` is enough.

## Method: `MainView(String player1Name, String player2Name, Board board)`

### Step 1: Inputs and outputs

| Input / state | Equivalence classes                                                       |
| ------------- | ------------------------------------------------------------------------- |
| `player1Name` | **Strings**                                                               |
| `player2Name` | **Strings**                                                               |
| `board`       | **Pointers** — mock or real `Board` supplied by caller                    |
| Frame         | newly constructed; no clicks; no moves applied                              |
| Outputs       | collaborators wired; stats labels; board on content pane; controller ready |

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
- **Pointers:** mock `Board` (nice mock, or strict mock with stubbed `getSnapshot()` for 8×8).

### Step 4: Test cases (MV-TC1–MV-TC3)

| ID     | User-story tie-in                                      |
| ------ | ------------------------------------------------------ |
| MV-TC1 | Injected `Board` reachable through wired controller    |
| MV-TC2 | Players shown; board and stats composed on the frame   |
| MV-TC3 | Game ready for first white move (via controller/board) |

**Retired (merged or dropped):** Old rows that split Standard vs Fischer or repeated the same constructor with a mock `Board`—e.g. `Constructor_OnAliceAndBobStandardMode_BoardControllerWired`, separate label/layout/`instanceof` tests, MV-TC4–MV-TC7, snapshot pass-through, duplicate readiness pairs. All of that is covered by MV-TC1–MV-TC3 only.

- **MV-TC1: Constructor_OnAliceAndBob_BoardControllerExposesSnapshot** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: mock `Board` stubbed with 8×8 `getSnapshot()`; frame just constructed
  - **Expected output**: `getBoardController().getBoardSnapshot().length == 8`

- **MV-TC2: Constructor_OnAliceAndBob_WiresStatsBoardAndLayout** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`; mock `Board`; frame constructed
  - **Expected output**: `GameStatsView` and `BoardView` present; current player `"Alice"`; matchup `"Alice vs Bob"`; `GameStatsView` in `BorderLayout.NORTH`, `BoardView` in `CENTER`

- **MV-TC3: Constructor_InitialReadinessFromInjectedBoard** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: mock `Board` returns `WHITE_TURN`; no clicks yet
  - **Expected output**: `getBoardController().getCurrentGameState() == GameState.WHITE_TURN` and `hasSelection() == false`

---
