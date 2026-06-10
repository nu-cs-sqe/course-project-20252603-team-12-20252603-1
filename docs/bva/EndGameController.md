# BVA Analysis for EndGameController

## Method: `EndGameController(String resultMessage, JFrame mainView)`

### Step 1: Equivalence Classes

- **Input: result message** — the game outcome text to display in the end-game screen
- **Input: main view reference** — the main game JFrame to be hidden when the end-game screen is shown
- **Output: main view visibility after `show()`** — whether the main game window is hidden
- **Output: end-game view visibility after `show()`** — whether the end-game screen is showing

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type |
| --- | --- |
| Input: result message | String |
| Input: main view reference | Pointer |
| Output: main view visibility after `show()` | Boolean |
| Output: end-game view visibility after `show()` | Boolean |

### Step 3: Boundary Values (from BVA Catalog)

**Result message — String:**
- `""` (the empty string)
- `"Alice wins!"` (a non-empty string)

**Main view reference — Pointer:**
- `null` — CAN'T SET: `show()` calls `mainView.setVisible(false)`, which throws `NullPointerException`
- a non-null `JFrame` instance

**Main view visibility — Boolean:**
- `false` — `show()` always hides mainView; expected post-condition
- `true` — CAN'T SET as a post-`show()` output

**End-game view visibility — Boolean:**
- `true` — `show()` always shows endGameView; expected post-condition
- `false` — CAN'T SET as a post-`show()` output

### Step 4: Test Cases (Each-Choice Strategy)

- **TC1: Constructor_WithEmptyResultMessage_ShowHidesMainView** ( :white_check_mark: )
  - **Method(s) under test**: `EndGameController(String, JFrame)`, `show()`
  - **State of the system**: `resultMessage = ""`, `mainView` = a non-null visible `JFrame`; `show()` is called
  - **Expected output**: `mainView.isVisible()` returns `false`

- **TC2: Constructor_WithNonEmptyResultMessage_ShowHidesMainViewAndDisplaysEndGameView** ( :white_check_mark: )
  - **Method(s) under test**: `EndGameController(String, JFrame)`, `show()`
  - **State of the system**: `resultMessage = "Alice wins!"`, `mainView` = a non-null visible `JFrame`; `show()` is called
  - **Expected output**: `mainView.isVisible()` returns `false`; `getEndGameView().isVisible()` returns `true`
  - **Note**: observing end-game view visibility requires a package-private `getEndGameView()` accessor on `EndGameController`, consistent with the `WelcomeController.getWelcomeView()` pattern

---

## Method: `void show()`

### Step 1: Equivalence Classes

- **Output: main view visibility** — mainView is hidden after `show()`
- **Output: end-game view visibility** — end-game screen is displayed after `show()`

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type |
| --- | --- |
| Output: main view visibility | Boolean |
| Output: end-game view visibility | Boolean |

### Step 3: Boundary Values (from BVA Catalog)

**Main view visibility — Boolean:**
- `false` — `show()` always hides mainView; expected post-condition
- `true` — CAN'T SET as a post-`show()` output

**End-game view visibility — Boolean:**
- `true` — `show()` always shows endGameView; expected post-condition
- `false` — CAN'T SET as a post-`show()` output

### Step 4: Test Cases (Each-Choice Strategy)

- **TC3: Show_HidesMainView** ( :white_check_mark: )
  - **Method(s) under test**: `show()`
  - **State of the system**: `EndGameController` constructed with a non-null visible `mainView`; `show()` is called
  - **Expected output**: `mainView.isVisible()` returns `false`
  - **Covered by**: TC1, TC2

- **TC4: Show_DisplaysEndGameView** ( :white_check_mark: )
  - **Method(s) under test**: `show()`
  - **State of the system**: `EndGameController` constructed; `show()` is called
  - **Expected output**: `getEndGameView().isVisible()` returns `true`
  - **Covered by**: TC2

---

## Method: `playAgain()` (invoked via `getEndGameView().clickPlayAgain()`)

### Step 1: Equivalence Classes

- **Output: end-game view disposal** — end-game screen is disposed after "Play Again" is clicked
- **Output: welcome screen shown** — a new `WelcomeView` becomes visible

### Step 2: Data Types (from BVA Catalog)

| Equivalence class | Catalog data type |
| --- | --- |
| Output: end-game view disposal | Boolean |
| Output: welcome screen shown | Boolean |

### Step 3: Boundary Values (from BVA Catalog)

**End-game view disposal — Boolean:**
- `true` — end-game view is disposed after `playAgain()`; expected post-condition
- `false` — CAN'T SET as a post-`playAgain()` output

**Welcome screen shown — Boolean:**
- `true` — a new `WelcomeView` is shown after `playAgain()`; expected post-condition
- `false` — CAN'T SET as a post-`playAgain()` output

### Step 4: Test Cases (Each-Choice Strategy)

- **TC5: PlayAgain_DisposesEndGameView** ( :white_check_mark: )
  - **Method(s) under test**: `playAgain()` (via `getEndGameView().clickPlayAgain()`)
  - **State of the system**: `EndGameController` constructed and `show()` called; `getEndGameView().clickPlayAgain()` is called
  - **Expected output**: `getEndGameView().isDisplayable()` returns `false`

- **TC6: PlayAgain_WhenShowHasBeenCalled_WelcomeViewIsVisible** ( :white_check_mark: )
  - **Method(s) under test**: `playAgain()` (via `getEndGameView().clickPlayAgain()`)
  - **State of the system**: `EndGameController` constructed and `show()` called; `getEndGameView().clickPlayAgain()` is called
  - **Expected output**: a new `WelcomeView` is visible

- **EC-TC7: PlayAgain_OnSpanishLocale_WelcomeViewTitleFromSpanishBundle** ( :white_check_mark: )
  - **Method(s) under test**: `playAgain()` (via `getEndGameView().clickPlayAgain()`)
  - **State of the system**: `EndGameController(..., Locale.forLanguageTag("es"))`; `show()` called; play again clicked
  - **Expected output**: new visible `WelcomeView` title is `"Ajedrez"`

---

## Mock-injection test cases (mutation coverage)

These test cases use the 4-arg injection constructor `EndGameController(String, JFrame, Locale, EndGameView)` to inject an `EasyMock`-mocked `EndGameView`, eliminating the Swing dependency that caused all 6 mutations to appear as NO_COVERAGE in PIT.

- **EC-TC8: Constructor_WithAnyResultMessage_SetsPlayAgainAction** ( :x: )
  - **Method(s) under test**: `EndGameController(String, JFrame, Locale, EndGameView)`
  - **State of the system**: controller constructed via 4-arg constructor with a mocked `EndGameView`; `setPlayAgainAction` call is expected
  - **Expected output**: `setPlayAgainAction` is called exactly once on the injected view

- **EC-TC9: GetEndGameView_AfterConstruction_ReturnsSameView** ( :x: )
  - **Method(s) under test**: `getEndGameView()`
  - **State of the system**: controller constructed via 4-arg constructor with a mocked `EndGameView`
  - **Expected output**: `getEndGameView()` returns the same mock instance that was injected

- **EC-TC10: Show_WhenCalled_HidesMainViewAndShowsEndGameView** ( :x: )
  - **Method(s) under test**: `show()`
  - **State of the system**: controller constructed via 4-arg constructor with mocked `EndGameView` and mocked `mainView`; `show()` is called
  - **Expected output**: `mainView.setVisible(false)` called once; `endGameView.setVisible(true)` called once

- **EC-TC11: PlayAgain_WhenCalled_DisposesEndGameView** ( :x: )
  - **Method(s) under test**: `playAgain()` (invoked via captured `Runnable` from `setPlayAgainAction`)
  - **State of the system**: controller constructed via 4-arg constructor; captured `Runnable` is invoked directly
  - **Expected output**: `endGameView.dispose()` called exactly once

- **EC-TC12: PlayAgain_WhenCalled_ShowsWelcomeView** ( :x: )
  - **Method(s) under test**: `playAgain()` (invoked via captured `Runnable` from `setPlayAgainAction`)
  - **State of the system**: controller constructed via 4-arg constructor; captured `Runnable` is invoked directly
  - **Expected output**: a `WelcomeView` window is visible (integration assertion; `WelcomeController` cannot be mocked)
