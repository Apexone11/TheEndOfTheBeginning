package gameproject.ui.hud;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import gameproject.MainControllerNew;
import main.model.Player;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the game HUD.
 */
public class GameHudController implements Initializable {
    
    @FXML private Label playerNameLabel;
    @FXML private ProgressBar healthBar;
    @FXML private ProgressBar manaBar;
    @FXML private ProgressBar expBar;
    @FXML private Label healthLabel;
    @FXML private Label manaLabel;
    @FXML private Label levelLabel;
    @FXML private VBox questInfoBox;
    @FXML private Label questTitleLabel;
    @FXML private ProgressBar questProgressBar;
    @FXML private TextArea gameTextArea;
    @FXML private Button attackButton;
    @FXML private Button heavyAttackButton;
    @FXML private Button defendButton;
    @FXML private Button itemButton;
    @FXML private Button skillButton;
    @FXML private Button runButton;
    @FXML private Button inventoryButton;
    @FXML private Button questsButton;
    @FXML private Button pauseButton;
    
    private MainControllerNew mainController;
    
    public void setMainController(MainControllerNew mainController) {
        this.mainController = mainController;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Controller initialization
    }
    
    public void updatePlayerStats(Player player) {
        if (player == null) return;
        
        if (playerNameLabel != null) {
            playerNameLabel.setText(player.getName());
        }
        
        if (healthBar != null && healthLabel != null) {
            double healthPercent = (double) player.getHealth() / player.getMaxHealth();
            healthBar.setProgress(Math.max(0.0, Math.min(1.0, healthPercent)));
            healthLabel.setText(player.getHealth() + "/" + player.getMaxHealth());
        }
        
        if (manaBar != null && manaLabel != null && player.getMaxMana() > 0) {
            double manaPercent = (double) player.getMana() / player.getMaxMana();
            manaBar.setProgress(Math.max(0.0, Math.min(1.0, manaPercent)));
            manaLabel.setText(player.getMana() + "/" + player.getMaxMana());
        }
        
        if (expBar != null && levelLabel != null) {
            if (player.getExperienceToNextLevel() > 0) {
                double expPercent = (double) player.getExperience() / player.getExperienceToNextLevel();
                expBar.setProgress(Math.max(0.0, Math.min(1.0, expPercent)));
            }
            levelLabel.setText("Level " + player.getLevel());
        }
        
        // Update button states
        updateButtonStates(player);
    }
    
    private void updateButtonStates(Player player) {
        if (heavyAttackButton != null) {
            heavyAttackButton.setDisable(player.getMana() < 10);
        }
    }
    
    public void appendGameText(String text) {
        if (gameTextArea != null) {
            gameTextArea.appendText(text);
        }
    }
    
    public void setGameText(String text) {
        if (gameTextArea != null) {
            gameTextArea.setText(text);
        }
    }
    
    @FXML
    private void handleAttack() {
        if (mainController != null) {
            mainController.performCombatAction("attack");
        }
    }
    
    @FXML
    private void handleHeavyAttack() {
        if (mainController != null) {
            mainController.performCombatAction("heavy");
        }
    }
    
    @FXML
    private void handleDefend() {
        if (mainController != null) {
            mainController.performCombatAction("defend");
        }
    }
    
    @FXML
    private void handleItem() {
        if (mainController != null) {
            mainController.showInventory();
        }
    }
    
    @FXML
    private void handleSkill() {
        if (mainController != null) {
            mainController.performCombatAction("skill");
        }
    }
    
    @FXML
    private void handleRun() {
        if (mainController != null) {
            mainController.performCombatAction("run");
        }
    }
    
    @FXML
    private void handleInventory() {
        if (mainController != null) {
            mainController.showInventory();
        }
    }
    
    @FXML
    private void handleQuests() {
        if (mainController != null) {
            mainController.showQuestLog();
        }
    }
    
    @FXML
    private void handlePause() {
        if (mainController != null) {
            mainController.showPauseMenu();
        }
    }
}

