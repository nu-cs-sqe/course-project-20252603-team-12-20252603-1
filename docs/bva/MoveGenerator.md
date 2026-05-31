# BVA Analysis for MoveGenerator

Package: domain.MoveGenerator

Scope: Add basic legal move generation for all piece types (pawn, knight, bishop, rook, queen, king) on a board snapshot.

## Method: MoveGenerator(Piece[][] board, Optional<Location> enPassantTarget)

### Step 1: Inputs and outputs

| Input / state   | Equivalence classes                                           |
| --------------- | ------------------------------------------------------------- |
| board           | Collections - 8x8 board with Piece objects                    |
| enPassantTarget | Optional - empty or present                                   |
| Output          | Cases - generator stores references for later move generation |

### Step 2: Catalog data types

| Variable / output   | Catalog data type |
| ------------------- | ----------------- |
| board               | Collections       |
| enPassantTarget     | Optional          |
| generator readiness | Cases             |

### Step 3: Concrete boundary values

- board: 8x8 array filled with NonePiece except test-specific piece placements.
- enPassantTarget: Optional.empty() for basic move tests.

### Step 4: Test cases

- MG-TC1: Constructor_WithBoardAndEmptyEnPassant_GenerateLegalMovesUsable ( :white_check_mark: )
  - Method(s) under test: MoveGenerator(Piece[][], Optional<Location>), generateLegalMoves(Location)
  - State of the system: 8x8 board, white knight at (4,4), enPassantTarget is Optional.empty()
  - Expected output: generateLegalMoves(new Location(4,4)) returns a non-null list

---

## Method: generateLegalMoves(Location from)

### Step 1: Inputs and outputs

| Input / state   | Equivalence classes                                          |
| --------------- | ------------------------------------------------------------ |
| from            | Pairs of variables - file/rank on board                      |
| piece at from   | Cases - NONE, PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING        |
| board occupancy | Collections - empty paths, blockers, capturable enemy pieces |
| Output          | Collection - list of legal moves                             |

### Step 2: Catalog data types

| Variable / output | Catalog data type |
| ----------------- | ----------------- |
| from.x, from.y    | Intervals [0,7]   |
| piece type        | Cases             |
| move list         | Collections       |

### Step 3: Concrete boundary values

- Center location: (4,4) for full directional coverage.
- Knight center expected count: 8.
- Bishop center expected count on empty board: 13.
- Rook center expected count on empty board: 14.
- Queen center expected count on empty board: 27.
- King center expected count on empty board: 8.
- White pawn start rank location: (4,6), expected forward targets (4,5) and (4,4).

### Step 4: Test cases

- MG-TC2: GenerateLegalMoves_OnEmptySquare_ReturnsEmptyList ( :white_check_mark: )
  - Method(s) under test: generateLegalMoves(Location)
  - State of the system: from (3,3) holds NonePiece
  - Expected output: returned move list size is 0

- MG-TC3: GenerateLegalMoves_OnKnightAtCenter_ReturnsEightMoves ( :white_check_mark: )
  - Method(s) under test: generateLegalMoves(Location)
  - State of the system: white knight at (4,4), all other squares NonePiece
  - Expected output: returned move list size is 8

- MG-TC4: GenerateLegalMoves_OnBishopAtCenter_ReturnsThirteenMoves ( :white_check_mark: )
  - Method(s) under test: generateLegalMoves(Location)
  - State of the system: white bishop at (4,4), all other squares NonePiece
  - Expected output: returned move list size is 13

- MG-TC5: GenerateLegalMoves_OnRookAtCenter_ReturnsFourteenMoves ( :white_check_mark: )
  - Method(s) under test: generateLegalMoves(Location)
  - State of the system: white rook at (4,4), all other squares NonePiece
  - Expected output: returned move list size is 14

- MG-TC6: GenerateLegalMoves_OnQueenAtCenter_ReturnsTwentySevenMoves ( :x: )
  - Method(s) under test: generateLegalMoves(Location)
  - State of the system: white queen at (4,4), all other squares NonePiece
  - Expected output: returned move list size is 27

- MG-TC7: GenerateLegalMoves_OnKingAtCenter_ReturnsEightMoves ( :x: )
  - Method(s) under test: generateLegalMoves(Location)
  - State of the system: white king at (4,4), all other squares NonePiece
  - Expected output: returned move list size is 8

- MG-TC8: GenerateLegalMoves_OnWhitePawnAtStart_ReturnsOneAndTwoStepMoves ( :x: )
  - Method(s) under test: generateLegalMoves(Location)
  - State of the system: white pawn at (4,6), squares (4,5) and (4,4) empty
  - Expected output: returned move list size is 2

---

## Method: generateAllLegalMovesForColor(PieceColor color)

### Step 1: Inputs and outputs

| Input / state      | Equivalence classes                                           |
| ------------------ | ------------------------------------------------------------- |
| color              | Cases - WHITE, BLACK                                          |
| board distribution | Collections - pieces of both colors                           |
| Output             | Collection - concatenated legal moves for all pieces of color |

### Step 2: Catalog data types

| Variable / output | Catalog data type |
| ----------------- | ----------------- |
| color             | Cases             |
| move list         | Collections       |

### Step 3: Concrete boundary values

- White-only board sample: white knight at (4,4), black king at (0,0).

### Step 4: Test cases

- MG-TC9: GenerateAllLegalMovesForColor_OnSingleWhiteKnight_ReturnsEightMoves ( :x: )
  - Method(s) under test: generateAllLegalMovesForColor(PieceColor)
  - State of the system: only movable white piece is knight at (4,4)
  - Expected output: returned move list size is 8 for PieceColor.WHITE

---

## Method: hasLegalMovesForColor(PieceColor color)

### Step 1: Inputs and outputs

| Input / state      | Equivalence classes                                    |
| ------------------ | ------------------------------------------------------ |
| color              | Cases - WHITE, BLACK                                   |
| board distribution | Collections - side has at least one legal move vs none |
| Output             | Boolean - true or false                                |

### Step 2: Catalog data types

| Variable / output | Catalog data type |
| ----------------- | ----------------- |
| color             | Cases             |
| result            | Boolean           |

### Step 3: Concrete boundary values

- Movable sample: white knight at (4,4) on empty board.
- No-move sample: board with only NonePiece for target color.

### Step 4: Test cases

- MG-TC10: HasLegalMovesForColor_OnMovableWhitePiece_ReturnsTrue ( :x: )
  - Method(s) under test: hasLegalMovesForColor(PieceColor)
  - State of the system: white knight at (4,4)
  - Expected output: hasLegalMovesForColor(PieceColor.WHITE) is true

- MG-TC11: HasLegalMovesForColor_OnNoPiecesForColor_ReturnsFalse ( :x: )
  - Method(s) under test: hasLegalMovesForColor(PieceColor)
  - State of the system: board has no black pieces
  - Expected output: hasLegalMovesForColor(PieceColor.BLACK) is false

---

## Method: isInCheck(PieceColor color)

### Step 1: Inputs and outputs

| Input / state     | Equivalence classes              |
| ----------------- | -------------------------------- |
| color             | Cases - WHITE, BLACK             |
| king attack state | Cases - attacked vs not attacked |
| king presence     | Cases - king present vs absent   |
| Output            | Boolean - true or false          |

### Step 2: Catalog data types

| Variable / output | Catalog data type |
| ----------------- | ----------------- |
| color             | Cases             |
| result            | Boolean           |

### Step 3: Concrete boundary values

- Attacked king sample: white king at (4,4), black rook at (4,0), clear file.
- Safe king sample: white king at (4,4), no black attacking line.

### Step 4: Test cases

- MG-TC12: IsInCheck_WhenRookAttacksKing_ReturnsTrue ( :x: )
  - Method(s) under test: isInCheck(PieceColor)
  - State of the system: white king on same file as black rook with empty squares between
  - Expected output: isInCheck(PieceColor.WHITE) is true

- MG-TC13: IsInCheck_WhenKingNotAttacked_ReturnsFalse ( :x: )
  - Method(s) under test: isInCheck(PieceColor)
  - State of the system: white king present and no black piece attacks it
  - Expected output: isInCheck(PieceColor.WHITE) is false
