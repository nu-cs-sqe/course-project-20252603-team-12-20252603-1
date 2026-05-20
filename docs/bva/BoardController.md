# BVA Analysis for BoardController

Package: `ui.BoardController`

Scope: **Game Initialization** — selection state, snapshot/turn delegation to `domain.Board`, first-click policy, optional `BoardView` wiring. The real `Board` implementation is another feature branch; **unit tests use EasyMock** (`createMock` / `createNiceMock`, `expect`, `replay`, `verify`) to stub `getSnapshot()`, `getCurrentGameState()`, and `getPieceAt()` with test-built `Piece[][]` grids.

### Step 1: Input and output equivalence classes

| Concern           | Equivalence classes                                                                 |
| ----------------- | ----------------------------------------------------------------------------------- |
| Object life cycle | Fresh instance; no clicks yet                                                       |
| Collaborators     | `Board` (mocked); **no `BoardView`** in controller unit tests |

### Step 2: BVA catalog data types

| Variable / output     | Catalog type | Rationale                                      |
| --------------------- | ------------ | ---------------------------------------------- |
| `lastSelectedLoc`     | Optional     | `Optional.empty()` vs `Optional.of(Location)` |

### Step 3: Concrete boundary values

- `lastSelectedLoc` is `Optional.empty()` after construction.
- Zero clicks processed.

### Step 4: Test cases

- **BC-TC1: Constructor_FreshInstance_LastSelectedUnset** ( :white_check_mark: )
  - **Method(s) under test**: `BoardController()`, `hasSelection()`
  - **State of the system**: newly constructed controller
  - **Expected output**: `hasSelection()` is `false`

- **BC-TC2: GetSelectedLocation_FreshInstance_ReturnsEmpty** ( :white_check_mark: )
  - **Method(s) under test**: `getSelectedLocation()`
  - **State of the system**: newly constructed controller; no clicks yet
  - **Expected output**: `Optional.empty()`

---

## Method: `getBoardSnapshot(): Piece[][]`

### Step 1: Input and output equivalence classes

| Input (implicit) | Classes                                                          |
| ---------------- | ---------------------------------------------------------------- |
| Underlying board | Standard init; fixed Chess960 init; seeded Fischer Random init |

| Output    | Classes                                                                        |
| --------- | ------------------------------------------------------------------------------ |
| Grid      | Always 8×8                                                                     |
| Cells     | `Piece` per cell; empty squares use `PieceType.NONE` (`NonePiece`)             |
| Counts    | 16 white and 16 black pieces at standard start                                 |
| Readiness | All non-`NONE` pieces `hasMoved() == false`; turn `WHITE_TURN` via `Board`     |

### Step 4: Test cases

- **BC-TC3: GetBoardSnapshot_AfterStandardInit_EightByEightGrid** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: `new BoardController()`
  - **Expected output**: outer length 8; each inner array length 8

- **BC-TC4: GetBoardSnapshot_AfterStandardInit_CornerCellsOccupied** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: standard start
  - **Expected output**: cell-wise type and color match canonical standard grid (including `NonePiece` on empty ranks)

- **BC-TC5: GetBoardSnapshot_MatchesBoardSnapshot_Cellwise** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: expected grid built in test; new controller
  - **Expected output**: snapshot matches expected grid by type and color per cell

- **BC-TC6: GetBoardSnapshot_StandardStart_ExactlySixteenWhitePieces** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: standard initialization
  - **Expected output**: count of white pieces with `type != NONE` is `16`

- **BC-TC7: GetBoardSnapshot_StandardStart_ExactlySixteenBlackPieces** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: standard initialization
  - **Expected output**: count of black pieces with `type != NONE` is `16`

- **BC-TC8: GameStart_Standard_WhiteTurn** ( :white_check_mark: )
  - **Method(s) under test**: `getCurrentGameState()`
  - **State of the system**: immediately after standard new game
  - **Expected output**: `GameState.WHITE_TURN`

- **BC-TC9: GameStart_Standard_NoOccupiedPieceHasMoved** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: immediately after standard new game
  - **Expected output**: every piece with `type != NONE` has `hasMoved() == false`

- **BC-TC10: GetBoardSnapshot_AfterStandardInit_ReturnsIndependentCopy** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()` twice
  - **State of the system**: standard start
  - **Expected output**: two returned arrays are different references

**Chess960 (fixed layout via `Chess960FixedBoardInitializer`)**

- **BC-TC11: GetBoardSnapshot_Chess960_BishopsOnOppositeColorSquares_WhiteBackRank** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()` with mock returning fixed Chess960 grid
  - **State of the system**: fixed Chess960 start
  - **Expected output**: white back rank bishops on opposite color parity

- **BC-TC12: GetBoardSnapshot_Chess960_BishopsOnOppositeColorSquares_BlackBackRank** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()` with mock returning fixed Chess960 grid
  - **State of the system**: fixed Chess960 start
  - **Expected output**: black back rank bishops on opposite color parity

- **BC-TC13: GetBoardSnapshot_Chess960_KingStrictlyBetweenRooksOnBackRank_WhiteBackRank** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: fixed Chess960 start
  - **Expected output**: white king file strictly between own rook files

- **BC-TC14: GetBoardSnapshot_Chess960_KingStrictlyBetweenRooksOnBackRank_BlackBackRank** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: fixed Chess960 start
  - **Expected output**: black king file strictly between own rook files

- **BC-TC15: GetBoardSnapshot_Chess960_BackRanksMirrorPieceTypes_BackRankTypesMirror** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: fixed Chess960 start
  - **Expected output**: same piece types per file on ranks 0 and 7; opposite colors

- **BC-TC16: GetBoardSnapshot_Chess960_BackRanksMirrorPieceTypes_StandardPawnRows** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: fixed Chess960 start
  - **Expected output**: pawn rows on ranks 1 and 6 with correct colors

- **BC-TC17: GetBoardSnapshot_Chess960_OneQueenTwoKnightsOnBackRank_WhiteBackRank** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: fixed Chess960 start
  - **Expected output**: white back rank has one queen, two knights, two bishops, two rooks, one king

- **BC-TC18: GetBoardSnapshot_Chess960_OneQueenTwoKnightsOnBackRank_BlackBackRank** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: fixed Chess960 start
  - **Expected output**: black back rank has one queen, two knights, two bishops, two rooks, one king

**Chess960 (seeded via `FischerRandomBoardInitializer`, seed `1L`)**

- **BC-TC19: GetBoardSnapshot_Chess960_SeedOne_BishopsOppositeColorSquares_WhiteBackRank** ( :white_check_mark: )
- **BC-TC20: GetBoardSnapshot_Chess960_SeedOne_BishopsOppositeColorSquares_BlackBackRank** ( :white_check_mark: )
- **BC-TC21: GetBoardSnapshot_Chess960_SeedOne_KingStrictlyBetweenRooks_WhiteBackRank** ( :white_check_mark: )
- **BC-TC22: GetBoardSnapshot_Chess960_SeedOne_KingStrictlyBetweenRooks_BlackBackRank** ( :white_check_mark: )
- **BC-TC23: GetBoardSnapshot_Chess960_SeedOne_BackRanksMirrorPieceTypes** ( :white_check_mark: )
- **BC-TC24: GetBoardSnapshot_Chess960_SeedOne_StandardPawnRows** ( :white_check_mark: )
- **BC-TC25: GetBoardSnapshot_Chess960_SeedOne_OneQueenTwoKnightsOnBackRank_WhiteBackRank** ( :white_check_mark: )
- **BC-TC26: GetBoardSnapshot_Chess960_SeedOne_OneQueenTwoKnightsOnBackRank_BlackBackRank** ( :white_check_mark: )

  - **Method(s) under test**: `getBoardSnapshot()` with mock returning seed-`1L` Chess960 grid (built in test)
  - **State of the system**: `chess960Seed == 1L`
  - **Expected output**: same predicates as BC-TC11–BC-TC18 for the seeded layout

---

## Method: `hasSelection(): boolean` / `getSelectedLocation(): Optional<Location>`

### Step 1: Input and output equivalence classes

| Output                    | Equivalence classes                                      |
| ------------------------- | -------------------------------------------------------- |
| `hasSelection()`          | `true` / `false`                                         |
| `getSelectedLocation()`   | `Optional.empty()` / `Optional.of(Location)`              |

### Step 2: BVA catalog data types

| Concern           | Catalog type                          |
| ----------------- | ------------------------------------- |
| Selection state   | Optional: empty vs present            |

### Step 4: Test cases

_(BC-TC1, BC-TC2 cover fresh instance; selection-after-click covered under `handleSquareClick`.)_

---

## Method: `handleSquareClick(loc: Location)`

### Step 1: Input and output equivalence classes

| Input           | Classes                                       |
| --------------- | --------------------------------------------- |
| `loc`           | In-bounds; out-of-bounds                      |
| Square at start | `NonePiece`; white piece; black piece         |

| Effect            | Classes                                              |
| ----------------- | ---------------------------------------------------- |
| Selection / guard | White may select; black or empty must not change board |

### Step 4: Test cases

- **BC-TC27: HandleSquareClick_BeforeFirstMove_OnWhitePiece_HasSelection** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `hasSelection()`
  - **State of the system**: standard new game; white piece at `loc`
  - **Expected output**: `hasSelection()` is `true`

- **BC-TC28: HandleSquareClick_BeforeFirstMove_OnWhitePiece_SelectedLocationMatches** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getSelectedLocation()`
  - **State of the system**: standard new game; click `Location(0, 1)`
  - **Expected output**: `getSelectedLocation()` present with same coordinates as click

- **BC-TC29: HandleSquareClick_BeforeFirstMove_OnWhitePiece_BoardUnchanged** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getBoardSnapshot()`
  - **State of the system**: standard new game; click white piece
  - **Expected output**: snapshot unchanged cell-wise

- **BC-TC30: HandleSquareClick_BeforeFirstMove_OnBlackPiece_NoSelectionAfterClick** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `hasSelection()`
  - **State of the system**: standard new game; black piece square
  - **Expected output**: `hasSelection()` is `false`

- **BC-TC31: HandleSquareClick_BeforeFirstMove_OnBlackPiece_TurnRemainsWhite** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getCurrentGameState()`
  - **State of the system**: standard new game; black piece square
  - **Expected output**: `GameState.WHITE_TURN`

- **BC-TC32: HandleSquareClick_BeforeFirstMove_OnBlackPiece_BoardUnchanged** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getBoardSnapshot()`
  - **State of the system**: standard new game; black piece square
  - **Expected output**: snapshot unchanged cell-wise

- **BC-TC33: HandleSquareClick_BeforeFirstMove_OnEmptySquare_NoSelectionAfterClick** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `hasSelection()`
  - **State of the system**: standard new game; `NonePiece` square (e.g. center)
  - **Expected output**: `hasSelection()` is `false`

- **BC-TC34: HandleSquareClick_BeforeFirstMove_OnEmptySquare_TurnRemainsWhite** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getCurrentGameState()`
  - **State of the system**: standard new game; empty square
  - **Expected output**: `GameState.WHITE_TURN`

- **BC-TC35: HandleSquareClick_BeforeFirstMove_OnEmptySquare_BoardUnchanged** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getBoardSnapshot()`
  - **State of the system**: standard new game; empty square
  - **Expected output**: snapshot unchanged cell-wise

- **BC-TC36: HandleSquareClick_InvalidLocation_NoSelectionAfterClick** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `hasSelection()`
  - **State of the system**: `new Location(-1, 0)`
  - **Expected output**: `hasSelection()` is `false`

- **BC-TC37: HandleSquareClick_InvalidLocation_TurnRemainsWhite** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getCurrentGameState()`
  - **State of the system**: out-of-bounds `loc`
  - **Expected output**: `GameState.WHITE_TURN`

- **BC-TC38: HandleSquareClick_InvalidLocation_BoardUnchanged** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getBoardSnapshot()`
  - **State of the system**: out-of-bounds `loc`
  - **Expected output**: snapshot unchanged cell-wise

- **BC-TC39: HandleSquareClick_Chess960Start_FirstWhiteSelectionSamePolicy** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getBoardSnapshot()`
  - **State of the system**: Chess960 fixed start
  - **Expected output**: snapshot matches fixed Chess960 grid before/after policy check

- **BC-TC40: HandleSquareClick_Chess960Start_FirstWhiteSelection_SelectsAndKeepsTurn** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `hasSelection()`
  - **State of the system**: Chess960 fixed start; white piece click
  - **Expected output**: `hasSelection()` is `true`

- **BC-TC41: HandleSquareClick_Chess960Start_FirstWhiteSelection_SelectedLocationMatches** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getSelectedLocation()`
  - **State of the system**: Chess960 fixed start; click `Location(0, 1)`
  - **Expected output**: `getSelectedLocation()` present with same coordinates

- **BC-TC42: HandleSquareClick_Chess960Start_FirstWhiteSelection_TurnRemainsWhite** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getCurrentGameState()`
  - **State of the system**: Chess960 fixed start; white piece click
  - **Expected output**: `GameState.WHITE_TURN`
