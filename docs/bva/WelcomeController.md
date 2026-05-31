# BVA Analysis for WelcomeController

## Method: `WelcomeController()`

### Step 1: Equivalence Classes

- **Output: WelcomeView initial visibility** — whether the welcome screen is visible immediately after construction, before `show()` is called
- **Output: start-game action wired** — whether clicking the Start Game button invokes `startGame()`

### Step 2: Data Types (from BVA Catalog)

| Equivalence class                       | Catalog data type | Parameters                          |
| --------------------------------------- | ----------------- | ----------------------------------- |
| Output: WelcomeView initial visibility  | Boolean           | true (visible), false (not visible) |
| Output: start-game action wired         | Boolean           | true (wired), false (not wired)     |

### Step 3: Boundary Values (from BVA Catalog)

**WelcomeView initial visibility — Boolean:**
- `false` (0)
- `true` (1) — CAN'T SET: `show()` has not been called

**start-game action wired — Boolean:**
- `false` (0) — CAN'T SET: the constructor always wires the action
- `true` (1)

### Step 4: Test Cases (Each-Choice Strategy)

- **TC1: Constructor_FreshInstance_WelcomeViewNotVisible** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeController()`
  - **State of the system**: freshly constructed controller; `show()` has not been called
  - **Expected output**: `welcomeView.isVisible()` is `false`

- **TC6: Constructor_ActionWired_ClickingStartGameCallsStartGame** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeController()`
  - **State of the system**: freshly constructed controller; `player1Name = "Alice"`, `player2Name = "Bob"`; `show()` called; `clickStartGame()` called on the view
  - **Expected output**: `WelcomeView` is disposed (`isDisplayable()` is `false`)

---

## Method: `show()`

### Step 1: Equivalence Classes

- **Output: WelcomeView visibility** — whether the welcome screen becomes visible after `show()` is called

### Step 2: Data Types (from BVA Catalog)

| Equivalence class                             | Catalog data type | Parameters                          |
| --------------------------------------------- | ----------------- | ----------------------------------- |
| Output: WelcomeView visibility after `show()` | Boolean           | true (visible), false (not visible) |

### Step 3: Boundary Values (from BVA Catalog)

**WelcomeView visibility — Boolean:**
- `false` (0) — CAN'T SET: `show()` always makes the view visible
- `true` (1)

### Step 4: Test Cases (Each-Choice Strategy)

- **TC2: Show_WhenCalled_WelcomeViewBecomesVisible** ( :white_check_mark: )
  - **Method(s) under test**: `show()`
  - **State of the system**: freshly constructed controller; `show()` called
  - **Expected output**: `welcomeView.isVisible()` is `true`

---

## Method: `startGame()`

### Step 1: Equivalence Classes

- **Input: player1Name** — the text in the player 1 name field at the moment start is triggered
- **Input: player2Name** — the text in the player 2 name field at the moment start is triggered
- **Output: WelcomeView disposed** — whether the `WelcomeView` is closed and disposed after start is triggered
- **Output: error message** — whether an error message is displayed to the user when validation fails

### Step 2: Data Types (from BVA Catalog)

| Equivalence class            | Catalog data type | Parameters                            |
| ---------------------------- | ----------------- | ------------------------------------- |
| Input: player1Name           | String            | —                                     |
| Input: player2Name           | String            | —                                     |
| Output: WelcomeView disposed | Boolean           | true (disposed), false (not disposed) |
| Output: error message        | Boolean           | true (shown), false (not shown)       |

### Step 3: Boundary Values (from BVA Catalog)

**player1Name — String:**
- `""` (empty string)
- A non-empty string (e.g., `"Alice"`)

**player2Name — String:**
- `""` (empty string)
- A non-empty string (e.g., `"Bob"`)

**WelcomeView disposed — Boolean:**
- `false` (0) — at least one name is empty; validation rejects the start
- `true` (1) — both names are non-empty; game proceeds

**error message — Boolean:**
- `false` (0) — both names non-empty; no error shown
- `true` (1) — at least one name is empty; error message displayed

### Step 4: Test Cases (Each-Choice Strategy)

- **TC3: StartGame_NonEmptyNames_WelcomeViewDisposed** ( :white_check_mark: )
  - **Method(s) under test**: `startGame()`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`; `startGame()` called
  - **Expected output**: the `WelcomeView` is disposed (`isDisplayable()` is `false`)

- **TC4: StartGame_EmptyPlayer1Name_GameDoesNotStart** ( :white_check_mark: )
  - **Method(s) under test**: `startGame()`
  - **State of the system**: `player1Name = ""`, `player2Name = "Bob"`; `startGame()` called
  - **Expected output**: `WelcomeView` is not disposed (`isDisplayable()` is `true`); error message is displayed (`getErrorText()` is non-empty)

- **TC5: StartGame_EmptyPlayer2Name_GameDoesNotStart** ( :white_check_mark: )
  - **Method(s) under test**: `startGame()`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = ""`; `startGame()` called
  - **Expected output**: `WelcomeView` is not disposed (`isDisplayable()` is `true`); error message is displayed (`getErrorText()` is non-empty)

---

## Method: `selectedInitializer()`

### Step 1: Equivalence Classes

- **Input: chess960 mode selected** — whether the Chess960 radio button is selected on the WelcomeView at the moment `selectedInitializer()` is called
- **Output: BoardInitializer type** — which concrete `BoardInitializer` implementation is returned

### Step 2: Data Types (from BVA Catalog)

| Equivalence class             | Catalog data type | Parameters                                                                    |
| ----------------------------- | ----------------- | ----------------------------------------------------------------------------- |
| Input: chess960 mode selected | Boolean           | true (chess960 selected), false (standard selected)                           |
| Output: BoardInitializer type | Cases             | StandardBoardInitializer, FischerRandomBoardInitializer                       |

### Step 3: Boundary Values (from BVA Catalog)

**chess960 mode selected — Boolean:**
- `false` (0) — standard radio button selected (default)
- `true` (1) — chess960 radio button selected

**BoardInitializer type — Cases:**
- `StandardBoardInitializer`
- `FischerRandomBoardInitializer`

### Step 4: Test Cases (Each-Choice Strategy)

- **TC7: SelectedInitializer_StandardModeSelected_ReturnsStandardBoardInitializer** ( :white_check_mark: )
  - **Method(s) under test**: `selectedInitializer()`
  - **State of the system**: chess960 not selected (default); `selectedInitializer()` called
  - **Expected output**: returned value is an instance of `StandardBoardInitializer`

- **TC8: SelectedInitializer_Chess960ModeSelected_ReturnsFischerRandomBoardInitializer** ( :white_check_mark: )
  - **Method(s) under test**: `selectedInitializer()`
  - **State of the system**: chess960 selected; `selectedInitializer()` called
  - **Expected output**: returned value is an instance of `FischerRandomBoardInitializer`
