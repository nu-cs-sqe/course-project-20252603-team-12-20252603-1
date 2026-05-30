# BVA Analysis for Move

## Method: `Move(Location from, Location to)`

### Step 1: Equivalence Classes

- **Input: from location** — the starting square of the move
- **Input: to location** — the destination square of the move
- **Output: move type** — the `MoveType` assigned by this constructor
- **Output: promotion type** — whether a promotion piece is stored

### Step 2: Data Types

| Equivalence class       | Catalog data type  | Parameters                              |
| ----------------------- | ------------------ | --------------------------------------- |
| Input: from location    | Representative value | any valid `Location`                  |
| Input: to location      | Representative value | any valid `Location`                  |
| Output: move type       | Cases              | `NORMAL` (sole case this constructor produces) |
| Output: promotion type  | Boolean            | absent / present — always absent        |

### Step 3: Boundary Values

- **from / to:** representative values; the constructor stores them without inspection
- **Move type:** `NORMAL` (single case)
- **Promotion type:** `Optional.empty()` (single case for this constructor)

### Step 4: Test Cases (Each-Choice Strategy)

- **TC1: Constructor_TwoArg_MoveTypeIsNormal** ( :x: )
  - **Method(s) under test**: `Move(Location from, Location to)`, `getType()`
  - **State of the system**: `new Move(new Location(1, 2), new Location(3, 4))` constructed
  - **Expected output**: `getType()` returns `NORMAL`

- **TC2: Constructor_TwoArg_PromotionTypeIsEmpty** ( :x: )
  - **Method(s) under test**: `Move(Location from, Location to)`, `getPromotionType()`
  - **State of the system**: `new Move(new Location(1, 2), new Location(3, 4))` constructed
  - **Expected output**: `getPromotionType()` returns `Optional.empty()`

---

## Method: `Move(Location from, Location to, MoveType type)`

### Step 1: Equivalence Classes

- **Input: MoveType** — which move type is assigned
- **Output: move type** — the stored `MoveType`
- **Output: promotion type** — whether a promotion piece is present

### Step 2: Data Types

| Equivalence class       | Catalog data type | Parameters                                                              |
| ----------------------- | ----------------- | ----------------------------------------------------------------------- |
| Input: `MoveType`       | Cases             | `NORMAL`, `EN_PASSANT`, `CASTLING_KINGSIDE`, `CASTLING_QUEENSIDE`, `PROMOTION` |
| Output: move type       | Cases             | `NORMAL`, `EN_PASSANT`, `CASTLING_KINGSIDE`, `CASTLING_QUEENSIDE`, `PROMOTION` |
| Output: promotion type  | Boolean           | absent / present — always absent                                        |

### Step 3: Boundary Values

**MoveType — Cases:**
- `NORMAL`, `EN_PASSANT`, `CASTLING_KINGSIDE`, `CASTLING_QUEENSIDE`, `PROMOTION`

**Promotion type — Boolean:**
- absent (always applies for this constructor)

### Step 4: Test Cases (Each-Choice Strategy)

- **TC3: Constructor_ThreeArg_WhenMoveTypeMatches_MoveTypeIsCorrect** ( :x: )
  - **Method(s) under test**: `Move(Location from, Location to, MoveType type)`, `getType()`
  - **State of the system**: `new Move(from, to, type)` constructed with each `MoveType` value
  - **Expected output**: `getType()` returns the same `MoveType` that was passed in
  - **Note**: TC4–TC8 are covered by this test case as a parameterized test

- **TC4: Constructor_ThreeArg_WhenTypeIsNormal_MoveTypeIsNormal** ( :x: )
  - **Covered by**: TC3 (parameterized test)

- **TC5: Constructor_ThreeArg_WhenTypeIsEnPassant_MoveTypeIsEnPassant** ( :x: )
  - **Covered by**: TC3 (parameterized test)

- **TC6: Constructor_ThreeArg_WhenTypeIsCastlingKingside_MoveTypeIsCastlingKingside** ( :x: )
  - **Covered by**: TC3 (parameterized test)

- **TC7: Constructor_ThreeArg_WhenTypeIsCastlingQueenside_MoveTypeIsCastlingQueenside** ( :x: )
  - **Covered by**: TC3 (parameterized test)

- **TC8: Constructor_ThreeArg_WhenTypeIsPromotion_MoveTypeIsPromotion** ( :x: )
  - **Covered by**: TC3 (parameterized test)

- **TC9: Constructor_ThreeArg_WhenTypeIsPromotion_PromotionTypeIsEmpty** ( :x: )
  - **Method(s) under test**: `Move(Location from, Location to, MoveType type)`, `getPromotionType()`
  - **State of the system**: `new Move(from, to, MoveType.PROMOTION)` constructed — edge case: `PROMOTION` type passed but no piece supplied
  - **Expected output**: `getPromotionType()` returns `Optional.empty()`

---

## Method: `Move(Location from, Location to, MoveType type, PieceType promotionPiece)`

### Step 1: Equivalence Classes

- **Input: MoveType** — which move type is assigned
- **Input: PieceType (promotionPiece)** — the piece stored as the promotion type
- **Output: move type** — the stored `MoveType`
- **Output: promotion type value** — the `PieceType` wrapped in the `Optional`

### Step 2: Data Types

| Equivalence class                     | Catalog data type | Parameters                                                              |
| ------------------------------------- | ----------------- | ----------------------------------------------------------------------- |
| Input: `MoveType`                     | Cases             | `NORMAL`, `EN_PASSANT`, `CASTLING_KINGSIDE`, `CASTLING_QUEENSIDE`, `PROMOTION` |
| Input: `PieceType` (promotion piece)  | Cases             | `ROOK`, `KNIGHT`, `BISHOP`, `QUEEN`, `KING`, `PAWN`, `NONE`            |
| Output: move type                     | Cases             | `NORMAL`, `EN_PASSANT`, `CASTLING_KINGSIDE`, `CASTLING_QUEENSIDE`, `PROMOTION` |
| Output: promotion type value          | Cases             | `ROOK`, `KNIGHT`, `BISHOP`, `QUEEN`, `KING`, `PAWN`, `NONE`            |

### Step 3: Boundary Values

**MoveType — Cases:**
- `NORMAL`, `EN_PASSANT`, `CASTLING_KINGSIDE`, `CASTLING_QUEENSIDE`, `PROMOTION`

**PieceType (promotion piece) — Cases:**
- `ROOK`, `KNIGHT`, `BISHOP`, `QUEEN` (domain-valid promotion targets)
- `KING`, `PAWN`, `NONE` (domain-invalid but accepted by the constructor)

### Step 4: Test Cases (Each-Choice Strategy)

- **TC10: Constructor_FourArg_WhenMoveTypeMatches_MoveTypeIsCorrect** ( :x: )
  - **Method(s) under test**: `Move(Location from, Location to, MoveType type, PieceType promotionPiece)`, `getType()`
  - **State of the system**: `new Move(from, to, type, PieceType.QUEEN)` constructed with each `MoveType` value
  - **Expected output**: `getType()` returns the same `MoveType` that was passed in
  - **Note**: TC11–TC14 are covered by this test case as a parameterized test

- **TC11: Constructor_FourArg_WhenTypeIsNormal_MoveTypeIsNormal** ( :x: )
  - **Covered by**: TC10 (parameterized test)

- **TC12: Constructor_FourArg_WhenTypeIsEnPassant_MoveTypeIsEnPassant** ( :x: )
  - **Covered by**: TC10 (parameterized test)

- **TC13: Constructor_FourArg_WhenTypeIsCastlingKingside_MoveTypeIsCastlingKingside** ( :x: )
  - **Covered by**: TC10 (parameterized test)

- **TC14: Constructor_FourArg_WhenTypeIsCastlingQueenside_MoveTypeIsCastlingQueenside** ( :x: )
  - **Covered by**: TC10 (parameterized test)

- **TC15: Constructor_FourArg_WhenPromotionPieceMatches_PromotionTypeContainsPiece** ( :x: )
  - **Method(s) under test**: `Move(Location from, Location to, MoveType type, PieceType promotionPiece)`, `getPromotionType()`
  - **State of the system**: `new Move(from, to, MoveType.PROMOTION, piece)` constructed with each `PieceType` value
  - **Expected output**: `getPromotionType()` returns `Optional.of(piece)` matching the passed-in `PieceType`
  - **Note**: TC16–TC21 are covered by this test case as a parameterized test

- **TC16: Constructor_FourArg_WhenPromotionPieceIsRook_PromotionTypeIsRook** ( :x: )
  - **Covered by**: TC15 (parameterized test)

- **TC17: Constructor_FourArg_WhenPromotionPieceIsKnight_PromotionTypeIsKnight** ( :x: )
  - **Covered by**: TC15 (parameterized test)

- **TC18: Constructor_FourArg_WhenPromotionPieceIsBishop_PromotionTypeIsBishop** ( :x: )
  - **Covered by**: TC15 (parameterized test)

- **TC19: Constructor_FourArg_WhenPromotionPieceIsQueen_PromotionTypeIsQueen** ( :x: )
  - **Covered by**: TC15 (parameterized test)

- **TC20: Constructor_FourArg_WhenPromotionPieceIsKing_PromotionTypeIsKing** ( :x: )
  - **Covered by**: TC15 (parameterized test)

- **TC21: Constructor_FourArg_WhenPromotionPieceIsPawn_PromotionTypeIsPawn** ( :x: )
  - **Covered by**: TC15 (parameterized test)

- **TC22: Constructor_FourArg_WhenPromotionPieceIsNone_PromotionTypeIsNone** ( :x: )
  - **Covered by**: TC15 (parameterized test)

---

## Method: `getPromotionType()`

### Step 1: Equivalence Classes

- **Output: promotion type state** — whether the `Optional` is empty or contains a value

### Step 2: Data Types

| Equivalence class          | Catalog data type | Parameters                  |
| -------------------------- | ----------------- | --------------------------- |
| Output: promotion type state | Boolean         | absent / present            |

### Step 3: Boundary Values

- **absent:** result of the two-arg or three-arg constructor
- **present:** result of the four-arg constructor or `withPromotionType()`

### Step 4: Test Cases (Each-Choice Strategy)

- **TC23: GetPromotionType_WhenConstructedWithTwoArg_ReturnsEmpty** ( :x: )
  - **Covered by**: TC2

- **TC24: GetPromotionType_WhenConstructedWithThreeArg_ReturnsEmpty** ( :x: )
  - **Covered by**: TC9

- **TC25: GetPromotionType_WhenConstructedWithFourArg_ReturnsPresentOptional** ( :x: )
  - **Covered by**: TC15

---

## Method: `withPromotionType(PieceType piece)`

### Step 1: Equivalence Classes

- **Input: PieceType (piece)** — the promotion piece type for the new move
- **Input: original move's MoveType** — the type the original move was constructed with
- **Output: returned move's type** — should always be `PROMOTION`
- **Output: returned move's promotion type** — should be `Optional.of(piece)`
- **Output: returned move's from / to** — should match the original move's locations
- **Output: original move** — should be unchanged (method is non-mutating)

### Step 2: Data Types

| Equivalence class                  | Catalog data type | Parameters                                                              |
| ---------------------------------- | ----------------- | ----------------------------------------------------------------------- |
| Input: `PieceType` (piece)         | Cases             | `ROOK`, `KNIGHT`, `BISHOP`, `QUEEN`, `KING`, `PAWN`, `NONE`            |
| Input: original move's `MoveType`  | Cases             | `NORMAL`, `EN_PASSANT`, `CASTLING_KINGSIDE`, `CASTLING_QUEENSIDE`, `PROMOTION` |
| Output: returned move's type       | Cases             | `PROMOTION` (sole case)                                                 |
| Output: returned move's promotion type | Cases         | `ROOK`, `KNIGHT`, `BISHOP`, `QUEEN`, `KING`, `PAWN`, `NONE`            |
| Output: original move mutated?     | Boolean           | mutated / not mutated — always not mutated                              |

### Step 3: Boundary Values

**PieceType — Cases:**
- `ROOK`, `KNIGHT`, `BISHOP`, `QUEEN` (domain-valid promotion targets)
- `KING`, `PAWN`, `NONE` (domain-invalid but accepted)

**Original move's MoveType — Cases:**
- `NORMAL` (typical: a normal pawn move becomes a promotion)
- `EN_PASSANT`, `CASTLING_KINGSIDE`, `CASTLING_QUEENSIDE`, `PROMOTION` (atypical but structurally possible)

**Returned move's type:** always `PROMOTION` regardless of original type

**Original move mutation:** never mutated

### Step 4: Test Cases (Each-Choice Strategy)

- **TC26: WithPromotionType_WhenCalledOnNormalMove_ReturnedMoveTypeIsPromotion** ( :x: )
  - **Method(s) under test**: `withPromotionType(PieceType piece)`, `getType()`
  - **State of the system**: original move is `new Move(from, to, MoveType.NORMAL)`; `withPromotionType(PieceType.QUEEN)` called
  - **Expected output**: returned move's `getType()` returns `PROMOTION`

- **TC27: WithPromotionType_WhenCalledWithPieceType_ReturnedMovePromotionTypeMatchesPiece** ( :x: )
  - **Method(s) under test**: `withPromotionType(PieceType piece)`, `getPromotionType()`
  - **State of the system**: original move is `new Move(from, to, MoveType.NORMAL)`; `withPromotionType(piece)` called with each `PieceType` value
  - **Expected output**: returned move's `getPromotionType()` returns `Optional.of(piece)` matching the passed-in `PieceType`
  - **Note**: TC28–TC33 are covered by this test case as a parameterized test

- **TC28: WithPromotionType_WhenCalledWithQueen_ReturnedMovePromotionTypeIsQueen** ( :x: )
  - **Covered by**: TC27 (parameterized test)

- **TC29: WithPromotionType_WhenCalledWithRook_ReturnedMovePromotionTypeIsRook** ( :x: )
  - **Covered by**: TC27 (parameterized test)

- **TC30: WithPromotionType_WhenCalledWithKnight_ReturnedMovePromotionTypeIsKnight** ( :x: )
  - **Covered by**: TC27 (parameterized test)

- **TC31: WithPromotionType_WhenCalledWithBishop_ReturnedMovePromotionTypeIsBishop** ( :x: )
  - **Covered by**: TC27 (parameterized test)

- **TC32: WithPromotionType_WhenCalledWithKing_ReturnedMovePromotionTypeIsKing** ( :x: )
  - **Covered by**: TC27 (parameterized test)

- **TC33: WithPromotionType_WhenCalledWithPawn_ReturnedMovePromotionTypeIsPawn** ( :x: )
  - **Covered by**: TC27 (parameterized test)

- **TC34: WithPromotionType_WhenCalledWithNone_ReturnedMovePromotionTypeIsNone** ( :x: )
  - **Covered by**: TC27 (parameterized test)

- **TC35: WithPromotionType_ReturnedMoveFromMatchesOriginal** ( :x: )
  - **Method(s) under test**: `withPromotionType(PieceType piece)`, `getFrom()`
  - **State of the system**: original move is `new Move(new Location(1, 2), new Location(3, 4), MoveType.NORMAL)`; `withPromotionType(PieceType.QUEEN)` called
  - **Expected output**: returned move's `getFrom()` has `x=1`, `y=2`

- **TC36: WithPromotionType_ReturnedMoveToMatchesOriginal** ( :x: )
  - **Method(s) under test**: `withPromotionType(PieceType piece)`, `getTo()`
  - **State of the system**: original move is `new Move(new Location(1, 2), new Location(3, 4), MoveType.NORMAL)`; `withPromotionType(PieceType.QUEEN)` called
  - **Expected output**: returned move's `getTo()` has `x=3`, `y=4`

- **TC37: WithPromotionType_OriginalMoveTypeIsUnchanged** ( :x: )
  - **Method(s) under test**: `withPromotionType(PieceType piece)`, `getType()`
  - **State of the system**: original move is `new Move(from, to, MoveType.NORMAL)`; `withPromotionType(PieceType.QUEEN)` called
  - **Expected output**: original move's `getType()` still returns `NORMAL`

- **TC38: WithPromotionType_OriginalMovePromotionTypeIsUnchanged** ( :x: )
  - **Method(s) under test**: `withPromotionType(PieceType piece)`, `getPromotionType()`
  - **State of the system**: original move is `new Move(from, to, MoveType.NORMAL)`; `withPromotionType(PieceType.QUEEN)` called
  - **Expected output**: original move's `getPromotionType()` still returns `Optional.empty()`