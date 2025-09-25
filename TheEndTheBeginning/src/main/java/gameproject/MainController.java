package gameproject;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController implements Initializable {
    
    @FXML private TextArea gameTextArea;
    @FXML private TextField inputField;
    @FXML private Button submitButton;
    @FXML private Button startButton;
    @FXML private Button statsButton;
    @FXML private Button resetButton;
    @FXML private Label healthLabel;
    @FXML private Label defenseLabel;
    @FXML private Label attackLabel;
    @FXML private Label levelLabel;
    
    // Game state variables
    private GameState gameState;
    private boolean isGameRunning = false;
    private boolean waitingForInput = false;
    private String expectedInputType = "";
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameState = new GameState();
        displayWelcomeMessage();
        
        // Enable Enter key to submit input
        inputField.setOnAction(event -> handleSubmit());
    }
    
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
    
    private void processInput(String input) {
        waitingForInput = false;
        
        switch (expectedInputType) {
            case "START_CONFIRMATION":
                if (input.equalsIgnoreCase("YES")) {
                    askForDifficulty();
                } else if (input.equalsIgnoreCase("NO")) {
                    showCredits();
                    isGameRunning = false;
                } else {
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
    
    // Inner class to manage game state
    private static class GameState {
        private int health = 100;
        private int defense = 1;
        private int attack = 1;
        private int level = 1;
        private int room = 0;
        private int roomSearches = 0;
        private int playerLevel = 1;
        private int difficulty = 1;
        private String playerName = "";
        private int monsterHealth = 0;
        private int currentEvent = 0;
        
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
