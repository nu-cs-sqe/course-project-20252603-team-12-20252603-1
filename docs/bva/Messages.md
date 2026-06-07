# BVA Analysis for Messages

Package: `ui.Messages`

Thin facade over `ResourceBundle` (lab 5 pattern). Loads `messages.properties` for a given `Locale` and exposes `getString(key)` with a visible fallback when a key is missing.

**API contract:** `Locale` and `key` parameters are **non-null**. Callers must not pass `null`. Invalid null input is **out of scope** (unrepresentable boundary — not tested here).

## Method: `Messages(Locale locale)`

### Step 1: Inputs and outputs

| Input / state | Equivalence classes                                      |
| ------------- | -------------------------------------------------------- |
| `locale`      | valid `Locale` (e.g. `Locale.ENGLISH`)                   |
| Bundle load   | default `messages.properties` available on classpath       |

### Step 2: Catalog types

| Variable / output | Catalog type | Notes                          |
| ----------------- | ------------ | ------------------------------ |
| `locale`          | **Cases**    | valid locale only              |
| Loaded bundle     | **Pointers** | `ResourceBundle` for `messages` |

### Step 3: Concrete boundary values

- **Cases:** `locale = Locale.ENGLISH`.

### Step 4: Test cases

Constructor-only behavior is covered indirectly by `getString` TCs below.

---

## Method: `getString(String key)`

### Step 1: Inputs and outputs

| Input / state | Equivalence classes                                                |
| ------------- | ---------------------------------------------------------------- |
| `key`         | present in bundle; absent from bundle                            |
| Return value  | bundle value for present key; `!key!` sentinel for missing key   |

### Step 2: Catalog types

| Variable / output | Catalog type | Notes                                           |
| ----------------- | ------------ | ----------------------------------------------- |
| `key`             | **Strings**  | known key vs unknown key                        |
| Return value      | **String**   | exact bundle text vs bang-wrapped missing marker |

### Step 3: Concrete boundary values

- **Strings (known):** `key = "matchupPattern"` (exists in `messages.properties`).
- **Strings (unknown):** `key = "nonexistentKey"` (guaranteed absent).

### Step 4: Test cases

- **MS-TC1: GetString_OnKnownKey_ReturnsBundleValue** ( :white_check_mark: )
  - **Method(s) under test**: `Messages(Locale)`, `getString(String)`
  - **State of the system**: `Messages` constructed with `Locale.ENGLISH`; key `"matchupPattern"` exists in default bundle
  - **Expected output**: returns `"{0} versus {1}"`

- **MS-TC2: GetString_OnMissingKey_ReturnsBangWrappedKey** ( :white_check_mark: )
  - **Method(s) under test**: `getString(String)`
  - **State of the system**: `Messages` constructed with `Locale.ENGLISH`; key `"nonexistentKey"` is absent
  - **Expected output**: returns `"!nonexistentKey!"`

- **MS-TC3: GetString_OnSpanishLocale_ReturnsSpanishBundleValue** ( :x: )
  - **Method(s) under test**: `getString(String)`
  - **State of the system**: `Messages` constructed with `Locale.forLanguageTag("es")`; `messages_es.properties` defines `matchupPattern`
  - **Expected output**: returns `"{0} contra {1}"`
