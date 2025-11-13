package gameproject.ui.menu;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import gameproject.MainControllerNew;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the game over screen.
 */
public class GameOverController implements Initializable {
    
    @FXML private Label titleLabel;
    @FXML private Label messageLabel;
    @FXML private Button retryButton;
    @FXML private Button mainMenuButton;
    
    private MainControllerNew mainController;
    
    public void setMainController(MainControllerNew mainController) {
        this.mainController = mainController;
    }
    
    public void setVictory(boolean victory) {
        if (titleLabel != null) {
            titleLabel.setText(victory ? "VICTORY!" : "GAME OVER");
        }
        if (messageLabel != null) {
            messageLabel.setText(victory ? 
                "You have escaped the dungeon!" : 
                "You have fallen in the depths...");
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Controller initialization
    }
    
    @FXML
    private void handleRetry() {
        if (mainController != null) {
            mainController.startNewGameFromMenu();
        }
    }
    
    @FXML
    private void handleMainMenu() {
        if (mainController != null) {
            mainController.showMainMenu();
        }
    }
}

