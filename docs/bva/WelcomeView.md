# BVA Analysis for WelcomeView

Package: `ui.WelcomeView`

Scope: **Constructor** (field initialization) and **`getPlayer1Name()`** / **`getPlayer2Name()`** (getter delegation to JTextField). `createWelcomeScreenUI` is **untestable** (Swing UI assembly side-effect) and is excluded from this BVA.

---

## Method / behavior: `WelcomeView()`

### Step 1: Input and output equivalence classes

| Concern | Equivalence classes |
| ------- | ------------------- |
| Post-construction `player1NameField` | Initialized; `getPlayer1Name()` returns `""` (JTextField default) |
| Post-construction `player2NameField` | Initialized; `getPlayer2Name()` returns `""` (JTextField default) |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Notes |
| ----------------- | ------------ | ----- |
| `getPlayer1Name()` result | String | Empty string (no text entered) vs non-empty (text typed) |
| `getPlayer2Name()` result | String | Same as above |

### Step 3: Concrete boundary values

- Empty string `""`: JTextField default — both fields start here.
- Non-empty string (e.g. `"Alice"`): text has been entered into the field.

### Step 4: Test cases

- **WV-TC1: Constructor_OnFreshWelcomeView_Player1NameIsEmpty** ( :x: )
  - **Method(s) under test**: `WelcomeView()`, `getPlayer1Name()`
  - **State of the system**: freshly constructed `WelcomeView`
  - **Expected output**: `getPlayer1Name()` returns `""`

- **WV-TC2: Constructor_OnFreshWelcomeView_Player2NameIsEmpty** ( :x: )
  - **Method(s) under test**: `WelcomeView()`, `getPlayer2Name()`
  - **State of the system**: freshly constructed `WelcomeView`
  - **Expected output**: `getPlayer2Name()` returns `""`

- **WV-TC3: GetPlayer1Name_WhenFieldHasText_ReturnsEnteredName** ( :x: )
  - **Method(s) under test**: `getPlayer1Name()`
  - **State of the system**: `WelcomeView` constructed; `player1NameField` text set to `"Alice"` via package-private setter
  - **Expected output**: `getPlayer1Name()` returns `"Alice"`

- **WV-TC4: GetPlayer2Name_WhenFieldHasText_ReturnsEnteredName** ( :x: )
  - **Method(s) under test**: `getPlayer2Name()`
  - **State of the system**: `WelcomeView` constructed; `player2NameField` text set to `"Bob"` via package-private setter
  - **Expected output**: `getPlayer2Name()` returns `"Bob"`
