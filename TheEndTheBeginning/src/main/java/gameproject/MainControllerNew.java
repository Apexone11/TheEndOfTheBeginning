package gameproject;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import main.model.Item;
import main.model.player;

/**
 * Enhanced MainController class - JavaFX Controller for "The End The Beginning" game.
 * 
 * This class serves as the primary game controller, managing the user interface
 * and game logic for the JavaFX version of the dungeon escape game. Now with
 * enhanced features including class selection, inventory system, and improved combat.
 * 
 * NEW FEATURES IN V2.0:
 * - Character class selection (Warrior, Mage, Rogue)
 * - Complete inventory system with item usage
 * - Enhanced monster encounters with proper Player integration
 * - Achievement tracking and progression system
 * - Beautiful UI styling with atmospheric text
 * - Improved combat mechanics with status effects
 * 
 * @author Abdul Fornah
 * @version 2.0 (Enhanced)
 */
public class MainControllerNew implements Initializable {
    
    // ===== JAVAFX UI COMPONENT REFERENCES =====
    @FXML private TextArea gameTextArea;    // Main game text display area
    @FXML private TextField inputField;     // Player text input field
    @FXML private Button submitButton;      // Submit player input
    @FXML private Button startButton;       // Start new game
    @FXML private Button statsButton;       // Display player statistics
    @FXML private Button resetButton;       // Reset/restart game
    @FXML private Label healthLabel;        // Player health display
    @FXML private Label defenseLabel;       // Player defense display
    @FXML private Label attackLabel;        // Player attack display
    @FXML private Label levelLabel;         // Player level display
    
    // ===== GAME STATE MANAGEMENT =====
    private player player;                  // Enhanced player system
    private GameState gameState;            // Legacy compatibility system
    private boolean isGameRunning = false;
    private boolean waitingForInput = false;
    private String expectedInputType = "";
    private Monster currentMonster;
    private int monsterHealth = 0;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize enhanced player system
        player = new player();
        
        // Initialize legacy game state for compatibility
        gameState = new GameState();
        
        displayWelcomeMessage();
        
        // Allow Enter key to submit input
        inputField.setOnAction(event -> handleSubmit());
        
        // Initialize UI
        updateUI();
    }
    
    private void displayWelcomeMessage() {
        appendToGameText("═══════════════════════════════════════════════════════════\n");
        appendToGameText("    ⚔️  THE END THE BEGINNING - DUNGEON ESCAPE  ⚔️\n");
        appendToGameText("═══════════════════════════════════════════════════════════\n\n");
        
        appendToGameText("Welcome, brave soul, to the depths of mystery and danger...\n\n");
        
        appendToGameText("🏰 You find yourself at the entrance of an ancient dungeon\n");
        appendToGameText("💀 Legends speak of treasures and terrors within\n");
        appendToGameText("🗝️ Only the cunning and strong may find their way to freedom\n\n");
        
        appendToGameText("═══ FEATURES ═══\n");
        appendToGameText("• Choose your character class (Warrior, Mage, Rogue)\n");
        appendToGameText("• Collect powerful items and equipment\n");
        appendToGameText("• Face challenging monsters with unique abilities\n");
        appendToGameText("• Progress through 10 levels to escape\n");
        appendToGameText("• Unlock achievements and track your progress\n\n");
        
        appendToGameText("🎮 Click 'Start New Game' when you're ready to begin!\n");
        appendToGameText("📋 Use 'View Stats' anytime to check your progress\n\n");
    }
    
    @FXML
    public void handleStart() {
        if (!isGameRunning) {
            startNewGame();
        }
    }
    
    @FXML
    public void handleSubmit() {
        String input = inputField.getText().trim();
        if (!input.isEmpty() && waitingForInput) {
            processInput(input);
            inputField.clear();
        }
    }
    
    @FXML
    public void handleStats() {
        if (isGameRunning && player != null) {
            appendToGameText("\n" + player.getDetailedStats() + "\n");
        } else {
            appendToGameText("\n═══ Basic Stats ═══\n");
            appendToGameText("Health: " + gameState.getHealth() + "\n");
            appendToGameText("Defense: " + gameState.getDefense() + "\n");
            appendToGameText("Attack: " + gameState.getAttack() + "\n");
            appendToGameText("Level: " + gameState.getLevel() + "\n\n");
        }
    }
    
    @FXML
    public void handleReset() {
        resetGame();
    }
    
    private void startNewGame() {
        player = new player();
        gameState.resetGame();
        isGameRunning = true;
        gameTextArea.clear();
        
        appendToGameText("═══ BEGINNING YOUR ADVENTURE ═══\n\n");
        appendToGameText("🏰 The ancient dungeon looms before you...\n");
        appendToGameText("🕰️ Your fate awaits within these cursed halls.\n\n");
        
        appendToGameText("🤔 Do you dare to enter the depths? (YES/NO): ");
        waitingForInput = true;
        expectedInputType = "START_CONFIRMATION";
        updateUI();
    }
    
    private void processInput(String input) {
        waitingForInput = false;
        
        switch (expectedInputType) {
            case "START_CONFIRMATION" -> {
                if (input.equalsIgnoreCase("YES") || input.equalsIgnoreCase("Y")) {
                    askForPlayerClass();
                } else if (input.equalsIgnoreCase("NO") || input.equalsIgnoreCase("N")) {
                    showCredits();
                    isGameRunning = false;
                } else {
                    appendToGameText("Please enter YES or NO: ");
                    waitingForInput = true;
                }
            }
            case "CLASS_SELECTION" -> handleClassSelection(input);
            case "DIFFICULTY" -> handleDifficultySelection(input);
            case "PLAYER_NAME" -> {
                player.setName(input);
                startGameplay();
            }
            case "ROOM_ACTION" -> handleRoomAction(input);
            case "MONSTER_ACTION" -> handleMonsterAction(input);
            case "COMBAT_ACTION" -> handleCombatAction(input);
            case "INVENTORY_ACTION" -> handleInventoryAction(input);
        }
    }
    
    private void askForPlayerClass() {
        appendToGameText("\n⚔️ Choose your adventurer class:\n\n");
        appendToGameText("🛡️  1: WARRIOR - High health and defense, steady damage\n");
        appendToGameText("   → Starting Stats: 120 HP, 15 ATK, 8 DEF\n\n");
        
        appendToGameText("🧙  2: MAGE - High attack power, learns quickly, fragile\n");
        appendToGameText("   → Starting Stats: 80 HP, 25 ATK, 3 DEF (+20% EXP)\n\n");
        
        appendToGameText("😏  3: ROGUE - Balanced stats, critical hit specialist\n");
        appendToGameText("   → Starting Stats: 100 HP, 20 ATK, 5 DEF (+30% Crit)\n\n");
        
        appendToGameText("🎯 Enter 1, 2, or 3: ");
        waitingForInput = true;
        expectedInputType = "CLASS_SELECTION";
    }
    
    private void handleClassSelection(String input) {
        try {
            int classChoice = Integer.parseInt(input);
            main.model.player.PlayerClass chosenClass;
            
            switch (classChoice) {
                case 1 -> {
                    chosenClass = main.model.player.PlayerClass.WARRIOR;
                    appendToGameText("\n🛡️ You have chosen the path of the WARRIOR!\n");
                    appendToGameText("Strong and resilient, you face danger with unwavering courage.\n");
                }
                case 2 -> {
                    chosenClass = main.model.player.PlayerClass.MAGE;
                    appendToGameText("\n🧙 You have chosen the path of the MAGE!\n");
                    appendToGameText("Wielding arcane power, you bend reality to your will.\n");
                }
                case 3 -> {
                    chosenClass = main.model.player.PlayerClass.ROGUE;
                    appendToGameText("\n😏 You have chosen the path of the ROGUE!\n");
                    appendToGameText("Swift and cunning, you strike from the shadows.\n");
                }
                default -> {
                    appendToGameText("Please enter 1, 2, or 3: ");
                    waitingForInput = true;
                    return;
                }
            }
            
            player.setPlayerClass(chosenClass);
            askForDifficulty();
            
        } catch (NumberFormatException e) {
            appendToGameText("Please enter a valid number (1-3): ");
            waitingForInput = true;
        }
    }
    
    private void askForDifficulty() {
        appendToGameText("\n⚙️ Choose your difficulty level:\n\n");
        appendToGameText("👍 1: EASY - Gentle introduction, more health and items\n");
        appendToGameText("⚖️ 2: MEDIUM - Balanced challenge for most players\n");
        appendToGameText("🔥 3: HARD - Tough encounters, limited resources\n");
        appendToGameText("☠️ 4: DEATH - Only for the truly brave... or foolish\n\n");
        appendToGameText("🎯 Enter 1-4: ");
        waitingForInput = true;
        expectedInputType = "DIFFICULTY";
    }
    
    private void handleDifficultySelection(String input) {
        try {
            int difficulty = Integer.parseInt(input);
            if (difficulty >= 1 && difficulty <= 4) {
                appendToGameText("\n⚙️ Difficulty set!\n");
                askForPlayerName();
            } else {
                appendToGameText("Please enter a number between 1-4: ");
                waitingForInput = true;
            }
        } catch (NumberFormatException e) {
            appendToGameText("Please enter a valid number (1-4): ");
            waitingForInput = true;
        }
    }
    
    private void askForPlayerName() {
        appendToGameText("\n📝 What name shall the bards sing of your deeds? ");
        waitingForInput = true;
        expectedInputType = "PLAYER_NAME";
    }
    
    private void startGameplay() {
        appendToGameText("\n🌙 You wake up in a cold, dark dungeon. The air tastes of iron and old candles.\n");
        appendToGameText("💪 Hope you survive this ordeal, " + player.getName() + ". May fortune favor you.\n\n");
        
        // Sync player stats to legacy system for compatibility
        syncPlayerToGameState();
        
        displayPlayerStats();
        continueGameplay();
    }
    
    private void syncPlayerToGameState() {
        gameState.setHealth(player.getHealth());
        gameState.setAttack(player.getAttack());
        gameState.setDefense(player.getDefense());
        gameState.setLevel(player.getDungeonLevel());
    }
    
    private void continueGameplay() {
        if (gameState.getLevel() >= 10) {
            appendToGameText("\n🎉 VICTORY! You have escaped the dungeon!\n");
            appendToGameText("🏆 Congratulations, " + player.getName() + "! You are truly a hero!\n\n");
            showCredits();
            isGameRunning = false;
            return;
        }
        
        if (player.getHealth() <= 0) {
            appendToGameText("\n💀 GAME OVER! You have fallen in the depths.\n");
            appendToGameText("⚰️ Your adventure ends here, " + player.getName() + "...\n\n");
            showCredits();
            isGameRunning = false;
            return;
        }
        
        appendToGameText("\n📍 Floor " + gameState.getLevel() + " :\n");
        appendToGameText("🚪 Room " + gameState.getRoom() + " :\n\n");
        
        int gameEvent = (int) (Math.random() * 4);
        
        if (gameEvent != 3) {
            appendToGameText("🤔 What do you want to do, " + player.getName() + "?\n");
            appendToGameText(" 1: Search the room\n 2: Move to next room\n 3: Check stats\n 4: View inventory\n");
            appendToGameText("Enter 1-4: ");
            waitingForInput = true;
            expectedInputType = "ROOM_ACTION";
            gameState.setCurrentEvent(gameEvent);
        } else {
            encounterMonster();
        }
    }
    
    private void handleRoomAction(String input) {
        try {
            int choice = Integer.parseInt(input);
            switch (choice) {
                case 1 -> {
                    if (gameState.getRoomSearches() < 3) {
                        appendToGameText("🔍 You search the room carefully...\n");
                        gameState.incrementRoomSearches();
                        gameState.incrementRoom();
                        player.recordRoomExplored();
                        handleRoomEvent();
                    } else {
                        appendToGameText("👀 You've searched enough. Better move on.\n");
                        gameState.nextLevel();
                        player.setDungeonLevel(gameState.getLevel());
                        handleRoomEvent();
                    }
                }
                case 2 -> {
                    appendToGameText("🚶 You move to the next room.\n");
                    gameState.nextLevel();
                    player.setDungeonLevel(gameState.getLevel());
                    handleRoomEvent();
                }
                case 3 -> {
                    handleStats();
                    continueGameplay();
                }
                case 4 -> showInventory();
                default -> {
                    appendToGameText("Please enter 1, 2, 3, or 4: ");
                    waitingForInput = true;
                }
            }
        } catch (NumberFormatException e) {
            appendToGameText("Please enter a valid number (1-4): ");
            waitingForInput = true;
        }
    }
    
    private void handleRoomEvent() {
        int event = gameState.getCurrentEvent();
        
        switch (event) {
            case 0 -> {
                // Find item
                Item foundItem = Item.generateRandomItem(gameState.getLevel());
                if (player.addItem(foundItem)) {
                    appendToGameText("✨ You found " + foundItem.getDisplayName() + "!\n");
                    appendToGameText("📦 " + foundItem.getDescription() + "\n");
                } else {
                    appendToGameText("💼 Your inventory is full! You couldn't pick up " + foundItem.getName() + ".\n");
                }
            }
            case 1 -> {
                // Health event
                int healthGain = 10 + (int) (Math.random() * 20);
                int actualHealing = player.heal(healthGain);
                appendToGameText("❤️ You found a health spring! Restored " + actualHealing + " health.\n");
            }
            case 2 -> {
                // Experience event
                int expGain = 20 + gameState.getLevel() * 5;
                boolean leveledUp = player.gainExperience(expGain);
                appendToGameText("⭐ You found ancient runes! Gained " + expGain + " experience.\n");
                if (leveledUp) {
                    appendToGameText("🎉 LEVEL UP! You feel stronger!\n");
                }
            }
        }
        
        syncPlayerToGameState();
        updateUI();
        continueGameplay();
    }
    
    private void encounterMonster() {
        // Create a monster based on current level
        String[] monsterNames = {"Goblin", "Orc", "Cave Lurker", "Ghoul", "Shadow Beast"};
        int index = Math.min(monsterNames.length - 1, gameState.getLevel() / 2);
        String monsterName = monsterNames[index];
        
        int baseHealth = 30 + (gameState.getLevel() * 10);
        int baseAttack = 5 + (gameState.getLevel() * 3);
        
        currentMonster = new Monster(monsterName, baseHealth, baseAttack, "Slash");
        monsterHealth = currentMonster.getHealth();
        
        appendToGameText("⚠️ A " + monsterName + " appears!\n");
        appendToGameText("👹 Health: " + monsterHealth + " | Attack: " + currentMonster.getAttack() + "\n\n");
        
        appendToGameText("🤺 What will you do?\n");
        appendToGameText(" 1: Attack\n 2: Try to run\n 3: Use item\n");
        appendToGameText("Choose 1-3: ");
        waitingForInput = true;
        expectedInputType = "MONSTER_ACTION";
    }
    
    private void handleMonsterAction(String input) {
        try {
            int choice = Integer.parseInt(input);
            switch (choice) {
                case 1 -> startCombat();
                case 2 -> {
                    if (Math.random() < 0.6) {
                        appendToGameText("💨 You managed to escape to the next room!\n");
                        gameState.nextLevel();
                        player.setDungeonLevel(gameState.getLevel());
                        continueGameplay();
                    } else {
                        appendToGameText("❌ You failed to escape! The monster attacks!\n");
                        int damage = currentMonster.calculateDamage() / 2;
                        int actualDamage = player.takeDamage(damage);
                        appendToGameText("💔 You lost " + actualDamage + " health!\n");
                        syncPlayerToGameState();
                        updateUI();
                        continueGameplay();
                    }
                }
                case 3 -> {
                    if (player.getInventory().isEmpty()) {
                        appendToGameText("🎒 Your inventory is empty! Choose another action.\n");
                        handleMonsterAction("0"); // Invalid choice to re-prompt
                    } else {
                        showInventory();
                    }
                }
                default -> {
                    appendToGameText("Please enter 1, 2, or 3: ");
                    waitingForInput = true;
                }
            }
        } catch (NumberFormatException e) {
            appendToGameText("Please enter a valid number (1-3): ");
            waitingForInput = true;
        }
    }
    
    private void startCombat() {
        appendToGameText("⚔️ Combat begins!\n\n");
        executeCombatRound();
    }
    
    private void executeCombatRound() {
        if (!currentMonster.isAlive()) {
            appendToGameText("🏆 Victory! The " + currentMonster.getName() + " has been defeated!\n");
            
            // Reward experience and potential level up
            int expReward = 30 + (gameState.getLevel() * 10);
            boolean leveledUp = player.gainExperience(expReward);
            appendToGameText("⭐ You gained " + expReward + " experience!\n");
            
            if (leveledUp) {
                appendToGameText("🎉 LEVEL UP! You grow stronger!\n");
            }
            
            // Record kill for achievements
            player.recordMonsterKill();
            
            // Advance to next level
            gameState.nextLevel();
            player.setDungeonLevel(gameState.getLevel());
            
            syncPlayerToGameState();
            updateUI();
            continueGameplay();
            return;
        }
        
        if (!player.isAlive()) {
            appendToGameText("💀 You have been defeated...\n");
            showCredits();
            isGameRunning = false;
            return;
        }
        
        // Player attacks first
        int playerDamage = player.calculateDamage();
        currentMonster.takeDamage(playerDamage);
        appendToGameText("⚔️ You deal " + playerDamage + " damage! Monster health: " + currentMonster.getHealth() + "\n");
        
        // Check if monster is defeated
        if (!currentMonster.isAlive()) {
            executeCombatRound(); // Re-run to handle victory
            return;
        }
        
        // Monster attacks back
        int monsterDamage = currentMonster.calculateDamage();
        int actualDamage = player.takeDamage(monsterDamage);
        appendToGameText("💢 The " + currentMonster.getName() + " deals " + actualDamage + " damage! Your health: " + player.getHealth() + "\n");
        
        syncPlayerToGameState();
        updateUI();
        
        // Check if player is defeated
        if (!player.isAlive()) {
            executeCombatRound(); // Re-run to handle defeat
            return;
        }
        
        // Continue combat
        appendToGameText("\n🤺 Continue fighting? 1: Yes  2: Try to escape\n");
        appendToGameText("Choose 1-2: ");
        waitingForInput = true;
        expectedInputType = "COMBAT_ACTION";
    }
    
    private void handleCombatAction(String input) {
        try {
            int choice = Integer.parseInt(input);
            switch (choice) {
                case 1 -> executeCombatRound();
                case 2 -> {
                    if (Math.random() < 0.4) {
                        appendToGameText("💨 You successfully escaped from combat!\n");
                        continueGameplay();
                    } else {
                        appendToGameText("❌ Escape failed! Combat continues!\n");
                        executeCombatRound();
                    }
                }
                default -> {
                    appendToGameText("Please enter 1 or 2: ");
                    waitingForInput = true;
                }
            }
        } catch (NumberFormatException e) {
            appendToGameText("Please enter a valid number (1-2): ");
            waitingForInput = true;
        }
    }
    
    private void handleInventoryAction(String input) {
        try {
            if (input.equalsIgnoreCase("back") || input.equalsIgnoreCase("exit")) {
                if (currentMonster != null && currentMonster.isAlive()) {
                    // Return to monster encounter
                    appendToGameText("\n🤺 Back to the encounter!\n");
                    appendToGameText(" 1: Attack\n 2: Try to run\n 3: Use item\n");
                    appendToGameText("Choose 1-3: ");
                    waitingForInput = true;
                    expectedInputType = "MONSTER_ACTION";
                } else {
                    continueGameplay();
                }
                return;
            }
            
            int itemIndex = Integer.parseInt(input) - 1;
            List<Item> inventory = player.getInventory();
            
            if (itemIndex < 0 || itemIndex >= inventory.size()) {
                appendToGameText("Invalid item number. Try again or type 'back': ");
                waitingForInput = true;
                return;
            }
            
            Item selectedItem = inventory.get(itemIndex);
            appendToGameText("\n📦 Using " + selectedItem.getName() + "...\n");
            if (player.useItem(selectedItem.getName())) {
                appendToGameText("✅ Item used successfully!\n");
            } else {
                appendToGameText("❌ Could not use item.\n");
            }
            
            syncPlayerToGameState();
            updateUI();
            
            // Continue based on context
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            if (currentMonster != null && currentMonster.isAlive()) {
                pause.setOnFinished(e -> {
                    appendToGameText("\n🤺 Back to the encounter!\n");
                    appendToGameText(" 1: Attack\n 2: Try to run\n 3: Use item\n");
                    appendToGameText("Choose 1-3: ");
                    waitingForInput = true;
                    expectedInputType = "MONSTER_ACTION";
                });
            } else {
                pause.setOnFinished(e -> continueGameplay());
            }
            pause.play();
            
        } catch (NumberFormatException e) {
            appendToGameText("Please enter a valid item number or 'back': ");
            waitingForInput = true;
        }
    }
    
    private void showInventory() {
        appendToGameText("\n" + player.getInventoryString() + "\n");
        
        if (!player.getInventory().isEmpty()) {
            appendToGameText("\n🎯 Enter item number to use, or 'back' to return: ");
            waitingForInput = true;
            expectedInputType = "INVENTORY_ACTION";
        } else {
            // Empty inventory, return to appropriate context
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            if (currentMonster != null && currentMonster.isAlive()) {
                pause.setOnFinished(e -> {
                    appendToGameText("\n🤺 Back to the encounter!\n");
                    appendToGameText(" 1: Attack\n 2: Try to run\n 3: Use item\n");
                    appendToGameText("Choose 1-3: ");
                    waitingForInput = true;
                    expectedInputType = "MONSTER_ACTION";
                });
            } else {
                pause.setOnFinished(e -> continueGameplay());
            }
            pause.play();
        }
    }
    
    private void displayPlayerStats() {
        appendToGameText("\n═══ Player Stats ═══\n");
        appendToGameText("❤️ Health: " + player.getHealth() + "/" + player.getMaxHealth() + "\n");
        appendToGameText("🛡️ Defense: " + player.getDefense() + "\n");
        appendToGameText("⚔️ Attack: " + player.getAttack() + "\n");
        appendToGameText("📈 Level: " + player.getLevel() + "\n");
        appendToGameText("⭐ Experience: " + player.getExperience() + "/" + player.getExperienceToNext() + "\n\n");
    }
    
    private void showCredits() {
        appendToGameText("\n═══ CREDITS ═══\n");
        appendToGameText("🎮 Game Developer: Abdul Fornah\n");
        appendToGameText("🛠️ Built with: Java + JavaFX\n");
        appendToGameText("🎨 Enhanced UI & Complete RPG System\n");
        appendToGameText("🏆 Thank you for playing!\n\n");
        
        if (!player.getAchievements().isEmpty()) {
            appendToGameText("🏅 Your Achievements:\n");
            for (String achievement : player.getAchievements()) {
                appendToGameText("   ★ " + achievement + "\n");
            }
            appendToGameText("\n");
        }
    }
    
    private void resetGame() {
        player = new player();
        gameState.resetGame();
        isGameRunning = false;
        waitingForInput = false;
        expectedInputType = "";
        currentMonster = null;
        monsterHealth = 0;
        gameTextArea.clear();
        displayWelcomeMessage();
        updateUI();
    }
    
    private void updateUI() {
        if (player != null) {
            healthLabel.setText("❤ Health: " + player.getHealth());
            defenseLabel.setText("🛡 Defense: " + player.getDefense());
            attackLabel.setText("⚔ Attack: " + player.getAttack());
            levelLabel.setText("📈 Level: " + player.getLevel());
            
            // Add visual feedback for low health
            if (player.getHealth() <= player.getMaxHealth() * 0.25) {
                healthLabel.getStyleClass().add("low-health");
            } else {
                healthLabel.getStyleClass().remove("low-health");
            }
        } else {
            healthLabel.setText("❤ Health: " + gameState.getHealth());
            defenseLabel.setText("🛡 Defense: " + gameState.getDefense());
            attackLabel.setText("⚔ Attack: " + gameState.getAttack());
            levelLabel.setText("📈 Level: " + gameState.getLevel());
        }
    }
    
    private void appendToGameText(String text) {
        gameTextArea.appendText(text);
    }
    
    // ===== LEGACY GAMESTATE CLASS FOR COMPATIBILITY =====
    private static class GameState {
        private int health = 100;
        private int defense = 1;
        private int attack = 1;
        private int level = 1;
        private int room = 0;
        private int roomSearches = 0;
        private int currentEvent = 0;
        
        public void resetGame() {
            health = 100;
            defense = 1;
            attack = 1;
            level = 1;
            room = 0;
            roomSearches = 0;
            currentEvent = 0;
        }
        
        public void nextLevel() {
            level++;
            room = 0;
            roomSearches = 0;
        }
        
        public void incrementRoom() { room++; }
        public void incrementRoomSearches() { roomSearches++; }
        
        // Getters and setters
        public int getHealth() { return health; }
        public void setHealth(int health) { this.health = health; }
        public int getDefense() { return defense; }
        public void setDefense(int defense) { this.defense = defense; }
        public int getAttack() { return attack; }
        public void setAttack(int attack) { this.attack = attack; }
        public int getLevel() { return level; }
        public void setLevel(int level) { this.level = level; }
        public int getRoom() { return room; }
        public int getRoomSearches() { return roomSearches; }
        public int getCurrentEvent() { return currentEvent; }
        public void setCurrentEvent(int event) { this.currentEvent = event; }
    }
}