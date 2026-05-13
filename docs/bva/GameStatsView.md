# BVA Analysis for GameStatsView

Scope: **Game Initialization** (constructor through `updateCurrentPlayerLabel`).

## Method / behavior: `GameStatsView(String player1Name, String player2Name)`

### Step 1: Input and output equivalence classes

| Input / concern                          | Equivalence classes                                                                                                                                                     |
| ---------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `player1Name`                            | `null` (if allowed); empty `""`; whitespace-only; short ASCII; long string; non-ASCII (e.g. accented or CJK)                                                            |
| `player2Name`                            | same dimensions as `player1Name`                                                                                                                                        |
| **Pairs** (`player1Name`, `player2Name`) | both empty; one empty / one non-empty; both non-empty and equal; both non-empty and different                                                                           |
| Initial labels                           | Constructor must leave the panel in a **consistent** initial state (whatever the design promises: e.g. placeholder text, first player shown, or names copied to labels) |

### Step 2: BVA catalog data types

| Variable / output             | Catalog type           | Notes                                                                   |
| ----------------------------- | ---------------------- | ----------------------------------------------------------------------- |
| Each name parameter           | **Strings**            | empty; same length differ in last char; one shorter prefix of the other |
| Pair of names                 | **Pairs of variables** | smallest/largest length pairs if you cap length                         |
| Label text after construction | **String** / **Cases** | depends on agreed initial UI copy                                       |
| Swing tree                    | **IMPLEMENTATION**     | focus tests on **public text outcomes**, not pixel layout               |

### Step 3: Concrete boundary values (catalog-aligned)

- Strings: `""`; `" "` or `"\t"`; `"A"`; `"Alice"`; a long repeated `'a'` (pick a max that matters for truncation if any); a string with combining characters or outside BMP if you support them.
- Pairs: `("", "")`; `("", "Bob")`; `("Alice", "")`; `("Same", "Same")`; `("Alice", "Bob")`.

### Step 4: Test cases (each-choice; avoid combinatorial explosion)

- **GS-TC1: Constructor_OnBothNamesNonEmpty_CurrentPlayerLabelShowsPlayerOneName** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: `player1Name` and `player2Name` are short distinct non-empty strings
  - **Expected output**: `currentPlayerLabel` shows player 1’s name (whose turn it is at game start) — **one assertion**

- **GS-TC2: Constructor_OnBothNamesNonEmpty_MatchupLabelShowsVersusLine** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: same as GS-TC1
  - **Expected output**: `gameStateLabel` shows both names in a fixed `"<p1> vs <p2>"` matchup line — **one assertion**

- **GS-TC3: Constructor_OnBothNamesEmpty_InitialLabelsDefined** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: both names are `""`
  - **Expected output**: no uncaught exception; both labels show empty text (no `" vs "` fragment when both names are empty)

- **GS-TC4: Constructor_OnOneNameEmptyOtherNonEmpty_InitialLabelsDefined** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: `player1Name == ""`, `player2Name == "Bob"` (and optionally the swapped pair in a **separate** test if not redundant)
  - **Expected output**: current-player label is empty; matchup label is `" vs Bob"` (same `<p1> vs <p2>` rule as GS-TC2, so the non-empty name is still visible)

- **GS-TC5: Constructor_OnEqualNames_InitialLabelsShowSameSpelling** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: both names are the same non-empty string
  - **Expected output**: current-player label is that string; matchup label is `"<name> vs <name>"` (two occurrences, no accidental interning or single-reference bug)

- **GS-TC6: Constructor_OnNullNamePolicy_DefensiveOrDocumented** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: `player1Name` is `null` (representative; both parameters are non-null in the public API contract)
  - **Expected output**: `NullPointerException` from `Objects.requireNonNull` before any label is shown with invalid state

---

## Method: `updateCurrentPlayerLabel(String playerName)`

### Step 1: Input and output equivalence classes

| Input            | Classes                                                                                            |
| ---------------- | -------------------------------------------------------------------------------------------------- |
| `playerName`     | `null` (policy); `""`; whitespace-only; normal name; very long; unicode                            |
| Prior label text | empty vs non-empty before update (**changing the contents of a collection** / overwrite semantics) |

### Step 2: BVA catalog data types

| Concern          | Catalog type                                                                                  |
| ---------------- | --------------------------------------------------------------------------------------------- |
| `playerName`     | **Pointers** (`null` vs reference); **Strings** (empty, compare-last-char difference)         |
| Repeated updates | **Overwriting the previous contents** (new string shorter, same length, longer than previous) |
| `JLabel` display | **Streaming / fixed UI string** — treat displayed text as the output per call                 |

### Step 3: Concrete boundary values

- `null`: `Objects.requireNonNull` on the argument (fail fast).
- `""` vs non-empty overwrite; whitespace-only string verbatim.
- Two calls: second value replaces the first (`"Alice"` then `"Bob"`).
- Long string (e.g. 500 ASCII chars): full text shown unless a later story adds truncation.

### Step 4: Test cases

- **GS-TC7: UpdateCurrentPlayerLabel_OnTypicalName_LabelTextMatches** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: constructed view; argument is a normal non-empty name
  - **Expected output**: `currentPlayerLabel` shows exactly that string (no prefix/suffix; `Objects.requireNonNull` on the argument)

- **GS-TC8: UpdateCurrentPlayerLabel_OnEmptyString_LabelShowsEmptyPolicy** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: label previously showed a non-empty name (e.g. `"Alice"` from construction); argument is `""`
  - **Expected output**: `currentPlayerLabel` text is empty (cleared; same contract as `setText` with `""`)

- **GS-TC9: UpdateCurrentPlayerLabel_OnWhitespaceOnly_LabelShowsWhitespacePolicy** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: argument is `"   "` (tabs/spaces only)
  - **Expected output**: `currentPlayerLabel` text equals the argument verbatim (no trim; same contract as `setText`)

- **GS-TC10: UpdateCurrentPlayerLabel_SecondCallOverwritesFirst_LabelShowsLatest** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)` twice
  - **State of the system**: first `"Alice"`, then `"Bob"`
  - **Expected output**: final visible text corresponds to `"Bob"` under the same formatting rules as GS-TC7

- **GS-TC11: UpdateCurrentPlayerLabel_OnLongName_NoExceptionAndLabelUpdated** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: argument is a long but finite string (e.g. 500 ASCII chars)
  - **Expected output**: no exception; `currentPlayerLabel` text equals the full argument (no truncation in this implementation)
