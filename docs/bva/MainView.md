# BVA Analysis for MainView

Package: `ui.MainView`

**API contract:** All `String`, `Locale`, and `BoardController` parameters are **non-null**. Callers must not pass `null`. Invalid null input is **out of scope** (unrepresentable boundary — not tested here).

**Testing:** Unit tests inject a `ui.BoardController` built around a mock `domain.Board` (EasyMock). `MainView` does **not** construct or initialize `BoardController`; the caller passes an already-initialized controller (e.g. from `WelcomeController` after creating the board). **Do not** name tests or TCs by Standard vs Fischer—`MainView` has no mode input; one injected controller is enough.

## Method: `MainView(String player1Name, String player2Name, BoardController boardController, Locale locale)`

### Step 1: Inputs and outputs

| Input / state     | Equivalence classes                                                        |
| ----------------- | -------------------------------------------------------------------------- |
| `player1Name`     | **Strings**                                                                |
| `player2Name`     | **Strings**                                                                |
| `boardController` | **Pointers** — mock or real `BoardController` supplied by caller           |
| `locale`          | **Cases** — `Locale.ENGLISH` (app default) or `Locale.forLanguageTag("es")`; loads `appTitle` and `GameStatsView` strings |
| Frame             | newly constructed; no clicks; no moves applied                             |
| Outputs           | collaborators wired; stats labels; board on content pane; controller ready |

### Step 2: Catalog types

| Variable / output            | Catalog type                                                      |
| ---------------------------- | ----------------------------------------------------------------- |
| `player1Name`, `player2Name` | **Strings**                                                       |
| `boardController`            | **Pointers**                                                      |
| `locale`                     | **Cases**                                                         |
| Collaborators                | **Pointers** (`GameStatsView`, `BoardView`, `BoardController`)    |
| Content pane                 | **Collections** — layout regions for stats and board              |
| Readiness                    | **Cases** (`WHITE_TURN`); **Boolean** (`hasSelection() == false`) |

### Step 3: Concrete boundary values

- **Strings:** `player1Name = "Alice"`, `player2Name = "Bob"`.
- **Pointers:** mock `BoardController` wrapping a mock `Board` with stubbed `getSnapshot()` for 8×8.
- **Cases:** `locale = Locale.ENGLISH`.

### Step 4: Test cases (MV-TC1–MV-TC3)

| ID     | User-story tie-in                                      |
| ------ | ------------------------------------------------------ |
| MV-TC1 | Injected `Board` reachable through wired controller    |
| MV-TC2 | Players shown; board and stats composed on the frame   |
| MV-TC3 | Game ready for first white move (via controller/board) |

**Retired (merged or dropped):** Old rows that split Standard vs Fischer or repeated the same constructor with a mock `Board`—e.g. `Constructor_OnAliceAndBobStandardMode_BoardControllerWired`, separate label/layout/`instanceof` tests, MV-TC4–MV-TC7, snapshot pass-through, duplicate readiness pairs. All of that is covered by MV-TC1–MV-TC3 only.

- **MV-TC1: Constructor_OnAliceAndBob_BoardControllerExposesSnapshot** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, BoardController, Locale)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`; mock `BoardController` wrapping a mock `Board` stubbed with 8×8 `getSnapshot()`; `locale = Locale.ENGLISH`; frame just constructed
  - **Expected output**: `getBoardController().getBoardSnapshot().length == 8`

- **MV-TC2: Constructor_OnAliceAndBob_WiresStatsBoardAndLayout** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, BoardController, Locale)`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`; mock `BoardController` wrapping a mock `Board`; `locale = Locale.ENGLISH`; frame constructed
  - **Expected output**: `GameStatsView` and `BoardView` present; current player `"Alice"`; matchup `"Alice versus Bob"`; `GameStatsView` in `BorderLayout.NORTH`, `BoardView` in `CENTER`

- **MV-TC3: Constructor_InitialReadinessFromInjectedBoard** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, BoardController, Locale)`
  - **State of the system**: mock `BoardController` wrapping a mock `Board` returns `WHITE_TURN`; `locale = Locale.ENGLISH`; no clicks yet
  - **Expected output**: `getBoardController().getCurrentGameState() == GameState.WHITE_TURN` and `hasSelection() == false`

### Step 4 (i18n): window title from bundle

- **MV-TC4: Constructor_OnEnglishLocale_WindowTitleFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `MainView(String, String, BoardController, Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`; otherwise same as MV-TC1
  - **Expected output**: frame title is `"Chess"` via `appTitle` key

- **MV-TC5: Constructor_OnSpanishLocale_WindowTitleFromBundle** ( :x: )
  - **Method(s) under test**: `MainView(String, String, BoardController, Locale)`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`; otherwise same as MV-TC1
  - **Expected output**: frame title is `"Ajedrez"` via `appTitle` key in `messages_es.properties`

---
