# BVA Analysis for Knight

## Design Review Notes

- The design document defines `Knight` as a subclass of `Piece` with three public methods: `Knight(PieceColor color)`, `makeCopy()`, and `isValidMoveShape(Location from, Location to)`.
- The board design notes separate obstacle handling from move-shape validation and model jump capability at the `Piece` level. That means the knight BVA should focus on constructor state, copy behavior, and move shape only.
- The current checkout does not contain the domain implementation, and `docs/requirements/game-rules.md` is still a placeholder. The analysis below therefore uses the UML plus the standard chess rule that a knight moves in an L-shape: two squares along one axis and one square along the other.

## Specification

- **`public Knight(PieceColor color)`**: creates a knight with the provided color. The created piece has type `KNIGHT`, reports the same color through `getColor()`, and reports that it can jump through `canJump()`.
- **`public Piece makeCopy()`**: returns a new `Piece` object representing the same kind of piece as the original knight. The returned piece is a distinct object, still has type `KNIGHT`, has the same color as the original, and can jump.
- **`public boolean isValidMoveShape(Location from, Location to)`**: returns `true` exactly when the displacement between `from` and `to` is `(2,1)` or `(1,2)` in absolute value. Returns `false` for all other displacements. This method checks shape only, not board occupancy or obstacles.

---

## Method: `Knight(PieceColor color)`

### Step 1-3: Analysis

| Parameter | Catalog clue | Values considered |
|-----------|--------------|-------------------|
| Input: `color` | Cases | `WHITE`, `BLACK` |
| Output: piece type | Cases | `KNIGHT` |
| Output: piece jump capability | Boolean | `true` |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC1: Construct a white knight** ( :x: )
  - **Method(s) under test**: `Knight(PieceColor color)`
  - **State of the system**: no existing knight object; input color is `WHITE`
  - **Expected output**: created piece type is `KNIGHT`; `getColor()` returns `WHITE`; `canJump()` returns `true`

- **TC2: Construct a black knight** ( :x: )
  - **Method(s) under test**: `Knight(PieceColor color)`
  - **State of the system**: no existing knight object; input color is `BLACK`
  - **Expected output**: created piece type is `KNIGHT`; `getColor()` returns `BLACK`; `canJump()` returns `true`

---

## Method: `makeCopy()`

### Step 1-3: Analysis

| Parameter | Catalog clue | Values considered |
|-----------|--------------|-------------------|
| Input: original piece color | Cases | `WHITE`, `BLACK` |
| Output: returned piece type | Cases | `KNIGHT` |
| Output: returned piece jump capability | Boolean | `true` |
| Output: original and copy references | Pairs of references | same object is not allowed; copy must be a different object |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC3: Copy a white knight** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing white knight
  - **Expected output**: returned piece is a different object from the original; returned piece type is `KNIGHT`; returned piece color is `WHITE`; returned piece can jump

- **TC4: Copy a black knight** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black knight
  - **Expected output**: returned piece is a different object from the original; returned piece type is `KNIGHT`; returned piece color is `BLACK`; returned piece can jump

---