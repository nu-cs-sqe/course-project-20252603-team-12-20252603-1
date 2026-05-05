# BVA Analysis for Bishop

## Design Review Notes

- The design document defines `Bishop` as a subclass of `Piece` with three public methods: `Bishop(PieceColor color)`, `makeCopy()`, and `isValidMoveShape(Location from, Location to)`.
- The board design notes separate obstacle handling from move-shape validation and model jump capability at the `Piece` level. That means the bishop BVA should focus on constructor state, copy behavior, and move shape only.
- The current checkout does not contain the domain implementation, and `docs/requirements/game-rules.md` is still a placeholder. The analysis below therefore uses the UML plus the standard chess rule that a bishop moves diagonally: the absolute row displacement must equal the absolute column displacement, and the displacement must be non-zero.

## Specification

- **`public Bishop(PieceColor color)`**: creates a bishop with the provided color. The created piece has type `BISHOP`, reports the same color through `getColor()`, and reports that it cannot jump through `canJump()`.
- **`public Piece makeCopy()`**: returns a new `Piece` object representing the same kind of piece as the original bishop. The returned piece is a distinct object, still has type `BISHOP`, has the same color as the original, and cannot jump.
- **`public boolean isValidMoveShape(Location from, Location to)`**: returns `true` exactly when the absolute row displacement equals the absolute column displacement and the displacement is non-zero. Returns `false` for all other displacements. This method checks shape only, not board occupancy or obstacles.

---

## Method: `Bishop(PieceColor color)`

### Step 1-3: Analysis

| Parameter | Catalog clue | Values considered |
|-----------|--------------|-------------------|
| Input: `color` | Cases | `WHITE`, `BLACK` |
| Output: piece type | Cases | `BISHOP` |
| Output: piece jump capability | Boolean | `false` |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC1: Constructor_OnWhiteBishop_TypeIsBishop** ( :white_check_mark: )
  - **Method(s) under test**: `Bishop(PieceColor color)`
  - **State of the system**: no existing bishop object; input color is `WHITE`
  - **Expected output**: created piece type is `BISHOP`

- **TC2: Constructor_OnWhiteBishop_ColorIsWhite** ( :white_check_mark: )
  - **Method(s) under test**: `Bishop(PieceColor color)`
  - **State of the system**: no existing bishop object; input color is `WHITE`
  - **Expected output**: `getColor()` returns `WHITE`

- **TC3: Constructor_OnWhiteBishop_CanJumpIsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `Bishop(PieceColor color)`
  - **State of the system**: no existing bishop object; input color is `WHITE`
  - **Expected output**: `canJump()` returns `false`

---

## Method: `makeCopy()`

### Step 1-3: Analysis

| Parameter | Catalog clue | Values considered |
|-----------|--------------|-------------------|
| Input: original piece color | Cases | `WHITE`, `BLACK` |
| Output: returned piece type | Cases | `BISHOP` |
| Output: returned piece jump capability | Boolean | `false` |
| Output: original and copy references | Pairs of references | same object is not allowed; copy must be a different object |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC4: MakeCopy_OnWhiteBishop_CopyTypeIsBishop** ( :white_check_mark: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing white bishop
  - **Expected output**: returned piece type is `BISHOP`

- **TC5: MakeCopy_OnBlackBishop_CopyTypeIsBishop** ( :white_check_mark: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black bishop
  - **Expected output**: returned piece type is `BISHOP`

- **TC6: MakeCopy_OnBlackBishop_CopyColorIsBlack** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black bishop
  - **Expected output**: returned piece color is `BLACK`

- **TC7: MakeCopy_OnBlackBishop_CopyCanJumpIsFalse** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black bishop
  - **Expected output**: returned piece cannot jump

- **TC8: MakeCopy_OnBlackBishop_CopyIsDifferentObject** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black bishop
  - **Expected output**: returned piece is a different object from the original

---
