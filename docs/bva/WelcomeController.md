# BVA Analysis for WelcomeController

`WelcomeView` and the game launch are injected seams (`setWelcomeView`,
`setGameLauncher`), so controller logic is unit-tested headless with EasyMock.
Only `show()` builds a real `WelcomeView`, so its line stays headed
(`assumeTrue` on a display), matching `BoardController.show()`. `MainView` state
(visibility, title, current-player label) is `BoardController`'s responsibility
and is verified in `BoardControllerTest`, not here.

## Method: `WelcomeController()` / `WelcomeController(Locale)`

### Step 1: Equivalence Classes
- Input: locale — stored, passed to `WelcomeView` in `show()` (no-arg defaults to English)
- Output: none (constructor only stores the locale)

### Step 2: Data Types (from BVA Catalog)
| Class | Type |
| --- | --- |
| Locale | Cases |

### Step 3: Boundary Values (from BVA Catalog)
- Locale: `Locale.ENGLISH` (no-arg default), `Locale.forLanguageTag("es")`
- Exceptions: none

### Step 4: Test Cases
No standalone output; verified through `show()` (WC-TC3) and `startGame()` (WC-TC4–WC-TC11).

---

## Method: `setWelcomeView(WelcomeView)` / `getWelcomeView()`

### Step 1: Equivalence Classes
- Input: injected welcome view
- Output: start-game action wired on the injected view
- Output: `getWelcomeView()` returns the injected view

### Step 2: Data Types (from BVA Catalog)
| Class | Type |
| --- | --- |
| Injected view | Pointers |
| `setStartGameAction` calls | Counts |
| `getWelcomeView()` return | Pointers |

### Step 3: Boundary Values (from BVA Catalog)
- Injected view: non-null mock (`null` CAN'T SET — setter calls `setStartGameAction` immediately)
- `setStartGameAction` calls: `1` (`0` CAN'T SET)
- Return: same reference as injected; `null` before first `setWelcomeView`/`show()`

### Step 4: Test Cases
- **WC-TC1: SetWelcomeView_WhenCalled_WiresStartGameAction** ( :white_check_mark: )
  - Method(s) under test: `setWelcomeView(WelcomeView)`
  - State of the system: mock `WelcomeView` injected via `setWelcomeView`
  - Expected output: `setStartGameAction` called once with a non-null action

- **WC-TC2: GetWelcomeView_AfterSetWelcomeView_ReturnsSameView** ( :white_check_mark: )
  - Method(s) under test: `getWelcomeView()`, `setWelcomeView(WelcomeView)`
  - State of the system: mock `WelcomeView` injected via `setWelcomeView`
  - Expected output: `getWelcomeView()` returns the same mock instance

---

## Method: `setGameLauncher(GameLauncher)`

### Step 1: Equivalence Classes
- Input: injected game launcher
- Output: `startGame()` invokes the injected launcher instead of the default

### Step 2: Data Types (from BVA Catalog)
| Class | Type |
| --- | --- |
| Injected launcher | Pointers |

### Step 3: Boundary Values (from BVA Catalog)
- Injected launcher: non-null `GameLauncher`; default launches a real `BoardController` when unset
- Exceptions: none

### Step 4: Test Cases
Verified through `startGame()` success path (WC-TC8–WC-TC11).

---

## Method: `show()`

### Step 1: Equivalence Classes
- Output: welcome view created
- Output: welcome view visible

### Step 2: Data Types (from BVA Catalog)
| Class | Type |
| --- | --- |
| Welcome view created | Pointers |
| Welcome view visibility | Boolean |

### Step 3: Boundary Values (from BVA Catalog)
- Welcome view: a real `WelcomeView` is created (no injection path; opens a window)
- Visibility: `true` (`false` CAN'T SET post-`show()`)
- Exceptions: `HeadlessException` with no display — tests guard with `assumeTrue`

### Step 4: Test Cases
- **WC-TC3: Show_WhenCalled_WelcomeViewBecomesVisible** ( :white_check_mark: )
  - Method(s) under test: `show()`
  - State of the system: freshly constructed controller; display available; `show()` called
  - Expected output: `getWelcomeView().isVisible()` is `true`

---

## Method: `startGame()`

### Step 1: Equivalence Classes
- Input: player1Name, player2Name — read from the view
- Input: selected locale — read from the view
- Output (invalid): error shown, launcher not invoked, view not closed
- Output (valid): view closed, launcher invoked with names, initializer, and locale

### Step 2: Data Types (from BVA Catalog)
| Class | Type |
| --- | --- |
| player1Name, player2Name | Strings |
| Selected locale | Cases |
| `showError` calls | Counts |
| `closeWelcomeView` calls (`setVisible(false)`, `dispose`) | Counts |
| Launcher invocations | Counts |
| Names forwarded to launcher | Strings |
| Locale forwarded to launcher | Cases |

### Step 3: Boundary Values (from BVA Catalog)
- player1Name / player2Name: `""` vs non-empty (`"Alice"`, `"Bob"`)
- Selected locale: `Locale.ENGLISH`, `Locale.forLanguageTag("es")`
- `showError` calls: invalid → `1`; valid → `0`
- Launcher invocations: valid → `1`; invalid → `0`
- Error text: English `"Player name cannot be empty"`, Spanish `"El nombre del jugador no puede estar vacío"`
- Exceptions: none

### Step 4: Test Cases
Invalid (at least one empty name):
- **WC-TC4: StartGame_EmptyPlayer1Name_ShowsErrorAndDoesNotLaunch** ( :white_check_mark: )
  - Method(s) under test: `startGame()`
  - State of the system: mock view `getPlayer1Name() = ""`, `getPlayer2Name() = "Bob"`, `getSelectedLocale() = ENGLISH`; stub launcher
  - Expected output: `showError` called once; launcher not invoked; view not disposed

- **WC-TC5: StartGame_EmptyPlayer2Name_ShowsErrorAndDoesNotLaunch** ( :white_check_mark: )
  - Method(s) under test: `startGame()`
  - State of the system: mock view `getPlayer1Name() = "Alice"`, `getPlayer2Name() = ""`
  - Expected output: `showError` called once; launcher not invoked; view not disposed

- **WC-TC6: StartGame_EmptyName_ErrorTextFromEnglishBundle** ( :white_check_mark: )
  - Method(s) under test: `startGame()`
  - State of the system: mock view `getPlayer1Name() = ""`, `getSelectedLocale() = ENGLISH`; capture the `showError` argument
  - Expected output: argument is `"Player name cannot be empty"`

- **WC-TC7: StartGame_EmptyName_ErrorTextFromSpanishBundle** ( :white_check_mark: )
  - Method(s) under test: `startGame()`
  - State of the system: mock view `getPlayer1Name() = ""`, `getSelectedLocale() = forLanguageTag("es")`; capture the `showError` argument
  - Expected output: argument is `"El nombre del jugador no puede estar vacío"`

Valid (both names non-empty):
- **WC-TC8: StartGame_NonEmptyNames_ClosesWelcomeView** ( :x: )
  - Method(s) under test: `startGame()`
  - State of the system: mock view `"Alice"`/`"Bob"`; stub launcher
  - Expected output: view `setVisible(false)` and `dispose()` each called once

- **WC-TC9: StartGame_NonEmptyNames_InvokesLauncherOnce** ( :x: )
  - Method(s) under test: `startGame()`, `setGameLauncher(GameLauncher)`
  - State of the system: mock view `"Alice"`/`"Bob"`; mock launcher
  - Expected output: launcher invoked exactly once

- **WC-TC10: StartGame_NonEmptyNames_LauncherReceivesPlayerNames** ( :x: )
  - Method(s) under test: `startGame()`, `setGameLauncher(GameLauncher)`
  - State of the system: mock view `"Alice"`/`"Bob"`; launcher capturing its arguments
  - Expected output: launcher received `player1Name = "Alice"`, `player2Name = "Bob"`

- **WC-TC11: StartGame_OnSpanishLocale_LauncherReceivesSpanishLocale** ( :x: )
  - Method(s) under test: `startGame()`, `setGameLauncher(GameLauncher)`
  - State of the system: mock view valid names, `getSelectedLocale() = forLanguageTag("es")`; launcher capturing its arguments
  - Expected output: launcher received `Locale.forLanguageTag("es")` (resulting `MainView` title verified in `BoardControllerTest`)

---

## Method: `selectedInitializer()`

### Step 1: Equivalence Classes
- Input: chess960 mode selected — read from the view
- Output: concrete `BoardInitializer` type

### Step 2: Data Types (from BVA Catalog)
| Class | Type |
| --- | --- |
| chess960 selected | Boolean |
| BoardInitializer type | Cases |

### Step 3: Boundary Values (from BVA Catalog)
- chess960 selected: `false` (standard), `true` (chess960)
- BoardInitializer type: `StandardBoardInitializer`, `FischerRandomBoardInitializer`
- Exceptions: none

### Step 4: Test Cases
- **WC-TC12: SelectedInitializer_StandardModeSelected_ReturnsStandardBoardInitializer** ( :x: )
  - Method(s) under test: `selectedInitializer()`
  - State of the system: mock view `isChess960Selected() = false`
  - Expected output: instance of `StandardBoardInitializer`

- **WC-TC13: SelectedInitializer_Chess960ModeSelected_ReturnsFischerRandomBoardInitializer** ( :x: )
  - Method(s) under test: `selectedInitializer()`
  - State of the system: mock view `isChess960Selected() = true`
  - Expected output: instance of `FischerRandomBoardInitializer`
