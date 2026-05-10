# BVA Analysis for Board

## Method: `Board()`

### Step 1: Equivalence Classes

- **Output: piece type at each black back rank position** — the piece type placed at each column of row 0
- **Output: piece type at each black pawn rank position** — the piece type placed at each column of row 1
- **Output: content at each empty position** — the value at positions in rows 2–5
- **Output: piece type at each white pawn rank position** — the piece type placed at each column of row 6
- **Output: piece type at each white back rank position** — the piece type placed at each column of row 7
- **Output: piece color at an occupied position** — the color of the piece at a given occupied board position
- **Output: initial game state** — the game state immediately after construction

### Step 2: Data Types (from BVA Catalog)

| Equivalence class                                    | Catalog data type | Parameters                                                                                                             |
| ---------------------------------------------------- | ----------------- | ---------------------------------------------------------------------------------------------------------------------- |
| Output: piece type at each black back rank position  | Cases             | `[0][0]`=ROOK, `[0][1]`=KNIGHT, `[0][2]`=BISHOP, `[0][3]`=QUEEN, `[0][4]`=KING, `[0][5]`=BISHOP, `[0][6]`=KNIGHT, `[0][7]`=ROOK |
| Output: piece type at each black pawn rank position  | Cases             | `[1][0]`=PAWN, `[1][1]`=PAWN, `[1][2]`=PAWN, `[1][3]`=PAWN, `[1][4]`=PAWN, `[1][5]`=PAWN, `[1][6]`=PAWN, `[1][7]`=PAWN         |
| Output: content at each empty position               | Cases             | `null`                                                                                                                 |
| Output: piece type at each white pawn rank position  | Cases             | `[6][0]`=PAWN, `[6][1]`=PAWN, `[6][2]`=PAWN, `[6][3]`=PAWN, `[6][4]`=PAWN, `[6][5]`=PAWN, `[6][6]`=PAWN, `[6][7]`=PAWN         |
| Output: piece type at each white back rank position  | Cases             | `[7][0]`=ROOK, `[7][1]`=KNIGHT, `[7][2]`=BISHOP, `[7][3]`=QUEEN, `[7][4]`=KING, `[7][5]`=BISHOP, `[7][6]`=KNIGHT, `[7][7]`=ROOK |
| Output: piece color at an occupied position          | Cases             | BLACK, WHITE                                                                                                           |
| Output: initial game state                           | Cases             | WHITE_TURN                                                                                                             |

### Step 3: Boundary Values (from BVA Catalog)

**Black back rank (row 0) — Cases:**
- `[0][0]` = ROOK
- `[0][1]` = KNIGHT
- `[0][2]` = BISHOP
- `[0][3]` = QUEEN
- `[0][4]` = KING
- `[0][5]` = BISHOP
- `[0][6]` = KNIGHT
- `[0][7]` = ROOK

**Black pawn rank (row 1) — Cases:**
- `[1][0]` = PAWN
- `[1][1]` = PAWN
- `[1][2]` = PAWN
- `[1][3]` = PAWN
- `[1][4]` = PAWN
- `[1][5]` = PAWN
- `[1][6]` = PAWN
- `[1][7]` = PAWN

**Empty positions (rows 2–5) — Cases:**
- `null`

**White pawn rank (row 6) — Cases:**
- `[6][0]` = PAWN
- `[6][1]` = PAWN
- `[6][2]` = PAWN
- `[6][3]` = PAWN
- `[6][4]` = PAWN
- `[6][5]` = PAWN
- `[6][6]` = PAWN
- `[6][7]` = PAWN

**White back rank (row 7) — Cases:**
- `[7][0]` = ROOK
- `[7][1]` = KNIGHT
- `[7][2]` = BISHOP
- `[7][3]` = QUEEN
- `[7][4]` = KING
- `[7][5]` = BISHOP
- `[7][6]` = KNIGHT
- `[7][7]` = ROOK

**Piece color — Cases:**
- BLACK
- WHITE

**Game state — Cases:**
- WHITE_TURN

### Step 4: Test Cases (Each-Choice Strategy)

Each boundary value from Step 3 appears in at least one test case.

- **TC1: Constructor_OnNewBoard_TypeAtRow0Col0IsRook** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[0][0]` has type `ROOK`
  - **Note**: TC2–TC33 are covered by this test case as a parameterized test

- **TC2: Constructor_OnNewBoard_TypeAtRow0Col1IsKnight** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[0][1]` has type `KNIGHT`
  - **Covered by**: TC1 (parameterized test)

- **TC3: Constructor_OnNewBoard_TypeAtRow0Col2IsBishop** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[0][2]` has type `BISHOP`
  - **Covered by**: TC1 (parameterized test)

- **TC4: Constructor_OnNewBoard_TypeAtRow0Col3IsQueen** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[0][3]` has type `QUEEN`
  - **Covered by**: TC1 (parameterized test)

- **TC5: Constructor_OnNewBoard_TypeAtRow0Col4IsKing** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[0][4]` has type `KING`
  - **Covered by**: TC1 (parameterized test)

- **TC6: Constructor_OnNewBoard_TypeAtRow0Col5IsBishop** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[0][5]` has type `BISHOP`
  - **Covered by**: TC1 (parameterized test)

- **TC7: Constructor_OnNewBoard_TypeAtRow0Col6IsKnight** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[0][6]` has type `KNIGHT`
  - **Covered by**: TC1 (parameterized test)

- **TC8: Constructor_OnNewBoard_TypeAtRow0Col7IsRook** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[0][7]` has type `ROOK`
  - **Covered by**: TC1 (parameterized test)

- **TC9: Constructor_OnNewBoard_TypeAtRow1Col0IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[1][0]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC10: Constructor_OnNewBoard_TypeAtRow1Col1IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[1][1]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC11: Constructor_OnNewBoard_TypeAtRow1Col2IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[1][2]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC12: Constructor_OnNewBoard_TypeAtRow1Col3IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[1][3]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC13: Constructor_OnNewBoard_TypeAtRow1Col4IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[1][4]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC14: Constructor_OnNewBoard_TypeAtRow1Col5IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[1][5]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC15: Constructor_OnNewBoard_TypeAtRow1Col6IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[1][6]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC16: Constructor_OnNewBoard_TypeAtRow1Col7IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[1][7]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC17: Constructor_OnNewBoard_PositionAtRow3Col0IsNull** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: position `[3][0]` is `null`
  - **Covered by**: TC1 (parameterized test)

- **TC18: Constructor_OnNewBoard_TypeAtRow6Col0IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[6][0]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC19: Constructor_OnNewBoard_TypeAtRow6Col1IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[6][1]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC20: Constructor_OnNewBoard_TypeAtRow6Col2IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[6][2]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC21: Constructor_OnNewBoard_TypeAtRow6Col3IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[6][3]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC22: Constructor_OnNewBoard_TypeAtRow6Col4IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[6][4]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC23: Constructor_OnNewBoard_TypeAtRow6Col5IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[6][5]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC24: Constructor_OnNewBoard_TypeAtRow6Col6IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[6][6]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC25: Constructor_OnNewBoard_TypeAtRow6Col7IsPawn** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[6][7]` has type `PAWN`
  - **Covered by**: TC1 (parameterized test)

- **TC26: Constructor_OnNewBoard_TypeAtRow7Col0IsRook** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[7][0]` has type `ROOK`
  - **Covered by**: TC1 (parameterized test)

- **TC27: Constructor_OnNewBoard_TypeAtRow7Col1IsKnight** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[7][1]` has type `KNIGHT`
  - **Covered by**: TC1 (parameterized test)

- **TC28: Constructor_OnNewBoard_TypeAtRow7Col2IsBishop** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[7][2]` has type `BISHOP`
  - **Covered by**: TC1 (parameterized test)

- **TC29: Constructor_OnNewBoard_TypeAtRow7Col3IsQueen** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[7][3]` has type `QUEEN`
  - **Covered by**: TC1 (parameterized test)

- **TC30: Constructor_OnNewBoard_TypeAtRow7Col4IsKing** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[7][4]` has type `KING`
  - **Covered by**: TC1 (parameterized test)

- **TC31: Constructor_OnNewBoard_TypeAtRow7Col5IsBishop** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[7][5]` has type `BISHOP`
  - **Covered by**: TC1 (parameterized test)

- **TC32: Constructor_OnNewBoard_TypeAtRow7Col6IsKnight** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[7][6]` has type `KNIGHT`
  - **Covered by**: TC1 (parameterized test)

- **TC33: Constructor_OnNewBoard_TypeAtRow7Col7IsRook** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[7][7]` has type `ROOK`
  - **Covered by**: TC1 (parameterized test)

- **TC34: Constructor_OnNewBoard_ColorAtRow0Col0IsBlack** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[0][0]` has color `BLACK`
  - **Note**: TC35 is covered by this test case as a parameterized test

- **TC35: Constructor_OnNewBoard_ColorAtRow7Col0IsWhite** ( :x: )
  - **Method(s) under test**: `Board()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: piece at `[7][0]` has color `WHITE`
  - **Covered by**: TC34 (parameterized test)

- **TC36: Constructor_OnNewBoard_GameStateIsWhiteTurn** ( :x: )
  - **Method(s) under test**: `Board()`, `getCurrentGameState()`
  - **State of the system**: no existing board; call constructor
  - **Expected output**: `getCurrentGameState()` returns `WHITE_TURN`

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

- **TC37: GetSnapshot_ReturnedOuterArrayIsDifferentObject** ( :white_check_mark: )
  - **Method(s) under test**: `getSnapshot()`
  - **State of the system**: a freshly constructed board
  - **Expected output**: the returned `Piece[][]` reference is not the same object as a second call to `getSnapshot()`

- **TC38: GetSnapshot_ReturnedRowArrayIsDifferentObject** ( :white_check_mark: )
  - **Method(s) under test**: `getSnapshot()`
  - **State of the system**: a freshly constructed board
  - **Expected output**: `getSnapshot()[0]` is not the same array reference as a second `getSnapshot()[0]`

- **TC39: GetSnapshot_ReturnedPieceIsDifferentObjectWithSameContents** ( :white_check_mark: )
  - **Method(s) under test**: `getSnapshot()`
  - **State of the system**: a freshly constructed board
  - **Expected output**: the Piece object at `getSnapshot()[0][0]` is not the same reference as the Piece at a second `getSnapshot()[0][0]`, but has the same type and color

- **TC40: GetSnapshot_SnapshotContentMatchesBoardState** ( :white_check_mark: )
  - **Method(s) under test**: `getSnapshot()`
  - **State of the system**: a freshly constructed board
  - **Expected output**: `getSnapshot()[7][4]` has type `KING` and color `WHITE`

- **TC41: GetSnapshot_ModifySnapshotDoesNotAffectBoard** ( :white_check_mark: )
  - **Method(s) under test**: `getSnapshot()`
  - **State of the system**: a freshly constructed board; set `getSnapshot()[7][0]` to `null`
  - **Expected output**: a subsequent `getSnapshot()[7][0]` still has type `KNIGHT` and color `WHITE`

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

- **TC42: GetCurrentGameState_OnNewBoard_ReturnsWhiteTurn** ( :x: )
  - **Method(s) under test**: `getCurrentGameState()`
  - **State of the system**: freshly constructed board
  - **Expected output**: returns `WHITE_TURN`

- **TC43: GetCurrentGameState_AfterSwitchTurn_ReturnsBlackTurn** ( :x: )
  - **Method(s) under test**: `getCurrentGameState()`, `switchTurn()`
  - **State of the system**: board after one `switchTurn()` call
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

- **TC44: SwitchTurn_FromWhiteTurn_GameStateIsBlackTurn** ( :x: )
  - **Method(s) under test**: `switchTurn()`, `getCurrentGameState()`
  - **State of the system**: freshly constructed board; `currentGameState` is `WHITE_TURN`
  - **Expected output**: after `switchTurn()`, `getCurrentGameState()` returns `BLACK_TURN`

- **TC45: SwitchTurn_FromBlackTurn_GameStateIsWhiteTurn** ( :x: )
  - **Method(s) under test**: `switchTurn()`, `getCurrentGameState()`
  - **State of the system**: board where `switchTurn()` has been called once (state is `BLACK_TURN`)
  - **Expected output**: after a second `switchTurn()`, `getCurrentGameState()` returns `WHITE_TURN`

---
