# BVA Analysis for BoardView

Package: `ui.BoardView`

Scope: **Refactor** — align with chess-master: direct screen-to-board coordinates (no rank flip), `mousePressed` instead of `mouseClicked`, remove stale `selectedRow` / `selectedCol` fields. **Constructor** (mouse-listener registration and preferred-size wiring) and **`BoardMouseListener.mousePressed`** (pixel-to-`Location` conversion and `handleSquareClick` delegation) remain testable. All graphics methods are **untestable** and excluded from this BVA.

**Coordinate convention (refactored):** Screen pixel `(x, y)` maps directly to board coordinates:
- `file = x / TILE_SIZE`
- `rank = y / TILE_SIZE`

Rank 0 is the **top** row of the panel; rank 7 is the **bottom**. `BoardController.handleSquareClick` receives `Location(file, rank)`. Bounds validation is the controller's responsibility.

**Mock policy:** `BoardController` is mocked with EasyMock for all `mousePressed` TCs.

**Stale fields:** `selectedRow` and `selectedCol` are removed; selection highlight reads `boardController.getSelectedLocation()` directly (untestable draw path).

---

## Method / behavior: `BoardView(BoardController boardController)`

### Step 1: Input and output equivalence classes

| Concern | Equivalence classes |
| ------- | ------------------- |
| `boardController` | Valid non-null controller |
| Post-construction panel state | Exactly one `MouseListener` registered; preferred size is `BOARD_SIZE × TILE_SIZE` in both dimensions |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Notes |
| ----------------- | ------------ | ----- |
| Mouse listener count | Counts | 0 vs 1 |
| Preferred size width | Counts | 0 vs `BOARD_SIZE × TILE_SIZE` |
| Preferred size height | Counts | 0 vs `BOARD_SIZE × TILE_SIZE` |

### Step 3: Concrete boundary values

- Mouse listener count: 0 vs **1**
- Preferred size: 0 vs `600 × 600`

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

## Method / behavior: `BoardMouseListener.mousePressed(MouseEvent e)`

### Step 1: Input and output equivalence classes

| Input | Equivalence classes |
| ----- | ------------------- |
| `e.getX()` (pixel x) | Leftmost pixel of a tile; rightmost pixel of a tile; first pixel of next tile; last pixel of board |
| `e.getY()` (pixel y) | Same four boundary positions as x |

| Output / effect | Classes |
| --------------- | ------- |
| `Location` passed to `handleSquareClick` | Correct `(file, rank)` per direct tile arithmetic |

### Step 2: BVA catalog data types

| Variable | Catalog type | Notes |
| -------- | ------------ | ----- |
| `file = x / TILE_SIZE` | Intervals | division boundaries at tile edges |
| `rank = y / TILE_SIZE` | Intervals | division boundaries at tile edges |

### Step 3: Concrete boundary values

File (x) boundaries:

| Pixel x | `x / TILE_SIZE` → file |
| ------- | ----------------------- |
| 0 | 0 |
| TILE_SIZE − 1 | 0 |
| TILE_SIZE | 1 |
| BOARD_SIZE × TILE_SIZE − 1 | 7 |

Rank (y) boundaries:

| Pixel y | `y / TILE_SIZE` → rank |
| ------- | ----------------------- |
| 0 | 0 (top row) |
| TILE_SIZE − 1 | 0 |
| TILE_SIZE | 1 |
| BOARD_SIZE × TILE_SIZE − 1 | 7 (bottom row) |

### Step 4: Test cases

**File boundary — x dimension**

- **BV-TC4: MousePressed_AtLeftmostPixel_CallsHandleSquareClickWithFileZero** ( :x: )
  - **Method(s) under test**: `BoardMouseListener.mousePressed`
  - **State of the system**: press at pixel `(0, 0)`
  - **Expected output**: `handleSquareClick` receives `Location` with `getX() == 0`

- **BV-TC5: MousePressed_AtLastPixelOfFirstTile_CallsHandleSquareClickWithFileZero** ( :x: )
  - **Method(s) under test**: `BoardMouseListener.mousePressed`
  - **State of the system**: press at pixel `(TILE_SIZE − 1, 0)`
  - **Expected output**: `handleSquareClick` receives `Location` with `getX() == 0`

- **BV-TC6: MousePressed_AtFirstPixelOfSecondTile_CallsHandleSquareClickWithFileOne** ( :x: )
  - **Method(s) under test**: `BoardMouseListener.mousePressed`
  - **State of the system**: press at pixel `(TILE_SIZE, 0)`
  - **Expected output**: `handleSquareClick` receives `Location` with `getX() == 1`

- **BV-TC7: MousePressed_AtLastPixelOfBoard_CallsHandleSquareClickWithFileSeven** ( :x: )
  - **Method(s) under test**: `BoardMouseListener.mousePressed`
  - **State of the system**: press at pixel `(BOARD_SIZE*TILE_SIZE − 1, BOARD_SIZE*TILE_SIZE − 1)`
  - **Expected output**: `handleSquareClick` receives `Location` with `getX() == 7`

**Rank boundary — y dimension**

- **BV-TC8: MousePressed_AtTopPixel_CallsHandleSquareClickWithRankZero** ( :x: )
  - **Method(s) under test**: `BoardMouseListener.mousePressed`
  - **State of the system**: press at pixel `(0, 0)`
  - **Expected output**: `handleSquareClick` receives `Location` with `getY() == 0`

- **BV-TC9: MousePressed_AtLastPixelOfTopTileRow_CallsHandleSquareClickWithRankZero** ( :x: )
  - **Method(s) under test**: `BoardMouseListener.mousePressed`
  - **State of the system**: press at pixel `(0, TILE_SIZE − 1)`
  - **Expected output**: `handleSquareClick` receives `Location` with `getY() == 0`

- **BV-TC10: MousePressed_AtFirstPixelOfSecondTileRow_CallsHandleSquareClickWithRankOne** ( :x: )
  - **Method(s) under test**: `BoardMouseListener.mousePressed`
  - **State of the system**: press at pixel `(0, TILE_SIZE)`
  - **Expected output**: `handleSquareClick` receives `Location` with `getY() == 1`

- **BV-TC11: MousePressed_AtBottomPixel_CallsHandleSquareClickWithRankSeven** ( :x: )
  - **Method(s) under test**: `BoardMouseListener.mousePressed`
  - **State of the system**: press at pixel `(0, BOARD_SIZE*TILE_SIZE − 1)`
  - **Expected output**: `handleSquareClick` receives `Location` with `getY() == 7`

---

## Refactor: remove stale selection fields (no automated TC)

- **BV-TC12: RemoveSelectedRowAndColFields** ( :white_check_mark: when `selectedRow` / `selectedCol` deleted and `drawSelectedSquare` uses controller location directly — verified by code review; graphics untestable)
