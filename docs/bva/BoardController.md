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
  - **Expected output**: corners contain rooks with correct `PieceColor` for your fixed orientation; **extend this test (or add neighbors in the same class)** with more squares until the full standard layout is asserted—BC-TC3 is the seed, not the whole “correct positions” proof by itself

- **BC-TC4: GetBoardSnapshot_MatchesBoardSnapshot_Cellwise** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: independent `Piece[][]` built in test with real `Piece` subclasses (stand-in for a `Board` snapshot until `Board` exists)
  - **Expected output**: controller snapshot matches that grid by `PieceType` and `PieceColor` per cell (different array identity and different piece instances allowed)

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

- **BC-TC11: GetBoardSnapshot_Chess960_OneQueenTwoKnightsOnBackRank** ( :x: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: Chess960 start
  - **Expected output**: on each back rank: one `QUEEN`, two `KNIGHT`, two `BISHOP`, two `ROOK`, one `KING`

- **BC-TC12: GetBoardSnapshot_Chess960_MultipleRandomSeeds_AllValid** ( :x: )
  - **Method(s) under test**: snapshot after repeated Chess960 inits (bounded trials)
  - **State of the system**: several RNG seeds
  - **Expected output**: each trial satisfies BC-TC8–BC-TC11

**Guard (optional)**

- **BC-TC13: GetBoardSnapshot_WhenBoardMissing_DefensiveBehavior** ( :x: or **N/A** )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: controller exists with **no** `Board` reference **only if** your constructor or factory allows that transient state
  - **Expected output**: explicit failure or empty snapshot per team policy—never a plausible but wrong position
  - **If** `Board` is **always** required in the `BoardController` constructor, **drop BC-TC13** (impossible state—prefer making an illegal state unrepresentable).

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

- **BC-TC14: HandleSquareClick_BeforeFirstMove_OnWhitePiece_Selects** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)`
  - **State of the system**: standard new game, `WHITE_TURN`; `loc` on a white piece
  - **Expected output**: selection updated; `Board` piece layout unchanged

- **BC-TC15: HandleSquareClick_BeforeFirstMove_OnBlackPiece_NoBoardMutation** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)`
  - **State of the system**: standard new game, `WHITE_TURN`; `loc` on a black piece
  - **Expected output**: `getBoardSnapshot()` unchanged cell-wise; turn still `WHITE_TURN`

- **BC-TC16: HandleSquareClick_BeforeFirstMove_OnEmptySquare_NoMutation** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)`
  - **State of the system**: standard new game; `loc` on an empty square (e.g. center)
  - **Expected output**: snapshot unchanged; turn still `WHITE_TURN`

- **BC-TC17: HandleSquareClick_InvalidLocation_Rejected** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)`
  - **State of the system**: `new Location(-1, 0)` or `new Location(8, 8)`
  - **Expected output**: no board mutation; no-op or documented exception

- **BC-TC18: HandleSquareClick_Chess960Start_FirstWhiteSelectionSamePolicy** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)`
  - **State of the system**: Chess960 start, `WHITE_TURN`
  - **Expected output**: selecting a white square follows the same first-click rules as standard start (no turn flip, no piece removed)

---
