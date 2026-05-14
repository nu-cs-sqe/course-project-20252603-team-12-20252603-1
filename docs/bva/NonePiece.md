# BVA Analysis for NonePiece

## Method: `NonePiece()`

### Step 1-3: Analysis

| Parameter                     | Catalog clue | Values considered |
| ----------------------------- | ------------ | ----------------- |
| Input: (none)                 | —            | —                 |
| Output: piece type            | Cases        | `NONE`            |
| Output: piece jump capability | Boolean      | `false`           |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC1: Constructor_OnNonePiece_TypeIsNone** ( :white_check_mark: )
  - **Method(s) under test**: `NonePiece()`
  - **State of the system**: no existing NonePiece object
  - **Expected output**: created piece type is `NONE`

- **TC2: Constructor_OnNonePiece_CanJumpIsFalse** ( :white_check_mark: )
  - **Method(s) under test**: `NonePiece()`
  - **State of the system**: no existing NonePiece object
  - **Expected output**: `canJump()` returns `false`

---

## Method: `makeCopy()`

### Step 1-3: Analysis

| Parameter                              | Catalog clue        | Values considered                                            |
| -------------------------------------- | ------------------- | ------------------------------------------------------------ |
| Input: original piece reference        | Pairs of references | same object is not allowed; copy must be a different object  |
| Output: returned piece type            | Cases               | `NONE`                                                       |
| Output: returned piece jump capability | Boolean             | `false`                                                      |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC3: MakeCopy_OnNonePiece_CopyTypeIsNone** ( :white_check_mark: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing NonePiece
  - **Expected output**: returned piece type is `NONE`

- **TC4: MakeCopy_OnNonePiece_CopyCanJumpIsFalse** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing NonePiece
  - **Expected output**: returned piece cannot jump

- **TC5: MakeCopy_OnNonePiece_CopyIsDifferentObject** ( :x: )
  - **Method(s) under test**: `makeCopy()`
  - **State of the system**: an existing NonePiece
  - **Expected output**: returned piece is a different object from the original

---
