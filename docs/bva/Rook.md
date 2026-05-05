# BVA Analysis for Rook

## Design Review Notes

- The design document defines `Rook` as a subclass of `Piece` with three public methods: `Rook(PieceColor color)`, `makeCopy()`, and `isValidMoveShape(Location from, Location to)`.
- The board design notes separate obstacle handling from move-shape validation and model jump capability at the `Piece` level. That means the rook BVA should focus on constructor state, copy behavior, and move shape only.
- The current checkout does not contain the domain implementation, and `docs/requirements/game-rules.md` is still a placeholder. The analysis below therefore uses the UML plus the standard chess rule that a rook moves any number of squares in a straight line (same row or same column) and cannot jump over pieces.

## Specification

- **`public Rook(PieceColor color)`**: creates a rook with the provided color. The created piece has type `ROOK`, reports the same color through `getColor()`, and reports that it cannot jump through `canJump()`.
- **`public Piece makeCopy()`**: returns a new `Piece` object representing the same kind of piece as the original rook. The returned piece is a distinct object, still has type `ROOK`, has the same color as the original, and cannot jump.
- **`public boolean isValidMoveShape(Location from, Location to)`**: returns `true` exactly when the displacement is purely horizontal or purely vertical and non-zero. Returns `false` for all other displacements. This method checks shape only, not board occupancy or obstacles.

---

## Method: `Rook(PieceColor color)`

### Step 1-3: Analysis

| Parameter | Catalog clue | Values considered |
|-----------|--------------|-------------------|
| Input: `color` | Cases | `WHITE`, `BLACK` |
| Output: piece type | Cases | `ROOK` |
| Output: piece jump capability | Boolean | `false` |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC1: Constructor_OnWhiteRook_TypeIsRook** ( :x: )
  - **Method(s) under test**: `Rook(PieceColor color)`
  - **State of the system**: no existing rook object; input color is `WHITE`
  - **Expected output**: created piece type is `ROOK`

- **TC2: Constructor_OnWhiteRook_ColorIsWhite** ( :x: )
  - **Method(s) under test**: `Rook(PieceColor color)`
  - **State of the system**: no existing rook object; input color is `WHITE`
  - **Expected output**: `getColor()` returns `WHITE`

- **TC3: Constructor_OnWhiteRook_CanJumpIsFalse** ( :x: )
  - **Method(s) under test**: `Rook(PieceColor color)`
  - **State of the system**: no existing rook object; input color is `WHITE`
  - **Expected output**: `canJump()` returns `false`

---

## Method: `makeCopy()`

### Step 1-3: Analysis

| Parameter | Catalog clue | Values considered |
|-----------|--------------|-------------------|
| Input: original piece color | Cases | `WHITE`, `BLACK` |
| Output: returned piece type | Cases | `ROOK` |
| Output: returned piece jump capability | Boolean | `false` |
| Output: original and copy references | Pairs of references | same object is not allowed; copy must be a different object |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC4: MakeCopy_OnWhiteRook_CopyTypeIsRook** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing white rook
  - **Expected output**: returned piece type is `ROOK`

- **TC5: MakeCopy_OnBlackRook_CopyTypeIsRook** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black rook
  - **Expected output**: returned piece type is `ROOK`

- **TC6: MakeCopy_OnBlackRook_CopyColorIsBlack** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black rook
  - **Expected output**: returned piece color is `BLACK`

- **TC7: MakeCopy_OnBlackRook_CopyCanJumpIsFalse** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black rook
  - **Expected output**: returned piece cannot jump

- **TC8: MakeCopy_OnBlackRook_CopyIsDifferentObject** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black rook
  - **Expected output**: returned piece is a different object from the original

---
