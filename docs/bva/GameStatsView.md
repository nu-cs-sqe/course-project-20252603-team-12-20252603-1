# BVA Analysis for GameStatsView

Package: `ui.GameStatsView`

Scope: **Game Initialization** (constructor through `updateCurrentPlayerLabel`).

**API contract:** All `String` and `Locale` parameters are **non-null**. Callers must not pass `null`. `GameStatsView` does not validate null; invalid null input is **out of scope** (unrepresentable boundary — not tested here).

## Method / behavior: `GameStatsView(String player1Name, String player2Name, Locale locale)`

Matchup text loads from `messages.properties` / `messages_es.properties` via `ui.Messages` (`matchupPattern`). Current-player label shows `player1Name` verbatim (user data, not bundled).

### Step 1: Input and output equivalence classes

| Input / state                            | Equivalence classes                                                                                                                                                   |
| ---------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `player1Name`                            | **Strings** — empty `""`; whitespace-only; short ASCII; long string; non-ASCII (e.g. accented or CJK)                                                                 |
| `player2Name`                            | **Strings** — same dimensions as `player1Name`                                                                                                                        |
| `locale`                                 | **Cases** — `Locale.ENGLISH` (app default); `Locale.forLanguageTag("es")` (Spanish bundle)                                                                            |
| **Pairs** (`player1Name`, `player2Name`) | both empty; one empty / one non-empty (`("", "Bob")` representative); both non-empty and equal; both non-empty and different                                        |
| Initial labels                           | Constructor leaves the panel in a **consistent** initial state: current-player label shows player 1 name; matchup label uses `matchupPattern` via `formatMatchupLine` |

### Step 2: BVA catalog data types

| Variable / output             | Catalog type           | Notes                                                                       |
| ----------------------------- | ---------------------- | --------------------------------------------------------------------------- |
| Each name parameter           | **Strings**            | empty; same length differ in last char; one shorter prefix of the other     |
| Pair of names                 | **Pairs of variables** | smallest/largest length pairs if you cap length                             |
| `locale`                      | **Cases**              | English vs Spanish (`matchupPattern` key)                                   |
| Matchup label text              | **Strings**            | formatted `matchupPattern` (empty names yield empty side, separator kept)   |
| Current-player label text     | **Strings**            | `player1Name` verbatim at construction                                      |

### Step 3: Concrete boundary values (catalog-aligned)

- **Strings:** `""`; `" "` or `"\t"`; `"A"`; `"Alice"`; a long repeated `'a'` (constructor names only in this scope).
- **Pairs:** `("", "")`; `("", "Bob")`; `("Pat", "Pat")`; `("Alice", "Bob")`.
- **Cases (`locale`):** `Locale.ENGLISH` → `{0} versus {1}`; `Locale.forLanguageTag("es")` → `{0} contra {1}`.
- **Bundle key:** `matchupPattern` (`{0}`, `{1}` placeholders).

### Step 4: Test cases (each-choice; avoid combinatorial explosion)

- **GS-TC1: Constructor_OnBothNamesNonEmpty_CurrentPlayerLabelShowsPlayerOneName** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String, Locale)`
  - **State of the system**: `player1Name` and `player2Name` are short distinct non-empty strings
  - **Expected output**: `currentPlayerLabel` shows player 1’s name

- **GS-TC2: Constructor_OnBothNamesNonEmpty_MatchupLabelShowsVersusLine** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String, Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`; same names as GS-TC1
  - **Expected output**: `gameStateLabel` shows `"Alice versus Bob"` for `"Alice"`, `"Bob"`

- **GS-TC3: Constructor_OnBothNamesEmpty_CurrentPlayerLabelEmpty** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String, Locale)`
  - **State of the system**: both names are `""`
  - **Expected output**: `currentPlayerLabel` text is empty

- **GS-TC4: Constructor_OnBothNamesEmpty_MatchupLabelShowsVersusSeparator** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String, Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`; both names are `""`
  - **Expected output**: `gameStateLabel` shows `" versus "` (`matchupPattern` with both names empty)

- **GS-TC5: Constructor_OnOneNameEmptyOtherNonEmpty_CurrentPlayerLabelEmpty** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String, Locale)`
  - **State of the system**: `player1Name == ""`, `player2Name == "Bob"`
  - **Expected output**: `currentPlayerLabel` text is empty

- **GS-TC6: Constructor_OnOneNameEmptyOtherNonEmpty_MatchupLabelShowsVersusLine** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String, Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`; `player1Name == ""`, `player2Name == "Bob"`
  - **Expected output**: `gameStateLabel` shows `" versus Bob"`

- **GS-TC7: Constructor_OnEqualNames_CurrentPlayerLabelShowsName** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String, Locale)`
  - **State of the system**: both names are the same non-empty string (`"Pat"`, `"Pat"`)
  - **Expected output**: `currentPlayerLabel` shows `"Pat"`

- **GS-TC8: Constructor_OnEqualNames_MatchupLabelShowsVersusLine** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String, Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`; both names are `"Pat"`, `"Pat"`
  - **Expected output**: `gameStateLabel` shows `"Pat versus Pat"`

- **GS-TC9: Constructor_OnPlayerTwoEmptyPlayerOneNonEmpty_N/A** ( N/A )
  - **Method(s) under test**: `GameStatsView(String, String, Locale)`
  - **State of the system**: `("Bob", "")` swapped pair vs GS-TC5
  - **Expected output**: same `matchupPattern` rule as GS-TC6 (`"Bob versus "`); redundant with formatting logic already covered by GS-TC5–GS-TC6

### Step 4 (i18n): Spanish `matchupPattern`

- **GS-TC10: Constructor_OnSpanishLocale_MatchupLabelShowsContraLine** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String, Locale)`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`; `player1Name = "Alice"`, `player2Name = "Bob"`
  - **Expected output**: `gameStateLabel` shows `"Alice contra Bob"`

- **GS-TC11: Constructor_OnSpanishLocale_EmptyNamesMatchupShowsContraSeparator** ( :white_check_mark: )
  - **Method(s) under test**: `GameStatsView(String, String, Locale)`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`; both names are `""`
  - **Expected output**: `gameStateLabel` shows `" contra "`

---

## Method: `updateCurrentPlayerLabel(String playerName)`

### Step 1: Input and output equivalence classes

| Input / state    | Equivalence classes                                                          |
| ---------------- | ---------------------------------------------------------------------------- |
| `playerName`     | **Strings** — `""`; whitespace-only; normal name; very long                |
| Prior label text | **Strings** — non-empty from construction, then overwritten on later call    |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Notes                                              |
| ----------------- | ------------ | -------------------------------------------------- |
| `playerName`      | **Strings**  | empty; whitespace-only; long                       |
| Repeated updates  | **Cases**    | single call vs second call overwrites first        |
| Label text        | **Strings**  | displayed text matches argument verbatim per call  |

### Step 3: Concrete boundary values

- **Strings:** `""` vs non-empty overwrite; whitespace-only string verbatim.
- **Cases:** two calls — `"Alice"` then `"Bob"`.
- Long string (500 ASCII chars): full text shown (no truncation in this story).

### Step 4: Test cases

- **GS-TC12: UpdateCurrentPlayerLabel_OnTypicalName_LabelTextMatches** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: constructed view; argument is a normal non-empty name
  - **Expected output**: `currentPlayerLabel` shows exactly that string (delegates to `JLabel.setText`)

- **GS-TC13: UpdateCurrentPlayerLabel_OnEmptyString_LabelShowsEmptyPolicy** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: label previously non-empty; argument is `""`
  - **Expected output**: `currentPlayerLabel` text is empty

- **GS-TC14: UpdateCurrentPlayerLabel_OnWhitespaceOnly_LabelShowsWhitespacePolicy** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: argument is `"   "`
  - **Expected output**: label text equals the argument verbatim (no trim)

- **GS-TC15: UpdateCurrentPlayerLabel_SecondCallOverwritesFirst_LabelShowsLatest** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)` twice
  - **State of the system**: first `"Alice"`, then `"Bob"`
  - **Expected output**: final label text is `"Bob"`

- **GS-TC16: UpdateCurrentPlayerLabel_OnLongName_NoExceptionAndLabelUpdated** ( :white_check_mark: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: argument is 500 ASCII `'a'` characters
  - **Expected output**: no exception; label text equals the full argument
