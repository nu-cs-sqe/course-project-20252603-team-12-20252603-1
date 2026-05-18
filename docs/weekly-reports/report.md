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
1. [done] Matthew: Create Board class skeleton in the domain package as a foundation for future Board implementation, BVA, and TDD passes ([#19](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/19))
2. [done] Alex: Generate BVA and implement constructor, makeCopy(), and isValidMoveShape() for remaining piece classes (Bishop, Pawn, Queen, Rook) ([#22](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/22))
3. [done] Didier: Implement Location class ([#20](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/20)), GameState enum ([#16](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/16)), and BoardController class ([#21](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/21))

**Planning Tracking**:
1. [not started] Matthew: Create NonePiece class, and implement the FischerRandom in Board for Game Initialization User Story (BVA, TDD passes, etc) ([#29](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/29))
2. [not started] Alex: Create BoardView class skeleton in the domain package, implementing all the necessary code for Game Initialization User Story (BVA, TDD passes, etc) ([#27](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/27))
3. [not started] Didier: Create GameStatsView class skeleton in the domain package, implementing all the necessary code for Game Initialization User Story (BVA, TDD passes, etc) ([#28](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/28))

# Week 7 (05/11/2026-05/17/2026)
**Progress Tracking**:
1. [done] Matthew: Implemented NonePiece class, and implemented the FischerRandom in Board for Game Initialization User Story (BVA, TDD passes, etc) ([#29](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/29))
2. [in progress] Alex: Implemented BoardView class skeleton in the `ui` package, implemented BVA for BoardView. ([#27](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/27)), reviewed ([#28](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/28))
3. [in progress] Didier: Implemented GameStatsView class in the `ui` package, implementing all the necessary code for Game Initialization User Story (BVA, TDD passes, etc) ([#28](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/28))

**Planning Tracking**:

1. [not started] Matthew: Implement `Board` constructor from `Piece[][]`; cyclomatic-complexity pass on [#19](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/19)
2. [not started] Alex: Implement final details of `BoardView` implementation + PR merging ([#27](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/27))
3. [not started] Didier: Merge [#28](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/28) and [#21](https://github.com/nu-cs-sqe/course-project-20252603-team-12-20252603-1/pull/21); coordinate `Board` / `BoardController` integration with Matthew’s branch


# Week X (XX/XX/2026-XX/XX/2026) TEMPLATE (You can change the format to whatever the team likes better)
**Planning and Progress Tracking**:
1. [done] Person: Task (Links to PR)
2. [not started] Person: Task (Links to PR)
3. [80% done] Person: Task (Links to PR)
