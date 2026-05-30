# BVA Analysis for WelcomeController

## Method: `WelcomeController()`

### Step 1: Equivalence Classes

- **Output: WelcomeView initial visibility** — whether the welcome screen is visible immediately after construction, before `show()` is called

### Step 2: Data Types (from BVA Catalog)

| Equivalence class                       | Catalog data type | Parameters                          |
| --------------------------------------- | ----------------- | ----------------------------------- |
| Output: WelcomeView initial visibility  | Boolean           | true (visible), false (not visible) |

### Step 3: Boundary Values (from BVA Catalog)

**WelcomeView initial visibility — Boolean:**
- `false` (0)
- `true` (1) — CAN'T SET: `show()` has not been called

### Step 4: Test Cases (Each-Choice Strategy)

- **TC1: Constructor_FreshInstance_WelcomeViewNotVisible** ( :x: )
  - **Method(s) under test**: `WelcomeController()`
  - **State of the system**: freshly constructed controller; `show()` has not been called
  - **Expected output**: `welcomeView.isVisible()` is `false`

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

- **TC2: Show_WhenCalled_WelcomeViewBecomesVisible** ( :x: )
  - **Method(s) under test**: `show()`
  - **State of the system**: freshly constructed controller; `show()` called
  - **Expected output**: `welcomeView.isVisible()` is `true`

---

## Method: `startGame()`

### Step 1: Equivalence Classes

- **Input: player1Name** — the text in the player 1 name field at the moment start is triggered
- **Input: player2Name** — the text in the player 2 name field at the moment start is triggered
- **Output: WelcomeView disposed** — whether the `WelcomeView` is closed and disposed after start is triggered

### Step 2: Data Types (from BVA Catalog)

| Equivalence class            | Catalog data type | Parameters                            |
| ---------------------------- | ----------------- | ------------------------------------- |
| Input: player1Name           | String            | —                                     |
| Input: player2Name           | String            | —                                     |
| Output: WelcomeView disposed | Boolean           | true (disposed), false (not disposed) |

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

### Step 4: Test Cases (Each-Choice Strategy)

- **TC3: StartGame_NonEmptyNames_WelcomeViewDisposed** ( :x: )
  - **Method(s) under test**: `startGame()`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = "Bob"`; `startGame()` called
  - **Expected output**: the `WelcomeView` is disposed (`isDisplayable()` is `false`)

- **TC4: StartGame_EmptyPlayer1Name_GameDoesNotStart** ( :x: )
  - **Method(s) under test**: `startGame()`
  - **State of the system**: `player1Name = ""`, `player2Name = "Bob"`; `startGame()` called
  - **Expected output**: `WelcomeView` is not disposed (`isDisplayable()` is `true`)

- **TC5: StartGame_EmptyPlayer2Name_GameDoesNotStart** ( :x: )
  - **Method(s) under test**: `startGame()`
  - **State of the system**: `player1Name = "Alice"`, `player2Name = ""`; `startGame()` called
  - **Expected output**: `WelcomeView` is not disposed (`isDisplayable()` is `true`)
