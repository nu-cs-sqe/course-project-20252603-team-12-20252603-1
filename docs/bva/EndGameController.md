# BVA Analysis for EndGameController

## Method: `EndGameController(String resultMessage, JFrame mainView)` / `EndGameController(String, JFrame, Locale)`

### Step 1: Equivalence Classes

- **Input: result message** — the game outcome text to display in the end-game screen
- **Input: main view reference** — the main game `JFrame` to be hidden when the end-game screen is shown
- **Input: locale** — resource bundle locale for end-game and welcome screens
- **Output: main view visibility after `show()`** — whether the main game window is hidden
- **Output: end-game view visibility after `show()`** — whether the end-game screen is showing

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type |
| --- | --- |
| Input: result message | Strings |
| Input: main view reference | Pointers |
| Input: locale | Pointers |
| Output: main view visibility after `show()` | Cases |
| Output: end-game view visibility after `show()` | Cases |

### Step 3: Boundary Values (from BVA Catalog)

**Result message — Strings:**
- `""` (empty)
- `"Alice wins!"` (non-empty)

**Main view reference — Pointers:**
- non-null `JFrame` instance (production path)

**Locale — Pointers:**
- `Locale.ENGLISH` (default)
- `Locale.forLanguageTag("es")` (non-default bundle)

**Main view visibility after `show()` — Cases:**
- `false` — `show()` hides `mainView`

**End-game view visibility after `show()` — Cases:**
- `true` — `show()` shows `endGameView`

### Step 4: Test Cases (Each-Choice Strategy)

- **TC1: Constructor_WithEmptyResultMessage_ShowHidesMainView** ( :white_check_mark: )
  - **Method(s) under test**: `EndGameController(String, JFrame)`, `show()`
  - **State of the system**: `resultMessage = ""`, mocked visible `mainView`; `show()` is called (lazy-creates `EndGameView`)
  - **Expected output**: `mainView.setVisible(false)` is called once

- **TC2: Constructor_WithNonEmptyResultMessage_ShowHidesMainViewAndDisplaysEndGameView** ( :white_check_mark: )
  - **Method(s) under test**: `EndGameController(String, JFrame)`, `show()`
  - **State of the system**: `resultMessage = "Alice wins!"`, mocked visible `mainView`; `show()` is called
  - **Expected output**: `mainView.setVisible(false)` is called once; `getEndGameView().isVisible()` returns `true`

---

## Method: `getEndGameView()` / `setEndGameView(EndGameView endGameView)`

### Step 1: Equivalence Classes

| State | Equivalence classes |
| ----- | ------------------- |
| `endGameView` field | injected via `setEndGameView`; lazy-created on first `show()` |

| Output | Equivalence classes |
| ------ | ------------------- |
| `getEndGameView()` | same instance as injected mock; lazy-created instance after `show()` |
| `setEndGameView` side effect | `setPlayAgainAction` registered on injected view |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Notes |
| ----------------- | ------------ | ----- |
| `endGameView` | Pointers | injected reference |
| Return value | Pointers | same reference as field |
| `setPlayAgainAction` call | Cases | wired once on injection |

### Step 3: Concrete boundary values

- After `setEndGameView(mock)`: `getEndGameView()` → same mock reference
- After `setEndGameView(mock)`: `setPlayAgainAction` called once on mock

### Step 4: Test cases

- **EC-TC8: SetEndGameView_WhenCalled_SetsPlayAgainAction** ( :white_check_mark: )
  - **Method(s) under test**: `setEndGameView(EndGameView)`
  - **State of the system**: mocked `EndGameView` injected via setter
  - **Expected output**: `setPlayAgainAction` is called exactly once on the injected view

- **EC-TC9: GetEndGameView_AfterSetEndGameView_ReturnsSameView** ( :white_check_mark: )
  - **Method(s) under test**: `getEndGameView()`, `setEndGameView(EndGameView)`
  - **State of the system**: mocked `EndGameView` injected via `setEndGameView`
  - **Expected output**: `getEndGameView()` returns the same mock instance

---

## Method: `void show()`

### Step 1: Equivalence Classes

- **Object state: end-game view** — injected via `setEndGameView` vs lazy-created on first `show()`
- **Output: main view visibility** — `mainView` is hidden after `show()`
- **Output: end-game view visibility** — end-game screen is displayed after `show()`

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type |
| --- | --- |
| Object state: end-game view | Pointers |
| Output: main view visibility | Cases |
| Output: end-game view visibility | Cases |

### Step 3: Boundary Values (from BVA Catalog)

**End-game view — Pointers:**
- injected mock via `setEndGameView` (no lazy creation)
- lazy-created real `EndGameView` when unset

**Main view visibility — Cases:**
- `false` — `show()` always hides `mainView`

**End-game view visibility — Cases:**
- `true` — `show()` always shows `endGameView`

### Step 4: Test Cases (Each-Choice Strategy)

- **TC3: Show_HidesMainView** ( :white_check_mark: )
  - **Method(s) under test**: `show()`
  - **State of the system**: `EndGameController` constructed with a non-null visible `mainView`; `show()` is called
  - **Expected output**: `mainView.setVisible(false)` is called once
  - **Covered by**: TC1, TC2, EC-TC10

- **TC4: Show_DisplaysEndGameView** ( :white_check_mark: )
  - **Method(s) under test**: `show()`
  - **State of the system**: `EndGameController` constructed; `show()` is called
  - **Expected output**: `getEndGameView().isVisible()` returns `true`
  - **Covered by**: TC2

- **EC-TC10: Show_WhenCalled_HidesMainViewAndShowsEndGameView** ( :white_check_mark: )
  - **Method(s) under test**: `show()`
  - **State of the system**: mocked `EndGameView` injected via `setEndGameView`; mocked `mainView`; `show()` is called
  - **Expected output**: `mainView.setVisible(false)` called once; `endGameView.setVisible(true)` called once

---

## Method: `playAgain()`

### Step 1: Equivalence Classes

- **Object state: end-game view** — injected mock vs lazy-created real view after `show()`
- **Object state: locale** — default English vs Spanish resource bundle
- **Output: end-game view disposal** — `dispose()` called on end-game view
- **Output: welcome screen shown** — a new `WelcomeView` becomes visible

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type |
| --- | --- |
| Object state: end-game view | Pointers |
| Object state: locale | Pointers |
| Output: end-game view disposal | Cases |
| Output: welcome screen shown | Cases |

### Step 3: Boundary Values (from BVA Catalog)

**End-game view — Pointers:**
- injected mock via `setEndGameView`
- lazy-created real `EndGameView` after `show()`

**Locale — Pointers:**
- `Locale.ENGLISH`
- `Locale.forLanguageTag("es")`

**End-game view disposal — Cases:**
- `dispose()` called once on mock; or `isDisplayable()` returns `false` on real view after `playAgain()`

**Welcome screen shown — Cases:**
- a `WelcomeView` window is visible after `playAgain()`
- Spanish locale: visible `WelcomeView` title is `"Ajedrez"`

### Step 4: Test Cases (Each-Choice Strategy)

- **EC-TC11: PlayAgain_WhenCalled_DisposesEndGameView** ( :white_check_mark: )
  - **Method(s) under test**: `playAgain()`
  - **State of the system**: mocked `EndGameView` injected via `setEndGameView`; `playAgain()` called directly
  - **Expected output**: `dispose()` is called once on the mock

- **EC-TC12: PlayAgain_WhenCalled_ShowsWelcomeView** ( :white_check_mark: )
  - **Method(s) under test**: `playAgain()`
  - **State of the system**: mocked `EndGameView` injected via `setEndGameView`; `playAgain()` called directly
  - **Expected output**: a `WelcomeView` window is visible

- **TC5: PlayAgain_WhenShowHasBeenCalled_EndGameViewIsDisposed** ( :white_check_mark: )
  - **Method(s) under test**: `playAgain()`
  - **State of the system**: 2-arg constructor; `show()` called (lazy-creates view); `playAgain()` called directly
  - **Expected output**: `getEndGameView().isDisplayable()` returns `false`

- **TC6: PlayAgain_WhenShowHasBeenCalled_WelcomeViewIsVisible** ( :white_check_mark: )
  - **Method(s) under test**: `playAgain()`
  - **State of the system**: 2-arg constructor; `show()` called; `playAgain()` called directly
  - **Expected output**: a new `WelcomeView` is visible

- **EC-TC7: PlayAgain_OnSpanishLocale_WelcomeViewTitleFromSpanishBundle** ( :white_check_mark: )
  - **Method(s) under test**: `playAgain()`
  - **State of the system**: 3-arg constructor with `Locale.forLanguageTag("es")`; `show()` called; `playAgain()` called directly
  - **Expected output**: visible `WelcomeView` title is `"Ajedrez"`
