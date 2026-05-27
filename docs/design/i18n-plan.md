# Internationalization (i18n) Plan

## 1. Purpose

Externalize user-visible UI text so the chess application can support multiple languages without changing Java logic.

## 2. Goals and non-goals

### Goals

- Store UI strings in locale-specific resource files, not hard-coded in Swing components.
- Use standard JDK APIs (`ResourceBundle`, `Locale`, `MessageFormat`) — no third-party i18n framework.
- Keep domain logic (`domain` package) free of locale-specific formatting; only the `ui` layer loads translated strings.
- Pass `Locale` as a parameter so language can be changed at runtime without restarting.

### Non-goals (for initial setup)

- Translating player-entered names (user data stays as provided).
- Translating chess notation, piece class names in code, or test assertion literals unless they become user-visible.
- Right-to-left (RTL) layout or accessibility-specific locale handling (out of scope unless required later).
- Gradle plugins for i18n — **not needed**; the JDK supplies everything we use.

## 3. Technical approach

### 3.1 Mechanism

| Piece                   | Location / convention                                                        |
| ----------------------- | ---------------------------------------------------------------------------- |
| Default bundle          | `src/main/resources/messages.properties`                                     |
| Locale-specific bundles | `src/main/resources/messages_<lang>.properties` (e.g. `messages_es.properties`) |
| Loading                 | Each view calls `ResourceBundle.getBundle("messages", locale)` directly |
| Key naming              | Flat camelCase: `matchupPattern`, `currentTurnPattern`, etc.                 |

`Locale` is passed into each view constructor and forwarded by `MainView`. This supports runtime locale switching without any refactor.

### 3.2 Build and dependencies

- **No changes to `build.gradle.kts`** for i18n scaffolding.
- Properties files are packaged automatically from `src/main/resources`.
- CI (`./gradlew test checkstyle`) continues to gate merges; i18n tests are small smoke tests on bundle loading.

## 4. Scope by package

| Package    | i18n?   | Notes                                                                                          |
| ---------- | ------- | ---------------------------------------------------------------------------------------------- |
| `ui`       | Yes     | Labels, buttons, dialogs, status text                                                          |
| `domain`   | No      | Game rules and state are locale-neutral                                                        |
| `src/test` | Minimal | Tests may assert English bundle values for loader smoke tests; avoid testing every translation |

## 5. Locale selection (phased)

| Phase                 | Behavior                                                                         |
| --------------------- | -------------------------------------------------------------------------------- |
| **Phase 0 (this PR)** | Document plan; add `messages.properties`; no UI refactor                         |
| **Phase 1**           | Replace hard-coded UI strings in existing views (start with `GameStatsView`); pass `Locale` through constructors |
| **Phase 2**           | Add `messages_es.properties` (or agreed second locale) with complete keys        |
| **Phase 3**           | In-app language menu in `WelcomeView`; user-selected `Locale` passed to `MainView` and all child views |