# BVA Analysis for MoveGenerator

## Method: `MoveGenerator(Piece[][] board, Optional<Location> enPassantTarget)`

### Step 1: Equivalence Classes

- **Input: board snapshot** — 8×8 grid of `Piece` references passed to the generator
- **Input: en-passant target state** — no target vs target set at a capture square
- **Output: generator readiness** — generator holds board and target for later move generation

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: board snapshot | Collections | 8×8 `Piece[][]` |
| Input: en-passant target state | Cases | no target, target present at `Location` |
| Output: generator readiness | Cases | ready for `generateLegalMoves` |

### Step 3: Boundary Values (from BVA Catalog)

**Board snapshot — Collections:**

- 8×8 array filled with `NonePiece` except test-specific placements

**En-passant target state — Cases:**

- No en-passant target
- En-passant target present at a capture square (used in later slices)

### Step 4: Test Cases (Each-Choice Strategy)

- **MG-TC1: Constructor_WithBoardAndEmptyEnPassant_GenerateLegalMovesUsable** ( :white_check_mark: )
  - **Method(s) under test**: `MoveGenerator(Piece[][], Optional<Location>)`, `generateLegalMoves(Location)`
  - **State of the system**: 8×8 board with white knight at `(4,4)`; no en-passant target
  - **Expected output**: `generateLegalMoves(new Location(4, 4))` returns a non-null list

---

## Method: `generateLegalMoves(Location from)`

Scope: pseudo-legal moves for the piece at `from` on the constructor board snapshot (piece-type rules only in this slice; check filtering documented below).

### Step 1: Equivalence Classes

- **Input: source file** — column index of `from`
- **Input: source rank** — row index of `from`
- **Input: piece type at `from`** — `NONE`, `PAWN`, `KNIGHT`, `BISHOP`, `ROOK`, `QUEEN`, `KING`
- **Input: board occupancy** — empty paths, blockers, capturable enemy pieces
- **Output: move list** — returned list size and destinations

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: source file | Interval | [0, 7] |
| Input: source rank | Interval | [0, 7] |
| Input: piece type at `from` | Cases | NONE, PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING |
| Input: board occupancy | Collections | empty board, blockers, capturable pieces |
| Output: move list size | Counts | 0, 2, 8, 13, 14, 27 |

### Step 3: Boundary Values (from BVA Catalog)

**Source file / rank — Interval [0, 7]:**

- Center `(4, 4)` for full directional coverage
- Edge and corner squares where move counts differ (covered by piece-type TCs)

**Piece type at `from` — Cases:**

- NONE → empty list
- PAWN at start rank `(4, 6)` with empty `(4, 5)` and `(4, 4)` → 2 moves
- KNIGHT at center → 8 moves
- BISHOP at center on empty board → 13 moves
- ROOK at center on empty board → 14 moves
- QUEEN at center on empty board → 27 moves
- KING at center on empty board → 8 moves

**Move list size — Counts:**

- 0 (empty square)
- 2 (white pawn at start)
- 8 (knight or king at center)
- 13, 14, 27 (bishop, rook, queen at center)

### Step 4: Test Cases (Each-Choice Strategy)

- **MG-TC2: GenerateLegalMoves_OnEmptySquare_ReturnsEmptyList** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: `from` `(3, 3)` holds `NonePiece`
  - **Expected output**: returned move list size is `0`

- **MG-TC3: GenerateLegalMoves_OnKnightAtCenter_ReturnsEightMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: white knight at `(4, 4)`; all other squares `NonePiece`
  - **Expected output**: returned move list size is `8`

- **MG-TC4: GenerateLegalMoves_OnBishopAtCenter_ReturnsThirteenMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: white bishop at `(4, 4)`; all other squares `NonePiece`
  - **Expected output**: returned move list size is `13`

- **MG-TC5: GenerateLegalMoves_OnRookAtCenter_ReturnsFourteenMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: white rook at `(4, 4)`; all other squares `NonePiece`
  - **Expected output**: returned move list size is `14`

- **MG-TC6: GenerateLegalMoves_OnQueenAtCenter_ReturnsTwentySevenMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: white queen at `(4, 4)`; all other squares `NonePiece`
  - **Expected output**: returned move list size is `27`

- **MG-TC7: GenerateLegalMoves_OnKingAtCenter_ReturnsEightMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: white king at `(4, 4)`; all other squares `NonePiece`
  - **Expected output**: returned move list size is `8`

- **MG-TC8: GenerateLegalMoves_OnWhitePawnAtStart_ReturnsOneAndTwoStepMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: white pawn at `(4, 6)`; squares `(4, 5)` and `(4, 4)` empty
  - **Expected output**: returned move list size is `2`

---

## Method / behavior: check filtering in `generateLegalMoves(Location from)`

Scope: after pseudo-legal generation, remove moves that leave the moving side's king in check (`filterLegalMoves` / `applyMoveToBoard` / `leavesOwnKingInCheck`). Move simulation uses **NORMAL** moves only in this slice (no en passant, castling, or promotion).

### Step 1: Equivalence Classes

- **Input: pin exposure** — pinned piece move that exposes king vs legal move along pin line
- **Input: king in check** — king attacked with escape squares vs square still under attack
- **Output: filtered move list** — excluded illegal destinations; reduced or empty list

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: pin exposure | Cases | move exposes king, move does not expose king |
| Input: king in check | Cases | in check with escapes, square still in check |
| Output: filtered move list | Collections | destination absent, list size reduced |
| Output: move list size | Counts | 6 (king escapes off attacked file) |

### Step 3: Boundary Values (from BVA Catalog)

**Pin exposure — Cases:**

- Pinned white bishop at `(2, 3)`; white king `(2, 2)`; black rook `(2, 7)` on c-file — pseudo move to `(3, 4)` exposes king

**King in check — Cases:**

- White king `(4, 4)`; black rook `(4, 0)` on same file — six escape squares off the file; `(4, 3)` still attacked

### Step 4: Test Cases (Each-Choice Strategy)

- **MG-TC14: GenerateLegalMoves_OnPinnedBishop_ExcludesMoveThatExposesKing** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: pinned white bishop at `(2, 3)`; king `(2, 2)`; black rook `(2, 7)`
  - **Expected output**: no returned move has destination `(3, 4)`

- **MG-TC15: GenerateLegalMoves_OnKingInCheck_ReturnsSixEscapeMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: white king at `(4, 4)`; black rook at `(4, 0)`
  - **Expected output**: returned move list size is `6`

- **MG-TC16: GenerateLegalMoves_OnKingInCheck_ExcludesSquareStillInCheck** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: white king at `(4, 4)`; black rook at `(4, 0)`
  - **Expected output**: no returned move has destination `(4, 3)`

---

## Method: `generateAllLegalMovesForColor(PieceColor color)`

### Step 1: Equivalence Classes

- **Input: color** — `WHITE` or `BLACK`
- **Input: board distribution** — pieces of both colors on the snapshot
- **Output: move list** — concatenated legal moves for all pieces of the given color

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: color | Cases | WHITE, BLACK |
| Input: board distribution | Collections | single movable piece, multiple pieces |
| Output: move list size | Counts | 8 (single white knight) |

### Step 3: Boundary Values (from BVA Catalog)

**Color — Cases:**

- WHITE
- BLACK

**Board distribution — Collections:**

- Only movable white piece: knight at `(4, 4)`; black king at `(0, 0)` (blocks nothing for knight)

**Move list size — Counts:**

- 8 (one white knight on empty board)

### Step 4: Test Cases (Each-Choice Strategy)

- **MG-TC9: GenerateAllLegalMovesForColor_OnSingleWhiteKnight_ReturnsEightMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateAllLegalMovesForColor(PieceColor)`
  - **State of the system**: only movable white piece is knight at `(4, 4)`
  - **Expected output**: returned move list size is `8` for `PieceColor.WHITE`

---

## Method: `hasLegalMovesForColor(PieceColor color)`

### Step 1: Equivalence Classes

- **Input: color** — `WHITE` or `BLACK`
- **Input: board distribution** — side has at least one legal move vs no pieces of that color
- **Input: king in check** — side in check but has a legal escape (check-filtering slice)
- **Output: result** — `true` or `false`

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: color | Cases | WHITE, BLACK |
| Input: board distribution | Collections | movable piece present, no pieces for color |
| Input: king in check | Cases | in check with legal escape |
| Output: result | Boolean | true, false |

### Step 3: Boundary Values (from BVA Catalog)

**Color — Cases:**

- WHITE
- BLACK

**Board distribution — Collections:**

- White knight at `(4, 4)` on otherwise empty board → `true` for WHITE
- No black pieces on board → `false` for BLACK

**King in check — Cases:**

- White king in check from rook on file but can escape off the file → `true` for WHITE

### Step 4: Test Cases (Each-Choice Strategy)

- **MG-TC10: HasLegalMovesForColor_OnMovableWhitePiece_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `hasLegalMovesForColor(PieceColor)`
  - **State of the system**: white knight at `(4, 4)`
  - **Expected output**: `hasLegalMovesForColor(PieceColor.WHITE)` is `true`

- **MG-TC11: HasLegalMovesForColor_OnNoPiecesForColor_ReturnsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `hasLegalMovesForColor(PieceColor)`
  - **State of the system**: board has no black pieces
  - **Expected output**: `hasLegalMovesForColor(PieceColor.BLACK)` is `false`

- **MG-TC17: HasLegalMovesForColor_WhenInCheckWithLegalEscape_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `hasLegalMovesForColor(PieceColor)`
  - **State of the system**: white king in check from rook on same file but can escape off the file
  - **Expected output**: `hasLegalMovesForColor(PieceColor.WHITE)` is `true`

---

## Method: `isInCheck(PieceColor color)`

### Step 1: Equivalence Classes

- **Input: color** — `WHITE` or `BLACK`
- **Input: king attack state** — king attacked vs not attacked
- **Input: king presence** — king on board vs absent (invalid layout; not primary TC focus)
- **Output: result** — `true` or `false`

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: color | Cases | WHITE, BLACK |
| Input: king attack state | Cases | attacked, not attacked |
| Output: result | Boolean | true, false |

### Step 3: Boundary Values (from BVA Catalog)

**Color — Cases:**

- WHITE
- BLACK

**King attack state — Cases:**

- Attacked: white king `(4, 4)`; black rook `(4, 0)`; clear file between
- Not attacked: white king `(4, 4)`; no black piece attacks it

### Step 4: Test Cases (Each-Choice Strategy)

- **MG-TC12: IsInCheck_WhenRookAttacksKing_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)`
  - **State of the system**: white king on same file as black rook with empty squares between
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `true`

- **MG-TC13: IsInCheck_WhenKingNotAttacked_ReturnsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)`
  - **State of the system**: white king present; no black piece attacks it
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `false`
