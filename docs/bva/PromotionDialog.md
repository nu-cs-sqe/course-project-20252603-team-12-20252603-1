# BVA Analysis for PromotionDialog

Package: `ui.PromotionView`

Scope: `PromotionDialog` is a package-private modal `JDialog` shown when a pawn reaches the back rank. It presents four image buttons (Queen, Rook, Bishop, Knight); clicking one sets `chosenType` and disposes the dialog. `showAndGetChoice()` blocks until a button is clicked and returns the chosen `PieceType`.

Both the constructor (`buildUi()` creates a `JDialog`) and `showAndGetChoice()` (blocks on `dialog.setVisible(true)`) are **untestable** — UI/IO operations that cannot be driven in a headless test environment. There are no automated test cases for this class.

**Null invariant (verified by code inspection):**
- `chosenType` is initialized to `PieceType.QUEEN` before the dialog opens.
- Every button's action listener sets `chosenType` to a non-null `PieceType` before calling `dialog.dispose()`.
- `JDialog.DO_NOTHING_ON_CLOSE` prevents dismissal without a choice.
- `showAndGetChoice()` therefore always returns a non-null `PieceType`.

---

## Method / behavior: `PromotionDialog(JFrame parent, PieceColor color)`

### Step 1: Input and output equivalence classes

| Concern | Equivalence classes |
| ------- | ------------------- |
| Input: `color` | WHITE, BLACK |
| Output: `chosenType` default | QUEEN (pre-click default) |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Notes |
| ----------------- | ------------ | ----- |
| `color` | Cases | WHITE, BLACK |
| `chosenType` default | Cases | QUEEN |

### Step 3: Concrete boundary values

- `color`: WHITE, BLACK
- `chosenType` initial value: QUEEN

### Step 4: Test cases

*Untestable — constructor calls `buildUi()` which creates a `JDialog`; cannot be exercised in a headless test environment.*

### Step 4 (i18n): bundle-loaded dialog strings (`PromotionView(JFrame, PieceColor, Locale)`)

**API contract:** `parent`, `color`, and `locale` are **non-null**. Invalid null input is **out of scope**.

**Headless guard:** i18n TCs use `@BeforeAll assumeTrue(!GraphicsEnvironment.isHeadless())`. Tests read dialog title and prompt label after construction without calling `showAndGetChoice()`.

- **PV-TC1: Constructor_OnEnglishLocale_DialogTitleFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `PromotionView(JFrame, PieceColor, Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`; `color = PieceColor.WHITE`
  - **Expected output**: dialog title is `"Promote Pawn"`

- **PV-TC2: Constructor_OnEnglishLocale_PromptLabelFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `PromotionView(JFrame, PieceColor, Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`
  - **Expected output**: prompt label is `"Choose promotion piece:"`

- **PV-TC3: Constructor_OnSpanishLocale_DialogTitleFromBundle** ( :x: )
  - **Method(s) under test**: `PromotionView(JFrame, PieceColor, Locale)`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`
  - **Expected output**: dialog title is `"Promover peón"`

- **PV-TC4: Constructor_OnSpanishLocale_PromptLabelFromBundle** ( :x: )
  - **Method(s) under test**: `PromotionView(JFrame, PieceColor, Locale)`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`
  - **Expected output**: prompt label is `"Elija la pieza de promoción:"`

---

## Method / behavior: `showAndGetChoice(): PieceType`

### Step 1: Input and output equivalence classes

| Concern | Equivalence classes |
| ------- | ------------------- |
| Button clicked | Queen, Rook, Bishop, Knight |
| Return value | Non-null PieceType matching the clicked button |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Notes |
| ----------------- | ------------ | ----- |
| Button clicked | Cases | QUEEN, ROOK, BISHOP, KNIGHT |
| Return value | Cases | QUEEN, ROOK, BISHOP, KNIGHT |

### Step 3: Concrete boundary values

- Each of the four buttons sets `chosenType` to the corresponding `PieceType`

### Step 4: Test cases

*Untestable — `dialog.setVisible(true)` blocks the calling thread on user input; cannot be driven in a headless test environment.*
