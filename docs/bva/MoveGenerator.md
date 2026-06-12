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

- No en-passant target (`Optional.empty()`)
- En-passant target present at capture square (see MG-TC25–26, MG-TC32)

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
- **MG-TC53: GenerateLegalMoves_OnEmptySquare_ReturnsMutableEmptyList** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: `from` `(3, 3)` holds `NonePiece`
  - **Expected output**: returned list accepts an added move (mutable `ArrayList`)
- **MG-TC3: GenerateLegalMoves_OnKnightAtCenter_ReturnsEightMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: white knight at `(4, 4)`; all other squares `NonePiece`
  - **Expected output**: returned move list size is `8`
- **MG-TC70: GenerateLegalMoves_OnKnightAtCorner_ReturnsTwoMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `generateKnightMoves`)
  - **State of the system**: white knight at `(0, 0)`; board otherwise empty
  - **Expected output**: returned move list size is `2`
- **MG-TC71: GenerateLegalMoves_OnKnightAtCorner_IncludesDestinationOneTwo** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `generateKnightMoves`)
  - **State of the system**: white knight at `(0, 0)`; board otherwise empty
  - **Expected output**: returned moves include destination `(1, 2)`
- **MG-TC54: GenerateLegalMoves_OnKnightBlockedByFriendly_ExcludesBlockedSquare** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `generateKnightMoves`)
  - **State of the system**: white knight at `(4, 4)`; white pawn at `(5, 6)`
  - **Expected output**: no returned move has destination `(5, 6)`
- **MG-TC4: GenerateLegalMoves_OnBishopAtCenter_ReturnsThirteenMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: white bishop at `(4, 4)`; all other squares `NonePiece`
  - **Expected output**: returned move list size is `13`
- **MG-TC5: GenerateLegalMoves_OnRookAtCenter_ReturnsFourteenMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: white rook at `(4, 4)`; all other squares `NonePiece`
  - **Expected output**: returned move list size is `14`
- **MG-TC55: GenerateLegalMoves_OnRookBlockedByFriendly_ExcludesSquareBeyondFriendly** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `generateSlidingMoves`)
  - **State of the system**: white rook at `(4, 4)`; white pawn at `(4, 6)`
  - **Expected output**: no returned move has destination `(4, 6)` or `(4, 7)`
- **MG-TC72: GenerateLegalMoves_OnRookFacingEnemy_IncludesCaptureDestination** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `generateSlidingMoves`)
  - **State of the system**: white rook at `(4, 4)`; black rook at `(4, 6)`
  - **Expected output**: returned moves include destination `(4, 6)`
- **MG-TC73: GenerateLegalMoves_OnRookFacingEnemy_ReturnsThirteenMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `generateSlidingMoves`)
  - **State of the system**: white rook at `(4, 4)`; black rook at `(4, 6)`
  - **Expected output**: returned move list size is `13`
- **MG-TC6: GenerateLegalMoves_OnQueenAtCenter_ReturnsTwentySevenMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: white queen at `(4, 4)`; all other squares `NonePiece`
  - **Expected output**: returned move list size is `27`
- **MG-TC7: GenerateLegalMoves_OnKingAtCenter_ReturnsEightMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: white king at `(4, 4)`; all other squares `NonePiece`
  - **Expected output**: returned move list size is `8`
- **MG-TC56: GenerateLegalMoves_OnKingBlockedByFriendly_ExcludesFriendlySquare** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `generateKingMoves`)
  - **State of the system**: white king at `(4, 4)`; white pawn at `(5, 5)`
  - **Expected output**: no returned move has destination `(5, 5)`
- **MG-TC69: GenerateLegalMoves_OnKingAtCenter_IncludesNorthEastDestination** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `generateKingMoves`)
  - **State of the system**: white king at `(4, 4)`; board otherwise empty
  - **Expected output**: returned moves include destination `(5, 3)`
- **MG-TC74: GenerateLegalMoves_OnKingAtCenter_IncludesSouthWestDestination** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `generateKingMoves`)
  - **State of the system**: white king at `(4, 4)`; board otherwise empty
  - **Expected output**: returned moves include destination `(3, 5)`
- **MG-TC8: GenerateLegalMoves_OnWhitePawnAtStart_ReturnsOneAndTwoStepMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)`
  - **State of the system**: white pawn at `(4, 6)`; squares `(4, 5)` and `(4, 4)` empty
  - **Expected output**: returned move list size is `2`
- **MG-TC61: GenerateLegalMoves_OnWhitePawnAtStart_IncludesTwoStepDestination** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addPawnForwardMoves`)
  - **State of the system**: white pawn at `(4, 6)`; squares ahead empty
  - **Expected output**: returned moves include destination `(4, 4)`
- **MG-TC62: GenerateLegalMoves_OnWhitePawnAtStartWithBlockerAtTwoAhead_ExcludesTwoStep** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addPawnForwardMoves`)
  - **State of the system**: white pawn at `(4, 6)`; blocker at `(4, 4)`; `(4, 5)` empty
  - **Expected output**: no returned move has destination `(4, 4)`
- **MG-TC67: GenerateLegalMoves_OnBlackPawnAtStart_ReturnsOneAndTwoStepMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `generatePawnMoves`)
  - **State of the system**: black pawn at `(4, 1)`; squares ahead empty
  - **Expected output**: returned move list size is `2`

---

## Method / behavior: check filtering in `generateLegalMoves(Location from)`

Scope: after pseudo-legal generation, remove moves that leave the moving side's king in check. Uses `filterLegalMoves`, `leavesOwnKingInCheck`, and `applyMoveToBoard` (all move types; see `applyMoveToBoard` section).

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

Scope: simulate a move on a deep copy for **check filtering**. Supports `NORMAL`, `EN_PASSANT`, `CASTLING_KINGSIDE`, `CASTLING_QUEENSIDE`, and `PROMOTION`.

### Step 1: Equivalence Classes

- **Input: original board** — layout before the move
- **Input: move type** — `NORMAL`, `EN_PASSANT`, castling, `PROMOTION`
- **Input: move endpoints** — `from` / `to` (and promotion piece when applicable)
- **Output: returned board** — new `Piece[][]`; special moves update king/rook/pawn capture squares
- **Output: `original` unchanged** — input array not modified

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: original board | Collections | knight-only; en-passant layout; castling layout |
| Input: move type | Cases | NORMAL, EN_PASSANT, CASTLING_KINGSIDE, CASTLING_QUEENSIDE, PROMOTION |
| Input: move endpoints | Pairs of variables | per move type in Step 3 |
| Output: piece at destination on returned board | Cases | KNIGHT, PAWN, KING, QUEEN |
| Output: captured / bypassed squares on returned board | Cases | NONE at source, captured pawn square, cleared rook square |

### Step 3: Boundary Values (from BVA Catalog)

**Move type — Cases:**

- NORMAL — knight `(4, 4)` → `(5, 6)`
- EN_PASSANT — white pawn `(4, 3)` → `(5, 2)`; black pawn at `(5, 3)` (rank `3`, file `5`) removed
- CASTLING_KINGSIDE — move `to` = rook square `(7, 7)` (king-takes-rook encoding); king `(4, 7)` → `(6, 7)`; rook `(7, 7)` → `(5, 7)`
- CASTLING_QUEENSIDE — move `to` = rook square `(0, 7)`; king `(4, 7)` → `(2, 7)`; rook `(0, 7)` → `(3, 7)`
- CASTLING_KINGSIDE (Chess960 adjacent swap) — king `(5, 7)`, rook `(6, 7)`; pieces swap to `(6, 7)` / `(5, 7)`
- CASTLING_KINGSIDE (Chess960 king already on destination) — king `(6, 7)`, rook `(7, 7)`; king stays, rook → `(5, 7)`
- PROMOTION — white pawn `(4, 1)` → `(4, 0)` with `PieceType.QUEEN`

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
  - **Expected output**: after the call, `original[4][4].getType()` is still `KNIGHT`
- **MG-TC33: ApplyMoveToBoard_OnEnPassant_RemovesCapturedPawn** ( :white_check_mark: )
  - **Method(s) under test**: `applyMoveToBoard(Piece[][], Move)`
  - **State of the system**: white pawn `(4, 3)`; black pawn `(5, 3)`; `EN_PASSANT` to `(5, 2)`
  - **Expected output**: returned board at `(5, 3)` has type `NONE`
- **MG-TC34: ApplyMoveToBoard_OnKingsideCastling_RelocatesKingAndRook** ( :white_check_mark: )
  - **Method(s) under test**: `applyMoveToBoard(Piece[][], Move)`
  - **State of the system**: white king `(4, 7)`, rook `(7, 7)`; `CASTLING_KINGSIDE` to rook square `(7, 7)`
  - **Expected output**: returned board at `(6, 7)` is `KING` and at `(5, 7)` is `ROOK`
- **MG-TC35: ApplyMoveToBoard_OnQueensideCastling_RelocatesKingAndRook** ( :x: )
  - **Method(s) under test**: `applyMoveToBoard(Piece[][], Move)`
  - **State of the system**: white king `(4, 7)`, rook `(0, 7)`; `CASTLING_QUEENSIDE` to rook square `(0, 7)`
  - **Expected output**: returned board at `(2, 7)` is `KING` and at `(3, 7)` is `ROOK`
- **MG-TC90: ApplyMoveToBoard_OnAdjacentKingsideCastling_SwapsKingAndRook** ( :x: )
  - **Method(s) under test**: `applyMoveToBoard(Piece[][], Move)`
  - **State of the system**: white king `(5, 7)`, rook `(6, 7)` (Chess960 adjacent); `CASTLING_KINGSIDE` to `(6, 7)`
  - **Expected output**: returned board at `(6, 7)` is `KING` and at `(5, 7)` is `ROOK`
- **MG-TC91: ApplyMoveToBoard_OnKingAlreadyOnDestination_KingStaysRookMoves** ( :x: )
  - **Method(s) under test**: `applyMoveToBoard(Piece[][], Move)`
  - **State of the system**: white king `(6, 7)`, rook `(7, 7)` (Chess960); `CASTLING_KINGSIDE` to `(7, 7)`
  - **Expected output**: returned board at `(6, 7)` is `KING` and at `(5, 7)` is `ROOK`
- **MG-TC36: ApplyMoveToBoard_OnPromotion_DestinationHasQueen** ( :white_check_mark: )
  - **Method(s) under test**: `applyMoveToBoard(Piece[][], Move)`
  - **State of the system**: white pawn `(4, 1)`; `PROMOTION` to `(4, 0)` with `PieceType.QUEEN`
  - **Expected output**: returned board at `(4, 0)` has type `QUEEN`
- **MG-TC64: ApplyMoveToBoard_OnPromotionRook_DestinationHasRook** ( :white_check_mark: )
  - **Method(s) under test**: `applyMoveToBoard(Piece[][], Move)` (via `createPiece`)
  - **State of the system**: white pawn `(4, 1)`; `PROMOTION` to `(4, 0)` with `PieceType.ROOK`
  - **Expected output**: returned board at `(4, 0)` has type `ROOK`
- **MG-TC65: ApplyMoveToBoard_OnPromotionBishop_DestinationHasBishop** ( :white_check_mark: )
  - **Method(s) under test**: `applyMoveToBoard(Piece[][], Move)`
  - **State of the system**: white pawn `(4, 1)`; `PROMOTION` to `(4, 0)` with `PieceType.BISHOP`
  - **Expected output**: returned board at `(4, 0)` has type `BISHOP`
- **MG-TC66: ApplyMoveToBoard_OnPromotionKnight_DestinationHasKnight** ( :white_check_mark: )
  - **Method(s) under test**: `applyMoveToBoard(Piece[][], Move)`
  - **State of the system**: white pawn `(4, 1)`; `PROMOTION` to `(4, 0)` with `PieceType.KNIGHT`
  - **Expected output**: returned board at `(4, 0)` has type `KNIGHT`
---

## Method: `createPiece(PieceType type, PieceColor color)` (package-private static)

Scope: factory used by `applyMoveToBoard` for promotion pieces. Each `PieceType` maps to the corresponding piece class.

### Step 1: Equivalence Classes

- **Input: `type`** — ROOK, BISHOP, KNIGHT, PAWN, KING, NONE
- **Output: piece type** — matches input type

### Step 2: Data Types (from BVA Catalog)

| Variable / output | Catalog data type |
| ----------------- | ----------------- |
| `type` | Cases |
| Returned piece | Cases |

### Step 3: Concrete boundary values

- One TC per `PieceType` enum value

### Step 4: Test Cases (Each-Choice Strategy)

- **MG-TC47: CreatePiece_OnRookType_ReturnsRook** ( :white_check_mark: )
  - **Method(s) under test**: `createPiece(PieceType, PieceColor)`
  - **State of the system**: `PieceType.ROOK`, `PieceColor.WHITE`
  - **Expected output**: returned piece type is `ROOK`
- **MG-TC48: CreatePiece_OnBishopType_ReturnsBishop** ( :white_check_mark: )
  - **Method(s) under test**: `createPiece(PieceType, PieceColor)`
  - **State of the system**: `PieceType.BISHOP`, `PieceColor.WHITE`
  - **Expected output**: returned piece type is `BISHOP`
- **MG-TC49: CreatePiece_OnKnightType_ReturnsKnight** ( :white_check_mark: )
  - **Method(s) under test**: `createPiece(PieceType, PieceColor)`
  - **State of the system**: `PieceType.KNIGHT`, `PieceColor.WHITE`
  - **Expected output**: returned piece type is `KNIGHT`
- **MG-TC50: CreatePiece_OnPawnType_ReturnsPawn** ( :white_check_mark: )
  - **Method(s) under test**: `createPiece(PieceType, PieceColor)`
  - **State of the system**: `PieceType.PAWN`, `PieceColor.WHITE`
  - **Expected output**: returned piece type is `PAWN`
- **MG-TC51: CreatePiece_OnKingType_ReturnsKing** ( :white_check_mark: )
  - **Method(s) under test**: `createPiece(PieceType, PieceColor)`
  - **State of the system**: `PieceType.KING`, `PieceColor.WHITE`
  - **Expected output**: returned piece type is `KING`
- **MG-TC52: CreatePiece_OnNoneType_ReturnsNonePiece** ( :white_check_mark: )
  - **Method(s) under test**: `createPiece(PieceType, PieceColor)`
  - **State of the system**: `PieceType.NONE`, `PieceColor.WHITE`
  - **Expected output**: returned piece type is `NONE`

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
| Output: move list size    | Counts            | `0`, `1`, `6`, `8`, `17`                                                   |


### Step 3: Boundary Values (from BVA Catalog)

**Color — Cases:**

- WHITE

**Board distribution — Collections:**

- White knight at `(4, 4)`; black king at `(0, 0)` — no moves removed by check filter
- White pawn at `(4, 5)` (rank `5`, file `4`) — only one one-step advance
- White king at `(0, 0)`; black rooks at `(0, 7)` and `(1, 7)`; black king at `(7, 0)` — checkmate, filtered subset empty
- White king at `(4, 4)`; black rook at `(4, 0)` — in check; two king moves removed from pseudo-legal `8`
- White king at `(2, 2)`; white bishop at `(2, 3)`; black rook at `(7, 7)` (does not pin) — two movable white pieces

**Move list size — Counts (subset boundaries):**

- `0` — filtered subset empty (checkmate)
- `1` — filtered subset has exactly one move
- `6` — filtered subset smaller than pseudo-legal king moves alone (`8`)
- `8` — filtered subset equals pseudo-legal aggregate for lone knight
- `17` — aggregate of two pieces (`8` king + `9` bishop); no check filter applied

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
- **MG-TC23: GenerateAllLegalMovesForColor_WhenOnlyKingInCheck_ReturnsSixMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateAllLegalMovesForColor(PieceColor)`
  - **State of the system**: only white piece is king at `(4, 4)` in check from black rook at `(4, 0)`
  - **Expected output**: returned move list size is `6` for `PieceColor.WHITE`
- **MG-TC24: GenerateAllLegalMovesForColor_OnKingAndBishop_ReturnsSeventeenMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateAllLegalMovesForColor(PieceColor)`
  - **State of the system**: white king at `(2, 2)` and bishop at `(2, 3)`; black rook at `(7, 7)` does not pin; black king at `(0, 0)`
  - **Expected output**: returned move list size is `17` for `PieceColor.WHITE` (`8` + `9` per-piece legal counts summed)

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
- Attacked by pawn: black king `(4, 4)`; white pawn `(3, 5)`
- Attacked by knight: white king `(4, 4)`; black knight `(6, 5)` or `(2, 5)`
- Attacked by bishop/queen slider: white king `(4, 4)`; black bishop `(0, 0)` or queen `(4, 0)`
- Attacked by adjacent king: white king `(4, 4)`; black king `(5, 5)` or `(3, 3)`
- Missing king: board with no white king
- Not attacked: white king `(4, 4)`; black rook at `(0, 0)` (no attack line)
- Near-miss: knight one square off L-shape; pawn one file off; king two squares away

**Result — Boolean:**

- `true` — king attacked on open file, by piece type, or adjacent king
- `false` — king not attacked; no king for color

### Step 4: Test Cases (Each-Choice Strategy)

- **MG-TC12: IsInCheck_WhenRookAttacksKing_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)`
  - **State of the system**: white king on same file as black rook with empty squares between
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `true`
- **MG-TC13: IsInCheck_WhenKingNotAttacked_ReturnsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)`
  - **State of the system**: white king present; no black piece attacks it
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `false`
- **MG-TC41: IsInCheck_WhenNoKingForColor_ReturnsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)`
  - **State of the system**: board with no white king
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `false`
- **MG-TC42: IsInCheck_WhenPawnAttacksKing_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)` (via `isPawnAttacking`)
  - **State of the system**: black king at `(4, 4)`; white pawn at `(3, 5)`
  - **Expected output**: `isInCheck(PieceColor.BLACK)` is `true`
- **MG-TC43: IsInCheck_WhenKnightAttacksKing_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)` (via `isKnightAttacking`)
  - **State of the system**: white king at `(4, 4)`; black knight at `(6, 5)`
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `true`
- **MG-TC44: IsInCheck_WhenBishopAttacksKing_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)` (via `isSliderAttacking`)
  - **State of the system**: white king at `(4, 4)`; black bishop at `(0, 0)`
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `true`
- **MG-TC45: IsInCheck_WhenQueenAttacksKing_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)` (via `isSliderAttacking`)
  - **State of the system**: white king at `(4, 4)`; black queen at `(4, 0)`
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `true`
- **MG-TC46: IsInCheck_WhenAdjacentEnemyKingAttacksKing_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)` (via `isKingAttacking`)
  - **State of the system**: white king at `(4, 4)`; black king at `(5, 5)`
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `true`
- **MG-TC75: IsInCheck_WhenKnightOnExactAttackSquare_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)` (via `isKnightAttacking`)
  - **State of the system**: white king at `(4, 4)`; black knight at `(2, 5)`
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `true`
- **MG-TC76: IsInCheck_WhenPawnOnExactAttackSquare_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)` (via `isPawnAttacking`)
  - **State of the system**: white king at `(3, 4)`; black pawn at `(2, 3)`
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `true`
- **MG-TC77: IsInCheck_WhenKingOnExactAdjacentSquare_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)` (via `isKingAttacking`)
  - **State of the system**: white king at `(4, 4)`; black king at `(3, 3)`
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `true`
- **MG-TC78: IsInCheck_WhenKnightOneSquareOffAttackLine_ReturnsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)` (via `isKnightAttacking`)
  - **State of the system**: white king at `(4, 4)`; black knight at `(2, 6)` (not on L-shape)
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `false`
- **MG-TC79: IsInCheck_WhenPawnOneFileOffAttackLine_ReturnsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)` (via `isPawnAttacking`)
  - **State of the system**: white king at `(3, 4)`; black pawn at `(2, 4)` (same file, not diagonal)
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `false`
- **MG-TC80: IsInCheck_WhenKingTwoSquaresAway_ReturnsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `isInCheck(PieceColor)` (via `isKingAttacking`)
  - **State of the system**: white king at `(4, 4)`; black king at `(6, 6)`
  - **Expected output**: `isInCheck(PieceColor.WHITE)` is `false`

---

## Method / behavior: en passant and castling in `generateLegalMoves(Location from)`

Scope: pseudo-legal **en passant** (via stored `enPassantTarget`) and **castling** (king/rook `hasMoved`, clear path, king path not attacked). Exercised through `generateLegalMoves`; check filtering above still applies to returned moves.

### Step 1: Equivalence Classes

- **Input: en-passant target** — absent vs present on valid capture square vs present on wrong rank
- **Input: castling side** — kingside vs queenside unmoved rook
- **Input: king/rook movement state** — both unmoved vs king moved vs rook moved
- **Input: castling path safety** — transit squares clear and unattacked vs square under attack
- **Output: move list contents** — special `MoveType` present vs absent for a given destination

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type | Parameters |
| --- | --- | --- |
| Input: en-passant target | Cases | no target, valid target at `(5, 2)`, invalid target at `(5, 4)` |
| Input: castling side | Cases | kingside, queenside |
| Input: king/rook movement state | Cases | both unmoved, king moved, rook moved |
| Input: castling path safety | Cases | safe path, attacked transit square `(5, 7)` |
| Output: move list contents | Collections | includes / excludes `EN_PASSANT`, `CASTLING_KINGSIDE`, `CASTLING_QUEENSIDE` |

### Step 3: Boundary Values (from BVA Catalog)

**En-passant target — Cases:**

- No target — `Optional.empty()` for pawn at `(4, 3)`
- Valid target — `(5, 2)` adjacent to white pawn at `(4, 3)` on rank `2`
- Invalid target — `(5, 4)` (wrong rank; not `rank + direction`)

**Castling side — Cases:**

- Kingside — white king `(4, 7)`, rook `(7, 7)` unmoved; move `to` = rook square `(7, 7)` (king-takes-rook encoding)
- Queenside — white king `(4, 7)`, rook `(0, 7)` unmoved; move `to` = rook square `(0, 7)`
- Kingside, Chess960 adjacent — white king `(5, 7)`, rook `(6, 7)` unmoved; move `to` = `(6, 7)`

**King/rook movement — Cases:**

- Both unmoved — castling allowed when path safe
- King moved — `king.changeToMoved()`; no castling types
- Kingside rook moved — `rook.changeToMoved()` at `(7, 7)`; kingside excluded

**Castling path safety — Cases:**

- Clear path — squares between king and rook empty; no attack on king path
- Attacked transit — black rook at `(5, 0)` attacks `(5, 7)` on kingside layout

### Step 4: Test Cases (Each-Choice Strategy)

`addEnPassantMoves` and `addCastlingMoves` are private; exercised indirectly through `generateLegalMoves`.

- **MG-TC25: GenerateLegalMoves_OnWhitePawnWithEnPassantTarget_IncludesEnPassantMove** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addEnPassantMoves`)
  - **State of the system**: white pawn at `(4, 3)`; `enPassantTarget` at `(5, 2)`
  - **Expected output**: returned moves include destination `(5, 2)` with `MoveType.EN_PASSANT`
- **MG-TC68: GenerateLegalMoves_OnBlackPawnWithEnPassantTarget_IncludesEnPassantMove** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addEnPassantMoves`)
  - **State of the system**: black pawn at `(4, 4)`; `enPassantTarget` at `(3, 5)`
  - **Expected output**: returned moves include destination `(3, 5)` with `MoveType.EN_PASSANT`
- **MG-TC26: GenerateLegalMoves_OnWhitePawnWithoutEnPassantTarget_ExcludesEnPassantMove** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addEnPassantMoves`)
  - **State of the system**: white pawn at `(4, 3)`; `Optional.empty()` en-passant target
  - **Expected output**: no returned move has `MoveType.EN_PASSANT`
- **MG-TC27: GenerateLegalMoves_OnUnmovedKingWithClearKingsidePath_IncludesKingsideCastling** ( :x: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addCastlingMoves`)
  - **State of the system**: white king `(4, 7)` and rook `(7, 7)` unmoved; path clear and safe
  - **Expected output**: returned moves include destination `(7, 7)` (rook square) with `MoveType.CASTLING_KINGSIDE`
- **MG-TC28: GenerateLegalMoves_OnMovedKing_ExcludesCastlingMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addCastlingMoves`)
  - **State of the system**: same as MG-TC27 but king has `hasMoved() == true`
  - **Expected output**: no returned move has `MoveType.CASTLING_KINGSIDE` or `MoveType.CASTLING_QUEENSIDE`
- **MG-TC29: GenerateLegalMoves_OnKingsidePathSquareUnderAttack_ExcludesKingsideCastling** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addCastlingMoves`)
  - **State of the system**: same as MG-TC27 with black rook at `(5, 0)` attacking transit square `(5, 7)`
  - **Expected output**: no returned move has `MoveType.CASTLING_KINGSIDE`
- **MG-TC30: GenerateLegalMoves_OnUnmovedKingWithClearQueensidePath_IncludesQueensideCastling** ( :x: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addCastlingMoves`)
  - **State of the system**: white king `(4, 7)` and rook `(0, 7)` unmoved; squares `(1, 7)`, `(2, 7)`, `(3, 7)` empty; path safe
  - **Expected output**: returned moves include destination `(0, 7)` (rook square) with `MoveType.CASTLING_QUEENSIDE`
- **MG-TC31: GenerateLegalMoves_OnMovedKingsideRook_ExcludesKingsideCastling** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addCastlingMoves`)
  - **State of the system**: white king `(4, 7)` unmoved; rook `(7, 7)` with `hasMoved() == true`
  - **Expected output**: no returned move has `MoveType.CASTLING_KINGSIDE`
- **MG-TC32: GenerateLegalMoves_OnEnPassantTargetWrongRank_ExcludesEnPassantMove** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addEnPassantMoves`)
  - **State of the system**: white pawn at `(4, 3)`; `enPassantTarget` at `(5, 4)` (not on capture rank `2`)
  - **Expected output**: no returned move has `MoveType.EN_PASSANT`
- **MG-TC60: GenerateLegalMoves_OnEnPassantTargetSameFile_ExcludesEnPassantMove** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addEnPassantMoves`)
  - **State of the system**: white pawn at `(4, 3)`; `enPassantTarget` at `(4, 2)` (same file)
  - **Expected output**: no returned move has `MoveType.EN_PASSANT`
- **MG-TC57: GenerateLegalMoves_OnKingsideCastlingWithBlockingPiece_ExcludesKingsideCastling** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `isPathClearForCastling`)
  - **State of the system**: white king `(4, 7)`, rook `(7, 7)`; white bishop at `(5, 7)`
  - **Expected output**: no returned move has `MoveType.CASTLING_KINGSIDE`
- **MG-TC58: GenerateLegalMoves_OnQueensideCastlingWithBlockingPiece_ExcludesQueensideCastling** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `isPathClearForCastling`)
  - **State of the system**: white king `(4, 7)`, rook `(0, 7)`; white knight at `(1, 7)`
  - **Expected output**: no returned move has `MoveType.CASTLING_QUEENSIDE`
- **MG-TC59: GenerateLegalMoves_OnMovedQueensideRook_ExcludesQueensideCastling** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `findUnmovedRookFileIn`)
  - **State of the system**: white king `(4, 7)` unmoved; rook `(0, 7)` with `hasMoved() == true`
  - **Expected output**: no returned move has `MoveType.CASTLING_QUEENSIDE`
- **MG-TC82: GenerateLegalMoves_OnKingWithOnlyKingsideRook_ExcludesQueensideCastling** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addCastlingMoves`)
  - **State of the system**: white king `(4, 7)` and kingside rook `(7, 7)` only; no queenside rook
  - **Expected output**: no returned move has `MoveType.CASTLING_QUEENSIDE`
- **MG-TC83: GenerateLegalMoves_OnKingWithoutRooks_ExcludesAllCastling** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addCastlingMoves`)
  - **State of the system**: white king `(4, 7)` alone; no rooks on back rank
  - **Expected output**: no returned move has `MoveType.CASTLING_KINGSIDE`
- **MG-TC92: GenerateLegalMoves_OnAdjacentKingAndRook_IncludesKingsideCastlingToRookSquare** ( :x: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addCastlingMoves`, `isPathClearForCastling`)
  - **State of the system**: white king `(5, 7)`, rook `(6, 7)` unmoved (Chess960 adjacent); destination squares occupied only by the castling pieces themselves; path safe
  - **Expected output**: returned moves include destination `(6, 7)` (rook square) with `MoveType.CASTLING_KINGSIDE`

---

## Method: generateLegalMoves(Location from) — pawn promotion candidates and captures

### Step 1: Inputs and outputs

| Input / state      | Equivalence classes                                                      |
| ------------------ | ------------------------------------------------------------------------ |
| from               | Pairs of variables - file/rank on board                                  |
| pawn rank          | Cases - at promotion rank boundary vs elsewhere                          |
| board occupancy    | Cases - forward clear, diagonal enemy, diagonal friendly, en passant set |
| Output             | Collection - list of legal moves (MoveType may be PROMOTION, EN_PASSANT) |

### Step 2: Catalog data types

| Variable / output | Catalog data type |
| ----------------- | ----------------- |
| from.x, from.y    | Intervals [0,7]   |
| promotionRank     | Cases             |
| move list         | Collections       |

### Step 3: Concrete boundary values

- White promotion rank: 0. White pawn at rank 1 is one step from back rank.
- Diagonal capture: enemy piece at (file±1, rank+direction); friendly diagonal skipped.
- Capture-promotion: enemy piece at (file±1, promotionRank).
- Back rank with no captures: pawn already on rank 0 with no legal moves.

### Step 4: Test Cases (Each-Choice Strategy)

`addPawnForwardMoves`, `addPawnCaptureMoves`, and `addPromotionMoves` are private; exercised indirectly through `generateLegalMoves`.

- **MG-TC37: GenerateLegalMoves_OnWhitePawnOneStepFromBackRank_ReturnsFourMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addPawnForwardMoves`, `addPromotionMoves`)
  - **State of the system**: white pawn at `(4, 1)`; square `(4, 0)` empty; no diagonals; no en-passant target
  - **Expected output**: returned move list size is `4` (four `PROMOTION` moves; no normal forward move)
- **MG-TC38: GenerateLegalMoves_OnWhitePawnWithEnemyDiagonal_ReturnsTwoMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addPawnCaptureMoves`)
  - **State of the system**: white pawn at `(4, 4)`; black rook at `(5, 3)`; square `(4, 3)` empty; no en-passant target
  - **Expected output**: returned move list size is `2` (forward to `(4, 3)` + capture to `(5, 3)`)
- **MG-TC39: GenerateLegalMoves_OnWhitePawnCaptureToBackRank_ReturnsEightMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addPawnCaptureMoves`, `addPromotionMoves`)
  - **State of the system**: white pawn at `(4, 1)`; black rook at `(5, 0)`; square `(4, 0)` empty; no en-passant target
  - **Expected output**: returned move list size is `8` (four forward `PROMOTION` + four capture `PROMOTION`)
- **MG-TC40: GenerateLegalMoves_OnWhitePawnWithEnPassantTarget_ReturnsTwoMoves** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addPawnForwardMoves`, `addEnPassantMoves`)
  - **State of the system**: white pawn at `(4, 3)`; `enPassantTarget` at `(5, 2)`; square `(4, 2)` empty
  - **Expected output**: returned move list size is `2` (forward to `(4, 2)` + `EN_PASSANT` to `(5, 2)`)
- **MG-TC63: GenerateLegalMoves_OnWhitePawnWithFriendlyDiagonal_SkipsDiagonalCapture** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addPawnCaptureMoves`)
  - **State of the system**: white pawn at `(4, 4)`; white pawn at `(5, 3)`; `(4, 3)` empty
  - **Expected output**: returned move list size is `1` (forward only)
- **MG-TC84: GenerateLegalMoves_OnWhitePawnWithEnemyDiagonal_IncludesCaptureDestination** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addPawnCaptureMoves`)
  - **State of the system**: white pawn at `(4, 4)`; black rook at `(5, 3)`
  - **Expected output**: returned moves include destination `(5, 3)`
- **MG-TC85: GenerateLegalMoves_OnWhitePawnWithLeftDiagonalEnemy_IncludesLeftCaptureOnly** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addPawnCaptureMoves`)
  - **State of the system**: white pawn at `(4, 4)`; black rook at `(3, 3)` only
  - **Expected output**: returned moves include destination `(3, 3)`
- **MG-TC86: GenerateLegalMoves_OnWhitePawnWithLeftDiagonalEnemy_ExcludesRightCapture** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `addPawnCaptureMoves`)
  - **State of the system**: white pawn at `(4, 4)`; black rook at `(3, 3)` only
  - **Expected output**: no returned move has destination `(5, 3)`
- **MG-TC87: GenerateLegalMoves_OnWhitePawnAtBackRankWithNoCaptures_ReturnsEmptyList** ( :white_check_mark: )
  - **Method(s) under test**: `generateLegalMoves(Location)` (via `generatePawnMoves`)
  - **State of the system**: white pawn at `(4, 0)`; no diagonal enemies
  - **Expected output**: returned move list size is `0`

---

## Method: `generatePseudoLegalMoves(Location from, Piece piece)` (private)

Scope: fallback when `piece.getType()` is `NONE`; exercised via reflection in unit tests.

### Step 4: Test Cases

- **MG-TC88: GeneratePseudoLegalMoves_OnNonePiece_ReturnsEmptyList** ( :white_check_mark: )
  - **Method(s) under test**: `generatePseudoLegalMoves(Location, Piece)` (via reflection)
  - **State of the system**: `NonePiece` at `(3, 3)`
  - **Expected output**: returned move list size is `0`
- **MG-TC89: GeneratePseudoLegalMoves_OnNonePiece_ReturnsMutableEmptyList** ( :white_check_mark: )
  - **Method(s) under test**: `generatePseudoLegalMoves(Location, Piece)` (via reflection)
  - **State of the system**: `NonePiece` at `(3, 3)`
  - **Expected output**: returned list accepts an added move (mutable `ArrayList`)
