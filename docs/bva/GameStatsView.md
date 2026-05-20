# BVA Analysis for GameStatsView

Scope: **Game Initialization** (constructor through `updateCurrentPlayerLabel`).

**API contract:** All `String` parameters are **non-null**. Callers (e.g. game setup / controller code) must not pass `null`. `GameStatsView` does not validate null; invalid null input is **out of scope** (unrepresentable boundary — not tested here).

## Method / behavior: `GameStatsView(String player1Name, String player2Name)`

### Step 1: Input and output equivalence classes

| Input / concern                          | Equivalence classes                                                                                                                                                     |
| ---------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `player1Name`                            | empty `""`; whitespace-only; short ASCII; long string; non-ASCII (e.g. accented or CJK). **`null` — unrepresentable** (caller contract)                                |
| `player2Name`                            | same dimensions as `player1Name`                                                                                                                                        |
| **Pairs** (`player1Name`, `player2Name`) | both empty; one empty / one non-empty (`("", "Bob")` representative); both non-empty and equal; both non-empty and different                                            |
| Initial labels                           | Constructor leaves the panel in a **consistent** initial state: current-player label shows player 1 name; matchup label uses `"<p1> vs <p2>"` via `formatMatchupLine` |

### Step 2: BVA catalog data types

| Variable / output             | Catalog type           | Notes                                                                   |
| ----------------------------- | ---------------------- | ----------------------------------------------------------------------- |
| Each name parameter           | **Strings**            | empty; same length differ in last char; one shorter prefix of the other |
| Pair of names                 | **Pairs of variables** | smallest/largest length pairs if you cap length                         |
| Label text after construction | **String** / **Cases** | `<p1> vs <p2>` concatenation (empty names yield empty side, separator kept) |
| Swing tree                    | **IMPLEMENTATION**     | focus tests on **public text outcomes**, not pixel layout               |

### Step 3: Concrete boundary values (catalog-aligned)

- Strings: `""`; `" "` or `"\t"`; `"A"`; `"Alice"`; a long repeated `'a'` (constructor names only in this scope).
- Pairs: `("", "")`; `("", "Bob")`; `("Pat", "Pat")`; `("Alice", "Bob")`.
- **`null`:** not a valid input — documented caller contract; no unit test.

### Step 4: Test cases (each-choice; avoid combinatorial explosion)

- **GS-TC1: Constructor_OnBothNamesNonEmpty_CurrentPlayerLabelShowsPlayerOneName** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: `player1Name` and `player2Name` are short distinct non-empty strings
  - **Expected output**: `currentPlayerLabel` shows player 1’s name — **one assertion**

- **GS-TC2: Constructor_OnBothNamesNonEmpty_MatchupLabelShowsVersusLine** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: same as GS-TC1
  - **Expected output**: `gameStateLabel` shows `"Alice vs Bob"` for `"Alice"`, `"Bob"` — **one assertion**

- **GS-TC3: Constructor_OnBothNamesEmpty_CurrentPlayerLabelEmpty** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: both names are `""`
  - **Expected output**: `currentPlayerLabel` text is empty

- **GS-TC4: Constructor_OnBothNamesEmpty_MatchupLabelShowsVersusSeparator** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: both names are `""`
  - **Expected output**: `gameStateLabel` shows `" vs "` (`"" + " vs " + ""`)

- **GS-TC5: Constructor_OnOneNameEmptyOtherNonEmpty_CurrentPlayerLabelEmpty** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: `player1Name == ""`, `player2Name == "Bob"`
  - **Expected output**: `currentPlayerLabel` text is empty

- **GS-TC6: Constructor_OnOneNameEmptyOtherNonEmpty_MatchupLabelShowsVersusLine** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: `player1Name == ""`, `player2Name == "Bob"`
  - **Expected output**: `gameStateLabel` shows `" vs Bob"`

- **GS-TC7: Constructor_OnEqualNames_CurrentPlayerLabelShowsName** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: both names are the same non-empty string (`"Pat"`, `"Pat"`)
  - **Expected output**: `currentPlayerLabel` shows `"Pat"`

- **GS-TC8: Constructor_OnEqualNames_MatchupLabelShowsVersusLine** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: both names are `"Pat"`, `"Pat"`
  - **Expected output**: `gameStateLabel` shows `"Pat vs Pat"`

- **GS-TC9: Constructor_OnNullName_N/A** ( N/A )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: `null` name argument
  - **Expected output**: not specified — **callers must not pass null**; no defensive check in `GameStatsView`

- **GS-TC10: Constructor_OnPlayerTwoEmptyPlayerOneNonEmpty_N/A** ( N/A )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: `("Bob", "")` swapped pair vs GS-TC5
  - **Expected output**: same `<p1> vs <p2>` rule as GS-TC6 (`"Bob vs "`); redundant with formatting logic already covered by GS-TC5–GS-TC6

---

## Method: `updateCurrentPlayerLabel(String playerName)`

### Step 1: Input and output equivalence classes

| Input            | Classes                                                                                            |
| ---------------- | -------------------------------------------------------------------------------------------------- |
| `playerName`     | `""`; whitespace-only; normal name; very long. **`null` — unrepresentable** (caller contract)     |
| Prior label text | non-empty from construction, then overwritten (**overwriting previous contents**)                |

### Step 2: BVA catalog data types

| Concern          | Catalog type                                                                                  |
| ---------------- | --------------------------------------------------------------------------------------------- |
| `playerName`     | **Strings** (empty, whitespace-only, long); **null** unrepresentable per API contract         |
| Repeated updates | **Overwriting the previous contents** (shorter then longer replacement)                       |
| `JLabel` display | **Streaming / fixed UI string** — displayed text is the output per call                       |

### Step 3: Concrete boundary values

- `""` vs non-empty overwrite; whitespace-only string verbatim.
- Two calls: `"Alice"` then `"Bob"`.
- Long string (500 ASCII chars): full text shown (no truncation in this story).
- **`null`:** not a valid input — documented caller contract; no unit test.

### Step 4: Test cases

- **GS-TC11: UpdateCurrentPlayerLabel_OnTypicalName_LabelTextMatches** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: constructed view; argument is a normal non-empty name
  - **Expected output**: `currentPlayerLabel` shows exactly that string (delegates to `JLabel.setText`)

- **GS-TC12: UpdateCurrentPlayerLabel_OnEmptyString_LabelShowsEmptyPolicy** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: label previously non-empty; argument is `""`
  - **Expected output**: `currentPlayerLabel` text is empty

- **GS-TC13: UpdateCurrentPlayerLabel_OnWhitespaceOnly_LabelShowsWhitespacePolicy** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: argument is `"   "`
  - **Expected output**: label text equals the argument verbatim (no trim)

- **GS-TC14: UpdateCurrentPlayerLabel_SecondCallOverwritesFirst_LabelShowsLatest** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)` twice
  - **State of the system**: first `"Alice"`, then `"Bob"`
  - **Expected output**: final label text is `"Bob"`

- **GS-TC15: UpdateCurrentPlayerLabel_OnLongName_NoExceptionAndLabelUpdated** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: argument is 500 ASCII `'a'` characters
  - **Expected output**: no exception; label text equals the full argument

- **GS-TC16: UpdateCurrentPlayerLabel_OnNullName_N/A** ( N/A )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: `playerName` is `null`
  - **Expected output**: not specified — **callers must not pass null**
