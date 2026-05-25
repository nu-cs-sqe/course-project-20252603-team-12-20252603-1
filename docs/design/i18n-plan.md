# Internationalization (i18n) Plan

## 1. Purpose

Externalize user-visible UI text so the chess application can support multiple languages without changing Java logic.

## 2. Goals and non-goals

### Goals

- Store UI strings in locale-specific resource files, not hard-coded in Swing components.
- Use standard JDK APIs (`ResourceBundle`, `.properties` files) — no third-party i18n framework for v1.
- Keep domain logic (`domain` package) free of locale-specific formatting; only the `ui` layer loads translated strings.
- Start with English as the default locale; add at least one additional locale in a follow-up milestone (see §6).

### Non-goals (for initial setup)

- Translating player-entered names (user data stays as provided).
- Translating chess notation, piece class names in code, or test assertion literals unless they become user-visible.
- Right-to-left (RTL) layout or accessibility-specific locale handling (out of scope unless required later).
- Gradle plugins for i18n — **not needed**; the JDK supplies everything we use.

## 3. Technical approach

### 3.1 Mechanism

| Piece                   | Location / convention                                                                 |
| ----------------------- | ------------------------------------------------------------------------------------- |
| Default bundle          | `src/main/resources/messages.properties`                                              |
| Locale-specific bundles | `src/main/resources/messages_<lang>.properties` (e.g. `messages_es.properties`)       |
| Loader helper           | `ui.Messages` — wraps `ResourceBundle.getBundle("messages")`                          |
| Key naming              | Dot-separated, grouped by feature: `game.matchup.separator`, `game.status.turn`, etc. |

`ResourceBundle` resolves the bundle using the **default JVM locale** unless we later pass an explicit `Locale` (see §5).

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

| Phase                  | Behavior                                                                                  |
| ---------------------- | ----------------------------------------------------------------------------------------- |
| **Phase 0 (this PR)**  | Document plan; add `messages.properties` + `Messages` helper; no UI refactor              |
| **Phase 1**            | Replace hard-coded UI strings in existing views (start with `GameStatsView`)              |
| **Phase 2**            | Add `messages_es.properties` (or agreed second locale) with complete keys                 |
| **Phase 3 (optional)** | In-app language menu or settings that call `ResourceBundle.getBundle("messages", locale)` |

Until Phase 3, users see strings for their JVM default locale if a matching bundle exists; otherwise the default `messages.properties` is used.

## 6. Testing strategy

- **Scaffolding:** `MessagesTest` — one test loads default bundle and asserts a known key (smoke test, not full translation QA).
- **After UI migration:** Existing `GameStatsView` BVA tests stay valid if they assert composed player names; separator comes from bundle in integration-style tests or we inject locale `en` explicitly in tests.

Full BVA for i18n-specific behavior is deferred until we expose locale switching or parameterized message formats.

## 7. Risks and decisions

| Topic                       | Decision                                                                             |
| --------------------------- | ------------------------------------------------------------------------------------ |
| Missing key at runtime      | `MissingResourceException` — fail fast in dev; add keys before merge                 |
| Duplicate keys across files | Code review + optional script later; manual parity check for P1                      |
| ICU / plural rules          | Not used in v1; plain `MessageFormat` only if we need placeholders like `{0}'s turn` |
| Swing-specific libs         | Not adopted; `ResourceBundle` is sufficient                                          |

## 8. References

- [Java ResourceBundle](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/ResourceBundle.html)
- UI scope: `docs/bva/GameStatsView.md`, `docs/design/chess-full-design.puml`
