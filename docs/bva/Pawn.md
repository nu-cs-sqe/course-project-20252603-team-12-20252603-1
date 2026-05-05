# BVA Analysis for Pawn

## Design Review Notes

- The design document defines `Pawn` as a subclass of `Piece` with three public methods: `Pawn(PieceColor color)`, `makeCopy()`, and `isValidMoveShape(Location from, Location to)`.
- The board design notes separate obstacle handling from move-shape validation and model jump capability at the `Piece` level. That means the pawn BVA should focus on constructor state, copy behavior, and move shape only.
- The current checkout does not contain the domain implementation, and `docs/requirements/game-rules.md` is still a placeholder. The analysis below therefore uses the UML plus the standard chess rule that a pawn moves one square forward and cannot jump over pieces.

## Specification

- **`public Pawn(PieceColor color)`**: creates a pawn with the provided color. The created piece has type `PAWN`, reports the same color through `getColor()`, and reports that it cannot jump through `canJump()`.
- **`public Piece makeCopy()`**: returns a new `Piece` object representing the same kind of piece as the original pawn. The returned piece is a distinct object, still has type `PAWN`, has the same color as the original, and cannot jump.
- **`public boolean isValidMoveShape(Location from, Location to)`**: returns `true` exactly when the displacement matches a valid pawn move shape. Returns `false` for all other displacements. This method checks shape only, not board occupancy or obstacles.

---

## Method: `Pawn(PieceColor color)`

### Step 1-3: Analysis

| Parameter | Catalog clue | Values considered |
|-----------|--------------|-------------------|
| Input: `color` | Cases | `WHITE`, `BLACK` |
| Output: piece type | Cases | `PAWN` |
| Output: piece jump capability | Boolean | `false` |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC1: Constructor_OnWhitePawn_TypeIsPawn** ( :white_check_mark: )
  - **Method(s) under test**: `Pawn(PieceColor color)`
  - **State of the system**: no existing pawn object; input color is `WHITE`
  - **Expected output**: created piece type is `PAWN`

- **TC2: Constructor_OnWhitePawn_ColorIsWhite** ( :x: )
  - **Method(s) under test**: `Pawn(PieceColor color)`
  - **State of the system**: no existing pawn object; input color is `WHITE`
  - **Expected output**: `getColor()` returns `WHITE`

- **TC3: Constructor_OnWhitePawn_CanJumpIsFalse** ( :x: )
  - **Method(s) under test**: `Pawn(PieceColor color)`
  - **State of the system**: no existing pawn object; input color is `WHITE`
  - **Expected output**: `canJump()` returns `false`

---

## Method: `makeCopy()`

### Step 1-3: Analysis

| Parameter | Catalog clue | Values considered |
|-----------|--------------|-------------------|
| Input: original piece color | Cases | `WHITE`, `BLACK` |
| Output: returned piece type | Cases | `PAWN` |
| Output: returned piece jump capability | Boolean | `false` |
| Output: original and copy references | Pairs of references | same object is not allowed; copy must be a different object |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC4: MakeCopy_OnWhitePawn_CopyTypeIsPawn** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing white pawn
  - **Expected output**: returned piece type is `PAWN`

- **TC5: MakeCopy_OnBlackPawn_CopyTypeIsPawn** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black pawn
  - **Expected output**: returned piece type is `PAWN`

- **TC6: MakeCopy_OnBlackPawn_CopyColorIsBlack** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black pawn
  - **Expected output**: returned piece color is `BLACK`

- **TC7: MakeCopy_OnBlackPawn_CopyCanJumpIsFalse** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black pawn
  - **Expected output**: returned piece cannot jump

- **TC8: MakeCopy_OnBlackPawn_CopyIsDifferentObject** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black pawn
  - **Expected output**: returned piece is a different object from the original

---
