# The End The Beginning - Dungeon Escape Game 🏰Dungeon Escape (JavaFX)

📖 Overview

## 📖 Overview

Dungeon Escape is a text-adventure style game built in Java with a JavaFX GUI.

**The End The Beginning** is an immersive text-adventure dungeon escape game built with Java and JavaFX. Players embark on a perilous journey through a mysterious dungeon, battling monsters, collecting items, and progressing through 10 challenging levels to achieve freedom.Players explore, fight, and progress through levels while their stats are updated in real time.



This enhanced version features a complete character class system, inventory management, achievements, and polished JavaFX GUI interface.The MainController connects the UI (FXML) with the game logic, handling input and updating the display.



**Current Version:** 2.0 (Enhanced Edition)  🚀 Features

**Developer:** Abdul Fornah  

**Framework:** Java 17+ with JavaFX  Interactive Story/Game Loop with dynamic text updates.



---Player Stats Tracking: health, attack, defense, and level displayed live.



## 🎮 Game FeaturesCommands System: enter actions via text field (e.g., attack, explore).



### Core GameplayGame Controls:

- **🏰 Epic Dungeon Adventure** - Navigate through 10 increasingly challenging dungeon levels

- **⚔️ Character Classes** - Choose from Warrior, Mage, or Rogue, each with unique abilities and stat growthStart button → begins the adventure.

- **👹 Dynamic Monster System** - Face diverse enemies with special abilities and scaling difficulty

- **📦 Inventory Management** - Collect and use various items including weapons, armor, and potionsSubmit button → sends player’s command.

- **🏆 Achievement System** - Unlock achievements for exploration, combat, and progression milestones

Stats button → shows current stats.

### Character Progression

- **📈 Experience & Leveling** - Gain XP from combat and exploration to level up your characterReset button → restarts the game.

- **💪 Stat Growth** - Each class has different stat growth patterns (health, attack, defense)

- **🎯 Class-Specific Bonuses** - Warriors are tanky, Mages hit hard, Rogues have critical strikes🛠️ Requirements

- **📊 Detailed Statistics** - Track your progress with comprehensive stat displays

Java 17+ (recommended)

### Interactive Interface

- **🖥️ Modern JavaFX GUI** - Clean, intuitive interface with real-time stat updatesJavaFX SDK (matching your JDK version)

- **⌨️ Text-Based Commands** - Enter actions via text field for immersive gameplay

- **🎛️ Control Panel** - Easy access to game controls (Start, Stats, Reset)IDE with JavaFX support (IntelliJ, Eclipse, or VS Code)

- **📱 Responsive Design** - Scales well with different window sizes

🎮 Gameplay

---

Press Start to begin the adventure.

## 🚀 Getting Started

Type commands (e.g., attack, explore, run) into the input field and hit Submit.

### System Requirements

- **Java:** 17 or higher (JDK recommended)Track your health, attack, defense, and level on-screen.

- **JavaFX:** Matching your JDK version (often bundled with JDK)

- **IDE:** IntelliJ IDEA, Eclipse, or VS Code with Java extensionsPress Stats anytime to review progress.

- **OS:** Windows, macOS, or Linux

Use Reset to start fresh.

### Installation & Setup

1. **Clone the repository:**🧑‍💻 Developer Notes

   ```bash

   git clone https://github.com/[your-username]/TheEndOfTheBeginning-game.gitMain.java → The entry point that loads the FXML and launches the JavaFX application.

   cd TheEndOfTheBeginning-game/TheEndTheBeginning

   ```MainController.java → Core controller that handles:



2. **Import into your IDE:**User input (TextField)

   - Open the `TheEndTheBeginning` folder as a Maven project

   - Ensure JavaFX is properly configured in your IDEUpdating story text (TextArea)

   - Verify Java 17+ is selected as the project JDK

Managing buttons (Start, Submit, Stats, Reset)

3. **Run the application:**

   - Main class: `gameproject.TheEndTheBeginning`Syncing player stats with labels (healthLabel, attackLabel, etc.)

   - Or use your IDE's run configuration for JavaFX applications

GameState.java → Holds all the game data (player health, level, attack, defense, etc.). Acts as the "model" in an MVC-like structure.

### Quick Start

1. Click **"Start New Game"** to begin your adventureFXML → Defines the GUI layout (buttons, text areas, input fields, labels).

2. Choose your character class (Warrior, Mage, or Rogue)

3. Select difficulty level (Easy, Medium, Hard, or Death)CSS (optional) → Can be used to style the game window (themes, fonts, colors).

4. Enter your character name

5. Use text commands to navigate and interact with the game world📌 Tip for future dev work:



---Keep game logic inside GameState (or dedicated classes) instead of bloating the controller.



## 🎯 Gameplay GuideUse the controller mainly for event handling and updating the UI.



### Character ClassesIf the game grows, consider splitting into multiple controllers for different screens (menu, battle, etc.).

- **🛡️ WARRIOR** - High health and defense, reliable damage

  - *Starting Stats:* 120 HP, 15 ATK, 8 DEF🧩 Next Steps

  - *Bonus:* Consistent damage output, high survivability

Add more story events and encounters.

- **🧙 MAGE** - High attack power, learns quickly, fragile

  - *Starting Stats:* 80 HP, 25 ATK, 3 DEF  Expand combat system with items and enemies.

  - *Bonus:* +20% experience gain, critical hit chance

Implement save/load functionality.

- **🗡️ ROGUE** - Balanced stats, critical hit specialist

  - *Starting Stats:* 100 HP, 20 ATK, 5 DEFPolish UI with CSS themes

  - *Bonus:* +30% critical hit chance, balanced growth

### Game Commands
During gameplay, you'll make choices by entering numbers or text commands:
- **Combat:** Choose to attack, run, or use items
- **Exploration:** Search rooms, move forward, check stats, manage inventory
- **Inventory:** Use items during encounters or exploration

### Progression System
- **Levels 1-3:** Learn the basics, face weak enemies
- **Levels 4-6:** Encounter stronger monsters, better loot
- **Levels 7-9:** Elite enemies with special abilities
- **Level 10:** Final boss encounter and escape

---

## 🛠️ Technical Architecture

### Project Structure
```
src/main/java/
├── gameproject/
│   ├── TheEndTheBeginning.java    # Application entry point
│   ├── MainControllerNew.java      # JavaFX controller & game logic
│   └── Monster.java                # Simple monster class for combat
├── main/model/
│   ├── Player.java                 # Enhanced player system
│   ├── Item.java                   # Item management system
│   └── [Monster.java]              # Advanced monster system (future)
└── resources/
    ├── game.fxml                   # UI layout definition
    └── game-style.css              # UI styling
```

### Design Patterns
- **MVC Architecture** - Clear separation of Model, View, and Controller
- **Observer Pattern** - UI updates when player stats change
- **Strategy Pattern** - Different character classes with unique behaviors
- **Factory Pattern** - Dynamic item and monster generation

### Key Components
- **MainControllerNew** - Handles all UI events and game state management
- **Player Class** - Comprehensive player data with progression system
- **GameState** - Legacy compatibility layer for smooth transitions
- **Item System** - Various item types (healing, equipment, consumables)

---

## 🔄 Recent Updates (Version 2.0)

### ✅ New Features
- Complete character class system with unique progression
- Enhanced inventory management with item types and rarity
- Achievement tracking and milestone rewards
- Improved combat mechanics with status effects
- Polished UI with better visual feedback

### 🔧 Technical Improvements  
- Modernized codebase with Java 17+ features
- Better error handling and user feedback
- Optimized performance and memory usage
- Comprehensive documentation and code comments

### 🐛 Bug Fixes
- Fixed class naming inconsistencies
- Resolved import and dependency issues
- Corrected switch statement syntax warnings
- Eliminated unused variables and methods

---



## 🤝 Contributing

We welcome contributions from developers, testers, and players! Here's how you can help:

### For Developers
- **Bug fixes** - Check the issue tracker for reported bugs
- **Feature implementation** - Pick items from the TODO list
- **Code optimization** - Performance improvements and refactoring
- **Documentation** - Improve comments, guides, and examples

### For Testers
- **Bug reporting** - Play the game and report issues with reproduction steps
- **Balance feedback** - Suggest improvements to gameplay mechanics
- **UI/UX suggestions** - Help improve the user experience

### For Players
- **Feedback** - Share your gameplay experience and suggestions
- **Screenshots** - Show off your achievements and high scores
- **Reviews** - Help spread the word about the game

---

## 📞 Support & Contact

- **Issues:** Report bugs via GitHub Issues
- **Questions:** Check documentation or open a discussion
- **Suggestions:** Feature requests welcome in Issues or Discussions

---

## 📄 License & Credits

**Developer:** Abdul Fornah  
**Framework:** JavaFX  
**Language:** Java 17+  

*This project is for educational and entertainment purposes. Feel free to learn from, modify, and build upon this code.*

---

## 🎉 Acknowledgments

- **Java & JavaFX Communities** for excellent documentation and examples
- **UMBC Computer Science Program** for educational support and resources
- **Beta Testers** who provided valuable feedback during development

*Ready to escape the dungeon? Your adventure awaits...* ⚔️