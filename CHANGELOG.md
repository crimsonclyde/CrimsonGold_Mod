# Changelog

## v1.0.3

### New Features

- **Audio Implementation**:
  - Added custom sound effects for **Count Duckula**.
    - Includes ambient quacks, hurt sounds, and death sounds.
  - Registered sounds in `ModSounds` registry.

- **World Generation**:
  - **Count Duckula**: Now spawns in **Deep Dark** and **Dark Forest** biomes.
  - Implemented via NeoForge biome modifiers for better compatibility.

## v1.0.2

### Improvements

- **Fuchsky Taming**:
  - Increased taming requirement to **3 Cookies** to match Count Duckula's difficulty.
  - Added visual feedback: Smoke particles for the first 2 cookies, Heart particles on the 3rd (Success).
- **Interaction Logic**:
  - **Sitting Parity**: Fixed an issue where Fuchsky would only sit if interacting with specific items. Now, both entities reliably toggle Sit/Stand when right-clicked with an Empty Hand or non-food item.
  - **Consistency**: Explicitly excluded Golden Carrots (Vanish item) and Taming Food from triggering the "Sit" command for both entities.

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
