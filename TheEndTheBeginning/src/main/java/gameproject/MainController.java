package gameproject;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * MainController class - JavaFX Controller for "The End The Beginning" game.
 * 
 * This class serves as the primary game controller, managing the user interface
 * and game logic for the JavaFX version of the dungeon escape game. It handles
 * all user interactions, game state management, and UI updates.
 * 
 * DESIGN PATTERNS USED:
 * - MVC (Model-View-Controller): Separates UI logic from game logic
 * - State Machine: Uses expectedInputType to manage different input contexts
 * - Event-Driven: Responds to user actions through JavaFX event handlers
 * 
 * CORE RESPONSIBILITIES:
 * - Managing JavaFX UI components and user interactions
 * - Processing player input and game commands
 * - Maintaining game state through GameState inner class
 * - Coordinating combat encounters and room exploration
 * - Displaying game text and updating player statistics
 * 
 * @author Abdul Fornah
 * @version 1.0
 */
public class MainController implements Initializable {
    
    // ===== JAVAFX UI COMPONENT REFERENCES =====
    // These @FXML annotations link to components defined in game.fxml
    
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
    
    // ===== GAME STATE MANAGEMENT VARIABLES =====
    
    /**
     * Core game state object - manages all player stats, progression, and game data
     * Implemented as inner class to keep game logic encapsulated
     */
    private GameState gameState;
    
    /**
     * Flag indicating whether a game session is currently active
     * Used to prevent multiple game instances and manage UI state
     */
    private boolean isGameRunning = false;
    
    /**
     * Input management flag - indicates if the game is waiting for player input
     * Prevents input processing when game is in narrative/display mode
     */
    private boolean waitingForInput = false;
    
    /**
     * State machine controller - defines what type of input is expected
     * Valid values: "START_CONFIRMATION", "DIFFICULTY", "PLAYER_NAME", 
     *               "ROOM_ACTION", "MONSTER_ACTION", "COMBAT_ACTION"
     * This enables context-sensitive input processing
     */
    private String expectedInputType = "";
    
    /**
     * JavaFX initialization method - called automatically when the FXML is loaded.
     * Sets up the initial game state and UI configuration.
     * 
     * INITIALIZATION PROCESS:
     * 1. Create new GameState instance with default values
     * 2. Display welcome message to player
     * 3. Configure Enter key as input submission shortcut
     * 
     * @param location URL location (unused but required by Initializable interface)
     * @param resources ResourceBundle (unused but required by Initializable interface)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize game state with default player stats
        gameState = new GameState();
        displayWelcomeMessage();
        
        // USER EXPERIENCE ENHANCEMENT: Allow Enter key to submit input
        // This provides a more natural interaction than requiring mouse clicks
        inputField.setOnAction(event -> handleSubmit());
    }
    
    /**
     * Displays the initial welcome message and instructions to the player.
     * This method sets up the game's first impression and guides user interaction.
     * 
     * UI DESIGN NOTE: Uses clear, friendly language to welcome new players
     * and provide obvious next steps for starting the game.
     */
    private void displayWelcomeMessage() {
        appendToGameText("Welcome to DUNGEON ESCAPE (JavaFX Edition)\n\n");
        appendToGameText("Click 'Start Game' to begin your adventure!\n");
    }
    
    @FXML
    private void handleStart() {
        if (!isGameRunning) {
            startNewGame();
        }
    }
    
    @FXML
    private void handleSubmit() {
        String input = inputField.getText().trim();
        if (!input.isEmpty() && waitingForInput) {
            processInput(input);
            inputField.clear();
        }
    }
    
    @FXML
    private void handleStats() {
        if (isGameRunning) {
            displayPlayerStats();
        }
    }
    
    @FXML
    private void handleReset() {
        resetGame();
    }
    
    private void startNewGame() {
        gameState.resetGame();
        isGameRunning = true;
        gameTextArea.clear();
        
        appendToGameText("Do you want to play? (YES/NO): ");
        waitingForInput = true;
        expectedInputType = "START_CONFIRMATION";
        updateUI();
    }
    
    /**
     * Core input processing method - handles all player text input based on current game state.
     * 
     * This method implements a state machine pattern where the expectedInputType determines
     * how player input should be interpreted and processed. This allows the same input field
     * to handle different types of data (yes/no, numbers, names, etc.) contextually.
     * 
     * STATE MACHINE DESIGN:
     * - Each game state expects specific input types
     * - Invalid input triggers re-prompting without advancing game state
     * - Successful input processing advances to next appropriate game state
     * - Error handling prevents game from breaking on unexpected input
     * 
     * @param input The player's text input, trimmed of whitespace
     */
    private void processInput(String input) {
        // Clear waiting flag since we're now processing input
        waitingForInput = false;
        
        // STATE MACHINE: Process input based on current expected input type
        switch (expectedInputType) {
            case "START_CONFIRMATION":
                // GAME START DECISION: Player chooses whether to begin playing
                if (input.equalsIgnoreCase("YES")) {
                    askForDifficulty();
                } else if (input.equalsIgnoreCase("NO")) {
                    // Player declined to play - show credits and end session
                    showCredits();
                    isGameRunning = false;
                } else {
                    // INVALID INPUT: Re-prompt without changing game state
                    appendToGameText("Please enter YES or NO: ");
                    waitingForInput = true;
                }
                break;
                
            case "DIFFICULTY":
                try {
                    int difficulty = Integer.parseInt(input);
                    if (difficulty >= 1 && difficulty <= 4) {
                        gameState.setDifficulty(difficulty);
                        askForPlayerName();
                    } else {
                        appendToGameText("Please enter a number between 1-4: ");
                        waitingForInput = true;
                    }
                } catch (NumberFormatException e) {
                    appendToGameText("Please enter a valid number (1-4): ");
                    waitingForInput = true;
                }
                break;
                
            case "PLAYER_NAME":
                gameState.setPlayerName(input);
                startGameplay();
                break;
                
            case "ROOM_ACTION":
                handleRoomAction(input);
                break;
                
            case "MONSTER_ACTION":
                handleMonsterAction(input);
                break;
                
            case "COMBAT_ACTION":
                handleCombatAction(input);
                break;
        }
    }
    
    private void askForDifficulty() {
        appendToGameText("\nChoose difficulty:\n");
        appendToGameText(" 1: Easy\n 2: Medium\n 3: Hard\n 4: Death\n");
        appendToGameText("Enter 1-4: ");
        waitingForInput = true;
        expectedInputType = "DIFFICULTY";
    }
    
    private void askForPlayerName() {
        appendToGameText("\nWhat's your name? ");
        waitingForInput = true;
        expectedInputType = "PLAYER_NAME";
    }
    
    private void startGameplay() {
        appendToGameText("\nYou wake up in a cold, dark dungeon. The air tastes of iron and old candles.\n");
        appendToGameText("Hope you survive this ordeal, " + gameState.getPlayerName() + ". May fortune favor you.\n\n");
        
        displayPlayerStats();
        continueGameplay();
    }
    
    private void continueGameplay() {
        if (gameState.getLevel() >= 10) {
            appendToGameText("\nYou have escaped the dungeon. Congratulations!\n");
            showCredits();
            isGameRunning = false;
            return;
        }
        
        if (gameState.getHealth() <= 0) {
            appendToGameText("\nGame Over! You have died.\n");
            showCredits();
            isGameRunning = false;
            return;
        }
        
        appendToGameText("\nfloor " + gameState.getLevel() + " :\n");
        appendToGameText("room " + gameState.getRoom() + " :\n\n");
        
        int gameEvent = (int) (Math.random() * 4);
        
        if (gameEvent != 3) {
            appendToGameText("What do you want to do, " + gameState.getPlayerName() + "?\n");
            appendToGameText(" 1: Continue in the room\n 2: Move to next room\n 3: Check stats\n");
            appendToGameText("Enter 1-3: ");
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
                case 1:
                    if (gameState.getRoomSearches() < 3) {
                        appendToGameText("You search the room carefully...\n");
                        gameState.incrementRoomSearches();
                        gameState.incrementRoom();
                        handleRoomEvent();
                    } else {
                        appendToGameText("You've searched enough. Better move on.\n");
                        gameState.nextLevel();
                        handleRoomEvent();
                    }
                    break;
                case 2:
                    appendToGameText("You move to the next room.\n");
                    gameState.nextLevel();
                    handleRoomEvent();
                    break;
                case 3:
                    displayPlayerStats();
                    continueGameplay();
                    break;
                default:
                    appendToGameText("Please enter 1, 2, or 3: ");
                    waitingForInput = true;
            }
        } catch (NumberFormatException e) {
            appendToGameText("Please enter a valid number (1-3): ");
            waitingForInput = true;
        }
    }
    
    private void handleRoomEvent() {
        int event = gameState.getCurrentEvent();
        int multiplier = (int) (Math.random() * 6);
        int changeAmount = (int) (Math.random() * 10);
        
        switch (event) {
            case 0:
                int healthGain = multiplier * changeAmount;
                gameState.addHealth(healthGain);
                appendToGameText("You found a health potion. Gained: " + healthGain + " health.\n");
                break;
            case 1:
                int defenseGain = multiplier * changeAmount;
                gameState.addDefense(defenseGain);
                appendToGameText("You found armor scraps. Defense increased by " + defenseGain + ".\n");
                break;
            case 2:
                int attackGain = multiplier * changeAmount;
                gameState.addAttack(attackGain);
                appendToGameText("You found a crude weapon. Attack increased by " + attackGain + ".\n");
                break;
        }
        
        updateUI();
        continueGameplay();
    }
    
    private void encounterMonster() {
        appendToGameText("You have been noticed by a monster!\n\n");
        appendToGameText(" 1: Fight\n 2: Run\n 3: Hide\n 4: Do nothing\n");
        appendToGameText("Choose 1-4: ");
        waitingForInput = true;
        expectedInputType = "MONSTER_ACTION";
    }
    
    private void handleMonsterAction(String input) {
        try {
            int choice = Integer.parseInt(input);
            switch (choice) {
                case 1:
                    startCombat();
                    break;
                case 2:
                    if (Math.random() < 0.5) {
                        appendToGameText("You managed to run away to the next room.\n");
                        gameState.nextLevel();
                        continueGameplay();
                    } else {
                        appendToGameText("You failed to run. The monster attacks!\n");
                        gameState.takeDamage(10);
                        appendToGameText("You lost 10 health. Current health: " + gameState.getHealth() + "\n");
                        updateUI();
                        continueGameplay();
                    }
                    break;
                case 3:
                    appendToGameText("You hide quietly. The monster moves on. You survive this encounter.\n");
                    continueGameplay();
                    break;
                case 4:
                    appendToGameText("You do nothing. The monster attacks angrily.\n");
                    gameState.takeDamage(8);
                    appendToGameText("You lost 8 health. Current health: " + gameState.getHealth() + "\n");
                    updateUI();
                    continueGameplay();
                    break;
                default:
                    appendToGameText("Please enter 1, 2, 3, or 4: ");
                    waitingForInput = true;
            }
        } catch (NumberFormatException e) {
            appendToGameText("Please enter a valid number (1-4): ");
            waitingForInput = true;
        }
    }
    
    private void startCombat() {
        appendToGameText("You choose to fight the monster!\n");
        gameState.initializeCombat();
        continueCombat();
    }
    
    private void continueCombat() {
        if (gameState.getMonsterHealth() <= 0) {
            appendToGameText("The monster has been slain! You may move on.\n");
            gameState.nextLevel();
            gameState.levelUp();
            updateUI();
            continueGameplay();
            return;
        }
        
        if (gameState.getHealth() <= 0) {
            appendToGameText("You have been defeated by the monster. Game over.\n");
            showCredits();
            isGameRunning = false;
            return;
        }
        
        // Combat round
        boolean playerFirst = Math.random() < 0.6;
        
        if (playerFirst) {
            playerAttack();
            if (gameState.getMonsterHealth() > 0) {
                monsterAttack();
            }
        } else {
            monsterAttack();
            if (gameState.getHealth() > 0) {
                playerAttack();
            }
        }
        
        if (gameState.getHealth() > 0 && gameState.getMonsterHealth() > 0) {
            appendToGameText("\nWhat will you do next? 1: Continue fight  2: Attempt to run\n");
            appendToGameText("Choose 1-2: ");
            waitingForInput = true;
            expectedInputType = "COMBAT_ACTION";
        } else {
            continueCombat(); // Check win/lose conditions
        }
    }
    
    private void handleCombatAction(String input) {
        try {
            int choice = Integer.parseInt(input);
            switch (choice) {
                case 1:
                    continueCombat();
                    break;
                case 2:
                    if (Math.random() < 0.5) {
                        appendToGameText("You successfully escaped the monster!\n");
                        continueGameplay();
                    } else {
                        appendToGameText("You failed to escape. The fight continues.\n");
                        continueCombat();
                    }
                    break;
                default:
                    appendToGameText("Please enter 1 or 2: ");
                    waitingForInput = true;
            }
        } catch (NumberFormatException e) {
            appendToGameText("Please enter a valid number (1-2): ");
            waitingForInput = true;
        }
    }
    
    private void playerAttack() {
        int damage = gameState.calculatePlayerDamage();
        gameState.damageMonster(damage);
        appendToGameText("You hit the monster for " + damage + " damage. Monster health: " + Math.max(0, gameState.getMonsterHealth()) + "\n");
    }
    
    private void monsterAttack() {
        int damage = gameState.calculateMonsterDamage();
        int absorbed = gameState.absorbDamage(damage);
        
        if (absorbed > 0) {
            appendToGameText("The monster strikes your shield for " + absorbed + " damage. Shield remaining: " + gameState.getDefense() + "\n");
        }
        
        int finalDamage = damage - absorbed;
        if (finalDamage > 0) {
            gameState.takeDamage(finalDamage);
            appendToGameText("The monster hits you for " + finalDamage + " damage. Your health: " + Math.max(0, gameState.getHealth()) + "\n");
        }
        
        updateUI();
    }
    
    private void displayPlayerStats() {
        appendToGameText("\n=== Player Stats ===\n");
        appendToGameText("Health : " + gameState.getHealth() + "\n");
        appendToGameText("Defense: " + gameState.getDefense() + "\n");
        appendToGameText("Attack : " + gameState.getAttack() + "\n");
        appendToGameText("Level  : " + gameState.getLevel() + "\n\n");
    }
    
    private void showCredits() {
        appendToGameText("\n--- Credits ---\n");
        appendToGameText("Game developed by: Abdul Fornah\n");
        appendToGameText("Special thanks: ChatGPT (ideas) and playtesters\n");
    }
    
    private void resetGame() {
        gameState.resetGame();
        isGameRunning = false;
        waitingForInput = false;
        expectedInputType = "";
        gameTextArea.clear();
        displayWelcomeMessage();
        updateUI();
    }
    
    private void updateUI() {
        healthLabel.setText("Health: " + gameState.getHealth());
        defenseLabel.setText("Defense: " + gameState.getDefense());
        attackLabel.setText("Attack: " + gameState.getAttack());
        levelLabel.setText("Level: " + gameState.getLevel());
    }
    
    private void appendToGameText(String text) {
        gameTextArea.appendText(text);
    }
    
    /**
     * GameState inner class - Encapsulates all game data and logic.
     * 
     * This class serves as the "Model" in the MVC pattern, containing all game state
     * variables and methods for manipulating that state. It's implemented as a static
     * inner class to keep game logic closely associated with the controller while
     * maintaining clear separation of concerns.
     * 
     * DESIGN BENEFITS:
     * - Encapsulation: All game data in one place
     * - Type Safety: Methods provide controlled access to game state
     * - Maintainability: Easy to add new stats or modify existing ones
     * - Testing: Can be tested independently of UI components
     * 
     * GAME STATE CATEGORIES:
     * - Player Stats: health, attack, defense, level progression
     * - World State: current dungeon level, room, exploration progress
     * - Combat State: monster health, current encounter type
     * - Meta State: player name, difficulty, session tracking
     */
    private static class GameState {
        
        // ===== PLAYER CORE STATISTICS =====
        private int health = 100;           // Current player health points
        private int defense = 1;            // Damage absorption capability  
        private int attack = 1;             // Base damage output potential
        private int level = 1;              // Current dungeon level (1-10 for escape)
        
        // ===== WORLD EXPLORATION STATE =====
        private int room = 0;               // Current room number within level
        private int roomSearches = 0;       // Times player searched current room
        private int playerLevel = 1;        // Player's character level (affects combat)
        
        // ===== GAME CONFIGURATION =====
        private int difficulty = 1;         // Chosen difficulty (1=Easy, 2=Medium, 3=Hard, 4=Death)
        private String playerName = "";     // Player's chosen character name
        
        // ===== ACTIVE ENCOUNTER STATE =====
        private int monsterHealth = 0;      // Current monster's remaining health
        private int currentEvent = 0;       // Type of current room event (0=health, 1=defense, 2=attack, 3=monster)
        
        public void resetGame() {
            health = 100;
            defense = 1;
            attack = 1;
            level = 1;
            room = 0;
            roomSearches = 0;
            playerLevel = 1;
            difficulty = 1;
            playerName = "";
            monsterHealth = 0;
            currentEvent = 0;
        }
        
        public void initializeCombat() {
            monsterHealth = 40 + (int) (Math.random() * 61); // 40-100
        }
        
        public void nextLevel() {
            level++;
            room = 0;
            roomSearches = 0;
        }
        
        public void levelUp() {
            playerLevel++;
        }
        
        public void incrementRoom() {
            room++;
        }
        
        public void incrementRoomSearches() {
            roomSearches++;
        }
        
        public int calculatePlayerDamage() {
            int minDamage = Math.max(1, attack / 2);
            int maxDamage = Math.max(minDamage, attack + (playerLevel * 2));
            return minDamage + (int) (Math.random() * (maxDamage - minDamage + 1));
        }
        
        public int calculateMonsterDamage() {
            return 5 + (int) (Math.random() * (5 + level));
        }
        
        public int absorbDamage(int damage) {
            if (defense > 0) {
                int absorbed = Math.min(defense, damage);
                defense -= absorbed;
                return absorbed;
            }
            return 0;
        }
        
        public void takeDamage(int damage) {
            health -= damage;
        }
        
        public void damageMonster(int damage) {
            monsterHealth -= damage;
        }
        
        public void addHealth(int amount) { health += amount; }
        public void addDefense(int amount) { defense += amount; }
        public void addAttack(int amount) { attack += amount; }
        
        // Getters and setters
        public int getHealth() { return health; }
        public int getDefense() { return defense; }
        public int getAttack() { return attack; }
        public int getLevel() { return level; }
        public int getRoom() { return room; }
        public int getRoomSearches() { return roomSearches; }
        public int getPlayerLevel() { return playerLevel; }
        public int getDifficulty() { return difficulty; }
        public String getPlayerName() { return playerName; }
        public int getMonsterHealth() { return monsterHealth; }
        public int getCurrentEvent() { return currentEvent; }
        
        public void setDifficulty(int difficulty) { this.difficulty = difficulty; }
        public void setPlayerName(String playerName) { this.playerName = playerName; }
        public void setCurrentEvent(int event) { this.currentEvent = event; }
    }
}
