# BVA Analysis for Board

## Method: `Board(BoardInitializer)`

### Step 1: Equivalence Classes

- **Input: piece type returned by initializer** — the `PieceType` at each position in the layout
- **Input: position half** — whether the position is in the top half (rows 0–3) or bottom half (rows 4–7)
- **Output: piece type at position** — the type of the `Piece` placed on the board
- **Output: piece color at an occupied position** — the color assigned to the piece
- **Output: initial game state** — the game state immediately after construction

### Step 2: Data Types (from BVA Catalog)

| Equivalence class                              | Catalog data type | Parameters                                    |
| ---------------------------------------------- | ----------------- | --------------------------------------------- |
| Input: piece type returned by initializer      | Cases             | ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN, NONE |
| Input: position half                           | Cases             | top (rows 0–3), bottom (rows 4–7)             |
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

**Position half — Cases:**
- top (rows 0–3)
- bottom (rows 4–7)

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

- **TC11: Constructor_OnNewBoard_GameStateIsWhiteTurn** ( :white_check_mark: )
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

- **TC12: `Constructor_WhenPieceArrayHasRookAtPosition_PieceTypeIsRook`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Rook(BLACK)` at `[0][0]`; all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[0][0].getType()` equals `ROOK`
  - **Note**: TC13–TC18 and TC22 are covered by this test case as a parameterized test

- **TC13: `Constructor_WhenPieceArrayHasKnightAtPosition_PieceTypeIsKnight`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Knight(BLACK)` at `[0][1]`; all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[0][1].getType()` equals `KNIGHT`
  - **Covered by**: TC12 (parameterized test)

- **TC14: `Constructor_WhenPieceArrayHasBishopAtPosition_PieceTypeIsBishop`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Bishop(BLACK)` at `[0][2]`; all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[0][2].getType()` equals `BISHOP`
  - **Covered by**: TC12 (parameterized test)

- **TC15: `Constructor_WhenPieceArrayHasQueenAtPosition_PieceTypeIsQueen`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Queen(BLACK)` at `[0][3]`; all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[0][3].getType()` equals `QUEEN`
  - **Covered by**: TC12 (parameterized test)

- **TC16: `Constructor_WhenPieceArrayHasKingAtPosition_PieceTypeIsKing`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `King(BLACK)` at `[0][4]`; all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[0][4].getType()` equals `KING`
  - **Covered by**: TC12 (parameterized test)

- **TC17: `Constructor_WhenPieceArrayHasPawnAtPosition_PieceTypeIsPawn`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Pawn(BLACK)` at `[1][0]`; all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[1][0].getType()` equals `PAWN`
  - **Covered by**: TC12 (parameterized test)

- **TC18: `Constructor_WhenPieceArrayHasNonePieceAtPosition_PieceTypeIsNone`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `NonePiece` at `[7][0]`; all other positions `NonePiece`; board is constructed — covers row 7 (max)
  - **Expected output**: `getSnapshot()[7][0].getType()` equals `NONE`
  - **Covered by**: TC12 (parameterized test)

- **TC19: `Constructor_WhenPieceArrayHasBlackPieceAtPosition_PieceColorIsBlack`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Rook(BLACK)` at `[7][0]` (bottom half, which `Board(BoardInitializer)` would assign `WHITE`); all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[7][0].getColor()` equals `BLACK`

- **TC20: `Constructor_WhenPieceArrayHasWhitePieceAtPosition_PieceColorIsWhite`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Rook(WHITE)` at `[0][0]` (top half, which `Board(BoardInitializer)` would assign `BLACK`); all other positions `NonePiece`; board is constructed
  - **Expected output**: `getSnapshot()[0][0].getColor()` equals `WHITE`

- **TC21: `Constructor_WithPieceArray_OnNewBoard_GameStateIsWhiteTurn`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: board constructed with an all-`NonePiece` `Piece[][]`
  - **Expected output**: `getCurrentGameState()` returns `WHITE_TURN`

- **TC22: `Constructor_WhenPieceArrayHasPieceAtRowZeroColSeven_PieceTypeMatches`** ( :white_check_mark: )
  - **Method(s) under test**: `Board(Piece[][])`
  - **State of the system**: `Piece[][]` with a `Rook(BLACK)` at `[0][7]`; all other positions `NonePiece`; board is constructed — covers col 7 (max)
  - **Expected output**: `getSnapshot()[0][7].getType()` equals `ROOK`
  - **Covered by**: TC12 (parameterized test)

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

- **TC23: CreatePiece_WithRookAndBlack_ReturnsPieceWithTypeRookAndColorBlack** ( :white_check_mark: )
  - **Method(s) under test**: `createPiece(PieceType, PieceColor)` via `Board(BoardInitializer)`
  - **State of the system**: initializer returns ROOK at a top-half position
  - **Expected output**: piece has type ROOK and color BLACK
  - **Covered by**: TC2 + TC9

- **TC24: CreatePiece_WithKnight_ReturnsPieceWithTypeKnight** ( :white_check_mark: )
  - **Covered by**: TC3

- **TC25: CreatePiece_WithBishop_ReturnsPieceWithTypeBishop** ( :white_check_mark: )
  - **Covered by**: TC4

- **TC26: CreatePiece_WithQueen_ReturnsPieceWithTypeQueen** ( :white_check_mark: )
  - **Covered by**: TC5

- **TC27: CreatePiece_WithKing_ReturnsPieceWithTypeKing** ( :white_check_mark: )
  - **Covered by**: TC6

- **TC28: CreatePiece_WithPawn_ReturnsPieceWithTypePawn** ( :white_check_mark: )
  - **Covered by**: TC7

- **TC29: CreatePiece_WithNone_ReturnsPieceWithTypeNone** ( :white_check_mark: )
  - **Covered by**: TC8

- **TC30: CreatePiece_WithWhite_ReturnsPieceWithColorWhite** ( :white_check_mark: )
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

- **TC31: GetSnapshot_ReturnedOuterArrayIsDifferentObject** ( :white_check_mark: )
  - **Method(s) under test**: `getSnapshot()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`
  - **Expected output**: the returned `Piece[][]` reference is not the same object as a second call to `getSnapshot()`

- **TC32: GetSnapshot_ReturnedRowArrayIsDifferentObject** ( :white_check_mark: )
  - **Method(s) under test**: `getSnapshot()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`
  - **Expected output**: `getSnapshot()[0]` is not the same array reference as a second `getSnapshot()[0]`

- **TC33: GetSnapshot_ReturnedPieceIsDifferentObjectWithSameContents** ( :white_check_mark: )
  - **Method(s) under test**: `getSnapshot()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`
  - **Expected output**: the Piece object at `getSnapshot()[0][0]` is not the same reference as the Piece at a second `getSnapshot()[0][0]`, but has the same type and color

- **TC34: GetSnapshot_SnapshotContentMatchesBoardState** ( :white_check_mark: )
  - **Method(s) under test**: `getSnapshot()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`
  - **Expected output**: `getSnapshot()[7][4]` has type `KING` and color `WHITE`

- **TC35: GetSnapshot_ModifySnapshotDoesNotAffectBoard** ( :white_check_mark: )
  - **Method(s) under test**: `getSnapshot()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`; set `getSnapshot()[7][0]` to `null`
  - **Expected output**: a subsequent `getSnapshot()[7][0]` still has type `ROOK` and color `WHITE`

---

## Method: `getCurrentGameState()`

### Step 1: Equivalence Classes

- **Output: the returned game state** — which `GameState` enum value is returned

### Step 2: Data Types (from BVA Catalog)

| Equivalence class           | Catalog data type | Parameters             |
| --------------------------- | ----------------- | ---------------------- |
| Output: returned game state | Cases             | WHITE_TURN, BLACK_TURN |

Note: `WHITE_WIN`, `BLACK_WIN`, and `DRAW` are reachable only through game-logic methods (`movePiece`), which are excluded from this BVA's scope.

### Step 3: Boundary Values (from BVA Catalog)

**Game state — Cases:**
- WHITE_TURN (first possibility)
- BLACK_TURN (second possibility)

### Step 4: Test Cases (Each-Choice Strategy)

- **TC36: GetCurrentGameState_OnNewBoard_ReturnsWhiteTurn** ( :white_check_mark: )
  - **Method(s) under test**: `getCurrentGameState()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`
  - **Expected output**: returns `WHITE_TURN`
  - **Covered by**: TC11

- **TC37: GetCurrentGameState_AfterSwitchTurn_ReturnsBlackTurn** ( :white_check_mark: )
  - **Method(s) under test**: `getCurrentGameState()`, `switchTurn()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`; `switchTurn()` called once
  - **Expected output**: returns `BLACK_TURN`

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

- **TC38: SwitchTurn_FromWhiteTurn_GameStateIsBlackTurn** ( :white_check_mark: )
  - **Method(s) under test**: `switchTurn()`, `getCurrentGameState()`
  - **State of the system**: a board constructed with `StandardBoardInitializer`; `currentGameState` is `WHITE_TURN`
  - **Covered by**: TC37
  - **Expected output**: after `switchTurn()`, `getCurrentGameState()` returns `BLACK_TURN`

- **TC39: SwitchTurn_FromBlackTurn_GameStateIsWhiteTurn** ( :white_check_mark: )
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

- **TC40: GetPieceAt_WhenBoardHasRookAtPosition_PieceTypeMatches** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Rook(BLACK)` at `[0][0]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(0, 0).getType()` equals `ROOK` — covers rank 0 (min), file 0 (min)
  - **Note**: TC41–TC46 are covered by this test case as a parameterized test

- **TC41: GetPieceAt_WhenBoardHasKnightAtPosition_PieceTypeIsKnight** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Knight(BLACK)` at `[0][1]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(0, 1).getType()` equals `KNIGHT`
  - **Covered by**: TC40 (parameterized test)

- **TC42: GetPieceAt_WhenBoardHasBishopAtPosition_PieceTypeIsBishop** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Bishop(BLACK)` at `[0][2]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(0, 2).getType()` equals `BISHOP`
  - **Covered by**: TC40 (parameterized test)

- **TC43: GetPieceAt_WhenBoardHasQueenAtPosition_PieceTypeIsQueen** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Queen(BLACK)` at `[0][3]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(0, 3).getType()` equals `QUEEN`
  - **Covered by**: TC40 (parameterized test)

- **TC44: GetPieceAt_WhenBoardHasKingAtPosition_PieceTypeIsKing** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `King(BLACK)` at `[0][4]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(0, 4).getType()` equals `KING`
  - **Covered by**: TC40 (parameterized test)

- **TC45: GetPieceAt_WhenBoardHasPawnAtPosition_PieceTypeIsPawn** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Pawn(BLACK)` at `[1][0]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(1, 0).getType()` equals `PAWN`
  - **Covered by**: TC40 (parameterized test)

- **TC46: GetPieceAt_WhenBoardHasNonePieceAtPosition_PieceTypeIsNone** ( :white_check_mark: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `NonePiece` at `[7][0]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(7, 0).getType()` equals `NONE`
  - **Covered by**: TC40 (parameterized test)

- **TC47: GetPieceAt_AtRankZeroFileZero_PieceColorIsBlack** ( :x: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Rook(BLACK)` at `[0][0]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(0, 0).getColor()` equals `BLACK`

- **TC48: GetPieceAt_AtRankSevenFileSeven_PieceColorIsWhite** ( :x: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Rook(WHITE)` at `[7][7]`; all other positions `NonePiece`
  - **Expected output**: `getPieceAt(7, 7).getColor()` equals `WHITE` — covers rank 7 (max), file 7 (max)

- **TC49: GetPieceAt_ReturnedPieceIsDifferentObject** ( :x: )
  - **Method(s) under test**: `getPieceAt(int rank, int file)`
  - **State of the system**: board constructed with `Rook(BLACK)` at `[0][0]`; `getPieceAt(0, 0)` called twice
  - **Expected output**: the two returned `Piece` references are not the same object, but have equal type and color

---
