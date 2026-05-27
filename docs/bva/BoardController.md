# BVA Analysis for BoardController

Package: `ui.BoardController`

Scope: **Game Initialization** and **Make a Legal Move (one turn)** — injected `domain.Board`, selection state, snapshot/turn delegation, first-click policy, second-click move completion, and optional `BoardView` wiring. **Unit tests use EasyMock** (`createMock` / `createNiceMock`, `expect`, `replay`, `verify`) to stub board collaborators. The second-click move-completion TCs are future TDD rows and require the `Board.movePiece(Location, Location): boolean` contract to be added to `Board.md` before implementation.

## Method: `BoardController(board: Board)`

### Step 1: Input and output equivalence classes

| Concern           | Equivalence classes                                                            |
| ----------------- | ------------------------------------------------------------------------------ |
| Object life cycle | Fresh controller created with an injected `Board`; no clicks yet               |
| Collaborators     | `Board` mock or stub; no `BoardView`; `BoardView` set through `setBoardView()` |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Rationale                                      |
| ----------------- | ------------ | ---------------------------------------------- |
| `board`           | Pointers     | injected collaborator object                   |
| `boardView`       | Optional     | unset vs set through `setBoardView(BoardView)` |
| `lastSelectedLoc` | Optional     | `Optional.empty()` vs `Optional.of(Location)`  |

### Step 3: Concrete boundary values

- `lastSelectedLoc` is `Optional.empty()` after construction.
- Zero clicks processed.

### Step 4: Test cases

- **BC-TC1: Constructor_FreshInstance_LastSelectedUnset** ( :white_check_mark: )
  - **Method(s) under test**: `BoardController(Board)`, `hasSelection()`
  - **State of the system**: newly constructed controller with an injected `Board` mock; no clicks yet
  - **Expected output**: `hasSelection()` is `false`

- **BC-TC2: GetSelectedLocation_FreshInstance_ReturnsEmpty** ( :white_check_mark: )
  - **Method(s) under test**: `getSelectedLocation()`
  - **State of the system**: newly constructed controller with an injected `Board` mock; no clicks yet
  - **Expected output**: `Optional.empty()`

---

## Method: `getBoardSnapshot(): Piece[][]`

### Step 1: Input and output equivalence classes

| Input (implicit) | Classes                                                        |
| ---------------- | -------------------------------------------------------------- |
| Underlying board | Standard init; fixed Chess960 init; seeded Fischer Random init |

| Output    | Classes                                                                    |
| --------- | -------------------------------------------------------------------------- |
| Grid      | Always 8×8                                                                 |
| Cells     | `Piece` per cell; empty squares use `PieceType.NONE` (`NonePiece`)         |
| Counts    | 16 white and 16 black pieces at standard start                             |
| Readiness | All non-`NONE` pieces `hasMoved() == false`; turn `WHITE_TURN` via `Board` |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Rationale / boundaries                                     |
| ----------------- | ------------ | ---------------------------------------------------------- |
| Snapshot grid     | Collection   | outer collection of 8 rows; each row collection of 8 cells |
| Snapshot cells    | Cases        | occupied piece vs `NonePiece`                              |
| Piece color count | Counts       | 0 through 16 per color in reachable start states           |
| Initializer mode  | Cases        | standard, fixed Chess960, seeded Fischer Random            |

### Step 3: Concrete boundary values

- Grid dimensions: exactly 8 rows and exactly 8 columns per row.
- Standard start piece counts: 16 white pieces, 16 black pieces, and 32 empty cells.
- Chess960 constraints: bishops on opposite colors; king between rooks; mirrored back-rank piece types.

### Step 4: Test cases

- **BC-TC3: GetBoardSnapshot_AfterStandardInit_EightByEightGrid** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardSnapshot()`
  - **State of the system**: `BoardController` constructed with a board stub whose snapshot is a standard starting grid
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

## Method: `getCurrentGameState(): GameState`

### Step 1: Input and output equivalence classes

| Input / output         | Classes                      |
| ---------------------- | ---------------------------- |
| Underlying board state | white to move; black to move |
| Returned game state    | `WHITE_TURN`; `BLACK_TURN`   |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Rationale                                                       |
| ----------------- | ------------ | --------------------------------------------------------------- |
| `GameState`       | Cases        | controller delegates and returns the board's current enum value |

### Step 3: Concrete boundary values

- New board state: `WHITE_TURN`.
- After a successful turn switch: `BLACK_TURN`.

### Step 4: Test cases

- **BC-TC8: GameStart_Standard_WhiteTurn** ( :white_check_mark: )
  - **Method(s) under test**: `getCurrentGameState()`
  - **State of the system**: immediately after standard new game
  - **Expected output**: `GameState.WHITE_TURN`

---

## Method: `hasSelection(): boolean` / `getSelectedLocation(): Optional<Location>`

### Step 1: Input and output equivalence classes

| Output                  | Equivalence classes                          |
| ----------------------- | -------------------------------------------- |
| `hasSelection()`        | `true` / `false`                             |
| `getSelectedLocation()` | `Optional.empty()` / `Optional.of(Location)` |

### Step 2: BVA catalog data types

| Concern         | Catalog type               |
| --------------- | -------------------------- |
| Selection state | Optional: empty vs present |

### Step 3: Concrete boundary values

- Empty selection: new controller before any valid piece click.
- Present selection: after a valid white-piece first click.

### Step 4: Test cases

_(BC-TC1, BC-TC2 cover fresh instance; selection-after-click covered under `handleSquareClick`.)_

---

## Method: `handleSquareClick(loc: Location)`

### Step 1: Input and output equivalence classes

| Input           | Classes                               |
| --------------- | ------------------------------------- |
| `loc`           | In-bounds; out-of-bounds              |
| Square at start | `NonePiece`; white piece; black piece |

| Effect            | Classes                                                |
| ----------------- | ------------------------------------------------------ |
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
  - **State of the system**: Chess960 fixed start; click white pawn at `Location(0, 6)`
  - **Expected output**: `getSelectedLocation()` present with same coordinates

- **BC-TC42: HandleSquareClick_Chess960Start_FirstWhiteSelection_TurnRemainsWhite** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getCurrentGameState()`
  - **State of the system**: Chess960 fixed start; white piece click
  - **Expected output**: `GameState.WHITE_TURN`

---

## Method: `setBoardView(boardView: BoardView)`

### Step 1: Input and output equivalence classes

| Input / state | Classes                           |
| ------------- | --------------------------------- |
| `boardView`   | concrete `BoardView` collaborator |
| Prior state   | no view set; view already set     |

| Output / side effect | Classes                                           |
| -------------------- | ------------------------------------------------- |
| Stored collaborator  | subsequent repaint path can use the assigned view |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Rationale                          |
| ----------------- | ------------ | ---------------------------------- |
| `boardView`       | Pointers     | real collaborator object reference |
| Previous view     | Optional     | unset vs present                   |

### Step 3: Concrete boundary values

- No previous view assigned.
- One previous view assigned and replaced by another view.

### Step 4: Test cases

- **BC-TC43: SetBoardView_NoPreviousView_StoresBoardView** ( :white_check_mark: )
  - **Method(s) under test**: `setBoardView(BoardView)`
  - **State of the system**: controller constructed with injected `Board`; no view assigned yet; package-visible `getBoardView()` used only as a test observation point
  - **Expected output**: `getBoardView()` returns the same `BoardView` reference passed to `setBoardView`

- **BC-TC44: SetBoardView_ExistingView_ReplacesBoardView** ( :x: )
  - **Method(s) under test**: `setBoardView(BoardView)`
  - **State of the system**: controller has one view assigned; `setBoardView` called again with a different `BoardView`
  - **Expected output**: `getBoardView()` returns the second `BoardView` reference

---

## Method: `handleSquareClick(loc: Location)` — move completion (second click)

Scope: **Make a Legal Move (one turn)** — applies when `lastSelectedLoc` is present. Depends on the future `Board.movePiece(Location, Location): boolean` contract. Before implementing these controller TCs, add the matching public method BVA and production API to `Board`. Team policy: an illegal destination clears selection; clicking a friendly piece is a separate reselection case and keeps selection.

### Step 1: Input and output equivalence classes

| Input                   | Classes                                                                  |
| ----------------------- | ------------------------------------------------------------------------ |
| Selection state         | `Optional.of(origin)` vs `Optional.empty()` (first-click path above)     |
| Destination coordinates | in-bounds; out-of-bounds low; out-of-bounds high                         |
| Destination square      | legal empty; legal capture; illegal shape/path; friendly occupied square |
| Active turn             | `WHITE_TURN` (black move completion deferred to Alternating Turns story) |
| View collaborator       | no `BoardView` set; `BoardView` set                                      |

| Output / side effect        | Classes                                                             |
| --------------------------- | ------------------------------------------------------------------- |
| `Board.movePiece`           | Called once with selected origin and clicked destination on attempt |
| `Board.switchTurn`          | Called only after `movePiece` returns `true`                        |
| Selection after success     | Cleared (`hasSelection()` false)                                    |
| Selection after failed move | Cleared (team policy)                                               |
| Selection after friendly    | Changed to the friendly destination                                 |
| `BoardView.repaint`         | Invoked when view is set                                            |
| Snapshot                    | Delegated through `Board.getSnapshot()` after board state changes   |

### Step 2: BVA catalog data types

| Variable / output       | Catalog type        | Rationale / boundaries                                  |
| ----------------------- | ------------------- | ------------------------------------------------------- |
| `loc.x`, `loc.y`        | Intervals           | invalid `-1`, valid min `0`, valid max `7`, invalid `8` |
| `lastSelectedLoc`       | Optional            | empty vs present; present selects second-click path     |
| Destination square      | Cases               | empty, enemy piece, friendly piece                      |
| `movePiece` return      | Boolean             | success vs rejection                                    |
| `getCurrentGameState()` | Cases               | `WHITE_TURN`, `BLACK_TURN`                              |
| `boardView`             | Optional / Pointers | no view set vs concrete view reference                  |

### Step 3: Concrete boundary values

- In-bounds destination examples: `Location(4, 5)` for a legal white pawn advance; `Location(4, 4)` for an illegal two-step shape after state is not initial.
- Out-of-bounds destination examples: `Location(-1, 5)`, `Location(8, 5)`, `Location(4, -1)`, `Location(4, 8)`.
- Friendly reselection example: selected white rook at `Location(0, 7)`, second click white knight at `Location(1, 7)` in a standard starting grid.

### Step 4: Test cases

- **BC-TC45: HandleSquareClick_WithSelection_LegalDestination_CallsMovePiece** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)` with mocked `Board`
  - **State of the system**: white pawn selected at `Location(4, 6)`; second click `Location(4, 5)`; mock `movePiece(from, to)` returns `true`
  - **Expected output**: `movePiece` invoked once with matching `from`/`to`; `verify(boardMock)` passes

- **BC-TC46: HandleSquareClick_WithSelection_LegalDestination_CallsSwitchTurn** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)` with mocked `Board`
  - **State of the system**: same as BC-TC45; `movePiece` returns `true`
  - **Expected output**: `switchTurn()` called once after successful move

- **BC-TC47: HandleSquareClick_WithSelection_LegalDestination_ClearsSelection** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `hasSelection()`
  - **State of the system**: same as BC-TC45
  - **Expected output**: `hasSelection()` is `false` after second click

- **BC-TC48: HandleSquareClick_WithSelection_LegalDestination_TurnBecomesBlack** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getCurrentGameState()`
  - **State of the system**: controller unit test with mocked `Board`; selected white pawn; `movePiece` returns `true`; `getCurrentGameState()` returns `BLACK_TURN` after `switchTurn()`
  - **Expected output**: `getCurrentGameState()` is `BLACK_TURN`

- **BC-TC49: HandleSquareClick_WithSelection_LegalDestination_RepaintsBoardView** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)` with mocked `BoardView`
  - **State of the system**: `setBoardView` called with a view mock; selected white pawn; `movePiece` returns `true`
  - **Expected output**: `repaint()` invoked once after successful move; `verify(boardViewMock)` passes

- **BC-TC50: HandleSquareClick_WithSelection_IllegalDestination_DoesNotCallSwitchTurn** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)` with mocked `Board`
  - **State of the system**: white piece selected; second click illegal; mock `movePiece` returns `false`
  - **Expected output**: `switchTurn()` never called; `verify(boardMock)` passes

- **BC-TC51: HandleSquareClick_WithSelection_IllegalDestination_TurnRemainsWhite** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getCurrentGameState()`
  - **State of the system**: same as BC-TC50; mock `getCurrentGameState()` returns `WHITE_TURN`
  - **Expected output**: `getCurrentGameState()` is `WHITE_TURN`

- **BC-TC52: HandleSquareClick_WithSelection_IllegalDestination_ClearsSelection** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `hasSelection()`
  - **State of the system**: same as BC-TC50
  - **Expected output**: `hasSelection()` is `false`

- **BC-TC53: HandleSquareClick_WithSelection_OnFriendlyPiece_ChangesSelection** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getSelectedLocation()`
  - **State of the system**: white rook selected at `Location(0, 7)`; second click white knight at `Location(1, 7)`; `movePiece` not called
  - **Expected output**: `getSelectedLocation()` present with knight coordinates; `hasSelection()` true

- **BC-TC54: HandleSquareClick_WithSelection_OnFriendlyPiece_DoesNotSwitchTurn** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getCurrentGameState()`
  - **State of the system**: same as BC-TC53
  - **Expected output**: `getCurrentGameState()` is `WHITE_TURN`; `switchTurn()` not called

- **BC-TC55: HandleSquareClick_WithSelection_OutOfBoundsDestination_DoesNotCallMovePiece** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `hasSelection()`
  - **State of the system**: white pawn selected at `Location(4, 6)`; second click out of bounds at `Location(8, 5)`
  - **Expected output**: `movePiece` and `switchTurn` are not called; selection remains on the original pawn

- **BC-TC56: HandleSquareClick_AfterSuccessfulMove_SnapshotReflectsNewPosition** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getBoardSnapshot()`
  - **State of the system**: controller unit test with mocked `Board`; selected white pawn; `movePiece` returns `true`; later `getSnapshot()` returns a test-built post-move grid
  - **Expected output**: snapshot shows pawn on rank 5 file 4 and `NONE` on rank 6 file 4
