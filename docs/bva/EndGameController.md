# BVA Analysis for EndGameController

## Method: `EndGameController(String resultMessage, JFrame mainView)` / `EndGameController(String, JFrame, Locale)` / `EndGameController(String, JFrame, Locale, EndGameView)`

### Step 1: Equivalence Classes

- **Input: result message** — the game outcome text to display in the end-game screen
- **Input: main view reference** — the main game `JFrame` to be hidden when the end-game screen is shown
- **Input: locale** — resource bundle locale for end-game and welcome screens
- **Input: end-game view** — optional injected `EndGameView` (4-arg constructor); otherwise created internally
- **Output: play-again action wiring** — `setPlayAgainAction` is registered on the end-game view at construction
- **Output: main view visibility after `show()`** — whether the main game window is hidden
- **Output: end-game view visibility after `show()`** — whether the end-game screen is showing

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type |
| --- | --- |
| Input: result message | Strings |
| Input: main view reference | Pointers |
| Input: locale | Pointers |
| Input: end-game view | Pointers |
| Output: play-again action wiring | Cases |
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

**End-game view — Pointers:**
- internally created `EndGameView` (2-arg / 3-arg constructors)
- injected mock or real `EndGameView` (4-arg constructor)

**Play-again action wiring — Cases:**
- `setPlayAgainAction` called once at construction

**Main view visibility after `show()` — Cases:**
- `false` — `show()` hides `mainView`

**End-game view visibility after `show()` — Cases:**
- `true` — `show()` shows `endGameView`

### Step 4: Test Cases (Each-Choice Strategy)

- **TC1: Constructor_WithEmptyResultMessage_ShowHidesMainView** ( :white_check_mark: )
  - **Method(s) under test**: `EndGameController(String, JFrame)`, `show()`
  - **State of the system**: `resultMessage = ""`, mocked visible `mainView`; `show()` is called
  - **Expected output**: `mainView.setVisible(false)` is called once

- **TC2: Constructor_WithNonEmptyResultMessage_ShowHidesMainViewAndDisplaysEndGameView** ( :white_check_mark: )
  - **Method(s) under test**: `EndGameController(String, JFrame)`, `show()`
  - **State of the system**: `resultMessage = "Alice wins!"`, mocked visible `mainView`; `show()` is called
  - **Expected output**: `mainView.setVisible(false)` is called once; `getEndGameView().isVisible()` returns `true`

- **EC-TC8: Constructor_WithAnyResultMessage_SetsPlayAgainAction** ( :white_check_mark: )
  - **Method(s) under test**: `EndGameController(String, JFrame, Locale, EndGameView)`
  - **State of the system**: 4-arg constructor with mocked `EndGameView` and mocked `mainView`
  - **Expected output**: `setPlayAgainAction` is called exactly once on the injected view

---

## Method: `getEndGameView()`

### Step 1: Equivalence Classes

- **Object state: end-game view field** — reference set at construction (internal or injected)
- **Output: returned view** — same instance as the controller's `endGameView` field

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type |
| --- | --- |
| Object state: end-game view field | Pointers |
| Output: returned view | Pointers |

### Step 3: Boundary Values (from BVA Catalog)

**End-game view field — Pointers:**
- injected mock `EndGameView` (4-arg constructor)

**Returned view — Pointers:**
- same reference as injected instance

### Step 4: Test Cases (Each-Choice Strategy)

- **EC-TC9: GetEndGameView_AfterConstruction_ReturnsSameView** ( :white_check_mark: )
  - **Method(s) under test**: `getEndGameView()`
  - **State of the system**: controller constructed via 4-arg constructor with mocked `EndGameView`
  - **Expected output**: `getEndGameView()` returns the same mock instance that was injected

---

## Method: `void show()`

### Step 1: Equivalence Classes

- **Output: main view visibility** — `mainView` is hidden after `show()`
- **Output: end-game view visibility** — end-game screen is displayed after `show()`

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type |
| --- | --- |
| Output: main view visibility | Cases |
| Output: end-game view visibility | Cases |

### Step 3: Boundary Values (from BVA Catalog)

**Main view visibility — Cases:**
- `false` — `show()` always hides `mainView`

**End-game view visibility — Cases:**
- `true` — `show()` always shows `endGameView`

### Step 4: Test Cases (Each-Choice Strategy)

- **TC3: Show_HidesMainView** ( :white_check_mark: )
  - **Method(s) under test**: `show()`
  - **State of the system**: `EndGameController` constructed with a non-null visible `mainView`; `show()` is called
  - **Expected output**: `mainView.isVisible()` returns `false`
  - **Covered by**: TC1, TC2, EC-TC10

- **TC4: Show_DisplaysEndGameView** ( :white_check_mark: )
  - **Method(s) under test**: `show()`
  - **State of the system**: `EndGameController` constructed; `show()` is called
  - **Expected output**: `getEndGameView().isVisible()` returns `true`
  - **Covered by**: TC2

- **EC-TC10: Show_WhenCalled_HidesMainViewAndShowsEndGameView** ( :white_check_mark: )
  - **Method(s) under test**: `show()`
  - **State of the system**: 4-arg constructor with mocked `EndGameView` and mocked `mainView`; `show()` is called
  - **Expected output**: `mainView.setVisible(false)` called once; `endGameView.setVisible(true)` called once

---

## Method: `playAgain()` (invoked via `getEndGameView().clickPlayAgain()`)

### Step 1: Equivalence Classes

- **Object state: construction path** — internal `EndGameView` (2-arg / 3-arg) vs injected real `EndGameView` (4-arg)
- **Object state: locale** — default English vs Spanish resource bundle
- **Output: end-game view disposal** — end-game screen is disposed after play again
- **Output: welcome screen shown** — a new `WelcomeView` becomes visible

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type |
| --- | --- |
| Object state: construction path | Cases |
| Object state: locale | Pointers |
| Output: end-game view disposal | Cases |
| Output: welcome screen shown | Cases |

### Step 3: Boundary Values (from BVA Catalog)

**Construction path — Cases:**
- 2-arg / 3-arg constructor with internally created `EndGameView`
- 4-arg constructor with injected real `EndGameView`

**Locale — Pointers:**
- `Locale.ENGLISH`
- `Locale.forLanguageTag("es")`

**End-game view disposal — Cases:**
- `isDisplayable()` returns `false` after `playAgain()`

**Welcome screen shown — Cases:**
- a `WelcomeView` window is visible after `playAgain()`
- Spanish locale: visible `WelcomeView` title is `"Ajedrez"`

### Step 4: Test Cases (Each-Choice Strategy)

- **EC-TC11: PlayAgain_WhenCalled_DisposesEndGameView** ( :white_check_mark: )
  - **Method(s) under test**: `playAgain()` (via `endGameView.clickPlayAgain()`)
  - **State of the system**: 4-arg constructor with injected real `EndGameView` and mocked `mainView`; play again clicked
  - **Expected output**: `endGameView.isDisplayable()` returns `false`

- **EC-TC12: PlayAgain_WhenCalled_ShowsWelcomeView** ( :white_check_mark: )
  - **Method(s) under test**: `playAgain()` (via `endGameView.clickPlayAgain()`)
  - **State of the system**: 4-arg constructor with injected real `EndGameView` and mocked `mainView`; play again clicked
  - **Expected output**: a `WelcomeView` window is visible

- **TC5: PlayAgain_WhenShowHasBeenCalled_EndGameViewIsDisposed** ( :white_check_mark: )
  - **Method(s) under test**: `playAgain()` (via `getEndGameView().clickPlayAgain()`)
  - **State of the system**: 2-arg constructor; `show()` called; play again clicked
  - **Expected output**: `getEndGameView().isDisplayable()` returns `false`

- **TC6: PlayAgain_WhenShowHasBeenCalled_WelcomeViewIsVisible** ( :white_check_mark: )
  - **Method(s) under test**: `playAgain()` (via `getEndGameView().clickPlayAgain()`)
  - **State of the system**: 2-arg constructor; `show()` called; play again clicked
  - **Expected output**: a new `WelcomeView` is visible

- **EC-TC7: PlayAgain_OnSpanishLocale_WelcomeViewTitleFromSpanishBundle** ( :white_check_mark: )
  - **Method(s) under test**: `playAgain()` (via `getEndGameView().clickPlayAgain()`)
  - **State of the system**: 3-arg constructor with `Locale.forLanguageTag("es")`; `show()` called; play again clicked
  - **Expected output**: visible `WelcomeView` title is `"Ajedrez"`
