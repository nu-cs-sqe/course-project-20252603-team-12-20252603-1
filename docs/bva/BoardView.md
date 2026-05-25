# BVA Analysis for BoardView

Package: `ui.BoardView`

Scope: **Constructor** (mouse-listener registration and preferred-size wiring) and **`BoardMouseListener.mouseClicked`** (pixel-to-`Location` conversion and `handleSquareClick` delegation). All graphics methods (`paintComponent`, `drawBoard`, `drawPieces`, `drawSelectedSquare`, `loadPieceImages`, `loadOnePieceImage`) are **untestable** (graphics / rendering side-effects) and are excluded from this BVA.

**Coordinate convention:** The board is rendered with rank 0 (white's back rank) at the **bottom** of the panel and rank 7 (black's back rank) at the **top**. Screen pixel `(x, y)` maps to:
- `file = x / TILE_SIZE`
- `rank = (BOARD_SIZE - 1) - (y / TILE_SIZE)`

`BoardController.handleSquareClick` receives a `Location(file, rank)`. Bounds validation is the controller's responsibility; `mouseClicked` converts and delegates unconditionally.

**Mock policy:** `BoardController` is mocked with EasyMock for all `mouseClicked` TCs so that `handleSquareClick` calls can be recorded and verified without a real `Board`.

---

## Method / behavior: `BoardView(BoardController boardController)`

### Step 1: Input and output equivalence classes

| Concern | Equivalence classes |
| ------- | ------------------- |
| `boardController` | Valid non-null controller (caller contract — null is unrepresentable) |
| Post-construction panel state | Exactly one `MouseListener` registered; preferred size is `BOARD_SIZE × TILE_SIZE` in both dimensions |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Notes |
| ----------------- | ------------ | ----- |
| Mouse listener count | Integer | 0 vs 1 (presence boundary) |
| Preferred size width | Integer | 0 vs `BOARD_SIZE × TILE_SIZE` |
| Preferred size height | Integer | same as width |

### Step 3: Concrete boundary values

- Mouse listener count: 0 (no registration) vs **1** (one `BoardMouseListener` added).
- Preferred size: 0 (no `setPreferredSize` called) vs `BOARD_SIZE × TILE_SIZE` × `BOARD_SIZE × TILE_SIZE`.

### Step 4: Test cases

- **BV-TC1: Constructor_WithValidController_RegistersExactlyOneMouseListener** ( :white_check_mark: )
  - **Method(s) under test**: `BoardView(BoardController)`
  - **State of the system**: fresh `BoardView` constructed with a mocked controller
  - **Expected output**: `getMouseListeners().length == 1`

- **BV-TC2: Constructor_WithValidController_PreferredWidthIsBoardSizeTimesTileSize** ( :white_check_mark: )
  - **Method(s) under test**: `BoardView(BoardController)`
  - **State of the system**: fresh `BoardView`
  - **Expected output**: `getPreferredSize().width == BOARD_SIZE * TILE_SIZE`

- **BV-TC3: Constructor_WithValidController_PreferredHeightIsBoardSizeTimesTileSize** ( :white_check_mark: )
  - **Method(s) under test**: `BoardView(BoardController)`
  - **State of the system**: fresh `BoardView`
  - **Expected output**: `getPreferredSize().height == BOARD_SIZE * TILE_SIZE`

---

## Method / behavior: `BoardMouseListener.mouseClicked(MouseEvent e)`

### Step 1: Input and output equivalence classes

| Input | Equivalence classes |
| ----- | ------------------- |
| `e.getX()` (pixel x) | Leftmost pixel of a tile; rightmost pixel of a tile; first pixel of next tile; last pixel of board |
| `e.getY()` (pixel y) | Same four boundary positions as x, in the y dimension |
| Combined | Corner pixels of the board (four corners cover both dimensions at once) |

| Output / effect | Classes |
| --------------- | ------- |
| `Location` passed to `handleSquareClick` | Correct `(file, rank)` per tile arithmetic |

### Step 2: BVA catalog data types

| Variable | Catalog type | Notes |
| -------- | ------------ | ----- |
| `file = x / TILE_SIZE` | Integer division boundary | `x = 0` vs `TILE_SIZE-1` vs `TILE_SIZE` vs `BOARD_SIZE*TILE_SIZE-1` |
| `rank = (BOARD_SIZE-1) - y/TILE_SIZE` | Integer division + inversion | `y = 0` vs `TILE_SIZE-1` vs `TILE_SIZE` vs `BOARD_SIZE*TILE_SIZE-1` |

### Step 3: Concrete boundary values

File (x) boundaries:

| Pixel x | `x / TILE_SIZE` → file |
| ------- | ----------------------- |
| 0 | 0 (left edge of file 0) |
| TILE_SIZE − 1 | 0 (right edge of file 0, still within first tile) |
| TILE_SIZE | 1 (first pixel of file 1) |
| BOARD_SIZE × TILE_SIZE − 1 | 7 (last pixel of file 7) |

Rank (y) boundaries:

| Pixel y | `(BOARD_SIZE-1) - y/TILE_SIZE` → rank |
| ------- | -------------------------------------- |
| 0 | 7 (top of panel → black's back rank) |
| TILE_SIZE − 1 | 7 (bottom of top tile row, still rank 7) |
| TILE_SIZE | 6 (first pixel of second tile row from top) |
| BOARD_SIZE × TILE_SIZE − 1 | 0 (bottom of panel → white's back rank) |

### Step 4: Test cases

**File boundary — x dimension**

- **BV-TC4: MouseClicked_AtLeftmostPixel_CallsHandleSquareClickWithFileZero** ( :white_check_mark: )
  - **Method(s) under test**: `BoardMouseListener.mouseClicked`
  - **State of the system**: click at pixel `(0, 0)`
  - **Expected output**: `handleSquareClick(Location(0, 7))` called on the controller

- **BV-TC5: MouseClicked_AtLastPixelOfFirstTile_CallsHandleSquareClickWithFileZero** ( :white_check_mark: )
  - **Method(s) under test**: `BoardMouseListener.mouseClicked`
  - **State of the system**: click at pixel `(TILE_SIZE − 1, 0)`
  - **Expected output**: `handleSquareClick(Location(0, 7))` called on the controller (same file, same rank as BV-TC4)

- **BV-TC6: MouseClicked_AtFirstPixelOfSecondTile_CallsHandleSquareClickWithFileOne** ( :white_check_mark: )
  - **Method(s) under test**: `BoardMouseListener.mouseClicked`
  - **State of the system**: click at pixel `(TILE_SIZE, 0)`
  - **Expected output**: `handleSquareClick(Location(1, 7))` called on the controller

- **BV-TC7: MouseClicked_AtLastPixelOfBoard_CallsHandleSquareClickWithFileSeven** ( :white_check_mark: )
  - **Method(s) under test**: `BoardMouseListener.mouseClicked`
  - **State of the system**: click at pixel `(BOARD_SIZE*TILE_SIZE − 1, BOARD_SIZE*TILE_SIZE − 1)`
  - **Expected output**: `handleSquareClick` receives `Location` with `getX() == 7`

**Rank boundary — y dimension**

- **BV-TC8: MouseClicked_AtTopPixel_CallsHandleSquareClickWithRankSeven** ( :white_check_mark: )
  - **Method(s) under test**: `BoardMouseListener.mouseClicked`
  - **State of the system**: click at pixel `(0, 0)`
  - **Expected output**: `handleSquareClick` receives `Location` with `getY() == 7`

- **BV-TC9: MouseClicked_AtLastPixelOfTopTileRow_CallsHandleSquareClickWithRankSeven** ( :white_check_mark: )
  - **Method(s) under test**: `BoardMouseListener.mouseClicked`
  - **State of the system**: click at pixel `(0, TILE_SIZE − 1)`
  - **Expected output**: `handleSquareClick` receives `Location` with `getY() == 7` (boundary still within rank-7 tile)

- **BV-TC10: MouseClicked_AtFirstPixelOfSecondTileRow_CallsHandleSquareClickWithRankSix** ( :white_check_mark: )
  - **Method(s) under test**: `BoardMouseListener.mouseClicked`
  - **State of the system**: click at pixel `(0, TILE_SIZE)`
  - **Expected output**: `handleSquareClick` receives `Location` with `getY() == 6`

- **BV-TC11: MouseClicked_AtBottomPixel_CallsHandleSquareClickWithRankZero** ( :white_check_mark: )
  - **Method(s) under test**: `BoardMouseListener.mouseClicked`
  - **State of the system**: click at pixel `(0, BOARD_SIZE*TILE_SIZE − 1)`
  - **Expected output**: `handleSquareClick` receives `Location` with `getY() == 0`
