# BVA Analysis for Board

## Method: `Board(BoardInitializer)`

### Step 1: Equivalence Classes

- **Input: piece type returned by initializer** — the `PieceType` at each position in the layout
- **Input: row position (color-assignment threshold)** — the row index of a position in the layout; implementation assigns BLACK to rows 0–3 and WHITE to rows 4–7 via the comparison `row < BLACK_RANK_ROWS` (= 4)
- **Output: piece type at position** — the type of the `Piece` placed on the board
- **Output: piece color at an occupied position** — the color assigned to the piece
- **Output: initial game state** — the game state immediately after construction

### Step 2: Data Types (from BVA Catalog)

| Equivalence class                              | Catalog data type | Parameters                                    |
| ---------------------------------------------- | ----------------- | --------------------------------------------- |
| Input: piece type returned by initializer      | Cases             | ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN, NONE |
| Input: row position (color-assignment threshold) | Interval        | [0, 7], threshold at 4; rows [0, 3] → BLACK, rows [4, 7] → WHITE |
| Output: piece type at position                 | Cases             | ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN, NONE |
| Output: piece color at an occupied position    | Cases             | BLACK, WHITE                                  |
| Output: initial game state                     | Cases             | WHITE_TURN                                    |

### Step 3: Boundary Values (from BVA Catalog)

**Piece types — Cases:**
- ROOK
- KNIGHT
- BISHOP
- QUEEN
- KING
- PAWN
- NONE

**Row position — Interval [0, 7] with color-assignment threshold at row 4:**
- 0 (min of [0, 3] BLACK sub-range — tested by TC9)
- 3 (max of [0, 3] BLACK sub-range — boundary, one step below threshold)
- 4 (min of [4, 7] WHITE sub-range — boundary, at threshold)
- 7 (max of [4, 7] WHITE sub-range — tested by TC10)

**Piece color — Cases:**
- BLACK
- WHITE

**Game state — Cases:**
- WHITE_TURN

### Step 4: Test Cases (Each-Choice Strategy)

- **TC1: Constructor_WhenInitializerHasPieceTypeAtPosition_PieceTypeMatches** ( :white_check_mark: )
  - **Method(s) under test**: `Board(BoardInitializer)`
  - **State of the system**: initializer returns a layout with a single non-NONE piece type at one position; all other positions are NONE; board is constructed
  - **Expected output**: `getSnapshot()[row][col].getType()` equals the piece type the initializer returned
  - **Note**: TC2–TC7 are covered by this test case as a parameterized test

- **TC2: Constructor_WhenInitializerHasRookAtPosition_PieceTypeIsRook** ( :white_check_mark: )
  - **Method(s) under test**: `Board(BoardInitializer)`
  - **State of the system**: initializer returns ROOK at some position
  - **Expected output**: piece at that position has type ROOK
  - **Covered by**: TC1 (parameterized test)

- **TC3: Constructor_WhenInitializerHasKnightAtPosition_PieceTypeIsKnight** ( :white_check_mark: )
  - **Method(s) under test**: `Board(BoardInitializer)`
  - **State of the system**: initializer returns KNIGHT at some position
  - **Expected output**: piece at that position has type KNIGHT
  - **Covered by**: TC1 (parameterized test)

- **TC4: Constructor_WhenInitializerHasBishopAtPosition_PieceTypeIsBishop** ( :white_check_mark: )
  - **Method(s) under test**: `Board(BoardInitializer)`
  - **State of the system**: initializer returns BISHOP at some position
  - **Expected output**: piece at that position has type BISHOP
  - **Covered by**: TC1 (parameterized test)

- **TC5: Constructor_WhenInitializerHasQueenAtPosition_PieceTypeIsQueen** ( :white_check_mark: )
  - **Method(s) under test**: `Board(BoardInitializer)`
  - **State of the system**: initializer returns QUEEN at some position
  - **Expected output**: piece at that position has type QUEEN
  - **Covered by**: TC1 (parameterized test)

- **TC6: Constructor_WhenInitializerHasKingAtPosition_PieceTypeIsKing** ( :white_check_mark: )
  - **Method(s) under test**: `Board(BoardInitializer)`
  - **State of the system**: initializer returns KING at some position
  - **Expected output**: piece at that position has type KING
  - **Covered by**: TC1 (parameterized test)

- **TC7: Constructor_WhenInitializerHasPawnAtPosition_PieceTypeIsPawn** ( :white_check_mark: )
  - **Method(s) under test**: `Board(BoardInitializer)`
  - **State of the system**: initializer returns PAWN at some position
  - **Expected output**: piece at that position has type PAWN
  - **Covered by**: TC1 (parameterized test)

- **TC8: Constructor_WhenInitializerHasNoneAtPosition_PieceTypeIsNone** ( :white_check_mark: )
  - **Method(s) under test**: `Board(BoardInitializer)`
  - **State of the system**: initializer returns NONE at some position
  - **Expected output**: piece at that position has type NONE
  - **Covered by**: TC1 (parameterized test)

- **TC9: Constructor_WhenInitializerHasNonNoneTypeInTopHalf_PieceColorIsBlack** ( :white_check_mark: )
  - **Method(s) under test**: `Board(BoardInitializer)`
  - **State of the system**: initializer returns a non-NONE piece type at a row 0–3 position; board is constructed
  - **Expected output**: piece at that position has color BLACK

- **TC10: Constructor_WhenInitializerHasNonNoneTypeInBottomHalf_PieceColorIsWhite** ( :white_check_mark: )
  - **Method(s) under test**: `Board(BoardInitializer)`
  - **State of the system**: initializer returns a non-NONE piece type at a row 4–7 position; board is constructed
  - **Expected output**: piece at that position has color WHITE

- **TC11: Constructor_WhenInitializerHasNonNoneTypeAtRowThree_PieceColorIsBlack** ( :white_check_mark: )
  - **Method(s) under test**: `Board(BoardInitializer)`
  - **State of the system**: initializer returns a non-NONE piece type at row 3 (max of BLACK sub-range [0, 3]); all other positions NONE; board is constructed
  - **Expected output**: piece at `[3][0]` has color BLACK

- **TC12: Constructor_WhenInitializerHasNonNoneTypeAtRowFour_PieceColorIsWhite** ( :white_check_mark: )
  - **Method(s) under test**: `Board(BoardInitializer)`
  - **State of the system**: initializer returns a non-NONE piece type at row 4 (min of WHITE sub-range [4, 7]); all other positions NONE; board is constructed
  - **Expected output**: piece at `[4][0]` has color WHITE

- **TC13: Constructor_OnNewBoard_GameStateIsWhiteTurn** ( :white_check_mark: )
  - **Method(s) under test**: `Board(BoardInitializer)`, `getCurrentGameState()`
  - **State of the system**: board is freshly constructed
  - **Expected output**: `getCurrentGameState()` returns `WHITE_TURN`

---

## Method: `Board(Piece[][])`

### Step 1: Equivalence Classes

- **Input: piece type of the `Piece` at each position** — the `PieceType` held by each object in the array
- **Input: piece color of the `Piece` at each position** — the `PieceColor` held by each object in the array
- **Input: row of each piece** — which row (0–7) the piece occupies in the array
- **Input: column of each piece** — which column (0–7) the piece occupies in the array
- **Output: piece type at position** — the type of the `Piece` stored on the board after construction
- **Output: piece color at position** — the color of the `Piece` stored on the board after construction (must equal the input color, not derived from row)
- **Output: initial game state** — the game state immediately after construction

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: piece type of the `Piece` at each position | Cases | ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN, NONE |
| Input: piece color of the `Piece` at each position | Cases | BLACK, WHITE |
| Input: row of each piece | Interval | [0, 7] |
| Input: column of each piece | Interval | [0, 7] |
| Output: piece type at position | Cases | ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN, NONE |
| Output: piece color at position | Cases | BLACK, WHITE |
| Output: initial game state | Cases | WHITE_TURN |

### Step 3: Boundary Values (from BVA Catalog)

**Piece types — Cases:**
- ROOK
- KNIGHT
- BISHOP
- QUEEN
- KING
- PAWN
- NONE

**Piece colors — Cases:**
- BLACK
- WHITE

**Row — Interval [0, 7]:**
- 0 (min), 7 (max)

**Column — Interval [0, 7]:**
- 0 (min), 7 (max)

**Game state — Cases:**
- WHITE_TURN

### Step 4: Test Cases (Each-Choice Strategy)

- **TC14: `Constructor_WhenPieceArrayHasRookAtPosition_PieceTypeIsRook`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Rook(BLACK)` at `[0][0]`; all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[0][0].getType()` equals `ROOK`
  - **Note**: TC15–TC20 and TC24 are covered by this test case as a parameterized test

- **TC15: `Constructor_WhenPieceArrayHasKnightAtPosition_PieceTypeIsKnight`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Knight(BLACK)` at `[0][1]`; all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[0][1].getType()` equals `KNIGHT`
  - **Covered by**: TC14 (parameterized test)

- **TC16: `Constructor_WhenPieceArrayHasBishopAtPosition_PieceTypeIsBishop`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Bishop(BLACK)` at `[0][2]`; all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[0][2].getType()` equals `BISHOP`
  - **Covered by**: TC14 (parameterized test)

- **TC17: `Constructor_WhenPieceArrayHasQueenAtPosition_PieceTypeIsQueen`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Queen(BLACK)` at `[0][3]`; all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[0][3].getType()` equals `QUEEN`
  - **Covered by**: TC14 (parameterized test)

- **TC18: `Constructor_WhenPieceArrayHasKingAtPosition_PieceTypeIsKing`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `King(BLACK)` at `[0][4]`; all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[0][4].getType()` equals `KING`
  - **Covered by**: TC14 (parameterized test)

- **TC19: `Constructor_WhenPieceArrayHasPawnAtPosition_PieceTypeIsPawn`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Pawn(BLACK)` at `[1][0]`; all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[1][0].getType()` equals `PAWN`
  - **Covered by**: TC14 (parameterized test)

- **TC20: `Constructor_WhenPieceArrayHasNonePieceAtPosition_PieceTypeIsNone`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `NonePiece` at `[7][0]`; all other positions `NonePiece`; board is constructed — covers row 7 (max)
  - **Expected output**: `getSnapshot()[7][0].getType()` equals `NONE`
  - **Covered by**: TC14 (parameterized test)

- **TC21: `Constructor_WhenPieceArrayHasBlackPieceAtPosition_PieceColorIsBlack`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Rook(BLACK)` at `[7][0]` (bottom half, which `Board(BoardInitializer)` would assign `WHITE`); all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[7][0].getColor()` equals `BLACK`

- **TC22: `Constructor_WhenPieceArrayHasWhitePieceAtPosition_PieceColorIsWhite`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Rook(WHITE)` at `[0][0]` (top half, which `Board(BoardInitializer)` would assign `BLACK`); all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[0][0].getColor()` equals `WHITE`

- **TC23: `Constructor_WithPieceArray_OnNewBoard_GameStateIsWhiteTurn`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: board constructed with an all-`NonePiece` `Piece[][]`
  - **Expected output**: `getCurrentGameState()` returns `WHITE_TURN`

- **TC24: `Constructor_WhenPieceArrayHasPieceAtRowZeroColSeven_PieceTypeMatches`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Rook(BLACK)` at `[0][7]`; all other positions `NonePiece`; board is constructed — covers col 7 (max)
  - **Expected output**: `getSnapshot()[0][7].getType()` equals `ROOK`
  - **Covered by**: TC14 (parameterized test)

---

## Method: `createPiece(PieceType, PieceColor)` (private)

### Step 1: Equivalence Classes

- **Input: piece type** — the `PieceType` value passed to the method
- **Input: piece color** — the `PieceColor` value passed to the method
- **Output: type of the returned `Piece`** — should match the input type
- **Output: color of the returned `Piece`** — should match the input color (not applicable for NONE)

### Step 2: Data Types (from BVA Catalog)

| Equivalence class              | Catalog data type | Parameters                                    |
| ------------------------------ | ----------------- | --------------------------------------------- |
| Input: piece type              | Cases             | ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN, NONE |
| Input: piece color             | Cases             | BLACK, WHITE                                  |
| Output: returned piece type    | Cases             | ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN, NONE |
| Output: returned piece color   | Cases             | BLACK, WHITE                                  |

### Step 3: Boundary Values (from BVA Catalog)

**Piece types — Cases:**
- ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN, NONE

**Piece colors — Cases:**
- BLACK, WHITE

### Step 4: Test Cases (Each-Choice Strategy)

`createPiece` is private; all test cases are exercised indirectly through `Board(BoardInitializer)`.

- **TC25: CreatePiece_WithRookAndBlack_ReturnsPieceWithTypeRookAndColorBlack** ( :white_check_mark: )
  - **Method(s) under test**: `createPiece(PieceType, PieceColor)` via `Board(BoardInitializer)`
  - **State of the system**: initializer returns ROOK at a top-half position
  - **Expected output**: piece has type ROOK and color BLACK
  - **Covered by**: TC2 + TC9

- **TC26: CreatePiece_WithKnight_ReturnsPieceWithTypeKnight** ( :white_check_mark: )
  - **Covered by**: TC3

- **TC27: CreatePiece_WithBishop_ReturnsPieceWithTypeBishop** ( :white_check_mark: )
  - **Covered by**: TC4

- **TC28: CreatePiece_WithQueen_ReturnsPieceWithTypeQueen** ( :white_check_mark: )
  - **Covered by**: TC5

- **TC29: CreatePiece_WithKing_ReturnsPieceWithTypeKing** ( :white_check_mark: )
  - **Covered by**: TC6

- **TC30: CreatePiece_WithPawn_ReturnsPieceWithTypePawn** ( :white_check_mark: )
  - **Covered by**: TC7

- **TC31: CreatePiece_WithNone_ReturnsPieceWithTypeNone** ( :white_check_mark: )
  - **Covered by**: TC8

- **TC32: CreatePiece_WithWhite_ReturnsPieceWithColorWhite** ( :white_check_mark: )
  - **Method(s) under test**: `createPiece(PieceType, PieceColor)` via `Board(BoardInitializer)`
  - **State of the system**: initializer returns a non-NONE piece at a bottom-half position
  - **Expected output**: piece has color WHITE
  - **Covered by**: TC10

---

## Method: `getSnapshot()`

### Step 1: Equivalence Classes

- **Output: relationship between returned array and internal array** — whether the snapshot is a distinct copy or the same reference
- **Output: snapshot content** — whether the snapshot accurately reflects the board state

### Step 2: Data Types (from BVA Catalog)

| Equivalence class                        | Catalog data type      | Parameters                                                       |
| ---------------------------------------- | ---------------------- | ---------------------------------------------------------------- |
| Output: returned outer array vs internal | Pairs of references    | two references should refer to different objects                 |
| Output: returned row array vs internal   | Pairs of references    | two references should refer to different objects                 |
| Output: returned Piece vs internal       | Pairs of references    | two references should refer to different objects with same contents |
| Output: snapshot content                 | Collections (contents) | snapshot matches current board state                             |

### Step 3: Boundary Values (from BVA Catalog)

**Pairs of references:**
- Two references refer to the same object (should NOT happen — this is what we test against)
- Two reference arguments refer to different objects with the same contents (should happen)

**Collections (contents):**
- Collection contains the expected elements (content matches board state)

### Step 4: Test Cases (Each-Choice Strategy)

- **TC33: GetSnapshot_ReturnedOuterArrayIsDifferentObject** ( :white_check_mark: )
  - **Method(s) under test**: `getSnapshot()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`
  - **Expected output**: the returned `Piece[][]` reference is not the same object as a second call to `getSnapshot()`

- **TC34: GetSnapshot_ReturnedRowArrayIsDifferentObject** ( :white_check_mark: )
  - **Method(s) under test**: `getSnapshot()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`
  - **Expected output**: `getSnapshot()[0]` is not the same array reference as a second `getSnapshot()[0]`

- **TC35: GetSnapshot_ReturnedPieceIsDifferentObjectWithSameContents** ( :white_check_mark: )
  - **Method(s) under test**: `getSnapshot()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`
  - **Expected output**: the Piece object at `getSnapshot()[0][0]` is not the same reference as the Piece at a second `getSnapshot()[0][0]`, but has the same type and color

- **TC36: GetSnapshot_SnapshotContentMatchesBoardState** ( :white_check_mark: )
  - **Method(s) under test**: `getSnapshot()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`
  - **Expected output**: `getSnapshot()[7][4]` has type `KING` and color `WHITE`

- **TC37: GetSnapshot_ModifySnapshotDoesNotAffectBoard** ( :white_check_mark: )
  - **Method(s) under test**: `getSnapshot()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`; set `getSnapshot()[7][0]` to `null`
  - **Expected output**: a subsequent `getSnapshot()[7][0]` still has type `ROOK` and color `WHITE`

---

## Method: `getLegalMoves(Location from)`

### Step 1: Equivalence Classes

- **Input: from location** — board location to generate legal moves from
- **Input: piece at from** — empty square vs movable piece
- **Input: enPassantTarget state** — empty vs present on the board (passed to `MoveGenerator` in production)
- **Output: legal move list** — list returned by `MoveGenerator.generateLegalMoves(from)`

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: from location | Pairs of variables | file/rank in [0, 7] |
| Input: piece at from | Cases | NONE, KNIGHT |
| Input: enPassantTarget | Optional | empty, present |
| Output: legal move list | Collections | empty list, list with moves |
| Collaborator | Pointers | `MoveGenerator` (same class used in production) |

### Step 3: Boundary Values (from BVA Catalog)

**from location — Pairs of variables:**
- (3,3) empty square
- (4,4) knight at center

**enPassantTarget — Optional:**
- `Optional.empty()` for basic delegation tests

**legal move list size — Counts:**
- 0
- 8

### Step 4: Test Cases

- **TC38: GetLegalMoves_OnEmptySquare_ReturnsEmptyList** ( :white_check_mark: )
  - **Method(s) under test**: `getLegalMoves(Location)`
  - **State of the system**: board with `NonePiece` at `(3,3)`; no other pieces
  - **Expected output**: returned list size is `0`

- **TC39: GetLegalMoves_OnCenterKnight_ReturnsEightMoves** ( :white_check_mark: )
  - **Method(s) under test**: `getLegalMoves(Location)`
  - **State of the system**: lone white knight at `(4,4)` on otherwise empty board
  - **Expected output**: returned list size is `8`

- **TC40: GetLegalMoves_WhenCalled_MatchesMoveGenerator** ( :white_check_mark: )
  - **Method(s) under test**: `getLegalMoves(Location)`
  - **State of the system**: board with white pawn at `(5,3)` and `enPassantTarget` set
  - **Expected output**: returned list size matches `new MoveGenerator(snapshot, enPassantTarget).generateLegalMoves(from)`

---

## Method: `getCurrentGameState()`

### Step 1: Equivalence Classes

- **Output: the returned game state** — which `GameState` enum value is returned

### Step 2: Data Types (from BVA Catalog)

| Equivalence class           | Catalog data type | Parameters                                         |
| --------------------------- | ----------------- | -------------------------------------------------- |
| Output: returned game state | Cases             | WHITE_TURN, BLACK_TURN, WHITE_WIN, BLACK_WIN, DRAW |

### Step 3: Boundary Values (from BVA Catalog)

**Game state — Cases:**
- WHITE_TURN
- BLACK_TURN
- WHITE_WIN
- BLACK_WIN
- DRAW

### Step 4: Test Cases (Each-Choice Strategy)

- **TC41: GetCurrentGameState_OnNewBoard_ReturnsWhiteTurn** ( :white_check_mark: )
  - **Method(s) under test**: `getCurrentGameState()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`
  - **Expected output**: returns `WHITE_TURN`
  - **Covered by**: TC13

- **TC42: GetCurrentGameState_AfterSwitchTurn_ReturnsBlackTurn** ( :white_check_mark: )
  - **Method(s) under test**: `getCurrentGameState()`, `switchTurn()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`; `switchTurn()` called once
  - **Expected output**: returns `BLACK_TURN`

- **TC43: GetCurrentGameState_AfterWhiteCheckmate_ReturnsWhiteWin** ( :white_check_mark: )
  - **Method(s) under test**: `getCurrentGameState()`, `makeMove()`
  - **State of the system**: one-move checkmate position; white's move leaves black with no legal moves and in check
  - **Expected output**: returns `WHITE_WIN`

- **TC44: GetCurrentGameState_AfterBlackCheckmate_ReturnsBlackWin** ( :white_check_mark: )
  - **Method(s) under test**: `getCurrentGameState()`, `makeMove()`
  - **State of the system**: one-move checkmate position; black's move leaves white with no legal moves and in check
  - **Expected output**: returns `BLACK_WIN`

- **TC45: GetCurrentGameState_AfterStalemate_ReturnsDraw** ( :white_check_mark: )
  - **Method(s) under test**: `getCurrentGameState()`, `makeMove()`
  - **State of the system**: white's move leaves black with no legal moves and not in check
  - **Expected output**: returns `DRAW`

---

## Method: `switchTurn()`

### Step 1: Equivalence Classes

- **Input: game state before the call** — which turn it currently is
- **Output: game state after the call** — which turn it switches to

### Step 2: Data Types (from BVA Catalog)

| Equivalence class             | Catalog data type | Parameters             |
| ----------------------------- | ----------------- | ---------------------- |
| Input: game state before call | Cases             | WHITE_TURN, BLACK_TURN |
| Output: game state after call | Cases             | BLACK_TURN, WHITE_TURN |

### Step 3: Boundary Values (from BVA Catalog)

**Input game state — Cases:**
- WHITE_TURN (first possibility)
- BLACK_TURN (second possibility)

**Output game state — Cases:**
- BLACK_TURN (first possibility)
- WHITE_TURN (second possibility)

### Step 4: Test Cases (Each-Choice Strategy)

- **TC46: SwitchTurn_FromWhiteTurn_GameStateIsBlackTurn** ( :white_check_mark: )
  - **Method(s) under test**: `switchTurn()`, `getCurrentGameState()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`; `currentGameState` is `WHITE_TURN`
  - **Covered by**: TC42
  - **Expected output**: after `switchTurn()`, `getCurrentGameState()` returns `BLACK_TURN`

- **TC47: SwitchTurn_FromBlackTurn_GameStateIsWhiteTurn** ( :white_check_mark: )
  - **Method(s) under test**: `switchTurn()`, `getCurrentGameState()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`; `switchTurn()` has been called once (state is `BLACK_TURN`)
  - **Expected output**: after a second `switchTurn()`, `getCurrentGameState()` returns `WHITE_TURN`

---

## Method: `getPieceAt(int rank, int file)`

### Step 1: Equivalence Classes

- **Input: rank** — which row (0–7) to access
- **Input: file** — which column (0–7) to access
- **Output: piece type at (rank, file)** — the type of the returned piece matches what was placed at that position
- **Output: piece color at (rank, file)** — the color of the returned piece matches what was placed at that position
- **Output: returned piece vs internal piece** — whether the returned piece is a defensive copy

### Step 2: Data Types (from BVA Catalog)

| Equivalence class                          | Catalog data type   | Parameters                                    |
| ------------------------------------------ | ------------------- | --------------------------------------------- |
| Input: rank                                | Interval            | [0, 7]                                        |
| Input: file                                | Interval            | [0, 7]                                        |
| Output: piece type at (rank, file)         | Cases               | ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN, NONE |
| Output: piece color at (rank, file)        | Cases               | BLACK, WHITE                                  |
| Output: returned piece vs internal piece   | Pairs of references | two references should refer to different objects with same contents |

### Step 3: Boundary Values (from BVA Catalog)

**Rank — Interval [0, 7]:**
- 0 (min), 7 (max)

**File — Interval [0, 7]:**
- 0 (min), 7 (max)

**Piece type — Cases:**
- ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN, NONE

**Piece color — Cases:**
- BLACK, WHITE

**Pairs of references:**
- Two references refer to different objects with the same contents (should happen)

### Step 4: Test Cases (Each-Choice Strategy)

- **TC48: GetPieceAt_WhenBoardHasRookAtPosition_PieceTypeMatches** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Rook(BLACK)` at `[0][0]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(0, 0).getType()` equals `ROOK` — covers rank 0 (min), file 0 (min)
  - **Note**: TC49–TC54 are covered by this test case as a parameterized test

- **TC49: GetPieceAt_WhenBoardHasKnightAtPosition_PieceTypeIsKnight** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Knight(BLACK)` at `[0][1]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(0, 1).getType()` equals `KNIGHT`
  - **Covered by**: TC48 (parameterized test)

- **TC50: GetPieceAt_WhenBoardHasBishopAtPosition_PieceTypeIsBishop** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Bishop(BLACK)` at `[0][2]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(0, 2).getType()` equals `BISHOP`
  - **Covered by**: TC48 (parameterized test)

- **TC51: GetPieceAt_WhenBoardHasQueenAtPosition_PieceTypeIsQueen** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Queen(BLACK)` at `[0][3]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(0, 3).getType()` equals `QUEEN`
  - **Covered by**: TC48 (parameterized test)

- **TC52: GetPieceAt_WhenBoardHasKingAtPosition_PieceTypeIsKing** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `King(BLACK)` at `[0][4]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(0, 4).getType()` equals `KING`
  - **Covered by**: TC48 (parameterized test)

- **TC53: GetPieceAt_WhenBoardHasPawnAtPosition_PieceTypeIsPawn** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Pawn(BLACK)` at `[1][0]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(1, 0).getType()` equals `PAWN`
  - **Covered by**: TC48 (parameterized test)

- **TC54: GetPieceAt_WhenBoardHasNonePieceAtPosition_PieceTypeIsNone** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `NonePiece` at `[7][0]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(7, 0).getType()` equals `NONE`
  - **Covered by**: TC48 (parameterized test)

- **TC55: GetPieceAt_AtRankZeroFileZero_PieceColorIsBlack** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Rook(BLACK)` at `[0][0]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(0, 0).getColor()` equals `BLACK`

- **TC56: GetPieceAt_AtRankSevenFileSeven_PieceColorIsWhite** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Rook(WHITE)` at `[7][7]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(7, 7).getColor()` equals `WHITE` — covers rank 7 (max), file 7 (max)

- **TC57: GetPieceAt_ReturnedPieceIsDifferentObject** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Rook(BLACK)` at `[0][0]`; `getPieceAt(0, 0)` called twice
  - **Expected output**: the two returned `Piece` references are not the same object, but have equal type and color

---

## Method: `makeMove(Move move)`

Scope: apply a **normal** move to internal board state, update `halfMoveClock`, and call `updateGameState()` to detect checkmate, stalemate, insufficient material, and the 50-move draw rule.

### Step 1: Equivalence Classes

- **Input: move** — `Move` with from/to locations and `MoveType.NORMAL`
- **Input: board state before move** — piece at source square; destination empty or capturable
- **Input: current game state** — `WHITE_TURN` or `BLACK_TURN`
- **Input: whether the moving piece is a pawn** — affects `halfMoveClock` reset
- **Input: whether the move is a capture** — affects `halfMoveClock` reset
- **Input: current `halfMoveClock` value** — Count with threshold at HIGH = 100
- **Output: piece at destination** — moved piece type and color match the piece that was at source
- **Output: piece at source** — `NonePiece` after move
- **Output: hasMoved flag on moving piece** — whether the piece now at the destination is marked as having moved
- **Output: `halfMoveClock` after move** — 0 if reset, prior value + 1 if incremented
- **Output: game state after move** — `WHITE_TURN`, `BLACK_TURN`, `WHITE_WIN`, `BLACK_WIN`, or `DRAW`

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: move from/to | Pairs of variables | file/rank in [0, 7] |
| Input: move type | Cases | NORMAL |
| Input: game state before | Cases | WHITE_TURN, BLACK_TURN |
| Input: pawn move | Boolean | true, false |
| Input: capture | Boolean | true, false |
| Input: halfMoveClock | Count | 0, 99, 100 |
| Output: destination piece type | Cases | PAWN, etc. |
| Output: source piece type | Cases | NONE |
| Output: hasMoved flag on moving piece | Boolean | true, false |
| Output: halfMoveClock | Count | 0, prior value + 1 |
| Output: game state after | Cases | WHITE_TURN, BLACK_TURN, WHITE_WIN, BLACK_WIN, DRAW |

### Step 3: Boundary Values (from BVA Catalog)

**move — Pairs of variables:**
- White pawn `(4,6)` → `(4,5)` on empty board
- Black pawn `(4,1)` → `(4,2)` on empty board

**game state — Cases:**
- WHITE_TURN, BLACK_TURN, WHITE_WIN, BLACK_WIN, DRAW

**isPawnMove — Boolean:**
- true, false

**isCapture — Boolean:**
- true, false

**halfMoveClock — Count:**
- 0, 99, 100 (threshold at HIGH = 100)

**hasMoved flag — Boolean:**
- true (piece is marked as moved after execution; only achievable post-condition)
- false is CAN'T SET as a post-condition of a normal move

### Step 4: Test Cases

- **TC58: MakeMove_OnNormalMove_PieceAtDestination** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getPieceAt(int, int)`
  - **State of the system**: white pawn at `(4,6)`, empty `(4,5)`, `WHITE_TURN`
  - **Expected output**: after move, `getPieceAt(5, 4)` returns type `PAWN` and color `WHITE`

- **TC59: MakeMove_OnNormalMove_MovingPieceIsMarkedAsMoved** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getPieceAt(int, int)`
  - **State of the system**: white pawn at `(4,6)`, empty `(4,5)`, `WHITE_TURN`
  - **Expected output**: after move, `getPieceAt(5, 4).hasMoved()` returns `true`

- **TC60: MakeMove_OnNormalMove_SourceSquareIsEmpty** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getPieceAt(int, int)`
  - **State of the system**: white pawn moves from `(4,6)` to `(4,5)`
  - **Expected output**: after move, `getPieceAt(6, 4)` returns type `NONE`

- **TC61: MakeMove_AfterWhiteMove_GameStateIsBlackTurn** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getCurrentGameState()`
  - **State of the system**: white pawn normal move on empty board; game state is `WHITE_TURN`
  - **Expected output**: after move, `getCurrentGameState()` returns `BLACK_TURN`

- **TC62: MakeMove_AfterBlackMove_GameStateIsWhiteTurn** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getCurrentGameState()`
  - **State of the system**: black pawn normal move on empty board; game state is `BLACK_TURN` (via prior `switchTurn()`)
  - **Expected output**: after move, `getCurrentGameState()` returns `WHITE_TURN`

- **TC63: MakeMove_WhenWhiteCausesCheckmate_GameStateIsWhiteWin** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getCurrentGameState()`
  - **State of the system**: white's NORMAL move leaves black in check with no legal moves
  - **Expected output**: `getCurrentGameState()` returns `WHITE_WIN`

- **TC64: MakeMove_WhenBlackCausesCheckmate_GameStateIsBlackWin** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getCurrentGameState()`
  - **State of the system**: game state is `BLACK_TURN`; black's NORMAL move leaves white in check with no legal moves
  - **Expected output**: `getCurrentGameState()` returns `BLACK_WIN`

- **TC65: MakeMove_WhenMoveCausesStalemate_GameStateIsDraw** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getCurrentGameState()`
  - **State of the system**: white's NORMAL move leaves black with no legal moves and not in check
  - **Expected output**: `getCurrentGameState()` returns `DRAW`

- **TC66: MakeMove_WhenOnlyKingsRemain_GameStateIsDraw** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getCurrentGameState()`
  - **State of the system**: white king and black king only; white knight captures the last non-king piece
  - **Expected output**: `getCurrentGameState()` returns `DRAW`

- **TC67: MakeMove_WhenHalfMoveClockReachesLimit_GameStateIsDraw** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getCurrentGameState()`, `getHalfMoveClock()`
  - **State of the system**: `halfMoveClock` set to 99; one non-pawn non-capture NORMAL move made
  - **Expected output**: `getHalfMoveClock()` returns 100 AND `getCurrentGameState()` returns `DRAW`

- **TC68: MakeMove_OnNonPawnNonCaptureMove_HalfMoveClockIncrements** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getHalfMoveClock()`
  - **State of the system**: `halfMoveClock` set to 0; white knight makes a NORMAL move to an empty square
  - **Expected output**: `getHalfMoveClock()` returns 1
  - **Note**: TC69–TC70 are covered by this test case as a parameterized test

- **TC69: MakeMove_OnPawnMove_HalfMoveClockResets** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getHalfMoveClock()`
  - **State of the system**: `halfMoveClock` set to 5; white pawn makes a one-step NORMAL move
  - **Expected output**: `getHalfMoveClock()` returns 0
  - **Covered by**: TC68 (parameterized test)

- **TC70: MakeMove_OnCapture_HalfMoveClockResets** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getHalfMoveClock()`
  - **State of the system**: `halfMoveClock` set to 5; white knight captures a black pawn
  - **Expected output**: `getHalfMoveClock()` returns 0
  - **Covered by**: TC68 (parameterized test)

---

---

## Method / behavior: `makeMove(Move move)` with en passant, castling, and promotion execution

Scope: execute `EN_PASSANT`, `CASTLING_KINGSIDE`/`CASTLING_QUEENSIDE`, and `PROMOTION` move types in board state, and maintain `enPassantTarget` so next-turn legal-move generation can include/exclude en passant correctly.

### Step 1: Equivalence Classes

- **Input: move type** — `EN_PASSANT`, `CASTLING_KINGSIDE`, `CASTLING_QUEENSIDE`, `PROMOTION`
- **Input: castling rook file** — carried by the castling move's `to` square (king-takes-rook encoding)
- **Output: en passant capture effect** — destination filled by mover; captured pawn square emptied
- **Output: hasMoved flag on capturing pawn (EN_PASSANT)** — the pawn that performed the capture is marked as having moved
- **Output: castling effect** — king and rook relocate to castling destination files
- **Output: hasMoved flag on king after castling** — the king is marked as having moved
- **Output: hasMoved flag on rook after castling** — the rook is marked as having moved
- **Output: enPassantTarget state** — set after two-step pawn move, cleared otherwise
- **Output: promotion execution** — pawn replaced by promoted piece type at the destination

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: move type | Cases | EN_PASSANT, CASTLING_KINGSIDE, CASTLING_QUEENSIDE, PROMOTION |
| Input: castling rook file (`move.getTo().getX()`) | Interval | queenside [0, kingFile−1] = [0, 3]; kingside [kingFile+1, 7] |
| Output: piece positions | Cases | expected squares occupied/empty |
| Output: hasMoved on capturing pawn (EN_PASSANT) | Boolean | true |
| Output: hasMoved on king after castling | Boolean | true |
| Output: hasMoved on rook after castling | Boolean | true |
| Output: enPassantTarget | Cases | target set, no target |
| Output: promotion | Cases | promoted piece at destination |

### Step 3: Boundary Values (from BVA Catalog)

**move type — Cases:**
- EN_PASSANT
- CASTLING_KINGSIDE
- CASTLING_QUEENSIDE
- PROMOTION

**piece positions — Cases:**
- expected squares occupied
- expected squares empty

**hasMoved flag — Boolean (applies to EN_PASSANT pawn and castling king/rook):**
- true (only achievable post-condition; `changeToMoved()` is always called)
- false is CAN'T SET as a post-condition of these move types

**castling rook file (from `move.getTo()`) — Interval:**
- queenside: 0 (min; TC77) and 3 (max = kingFile−1; TC80 — rook already on its destination)
- kingside: 7 (max; TC74) and 5 (min = kingFile+1, Chess960 adjacent swap; TC81)

**enPassantTarget — Cases:**
- target set
- no target

**promotion — Cases:**
- promoted piece at destination

### Step 4: Test Cases

- **TC71: MakeMove_OnEnPassantMove_DestinationHasMovingPawn** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getPieceAt(int, int)`
  - **State of the system**: white pawn at `(4,3)`, black pawn at `(5,3)`, move type `EN_PASSANT` from `(4,3)` to `(5,2)`
  - **Expected output**: destination `(5,2)` has a white pawn

- **TC72: MakeMove_OnEnPassantMove_CapturedPawnSquareIsEmpty** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getPieceAt(int, int)`
  - **State of the system**: same as TC71
  - **Expected output**: captured pawn square `(5,3)` is `NONE`

- **TC73: MakeMove_OnEnPassantMove_MovingPawnIsMarkedAsMoved** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getPieceAt(int, int)`
  - **State of the system**: same as TC71 — white pawn at `(4,3)`, black pawn at `(5,3)`, move type `EN_PASSANT` from `(4,3)` to `(5,2)`
  - **Expected output**: `getPieceAt(5, 2).hasMoved()` returns `true`

- **TC74: MakeMove_OnKingsideCastling_KingAndRookReachCastledSquares** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getPieceAt(int, int)`
  - **State of the system**: white king `(4,7)`, white rook `(7,7)`, move type `CASTLING_KINGSIDE` to rook square `(7,7)`
  - **Expected output**: king at `(6,7)` and rook at `(5,7)`

- **TC75: MakeMove_OnKingsideCastling_KingIsMarkedAsMoved** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getPieceAt(int, int)`
  - **State of the system**: white king `(4,7)`, white rook `(7,7)`, move type `CASTLING_KINGSIDE` to rook square `(7,7)`
  - **Expected output**: `getPieceAt(7, 6).hasMoved()` returns `true`

- **TC76: MakeMove_OnKingsideCastling_RookIsMarkedAsMoved** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getPieceAt(int, int)`
  - **State of the system**: white king `(4,7)`, white rook `(7,7)`, move type `CASTLING_KINGSIDE` to rook square `(7,7)`
  - **Expected output**: `getPieceAt(7, 5).hasMoved()` returns `true`

- **TC77: MakeMove_OnQueensideCastling_KingAndRookReachCastledSquares** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getPieceAt(int, int)`
  - **State of the system**: white king `(4,7)`, white rook `(0,7)`, move type `CASTLING_QUEENSIDE` to rook square `(0,7)`
  - **Expected output**: king at `(2,7)` and rook at `(3,7)`

- **TC78: MakeMove_OnQueensideCastling_KingIsMarkedAsMoved** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getPieceAt(int, int)`
  - **State of the system**: white king `(4,7)`, white rook `(0,7)`, move type `CASTLING_QUEENSIDE` to rook square `(0,7)`
  - **Expected output**: `getPieceAt(7, 2).hasMoved()` returns `true`

- **TC79: MakeMove_OnQueensideCastling_RookIsMarkedAsMoved** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getPieceAt(int, int)`
  - **State of the system**: white king `(4,7)`, white rook `(0,7)`, move type `CASTLING_QUEENSIDE` to rook square `(0,7)`
  - **Expected output**: `getPieceAt(7, 3).hasMoved()` returns `true`

- **TC80: MakeMove_OnQueensideCastlingWithRookAtFileThree_KingAndRookReachCastledSquares** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getPieceAt(int, int)`
  - **State of the system**: white king `(4,7)`, white rook at `(3,7)` (file 3 = max of queenside interval; rook already on its destination), move type `CASTLING_QUEENSIDE` to rook square `(3,7)`
  - **Expected output**: king at `(2,7)` and rook at `(3,7)` (rook stays in place)

- **TC81: MakeMove_OnAdjacentKingsideCastling_SwapsKingAndRook** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getPieceAt(int, int)`
  - **State of the system**: white king `(5,7)`, white rook `(6,7)` (Chess960 adjacent; rook file 5+1 = min of kingside interval), move type `CASTLING_KINGSIDE` to rook square `(6,7)`
  - **Expected output**: king at `(6,7)` and rook at `(5,7)`

- **TC82: MakeMove_OnTwoStepPawnMove_SetsEnPassantTargetForOpponentCapture** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getEnPassantTarget()`
  - **State of the system**: only white pawn at `(4,6)`; white pawn double-steps to `(4,4)`
  - **Expected output**: `getEnPassantTarget()` is present at `(4,5)` (passed-over square for a later en-passant capture)

- **TC83: MakeMove_OnNonDoubleStepMove_ClearsEnPassantTarget** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getLegalMoves(Location)`
  - **State of the system**: board starts with en-passant target set to `(4,5)`; then white knight makes a normal move
  - **Expected output**: adjacent black pawn legal moves include no `EN_PASSANT` move

- **TC84: MakeMove_OnPromotionMove_PromotedPieceAtDestinationIsQueen** ( :white_check_mark: )
  - **Method(s) under test**: `makeMove(Move)`, `getPieceAt(int, int)`
  - **State of the system**: white pawn at `(4,1)`, move type `PROMOTION` to `(4,0)` with no promotion type specified
  - **Expected output**: `getPieceAt(0, 4).getType()` equals `QUEEN`

---

## Method: `updateGameState(PieceColor justMovedColor)` (package-private)

### Step 1: Equivalence Classes

- **Input: color of the player who just moved**
- **Input: whether the next player is in check after the move**
- **Input: whether the next player has any legal moves after the move**
- **Input: whether remaining pieces are insufficient to force checkmate**
- **Input: current value of `halfMoveClock` after clock update**
- **Output: `currentGameState` after the call**

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: justMovedColor | Cases | WHITE, BLACK |
| Input: next player in check | Boolean | true, false |
| Input: next player has legal moves | Boolean | true, false |
| Input: isInsufficientMaterial | Boolean | true, false |
| Input: halfMoveClock | Count | 99, 100 |
| Output: currentGameState | Cases | WHITE_WIN, BLACK_WIN, DRAW, WHITE_TURN, BLACK_TURN |

### Step 3: Boundary Values (from BVA Catalog)

**justMovedColor — Cases:**
- WHITE
- BLACK

**next player in check — Boolean:**
- true
- false

**next player has legal moves — Boolean:**
- true
- false

**isInsufficientMaterial — Boolean:**
- true
- false

**halfMoveClock — Count:**
- 0
- 1
- 99
- 100

**currentGameState — Cases:**
- WHITE_WIN
- BLACK_WIN
- DRAW
- WHITE_TURN
- BLACK_TURN

### Step 4: Test Cases (Each-Choice Strategy)

- **TC85: UpdateGameState_WhenNextHasNoMovesAndIsInCheckAndJustMovedIsWhite_GameStateIsWhiteWin** ( :white_check_mark: )
  - **Method(s) under test**: `updateGameState(PieceColor)`
  - **State of the system**: board where black has no legal moves and is in check; call `updateGameState(WHITE)`
  - **Expected output**: `currentGameState` = `WHITE_WIN`
  - **Note**: TC86 is covered by this test case as a parameterized test

- **TC86: UpdateGameState_WhenNextHasNoMovesAndIsInCheckAndJustMovedIsBlack_GameStateIsBlackWin** ( :white_check_mark: )
  - **Method(s) under test**: `updateGameState(PieceColor)`
  - **State of the system**: board where white has no legal moves and is in check; call `updateGameState(BLACK)`
  - **Expected output**: `currentGameState` = `BLACK_WIN`
  - **Covered by**: TC85 (parameterized test)

- **TC87: UpdateGameState_WhenNextHasNoMovesAndNotInCheck_GameStateIsDraw** ( :white_check_mark: )
  - **Method(s) under test**: `updateGameState(PieceColor)`
  - **State of the system**: board where black has no legal moves and is not in check; call `updateGameState(WHITE)`
  - **Expected output**: `currentGameState` = `DRAW`

- **TC88: UpdateGameState_WhenInsufficientMaterial_GameStateIsDraw** ( :white_check_mark: )
  - **Method(s) under test**: `updateGameState(PieceColor)`
  - **State of the system**: board has only two kings and black has legal moves; call `updateGameState(WHITE)`
  - **Expected output**: `currentGameState` = `DRAW`

- **TC89: UpdateGameState_WhenHalfMoveClockAtLimit_GameStateIsDraw** ( :white_check_mark: )
  - **Method(s) under test**: `updateGameState(PieceColor)`
  - **State of the system**: board with legal moves for next player; `halfMoveClock` = 100; call `updateGameState(WHITE)`
  - **Expected output**: `currentGameState` = `DRAW`

- **TC90: UpdateGameState_WhenHalfMoveClockBelowLimit_GameStateIsNextTurn** ( :white_check_mark: )
  - **Method(s) under test**: `updateGameState(PieceColor)`
  - **State of the system**: board with legal moves; `halfMoveClock` = 99; call `updateGameState(WHITE)`
  - **Expected output**: `currentGameState` = `BLACK_TURN`

- **TC91: UpdateGameState_WhenJustMovedWhiteAndGameContinues_GameStateIsBlackTurn** ( :white_check_mark: )
  - **Method(s) under test**: `updateGameState(PieceColor)`
  - **State of the system**: board with legal moves for black; `halfMoveClock` = 0; not insufficient material; call `updateGameState(WHITE)`
  - **Expected output**: `currentGameState` = `BLACK_TURN`
  - **Note**: TC92 is covered by this test case as a parameterized test

- **TC92: UpdateGameState_WhenJustMovedBlackAndGameContinues_GameStateIsWhiteTurn** ( :white_check_mark: )
  - **Method(s) under test**: `updateGameState(PieceColor)`
  - **State of the system**: board with legal moves for white; `halfMoveClock` = 1; not insufficient material; call `updateGameState(BLACK)`
  - **Expected output**: `currentGameState` = `WHITE_TURN`
  - **Covered by**: TC91 (parameterized test)

---

## Method: `isInsufficientMaterial()` (package-private)

### Step 1: Equivalence Classes

- **Input: count of non-king pieces on the board**
- **Input: whether any non-king piece is a pawn, rook, or queen**
- **Output: whether material is insufficient to force checkmate**

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: non-king piece count | Count | 0, 1, 2 |
| Input: hasMajorOrPawn | Boolean | true, false |
| Output: return value | Boolean | true, false |

Logic: returns `true` iff `!hasMajorOrPawn && nonKingCount <= 1`.

### Step 3: Boundary Values (from BVA Catalog)

**non-king piece count — Count:**
- 0
- 1
- 2

**hasMajorOrPawn — Boolean:**
- true
- false

**return value — Boolean:**
- true
- false

### Step 4: Test Cases (Each-Choice Strategy)

- **TC93: IsInsufficientMaterial_WithOnlyKings_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isInsufficientMaterial()`
  - **State of the system**: board with one white king and one black king
  - **Expected output**: `isInsufficientMaterial()` returns `true`
  - **Note**: TC94–TC97 are covered by this test case as a parameterized test

- **TC94: IsInsufficientMaterial_WithKingAndBishopVsKing_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isInsufficientMaterial()`
  - **State of the system**: white king, white bishop, black king
  - **Expected output**: `isInsufficientMaterial()` returns `true`
  - **Covered by**: TC93 (parameterized test)

- **TC95: IsInsufficientMaterial_WithKingAndKnightVsKing_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isInsufficientMaterial()`
  - **State of the system**: white king, white knight, black king
  - **Expected output**: `isInsufficientMaterial()` returns `true`
  - **Covered by**: TC93 (parameterized test)

- **TC96: IsInsufficientMaterial_WithKingAndPawnVsKing_ReturnsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `isInsufficientMaterial()`
  - **State of the system**: white king, white pawn, black king
  - **Expected output**: `isInsufficientMaterial()` returns `false`
  - **Covered by**: TC93 (parameterized test)

- **TC97: IsInsufficientMaterial_WithKingAndTwoBishopsVsKing_ReturnsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `isInsufficientMaterial()`
  - **State of the system**: white king, white bishop, black bishop, black king
  - **Expected output**: `isInsufficientMaterial()` returns `false`
  - **Covered by**: TC93 (parameterized test)

---

## Method: `isCapture(Move move)` (package-private)

### Step 1: Equivalence Classes

- **Input: move type** — `EN_PASSANT` or other
- **Input: piece type at the destination square** — `NONE` or non-`NONE`
- **Output: whether the move is a capture**

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: move type | Cases | EN_PASSANT, NORMAL |
| Input: destination piece type | Cases | NONE, non-NONE |
| Output: return value | Boolean | true, false |

### Step 3: Boundary Values (from BVA Catalog)

**move type — Cases:**
- EN_PASSANT
- NORMAL

**destination piece type — Cases:**
- NONE
- non-NONE

**return value — Boolean:**
- true
- false

### Step 4: Test Cases (Each-Choice Strategy)

- **TC98: IsCapture_OnEnPassantMove_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isCapture(Move)`
  - **State of the system**: white pawn at `(4,3)`, black pawn at `(5,3)`; EN_PASSANT move from `(4,3)` to `(5,2)`
  - **Expected output**: `isCapture(move)` returns `true`
  - **Note**: TC99–TC100 are covered by this test case as a parameterized test

- **TC99: IsCapture_OnNormalMoveToEmptySquare_ReturnsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `isCapture(Move)`
  - **State of the system**: white pawn at `(4,6)`, empty destination `(4,5)`; NORMAL move
  - **Expected output**: `isCapture(move)` returns `false`
  - **Covered by**: TC98 (parameterized test)

- **TC100: IsCapture_OnNormalMoveToOccupiedSquare_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isCapture(Move)`
  - **State of the system**: white knight at `(3,5)`, black pawn at `(4,3)`; NORMAL move from `(3,5)` to `(4,3)`
  - **Expected output**: `isCapture(move)` returns `true`
  - **Covered by**: TC98 (parameterized test)

---

## Method: `currentPlayerColor()` (package-private)

### Step 1: Equivalence Classes

- **Input: `currentGameState`** — which turn it is
- **Output: `PieceColor`** — the player whose turn it is

Terminal states are impossible inputs: `currentPlayerColor()` is only called at the start of `makeMove()`.

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: currentGameState | Cases | WHITE_TURN, BLACK_TURN |
| Output: PieceColor | Cases | WHITE, BLACK |

### Step 3: Boundary Values (from BVA Catalog)

**currentGameState — Cases:**
- WHITE_TURN
- BLACK_TURN

**PieceColor — Cases:**
- WHITE
- BLACK

### Step 4: Test Cases (Each-Choice Strategy)

- **TC101: CurrentPlayerColor_WhenWhiteTurn_ReturnsWhite** ( :white_check_mark: )
  - **Method(s) under test**: `currentPlayerColor()`
  - **State of the system**: board freshly constructed; state is `WHITE_TURN`
  - **Expected output**: `currentPlayerColor()` returns `WHITE`
  - **Note**: TC102 is covered by this test case as a parameterized test

- **TC102: CurrentPlayerColor_WhenBlackTurn_ReturnsBlack** ( :white_check_mark: )
  - **Method(s) under test**: `currentPlayerColor()`
  - **State of the system**: board with state set to `BLACK_TURN` via `switchTurn()`
  - **Expected output**: `currentPlayerColor()` returns `BLACK`
  - **Covered by**: TC101 (parameterized test)
