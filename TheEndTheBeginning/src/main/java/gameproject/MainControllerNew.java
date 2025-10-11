package gameproject;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import main.model.Item;
import main.model.Player;

// NEW V4.0.0 IMPORTS - Advanced Systems
import gameproject.combat.CombatEngine;
import gameproject.audio.AudioManager;
import gameproject.achievements.AchievementManager;
import gameproject.achievements.Achievement;
import gameproject.achievements.AchievementListener;

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
 * @version 3.1.0 (Text/UI Overhaul)
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
    @FXML private javafx.scene.layout.HBox achievementNotificationArea; // Achievement notification display
    
    // ===== GAME STATE MANAGEMENT =====
    private Player player;                  // Enhanced player system
    private GameState gameState;            // Legacy compatibility system
    private boolean isGameRunning = false;
    private boolean waitingForInput = false;
    private String expectedInputType = "";
    private Monster currentMonster;
    private int monsterHealth = 0;
    
    // ===== V3.1.0 FEATURES =====
    private Settings settings;              // Game settings
    private int invalidInputCount = 0;      // For contextual hints
    private String lastGameState = "";      // Track state for hints
    private String difficulty = "NORMAL";   // Current difficulty
    
    // ===== V4.0.0 ADVANCED SYSTEMS =====
    private CombatEngine combatEngine;      // Advanced combat system
    private AudioManager audioManager;     // Audio management system
    private AchievementManager achievementManager; // Achievement tracking system
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize enhanced player system
        player = new Player();
        
        // Initialize legacy game state for compatibility
        gameState = new GameState();
        
        // Load settings
        settings = Settings.load();
        applySettings();
        
        // ===== V4.0.0 INITIALIZE ADVANCED SYSTEMS =====
        // Initialize combat engine
        combatEngine = new CombatEngine();
        
        // Initialize audio manager
        audioManager = AudioManager.getInstance();
        audioManager.setGameStateMusic("menu");
        
        // Initialize achievement system with listener for UI notifications
        achievementManager = AchievementManager.getInstance();
        achievementManager.addAchievementListener(new AchievementListener() {
            @Override
            public void onAchievementUnlocked(Achievement achievement) {
                showAchievementNotification(achievement);
                audioManager.playUISound("achievement");
            }
        });
        
        displayWelcomeMessage();
        
        // Allow Enter key to submit input
        inputField.setOnAction(event -> handleSubmit());
        
        // Initialize UI - sync player stats to gameState before updating display
        syncPlayerToGameState();
        updateUI();
        
        // Keep input focused
        Platform.runLater(() -> inputField.requestFocus());
    }
    
    /**
     * Applies current settings to the game UI.
     */
    private void applySettings() {
        if (settings.highContrast) {
            try {
                gameTextArea.getScene().getStylesheets().clear();
                gameTextArea.getScene().getStylesheets().add(
                    getClass().getResource("/high-contrast.css").toExternalForm());
            } catch (Exception e) {
                System.err.println("Error loading high-contrast theme: " + e.getMessage());
            }
        } else {
            try {
                gameTextArea.getScene().getStylesheets().clear();
                gameTextArea.getScene().getStylesheets().add(
                    getClass().getResource("/game-style.css").toExternalForm());
            } catch (Exception e) {
                // Normal stylesheet might not exist yet, that's ok
            }
        }
    }
    
    private void displayWelcomeMessage() {
        appendToGameText("═══════════════════════════════════════════════════════════\n");
        appendToGameText("    ⚔️  THE END THE BEGINNING - DUNGEON ESCAPE v3.0  ⚔️\n");
        appendToGameText("═══════════════════════════════════════════════════════════\n\n");
        
        appendToGameText("Welcome, brave soul, to the depths of mystery and danger...\n\n");
        
        appendToGameText("🏰 You find yourself at the entrance of an ancient dungeon\n");
        appendToGameText("💀 Legends speak of treasures and terrors within\n");
        appendToGameText("🗝️ Only the cunning and strong may find their way to freedom\n\n");
        
        appendToGameText("═══ FEATURES ═══\n");
        appendToGameText("• Choose your character class (Warrior, Mage, Rogue)\n");
        appendToGameText("• Collect powerful items and equipment\n");
        appendToGameText("• Face challenging monsters with unique abilities\n");
        appendToGameText("• Progress through 50 levels to escape\n");
        appendToGameText("• Save and load your progress\n");
        appendToGameText("• Unlock achievements and track your progress\n\n");
        
        appendToGameText("🎮 Click 'Start New Game' when you're ready to begin!\n");
        appendToGameText("📋 Use 'View Stats' anytime to check your progress\n");
        appendToGameText("💾 Your progress is automatically saved!\n\n");
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
    
    @FXML
    public void dismissAchievement() {
        // Hide the achievement notification area
        if (achievementNotificationArea != null) {
            achievementNotificationArea.setVisible(false);
            achievementNotificationArea.setManaged(false);
        }
        
        // Play dismissal sound
        audioManager.playUISound("button");
        
        // Return focus to input field
        Platform.runLater(() -> inputField.requestFocus());
    }
    
    @FXML
    public void performNormalAttack() {
        if (currentMonster != null && currentMonster.isAlive()) {
            processCombatAction("1"); // Normal attack
        }
    }
    
    @FXML
    public void performDefend() {
        if (currentMonster != null && currentMonster.isAlive()) {
            processCombatAction("2"); // Defend
        }
    }
    
    @FXML
    public void performHeavyAttack() {
        if (currentMonster != null && currentMonster.isAlive()) {
            processCombatAction("3"); // Heavy attack
        }
    }
    
    @FXML
    public void performQuickAttack() {
        if (currentMonster != null && currentMonster.isAlive()) {
            processCombatAction("4"); // Quick attack
        }
    }
    
    @FXML
    public void useItemInCombat() {
        if (currentMonster != null && currentMonster.isAlive()) {
            processCombatAction("5"); // Use item
        }
    }
    
    @FXML
    public void attemptToRun() {
        if (currentMonster != null && currentMonster.isAlive()) {
            processCombatAction("6"); // Run away
        }
    }
    
    @FXML
    public void showAchievements() {
        if (player != null) {
            appendToGameText("\n" + achievementManager.getAchievementSummary() + "\n");
        } else {
            appendToGameText("\n🏆 No achievements yet. Start playing to unlock achievements!\n");
        }
    }
    
    private void startNewGame() {
        player = new Player();
        gameState.resetGame();
        isGameRunning = true;
        gameTextArea.clear();
        
        appendToGameText("═══ BEGINNING YOUR ADVENTURE ═══\n\n");
        
        // Check for existing save
        if (SaveManager.saveExists()) {
            appendToGameText("💾 A saved game was found!\n");
            appendToGameText("   Do you want to LOAD it or start a NEW game?\n");
            appendToGameText("   Type LOAD or NEW: ");
            waitingForInput = true;
            expectedInputType = "LOAD_OR_NEW";
            return;
        }
        
        appendToGameText("🏰 The ancient dungeon looms before you...\n");
        appendToGameText("🕰️ Your fate awaits within these cursed halls.\n\n");
        
        appendToGameText("🤔 Do you dare to enter the depths? (YES/NO): ");
        waitingForInput = true;
        expectedInputType = "START_CONFIRMATION";
        syncPlayerToGameState();
        updateUI();
    }
    
    private void processInput(String input) {
        waitingForInput = false;
        
        // Check for quick-use command (Feature 3)
        String normalizedInput = InputUtil.norm(input);
        if (normalizedInput.startsWith("USE ") && isGameRunning) {
            String itemName = normalizedInput.substring(4).trim();
            handleQuickUse(itemName);
            return;
        }
        
        switch (expectedInputType) {
            case "LOAD_OR_NEW" -> {
                if (input.equalsIgnoreCase("LOAD") || input.equalsIgnoreCase("L")) {
                    loadSavedGame();
                } else if (input.equalsIgnoreCase("NEW") || input.equalsIgnoreCase("N")) {
                    appendToGameText("\n🗑️ Starting fresh adventure (old save will be overwritten)...\n\n");
                    appendToGameText("🏰 The ancient dungeon looms before you...\n");
                    appendToGameText("🕰️ Your fate awaits within these cursed halls.\n\n");
                    appendToGameText("🤔 Do you dare to enter the depths? (YES/NO): ");
                    waitingForInput = true;
                    expectedInputType = "START_CONFIRMATION";
                } else {
                    appendToGameText("Please enter LOAD or NEW: ");
                    waitingForInput = true;
                }
            }
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
                // Validate name is not empty
                String normalizedName = input.trim();
                if (normalizedName.isEmpty()) {
                    appendToGameText("\n❌ Your name cannot be empty! Please enter a valid name: ");
                    waitingForInput = true;
                    expectedInputType = "PLAYER_NAME";
                } else {
                    player.setName(normalizedName);
                    startGameplay();
                }
            }
            case "ROOM_ACTION" -> {
                handleRoomAction(input);
                resetInvalidInputCount();
            }
            case "MONSTER_ACTION" -> {
                handleMonsterAction(input);
                resetInvalidInputCount();
            }
            case "COMBAT_ACTION" -> {
                handleCombatAction(input);
                resetInvalidInputCount();
            }
            case "INVENTORY_ACTION" -> {
                handleInventoryAction(input);
                resetInvalidInputCount();
            }
        }
    }
    
    /**
     * Tracks invalid inputs for contextual hint system (Feature 4 - v3.1.0).
     */
    private void trackInvalidInput(String context) {
        if (!lastGameState.equals(context)) {
            lastGameState = context;
            invalidInputCount = 0;
        }
        
        invalidInputCount++;
        
        if (invalidInputCount >= 3) {
            showContextualHint(context);
            invalidInputCount = 0; // Reset after showing hint
        }
    }
    
    /**
     * Resets invalid input counter when valid input is received.
     */
    private void resetInvalidInputCount() {
        invalidInputCount = 0;
    }
    
    /**
     * Shows contextual hints based on current game state (Feature 4 - v3.1.0).
     */
    private void showContextualHint(String context) {
        StringBuilder hint = new StringBuilder();
        hint.append("\n💡 HINT: ");
        
        switch (context) {
            case "ROOM_ACTION" -> hint.append("Enter 1 to search, 2 to move, 3 for stats, or 4 for inventory. You can also type 'use <item>' to use items quickly!");
            case "MONSTER_ACTION" -> hint.append("Enhanced Combat Options: 1=Attack, 2=Defend, 3=Heavy Attack (mana), 4=Quick Attack, 5=Use Item, 6=Run!");
            case "COMBAT_ACTION" -> hint.append("Enter 1 to attack or 2 to use an item during combat.");
            case "CLASS_SELECTION" -> hint.append("Choose a class: 1 for Warrior (tanky), 2 for Mage (high damage), or 3 for Rogue (balanced).");
            case "DIFFICULTY" -> hint.append("Select difficulty: 1-Easy, 2-Normal, 3-Hard, or 4-Death mode!");
            default -> hint.append("Follow the on-screen prompts and enter the corresponding number or command.");
        }
        
        hint.append("\n");
        appendToGameText(hint.toString());
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
            Player.PlayerClass chosenClass;
            
            switch (classChoice) {
                case 1 -> {
                    chosenClass = Player.PlayerClass.WARRIOR;
                    appendToGameText("\n🛡️ You have chosen the path of the WARRIOR!\n");
                    appendToGameText("Strong and resilient, you face danger with unwavering courage.\n");
                }
                case 2 -> {
                    chosenClass = Player.PlayerClass.MAGE;
                    appendToGameText("\n🧙 You have chosen the path of the MAGE!\n");
                    appendToGameText("Wielding arcane power, you bend reality to your will.\n");
                }
                case 3 -> {
                    chosenClass = Player.PlayerClass.ROGUE;
                    appendToGameText("\n😏 You have chosen the path of the ROGUE!\n");
                    appendToGameText("Swift and cunning, you strike from the shadows.\n");
                }
                default -> {
                    trackInvalidInput("CLASS_SELECTION");
                    appendToGameText("Please enter 1, 2, or 3: ");
                    waitingForInput = true;
                    return;
                }
            }
            
            player.setPlayerClass(chosenClass);
            askForDifficulty();
            
        } catch (NumberFormatException e) {
            trackInvalidInput("CLASS_SELECTION");
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
            int diffChoice = Integer.parseInt(input);
            if (diffChoice >= 1 && diffChoice <= 4) {
                // Show difficulty preview
                StringBuilder preview = new StringBuilder();
                preview.append("\n📊 Difficulty Preview:\n\n");
                
                switch (diffChoice) {
                    case 1 -> {
                        difficulty = "EASY";
                        preview.append("🟢 EASY MODE\n");
                        preview.append("• Monster HP: ").append((int)(Balance.EASY_HP * 100)).append("%\n");
                        preview.append("• Monster ATK: ").append((int)(Balance.EASY_ATK * 100)).append("%\n");
                        preview.append("• Player DEF Bonus: +").append((int)(Balance.EASY_DEF_BONUS * 100)).append("%\n");
                        preview.append("• Healing: ").append((int)(Balance.EASY_HEAL_MOD * 100)).append("%\n");
                    }
                    case 2 -> {
                        difficulty = "NORMAL";
                        preview.append("🟡 NORMAL MODE\n");
                        preview.append("• Monster HP: ").append((int)(Balance.NORM_HP * 100)).append("%\n");
                        preview.append("• Monster ATK: ").append((int)(Balance.NORM_ATK * 100)).append("%\n");
                        preview.append("• Balanced gameplay\n");
                    }
                    case 3 -> {
                        difficulty = "HARD";
                        preview.append("🔴 HARD MODE\n");
                        preview.append("• Monster HP: ").append((int)(Balance.HARD_HP * 100)).append("%\n");
                        preview.append("• Monster ATK: ").append((int)(Balance.HARD_ATK * 100)).append("%\n");
                        preview.append("• Healing: ").append((int)(Balance.HARD_HEAL_MOD * 100)).append("%\n");
                    }
                    case 4 -> {
                        difficulty = "DEATH";
                        preview.append("⚫ DEATH MODE\n");
                        preview.append("• Monster HP: 150%\n");
                        preview.append("• Monster ATK: 130%\n");
                        preview.append("• Healing: 80%\n");
                        preview.append("• ⚠️ No mercy!\n");
                    }
                }
                
                preview.append("\n⚙️ Difficulty set to ").append(difficulty).append("!\n");
                appendToGameText(preview.toString());
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
    
    /**
     * Loads a saved game from disk and restores player state.
     */
    private void loadSavedGame() {
        SaveManager.SaveData saveData = SaveManager.loadGame();
        
        if (saveData == null) {
            appendToGameText("\n❌ Error loading save game. Starting new game...\n\n");
            appendToGameText("🤔 Do you dare to enter the depths? (YES/NO): ");
            waitingForInput = true;
            expectedInputType = "START_CONFIRMATION";
            return;
        }
        
        try {
            // Restore player class
            Player.PlayerClass loadedClass = 
                Player.PlayerClass.valueOf(saveData.playerClass);
            player = new Player(saveData.name, loadedClass);
            
            // Restore player stats
            player.restoreSaveData(
                saveData.level,
                saveData.experience,
                saveData.health,
                saveData.maxHealth,
                saveData.attack,
                saveData.defense,
                saveData.magic,
                saveData.roomsExplored,
                saveData.monstersDefeated
            );
            
            player.setDungeonLevel(saveData.dungeonLevel);
            
            // Sync to game state
            gameState.resetGame();
            gameState.setLevel(saveData.dungeonLevel);
            syncPlayerToGameState();
            
            appendToGameText("\n✅ Game loaded successfully!\n");
            appendToGameText("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            appendToGameText("📜 Welcome back, " + player.getName() + "!\n");
            appendToGameText("🏰 You were at floor " + saveData.dungeonLevel + "\n");
            appendToGameText("⭐ Character Level: " + saveData.level + "\n");
            appendToGameText("❤️  Health: " + saveData.health + "/" + saveData.maxHealth + "\n");
            appendToGameText("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
            
            updateUI();
            continueGameplay();
            
        } catch (Exception e) {
            appendToGameText("\n❌ Error restoring save data: " + e.getMessage() + "\n");
            appendToGameText("Starting new game...\n\n");
            player = new Player();
            gameState.resetGame();
            appendToGameText("🤔 Do you dare to enter the depths? (YES/NO): ");
            waitingForInput = true;
            expectedInputType = "START_CONFIRMATION";
        }
    }
    
    /**
     * Auto-saves the current game state.
     */
    private void autoSave() {
        if (player != null && isGameRunning) {
            boolean success = SaveManager.saveGame(player, gameState.getLevel());
            if (success) {
                // Optionally show save indicator (commented out to avoid spam)
                // appendToGameText("💾 Game auto-saved.\n");
            }
        }
    }
    
    
    /**
     * Synchronizes player stats to the legacy GameState system.
     * 
     * IMPORTANT: This method must be called before updateUI() to ensure
     * the UI displays accurate player stats. The Player object is the
     * source of truth for all player stats (health, attack, defense),
     * while GameState tracks dungeon progression (level, room, searches).
     * 
     * Call this after any operation that modifies player stats:
     * - Taking damage or healing
     * - Gaining experience or leveling up
     * - Using items that affect stats
     * - Loading a saved game
     * - Creating a new player instance
     */
    private void syncPlayerToGameState() {
        gameState.setHealth(player.getHealth());
        gameState.setAttack(player.getAttack());
        gameState.setDefense(player.getDefense());
        gameState.setLevel(player.getDungeonLevel());
    }
    
    private void continueGameplay() {
        if (gameState.getLevel() >= 50) {
            appendToGameText("\n🎉 VICTORY! You have escaped the dungeon!\n");
            appendToGameText("🏆 Congratulations, " + player.getName() + "! You are truly a hero!\n");
            appendToGameText("⭐ You conquered all 50 floors! A legendary feat!\n\n");
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
        
        // Auto-save progress
        autoSave();
        
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
        // V4.0.0 - Enhanced monster encounter using advanced Monster system
        audioManager.setGameStateMusic("combat");
        
        // Create a monster using the new factory system based on current level
        int dungeonLevel = gameState.getLevel();
        currentMonster = createLevelAppropriateMonster(dungeonLevel);
        monsterHealth = currentMonster.getHealth();
        
        // Enhanced encounter display
        appendToGameText("⚠️ A " + currentMonster.getName() + " [" + currentMonster.getType() + "] appears!\n");
        appendToGameText("👹 " + currentMonster.getName() + " [Level " + dungeonLevel + "] - " + currentMonster.getFamily() + "\n");
        appendToGameText("💚 Health: " + currentMonster.getHealth() + "/" + currentMonster.getMaxHealth() + 
                        " | ⚔️ Attack: " + currentMonster.getAttack() + 
                        " | 🛡️ Defense: " + currentMonster.getDefense() + "\n");
        
        // Show monster's special abilities (simplified)
        appendToGameText("✨ This " + currentMonster.getName() + " looks dangerous!\n");
        
        // Show enhanced combat options
        showCombatOptions();
        waitingForInput = true;
        expectedInputType = "MONSTER_ACTION";
        
        // Play encounter sound
        audioManager.playEnvironmentSound("footsteps");
    }
    
    /**
     * V4.0.0 - Create a level-appropriate monster using the new Monster system
     */
    private Monster createLevelAppropriateMonster(int dungeonLevel) {
        // Determine monster type based on level ranges
        if (dungeonLevel <= 3) {
            // Early game monsters
            return Math.random() < 0.7 ? Monster.createGoblin(dungeonLevel) : Monster.createWolf(dungeonLevel);
        } else if (dungeonLevel <= 7) {
            // Mid-early game monsters
            double roll = Math.random();
            if (roll < 0.3) return Monster.createOrc(dungeonLevel);
            else if (roll < 0.6) return Monster.createSkeleton(dungeonLevel);
            else return Monster.createSpider(dungeonLevel);
        } else if (dungeonLevel <= 12) {
            // Mid-game monsters
            double roll = Math.random();
            if (roll < 0.25) return Monster.createZombie(dungeonLevel);
            else if (roll < 0.5) return Monster.createFireElemental(dungeonLevel);
            else if (roll < 0.75) return Monster.createIceElemental(dungeonLevel);
            else return Monster.createDemon(dungeonLevel);
        } else {
            // Late game - chance for boss monsters
            if (Math.random() < 0.3) {
                // Boss encounter!
                return Monster.createBossMonster(dungeonLevel);
            } else {
                // Elite monsters
                double roll = Math.random();
                if (roll < 0.5) return Monster.createDemon(dungeonLevel);
                else return Monster.createSkeleton(dungeonLevel); // Elite skeleton
            }
        }
    }
    
    private void handleMonsterAction(String input) {
        // Use the new enhanced combat system
        processCombatAction(input);
    }
    
    private void startCombat() {
        appendToGameText("⚔️ Combat begins!\n\n");
        executeCombatRound();
    }
    
    private void executeCombatRound() {
        if (!currentMonster.isAlive()) {
            // V4.0.0 - Enhanced victory handling with achievements
            appendToGameText("🏆 Victory! The " + currentMonster.getName() + " has been defeated!\n");
            audioManager.playSound("monster_death");
            
            // Reward experience and potential level up
            int expReward = 30 + (gameState.getLevel() * 10);
            boolean leveledUp = player.gainExperience(expReward);
            appendToGameText("⭐ You gained " + expReward + " experience!\n");
            
            if (leveledUp) {
                appendToGameText("🎉 LEVEL UP! You grow stronger!\n");
                audioManager.playUISound("level_up");
                achievementManager.checkLevelAchievements(player);
            }
            
            // Record kill for achievements - V4.0.0 enhanced
            player.recordMonsterKill();
            achievementManager.checkCombatAchievements(player, currentMonster, true, false, false, 0);
            achievementManager.checkCollectionAchievements(player);
            
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
            audioManager.setGameStateMusic("game_over");
            showCredits();
            isGameRunning = false;
            return;
        }
        
        // V4.0.0 - ADVANCED COMBAT SYSTEM INTEGRATION
        appendToGameText("\n⚔️ === COMBAT ROUND ===\n");
        
        // Player attacks using advanced combat engine
        CombatEngine.CombatResult playerAttackResult = CombatEngine.playerAttackMonster(
            player, currentMonster, CombatEngine.AttackType.NORMAL_ATTACK);
        
        // Enhanced combat feedback with audio
        if (playerAttackResult.result == CombatEngine.AttackResult.MISS) {
            appendToGameText("💨 Your attack misses the " + currentMonster.getName() + "!\n");
            audioManager.playCombatSound("weapon", false, false);
        } else if (playerAttackResult.result == CombatEngine.AttackResult.CRITICAL_HIT) {
            appendToGameText("💥 CRITICAL HIT! You deal " + playerAttackResult.damage + " damage! Monster health: " + 
                           currentMonster.getHealth() + "/" + currentMonster.getMaxHealth() + "\n");
            audioManager.playCombatSound("weapon", true, true);
            achievementManager.checkCombatAchievements(player, currentMonster, false, true, false, playerAttackResult.damage);
        } else {
            // Normal hit or other result
            appendToGameText("⚔️ You hit for " + playerAttackResult.damage + " damage! Monster health: " + 
                           currentMonster.getHealth() + "/" + currentMonster.getMaxHealth() + "\n");
            audioManager.playCombatSound("weapon", true, false);
            achievementManager.checkCombatAchievements(player, currentMonster, false, false, false, playerAttackResult.damage);
        }
        
        // Show combat description if available
        if (playerAttackResult.description != null && !playerAttackResult.description.isEmpty()) {
            appendToGameText("📝 " + playerAttackResult.description + "\n");
        }
        
        // Check if monster is defeated
        if (!currentMonster.isAlive()) {
            executeCombatRound(); // Re-run to handle victory
            return;
        }
        
        // Monster attacks back using advanced combat engine
        CombatEngine.CombatResult monsterAttackResult = CombatEngine.monsterAttackPlayer(currentMonster, player);
        
        if (monsterAttackResult.result == CombatEngine.AttackResult.MISS) {
            appendToGameText("🛡️ You dodge the " + currentMonster.getName() + "'s attack!\n");
            audioManager.playSound("dodge");
            achievementManager.checkCombatAchievements(player, currentMonster, false, false, true, 0);
        } else if (monsterAttackResult.result == CombatEngine.AttackResult.CRITICAL_HIT) {
            appendToGameText("☠️ The " + currentMonster.getName() + " lands a critical hit for " + monsterAttackResult.damage + 
                           " damage! Your health: " + player.getHealth() + "/" + player.getMaxHealth() + "\n");
            audioManager.playSound("player_hurt");
        } else {
            appendToGameText("💢 The " + currentMonster.getName() + " hits you for " + monsterAttackResult.damage + 
                           " damage! Your health: " + player.getHealth() + "/" + player.getMaxHealth() + "\n");
            audioManager.playSound("player_hurt");
        }
        
        // Show monster combat description if available
        if (monsterAttackResult.description != null && !monsterAttackResult.description.isEmpty()) {
            appendToGameText("💀 " + monsterAttackResult.description + "\n");
        }
        
        // Display status effects if any (simplified for now)
        if (!playerAttackResult.appliedEffects.isEmpty() || !monsterAttackResult.appliedEffects.isEmpty()) {
            appendToGameText("🌟 Special effects occurred during combat!\n");
        }
        
        syncPlayerToGameState();
        updateUI();
        
        // Check if player is defeated
        if (!player.isAlive()) {
            executeCombatRound(); // Re-run to handle defeat
            return;
        }
        
        // Continue combat with enhanced options
        appendToGameText("\n🤺 Choose your action:\n");
        appendToGameText(" 1: Continue attacking\n");
        appendToGameText(" 2: Try to escape\n");
        if (player.getPlayerClass() == Player.PlayerClass.MAGE && player.getMana() >= 10) {
            appendToGameText(" 3: Cast spell (10 mana)\n");
        }
        appendToGameText("Choose 1-" + (player.getPlayerClass() == Player.PlayerClass.MAGE ? "3" : "2") + ": ");
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
        player = new Player();
        gameState.resetGame();
        isGameRunning = false;
        waitingForInput = false;
        expectedInputType = "";
        currentMonster = null;
        monsterHealth = 0;
        gameTextArea.clear();
        displayWelcomeMessage();
        syncPlayerToGameState();
        updateUI();
    }
    
    /**
     * Updates the UI labels with current player stats.
     * 
     * IMPORTANT: Always call syncPlayerToGameState() before this method
     * to ensure GameState has the latest player stats. The UI reads from
     * the Player object when available, but falls back to GameState if
     * the player is not initialized (e.g., during initial UI setup).
     */
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
            // Fallback to GameState (only used during initialization)
            healthLabel.setText("❤ Health: " + gameState.getHealth());
            defenseLabel.setText("🛡 Defense: " + gameState.getDefense());
            attackLabel.setText("⚔ Attack: " + gameState.getAttack());
            levelLabel.setText("📈 Level: " + gameState.getLevel());
        }
    }
    
    /**
     * Shows game text by clearing the text area and displaying only the new message.
     * This implements the overwrite-only text behavior specified in v3.1.0.
     * 
     * @param text The text to display
     */
    private void showGameText(String text) {
        Platform.runLater(() -> {
            gameTextArea.setEditable(false);
            gameTextArea.setWrapText(true);
            gameTextArea.clear();
            gameTextArea.setText(text);
            gameTextArea.positionCaret(0); // ensure top visible
        });
    }
    
    /**
     * Shows a single line of game text (adds newline if not present).
     * 
     * @param text The text to display
     */
    private void showGameTextLine(String text) {
        showGameText(text.endsWith("\n") ? text : text + "\n");
    }
    
    /**
     * Legacy method - temporarily kept for compatibility during refactoring.
     * DEPRECATED: Use showGameText() or showGameTextLine() instead.
     * 
     * @param text The text to append (will actually replace all text)
     * @deprecated Use showGameText() for proper overwrite-only behavior
     */
    @Deprecated
    private void appendToGameText(String text) {
        // For now, accumulate text for gradual transition
        Platform.runLater(() -> {
            gameTextArea.appendText(text);
        });
    }
    
    /**
     * V4.0.0 - Display achievement notification in the game text area
     * 
     * @param achievement The unlocked achievement
     */
    private void showAchievementNotification(Achievement achievement) {
        showGameText("\n" + "🎉".repeat(20));
        showGameText("★ ACHIEVEMENT UNLOCKED! ★");
        showGameText(achievement.getIcon() + " " + achievement.getName());
        showGameText("\"" + achievement.getDescription() + "\"");
        showGameText("[" + achievement.getRarity().getName() + " - " + 
                    achievement.getRarity().getPointValue() + " points]");
        showGameText("🎉".repeat(20) + "\n");
    }
    
    /**
     * Handles quick-use command for items (Feature 3 - v3.1.0).
     * Allows using items during exploration or combat with "use <item>" command.
     * 
     * @param itemName The name of the item to use
     */
    private void handleQuickUse(String itemName) {
        // Use player's existing useItem method
        boolean success = player.useItem(itemName);
        
        if (!success) {
            StringBuilder message = new StringBuilder();
            message.append("❌ Item '").append(itemName).append("' not found in inventory.\n");
            message.append("💼 Type '4' or use inventory menu to view your items.\n");
            appendToGameText(message.toString());
        } else {
            // Show result
            StringBuilder message = new StringBuilder();
            message.append("✨ Used ").append(itemName).append("!\n");
            message.append("❤️ Health: ").append(player.getHealth()).append("/").append(player.getMaxHealth()).append("\n");
            appendToGameText(message.toString());
            
            // Update UI
            syncPlayerToGameState();
            updateUI();
            
            // Auto-save after item use
            autoSave();
        }
        
        waitingForInput = true;
    }
    
    /**
     * Enhanced attack options for the player during combat.
     */
    private void showCombatOptions() {
        appendToGameText("\n═══ COMBAT OPTIONS ═══\n");
        appendToGameText("1. 🗡️ Attack - Standard attack\n");
        appendToGameText("2. 🛡️ Defend - Reduce incoming damage\n");
        appendToGameText("3. ⚡ Heavy Attack - High damage but uses mana\n");
        appendToGameText("4. 🏹 Ranged Attack - Attack from distance\n");
        appendToGameText("5. 🧪 Use Item - Use healing potion or other items\n");
        appendToGameText("6. 🏃 Run - Attempt to flee\n");
        appendToGameText("Choose your action: ");
    }
    
    /**
     * Shows visual combat feedback with animations.
     */
    private void showCombatAnimation(String animationType, boolean isPlayerAction) {
        String prefix = isPlayerAction ? "🧑‍💼 " : "👹 ";
        String actor = isPlayerAction ? "You" : currentMonster.getName();
        
        switch (animationType.toLowerCase()) {
            case "attack":
                appendToGameText(prefix + actor + " swings their weapon! ⚔️\n");
                break;
            case "heavy_attack":
                appendToGameText(prefix + actor + " charges up for a powerful blow! ⚡⚔️\n");
                break;
            case "defend":
                appendToGameText(prefix + actor + " raises their guard! 🛡️\n");
                break;
            case "miss":
                appendToGameText(prefix + actor + " attacks but misses! 💨\n");
                break;
            case "critical":
                appendToGameText(prefix + actor + " lands a devastating critical hit! ✨💥\n");
                break;
            case "block":
                appendToGameText(prefix + actor + " blocks the attack! 🛡️\n");
                break;
            default:
                appendToGameText(prefix + actor + " performs an action!\n");
        }
    }
    
    /**
     * Shows status effects on player or monster.
     */
    private void showStatusEffects() {
        // Status effects display - simplified for current implementation
        if (player.getHealth() < player.getMaxHealth() * 0.3) {
            appendToGameText("⚠️ You are badly wounded!\n");
        }
        if (player.getMana() < player.getMaxMana() * 0.3) {
            appendToGameText("💙 Your mana is running low!\n");
        }
    }
    
    /**
     * Enhanced combat action processing with new attack types.
     */
    private void processCombatAction(String action) {
        switch (action.toLowerCase()) {
            case "1":
            case "attack":
                showCombatAnimation("attack", true);
                performPlayerAttack(CombatEngine.AttackType.NORMAL_ATTACK);
                break;
            case "2":
            case "defend":
                showCombatAnimation("defend", true);
                performPlayerAttack(CombatEngine.AttackType.DEFENSIVE_STANCE);
                break;
            case "3":
            case "heavy attack":
            case "heavy":
                if (player.getMana() >= 10) {
                    showCombatAnimation("heavy_attack", true);
                    // Consume mana for heavy attack (direct access to field)
                    appendToGameText("💙 You channel your mana for a powerful attack! (-10 mana)\n");
                    performPlayerAttack(CombatEngine.AttackType.HEAVY_ATTACK);
                } else {
                    appendToGameText("❌ Not enough mana for heavy attack! (Need 10 mana)\n");
                    showCombatOptions();
                }
                break;
            case "4":
            case "quick":
            case "quick attack":
                showCombatAnimation("attack", true);
                performPlayerAttack(CombatEngine.AttackType.QUICK_ATTACK);
                break;
            case "5":
            case "item":
            case "use item":
                useItemInCombat();
                break;
            case "6":
            case "run":
            case "flee":
                attemptToRun();
                break;
            default:
                appendToGameText("❌ Invalid action! Please choose 1-6.\n");
                showCombatOptions();
        }
    }
    
    /**
     * Performs a single player attack with the specified attack type.
     */
    private void performPlayerAttack(CombatEngine.AttackType attackType) {
        CombatEngine.CombatResult playerAttackResult = 
            CombatEngine.playerAttackMonster(player, currentMonster, attackType);
        
        // Process player attack result
        handleCombatResult(playerAttackResult, true);
        
        // If monster is still alive, it attacks back
        if (currentMonster.getHealth() > 0) {
            CombatEngine.CombatResult monsterAttackResult = 
                CombatEngine.monsterAttackPlayer(currentMonster, player);
            handleCombatResult(monsterAttackResult, false);
        }
        
        // Continue combat or end it
        if (player.getHealth() <= 0) {
            appendToGameText("💀 You have been defeated!\n");
            appendToGameText("🔄 Game Over - Use 'Reset' to try again.\n");
            waitingForInput = false;
        } else if (currentMonster.getHealth() <= 0) {
            appendToGameText("🎉 You defeated the " + currentMonster.getName() + "!\n");
            
            // Gain experience and possibly level up
            int expGained = player.getLevel() * 15;
            player.gainExperience(expGained);
            appendToGameText("✨ You gained " + expGained + " experience!\n");
            
            // Gain gold (simplified - just display message for now)
            int goldGained = (int)(Math.random() * 50) + player.getLevel() * 10;
            appendToGameText("💰 You found " + goldGained + " gold!\n");
            
            // Check for level up (simplified level up check)
            if (player.getExperience() >= player.getLevel() * 100) {
                // Simple level up - increase level and stats
                player.setLevel(player.getLevel() + 1);
                player.setMaxHealth(player.getMaxHealth() + 10);
                // Heal player to full health on level up (using existing healing method)
                player.heal(player.getMaxHealth());
                appendToGameText("🎊 LEVEL UP! You are now level " + player.getLevel() + "!\n");
                audioManager.playUISound("level_up");
            }
            
            // Achievement tracking (simplified)
            // achievementManager tracks combat victories automatically
            
            currentMonster = null;
            waitingForInput = true;
            appendToGameText("\nYou continue exploring the dungeon...\n");
            appendToGameText("Enter command: ");
        } else {
            showStatusEffects();
            showCombatOptions();
        }
    }
    
    /**
     * Handles combat result display and processing.
     */
    private void handleCombatResult(CombatEngine.CombatResult result, boolean isPlayerAction) {
        if (isPlayerAction) {
            // Player action result
            switch (result.result) {
                case MISS:
                    appendToGameText("💨 Your attack misses!\n");
                    audioManager.playCombatSound("miss", false, false);
                    break;
                case HIT:
                    appendToGameText("⚔️ You hit for " + result.damage + " damage!\n");
                    audioManager.playCombatSound("hit", false, false);
                    break;
                case CRITICAL_HIT:
                    appendToGameText("✨💥 CRITICAL HIT! You deal " + result.damage + " damage!\n");
                    audioManager.playCombatSound("critical", false, false);
                    break;
                case BLOCKED:
                    appendToGameText("🛡️ Your attack was blocked! Only " + result.damage + " damage dealt.\n");
                    audioManager.playCombatSound("block", false, false);
                    break;
                case PARRIED:
                    appendToGameText("⚡ Your attack was parried! " + result.damage + " damage dealt.\n");
                    audioManager.playCombatSound("parry", false, false);
                    break;
                case COUNTERED:
                    appendToGameText("🔄 Your attack was countered! You take " + result.damage + " damage!\n");
                    audioManager.playCombatSound("counter", false, false);
                    break;
            }
        } else {
            // Monster action result
            switch (result.result) {
                case MISS:
                    appendToGameText("💨 The " + currentMonster.getName() + " misses!\n");
                    audioManager.playCombatSound("miss", false, false);
                    break;
                case HIT:
                    appendToGameText("💔 The " + currentMonster.getName() + " hits you for " + result.damage + " damage!\n");
                    audioManager.playCombatSound("hit", false, false);
                    break;
                case CRITICAL_HIT:
                    appendToGameText("💀 The " + currentMonster.getName() + " scores a critical hit for " + result.damage + " damage!\n");
                    audioManager.playCombatSound("critical", false, false);
                    break;
                case BLOCKED:
                    appendToGameText("🛡️ You partially block the attack! " + result.damage + " damage taken.\n");
                    audioManager.playCombatSound("block", false, false);
                    break;
                case PARRIED:
                    appendToGameText("⚡ You parry the attack! " + result.damage + " damage taken.\n");
                    audioManager.playCombatSound("parry", false, false);
                    break;
                case COUNTERED:
                    appendToGameText("🔄 You counter the attack! The " + currentMonster.getName() + " takes " + result.damage + " damage!\n");
                    audioManager.playCombatSound("counter", false, false);
                    break;
            }
        }
        
        // Update UI after combat result
        syncPlayerToGameState();
        updateUI();
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