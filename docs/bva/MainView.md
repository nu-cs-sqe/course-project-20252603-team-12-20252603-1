# BVA Analysis for MainView

Package: `ui.MainView`

**Testing:** Unit tests inject a mock `domain.Board` (EasyMock). `MainView` does not construct `Board` or initializers—that is the caller’s responsibility (e.g. `WelcomeView` applies `StandardBoardInitializer` or `FischerRandomBoardInitializer` before passing the `Board`).

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
| Content pane                 | **Collections** — size **> 1**                                    |
| Readiness                    | **Cases** (`WHITE_TURN`); **Boolean** (`hasSelection() == false`) |

### Step 3: Concrete boundary values

- **Strings:** `player1Name = "Alice"`, `player2Name = "Bob"`.
- **Pointers:** mock `Board` (nice mock or stubbed snapshot); real `Board` from caller after initializer.

### Step 4: Test cases — UI composition (MV-TC1–MV-TC7)

| ID       | User-story tie-in                                      |
| -------- | ------------------------------------------------------ |
| MV-TC1–3 | System displays initialized game (collaborators exist) |
| MV-TC4–5 | Players shown; white / player 1 indicated at start     |
| MV-TC6–7 | Board displayed and connected to controller            |

- **MV-TC1: Constructor_OnAliceAndBobStandardMode_BoardControllerWired** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, mock `Board` returns 8×8 snapshot, frame just constructed
  - **Expected output**: `getBoardController().getBoardSnapshot().length == 8`

- **MV-TC2: Constructor_OnAliceAndBobFischerRandomMode_BoardViewWired** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, mock `Board`, frame just constructed
  - **Expected output**: `getBoardView()` is a `BoardView` instance

- **MV-TC3: Constructor_OnAliceAndBobStandardMode_GameStatsViewWired** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, mock `Board`, frame just constructed
  - **Expected output**: `getGameStatsView()` is a `GameStatsView` instance

- **MV-TC4: Constructor_OnAliceAndBobStandardMode_CurrentPlayerLabelIsAlice** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, frame just constructed
  - **Expected output**: `getGameStatsView().getCurrentPlayerLabelText()` equals `"Alice"`

- **MV-TC5: Constructor_OnAliceAndBobFischerRandomMode_MatchupLabelIsAliceVsBob** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, mock `Board`, frame just constructed
  - **Expected output**: `getGameStatsView().getGameStateLabelText()` equals `"Alice vs Bob"`

- **MV-TC6: Constructor_OnAliceAndBobStandardMode_ContentPaneHasBoardViewAndGameStatsView** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)` (via `-configureMainView`, `-addGameStatsView`, `-addBoardViewToContentPane`)
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, unit test does not call `setVisible(true)`
  - **Expected output**: `GameStatsView` in content pane `BorderLayout.NORTH`; `BoardView` in `BorderLayout.CENTER` (both visible when frame is shown)

- **MV-TC7: Constructor_OnAliceAndBobFischerRandomMode_RegisteredBoardViewSameInstance** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)` (via `-registerBoardViewWithController`)
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, frame just constructed
  - **Expected output**: `getRegisteredBoardView() == getBoardView()`

---

## Method: `MainView` — game ready for first white move (MV-TC8–MV-TC9)

> MV-TC8/TC9 (snapshot pass-through) removed: redundant with MV-TC1 and did not exercise initializers.
>
> MV-TC10/TC11 (Fischer-labeled readiness) removed: duplicate of MV-TC8/TC9 after `GameStartMode` was dropped from the constructor.

Readiness is part of the user story; asserted through the wired `BoardController` (not click handling). Board layout mode is not a `MainView` input.

- **MV-TC8: Constructor_CurrentGameStateWhiteTurn** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, mock `Board` returns `WHITE_TURN`, no clicks yet
  - **Expected output**: `getBoardController().getCurrentGameState() == GameState.WHITE_TURN`

- **MV-TC9: Constructor_HasSelectionFalse** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, Board)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`, mock `Board`, no clicks yet
  - **Expected output**: `getBoardController().hasSelection() == false`

---
