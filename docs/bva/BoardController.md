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
| Selection / guard | Active player may select own-color pieces only; opponent or empty must not change board |

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

---

## Method / behavior: `handleSquareClick` — alternating turn enforcement

Scope addition: derive **current player color** from `board.getCurrentGameState()` (`WHITE_TURN` → white pieces only; `BLACK_TURN` → black pieces only). Wrong-color and empty squares must not change selection or turn. Terminal states (`WHITE_WIN`, `BLACK_WIN`, `DRAW`) ignore clicks.

### Step 1: Input and output equivalence classes

| Input | Equivalence classes |
| ----- | ------------------- |
| `board.getCurrentGameState()` | `WHITE_TURN`; `BLACK_TURN`; terminal (win/draw) |
| Square at `loc` | `NonePiece`; own-color piece; opponent-color piece |

| Effect | Classes |
| ------ | ------- |
| Source selection | Own-color piece on active turn → selection set |
| Rejection | Empty, opponent piece, or terminal state → no selection |

### Step 2: BVA catalog data types

| Variable | Catalog type | Notes |
| -------- | ------------ | ----- |
| `currentGameState` | Cases | WHITE_TURN vs BLACK_TURN vs terminal |
| Piece at square | Cases | NONE vs own color vs opponent color |
| `lastSelectedLoc` | Optional | empty vs present |

### Step 3: Concrete boundary values

- Turn: `WHITE_TURN` (existing BC-TC27–35) vs **`BLACK_TURN`** (new)
- Black turn own piece: standard grid rank `1` file `0` (black pawn)
- Black turn opponent: standard grid rank `6` file `0` (white pawn)
- Black turn empty: rank `3` file `3`

### Step 4: Test cases — `BLACK_TURN` source selection

- **BC-TC43: HandleSquareClick_OnBlackTurn_OnBlackPiece_HasSelection** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `hasSelection()`
  - **State of the system**: `GameState.BLACK_TURN`; click black piece at `Location(0, 1)`
  - **Expected output**: `hasSelection()` is `true`

- **BC-TC44: HandleSquareClick_OnBlackTurn_OnBlackPiece_SelectedLocationMatches** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getSelectedLocation()`
  - **State of the system**: `GameState.BLACK_TURN`; click `Location(0, 1)`
  - **Expected output**: `getSelectedLocation()` present with same coordinates

- **BC-TC45: HandleSquareClick_OnBlackTurn_OnBlackPiece_BoardUnchanged** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getBoardSnapshot()`
  - **State of the system**: `GameState.BLACK_TURN`; click black piece
  - **Expected output**: snapshot unchanged cell-wise

- **BC-TC46: HandleSquareClick_OnBlackTurn_OnWhitePiece_NoSelectionAfterClick** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `hasSelection()`
  - **State of the system**: `GameState.BLACK_TURN`; click white piece at `Location(0, 6)`
  - **Expected output**: `hasSelection()` is `false`

- **BC-TC47: HandleSquareClick_OnBlackTurn_OnWhitePiece_TurnRemainsBlack** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getCurrentGameState()`
  - **State of the system**: `GameState.BLACK_TURN`; click white piece
  - **Expected output**: `GameState.BLACK_TURN`

- **BC-TC48: HandleSquareClick_OnBlackTurn_OnWhitePiece_BoardUnchanged** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getBoardSnapshot()`
  - **State of the system**: `GameState.BLACK_TURN`; click white piece
  - **Expected output**: snapshot unchanged cell-wise

- **BC-TC49: HandleSquareClick_OnBlackTurn_OnEmptySquare_NoSelectionAfterClick** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `hasSelection()`
  - **State of the system**: `GameState.BLACK_TURN`; empty square `Location(3, 3)`
  - **Expected output**: `hasSelection()` is `false`

- **BC-TC50: HandleSquareClick_OnBlackTurn_OnEmptySquare_TurnRemainsBlack** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getCurrentGameState()`
  - **State of the system**: `GameState.BLACK_TURN`; empty square
  - **Expected output**: `GameState.BLACK_TURN`

- **BC-TC51: HandleSquareClick_OnBlackTurn_OnEmptySquare_BoardUnchanged** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getBoardSnapshot()`
  - **State of the system**: `GameState.BLACK_TURN`; empty square
  - **Expected output**: snapshot unchanged cell-wise

---

## Method: `getLegalMovesForSelection(): List<Move>`

Scope: After the player selects an own-color piece on their turn, expose that piece's **legal** moves from `Board.getLegalMoves` for UI highlighting (`BoardView`). When nothing is selected, return an empty list without calling the board.

### Step 1: Input and output equivalence classes

| Input / state | Equivalence classes |
| ------------- | ------------------- |
| `lastSelectedLoc` | `Optional.empty()` vs present |
| `board.getLegalMoves(from)` | empty collection vs non-empty collection |

| Output | Equivalence classes |
| ------ | ------------------- |
| Returned list | empty; non-empty (same as board) |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Notes |
| ----------------- | ------------ | ----- |
| `lastSelectedLoc` | Optional | empty vs present |
| Board move list | Collections | empty vs one-or-many |
| Returned list | Collections | mirrors board when selected |

### Step 3: Concrete boundary values

- No selection: fresh controller; `getLegalMovesForSelection()` before any click.
- With selection: white turn; click white pawn at `Location(0, 6)`; stub `board.getLegalMoves(Location(0, 6))`.
- Board returns empty: same selection state; stub empty list from `getLegalMoves`.

### Step 4: Test cases

- **BC-TC52: GetLegalMovesForSelection_NoSelection_ReturnsEmptyList** ( :white_check_mark: )
  - **Method(s) under test**: `getLegalMovesForSelection()`
  - **State of the system**: newly constructed controller; no piece selected
  - **Expected output**: returned list size is `0`

- **BC-TC53: GetLegalMovesForSelection_WithSelection_ReturnsMovesFromBoard** ( :white_check_mark: )
  - **Method(s) under test**: `getLegalMovesForSelection()`, `handleSquareClick(Location)`
  - **State of the system**: white turn; white pawn selected at `Location(0, 6)`; board stubs one legal move
  - **Expected output**: returned list equals board's `getLegalMoves` result for selected square

- **BC-TC54: GetLegalMovesForSelection_WithSelection_WhenBoardReturnsEmpty_ReturnsEmptyList** ( :white_check_mark: )
  - **Method(s) under test**: `getLegalMovesForSelection()`, `handleSquareClick(Location)`
  - **State of the system**: white turn; own piece selected; board stubs empty legal-move list
  - **Expected output**: returned list size is `0`

---

## Method / behavior: move execution via `handleSquareClick(Location loc)`

Scope: when a piece is already selected, a second click either executes a legal move (`board.makeMove`), changes selection to another own piece, or clears selection. Promotion and end-game paths are covered in the sections below.

### Step 1: Equivalence Classes

- **Input: selection state** — `lastSelectedLoc` present vs absent (second click path)
- **Input: destination square** — legal move target vs illegal vs another own piece
- **Output: board interaction** — `makeMove` invoked vs not
- **Output: selection after click** — cleared vs updated

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: selection state | Cases | selected, not selected |
| Input: destination square | Cases | legal destination, illegal empty, own piece |
| Output: `makeMove` called | Boolean | `true`, `false` |
| Output: selection cleared | Boolean | `true`, `false` |

### Step 3: Boundary Values (from BVA Catalog)

- Legal destination: stub `getLegalMoves(src)` returns move to `(0, 5)`; click `(0, 5)` → `makeMove` once
- Illegal destination: stub returns move list with no matching `to` → selection cleared
- Own piece: click another white piece while selected → new `lastSelectedLoc`, no `makeMove`

### Step 4: Test Cases (Each-Choice Strategy)

Unit tests use **EasyMock** on `Board`; `makeMove` verified with `EasyMock.verify`.

- **BC-TC55: HandleSquareClick_WithSelection_OnLegalDestination_CallsMakeMove** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`
  - **State of the system**: white turn; pawn selected at `(0, 6)`; board returns legal move to `(0, 5)`
  - **Expected output**: `board.makeMove` called once with that move; `hasSelection()` is `false`
- **BC-TC56: HandleSquareClick_WithSelection_OnIllegalDestination_ClearsSelection** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`
  - **State of the system**: white turn; piece selected; click `(3, 3)` not in legal moves
  - **Expected output**: `board.makeMove` not called; `hasSelection()` is `false`
- **BC-TC57: HandleSquareClick_WithSelection_OnOwnPiece_ChangesSelection** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)`, `getSelectedLocation()`
  - **State of the system**: white turn; pawn at `(0, 6)` selected; click white knight at `(1, 7)`
  - **Expected output**: `makeMove` not called; `getSelectedLocation()` is `(1, 7)`

---

## Method: `executeMove(Move move, PieceColor currentColor)`

### Step 1: Input and output equivalence classes

| Input | Classes |
| ----- | ------- |
| `move.getType()` | `PROMOTION`; non-promotion (`NORMAL`, `EN_PASSANT`, etc.) |
| `currentColor` | `WHITE`; `BLACK` |
| `board.getCurrentGameState()` after `makeMove` | game over (`WHITE_WIN` / `BLACK_WIN` / `DRAW`); game continues (`WHITE_TURN` / `BLACK_TURN`) |

| Output | Classes |
| ------ | ------- |
| `promptForPromotionPiece` called | `true` (promotion) / `false` (non-promotion) |
| `showEndGame()` called | `true` (game over) / `false` (game continues) |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Parameters |
| --- | --- | --- |
| `move.getType()` | Cases | PROMOTION, non-promotion |
| `currentColor` | Cases | WHITE, BLACK |
| `board.getCurrentGameState()` after move | Cases | game over, game continues |
| `promptForPromotionPiece` called | Boolean | true, false |
| `showEndGame()` called | Boolean | true, false |

### Step 3: Concrete boundary values

**`move.getType()` — Cases:**
- non-promotion (e.g. `NORMAL`)
- `PROMOTION`

**`currentColor` — Cases:**
- `WHITE`
- `BLACK`

**`board.getCurrentGameState()` after move — Cases:**
- game over (`WHITE_WIN` / `BLACK_WIN` / `DRAW`) → `showEndGame()` called
- game continues (`WHITE_TURN` / `BLACK_TURN`) → covered by BC-TC55

**`promptForPromotionPiece` called — Boolean:**
- `false`: non-promotion move — covered by BC-TC55
- `true`: promotion move

**`showEndGame()` called — Boolean:**
- `true`: game is over
- `false`: game continues — covered by BC-TC55

### Step 4: Test cases

- **BC-TC58: ExecuteMove_OnNonPromotionMove_AsWhite_MakeMoveCalledDirectly** ( :white_check_mark: )
  - **Method(s) under test**: `executeMove(Move, PieceColor)`
  - **State of the system**: white pawn normal move; board stubs `WHITE_TURN` before move, `BLACK_TURN` after; `makeMove` expected once
  - **Expected output**: `board.makeMove` called once with the non-promotion move; `hasSelection()` is `false`
  - **Covered by**: BC-TC55

- **BC-TC59: ExecuteMove_AfterMoveResultsInGameOver_ShowEndGameCalled** ( :white_check_mark: )
  - **Method(s) under test**: `executeMove(Move, PieceColor)`
  - **State of the system**: board returns `WHITE_WIN` after `makeMove`; `boardController.show()` called first
  - **Expected output**: a visible `EndGameView` window found in `Window.getWindows()`

- **BC-TC60: ExecuteMove_OnNonPromotionMove_AsBlack_MakeMoveCalledDirectly** ( :white_check_mark: )
  - **Method(s) under test**: `executeMove(Move, PieceColor)`
  - **State of the system**: black pawn normal move; board stubs `BLACK_TURN` before move, `WHITE_TURN` after; `makeMove` expected once
  - **Expected output**: `board.makeMove` called once with the non-promotion move; `hasSelection()` is `false`

- **BC-TC61: ExecuteMove_OnPromotionMove_AsWhite_CallsPromptForPromotionPiece** ( :white_check_mark: )
  - **Method(s) under test**: `executeMove(Move, PieceColor)`
  - **State of the system**: white pawn promotion move (`MoveType.PROMOTION`) to back rank; `mainView` wired; board stubs `WHITE_TURN` → `BLACK_TURN`; dialog returns `QUEEN`
  - **Expected output**: `board.makeMove` called once with a `PROMOTION` move carrying `QUEEN` as promotion type

- **BC-TC62: ExecuteMove_OnPromotionMove_AsBlack_CallsPromptForPromotionPiece** ( :white_check_mark: )
  - **Method(s) under test**: `executeMove(Move, PieceColor)`
  - **State of the system**: black pawn promotion move (`MoveType.PROMOTION`) to back rank; `mainView` wired; board stubs `BLACK_TURN` → `WHITE_TURN`; dialog returns `QUEEN`
  - **Expected output**: `board.makeMove` called once with a `PROMOTION` move carrying `QUEEN` as promotion type

---

## Method: `isGameOver()`

### Step 1: Input and output equivalence classes

| Input (implicit) | Classes |
| ---------------- | ------- |
| `board.getCurrentGameState()` | `WHITE_WIN`; `BLACK_WIN`; `DRAW`; `WHITE_TURN`; `BLACK_TURN` |

| Output | Classes |
| ------ | ------- |
| Return value | `true` (terminal) / `false` (active) |

### Step 2: BVA catalog data types

| Variable / output | Catalog type |
| ----------------- | ------------ |
| `getCurrentGameState()` | Cases: WHITE_WIN, BLACK_WIN, DRAW, WHITE_TURN, BLACK_TURN |
| Return value | Boolean |

### Step 3: Concrete boundary values

**`getCurrentGameState()` — Cases:**
- `WHITE_WIN` → return `true`
- `BLACK_WIN` → return `true`
- `DRAW` → return `true`
- `WHITE_TURN` → return `false`
- `BLACK_TURN` → return `false` (covered by existing black-turn tests)

**Return value — Boolean:**
- `true`: game state is WHITE_WIN, BLACK_WIN, or DRAW
- `false`: game state is WHITE_TURN or BLACK_TURN

### Step 4: Test cases

- **BC-TC63: IsGameOver_WhenStateIsWhiteWin_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isGameOver()`
  - **State of the system**: board stubs `getCurrentGameState()` returning `WHITE_WIN`; move executed
  - **Expected output**: `EndGameView` visible (observable proxy for `isGameOver()` returning `true`)
  - **Covered by**: BC-TC59

- **BC-TC64: IsGameOver_WhenStateIsBlackWin_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isGameOver()`
  - **State of the system**: board stubs `BLACK_WIN`
  - **Expected output**: `EndGameView` visible

- **BC-TC65: IsGameOver_WhenStateIsDraw_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isGameOver()`
  - **State of the system**: board stubs `DRAW`
  - **Expected output**: `EndGameView` visible

- **BC-TC66: IsGameOver_WhenStateIsWhiteTurn_ReturnsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `isGameOver()`
  - **State of the system**: board stubs `WHITE_TURN` after move
  - **Expected output**: no `EndGameView` in `Window.getWindows()`
  - **Covered by**: BC-TC59 (board returns WHITE_TURN post-move; no `EndGameView` is shown)

---

## Method: `showEndGame()`

### Step 1: Input and output equivalence classes

| Output | Classes |
| ------ | ------- |
| `EndGameView` shown | `true` (always when called) |

### Step 2: BVA catalog data types

| Variable / output | Catalog type |
| ----------------- | ------------ |
| `EndGameView` visible | Boolean |

### Step 3: Concrete boundary values

**`EndGameView` visible — Boolean:**
- `true`: `showEndGame()` always makes `EndGameView` visible
- `false`: CAN'T SET as a post-`showEndGame()` output

### Step 4: Test cases

- **BC-TC67: ShowEndGame_WhenCalled_EndGameViewIsVisible** ( :white_check_mark: )
  - **Method(s) under test**: `showEndGame()`
  - **State of the system**: game is in a terminal state; `boardController.show()` called first
  - **Expected output**: a visible `EndGameView` in `Window.getWindows()`
  - **Covered by**: BC-TC59, BC-TC64, BC-TC65 (each triggers `showEndGame()` and verifies `EndGameView` is visible)

---

## Method: `buildEndGameMessage()`

Win and draw text load from `messages.properties` / `messages_es.properties` via `ui.Messages` (`winPattern`, `drawResult`). `updateCurrentPlayerLabel()` uses the same keys for terminal states.

### Step 1: Input and output equivalence classes

| Input (implicit) | Classes |
| ---------------- | ------- |
| `locale` (constructor) | `Locale.ENGLISH` (app default); `Locale.forLanguageTag("es")` |
| `board.getCurrentGameState()` | `WHITE_WIN`; `BLACK_WIN`; `DRAW` |

| Output | Classes |
| ------ | ------- |
| Returned message | formatted `winPattern` with player name; `drawResult` literal |

### Step 2: BVA catalog data types

| Variable / output | Catalog type |
| ----------------- | ------------ |
| `locale` | **Cases** — English vs Spanish |
| `getCurrentGameState()` | **Cases** — WHITE_WIN, BLACK_WIN, DRAW |
| Returned message | **String** — one outcome per TC |

### Step 3: Concrete boundary values

**`locale` — Cases:** `Locale.ENGLISH`; `Locale.forLanguageTag("es")`.

**`getCurrentGameState()` — Cases:**
- `WHITE_WIN` → `MessageFormat.format(winPattern, player1Name)`
- `BLACK_WIN` → `MessageFormat.format(winPattern, player2Name)`
- `DRAW` → `drawResult`

**Bundle keys:** `winPattern` (`{0}` placeholder); `drawResult`.

### Step 4: Test cases

- **BC-TC68: BuildEndGameMessage_WhiteWin_ReturnsPlayer1WinsMessage** ( :white_check_mark: )
  - **Method(s) under test**: `buildEndGameMessage()`
  - **State of the system**: `locale = Locale.ENGLISH`; board returns `WHITE_WIN`; `player1Name = "Alice"`
  - **Expected output**: `"Alice wins!"`

- **BC-TC69: BuildEndGameMessage_BlackWin_ReturnsPlayer2WinsMessage** ( :white_check_mark: )
  - **Method(s) under test**: `buildEndGameMessage()`
  - **State of the system**: `locale = Locale.ENGLISH`; board returns `BLACK_WIN`; `player2Name = "Bob"`
  - **Expected output**: `"Bob wins!"`

- **BC-TC70: BuildEndGameMessage_Draw_ReturnsDraw** ( :white_check_mark: )
  - **Method(s) under test**: `buildEndGameMessage()`
  - **State of the system**: `locale = Locale.ENGLISH`; board returns `DRAW`
  - **Expected output**: `"Draw!"`

- **BC-TC71: BuildEndGameMessage_OnSpanishLocale_WhiteWin_ReturnsSpanishWinMessage** ( :white_check_mark: )
  - **Method(s) under test**: `buildEndGameMessage()`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`; board returns `WHITE_WIN`; `player1Name = "Alice"`
  - **Expected output**: `"¡Alice gana!"`

- **BC-TC72: BuildEndGameMessage_OnSpanishLocale_BlackWin_ReturnsSpanishWinMessage** ( :white_check_mark: )
  - **Method(s) under test**: `buildEndGameMessage()`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`; board returns `BLACK_WIN`; `player2Name = "Bob"`
  - **Expected output**: `"¡Bob gana!"`

- **BC-TC73: BuildEndGameMessage_OnSpanishLocale_Draw_ReturnsSpanishDrawMessage** ( :white_check_mark: )
  - **Method(s) under test**: `buildEndGameMessage()`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`; board returns `DRAW`
  - **Expected output**: `"¡Empate!"`

---

## Method / behavior: coverage gaps — getters, repaint, bounds, `show()`

Scope: PIT line/mutation gaps not exercised by BC-TC1–73. Tests use EasyMock strict `verify` on `BoardView.repaint()` where void-call mutants survived; headless-gated `show()` for Swing UI.

### Step 4: Test cases

- **BC-TC74: GetMainView_BeforeShow_ReturnsNull** ( :white_check_mark: )
  - **Method(s) under test**: `getMainView()`
  - **State of the system**: fresh controller; `show()` not called
  - **Expected output**: `null`
- **BC-TC86: GetMainView_AfterSetMainView_ReturnsInjectedView** ( :white_check_mark: )
  - **Method(s) under test**: `getMainView()`, `setMainView(MainView)`
  - **State of the system**: `MainView` mock injected via `setMainView`
  - **Expected output**: `getMainView()` returns the same mock instance
- **BC-TC75: GetBoardView_AfterSetBoardView_ReturnsInjectedView** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardView()`, `setBoardView(BoardView)`
  - **State of the system**: `BoardView` mock injected via `setBoardView`
  - **Expected output**: `getBoardView()` returns the same mock instance
- **BC-TC76: GetLegalMovesForSelection_NoSelection_ReturnsMutableEmptyList** ( :white_check_mark: )
  - **Method(s) under test**: `getLegalMovesForSelection()`
  - **State of the system**: no selection (`Optional.empty()`)
  - **Expected output**: returned list accepts an added element (mutable `ArrayList`)
- **BC-TC77: HandleSquareClick_OnWhitePiece_RepaintsBoardView** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)` (via `handleSourceClick`, `repaintBoardView`)
  - **State of the system**: `BoardView` strict mock; white turn; click own white pawn
  - **Expected output**: `boardView.repaint()` called once; `verify(boardViewMock)` passes
- **BC-TC78: HandleSquareClick_WithSelection_OnOwnPiece_RepaintsBoardViewTwice** ( :white_check_mark: )
  - **Method(s) under test**: `handleSquareClick(Location)` (via `handleDestinationClick` reselect)
  - **State of the system**: selection then click second own piece
  - **Expected output**: `boardView.repaint()` called twice
- **BC-TC79: HandleSquareClick_WithSelection_OnIllegalDestination_RepaintsBoardViewTwice** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)` (illegal destination path)
  - **State of the system**: selection then illegal empty destination with no matching move
  - **Expected output**: `boardView.repaint()` called twice
- **BC-TC80: ExecuteMove_OnLegalMove_RepaintsBoardViewTwice** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)` (via `executeMove`, `repaintBoardView`)
  - **State of the system**: selection then legal destination; `makeMove` invoked
  - **Expected output**: `boardView.repaint()` called twice
- **BC-TC81: HandleSquareClick_OnFileEight_IgnoresClick** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)` (via `isInBounds`)
  - **State of the system**: `Location(8, 0)` out of bounds; white turn
  - **Expected output**: `hasSelection()` remains `false`
- **BC-TC87: HandleSquareClick_OnRankEight_IgnoresClick** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)` (via `isInBounds`)
  - **State of the system**: `Location(0, 8)` out of bounds; white turn
  - **Expected output**: `hasSelection()` remains `false`
- **BC-TC82: HandleSquareClick_OnMaxInBoundsSquare_AcceptsClick** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)` (via `isInBounds`)
  - **State of the system**: `Location(7, 7)` white rook on white turn
  - **Expected output**: `hasSelection()` is `true`
- **BC-TC83: HandleSquareClick_WhenGameOver_IgnoresClick** ( :x: )
  - **Method(s) under test**: `handleSquareClick(Location)`
  - **State of the system**: board returns `WHITE_WIN`; click `(0, 6)`
  - **Expected output**: `hasSelection()` remains `false`
- **BC-TC84: ExecuteMove_AfterMoveResultsInGameOver_EndGameViewIsVisible** ( :x: )
  - **Method(s) under test**: `executeMove` → `showEndGame()` → `EndGameController.show()`
  - **State of the system**: mocked `MainView`; post-move `WHITE_WIN`
  - **Expected output**: an `EndGameView` window is visible after the move
- **BC-TC85: Show_WhenCalled_MainViewBecomesVisible** ( :x: )
  - **Method(s) under test**: `show()`
  - **State of the system**: real `Board` standard start; display available (not headless)
  - **Expected output**: `getMainView().isVisible()` is `true`
