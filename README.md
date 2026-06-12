![Gradle Build](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/actions/workflows/main.yml/badge.svg)

[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23617110)
# Chess

## Contributors
- Alex Anca
- Matthew Xu
- Didier Munezero

## Dependencies
- JDK 11
- JUnit 5.10
- Gradle 8.10

## Running the Game

Launch the game with:

```
./gradlew run
```

## Code Coverage & Mutation Testing

All testable code has 100% line coverage and 100% of its mutants killed. The remaining gaps are explained below.

| Class | Why coverage isn't 100% / mutants can't be killed |
| --- | --- |
| `WelcomeView` | Swing window. PIT runs without a display, so the class can't be constructed. Its lines never run and its mutants can't be reached. |
| `MainView` | Swing window. Can't be constructed without a display, so lines never run and mutants are unreachable. |
| `BoardView` | Swing panel. Can't be constructed without a display, so lines never run and mutants are unreachable. |
| `EndGameView` | Swing window. Can't be constructed without a display, so lines never run and mutants are unreachable. |
| `PromotionView` | Swing window. Can't be constructed without a display, so lines never run and mutants are unreachable. |
| `Main` | Entry point. Not unit testable, so no lines are covered. |
| `WelcomeController` | The lines in `show()` open a real window, which needs a display. The default game-launch code never runs in tests because tests replace it with a mock. These uncovered lines account for all missed mutants. |
| `EndGameController` | The lines in `show()` open a real window, which needs a display. The default welcome-screen navigation never runs in tests because tests replace it with a mock. These uncovered lines account for all missed mutants. |
| `BoardController` | The lines in `show()` open a real window, which needs a display. The default promotion dialog never runs in tests because tests replace it with a mock. |
| `MoveGenerator` | 100% line coverage. 15 equivalent mutants: swapping `+`/`-` on symmetric move offsets, or `*`/`/` by ±1, produces identical behavior; the boundary mutants test values that can never occur. 2 mutants are in error-handling code that can never run, because castling moves are only generated when a rook exists. |
| `Board` | 100% line coverage. 2 equivalent mutants in `findCastlingRookFile`: the boundary value can never occur. |

## Acknowledgements
 - Dr Yiji Zhang
 - PM Eiko Reisz
 - Uncle Bob (Robert Cecil Martin)
