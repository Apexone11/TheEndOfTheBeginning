# The End The Beginning - Dungeon Escape Game v4.0.0

**The End The Beginning** is an immersive text-adventure dungeon escape game built with Java and JavaFX. Players embark on a perilous journey through a mysterious dungeon, battling monsters, collecting items, and progressing through **50 challenging levels** to achieve freedom.

**Current Version:** 4.0.0 (Advanced Combat Edition)  
**Developer:** Abdul Fornah  
**Framework:** Pure JavaFX 20 (Java 17+)

---

## What's New in v4.0.0

### Enhanced UI & UX
- **Improved Window Sizing** - Optimized 950x750 resolution with better component spacing
- **Updated Version Display** - Consistent v4.0.0 branding throughout the game
- **Better Text Area** - Increased display area (450px height) for improved readability
- **Refined Input Field** - Larger input area (600px) with helpful prompt text

### Keyboard Shortcuts
- **Ctrl+S** - Quick Save your progress anytime
- **Ctrl+L** - Quick Load your saved game
- **F1** - Show help and keyboard shortcuts
- **1-6 Keys** - Quick combat actions during battles
  - 1: Normal Attack
  - 2: Defend
  - 3: Heavy Attack (costs mana)
  - 4: Quick Attack
  - 5: Use Item
  - 6: Attempt to Run

### Audio System Framework
- **Copyright-Free Music Integration** - Complete framework for background music and sound effects
- **Audio Attribution Guide** - Comprehensive guide for adding copyright-free music
- **Ready for Audio Files** - Pre-configured directories and documentation for easy audio integration
- See [MUSIC_ATTRIBUTION.md](TheEndTheBeginning/MUSIC_ATTRIBUTION.md) for details

### Previous Features (v3.1.0)
- **Auto-Save & Quick-Load** - Progress auto-saved after key events
- **In-Game Settings Panel** - Configure text speed, high-contrast mode, and confirmations
- **Inventory Quick-Use** - Type `use <item>` to quickly use items during gameplay
- **Contextual Hints** - Helpful hints appear after 3 invalid inputs in the same state
- **Difficulty Preview** - See exact multipliers and bonuses before confirming difficulty

---

## Game Features

- **Epic 50-Level Dungeon** - Navigate through 50 increasingly challenging levels
- **Three Character Classes** - Warrior, Mage, or Rogue with unique abilities
- **Advanced Combat System** - Multiple attack types with strategic options
- **Automatic Saves** - Progress saved automatically
- **Keyboard Shortcuts** - Quick access to common actions
- **Dynamic Monster System** - Face diverse enemies with scaling difficulty
- **Inventory Management** - Collect and use items
- **Achievement System** - Unlock achievements for milestones
- **Audio-Ready Framework** - Add your own copyright-free music and sounds

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

#### Keyboard Shortcuts (NEW in v4.0.0!)
- **Ctrl+S** - Quick Save your progress
- **Ctrl+L** - Quick Load saved game
- **F1** - Show help and keyboard shortcuts guide
- **1-6** - Quick combat actions (during battle)
  - 1: Normal Attack
  - 2: Defend
  - 3: Heavy Attack
  - 4: Quick Attack
  - 5: Use Item
  - 6: Run Away

#### Game Commands
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

### Gameplay Tips
- **Press F1** anytime for help and keyboard shortcuts
- **Use Ctrl+S** frequently to save your progress
- Type `use potion` anytime to quickly use items
- Your progress auto-saves after rooms and combat
- Enable high-contrast mode in settings for better visibility
- Watch for contextual hints if you're stuck
- Number keys (1-6) provide quick combat actions during battles

### Saves & Config
- **Saves**: `~/.the-end-the-beginning/saves/`
- **Config**: `~/.the-end-the-beginning/config.properties`

---

## Adding Music & Sound Effects (Optional)

The game includes a complete audio framework ready for copyright-free music and sound effects!

### Quick Setup
1. Download copyright-free audio from sources listed in [MUSIC_ATTRIBUTION.md](TheEndTheBeginning/MUSIC_ATTRIBUTION.md)
2. Place audio files in the appropriate directories:
   - Music (MP3): `TheEndTheBeginning/src/main/resources/audio/music/`
   - Sound Effects (WAV): `TheEndTheBeginning/src/main/resources/audio/combat/`, `/ui/`, `/environment/`
3. See the complete guide in [MUSIC_ATTRIBUTION.md](TheEndTheBeginning/MUSIC_ATTRIBUTION.md)

### Recommended Copyright-Free Sources
- **FreePD** (https://freepd.com/) - Public Domain music
- **Incompetech** (https://incompetech.com/) - CC BY 4.0 licensed music
- **Freesound** (https://freesound.org/) - Sound effects with various CC licenses
- **OpenGameArt** (https://opengameart.org/) - Game audio resources

**Note**: The game works perfectly without audio files - they're optional enhancements!

---

## Documentation

- [CHANGELOG.md](CHANGELOG.md) - Version history and updates
- [RELEASE_NOTES.md](RELEASE_NOTES.md) - Detailed release information
- [TODO.md](TODO.md) - Tasks, bugs, and feature roadmap
- [MUSIC_ATTRIBUTION.md](TheEndTheBeginning/MUSIC_ATTRIBUTION.md) - Audio licensing and attribution guide

---

## Credits

**Developer:** Abdul Fornah  
**Framework:** JavaFX 20 with Java 17+

Thank you for playing!
