package gameproject.ui.combat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import gameproject.MainControllerNew;
import gameproject.Monster;
import main.model.Player;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the combat overlay screen.
 */
public class CombatOverlayController implements Initializable {
    
    @FXML private Label monsterNameLabel;
    @FXML private Label monsterTypeLabel;
    @FXML private Label monsterHealthLabel;
    @FXML private ProgressBar monsterHealthBar;
    @FXML private Button combatAttackButton;
    @FXML private Button combatHeavyButton;
    @FXML private Button combatDefendButton;
    @FXML private Button combatItemButton;
    @FXML private Button combatSkillButton;
    @FXML private Button combatRunButton;
    @FXML private VBox combatStatusBox;
    @FXML private Label combatStatusLabel;
    
    private MainControllerNew mainController;
    
    public void setMainController(MainControllerNew mainController) {
        this.mainController = mainController;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Controller initialization
    }
    
    public void updateMonsterInfo(Monster monster) {
        if (monster == null) return;
        
        if (monsterNameLabel != null) {
            monsterNameLabel.setText(monster.getName());
        }
        
        if (monsterTypeLabel != null) {
            monsterTypeLabel.setText(monster.getType().toString());
        }
        
        if (monsterHealthBar != null && monsterHealthLabel != null) {
            double healthPercent = (double) monster.getHealth() / monster.getMaxHealth();
            monsterHealthBar.setProgress(Math.max(0.0, Math.min(1.0, healthPercent)));
            monsterHealthLabel.setText("HP: " + monster.getHealth() + "/" + monster.getMaxHealth());
        }
    }
    
    public void updateButtonStates(Player player) {
        if (player == null) return;
        
        if (combatHeavyButton != null) {
            combatHeavyButton.setDisable(player.getMana() < 10);
        }
    }
    
    public void setCombatStatus(String status) {
        if (combatStatusLabel != null) {
            combatStatusLabel.setText(status);
            combatStatusBox.setVisible(status != null && !status.isEmpty());
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
}

