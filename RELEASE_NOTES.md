# Release Notes - The End The Beginning

This file tracks all releases and updates for **The End The Beginning - Dungeon Escape Game**.


## Version 3.1.0 - Text/UI Overhaul (October 2025)

**Release Date:** October 2025

### 🎉 Overview

Version 3.1.0 is a major refactoring release focused on improving text display, user experience, and code quality. The most significant change is the **removal of FXGL** in favor of pure JavaFX, resulting in a lighter, more maintainable codebase.

### 🌟 New Features

#### Overwrite-Only Text Behavior
- ✅ **No Scrolling** - Game text now replaces instead of appending for cleaner UX
- ✅ **New Text Helpers** - `showGameText()` and `showGameTextLine()` methods
- ✅ **Platform.runLater** - All UI updates properly threaded for safety

#### Five Single-Player Features
1. ✅ **Auto-Save & Quick-Load** - Auto-saves after rooms/combat/level-ups; quick-load latest save
2. ✅ **In-Game Settings Panel** - Text speed, high-contrast mode, confirmations
3. ✅ **Inventory Quick-Use** - Type `use <item>` anytime (e.g., `use potion`)
4. ✅ **Contextual Hints** - Helpful hints after 3 invalid inputs in same state
5. ✅ **Difficulty Preview** - See exact multipliers/bonuses before confirming difficulty

#### Code Quality Improvements
- ✅ **Input Normalization** - `InputUtil` class for consistent input handling
- ✅ **Balance Constants** - Centralized difficulty multipliers in `Balance` class
- ✅ **Combat Safety** - Clamped HP/ATK values prevent overflow
- ✅ **Settings Persistence** - Config saved to `~/.the-end-the-beginning/config.properties`

### ⚠️ Breaking/Infrastructure Changes

- ✅ **FXGL Removed** - Now pure JavaFX; lighter and more maintainable
- ✅ **Class Rename** - `player` → `Player` (proper Java naming)
- ✅ **New Save Location** - `~/.the-end-the-beginning/saves/` (from `~/.theendthebeginning/`)
- ✅ **Version Bump** - Project version: 3.0.0 → 3.1.0

### 🐛 Bug Fixes

- ✅ **UI Thread Safety** - All UI updates wrapped in `Platform.runLater`
- ✅ **SaveManager Hardening** - Try-with-resources for file operations
- ✅ **Input Validation** - Empty player names rejected with clear message
- ✅ **Monster Damage** - Clamped to safe ranges [0, 1,000,000]
- ✅ **HP Clamping** - Player and monster HP always within valid bounds

### 🎨 UI/UX Improvements

- ✅ **High-Contrast Theme** - `high-contrast.css` for accessibility
- ✅ **Focus Management** - Input field stays focused after actions
- ✅ **Difficulty Preview** - Shows exact stats before confirming
- ✅ **Contextual Hints** - Smart help system triggers after repeated mistakes

### 📦 New Files

**Java Classes:**
- `gameproject/InputUtil.java` - Input normalization utility
- `gameproject/Balance.java` - Difficulty constants and safety methods
- `gameproject/Settings.java` - Settings persistence

**Resources:**
- `high-contrast.css` - Accessibility theme

### 📝 Documentation Updates

- ✅ **README.md** - Updated to v3.1.0 with new features
- ✅ **RELEASE_NOTES.md** - This file (new section)
- ✅ **Version** - Updated in `pom.xml` to 3.1.0

### 🎯 Compatibility

**Supported Versions:** 3.1.x (Current)  
**Java:** 17+ required  
**JavaFX:** 20  
**FXGL:** None (removed)

---

## Version 3.0.1 - Infrastructure & Future-Ready Update

**Release Date:** October 2025

### 🎉 Overview

Version 3.0.1 focuses on infrastructure improvements, future-ready enhancements, and privacy updates. This release prepares the project for advanced GUI features and ensures the game can be easily shared and built by anyone.

### 🌟 New Features

- ✅ **FXGL Integration**: Added FXGL game development framework for future GUI enhancements
- ✅ **Package Documentation**: Added `package-info.java` files for better code documentation and GitHub visibility
- ✅ **Portable Launch Scripts**: Fixed batch files to work from any directory location
- ✅ **Enhanced Security Policy**: Updated with actual version support and clear vulnerability reporting

### 🐛 Bug Fixes

- ✅ **Hardcoded Paths**: Removed absolute paths from `Launch-Game.bat` and `run-game.bat`
- ✅ **Launch Scripts**: Now use relative paths (`%~dp0`) so they work from any download location

### 🔧 Technical Changes

- ✅ **Dependencies**: Added FXGL 17.3 for future game engine features
- ✅ **Code Documentation**: Java packages now properly documented for GitHub
- ✅ **Privacy Updates**: Removed direct email addresses from public documentation
- ✅ **.gitignore**: Enhanced to exclude more build artifacts and temporary files
- ✅ **CODE_OF_CONDUCT.md**: Customized for this project with privacy-conscious contact methods
- ✅ **SECURITY.md**: Updated with accurate version info and reporting procedures

### 📦 Infrastructure

**Added Files:**
- `src/main/java/gameproject/package-info.java`
- `src/main/java/main/model/package-info.java`

**Modified Files:**
- `pom.xml` - Added FXGL dependency
- `.gitignore` - Enhanced exclusions
- `CODE_OF_CONDUCT.md` - Customized and privacy-focused
- `SECURITY.md` - Project-specific security policy
- `Launch-Game.bat` - Relative paths
- `run-game.bat` - Relative paths

### 🎯 Future Ready

This release lays the groundwork for:
- Advanced graphics and animations using FXGL
- Better code organization and documentation
- Easier collaboration and contribution
- Professional open-source project structure

---

## Version 3.0.0 - Extended Edition

**Release Date:** September 30, 2024

### 🎉 Overview

Version 3.0.0 represents a major update to "The End The Beginning - Dungeon Escape," bringing extended gameplay, persistent save system, and enhanced visual presentation. This release transforms the game from a 10-level dungeon crawler into an epic 50-level adventure with automatic progress saving.

---

## 🌟 Headline Features

### 1. Extended 50-Level Dungeon
The dungeon has grown from 10 to 50 levels, providing:
- **5x longer gameplay** - Hours of additional content
- **Improved difficulty curve** - Better balanced progression
- **New achievements** - Milestones at levels 30, 40, and 50
- **Epic victory** - Special message for conquering all 50 floors

### 2. Automatic Save System
Never lose your progress again:
- **Auto-save** on every floor progression
- **Save/Load menu** on game start
- **Persistent storage** in user home directory
- **Complete state preservation** - All stats, items, and progress saved

### 3. Enhanced User Interface
Improved visual experience:
- **Larger window** - 900x700 (up from 800x600)
- **Better CSS styling** - Enhanced gradients and effects
- **Minimum size constraints** - Prevents window from becoming too small
- **Version in title** - Shows v3.0 in window title
- **Icon framework** - Ready for custom icon (file needed)

---

## 📋 Complete Feature List

### Gameplay Enhancements
- ✅ 50-level dungeon (was 10)
- ✅ Three new achievements (Elite Warrior, Dungeon Conqueror, Supreme Champion)
- ✅ Auto-save on floor progression
- ✅ Load saved game from main menu
- ✅ Choose between loading save or starting fresh
- ✅ Extended victory message for 50-level completion

### Technical Improvements
- ✅ SaveManager class for game persistence
- ✅ Player.restoreSaveData() method for loading saves
- ✅ Enhanced error handling for file I/O
- ✅ Better code organization and documentation
- ✅ .gitignore for clean repository

### UI/UX Improvements
- ✅ Window size increased to 900x700
- ✅ Minimum window size set to 800x600
- ✅ Enhanced CSS with improved gradients
- ✅ Better visual feedback on UI elements
- ✅ Version number in window title

### Documentation
- ✅ CHANGELOG.md - Complete version history
- ✅ BUGS.md - Known issues and planned improvements
- ✅ BUILD.md - Detailed build instructions
- ✅ README.md - Completely rewritten for v3.0
- ✅ Icon directory README with instructions

### Build System
- ✅ Updated to version 3.0.0 in pom.xml
- ✅ JAR packaging plugin configured
- ✅ Successfully builds executable JAR
- ✅ Proper metadata (name, description)

---

## 🔧 Technical Details

### Save File Format
- **Location:** `~/.theendthebeginning/savegame.txt`
- **Format:** Plain text key-value pairs
- **Encoding:** UTF-8
- **Size:** ~500 bytes typical

### Saved Data Includes:
- Player name and class
- Character level and experience
- Current and max health
- Attack, defense, and magic stats
- Dungeon floor progress
- Rooms explored count
- Monsters defeated count
- Timestamp of save

### New Files Added:
```
SaveManager.java          - Save/load system
CHANGELOG.md             - Version history
BUGS.md                  - Known issues
BUILD.md                 - Build instructions
.gitignore               - Git ignore rules
icons/README.md          - Icon instructions
RELEASE_NOTES_v3.0.md   - This file
```

### Modified Files:
```
TheEndTheBeginning.java  - Window setup, icon support
MainControllerNew.java   - 50 levels, save integration
player.java              - Save restore, new achievements
game-style.css           - Enhanced styling
pom.xml                  - Version 3.0.0, JAR plugin
README.md                - Complete rewrite
```

---

## 🐛 Known Issues

### Critical
None

### High Priority
1. **Icon file missing** - Framework in place, image file needed
   - Impact: Uses default Java icon
   - Location: `src/main/resources/icons/game-icon.png`

### Medium Priority
2. **GameState synchronization** - Legacy system edge cases
   - Impact: Stats display may occasionally desync
   - Planned: Full migration away from GameState

3. **Save file location** - No user choice
   - Impact: Files in home directory without asking
   - Planned: Add save location option

4. **Monster scaling** - May need tuning for levels 30-50
   - Impact: Difficulty curve might not be perfect
   - Planned: Balance adjustments in v3.1

See [BUGS.md](BUGS.md) for complete list.

---

## 🎮 How to Use New Features

### Using the Save System

1. **Starting a New Game:**
   - Click "Start New Game"
   - If a save exists, choose LOAD or NEW
   - Type your choice and press Enter

2. **Automatic Saving:**
   - Game saves automatically when you progress floors
   - No manual save button needed
   - Progress saved silently in background

3. **Loading a Save:**
   - Select LOAD when prompted at game start
   - Your progress will be restored exactly as saved
   - Continue from where you left off

### Finding Your Save File

**Windows:** `C:\Users\[YourName]\.theendthebeginning\savegame.txt`  
**macOS:** `/Users/[YourName]/.theendthebeginning/savegame.txt`  
**Linux:** `/home/[YourName]/.theendthebeginning/savegame.txt`

---

## 📊 Version Comparison

| Feature | v2.0 | v3.0 |
|---------|------|------|
| Max Levels | 10 | **50** |
| Save System | ❌ | **✅** |
| Window Size | 800x600 | **900x700** |
| Achievements | 4 | **7** |
| JAR Build | ❌ | **✅** |
| Documentation | Basic | **Complete** |
| Version Tracking | None | **CHANGELOG.md** |
| Build Guide | None | **BUILD.md** |

---

## 🚀 Upgrade Instructions

### From v2.0 to v3.0

1. **Backup** (optional):
   - No save files existed in v2.0
   - No backup needed

2. **Install v3.0:**
   ```bash
   git pull origin main
   cd TheEndTheBeginning
   mvn clean compile
   mvn javafx:run
   ```

3. **What's Different:**
   - Game now has 50 levels instead of 10
   - Your progress saves automatically
   - New achievements to unlock
   - Larger game window
   - Better performance

4. **Breaking Changes:**
   - None - fully backward compatible

---

## 🗺️ Roadmap

### Version 3.1 (Next Release)
- FXGL integration for enhanced graphics and animations
- Manual save slots (multiple saves)
- Boss monsters at levels 10, 25, 50
- More monster varieties for late game
- Game icon image file
- Sound effects
- Improved GUI components using FXGL framework

### Version 3.2
- Background music
- Tutorial system
- Settings menu
- Keyboard shortcuts
- Advanced particle effects

### Version 4.0 (Future)
- Multiplayer support
- Procedural dungeons
- Crafting system
- New character classes
- Full FXGL game engine integration

---

## 👥 Credits

**Development:** Abdul Fornah  
**Framework:** JavaFX 20  
**Language:** Java 17  
**Build Tool:** Apache Maven

---

## 📞 Support & Feedback

- **Issues:** [GitHub Issues](https://github.com/Apexone11/TheEndOfTheBeginning/issues)
- **Documentation:** See README.md, CHANGELOG.md, BUGS.md
- **Build Help:** See BUILD.md

---

## 🙏 Thank You

Thank you to everyone who played v2.0 and provided feedback. Your input helped shape this release!

**Enjoy the expanded adventure!** ⚔️🏰

---

*"May your dungeon runs be legendary!"*
