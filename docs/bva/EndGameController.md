# BVA Analysis for EndGameController

## Method: `EndGameController(String, JFrame, Locale)`

### Step 1: Equivalence Classes
- Input: result message — stored, passed to `EndGameView` in `show()`
- Input: main view reference — stored, hidden in `show()`
- Input: locale — stored, used by `show()` and `playAgain()`
- Output: none (constructor only stores fields)

### Step 2: Data Types (from BVA Catalog)
| Class | Type |
| --- | --- |
| Result message | Strings |
| Main view reference | Pointers |
| Locale | Cases |

### Step 3: Boundary Values (from BVA Catalog)
- Result message: `""`, `"Alice wins!"`
- Main view: non-null `JFrame` (`null` CAN'T SET — `show()` calls `mainView.setVisible(false)`)
- Locale: `Locale.ENGLISH`, `Locale.forLanguageTag("es")`
- Exceptions: none from the constructor

### Step 4: Test Cases
Constructor has no standalone output; each stored input is verified where it is used:
- `mainView` → TC3 (`show()` hides the exact `mainView` mock; `verify(mainView)`)
- `locale` → EC-TC11/EC-TC12 (`playAgain()` forwards the stored locale to the action)
- `resultMessage` → passed to `EndGameView` in `show()`; observable only if `EndGameView`
  exposes the label text (no such accessor today)

---

## Method: `setEndGameView(EndGameView)` / `getEndGameView()`

### Step 1: Equivalence Classes
- Input: injected end-game view
- Output: play-again action wired on the injected view
- Output: `getEndGameView()` returns the injected view

### Step 2: Data Types (from BVA Catalog)
| Class | Type |
| --- | --- |
| Injected view | Pointers |
| `setPlayAgainAction` calls | Counts |
| `getEndGameView()` return | Pointers |

### Step 3: Boundary Values (from BVA Catalog)
- Injected view: non-null mock (`null` CAN'T SET — setter calls `setPlayAgainAction` immediately)
- `setPlayAgainAction` calls: `1` (`0` CAN'T SET)
- Return: same reference as injected; `null` before first `setEndGameView`/`show()`
- Exceptions: none

### Step 4: Test Cases
- **EC-TC8: SetEndGameView_WhenCalled_WiresPlayAgainAction** ( :white_check_mark: )
  - Method(s) under test: `setEndGameView(EndGameView)`
  - State of the system: mock `EndGameView` injected via `setEndGameView`
  - Expected output: `setPlayAgainAction` called once with a non-null action

- **EC-TC9: GetEndGameView_AfterSetEndGameView_ReturnsSameView** ( :white_check_mark: )
  - Method(s) under test: `getEndGameView()`, `setEndGameView(EndGameView)`
  - State of the system: mock `EndGameView` injected via `setEndGameView`
  - Expected output: `getEndGameView()` returns the same mock instance

---

## Method: `setPlayAgainAction(Consumer<Locale>)`

### Step 1: Equivalence Classes
- Input: injected play-again action
- Output: `playAgain()` invokes the injected action instead of the default

### Step 2: Data Types (from BVA Catalog)
| Class | Type |
| --- | --- |
| Injected action | Pointers |

### Step 3: Boundary Values (from BVA Catalog)
- Injected action: non-null `Consumer<Locale>`; default lambda when unset
- Exceptions: none

### Step 4: Test Cases
Verified through `playAgain()` (EC-TC11, EC-TC12).

---

## Method: `playAgain()`

### Step 1: Equivalence Classes
- Input: locale held by the controller
- Output: end-game view disposed
- Output: play-again action invoked with the locale

### Step 2: Data Types (from BVA Catalog)
| Class | Type |
| --- | --- |
| Locale | Cases |
| `dispose()` calls | Counts |
| Action invocations | Counts |
| Locale passed to action | Cases |

### Step 3: Boundary Values (from BVA Catalog)
- Locale: `Locale.ENGLISH`, `Locale.forLanguageTag("es")`
- `dispose()` calls: `1` (`0` CAN'T SET)
- Action invocations: `1` (`0` CAN'T SET)
- Exceptions: none

### Step 4: Test Cases
EC-TC10–EC-TC12 replace the headed TC5/TC6/EC-TC7, which scanned `Window.getWindows()`
for a real `WelcomeView`.

- **EC-TC10: PlayAgain_WhenCalled_DisposesEndGameView** ( :white_check_mark: )
  - Method(s) under test: `playAgain()`
  - State of the system: mock view injected; stub action injected; `playAgain()` called
  - Expected output: `dispose()` called once on the view

- **EC-TC11: PlayAgain_OnEnglishLocale_InvokesActionWithEnglishLocale** ( :x: )
  - Method(s) under test: `playAgain()`, `setPlayAgainAction(Consumer)`
  - State of the system: mock view; action capturing its `Locale`; English controller
  - Expected output: action invoked once with `Locale.ENGLISH`

- **EC-TC12: PlayAgain_OnSpanishLocale_InvokesActionWithSpanishLocale** ( :x: )
  - Method(s) under test: `playAgain()`, `setPlayAgainAction(Consumer)`
  - State of the system: mock view; action capturing its `Locale`; `Locale.forLanguageTag("es")` controller
  - Expected output: action invoked once with `Locale.forLanguageTag("es")`

---

## Method: `show()`

### Step 1: Equivalence Classes
- Output: end-game view created
- Output: main view hidden
- Output: end-game view shown

### Step 2: Data Types (from BVA Catalog)
| Class | Type |
| --- | --- |
| End-game view created | Pointers |
| Main view visibility | Boolean |
| End-game view visibility | Boolean |

### Step 3: Boundary Values (from BVA Catalog)
- End-game view: a real `EndGameView` is created (no injection path; opens a window)
- Main view visibility: `false` (`true` CAN'T SET post-`show()`)
- End-game view visibility: `true` (`false` CAN'T SET post-`show()`)
- Exceptions: `HeadlessException` with no display — tests guard with `assumeTrue`

### Step 4: Test Cases
- **TC3: Show_HidesMainView** ( :white_check_mark: )
  - Method(s) under test: `show()`
  - State of the system: controller constructed with a non-null `mainView`; display available; `show()` called
  - Expected output: `mainView.isVisible()` is `false`

- **TC4: Show_DisplaysEndGameView** ( :white_check_mark: )
  - Method(s) under test: `show()`
  - State of the system: controller constructed; display available; `show()` called
  - Expected output: `getEndGameView().isVisible()` is `true`
