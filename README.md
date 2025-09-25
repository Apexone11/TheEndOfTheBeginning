Dungeon Escape (JavaFX)
📖 Overview

Dungeon Escape is a text-adventure style game built in Java with a JavaFX GUI.
Players explore, fight, and progress through levels while their stats are updated in real time.

The MainController connects the UI (FXML) with the game logic, handling input and updating the display.

🚀 Features

Interactive Story/Game Loop with dynamic text updates.

Player Stats Tracking: health, attack, defense, and level displayed live.

Commands System: enter actions via text field (e.g., attack, explore).

Game Controls:

Start button → begins the adventure.

Submit button → sends player’s command.

Stats button → shows current stats.

Reset button → restarts the game.

🛠️ Requirements

Java 17+ (recommended)

JavaFX SDK (matching your JDK version)

IDE with JavaFX support (IntelliJ, Eclipse, or VS Code)

🎮 Gameplay

Press Start to begin the adventure.

Type commands (e.g., attack, explore, run) into the input field and hit Submit.

Track your health, attack, defense, and level on-screen.

Press Stats anytime to review progress.

Use Reset to start fresh.

🧑‍💻 Developer Notes

Main.java → The entry point that loads the FXML and launches the JavaFX application.

MainController.java → Core controller that handles:

User input (TextField)

Updating story text (TextArea)

Managing buttons (Start, Submit, Stats, Reset)

Syncing player stats with labels (healthLabel, attackLabel, etc.)

GameState.java → Holds all the game data (player health, level, attack, defense, etc.). Acts as the "model" in an MVC-like structure.

FXML → Defines the GUI layout (buttons, text areas, input fields, labels).

CSS (optional) → Can be used to style the game window (themes, fonts, colors).

📌 Tip for future dev work:

Keep game logic inside GameState (or dedicated classes) instead of bloating the controller.

Use the controller mainly for event handling and updating the UI.

If the game grows, consider splitting into multiple controllers for different screens (menu, battle, etc.).

🧩 Next Steps

Add more story events and encounters.

Expand combat system with items and enemies.

Implement save/load functionality.

Polish UI with CSS themes
