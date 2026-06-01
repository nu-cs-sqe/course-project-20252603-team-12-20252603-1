# BVA Analysis for WelcomeView

Package: `ui.WelcomeView`

Scope: **Constructor** (field initialization), **`getPlayer1Name()`** / **`getPlayer2Name()`** (getter delegation to JTextField), **`isChess960Selected()`** (radio button state), and **`setStartGameAction(Runnable)`** (action wiring). `createWelcomeScreenUI` is **untestable** (Swing UI assembly side-effect) and is excluded from this BVA.

**Headless guard:** `WelcomeView` extends `JFrame`, which cannot be instantiated in a headless JVM. All TCs use a `@BeforeAll assumeTrue(!GraphicsEnvironment.isHeadless())` guard so they skip cleanly on headless CI and run on machines with a display.

**Radio button note:** Game mode is selected via `standardRadioButton` / `chess960RadioButton` in a `ButtonGroup`. Standard is selected by default. `isChess960Selected()` delegates to `chess960RadioButton.isSelected()`. Tests use a package-private `setChess960Selected(boolean)` setter to flip state without navigating the component tree.

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

- **WV-TC1: Constructor_OnFreshWelcomeView_Player1NameIsEmpty** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView()`, `getPlayer1Name()`
  - **State of the system**: freshly constructed `WelcomeView`
  - **Expected output**: `getPlayer1Name()` returns `""`

- **WV-TC2: Constructor_OnFreshWelcomeView_Player2NameIsEmpty** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView()`, `getPlayer2Name()`
  - **State of the system**: freshly constructed `WelcomeView`
  - **Expected output**: `getPlayer2Name()` returns `""`

- **WV-TC3: GetPlayer1Name_WhenFieldHasText_ReturnsEnteredName** ( :white_check_mark: )
  - **Method(s) under test**: `getPlayer1Name()`
  - **State of the system**: `WelcomeView` constructed; `player1NameField` text set to `"Alice"` via package-private setter
  - **Expected output**: `getPlayer1Name()` returns `"Alice"`

- **WV-TC4: GetPlayer2Name_WhenFieldHasText_ReturnsEnteredName** ( :white_check_mark: )
  - **Method(s) under test**: `getPlayer2Name()`
  - **State of the system**: `WelcomeView` constructed; `player2NameField` text set to `"Bob"` via package-private setter
  - **Expected output**: `getPlayer2Name()` returns `"Bob"`

---

## Method / behavior: `isChess960Selected()`

### Step 1: Input and output equivalence classes

| Concern | Equivalence classes |
| ------- | ------------------- |
| `chess960RadioButton` state | Unselected (default, `standardRadioButton` selected) → `false`; selected → `true` |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Notes |
| ----------------- | ------------ | ----- |
| `isChess960Selected()` result | Boolean | `false` (standard selected by default) vs `true` (Chess960 selected) — two-state boundary |

### Step 3: Concrete boundary values

- `false`: `standardRadioButton` selected by default on construction.
- `true`: `chess960RadioButton` selected.

### Step 4: Test cases

- **WV-TC5: IsChess960Selected_OnFreshWelcomeView_ReturnsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView()`, `isChess960Selected()`
  - **State of the system**: freshly constructed `WelcomeView`
  - **Expected output**: `isChess960Selected()` returns `false`

- **WV-TC6: IsChess960Selected_WhenChess960RadioIsSelected_ReturnsTrue** ( :white_check_mark: )
  - **Method(s) under test**: `isChess960Selected()`
  - **State of the system**: `WelcomeView` constructed; `chess960RadioButton` selected via package-private setter
  - **Expected output**: `isChess960Selected()` returns `true`

---

## Method / behavior: `setStartGameAction(Runnable action)`

### Step 1: Input and output equivalence classes

| Concern | Equivalence classes |
| ------- | ------------------- |
| Action registration | Runnable registered → invoked when Start Game is clicked |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Notes |
| ----------------- | ------------ | ----- |
| Invocation count | Integer | 0 (not clicked) vs 1 (clicked once) |

### Step 3: Concrete boundary values

- 0: action not yet invoked (button not clicked).
- 1: button clicked once → action invoked exactly once.

### Step 4: Test cases

- **WV-TC7: SetStartGameAction_WhenStartGameClicked_ActionIsInvoked** ( :white_check_mark: )
  - **Method(s) under test**: `setStartGameAction(Runnable)`
  - **State of the system**: `WelcomeView` constructed; a counting `Runnable` registered; Start Game triggered via package-private `clickStartGame()`
  - **Expected output**: Runnable invoked exactly once

- **WV-TC8: ClickStartGame_WhenNoActionRegistered_DoesNotThrow** ( :white_check_mark: )
  - **Method(s) under test**: `clickStartGame()`
  - **State of the system**: `WelcomeView` constructed; no action registered; `clickStartGame()` called
  - **Expected output**: no exception thrown
