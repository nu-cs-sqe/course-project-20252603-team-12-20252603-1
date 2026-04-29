# BVA Analysis for King

## Design Review Notes

- The design document defines `King` as a subclass of `Piece` with three public methods: `King(PieceColor color)`, `makeCopy()`, and `isValidMoveShape(Location from, Location to)`.
- The board design notes separate obstacle handling from move-shape validation and model jump capability at the `Piece` level. This BVA analysis focuses on constructor state and copy behavior only.
- The current checkout does not contain the domain implementation, and `docs/requirements/game-rules.md` is still a placeholder. The analysis below uses the UML design plus standard chess rules: a king moves one square in any direction (horizontal, vertical, or diagonal).

## Specification

- **`public King(PieceColor color)`**: creates a king with the provided color. The created piece has type `KING`, reports the same color through `getColor()`, and reports that it cannot jump through `canJump()`.
- **`public Piece makeCopy()`**: returns a new `Piece` object representing the same kind of piece as the original king. The returned piece is a distinct object, still has type `KING`, has the same color as the original, and cannot jump.

---

## Method: `King(PieceColor color)`

### Step 1-3: Analysis

| Parameter                     | Catalog clue | Values considered |
| ----------------------------- | ------------ | ----------------- |
| Input: `color`                | Cases        | `WHITE`, `BLACK`  |
| Output: piece type            | Cases        | `KING`            |
| Output: piece jump capability | Boolean      | `false`           |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC1: Constructor_OnWhiteKing_TypeIsKing** ( :white_check_mark: )
  - **Method(s) under test**: `King(PieceColor color)`
  - **State of the system**: no existing king object; input color is `WHITE`
  - **Expected output**: created piece type is `KING`

- **TC2: Constructor_OnWhiteKing_ColorIsWhite** ( :white_check_mark: )
  - **Method(s) under test**: `King(PieceColor color)`
  - **State of the system**: no existing king object; input color is `WHITE`
  - **Expected output**: `getColor()` returns `WHITE`

- **TC3: Constructor_OnWhiteKing_CanJumpIsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `King(PieceColor color)`
  - **State of the system**: no existing king object; input color is `WHITE`
  - **Expected output**: `canJump()` returns `false`

---

## Method: `makeCopy()`

### Step 1-3: Analysis

| Parameter                              | Catalog clue        | Values considered                                           |
| -------------------------------------- | ------------------- | ----------------------------------------------------------- |
| Input: original piece color            | Cases               | `WHITE`, `BLACK`                                            |
| Output: returned piece type            | Cases               | `KING`                                                      |
| Output: returned piece jump capability | Boolean             | `false`                                                     |
| Output: original and copy references   | Pairs of references | same object is not allowed; copy must be a different object |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC3: Copy a white king and check its type** ( :white_check_mark: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing white king
  - **Expected output**: returned piece type is `KING`

- **TC4: Copy a black king and check its type** ( :white_check_mark: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black king
  - **Expected output**: returned piece type is `KING`

- **TC5: Copy a black king and check its color** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black king
  - **Expected output**: returned piece color is `BLACK`

- **TC6: Copy a black king and check jump capability** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black king
  - **Expected output**: returned piece cannot jump

- **TC7: Copy a black king and check reference equality** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing black king
  - **Expected output**: returned piece is a different object from the original

---
