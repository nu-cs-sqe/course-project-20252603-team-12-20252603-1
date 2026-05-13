# BVA Analysis for GameStatsView

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

- **GS-TC1: Constructor_OnBothNamesNonEmpty_InitialLabelsConsistentWithDesign** ( :x: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: `player1Name` and `player2Name` are short distinct non-empty strings
  - **Expected output**: initial `JLabel` texts (or documented placeholders) match the team’s **written UI contract** for “game just opened”

- **GS-TC2: Constructor_OnBothNamesEmpty_InitialLabelsDefined** ( :x: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: both names are `""`
  - **Expected output**: no uncaught exception; initial texts are the agreed empty-state copy (or placeholders), not `null` text on visible labels if that would break the UI

- **GS-TC3: Constructor_OnOneNameEmptyOtherNonEmpty_InitialLabelsDefined** ( :x: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: `player1Name == ""`, `player2Name == "Bob"` (and optionally the swapped pair in a **separate** test if not redundant)
  - **Expected output**: consistent with GS-TC2’s empty policy and still shows usable distinction where required

- **GS-TC4: Constructor_OnEqualNames_InitialLabelsShowSameSpelling** ( :x: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: both names are the same non-empty string
  - **Expected output**: both sides of the UI that display names reflect that spelling (no accidental deduplication bug)

- **GS-TC5: Constructor_OnNullNamePolicy_DefensiveOrDocumented** ( :x: )
  - **Method(s) under test**: `GameStatsView(String, String)`
  - **State of the system**: one or both arguments `null` **if** the API permits (prefer **non-null** API and `Objects.requireNonNull`—then mark this **N/A** and delete the test)
  - **Expected output**: clear failure fast, or safe substitution per policy—never a broken `JLabel` state

---

## Method: `updateCurrentPlayerLabel(String playerName)`

### Step 1: Input and output equivalence classes

| Input            | Classes                                                                                            |
| ---------------- | -------------------------------------------------------------------------------------------------- |
| `playerName`     | `null` (policy); `""`; whitespace-only; normal name; very long; unicode                            |
| Prior label text | empty vs non-empty before update (**changing the contents of a collection** / overwrite semantics) |

### Step 2: BVA catalog data types

| Concern          | Catalog type                                                                                  |
| ---------------- | --------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------- |
| `playerName`     | **Pointers** (`null` vs reference); **Strings** (empty, compare-last-char difference)         |
| Repeated updates | **Overwriting the previous contents** (new string shorter, same length, longer than previous) |
| `JLabel` display | **Streaming / fixed UI string**                                                               | treat displayed text as the output stream of length 1 per call for that label |

### Step 3: Concrete boundary values

- `null` (only if constructor allows null strings—align with GS-TC5 policy).
- `""` then `"White"` (overwrite shorter/longer pattern).
- `"Alice"` then `"Alice"` (plateau / idempotent display).
- Long string: length 0 → 1 → many → “uncomfortably large” if you impose no hard cap.

### Step 4: Test cases

- **GS-TC6: UpdateCurrentPlayerLabel_OnTypicalName_LabelTextMatches** ( :x: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: constructed view; argument is a normal non-empty name
  - **Expected output**: `currentPlayerLabel` shows the agreed format (exact string vs `"Current: " + name`—fix one contract in tests)

- **GS-TC7: UpdateCurrentPlayerLabel_OnEmptyString_LabelShowsEmptyPolicy** ( :x: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: label previously showed `"White"`; argument is `""`
  - **Expected output**: label shows the team’s empty policy (cleared text, dash, or placeholder)—must match spec, not random whitespace

- **GS-TC8: UpdateCurrentPlayerLabel_OnWhitespaceOnly_LabelShowsWhitespacePolicy** ( :x: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: argument is `"   "` (tabs/spaces only)
  - **Expected output**: documented behavior (trim or show verbatim)

- **GS-TC9: UpdateCurrentPlayerLabel_SecondCallOverwritesFirst_LabelShowsLatest** ( :x: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)` twice
  - **State of the system**: first `"Alice"`, then `"Bob"`
  - **Expected output**: final visible text corresponds to `"Bob"` under the same formatting rules as GS-TC6

- **GS-TC10: UpdateCurrentPlayerLabel_OnLongName_NoExceptionAndLabelUpdated** ( :x: )
  - **Method(s) under test**: `updateCurrentPlayerLabel(String)`
  - **State of the system**: argument is a long but finite string (e.g. 500 ASCII chars)
  - **Expected output**: no exception; label text equals formatted output (or documents truncation with a separate truncation test if you add ellipsis rules later)

---

## Method: `updateGameStateLabel(GameState gameState)`

### Step 1: Input and output equivalence classes

| Input       | Classes                                                                                  |
| ----------- | ---------------------------------------------------------------------------------------- |
| `gameState` | Each enum case: `WHITE_TURN`, `BLACK_TURN`, `WHITE_WIN`, `BLACK_WIN`, `DRAW`             |
| Invalid     | `null` if API allows—otherwise **unrepresentable**                                       |
| Sequences   | Overwrite same state twice; transition between two different states (pairs of **Cases**) |

### Step 2: BVA catalog data types

| Concern       | Catalog type                                                                                                            |
| ------------- | ----------------------------------------------------------------------------------------------------------------------- |
| `gameState`   | **Cases** (enum): every enumerator; impossible case `null` if permitted                                                 |
| Label updates | **Overwriting the previous contents**; **Streaming** with two values if you only ever alternate two strings in practice |

### Step 3: Concrete boundary values

- All five `GameState` literals from `domain.gamestate.GameState`.
- Sequence: `WHITE_TURN` → `BLACK_TURN` → `WHITE_WIN` (exercises non-terminal then terminal copy).
- Repeat: `DRAW` → `DRAW` (plateau).

### Step 4: Test cases

- **GS-TC11: UpdateGameStateLabel_OnWhiteTurn_TextMatchesContract** ( :x: )
  - **Method(s) under test**: `updateGameStateLabel(GameState)`
  - **State of the system**: `gameState == WHITE_TURN`
  - **Expected output**: `gameStateLabel` text equals the agreed string for white to move (document the exact copy in one place—tests lock it)

- **GS-TC12: UpdateGameStateLabel_OnBlackTurn_TextMatchesContract** ( :x: )
  - **Method(s) under test**: `updateGameStateLabel(GameState)`
  - **State of the system**: `gameState == BLACK_TURN`
  - **Expected output**: label text matches agreed black-to-move copy

- **GS-TC13: UpdateGameStateLabel_OnWhiteWin_TextMatchesContract** ( :x: )
  - **Method(s) under test**: `updateGameStateLabel(GameState)`
  - **State of the system**: `gameState == WHITE_WIN`
  - **Expected output**: label text matches agreed end-state copy

- **GS-TC14: UpdateGameStateLabel_OnBlackWin_TextMatchesContract** ( :x: )
  - **Method(s) under test**: `updateGameStateLabel(GameState)`
  - **State of the system**: `gameState == BLACK_WIN`
  - **Expected output**: label text matches agreed end-state copy

- **GS-TC15: UpdateGameStateLabel_OnDraw_TextMatchesContract** ( :x: )
  - **Method(s) under test**: `updateGameStateLabel(GameState)`
  - **State of the system**: `gameState == DRAW`
  - **Expected output**: label text matches agreed draw copy

- **GS-TC16: UpdateGameStateLabel_TransitionWhiteTurnToBlackTurn_SecondTextShown** ( :x: )
  - **Method(s) under test**: `updateGameStateLabel(GameState)` twice
  - **State of the system**: call with `WHITE_TURN`, then `BLACK_TURN`
  - **Expected output**: label shows black-turn copy after the second call

- **GS-TC17: UpdateGameStateLabel_OnNullState_DefensiveOrN_A** ( :x: or **N/A** )
  - **Method(s) under test**: `updateGameStateLabel(GameState)`
  - **State of the system**: `null` only if the signature allows it (prefer non-null `GameState` and drop this row)
  - **Expected output**: fail fast or no-op per policy—never a misleading label

---

## Collaboration: constructor + updaters (ordering)

### Step 1–3: Catalog mapping

| Concern                  | Catalog type                                                                                                         |
| ------------------------ | -------------------------------------------------------------------------------------------------------------------- |
| First paint vs updates   | **Overwriting** label text after construction                                                                        |
| Cross-field independence | **Pairs of variables**: current-player text vs game-state text should not accidentally share one `StringBuilder` bug |

### Step 4: Test cases

- **GS-TC18: AfterConstruction_UpdatePlayerThenState_BothLabelsIndependent** ( :x: )
  - **Method(s) under test**: constructor, then `updateCurrentPlayerLabel`, then `updateGameStateLabel`
  - **State of the system**: typical names at construction; then `"Carol"`; then `BLACK_TURN`
  - **Expected output**: player label reflects `"Carol"` formatting; state label reflects `BLACK_TURN` formatting; neither clobbers the other’s text
