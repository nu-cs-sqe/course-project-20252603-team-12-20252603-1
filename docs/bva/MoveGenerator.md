# BVA Analysis for MoveGenerator

## Method: `MoveGenerator(Piece[][] board, Optional<Location> enPassantTarget)`

### Step 1: Equivalence Classes

- **Input: board snapshot** — the 8×8 `Piece[][]` passed to the constructor
- **Input: en-passant target state** — whether a capture square is recorded for en passant
- **Output: generator readiness** — generator can produce legal moves from the stored snapshot

### Step 2: Data Types (from BVA Catalog)


| Equivalence class              | Catalog data type | Parameters                                 |
| ------------------------------ | ----------------- | ------------------------------------------ |
| Input: board snapshot          | Collections       | 8×8 `Piece[][]`                            |
| Input: en-passant target state | Cases             | no target, target present at `Location`    |
| Output: generator readiness    | Cases             | `generateLegalMoves(from)` returns a non-null `List<Move>` (may be empty) |


### Step 3: Boundary Values (from BVA Catalog)

**Board snapshot — Collections:**

- 8×8 array filled with `NonePiece` except test-specific placements

**En-passant target state — Cases:**

- No en-passant target
- En-passant target present at a capture square (later slices)

### Step 4: Test Cases (Each-Choice Strategy)

- **MG-TC1: Constructor_WithBoardAndEmptyEnPassant_GenerateLegalMovesUsable** ( :white_check_mark: )
  - **Method(s) under test**: `MoveGenerator(Piece[][], Optional<Location>)`, `generateLegalMoves(Location)`
  - **State of the system**: 8×8 board with white knight at `(4, 4)`; no en-passant target
  - **Expected output**: `generateLegalMoves(new Location(4, 4))` returns a non-null list

---

## Method: `generateLegalMoves(Location from)`

Scope: pseudo-legal moves for the piece at `from`, then check filtering (see check-filtering section). File/rank are in-bounds `Location` values from callers; this slice does not vary min/max file or rank.

### Step 1: Equivalence Classes

- **Input: piece type at `from`** — the `PieceType` on the source square
- **Input: board layout** — lone piece on empty board
- **Output: move list size** — count of legal moves returned

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: piece type at `from` | Cases | NONE, PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING |
| Input: board layout | Collections | lone piece on otherwise empty board |
| Output: move list size | Counts | `0`, `2`, `8`, `13`, `14`, `27` |

### Step 3: Boundary Values (from BVA Catalog)

**Piece type at `from` — Cases:**

- NONE — `NonePiece` at `(3, 3)`
- PAWN — white pawn at `(4, 6)`; `(4, 5)` and `(4, 4)` empty
- KNIGHT — white knight at `(4, 4)`
- BISHOP — white bishop at `(4, 4)`
- ROOK — white rook at `(4, 4)`
- QUEEN — white queen at `(4, 4)`
- KING — white king at `(4, 4)`

**Move list size — Counts:**

- `0`, `2`, `8`, `13`, `14`, `27`

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

Scope: after pseudo-legal generation, remove moves that leave the moving side's king in check. Uses `filterLegalMoves`, `leavesOwnKingInCheck`, and `applyMoveToBoard` (NORMAL moves only in this slice).

### Step 1: Equivalence Classes

- **Input: pin exposure** — pinned piece move that exposes king vs legal move along pin line
- **Input: king in check** — king attacked with escape squares vs square still under attack
- **Output: filtered move list size** — reduced count when moves are removed
- **Output: filtered move list contents** — illegal destinations absent from list

### Step 2: Data Types (from BVA Catalog)


| Equivalence class                   | Catalog data type | Parameters                                   |
| ----------------------------------- | ----------------- | -------------------------------------------- |
| Input: pin exposure | Cases | move exposes king |
| Input: king in check | Cases | in check with escapes, square still in check |
| Output: filtered move list size | Counts | `6` |
| Output: filtered move list contents | Collections | excluded destination `(file, rank)` |


### Step 3: Boundary Values (from BVA Catalog)

**Pin exposure — Cases:**

- Pinned white bishop `(2, 3)`; king `(2, 2)`; black rook `(2, 7)` — destination `(3, 4)` excluded

**King in check — Cases:**

- White king `(4, 4)`; black rook `(4, 0)` — six escapes; destination `(4, 3)` excluded

**Filtered move list size — Counts:**

- `6`

### Step 4: Test Cases (Each-Choice Strategy)

`filterLegalMoves` and `leavesOwnKingInCheck` are private; exercised indirectly through `generateLegalMoves`.

- **MG-TC14: GenerateLegalMoves_OnPinnedBishop_ExcludesMoveThatExposesKing** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `filterLegalMoves`, `leavesOwnKingInCheck`, `applyMoveToBoard`)
  - **State of the system**: pinned white bishop at `(2, 3)`; king `(2, 2)`; black rook `(2, 7)`
  - **Expected output**: no returned move has destination `(3, 4)`
- **MG-TC15: GenerateLegalMoves_OnKingInCheck_ReturnsSixEscapeMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via check filtering)
  - **State of the system**: white king at `(4, 4)`; black rook at `(4, 0)`
  - **Expected output**: returned move list size is `6`
- **MG-TC16: GenerateLegalMoves_OnKingInCheck_ExcludesSquareStillInCheck** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via check filtering)
  - **State of the system**: white king at `(4, 4)`; black rook at `(4, 0)`
  - **Expected output**: no returned move has destination `(4, 3)`

---

## Method: `applyMoveToBoard(Piece[][] original, Move move)` (package-private static)

Scope: simulate a **NORMAL** move on a deep copy of the board for check filtering. En passant, castling, and promotion are deferred to later slices.

### Step 1: Equivalence Classes

- **Input: original board** — board snapshot before the move
- **Input: move type** — `NORMAL` (sole case in this slice)
- **Input: move endpoints** — `from` and `to` locations on the board
- **Output: returned board array** — new `Piece[][]` reference; simulates the move on a copy
- **Output: piece at destination on returned board** — `move.getTo()` holds the piece that moved from `from`
- **Output: piece at source on returned board** — `move.getFrom()` is `NONE` after the simulated move
- **Output: piece at source on `original`** — `move.getFrom()` unchanged (still the moving piece type)

### Step 2: Data Types (from BVA Catalog)


| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: original board | Collections | 8×8 `Piece[][]` with one white knight at `(4, 4)`; otherwise `NonePiece` |
| Input: move type | Cases | NORMAL |
| Input: move endpoints | Pairs of variables | `from` `(4, 4)`, `to` `(5, 6)` |
| Output: piece type at `move.getTo()` on returned board | Cases | KNIGHT |
| Output: piece type at `move.getFrom()` on returned board | Cases | NONE |
| Output: piece type at `move.getFrom()` on `original` after call | Cases | KNIGHT |


### Step 3: Boundary Values (from BVA Catalog)

**Move type — Cases:**

- NORMAL

**Move endpoints — Pairs of variables:**

- `from` `(4, 4)` (white knight), `to` `(5, 6)` on otherwise empty board

**Piece at destination on returned board — Cases:**

- `result[6][5].getType()` is `KNIGHT` (rank 6, file 5 = location `(5, 6)`)

**Piece at source on returned board — Cases:**

- `result[4][4].getType()` is `NONE` (rank 4, file 4 = location `(4, 4)`)

**Piece at source on `original` after call — Cases:**

- `original[4][4].getType()` remains `KNIGHT` (pre-move layout preserved on input array)

### Step 4: Test Cases (Each-Choice Strategy)

- **MG-TC18: ApplyMoveToBoard_OnNormalMove_DestinationHasMovingPiece** ( :white_check_mark: )
  - **Method(s) under test**: `applyMoveToBoard(Piece[][], Move)`
  - **State of the system**: white knight at `(4, 4)`; NORMAL move to `(5, 6)`
  - **Expected output**: returned board at `(5, 6)` has type `KNIGHT`
- **MG-TC19: ApplyMoveToBoard_OnNormalMove_SourceSquareIsEmpty** ( :white_check_mark: )
  - **Method(s) under test**: `applyMoveToBoard(Piece[][], Move)`
  - **State of the system**: same as MG-TC18
  - **Expected output**: returned board at `(4, 4)` has type `NONE`
- **MG-TC20: ApplyMoveToBoard_OnNormalMove_OriginalBoardUnchanged** ( :white_check_mark: )
  - **Method(s) under test**: `applyMoveToBoard(Piece[][], Move)`
  - **State of the system**: same as MG-TC18
  - **Expected output**: after the call, `original[4][4].getType()` is still `KNIGHT` (input array not modified)

---

## Method: `generateAllLegalMovesForColor(PieceColor color)`

Scope: aggregates `generateLegalMoves` for every piece of `color`, so check filtering can shrink the combined list (BVA catalog: **subset of a collection** — empty, one element, smaller than pseudo-legal aggregate, unchanged when nothing is filtered).

### Step 1: Equivalence Classes

- **Input: color** — side whose moves are collected
- **Input: board distribution** — one movable piece vs several; checkmate vs in-check vs unrestricted
- **Output: move list size** — total legal moves for that color after filtering

### Step 2: Data Types (from BVA Catalog)


| Equivalence class         | Catalog data type | Parameters                                                                 |
| ------------------------- | ----------------- | -------------------------------------------------------------------------- |
| Input: color              | Cases             | WHITE                                                                      |
| Input: board distribution | Collections       | single piece (knight, pawn, king); corner checkmate with two black rooks   |
| Output: move list size    | Counts            | `0`, `1`, `6`, `8`                                                         |


### Step 3: Boundary Values (from BVA Catalog)

**Color — Cases:**

- WHITE

**Board distribution — Collections:**

- White knight at `(4, 4)`; black king at `(0, 0)` — no moves removed by check filter
- White pawn at `(4, 5)` (rank `5`, file `4`) — only one one-step advance
- White king at `(0, 0)`; black rooks at `(0, 7)` and `(1, 7)`; black king at `(7, 0)` — checkmate, filtered subset empty
- White king at `(4, 4)`; black rook at `(4, 0)` — in check; two king moves removed from pseudo-legal `8`

**Move list size — Counts (subset boundaries):**

- `0` — filtered subset empty (checkmate)
- `1` — filtered subset has exactly one move
- `6` — filtered subset smaller than pseudo-legal king moves alone (`8`)
- `8` — filtered subset equals pseudo-legal aggregate for lone knight

### Step 4: Test Cases (Each-Choice Strategy)

- **MG-TC9: GenerateAllLegalMovesForColor_OnSingleWhiteKnight_ReturnsEightMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateAllLegalMovesForColor(PieceColor)`
  - **State of the system**: only movable white piece is knight at `(4, 4)`
  - **Expected output**: returned move list size is `8` for `PieceColor.WHITE`
- **MG-TC21: GenerateAllLegalMovesForColor_WhenWhiteCheckmated_ReturnsZeroMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateAllLegalMovesForColor(PieceColor)`
  - **State of the system**: white king at `(0, 0)`; black rooks at `(0, 7)` and `(1, 7)`; black king at `(7, 0)`
  - **Expected output**: returned move list size is `0` for `PieceColor.WHITE`
- **MG-TC22: GenerateAllLegalMovesForColor_OnPawnWithOnlyOneStep_ReturnsOneMove** ( :white_check_mark: )
  - **Method(s) under test**: `generateAllLegalMovesForColor(PieceColor)`
  - **State of the system**: only white piece is pawn at `(4, 5)` (not on starting rank; one empty square ahead)
  - **Expected output**: returned move list size is `1` for `PieceColor.WHITE`
- **MG-TC23: GenerateAllLegalMovesForColor_WhenOnlyKingInCheck_ReturnsSixMoves** ( :x: )
  - **Method(s) under test**: `generateAllLegalMovesForColor(PieceColor)`
  - **State of the system**: only white piece is king at `(4, 4)` in check from black rook at `(4, 0)`
  - **Expected output**: returned move list size is `6` for `PieceColor.WHITE`

---

## Method: `hasLegalMovesForColor(PieceColor color)`

### Step 1: Equivalence Classes

- **Input: color** — side queried for legal moves
- **Input: board distribution** — movable pieces present vs no pieces of that color
- **Input: king in check** — side in check but has a legal escape
- **Output: result** — whether at least one legal move exists

### Step 2: Data Types (from BVA Catalog)


| Equivalence class         | Catalog data type | Parameters                                 |
| ------------------------- | ----------------- | ------------------------------------------ |
| Input: color              | Cases             | WHITE, BLACK                               |
| Input: board distribution | Collections       | movable piece present, no pieces for color |
| Input: king in check      | Cases             | in check with legal escape                 |
| Output: result | Boolean | `true`, `false` |


### Step 3: Boundary Values (from BVA Catalog)

**Color — Cases:**

- WHITE
- BLACK

**Board distribution — Collections:**

- White knight at `(4, 4)` on otherwise empty board
- No black pieces on board

**King in check — Cases:**

- White king in check from rook on file but can escape off the file

**Result — Boolean:**

- `true` — movable white piece; white in check with legal escape
- `false` — no pieces of queried color on board

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

- **Input: color** — side whose king is queried
- **Input: king attack state** — king square attacked vs not attacked
- **Output: result** — whether that side's king is in check

### Step 2: Data Types (from BVA Catalog)


| Equivalence class        | Catalog data type | Parameters             |
| ------------------------ | ----------------- | ---------------------- |
| Input: color             | Cases             | WHITE, BLACK           |
| Input: king attack state | Cases             | attacked, not attacked |
| Output: result | Boolean | `true`, `false` |


### Step 3: Boundary Values (from BVA Catalog)

**Color — Cases:**

- WHITE
- BLACK

**King attack state — Cases:**

- Attacked: white king `(4, 4)`; black rook `(4, 0)`; clear file between
- Not attacked: white king `(4, 4)`; black rook at `(0, 0)` (no attack line)

**Result — Boolean:**

- `true` — king attacked on open file
- `false` — king not attacked

### Step 4: Test Cases (Each-Choice Strategy)

- **MG-TC12: IsInCheck_WhenRookAttacksKing_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)`
  - **State of the system**: white king on same file as black rook with empty squares between
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `true`
- **MG-TC13: IsInCheck_WhenKingNotAttacked_ReturnsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)`
  - **State of the system**: white king present; no black piece attacks it
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `false`

