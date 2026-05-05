# BVA Analysis for Queen

## Design Review Notes

- The design document defines `Queen` as a subclass of `Piece` with three public methods: `Queen(PieceColor color)`, `makeCopy()`, and `isValidMoveShape(Location from, Location to)`.
- The board design notes separate obstacle handling from move-shape validation and model jump capability at the `Piece` level. That means the queen BVA should focus on constructor state, copy behavior, and move shape only.
- The current checkout does not contain the domain implementation, and `docs/requirements/game-rules.md` is still a placeholder. The analysis below therefore uses the UML plus the standard chess rule that a queen moves any number of squares in any direction (row, column, or diagonal) and cannot jump over pieces.

## Specification

- **`public Queen(PieceColor color)`**: creates a queen with the provided color. The created piece has type `QUEEN`, reports the same color through `getColor()`, and reports that it cannot jump through `canJump()`.
- **`public Piece makeCopy()`**: returns a new `Piece` object representing the same kind of piece as the original queen. The returned piece is a distinct object, still has type `QUEEN`, has the same color as the original, and cannot jump.
- **`public boolean isValidMoveShape(Location from, Location to)`**: returns `true` exactly when the displacement matches a valid queen move shape (any non-zero row-only, column-only, or equal-magnitude diagonal displacement). Returns `false` for all other displacements. This method checks shape only, not board occupancy or obstacles.

---

## Method: `Queen(PieceColor color)`

### Step 1-3: Analysis

| Parameter | Catalog clue | Values considered |
|-----------|--------------|-------------------|
| Input: `color` | Cases | `WHITE`, `BLACK` |
| Output: piece type | Cases | `QUEEN` |
| Output: piece jump capability | Boolean | `false` |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC1: Constructor_OnWhiteQueen_TypeIsQueen** ( :white_check_mark: )
  - **Method(s) under test**: `Queen(PieceColor color)`
  - **State of the system**: no existing queen object; input color is `WHITE`
  - **Expected output**: created piece type is `QUEEN`

- **TC2: Constructor_OnWhiteQueen_ColorIsWhite** ( :white_check_mark: )
  - **Method(s) under test**: `Queen(PieceColor color)`
  - **State of the system**: no existing queen object; input color is `WHITE`
  - **Expected output**: `getColor()` returns `WHITE`

- **TC3: Constructor_OnWhiteQueen_CanJumpIsFalse** ( :x: )
  - **Method(s) under test**: `Queen(PieceColor color)`
  - **State of the system**: no existing queen object; input color is `WHITE`
  - **Expected output**: `canJump()` returns `false`

---

## Method: `makeCopy()`

### Step 1-3: Analysis

| Parameter | Catalog clue | Values considered |
|-----------|--------------|-------------------|
| Input: original piece color | Cases | `WHITE`, `BLACK` |
| Output: returned piece type | Cases | `QUEEN` |
| Output: returned piece jump capability | Boolean | `false` |
| Output: original and copy references | Pairs of references | same object is not allowed; copy must be a different object |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC4: MakeCopy_OnWhiteQueen_CopyTypeIsQueen** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing white queen
  - **Expected output**: returned piece type is `QUEEN`

- **TC5: MakeCopy_OnBlackQueen_CopyTypeIsQueen** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black queen
  - **Expected output**: returned piece type is `QUEEN`

- **TC6: MakeCopy_OnBlackQueen_CopyColorIsBlack** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black queen
  - **Expected output**: returned piece color is `BLACK`

- **TC7: MakeCopy_OnBlackQueen_CopyCanJumpIsFalse** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black queen
  - **Expected output**: returned piece cannot jump

- **TC8: MakeCopy_OnBlackQueen_CopyIsDifferentObject** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black queen
  - **Expected output**: returned piece is a different object from the original

---
