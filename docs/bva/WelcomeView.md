# BVA Analysis for WelcomeView

Package: `ui.WelcomeView`

**API contract:** All `String` and `Locale` parameters are **non-null**. Callers must not pass `null`. Invalid null input is **out of scope** (unrepresentable boundary — not tested here).

Scope: **Constructor** (field initialization), **`getPlayer1Name()`** / **`getPlayer2Name()`** (getter delegation to JTextField), **`isChess960Selected()`** (radio button state), and **`setStartGameAction(Runnable)`** (action wiring). `createWelcomeScreenUI` is **untestable** (Swing UI assembly side-effect) and is excluded from this BVA.

**Headless guard:** `WelcomeView` extends `JFrame`, which cannot be instantiated in a headless JVM. All TCs use a `@BeforeAll assumeTrue(!GraphicsEnvironment.isHeadless())` guard so they skip cleanly on headless CI and run on machines with a display.

**Radio button note:** Game mode is selected via `standardRadioButton` / `chess960RadioButton` in a `ButtonGroup`. Standard is selected by default. `isChess960Selected()` delegates to `chess960RadioButton.isSelected()`. Tests use a package-private `setChess960Selected(boolean)` setter to flip state without navigating the component tree.

---

## Method / behavior: `WelcomeView(Locale locale)`

### Step 1: Input and output equivalence classes

| Concern | Equivalence classes |
| ------- | ------------------- |
| `locale`                               | **Cases** — `Locale.ENGLISH` (app default) or `Locale.forLanguageTag("es")`                    |
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
  - **Method(s) under test**: `WelcomeView(Locale)`, `getPlayer1Name()`
  - **State of the system**: freshly constructed `WelcomeView`
  - **Expected output**: `getPlayer1Name()` returns `""`

- **WV-TC2: Constructor_OnFreshWelcomeView_Player2NameIsEmpty** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`, `getPlayer2Name()`
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
  - **Method(s) under test**: `WelcomeView(Locale)`, `isChess960Selected()`
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

---

## Method / behavior: i18n label text (`WelcomeView(Locale locale)`)

UI strings load from `messages.properties` / `messages_es.properties` via `ui.Messages`. Tests assert **public text outcomes** through package-private getters; Swing layout is not tested.

### Step 1: Input and output equivalence classes

| Input / concern | Equivalence classes |
| --------------- | ------------------- |
| `locale`        | `Locale.ENGLISH` (default bundle); `Locale.forLanguageTag("es")` (Spanish bundle) |
| Bundle keys     | `appTitle`, `welcomeTitle`, `player1Label`, `player2Label`, `standardMode`, `chess960Mode`, `startGame` |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Notes |
| ----------------- | ------------ | ----- |
| `locale`          | **Cases**    | English vs Spanish |
| Label / button text | **String** | one visible string per TC |

### Step 3: Concrete boundary values

- **Cases:** `Locale.ENGLISH`; `Locale.forLanguageTag("es")`.

### Step 4: Test cases

- **WV-TC9: Constructor_OnEnglishLocale_WindowTitleFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`
  - **Expected output**: frame title is `"Chess"`

- **WV-TC10: Constructor_OnEnglishLocale_WelcomeTitleFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`
  - **Expected output**: welcome title label is `"♟  Chess  ♟"`

- **WV-TC11: Constructor_OnEnglishLocale_Player1LabelFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`
  - **Expected output**: player 1 label is `"Player 1"`

- **WV-TC12: Constructor_OnEnglishLocale_Player2LabelFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`
  - **Expected output**: player 2 label is `"Player 2"`

- **WV-TC13: Constructor_OnEnglishLocale_StandardModeLabelFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`
  - **Expected output**: standard radio label is `"Standard"`

- **WV-TC14: Constructor_OnEnglishLocale_Chess960ModeLabelFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`
  - **Expected output**: Chess960 radio label is `"Chess960"`

- **WV-TC15: Constructor_OnEnglishLocale_StartGameButtonFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`
  - **Expected output**: start button text is `"Start Game"`

- **WV-TC16: Constructor_OnSpanishLocale_WindowTitleFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`
  - **Expected output**: frame title is `"Ajedrez"`

- **WV-TC17: Constructor_OnSpanishLocale_WelcomeTitleFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`
  - **Expected output**: welcome title label is `"♟  Ajedrez  ♟"`

- **WV-TC18: Constructor_OnSpanishLocale_Player1LabelFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`
  - **Expected output**: player 1 label is `"Jugador 1"`

- **WV-TC19: Constructor_OnSpanishLocale_Player2LabelFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`
  - **Expected output**: player 2 label is `"Jugador 2"`

- **WV-TC20: Constructor_OnSpanishLocale_StandardModeLabelFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`
  - **Expected output**: standard radio label is `"Estándar"`

- **WV-TC21: Constructor_OnSpanishLocale_Chess960ModeLabelFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`
  - **Expected output**: Chess960 radio label is `"Chess960"`

- **WV-TC22: Constructor_OnSpanishLocale_StartGameButtonFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`
  - **Expected output**: start button text is `"Iniciar juego"`

---

## Method / behavior: language selection (`WelcomeView`)

User picks **English** or **Español** from a combo box. `getSelectedLocale()` returns the choice. Changing the selection relocalizes welcome-screen labels via `applyLocale`. Combo item text stays `"English"` / `"Español"` (native language names).

### Step 1: Input and output equivalence classes

| Input / concern | Equivalence classes |
| --------------- | ------------------- |
| Language combo selection | English (`Locale.ENGLISH`); Spanish (`Locale.forLanguageTag("es")`) |
| Initial constructor `locale` | Sets default combo selection and initial label language |
| Bundle keys | `languageLabel`, `languageEnglish`, `languageSpanish` |

### Step 2: BVA catalog data types

| Variable / output | Catalog type | Notes |
| ----------------- | ------------ | ----- |
| Combo selection | **Cases** | English vs Spanish |
| `getSelectedLocale()` result | **Cases** | matches combo |
| `languageLabel` text | **String** | from bundle for current UI locale |

### Step 3: Concrete boundary values

- **Cases:** combo index 0 → `Locale.ENGLISH`; index 1 → `Locale.forLanguageTag("es")`.

### Step 4: Test cases

- **WV-TC23: GetSelectedLocale_OnFreshEnglishView_ReturnsEnglish** ( :white_check_mark: )
  - **Method(s) under test**: `getSelectedLocale()`
  - **State of the system**: `WelcomeView(Locale.ENGLISH)` freshly constructed
  - **Expected output**: `Locale.ENGLISH`

- **WV-TC24: GetSelectedLocale_WhenSpanishOptionSelected_ReturnsSpanish** ( :white_check_mark: )
  - **Method(s) under test**: `getSelectedLocale()`
  - **State of the system**: `WelcomeView(Locale.ENGLISH)`; Spanish option selected via test seam
  - **Expected output**: `Locale.forLanguageTag("es")`

- **WV-TC25: Constructor_OnEnglishLocale_LanguageLabelFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `WelcomeView(Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`
  - **Expected output**: language label is `"Language:"`

- **WV-TC26: SelectLanguage_WhenSpanishChosen_RelocalizesWelcomeTitle** ( :x: )
  - **Method(s) under test**: language combo action / `applyLocale`
  - **State of the system**: `WelcomeView(Locale.ENGLISH)`; user selects Spanish
  - **Expected output**: welcome title label is `"♟  Ajedrez  ♟"`
