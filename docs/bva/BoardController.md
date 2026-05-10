# BVA Analysis for BoardController

### Step 1: Input and output equivalence classes

| Concern           | Equivalence classes                                                                             |
| ----------------- | ----------------------------------------------------------------------------------------------- |
| Object life cycle | Fresh instance; no clicks yet                                                                   |
| Collaborators     | Internal `Piece[][]` exists; **`Board` domain class not used yet**; **no `BoardView`** in tests |

### Step 2: BVA catalog data types

| Variable / output                  | Catalog type | Rationale                          |
| ---------------------------------- | ------------ | ---------------------------------- |
| `lastSelectedLoc` (if inspectable) | Pointers     | `null` vs non-`null` at boundaries |

### Step 3: Concrete boundary values

- `lastSelectedLoc` absent / `null` after construction.
- Zero clicks processed.

### Step 4: Test cases

- **BC-TC1: Constructor_FreshInstance_LastSelectedUnset** ( :white_check_mark: )
  - **Method(s) under test**: `BoardController()` (and any agreed way to observe selection state in tests)
  - **State of the system**: newly constructed controller; **no UI types**
  - **Expected output**: no square is selected for movement (`lastSelectedLoc` absent or `null`)

---

## Method: `hasSelection(): boolean`

### Step 1: Input and output equivalence classes

| Output         | Equivalence classes                                    |
| -------------- | ------------------------------------------------------ |
| Boolean result | `true` (selection exists); `false` (no selection)      |
| Internal state | `lastSelectedLoc == null` vs `lastSelectedLoc != null` |

### Step 2: BVA catalog data types

| Concern         | Catalog type                   |
| --------------- | ------------------------------ |
| Selection state | Pointers: `null` vs non-`null` |

### Step 3: Concrete boundary values

- `lastSelectedLoc` is `null` after construction.
- `lastSelectedLoc` is non-`null` after successful `handleSquareClick()` on a white piece.

### Step 4: Test cases

- **BC-TC1b: HasSelection_FreshInstance_ReturnsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `hasSelection()`
  - **State of the system**: newly constructed controller; no clicks yet
  - **Expected output**: `false`

- **BC-TC1c: HasSelection_AfterSelectingWhitePiece_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `hasSelection()`
  - **State of the system**: standard start; clicked on a white piece (e.g., `Location(0, 1)` for knight)
  - **Expected output**: `true`

---

## Method: `getSelectedLocation(): Location`

### Step 1: Input and output equivalence classes

| Output             | Equivalence classes                                             |
| ------------------ | --------------------------------------------------------------- |
| Returned reference | `null` (no selection); non-`null` `Location` (selection exists) |
| Location identity  | Exact location clicked; matches input coordinates               |

### Step 2: BVA catalog data types

| Concern              | Catalog type                                             |
| -------------------- | -------------------------------------------------------- |
| Selection state      | Pointers: `null` vs non-`null` reference                 |
| Location coordinates | Values: `(0, 1)`, `(7, 6)` (representative white pieces) |

### Step 3: Concrete boundary values

- `null` after construction (no selection).
- Non-`null` `Location` matching clicked coordinates after successful selection.

### Step 4: Test cases

- **BC-TC1d: GetSelectedLocation_FreshInstance_ReturnsNull** ( :white_check_mark: )
  - **Method(s) under test**: `getSelectedLocation()`
  - **State of the system**: newly constructed controller; no clicks yet
  - **Expected output**: `null`

- **BC-TC1e: GetSelectedLocation_AfterSelectingWhitePiece_ReturnsClickedLocation** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getSelectedLocation()`
  - **State of the system**: standard start; clicked on a white piece at `Location(3, 1)` (pawn)
  - **Expected output**: non-`null` `Location` equal to `Location(3, 1)`

---

## Method: `getBoardSnapshot(): Piece[][]`

### Step 1: Input and output equivalence classes

| Input (implicit) | Classes                                                                                                |
| ---------------- | ------------------------------------------------------------------------------------------------------ |
| Underlying board | Standard initial position; Chess960 initial position; board not wired (error / guard state if allowed) |

| Output    | Classes                                                                                         |
| --------- | ----------------------------------------------------------------------------------------------- |
| Grid      | Always 8×8 for a live game                                                                      |
| Cells     | `null` or `Piece`; standard vs Chess960 layouts                                                 |
| Counts    | 16 white and 16 black pieces at game start                                                      |
| Readiness | All pieces `hasMoved() == false`; turn `WHITE_TURN` (via `Board` API you expose for this story) |

### Step 2: BVA catalog data types

| Concern               | Catalog type                                                               |
| --------------------- | -------------------------------------------------------------------------- |
| Row/column count      | Sizes of collections (exactly 8 per dimension)                             |
| Indices when scanning | Multidimensional arrays: `(0,0)`, `(7,7)`, edges                           |
| Per-side totals       | Counts: exactly `16` per color at start                                    |
| Chess960 back rank    | Contents of collections; king-between-rooks; bishops opposite color parity |
| Delegation            | Cell-wise equality vs `Board`’s snapshot; array identity may differ        |

### Step 3: Concrete boundary values

- Exactly 8×8 cells; indices `0`…`7`; no ragged rows.
- Corners for placement checks once orientation is fixed.
- Chess960: include interior king file and extreme rook separation cases across sampled seeds.

### Step 4: Test cases

**Shape and delegation**

- **BC-TC2: GetBoardSnapshot_AfterStandardInit_EightByEightGrid** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: new game controller (grid dimensions only for this increment; **`Board` domain class deferred**—snapshot backed by controller-owned `Piece[][]` until extraction)
  - **Expected output**: outer length 8; each inner array length 8

- **BC-TC3: GetBoardSnapshot_AfterStandardInit_CornerCellsOccupied** ( :white_check_mark: )
- **Method(s) under test**: `getBoardSnapshot()`
- **State of the system**: standard start
- **Expected output**: full standard starting layout is present: back-rank piece types and colors match the expected standard layout and the pawn rows are present on ranks 1 and 6. This test asserts cell-wise piece type and color equality against the canonical standard starting grid.

- **BC-TC4: GetBoardSnapshot_MatchesBoardSnapshot_Cellwise** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: independent `Piece[][]` built in test with real `Piece` subclasses (stand-in for a `Board` snapshot until `Board` exists)
  - **Expected output**: controller snapshot matches that grid by `PieceType` and `PieceColor` per cell (different array identity and different piece instances allowed)

- **BC-TC4a: GetBoardSnapshot_AfterStandardInit_ReturnsIndependentCopy** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: standard start; called twice in succession
  - **Expected output**: two snapshot calls return different array objects (different references); encapsulation verified—internal `Piece[][]` protected from external mutation

**Standard start — counts and turn**

- **BC-TC5: GetBoardSnapshot_StandardStart_ExactlySixteenWhitePieces** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()` and/or `Board` state readers you add
  - **State of the system**: standard initialization complete
  - **Expected output**: count of non-`null` white pieces is `16`

- **BC-TC6: GetBoardSnapshot_StandardStart_ExactlySixteenBlackPieces** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()` (and `Board` as needed)
  - **State of the system**: standard initialization complete
  - **Expected output**: count of non-`null` black pieces is `16`

- **BC-TC7: GameStart_Standard_WhiteTurnAndNoPieceHasMoved** ( :white_check_mark: )
  - **Method(s) under test**: `getCurrentGameState()`, `getBoardSnapshot()` then `Piece.hasMoved()` on each non-`null` cell (until a `Board` class exists, turn lives on `BoardController` per diagram intent)
  - **State of the system**: immediately after standard new game
  - **Expected output**: `GameState.WHITE_TURN`; every piece has `hasMoved() == false`

**Chess960**

- **BC-TC8: GetBoardSnapshot_Chess960_BishopsOnOppositeColorSquares** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()` with `BoardController(StartingPositionKind.CHESS960)` (fixed legal SP until random SP is implemented)
  - **State of the system**: legal Chess960 start
  - **Expected output**: on each back rank, the two bishops’ squares differ in `(file + rank) % 2` parity

- **BC-TC9: GetBoardSnapshot_Chess960_KingStrictlyBetweenRooksOnBackRank** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: Chess960 start
  - **Expected output**: on each side’s back rank, king file is strictly between the two rook files

- **BC-TC10: GetBoardSnapshot_Chess960_BackRanksMirrorPieceTypes** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: Chess960 start
  - **Expected output**: same piece **types** per file on both back ranks; opposite colors; pawns on the two usual pawn ranks

- **BC-TC11: GetBoardSnapshot_Chess960_OneQueenTwoKnightsOnBackRank** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: Chess960 start
  - **Expected output**: on each back rank: one `QUEEN`, two `KNIGHT`, two `BISHOP`, two `ROOK`, one `KING`

- **BC-TC12: GetBoardSnapshot_Chess960_SeedOne_PassesTc8ThroughTc11** ( :white_check_mark: )
  - **Method(s) under test**: `new BoardController(long chess960Seed)`, `getBoardSnapshot()`
  - **State of the system**: `chess960Seed == 1L`
  - **Expected output**: composite snapshot check (same predicates as BC-TC8–BC-TC11) is satisfied — **one assertion** (additional seeds: deferred)

---

## Method: `handleSquareClick(loc: Location)`

_(Later stories extend full move/castle pipeline; here: first-turn readiness using real `Location` and `Board` only.)_

### Step 1: Input and output equivalence classes

| Input           | Classes                                                                |
| --------------- | ---------------------------------------------------------------------- |
| `loc`           | In-bounds; out-of-bounds coordinates still constructible as `Location` |
| Square at start | Empty; white piece; black piece                                        |

| Effect            | Classes                                                             |
| ----------------- | ------------------------------------------------------------------- |
| Selection / guard | White may select; black or empty at start must not change the board |

### Step 2: BVA catalog data types

| Concern     | Catalog type                                                                   |
| ----------- | ------------------------------------------------------------------------------ |
| Coordinates | Values `-1`, `0`, `7`, `8` inside `Location`; validation in controller/`Board` |
| Occupancy   | Cases: empty, own piece, opponent piece                                        |

### Step 3: Concrete boundary values

- `new Location(0,0)`, `new Location(7,7)`, other corners at standard start.
- `new Location(-1, 0)`, `new Location(8, 8)` for rejection paths.

### Step 4: Test cases

- **BC-TC13: HandleSquareClick_BeforeFirstMove_OnWhitePiece_Selects** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`
  - **State of the system**: standard new game, `WHITE_TURN`; `loc` on a white piece
  - **Expected output**: selection updated; `Board` piece layout unchanged

- **BC-TC14: HandleSquareClick_BeforeFirstMove_OnBlackPiece_NoBoardMutation** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`
  - **State of the system**: standard new game, `WHITE_TURN`; `loc` on a black piece
  - **Expected output**: `getBoardSnapshot()` unchanged cell-wise; turn still `WHITE_TURN`

- **BC-TC15: HandleSquareClick_BeforeFirstMove_OnEmptySquare_NoMutation** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`
  - **State of the system**: standard new game; `loc` on an empty square (e.g. center)
  - **Expected output**: snapshot unchanged; turn still `WHITE_TURN`

- **BC-TC16: HandleSquareClick_InvalidLocation_Rejected** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`
  - **State of the system**: `new Location(-1, 0)` or `new Location(8, 8)`
  - **Expected output**: no board mutation; no-op or documented exception

- **BC-TC17: HandleSquareClick_Chess960Start_FirstWhiteSelectionSamePolicy** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`
  - **State of the system**: Chess960 start, `WHITE_TURN`
  - **Expected output**: selecting a white square follows the same first-click rules as standard start (no turn flip, no piece removed)

  - **BC-TC17a: HandleSquareClick_Chess960Start_FirstWhiteSelection_Selects** ( :white_check_mark: )
    - **Method(s) under test**: `handleSquareClick(Location)`, `hasSelection()`
    - **State of the system**: Chess960 start; click on a white piece
    - **Expected output**: `hasSelection()` returns `true`

  - **BC-TC17b: HandleSquareClick_Chess960Start_FirstWhiteSelection_SelectedLocationMatches** ( :white_check_mark: )
    - **Method(s) under test**: `handleSquareClick(Location)`, `getSelectedLocation()`
    - **State of the system**: Chess960 start; click on a white piece at `loc`
    - **Expected output**: `getSelectedLocation()` returns a non-`null` `Location` equal to the clicked `loc`

  - **BC-TC17c: HandleSquareClick_Chess960Start_FirstWhiteSelection_TurnRemainsWhite** ( :white_check_mark: )
    - **Method(s) under test**: `handleSquareClick(Location)`, `getCurrentGameState()`
    - **State of the system**: Chess960 start; click on a white piece
    - **Expected output**: `getCurrentGameState()` remains `GameState.WHITE_TURN`

---
