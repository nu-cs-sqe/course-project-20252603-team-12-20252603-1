# Workspace Instructions

This repository uses a strict TDD workflow.

## Coding Rules

- Write one focused test at a time.
- Keep each test to one assertion when practical.
- For equality assertions, assign the expected value to `expected` and the real value to `actual` before calling `assertEquals(expected, actual)`.
- Implement only the minimum production code needed for the current test to pass.
- After each test and implementation step, run the smallest useful validation.
- Update the relevant BVA or design note when a test case is implemented.

## Commit Rules

- Commit one test/implementation step at a time.
- Use descriptive commit messages in this format:
  - `Method_State_Expectation passes`
- Example:
  - `Pushback_OnTwoDifferentElements_AddZero_LastElementIsZero passes`

## Workflow Preference

- Prefer red-green-refactor in small increments.
- Do not bundle multiple unrelated test cases into one commit.
- Keep changes minimal and aligned with the current test only.
