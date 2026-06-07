# BVA Analysis for EndGameView

Package: `ui.EndGameView`

**API contract:** All `String` and `Locale` parameters are **non-null**. Callers must not pass `null`. Invalid null input is **out of scope** (unrepresentable boundary — not tested here).

**Headless guard:** `EndGameView` extends `JFrame`. TCs use `@BeforeAll assumeTrue(!GraphicsEnvironment.isHeadless())`.

Scope: **Constructor** UI strings from bundle. `resultMessage` is caller-supplied dynamic text (player names / win outcome) — not part of this i18n slice.

## Method: `EndGameView(String resultMessage, Locale locale)`

### Step 1: Inputs and outputs

| Input / state   | Equivalence classes                                                          |
| --------------- | ---------------------------------------------------------------------------- |
| `resultMessage` | **Strings** — non-empty outcome line shown on result label (caller-supplied) |
| `locale`        | **Cases** — `Locale.ENGLISH` (app default) or `Locale.forLanguageTag("es")`  |
| Bundle keys     | `gameOverTitle`, `playAgain`                                                 |

### Step 2: Catalog types

| Variable / output | Catalog type | Notes                              |
| ----------------- | ------------ | ---------------------------------- |
| `resultMessage`   | **Strings**  | dynamic; not loaded from bundle    |
| `locale`          | **Cases**    | English vs Spanish                 |
| Window title      | **String**   | from `gameOverTitle`               |
| Play-again button | **String**   | from `playAgain`                   |

### Step 3: Concrete boundary values

- **Strings:** `resultMessage = "Alice wins!"` (representative non-empty).
- **Cases:** `Locale.ENGLISH`; `Locale.forLanguageTag("es")`.

### Step 4: Test cases

- **EG-TC1: Constructor_OnEnglishLocale_WindowTitleFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `EndGameView(String, Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`; `resultMessage = "Alice wins!"`
  - **Expected output**: frame title is `"Game Over"`

- **EG-TC2: Constructor_OnEnglishLocale_PlayAgainButtonFromBundle** ( :white_check_mark: )
  - **Method(s) under test**: `EndGameView(String, Locale)`
  - **State of the system**: `locale = Locale.ENGLISH`
  - **Expected output**: play-again button text is `"Play Again"`

- **EG-TC3: Constructor_OnSpanishLocale_WindowTitleFromBundle** ( :x: )
  - **Method(s) under test**: `EndGameView(String, Locale)`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`
  - **Expected output**: frame title is `"Fin del juego"`

- **EG-TC4: Constructor_OnSpanishLocale_PlayAgainButtonFromBundle** ( :x: )
  - **Method(s) under test**: `EndGameView(String, Locale)`
  - **State of the system**: `locale = Locale.forLanguageTag("es")`
  - **Expected output**: play-again button text is `"Jugar de nuevo"`
