# Changelog

## v1.0.1

### New Features

- **Sitting Mechanics**:
  - Implemented proper sitting logic for **Count Duckula** and **Fuchsky**.
  - Entities now stop moving immediately when ordered to sit.
  - **Count Duckula**:
    - Wings now stop flapping and return to a neutral position when sitting.
    - Legs are hidden and body is lowered to visually represent sitting.
- **Randomized Messages**:
  - Added a curated list of fun "Vanish" messages for the Golden Carrot interaction (e.g., "Gone fishin'", "slipped into the shadows...").
  - Applied shared message logic to both **Count Duckula** and **Fuchsky**.

### Bug Fixes

- **Wing Animation**:
  - Fixed Count Duckula's wings rotating inwards. They now flap outwards correctly.
- **Fuchsky Interaction**:
  - Fixed an issue where using a Cookie would cause the player to eat it instead of feeding the fox.
  - Added interaction feedback (Hearts/Particles) even if the entity is already tamed.

### Internal

- Refactored message logic into `ModUtils` for shared use.
