# BVA Analysis for FischerRandomBoardInitializer

## Method: `getBoardLayout()`

### Step 1: Equivalence Classes

- **Output: piece type constraints on black back rank** — Chess960 rules for row 0
- **Output: piece type at each black pawn rank position** — the PieceType placed at each column of row 1
- **Output: piece type at each empty position** — the PieceType at positions in rows 2–5
- **Output: piece type at each white pawn rank position** — the PieceType placed at each column of row 6
- **Output: piece type constraints on white back rank** — Chess960 rules for row 7

### Step 2: Data Types (from BVA Catalog)

| Equivalence class                                    | Catalog data type | Parameters                                                                                                             |
| ---------------------------------------------------- | ----------------- | ---------------------------------------------------------------------------------------------------------------------- |
| Output: piece type constraints on black back rank    | Cases             | back rank contains ROOK×2, KNIGHT×2, BISHOP×2, QUEEN×1, KING×1; bishops on opposite colors; king between rooks |
| Output: piece type at each black pawn rank position  | Cases             | `[1][0]`=PAWN, `[1][1]`=PAWN, `[1][2]`=PAWN, `[1][3]`=PAWN, `[1][4]`=PAWN, `[1][5]`=PAWN, `[1][6]`=PAWN, `[1][7]`=PAWN |
| Output: piece type at each empty position            | Cases             | `NONE`                                                                                                                 |
| Output: piece type at each white pawn rank position  | Cases             | `[6][0]`=PAWN, `[6][1]`=PAWN, `[6][2]`=PAWN, `[6][3]`=PAWN, `[6][4]`=PAWN, `[6][5]`=PAWN, `[6][6]`=PAWN, `[6][7]`=PAWN |
| Output: piece type constraints on white back rank    | Cases             | back rank contains ROOK×2, KNIGHT×2, BISHOP×2, QUEEN×1, KING×1; bishops on opposite colors; king between rooks |

### Step 3: Boundary Values (from BVA Catalog)

**Black back rank (row 0) — Cases:**
- back rank contains ROOK×2, KNIGHT×2, BISHOP×2, QUEEN×1, KING×1
- one bishop on an even column, one bishop on an odd column
- king column is between the two rook columns

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
- back rank contains ROOK×2, KNIGHT×2, BISHOP×2, QUEEN×1, KING×1
- one bishop on an even column, one bishop on an odd column
- king column is between the two rook columns

### Step 4: Test Cases (Each-Choice Strategy)

Each boundary value from Step 3 appears in at least one test case. TC1–TC3 are parameterized over both rows 0 and 7, so TC4–TC6 (white back rank) are covered by them.

- **TC1: GetBoardLayout_BlackBackRankContainsAllRequiredPieceTypes** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardLayout()`
  - **State of the system**: call `getBoardLayout()` on a new `FischerRandomBoardInitializer`
  - **Expected output**: row 0 contains exactly ROOK×2, KNIGHT×2, BISHOP×2, QUEEN×1, KING×1
  - **Note**: TC4 is covered by this test as a parameterized test (row 7)

- **TC2: GetBoardLayout_BlackBishopsAreOnOppositeColoredSquares** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardLayout()`
  - **State of the system**: call `getBoardLayout()` on a new `FischerRandomBoardInitializer`
  - **Expected output**: one bishop in row 0 is on an even column; the other is on an odd column
  - **Note**: TC5 is covered by this test as a parameterized test (row 7)

- **TC3: GetBoardLayout_BlackKingIsBetweenRooks** ( :white_check_mark: )
  - **Method(s) under test**: `getBoardLayout()`
  - **State of the system**: call `getBoardLayout()` on a new `FischerRandomBoardInitializer`
  - **Expected output**: king column in row 0 is greater than the left rook's column and less than the right rook's column
  - **Note**: TC6 is covered by this test as a parameterized test (row 7)

- **TC4: GetBoardLayout_WhiteBackRankContainsAllRequiredPieceTypes** ( :white_check_mark: )
  - **Covered by**: TC1 (parameterized test)

- **TC5: GetBoardLayout_WhiteBishopsAreOnOppositeColoredSquares** ( :white_check_mark: )
  - **Covered by**: TC2 (parameterized test)

- **TC6: GetBoardLayout_WhiteKingIsBetweenRooks** ( :white_check_mark: )
  - **Covered by**: TC3 (parameterized test)

- **TC7: GetBoardLayout_PieceTypeAtPawnRankIsCorrect** ( :x: )
  - **Method(s) under test**: `getBoardLayout()`
  - **State of the system**: call `getBoardLayout()` on a new `FischerRandomBoardInitializer`
  - **Expected output**: position `[1][0]` has type `PAWN`
  - **Note**: TC8–TC22 are covered by this test as a parameterized test (same 16 pawn positions across rows 1 and 6)

- **TC8: GetBoardLayout_TypeAtRow1Col1IsPawn** ( :x: )
  - **Covered by**: TC7 (parameterized test)

- **TC9: GetBoardLayout_TypeAtRow1Col2IsPawn** ( :x: )
  - **Covered by**: TC7 (parameterized test)

- **TC10: GetBoardLayout_TypeAtRow1Col3IsPawn** ( :x: )
  - **Covered by**: TC7 (parameterized test)

- **TC11: GetBoardLayout_TypeAtRow1Col4IsPawn** ( :x: )
  - **Covered by**: TC7 (parameterized test)

- **TC12: GetBoardLayout_TypeAtRow1Col5IsPawn** ( :x: )
  - **Covered by**: TC7 (parameterized test)

- **TC13: GetBoardLayout_TypeAtRow1Col6IsPawn** ( :x: )
  - **Covered by**: TC7 (parameterized test)

- **TC14: GetBoardLayout_TypeAtRow1Col7IsPawn** ( :x: )
  - **Covered by**: TC7 (parameterized test)

- **TC15: GetBoardLayout_TypeAtRow6Col0IsPawn** ( :x: )
  - **Covered by**: TC7 (parameterized test)

- **TC16: GetBoardLayout_TypeAtRow6Col1IsPawn** ( :x: )
  - **Covered by**: TC7 (parameterized test)

- **TC17: GetBoardLayout_TypeAtRow6Col2IsPawn** ( :x: )
  - **Covered by**: TC7 (parameterized test)

- **TC18: GetBoardLayout_TypeAtRow6Col3IsPawn** ( :x: )
  - **Covered by**: TC7 (parameterized test)

- **TC19: GetBoardLayout_TypeAtRow6Col4IsPawn** ( :x: )
  - **Covered by**: TC7 (parameterized test)

- **TC20: GetBoardLayout_TypeAtRow6Col5IsPawn** ( :x: )
  - **Covered by**: TC7 (parameterized test)

- **TC21: GetBoardLayout_TypeAtRow6Col6IsPawn** ( :x: )
  - **Covered by**: TC7 (parameterized test)

- **TC22: GetBoardLayout_TypeAtRow6Col7IsPawn** ( :x: )
  - **Covered by**: TC7 (parameterized test)

- **TC23: GetBoardLayout_EmptyPositionPieceTypeIsNone** ( :x: )
  - **Method(s) under test**: `getBoardLayout()`
  - **State of the system**: call `getBoardLayout()` on a new `FischerRandomBoardInitializer`
  - **Expected output**: position `[3][0]` has type `NONE`
