# BVA Analysis for Location

## Design Review Notes

- The design defines `Location` with two immutable fields, `x` and `y`, and three public methods: `Location(int x, int y)`, `getX()`, and `getY()`.
- The model represents board coordinates, so boundary values for both coordinates are the board edges.

## Specification

- **`public Location(int x, int y)`**: creates a location storing the provided coordinates.
- **`public int getX()`**: returns the stored x-coordinate.
- **`public int getY()`**: returns the stored y-coordinate.

---

## Method: `Location(int x, int y)` + `getX()`

### Step 1-3: Analysis

| Parameter            | Catalog clue           | Values considered |
| -------------------- | ---------------------- | ----------------- |
| Input: `x`           | Representative value   | `3`               |
| Input: `y`           | Fixed supporting value | `4`               |
| Output from `getX()` | Returned coordinate    | `3`               |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC1: Constructor_OnRepresentativeInput_GetXReturnsX** ( :white_check_mark: )
  - **Method(s) under test**: `Location(int x, int y)`, `getX()`
  - **State of the system**: create location with `x=3`, `y=4`
  - **Expected output**: `getX()` returns `3`

---

## Method: `Location(int x, int y)` + `getY()`

### Step 1-3: Analysis

| Parameter            | Catalog clue           | Values considered |
| -------------------- | ---------------------- | ----------------- |
| Input: `x`           | Fixed supporting value | `3`               |
| Input: `y`           | Representative value   | `4`               |
| Output from `getY()` | Returned coordinate    | `4`               |

### Step 4: Test Cases (Catalog-aligned Each-Choice Strategy)

- **TC2: Constructor_OnRepresentativeInput_GetYReturnsY** ( :x: )
  - **Method(s) under test**: `Location(int x, int y)`, `getY()`
  - **State of the system**: create location with `x=3`, `y=4`
  - **Expected output**: `getY()` returns `4`
