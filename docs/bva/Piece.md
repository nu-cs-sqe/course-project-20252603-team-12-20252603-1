# BVA Analysis for Piece

---

## Method: `isSameColor(Piece piece)`

### Step 1-3: Analysis

| Parameter | Catalog clue | Values considered |
|-----------|--------------|-------------------|
| Input: color relationship between `this` and `piece` | Cases | same color, different color |
| Output: result | Boolean | `true`, `false` |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC1: IsSameColor_OnSameColorPieces_ReturnsTrue** ( :x: )
  - **Method(s) under test**: `isSameColor(Piece piece)`
  - **State of the system**: two white pieces
  - **Expected output**: `true`

- **TC2: IsSameColor_OnDifferentColorPieces_ReturnsFalse** ( :x: )
  - **Method(s) under test**: `isSameColor(Piece piece)`
  - **State of the system**: one white piece and one black piece
  - **Expected output**: `false`

---

## Method: `resetHasMoved()`

### Step 1-3: Analysis

| Parameter | Catalog clue | Values considered |
|-----------|--------------|-------------------|
| Input: current `hasMoved` state | Boolean | `false`, `true` |
| Output: `hasMoved()` after reset | Boolean | `false` |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC3: ResetHasMoved_OnMovedPiece_HasMovedIsFalse** ( :x: )
  - **Method(s) under test**: `resetHasMoved()`
  - **State of the system**: a piece on which `changeToMoved()` has been called
  - **Expected output**: `hasMoved()` returns `false`

- **TC4: ResetHasMoved_OnUnmovedPiece_HasMovedIsFalse** ( :x: )
  - **Method(s) under test**: `resetHasMoved()`
  - **State of the system**: a fresh piece that has not moved
  - **Expected output**: `hasMoved()` returns `false`

---

## Method: `toString()`

### Step 1-3: Analysis

| Parameter | Catalog clue | Values considered |
|-----------|--------------|-------------------|
| Input: piece color | Cases | `WHITE`, `BLACK` |
| Output: string representation | String (non-empty) | `"WHITE PAWN"`, `"BLACK PAWN"` |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC5: ToString_OnWhitePawn_ReturnsWhitePawn** ( :x: )
  - **Method(s) under test**: `toString()`
  - **State of the system**: a white pawn
  - **Expected output**: `"WHITE PAWN"`

- **TC6: ToString_OnBlackPawn_ReturnsBlackPawn** ( :x: )
  - **Method(s) under test**: `toString()`
  - **State of the system**: a black pawn
  - **Expected output**: `"BLACK PAWN"`
