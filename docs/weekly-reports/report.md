# Week 3 (04/13/2026-04/19/2026)
**Planning and Progress Tracking**:
1. [done] Didier: Project setup
2. [done] Matthew: Review and accept PR
3. [done] Alex: Review PR
4. [done] Group: Decided on chess project


# Week 4 (04/20/2026-04/26/2026) 
**Progress Tracking**:
1. [done] Matthew: Created design docs ([#9](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/9))
2. [done] Alex: Created user stories ([#3](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/issues/3))
3. [done] Matthew: Initialized Project task ([#3](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/issues/3))
4. [done] Didier: Edited user stories ([#3](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/issues/3))

**Planning Tracking**:
1. [done] Matthew: Piece abstract class ([#13](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/13))
2. [done] Alex: Knight class ([#14](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/14))
3. [done] Didier: King class ([#12](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/12))

# Week 5 (04/27/2026-05/03/2026) 
**Progress Tracking**:
1. [done] Matthew: Implemented Piece abstract class with constructor, getters, canJump logic, and makeCopy abstract method ([#13](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/13))
2. [done] Alex: Generated BVA, implemented constructor and makeCopy() for Knight class using TDD workflow ([#14](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/14))
3. [done] Didier: Generated BVA, implemented constructor and makeCopy() for King class using TDD workflow ([#12](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/12))

**Planning Tracking**:
1. [done] Matthew: Create Board class skeleton in the domain package as a foundation for future Board implementation, BVA, and TDD passes ([#19](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/19))
2. [done] Alex: Generate BVA and implement constructor, makeCopy(), and isValidMoveShape() for remaining piece classes (Bishop, Pawn, Queen, Rook) ([#22](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/22))
3. [done] Didier: Implement Location class ([#20](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/20)), GameState enum ([#16](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/16)), and BoardController class ([#21](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/21))

# Week 6 (05/04/2026-05/10/2026)
**Progress Tracking**:
1. [done] Matthew: Create Board class, generate BVA for constructor, getSnapshot, and getCurrentGameState. Implemented tests and methods. ([#19](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/19))
2. [done] Alex: Generate BVA and implement constructor, makeCopy(), and isValidMoveShape() for remaining piece classes (Bishop, Pawn, Queen, Rook) ([#22](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/22))
3. [done] Didier: Implement Location class ([#20](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/20)), GameState enum ([#16](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/16)), and BoardController class ([#21](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/21))

**Planning Tracking**:
1. [not started] Matthew: Create NonePiece class, and refactor Board to use strategy pattern. Implement Standard and FischerRandom pattern in Board for Game Initialization User Story. ([#29](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/29))
2. [not started] Alex: Create BoardView class skeleton in the domain package, implementing all the necessary code for Game Initialization User Story (BVA, TDD passes, etc) ([#27](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/27))
3. [not started] Didier: Create GameStatsView class skeleton in the domain package, implementing all the necessary code for Game Initialization User Story (BVA, TDD passes, etc) ([#28](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/28))

# Week 7 (05/11/2026-05/17/2026)
**Progress Tracking**:
1. [done] Matthew: Implemented NonePiece class, and implemented Standard and FischerRandom patterns Board for Game Initialization User Story. ([#29](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/29))
2. [in progress] Alex: Implemented BoardView class skeleton in the `ui` package, implemented BVA for BoardView. ([#27](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/27)), reviewed ([#28](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/28))
3. [in progress] Didier: Implemented GameStatsView class in the `ui` package, implementing all the necessary code for Game Initialization User Story (BVA, TDD passes, etc) ([#28](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/28))

**Planning Tracking**:
1. [not started] Matthew: Implement `Board` constructor from `Piece[][]` to allow for testing and further dependency injection (strategy pattern works for initialization, but limits testing to default board states). Also should implement cyclomatic complexity testing (Jacoco and Pitest). ([#19](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/19))
2. [not started] Alex: Implement final details of `BoardView` implementation + PR merging ([#27](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/27))
3. [not started] Didier: Merge GameStatsView [#28](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/28) and BoardController [#21](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/21); coordinate `Board` / `BoardController` integration with Matthew’s branch

# Week 8 (05/18/2026-05/24/2026) 
**Progress Tracking**:
1. [done] Matthew: Implemented new Board constructor (with BVA, tests, and logic) with `Piece[][]` input to allow for dependency injection during testing. Implemented Jacoco and Pitest for project. ([#19](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/19), [#36](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/36)). Opened PR draft and issue for integration testing (https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/issues/40).
2. [done] Didier: Merged GameStatsView ([#28](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/28)). Pushed MainView for review. ([#35](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/35)). Created i18n draft PR ([#38](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/38))
3. [done] Alex: Reviewed and merged GameStatsView ([#28](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/28))
4. [done] Group: Decided on and documented decisions for integration testing (https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/issues/40)


**Planning Tracking**
1. [not started] Matthew: Implement i18n in English and Spanish
   ([#38](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/38)), implement Move class (BVA, TDD)
   ([#56](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/56)), implement WelcomeController (BVA, TDD)
   ([#54](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/54)), create AppController BVA
   ([#50](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/50)), refactor piece makeCopy() to preserve
   hasMoved state ([#57](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/57))
2. [50% done] Didier: Review feedback for MainView
   ([#35](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/35)) and review other PRs, implement Board
   movePiece and isValidMoveShape for all piece types (BVA, TDD)
   ([#47](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/47)), complete BoardController
   ([#48](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/48)), update GameStatsView for move-piece
   integration ([#51](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/51)), refactor MainView
   ([#53](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/53))
3. [80%] Alex: Continue working on BoardView
   ([#27](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/27)), complete WelcomeView implementation
   (Chess960 radio buttons, isChess960Selected, setStartGameAction; BVA, TDD)
   ([#42](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/42))

# Week 9 (05/25/2026-05/31/2026)
**Progress Tracking**:
1. [done] Matthew: Implemented Move class  (BVA, MoveType enum, all constructors, withPromotionType)
   ([#56](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/56))
2. [done] Matthew: Refactored all piece makeCopy() methods to preserve hasMoved state
   ([#57](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/57))
3. [done] Matthew: Implemented WelcomeController (BVA, startGame, selectedInitializer) and updated design docs
   ([#54](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/54))
4. [done] Matthew: Created BVA for AppController. This was deprecated due to project redesign. 
   ([#50](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/50))
5. [in progress] Matthew: Set up i18n infrastructure (plan, messages.properties, GameStatsView i18n integration). Will wait until finished logical implementation to fully implement i18n.
   ([#38](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/38))
6. [done] Didier: Implemented Board movePiece ([#47](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/47))
7. [done] Didier: Implemented BoardController (setBoard, handleSquareClick). This was deprecated due to project redesign. 
   ([#48](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/48))
8. [done] Didier: Added updateCurrentPlayerLabel and GameStatsView updates. This was deprecated due to project redesign.
   ([#51](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/51))
9. [done] Didier: Refactored MainView to inject BoardController and aligned tests
   ([#53](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/53))
10. [done] Alex: Implemented WelcomeView (Chess960 radio buttons, isChess960Selected, setStartGameAction, createWelcomeScreenUI) ([#42](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/42))
11. [done] Group: Redesigned project to have controllers create views; Main only instantiates WelcomeController, it no longer wires everything together

**Planning Tracking**:
1. [not started] Matthew: Complete and merge i18n
   ([#38](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/38)). Update BoardView to render legal move and capture highlights. Fix WelcomeController to work with new wiring.
2. [not started] Didier: Add check filtering to MoveGenerator. Add BoardController.getLegalMovesForSelection(). 
3. [not started] Alex: Add PromotionDialog.

# Week X (XX/XX/2026-XX/XX/2026) TEMPLATE (You can change the format to whatever the team likes better)
**Planning and Progress Tracking**:
1. [done] Person: Task (Links to PR)
2. [not started] Person: Task (Links to PR)
3. [80% done] Person: Task (Links to PR)
