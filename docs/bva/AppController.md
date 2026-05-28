# BVA Analysis for AppController

## Method: `AppController()`

### Step 1: Equivalence Classes

- **Output: type of `currentFrame`** — the view stored in `currentFrame` after construction
- **Output: start-game callback registration** — whether the start-game action is wired on the `WelcomeView`

### Step 2: Data Types (from BVA Catalog)

| Equivalence class                          | Catalog data type | Parameters                                    |
| ------------------------------------------ | ----------------- | --------------------------------------------- |
| Output: type of `currentFrame`             | Cases             | WelcomeView                                   |
| Output: start-game callback registration   | Cases             | registered                                    |

### Step 3: Boundary Values (from BVA Catalog)

**Type of `currentFrame` — Cases:**
- WelcomeView

**Start-game callback registration — Cases:**
- registered

### Step 4: Test Cases

- **TC1: Constructor_FreshInstance_CurrentFrameIsWelcomeView** ( :x: )
  - **Method(s) under test**: `AppController()`
  - **State of the system**: freshly constructed `AppController`
  - **Expected output**: `getCurrentFrame()` is an instance of `WelcomeView`

- **TC2: Constructor_FreshInstance_StartGameActionWired** ( :x: )
  - **Method(s) under test**: `AppController()`
  - **State of the system**: freshly constructed `AppController`; `clickStartGame()` fired on `getCurrentFrame()` without calling `start()` first
  - **Expected output**: `getCurrentFrame()` is an instance of `MainView`

---

## Method: `start()`

### Step 1: Equivalence Classes

- **Input: visibility of `currentFrame` before the call** — the frame is always not visible; the constructor stores the `WelcomeView` without showing it
- **Output: visibility of `currentFrame` after the call** — whether the `WelcomeView` is now visible

### Step 2: Data Types (from BVA Catalog)

| Equivalence class         | Catalog data type | Parameters  |
| ------------------------- | ----------------- | ----------- |
| Input: visibility before  | Cases             | not visible |
| Output: visibility after  | Cases             | visible     |

### Step 3: Boundary Values (from BVA Catalog)

**Visibility before — Cases:**
- not visible

**Visibility after — Cases:**
- visible

### Step 4: Test Cases

- **TC3: Start_FreshController_WelcomeViewIsVisible** ( :x: )
  - **Method(s) under test**: `start()`
  - **State of the system**: freshly constructed `AppController`; `start()` called once
  - **Expected output**: `getCurrentFrame().isVisible()` returns true

---

## Method: `onGameStart(player1Name, player2Name, isChess960)` (private — triggered via `WelcomeView.clickStartGame()`)

### Step 1: Equivalence Classes

- **Input: `player1Name`** — the name string entered for player 1
- **Input: `player2Name`** — the name string entered for player 2
- **Input: `isChess960`** — which board initializer to use
- **Output: type of `currentFrame` after** — the view shown after the transition
- **Output: board initializer used** — which initializer was passed to `Board`
- **Output: previous `WelcomeView` disposed** — whether the prior frame is cleaned up

### Step 2: Data Types (from BVA Catalog)

| Equivalence class                       | Catalog data type | Parameters                                              |
| --------------------------------------- | ----------------- | ------------------------------------------------------- |
| Input: `player1Name`                    | Strings           | empty, non-empty                                        |
| Input: `player2Name`                    | Strings           | empty, non-empty                                        |
| Input: `isChess960`                     | Boolean           | false (Standard), true (Chess960)                       |
| Output: type of `currentFrame` after    | Cases             | MainView                                                |
| Output: board initializer used          | Cases             | StandardBoardInitializer, FischerRandomBoardInitializer |
| Output: previous `WelcomeView` disposed | Boolean           | disposed (true)                                         |

### Step 3: Boundary Values (from BVA Catalog)

**`player1Name`, `player2Name` — Strings:**
- empty (`""`)
- non-empty (`"Alice"`, `"Bob"`)

**`isChess960` — Boolean:**
- false (Standard)
- true (Chess960)

**Board initializer — Cases:**
- StandardBoardInitializer
- FischerRandomBoardInitializer

### Step 4: Test Cases (Each-Choice)

- **TC4: OnGameStart_StandardMode_CurrentFrameIsMainView** ( :x: )
  - **Method(s) under test**: `onGameStart` via `WelcomeView.clickStartGame()`
  - **State of the system**: `isChess960 = false`; `player1Name = "Alice"`; `player2Name = "Bob"`
  - **Expected output**: `getCurrentFrame()` is an instance of `MainView`

- **TC5: OnGameStart_StandardMode_BoardHasStandardBackRank** ( :x: )
  - **Method(s) under test**: `onGameStart` via `WelcomeView.clickStartGame()`
  - **State of the system**: `isChess960 = false`; `player1Name = "Alice"`; `player2Name = "Bob"`
  - **Expected output**: `getPieceAt(7, 0)` is `ROOK` (WHITE); `getPieceAt(7, 4)` is `KING` (WHITE)

- **TC6: OnGameStart_Chess960Mode_CurrentFrameIsMainView** ( :x: )
  - **Method(s) under test**: `onGameStart` via `WelcomeView.clickStartGame()`
  - **State of the system**: `isChess960 = true`; `player1Name = "Alice"`; `player2Name = "Bob"`
  - **Expected output**: `getCurrentFrame()` is an instance of `MainView`

- **TC7: OnGameStart_Chess960Mode_BoardSatisfiesChess960Constraints** ( :x: )
  - **Method(s) under test**: `onGameStart` via `WelcomeView.clickStartGame()`
  - **State of the system**: `isChess960 = true`
  - **Expected output**: white back-rank bishops on opposite-parity squares; king file strictly between the two rook files

- **TC8: OnGameStart_EmptyNames_CurrentFrameIsMainView** ( :x: )
  - **Method(s) under test**: `onGameStart` via `WelcomeView.clickStartGame()`
  - **State of the system**: `player1Name = ""`; `player2Name = ""`; `isChess960 = false`
  - **Expected output**: `getCurrentFrame()` is an instance of `MainView`

- **TC9: OnGameStart_StandardMode_PreviousWelcomeViewDisposed** ( :x: )
  - **Method(s) under test**: `onGameStart` via `WelcomeView.clickStartGame()`
  - **State of the system**: reference to initial `WelcomeView` retained; `isChess960 = false`
  - **Expected output**: `welcomeView.isDisplayable()` returns false

---

## Method: `onGameOver(result)` (private — triggered via `BoardController` game-over callback)

### Step 1: Equivalence Classes

- **Input: `result`** — the terminal game state that ended the game
- **Output: type of `currentFrame` after** — the view shown after the transition
- **Output: previous `MainView` disposed** — whether the prior frame is cleaned up

### Step 2: Data Types (from BVA Catalog)

| Equivalence class                       | Catalog data type | Parameters                                                          |
| --------------------------------------- | ----------------- | ------------------------------------------------------------------- |
| Input: `result`                         | Cases             | WHITE_WIN, BLACK_WIN, DRAW; WHITE_TURN CAN'T SET, BLACK_TURN CAN'T SET |
| Output: type of `currentFrame` after    | Cases             | EndGameView                                                         |
| Output: previous `MainView` disposed    | Boolean           | disposed (true)                                                     |

### Step 3: Boundary Values (from BVA Catalog)

**`result` — Cases:**
- WHITE_WIN
- BLACK_WIN
- DRAW
- WHITE_TURN — CAN'T SET
- BLACK_TURN — CAN'T SET

### Step 4: Test Cases

- **TC10: OnGameOver_WhiteWin_CurrentFrameIsEndGameView** ( :x: )
  - **Method(s) under test**: `onGameOver` via `boardController.fireGameOverForTest(WHITE_WIN)`
  - **State of the system**: AppController in MainView state; `result = WHITE_WIN`
  - **Expected output**: `getCurrentFrame()` is an instance of `EndGameView`

- **TC11: OnGameOver_BlackWin_CurrentFrameIsEndGameView** ( :x: )
  - **Method(s) under test**: `onGameOver` via `boardController.fireGameOverForTest(BLACK_WIN)`
  - **State of the system**: AppController in MainView state; `result = BLACK_WIN`
  - **Expected output**: `getCurrentFrame()` is an instance of `EndGameView`

- **TC12: OnGameOver_Draw_CurrentFrameIsEndGameView** ( :x: )
  - **Method(s) under test**: `onGameOver` via `boardController.fireGameOverForTest(DRAW)`
  - **State of the system**: AppController in MainView state; `result = DRAW`
  - **Expected output**: `getCurrentFrame()` is an instance of `EndGameView`

- **TC13: OnGameOver_WhiteWin_PreviousMainViewDisposed** ( :x: )
  - **Method(s) under test**: `onGameOver` via `boardController.fireGameOverForTest(WHITE_WIN)`
  - **State of the system**: reference to current `MainView` retained; `result = WHITE_WIN`
  - **Expected output**: `mainView.isDisplayable()` returns false

---

## Method: `onPlayAgain()` (private — triggered via `EndGameView.clickPlayAgain()`)

### Step 1: Equivalence Classes

- **Output: type of `currentFrame` after** — the view shown after the transition
- **Output: start-game callback registration on new `WelcomeView`** — whether the full cycle is repeatable
- **Output: previous `EndGameView` disposed** — whether the prior frame is cleaned up

### Step 2: Data Types (from BVA Catalog)

| Equivalence class                              | Catalog data type | Parameters                           |
| ---------------------------------------------- | ----------------- | ------------------------------------ |
| Output: type of `currentFrame` after           | Cases             | WelcomeView                          |
| Output: start-game callback registration       | Cases             | registered                           |
| Output: previous `EndGameView` disposed        | Boolean           | disposed (true)                      |

### Step 3: Boundary Values (from BVA Catalog)

**Type of `currentFrame` — Cases:**
- WelcomeView

**Start-game callback registration — Cases:**
- registered

### Step 4: Test Cases

- **TC14: OnPlayAgain_EndGameViewState_CurrentFrameIsWelcomeView** ( :x: )
  - **Method(s) under test**: `onPlayAgain` via `EndGameView.clickPlayAgain()`
  - **State of the system**: AppController in EndGameView state (reached via start → game-start → game-over with `WHITE_WIN`)
  - **Expected output**: `getCurrentFrame()` is an instance of `WelcomeView`

- **TC15: OnPlayAgain_EndGameViewState_PreviousEndGameViewDisposed** ( :x: )
  - **Method(s) under test**: `onPlayAgain` via `EndGameView.clickPlayAgain()`
  - **State of the system**: reference to current `EndGameView` retained
  - **Expected output**: `endGameView.isDisplayable()` returns false

- **TC16: OnPlayAgain_EndGameViewState_NewCycleStartable** ( :x: )
  - **Method(s) under test**: `onPlayAgain` via `EndGameView.clickPlayAgain()`, then `WelcomeView.clickStartGame()`
  - **State of the system**: after `clickPlayAgain()`, new `WelcomeView` shown; `isChess960 = false`
  - **Expected output**: `getCurrentFrame()` is an instance of `MainView`
