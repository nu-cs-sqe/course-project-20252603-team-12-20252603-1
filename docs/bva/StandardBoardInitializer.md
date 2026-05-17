# BVA Analysis for StandardBoardInitializer

## Method: `getBoardLayout()`

### Step 1: Equivalence Classes

- **Output: piece type at each black back rank position** — the PieceType placed at each column of row 0
- **Output: piece type at each black pawn rank position** — the PieceType placed at each column of row 1
- **Output: piece type at each empty position** — the PieceType at positions in rows 2–5
- **Output: piece type at each white pawn rank position** — the PieceType placed at each column of row 6
- **Output: piece type at each white back rank position** — the PieceType placed at each column of row 7

### Step 2: Data Types (from BVA Catalog)

| Equivalence class                                    | Catalog data type | Parameters                                                                                                             |
| ---------------------------------------------------- | ----------------- | ---------------------------------------------------------------------------------------------------------------------- |
| Output: piece type at black back rank positions      | Cases             | `[0][0]`=ROOK, `[0][1]`=KNIGHT, `[0][2]`=BISHOP, `[0][3]`=QUEEN, `[0][4]`=KING, `[0][5]`=BISHOP, `[0][6]`=KNIGHT, `[0][7]`=ROOK |
| Output: piece type at black pawn rank positions      | Cases             | `[1][0]`=PAWN, `[1][1]`=PAWN, `[1][2]`=PAWN, `[1][3]`=PAWN, `[1][4]`=PAWN, `[1][5]`=PAWN, `[1][6]`=PAWN, `[1][7]`=PAWN         |
| Output: piece type at empty positions                | Cases             | `NONE`                                                                                                                 |
| Output: piece type at white pawn rank positions      | Cases             | `[6][0]`=PAWN, `[6][1]`=PAWN, `[6][2]`=PAWN, `[6][3]`=PAWN, `[6][4]`=PAWN, `[6][5]`=PAWN, `[6][6]`=PAWN, `[6][7]`=PAWN         |
| Output: piece type at white back rank positions      | Cases             | `[7][0]`=ROOK, `[7][1]`=KNIGHT, `[7][2]`=BISHOP, `[7][3]`=QUEEN, `[7][4]`=KING, `[7][5]`=BISHOP, `[7][6]`=KNIGHT, `[7][7]`=ROOK |

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
- `NONE`

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

### Step 4: Test Cases (Each-Choice Strategy)

Each boundary value from Step 3 appears in at least one test case.

- **TC1: GetBoardLayout_PieceTypeAtPositionIsCorrect** ( :x: )
  - **Method(s) under test**: `getBoardLayout()`
  - **State of the system**: call `getBoardLayout()` on a new `StandardBoardInitializer`
  - **Expected output**: position `[0][0]` has type `ROOK`
  - **Note**: TC2–TC33 are covered by this test as a parameterized test

- **TC2: GetBoardLayout_TypeAtRow0Col1IsKnight** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC3: GetBoardLayout_TypeAtRow0Col2IsBishop** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC4: GetBoardLayout_TypeAtRow0Col3IsQueen** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC5: GetBoardLayout_TypeAtRow0Col4IsKing** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC6: GetBoardLayout_TypeAtRow0Col5IsBishop** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC7: GetBoardLayout_TypeAtRow0Col6IsKnight** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC8: GetBoardLayout_TypeAtRow0Col7IsRook** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC9: GetBoardLayout_TypeAtRow1Col0IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC10: GetBoardLayout_TypeAtRow1Col1IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC11: GetBoardLayout_TypeAtRow1Col2IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC12: GetBoardLayout_TypeAtRow1Col3IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC13: GetBoardLayout_TypeAtRow1Col4IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC14: GetBoardLayout_TypeAtRow1Col5IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC15: GetBoardLayout_TypeAtRow1Col6IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC16: GetBoardLayout_TypeAtRow1Col7IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC17: GetBoardLayout_TypeAtRow3Col0IsNone** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC18: GetBoardLayout_TypeAtRow6Col0IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC19: GetBoardLayout_TypeAtRow6Col1IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC20: GetBoardLayout_TypeAtRow6Col2IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC21: GetBoardLayout_TypeAtRow6Col3IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC22: GetBoardLayout_TypeAtRow6Col4IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC23: GetBoardLayout_TypeAtRow6Col5IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC24: GetBoardLayout_TypeAtRow6Col6IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC25: GetBoardLayout_TypeAtRow6Col7IsPawn** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC26: GetBoardLayout_TypeAtRow7Col0IsRook** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC27: GetBoardLayout_TypeAtRow7Col1IsKnight** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC28: GetBoardLayout_TypeAtRow7Col2IsBishop** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC29: GetBoardLayout_TypeAtRow7Col3IsQueen** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC30: GetBoardLayout_TypeAtRow7Col4IsKing** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC31: GetBoardLayout_TypeAtRow7Col5IsBishop** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC32: GetBoardLayout_TypeAtRow7Col6IsKnight** ( :x: )
  - **Covered by**: TC1 (parameterized test)

- **TC33: GetBoardLayout_TypeAtRow7Col7IsRook** ( :x: )
  - **Covered by**: TC1 (parameterized test)
