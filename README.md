# The End The Beginning - Dungeon Escape Game v3.1.0

**The End The Beginning** is an immersive text-adventure dungeon escape game built with Java and JavaFX. Players embark on a perilous journey through a mysterious dungeon, battling monsters, collecting items, and progressing through **50 challenging levels** to achieve freedom.

**Current Version:** 3.1.0 (Text/UI Overhaul)  
**Developer:** Abdul Fornah  
**Framework:** Pure JavaFX 20 (Java 17+)

---

## What's New in v3.1.0

### Removed FXGL — Now Pure JavaFX
- Removed FXGL dependency for lighter, more maintainable codebase
- Pure JavaFX implementation with improved performance

### Text/UI Improvements
- **Overwrite-Only Text** - Screen text no longer scrolls; always shows newest message
- **High-Contrast Mode** - Accessibility-focused theme available in settings
- **Improved Focus Management** - Input field stays focused for seamless gameplay

### Five New Single-Player Features
- **Auto-Save & Quick-Load** - Progress auto-saved after key events; quick-load latest save
- **In-Game Settings Panel** - Configure text speed, high-contrast mode, and confirmations
- **Inventory Quick-Use** - Type `use <item>` to quickly use items during gameplay
- **Contextual Hints** - Helpful hints appear after 3 invalid inputs in the same state
- **Difficulty Preview** - See exact multipliers and bonuses before confirming difficulty

### Enhanced Code Quality
- Input normalization for consistent behavior
- Combat/math safety with clamped values
- Hardened SaveManager with new location (~/.the-end-the-beginning/saves/)
- Better error handling with Platform.runLater for UI thread safety

See [RELEASE_NOTES.md](RELEASE_NOTES.md) for detailed release notes.

---

## Game Features

- **Epic 50-Level Dungeon** - Navigate through 50 increasingly challenging levels
- **Three Character Classes** - Warrior, Mage, or Rogue with unique abilities
- **Automatic Saves** - Progress saved automatically
- **Dynamic Monster System** - Face diverse enemies with scaling difficulty
- **Inventory Management** - Collect and use items
- **Achievement System** - Unlock achievements for milestones

### Character Classes

**Warrior** - High health and defense (120 HP, 15 ATK, 8 DEF)
**Mage** - High attack power, bonus XP (80 HP, 25 ATK, 3 DEF)
**Rogue** - Balanced stats, critical hits (100 HP, 20 ATK, 5 DEF)

---

## Getting Started

### System Requirements
- **Java 17+** (required)
- **JavaFX 20** (included via Maven)
- 512MB RAM minimum
- **No FXGL required** - Pure JavaFX

### Quick Start

#### Build & Run (Maven - Recommended)
```bash
git clone https://github.com/Apexone11/TheEndOfTheBeginning.git
cd TheEndOfTheBeginning/TheEndTheBeginning
mvn clean javafx:run
```

#### Build & Run (Alternative)
```bash
# Compile the project
mvn clean compile

# Run the game
mvn javafx:run

# Package as JAR
mvn clean package
```

### Controls / Commands
- **Numbered choices** - Enter 1, 2, 3, etc. for menu options
- **Quick-Use Items** - Type `use <item>` (e.g., `use potion`) anytime during gameplay
- **Settings** - Accessible from game menu
- **Navigation** - Follow on-screen prompts

---

## How to Play

1. Click "Start New Game"
2. Choose to LOAD saved game or start NEW
3. Select character class (Warrior/Mage/Rogue)
4. Choose difficulty (with preview!)
5. Explore, fight, and survive 50 floors!

### Tips
- Type `use potion` anytime to quickly use items
- Your progress auto-saves after rooms and combat
- Enable high-contrast mode in settings for better visibility
- Watch for contextual hints if you're stuck

### Saves & Config
- **Saves**: `~/.the-end-the-beginning/saves/`
- **Config**: `~/.the-end-the-beginning/config.properties`

---

## Documentation

- [CHANGELOG.md](CHANGELOG.md) - Version history and updates
- [RELEASE_NOTES.md](RELEASE_NOTES.md) - Detailed release information
- [TODO.md](TODO.md) - Tasks, bugs, and feature roadmap

---

## Credits

**Developer:** Abdul Fornah  
**Framework:** JavaFX 20 with Java 17+

Thank you for playing!
